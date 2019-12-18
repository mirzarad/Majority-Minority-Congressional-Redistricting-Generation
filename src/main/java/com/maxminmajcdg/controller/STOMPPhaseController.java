package com.maxminmajcdg.controller;

import java.util.Map;
import java.util.Set;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.maxminmajcdg.PrecinctGraph;
import com.maxminmajcdg.Properties;
import com.maxminmajcdg.dto.GraphPartitioningForm;
import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.NeighborEntity;
import com.maxminmajcdg.services.CaliService;
import com.maxminmajcdg.services.PennService;
import com.maxminmajcdg.services.StateService;

@Controller
public class STOMPPhaseController {
	@Autowired
	PennService pennService;
	
	@Autowired
	CaliService caliService;
	
	private static SimpMessageSendingOperations messagingTemplate;

	@Autowired
	public STOMPPhaseController(SimpMessageSendingOperations messagingTemplate) {
	    STOMPPhaseController.messagingTemplate = messagingTemplate;
	}
	
	@MessageMapping("/run_phase1")
	@SendTo("/phase1/results")
	public Response<?> phase1(@RequestBody GraphPartitioningForm phase1Form) {
		String hello = "hello";
		STOMPPhaseController.messagingTemplate.convertAndSend(hello);
		System.err.println("Running Phase 1 state: " + phase1Form.getState());

		Response<NeighborEntity> result = new Response<NeighborEntity>();
		result.setMessage("Success");
		
		StateService service;
		switch(phase1Form.getState()) {
		case PENN:
			service = pennService;
			break;
		case CALI:
			service = caliService;
			break;
			default:
				return null;
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
		while (!graph.isFinished() && graph.getPhase1Iter() < Properties.MAX_ITERATIONS && graph.getSuccessiveFails() < Properties.MAX_FAILS) {
			NeighborDistrictWrapper randomPrecinct = graph.getRandomPrecinct();		
			NeighborDistrictWrapper optimalPrecinct = graph.getOptimalPrecinct(randomPrecinct);	
			Pair<Integer, Set<Integer>> merged = graph.join(randomPrecinct, optimalPrecinct);
			graph.updatePhase1Iter();
			if (merged == null) {
				continue;
			}
			STOMPPhaseController.messagingTemplate.convertAndSend(merged);
		}
 
		while(!graph.isFinished()) {
			Pair<Integer, Set<Integer>> finalDistrict = graph.finalizeDistricts();
			System.out.println(finalDistrict);
			STOMPPhaseController.messagingTemplate.convertAndSend(finalDistrict);
		}
		
		return result;
	}
}
