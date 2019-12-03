package com.maxminmajcdg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maxminmajcdg.dto.DemVotePair;
import com.maxminmajcdg.dto.DemographicBlocForm;
import com.maxminmajcdg.dto.GraphPartitioningForm;
import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.dto.SimmulatedAnnealingForm;
import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.ElectionCategory;
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
	
	@PostMapping(value = "/runPhase0")
	@ResponseBody
	public Response<List<DemVotePair>> runPhase0(@RequestBody DemographicBlocForm phase0Form){
		System.err.println("Running Phase 0: Initial Run");
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
				
		List<?> demographics = service.getDemographicBloc(election, demographicThreshold);

		for (Object demographicObject : demographics) {
			DemographicsEntity demographic = (DemographicsEntity) demographicObject;
			Long geomID = demographic.getGeomID();
			if (service.votesAsBloc(election, geomID, voteThreshold)) {
				DemVotePair data = new DemVotePair();
				Object voteObject = service.getPrecinctVoteData(election, geomID).get();
				VoteEntity vote = (VoteEntity) voteObject;
				data.setDemographics(demographic);
				data.setVotes(vote);
				precincts.add(data);
			}
		}
				
		result.setMessage("Success");
		result.setResponse(precincts);
		return result;
	}
	
	@PostMapping(value="/pausePhase0")
	@ResponseBody
	public ResponseEntity<Response<Object>> pausePhase0() {
		System.err.println("Pausing Phase 0");
	    
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/resumePhase0")
	@ResponseBody
	public ResponseEntity<Response<Object>> resumePhase0() {
		System.err.println("Resuming Phase 0");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/stopPhase0")
	@ResponseBody
	public ResponseEntity<Response<Object>> stopPhase0() {
		System.err.println("Stopping Phase 0");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "/runPhase1")
	@ResponseBody
	public ResponseEntity<Response<Object>> runPhase1(@RequestBody GraphPartitioningForm phase1Form, HttpSession session){
		System.err.println("Running Phase 1: Initial Run");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/pausePhase1")
	@ResponseBody
	public ResponseEntity<Response<Object>> pausePhase1() {
		System.err.println("Pausing Phase 1");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/resumePhase1")
	@ResponseBody
	public ResponseEntity<Response<Object>> resumePhase1() {
		System.err.println("Resuming Phase 1");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/stopPhase1")
	@ResponseBody
	public ResponseEntity<Response<Object>> stopPhase1() {
		System.err.println("Stopping Phase 1");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value = "/runPhase2")
	@ResponseBody
	public ResponseEntity<Response<Object>> runPhase2(@RequestBody SimmulatedAnnealingForm phase2Form, HttpSession session){
		System.err.println("Running Phase 2: Initial Run");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/pausePhase2")
	@ResponseBody
	public ResponseEntity<Response<Object>> pausePhase2() {
		System.err.println("Pausing Phase 2");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/resumePhase2")
	@ResponseBody
	public ResponseEntity<Response<Object>> resumePhase2() {
		System.err.println("Resuming Phase 2");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value="/stopPhase2")
	@ResponseBody
	public ResponseEntity<Response<Object>> stopPhase2() {
		System.err.println("Stopping Phase 2");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
}