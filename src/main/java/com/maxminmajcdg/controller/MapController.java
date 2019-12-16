package com.maxminmajcdg.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxminmajcdg.States;
import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.dto.StateDataResponse;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.services.CaliService;
import com.maxminmajcdg.services.PennService;
import com.maxminmajcdg.services.StateService;
import com.maxminmajcdg.services.USAService;

@Controller
@RequestMapping("/map")
public class MapController{
	
	@Autowired
	USAService usaService;
	
	@Autowired
	PennService pennService;
	
	@Autowired
	CaliService caliService;
	
	@RequestMapping(value="/stateHover/full/{id}/{election}", method = RequestMethod.GET)
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
				
		Response<StateDataResponse> result = new Response<StateDataResponse>(); 
		StateDataResponse data = new StateDataResponse();
		ElectionCategory electionEnum = ElectionCategory.fromValue(election);
		States stateEnum = States.fromValue(state);
		
		StateService service;
		
		switch(stateEnum) {
		case PENN:
			service = pennService;
			break;
		case CALI:
			service = caliService;
			break;
			default:
				return null;
		}
		
		data.setVotes(service.getPrecinctVoteData(electionEnum, new Long(precinctId)));
		data.setDemographics(service.getPrecinctDemographicData(electionEnum, new Long(precinctId)));
		result.setResponse(data);
		result.setMessage("Success");
		return result;
	}
	
	@RequestMapping(value="/districtHover/{state}/{id}/{election}", method = RequestMethod.GET)
	@ResponseBody
	public Response<StateDataResponse> onHoverDistrict(@PathVariable(value="id") int districtId, @PathVariable(value="state") String state, @PathVariable(value="election") String election) {
		System.err.println("Precinct ID: " + districtId);
				
		Response<StateDataResponse> result = new Response<StateDataResponse>(); 
		StateDataResponse data = new StateDataResponse();
		ElectionCategory electionEnum = ElectionCategory.fromValue(election);
		States stateEnum = States.fromValue(state);
		
		StateService service;
		
		switch(stateEnum) {
		case PENN:
			service = pennService;
			break;
		case CALI:
			service = caliService;
			break;
			default:
				return null;
		}
		
		//data.setVotes(service.getPrecinctVoteData(electionEnum, new Long(districtId)));
		//data.setDemographics(service.getPrecinctDemographicData(electionEnum, new Long(districtId)));
		//result.setResponse(data);
		result.setMessage("Success");
		return result;
	}

}