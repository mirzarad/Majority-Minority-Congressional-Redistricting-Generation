package com.maxminmajcdg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maxminmajcdg.Mode;
import com.maxminmajcdg.PrecinctGraph;
import com.maxminmajcdg.Properties;
import com.maxminmajcdg.dto.DemVotePair;
import com.maxminmajcdg.dto.DemographicBlocForm;
import com.maxminmajcdg.dto.GraphPartitioningForm;
import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.dto.SimmulatedAnnealingForm;
import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.NeighborEntity;
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
		
		Map<Long, DemographicsEntity> demographics = service.getDemographicBloc(election, demographicThreshold);
		
		Set<Long> geomID = demographics.keySet();
		Map<Long, VoteEntity> votes = service.votesAsBloc(election, geomID, voteThreshold);

		Set<Long> voteGeomID = votes.keySet();
		geomID.retainAll(voteGeomID);
		

		for(Long blocGeomID : geomID) {
			DemVotePair pair = new DemVotePair();
			pair.setDemographics(demographics.get(blocGeomID));
			pair.setVotes(votes.get(blocGeomID));
			precincts.add(pair);
		}
		result.setMessage("Success");
		result.setResponse(precincts);	

		return result;
	}
		
	@PostMapping(value = "/phase1/{mode}")
	@ResponseBody
	public Response<?> phase1(@RequestBody GraphPartitioningForm phase1Form, @PathVariable(name="mode") String mode, HttpServletResponse response) throws IOException {
		System.err.println("Running Phase 1: " + mode + " state: " + phase1Form.getState());

		Response<NeighborEntity> result = new Response<NeighborEntity>();
		result.setMessage("Success");
		Mode modeEnum = Mode.fromValue(mode);	

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
		System.out.println("DAMN");

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
		
		switch(modeEnum) {
		case ITERATE:
			System.out.println(graph.getNumDistricts());
			NeighborDistrictWrapper randomPrecinct;
			do
			{
				long a = System.nanoTime();
				randomPrecinct = graph.getRandomPrecinct();			
				NeighborDistrictWrapper optimalPrecinct = graph.getOptimalPrecinct(randomPrecinct);			
				NeighborDistrictWrapper merged = graph.join(randomPrecinct, optimalPrecinct);
				System.out.println(System.nanoTime() - a);
			} while (randomPrecinct != null || graph.isFinished() || graph.getPhase1Iter() >= Properties.MAX_ITERATIONS);
			
			while(!graph.isFinished()) {
				NeighborDistrictWrapper finalDistrict = graph.finalizeDistricts();
			}
			System.out.println(graph.getNumDistricts());
			System.out.println(graph.getLiveDistricts());
			return result;
		case FULL:
			return null;
			default:
				return null;
		}
		
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