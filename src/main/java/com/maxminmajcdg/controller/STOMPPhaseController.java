package com.maxminmajcdg.controller;

import java.util.Map;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.maxminmajcdg.PrecinctGraph;
import com.maxminmajcdg.Properties;
import com.maxminmajcdg.dto.DistrictResponse;
import com.maxminmajcdg.dto.GraphPartitioningForm;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.services.CaliService;
import com.maxminmajcdg.services.PennService;
import com.maxminmajcdg.services.StateService;

@Controller
@Component
public class STOMPPhaseController {

	@Autowired
	PennService pennService;
	
	@Autowired
	CaliService caliService;
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	  
	
	@MessageMapping("/run.phase1")
	@SendToUser("/phase/results")
	public void phase1(@Payload GraphPartitioningForm phase1Form) {
		System.err.println("Running Phase 1 state: " + phase1Form.getState());

		StateService service;
		switch(phase1Form.getState()) {
		case PENN:
			service = pennService;
			break;
		case CALI:
			service = caliService;
			break;
			default:
				return;
		}

		Map<Integer, NeighborDistrictWrapper> precincts = service.getNeighbors(phase1Form.getElection());
		Map<Integer, Double> precinctsPopulation = service.getNeighborPopulations(phase1Form.getElection());
		int totalPopulation = service.getTotalPopulation(phase1Form.getElection()).intValue();

		PrecinctGraph graph = new PrecinctGraph(precincts, 
				precinctsPopulation,
				phase1Form.getState(), 
				phase1Form.getElection(), 
				phase1Form.getDemographics(), 
				totalPopulation, 
				phase1Form.getNumberOfDistricts(),
				phase1Form.getMaxDemographicBlocPercentage(),
				phase1Form.getMinDemographicBlocPercentage());
		
		System.out.println(graph.getNumDistricts());
		NeighborDistrictWrapper randomPrecinct = null;
		while (!graph.isFinished() && graph.getPhase1Iter() < Properties.MAX_ITERATIONS && graph.getSuccessiveFails() < Properties.MAX_FAILS) {
			if (randomPrecinct == null || !randomPrecinct.isPhase1ThresholdMet(phase1Form.getElection(), phase1Form.getDemographics(), phase1Form.getMaxDemographicBlocPercentage(), phase1Form.getMinDemographicBlocPercentage())) {
				randomPrecinct = graph.getRandomPrecinct();	
				if (randomPrecinct == null) {
					graph.updatePhase1Iter();
					continue;
				}
				messagingTemplate.convertAndSend("/phase/results", new DistrictResponse(randomPrecinct, phase1Form.getElection()));
			}
			NeighborDistrictWrapper optimalPrecinct = graph.getOptimalPrecinct(randomPrecinct);	
			Pair<NeighborDistrictWrapper, DistrictResponse> merged = graph.join(randomPrecinct, optimalPrecinct);
			
			graph.updatePhase1Iter();
			if (merged == null) {
				continue;
			}
			randomPrecinct = merged.getValue0();
			messagingTemplate.convertAndSend("/phase/results", merged.getValue1());
		}
 
		while(!graph.isFinished()) {
			Pair<NeighborDistrictWrapper, DistrictResponse> finalDistrict = graph.finalizeDistricts();
			messagingTemplate.convertAndSend("/phase/results", finalDistrict.getValue1());
		}
		messagingTemplate.convertAndSend("/phase/results", "DONE");
	}
}