package com.maxminmajcdg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maxminmajcdg.DemographicCategory;
import com.maxminmajcdg.PartyCategory;
import com.maxminmajcdg.PrecinctGraph;
import com.maxminmajcdg.Properties;
import com.maxminmajcdg.dto.DemVotePair;
import com.maxminmajcdg.dto.DemographicBlocForm;
import com.maxminmajcdg.dto.DistrictResponse;
import com.maxminmajcdg.dto.GraphPartitioningForm;
import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.dto.SimmulatedAnnealingForm;
import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.VoteEntity;
import com.maxminmajcdg.services.CaliService;
import com.maxminmajcdg.services.PennService;
import com.maxminmajcdg.services.StateService;

@RestController
@RequestMapping("/phaseController")
public class PhaseController{
	
	@Autowired
	PennService pennService;
	
	@Autowired
	CaliService caliService;
				
	private PrecinctGraph graph;

	@PostMapping(value="/phase0")
	@ResponseBody
	public Response<?> phase0(@RequestBody DemographicBlocForm phase0Form) {
		System.err.println("PHASE 0 " + phase0Form.getState() + " " + phase0Form.getElection());		
		
		Response<List<DemVotePair>> result = new Response<List<DemVotePair>>();
		List<DemVotePair> precincts = new ArrayList<DemVotePair>();
			
		ElectionCategory election = phase0Form.getElection();
		float demographicThreshold =  phase0Form.getDemographicBlocPercentage();
		float voteThreshold =  phase0Form.getVoteBlocPercentage();
		
		StateService service;
		switch(phase0Form.getState()) {
		case PENN:
			service = pennService;
			break;
		case CALI:
			service = caliService;
			break;
			default:
				return null;
		}
		
		Map<Long, Pair<DemographicsEntity, DemographicCategory>> demographics = service.getDemographicBloc(election, demographicThreshold);
		
		Set<Long> geomID = demographics.keySet();
		Map<Long, Pair<VoteEntity, PartyCategory>> votes = service.votesAsBloc(election, geomID, voteThreshold);

		Set<Long> voteGeomID = votes.keySet();
		geomID.retainAll(voteGeomID);
		

		for(Long blocGeomID : geomID) {
			DemVotePair pair = new DemVotePair();
			pair.setDemographics(demographics.get(blocGeomID).getValue0());
			pair.setMaxDemographic(demographics.get(blocGeomID).getValue1());
			pair.setVotes(votes.get(blocGeomID).getValue0());
			pair.setMaxVote(votes.get(blocGeomID).getValue1());
			precincts.add(pair);
		}
		result.setMessage("Success");
		result.setResponse(precincts);	

		return result;
	}
	
	@PostMapping(value = "/phase1/{mode}")
	@ResponseBody
	public DistrictResponse phase1(@RequestBody GraphPartitioningForm phase1Form, @PathVariable(name="mode") String mode) {
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
				return null;
		}
		
		if (mode.equals("INIT")) {
			Map<Integer, NeighborDistrictWrapper> precincts = service.getNeighbors(phase1Form.getElection());
			Map<Integer, Double> precinctsPopulation = service.getNeighborPopulations(phase1Form.getElection());
			int totalPopulation = service.getTotalPopulation(phase1Form.getElection()).intValue();
			if (graph == null || !(graph.getStateName().equals(phase1Form.getState()) && graph.getElection().equals(phase1Form.getElection()))) {
				graph = new PrecinctGraph(precincts);
			}
			graph.reset();
			graph.setStateName(phase1Form.getState());
			graph.setPrecinctsPopulation(precinctsPopulation);
			graph.setState(phase1Form.getState());
			graph.setElection(phase1Form.getElection());
			graph.setDeomographics(phase1Form.getDemographics());
			graph.setTotalPopulation(totalPopulation);
			graph.setNumDistricts(phase1Form.getNumberOfDistricts());
			graph.setMaxDemographicBlocPercentage(phase1Form.getMaxDemographicBlocPercentage());
			graph.setMinDemographicBlocPercentage(phase1Form.getMinDemographicBlocPercentage());
		}
		
		
		System.out.println(graph.getNumDistricts());
		while (!graph.isFinished() && graph.getPhase1Iter() < Properties.MAX_ITERATIONS && graph.getSuccessiveFails() < Properties.MAX_FAILS) {
			NeighborDistrictWrapper randomPrecinct = graph.getRandomPrecinct();		
			NeighborDistrictWrapper optimalPrecinct = graph.getOptimalPrecinct(randomPrecinct);	
			DistrictResponse merged = graph.join(randomPrecinct, optimalPrecinct);
			graph.updatePhase1Iter();
			if (merged == null) {
				continue;
			}
			return merged;
		}
 
		while(!graph.isFinished()) {
			DistrictResponse finalDistrict = graph.finalizeDistricts();
			return finalDistrict;
		}
		
		return null;
	}
	
	@PostMapping(value = "/phase2/iterate")
	@ResponseBody
	public ResponseEntity<Response<Object>> phase2Iterate(@RequestBody SimmulatedAnnealingForm phase2Form){
		System.err.println("Running Phase 2: Initial Run");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value = "/phase2/entire")
	@ResponseBody
	public ResponseEntity<Response<Object>> phase2Entire(@RequestBody SimmulatedAnnealingForm phase2Form){
		System.err.println("Running Phase 2: Initial Run");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}

}