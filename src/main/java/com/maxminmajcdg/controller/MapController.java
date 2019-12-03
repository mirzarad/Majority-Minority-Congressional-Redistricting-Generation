package com.maxminmajcdg.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.dto.StateDataResponse;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.services.CaliService;
import com.maxminmajcdg.services.PennService;
import com.maxminmajcdg.services.USAService;

@Controller
@RequestMapping("/map")
public class MapController{
	
	private final String PENN = "penn";
	private final String CA = "california";
	
	@Autowired
	USAService usaService;
	
	@Autowired
	PennService pennService;
	
	@Autowired
	CaliService caliService;
	
	//@Autowired
	//CAVotesService caVotes;
	
	@RequestMapping(value="/stateHover/full/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response<Map<?, ?>>> onHoverState(@PathVariable(value="id") String stateId) {
		System.err.println("State ID: " + stateId);
		
		Response<Map<?, ?>> result = new Response<Map<?, ?>>();
		result.setMessage("Success");
		result.setResponse(usaService.getState(stateId));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value="/precinctHover/{state}/{id}/{election}", method = RequestMethod.GET)
	@ResponseBody
	public Response<StateDataResponse> onHoverPrecinct(@PathVariable(value="id") int precinctId, @PathVariable(value="state") String state, @PathVariable(value="election") String election) {
		System.err.println("Precinct ID: " + precinctId);
		
		ElectionCategory electionEnum = ElectionCategory.fromValue(election);
		
		Response<StateDataResponse> result = new Response<StateDataResponse>(); 
		StateDataResponse data = new StateDataResponse();
		
		switch(state) {
		case PENN:
			data.setVotes(pennService.getPrecinctVoteData(electionEnum, new Long(precinctId)));
			data.setDemographics(pennService.getPrecinctDemographicData(electionEnum, new Long(precinctId)));
			result.setResponse(data);
			break;
		case CA:
			data.setVotes(caliService.getPrecinctVoteData(electionEnum, new Long(precinctId)));
			data.setDemographics(caliService.getPrecinctDemographicData(electionEnum, new Long(precinctId)));
			result.setResponse(data);
			break;
		}
		
		result.setMessage("Success");
		return result;
	}
	
	@RequestMapping(value="/districtHover/{state}/{id}/{election}", method = RequestMethod.GET)
	@ResponseBody
	public Response<StateDataResponse> onHoverDistrict(@PathVariable(value="id") int districtId, @PathVariable(value="state") String state, @PathVariable(value="election") String election) {
		System.err.println("Precinct ID: " + districtId);
		
		ElectionCategory electionEnum = ElectionCategory.fromValue(election);
		
		Response<StateDataResponse> result = new Response<StateDataResponse>(); 
		StateDataResponse data = new StateDataResponse();
		
		switch(state) {
		case PENN:
			data.setVotes(pennService.getPrecinctVoteData(electionEnum, new Long(districtId)));
			data.setDemographics(pennService.getPrecinctDemographicData(electionEnum, new Long(districtId)));
			result.setResponse(data);
			break;
		case CA:
			data.setVotes(caliService.getPrecinctVoteData(electionEnum, new Long(districtId)));
			data.setDemographics(caliService.getPrecinctDemographicData(electionEnum, new Long(districtId)));
			result.setResponse(data);
			break;
		}
		
		result.setMessage("Success");
		return result;
	}

}