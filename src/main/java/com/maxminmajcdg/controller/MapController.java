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
import com.maxminmajcdg.entities.CADemographicsEntity;
import com.maxminmajcdg.entities.CAVotesEntity;
import com.maxminmajcdg.entities.PADemographicsEntity;
import com.maxminmajcdg.entities.PAVotesEntity;
import com.maxminmajcdg.services.CaliService;
import com.maxminmajcdg.services.PennService;
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
	
	//@Autowired
	//CAVotesService caVotes;
	
	@RequestMapping(value="/stateHover/full/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response<Map<?, ?>>> onHoverState(@PathVariable(value="id") int stateId) {
		System.err.println("State ID: " + stateId);
		
		Response<Map<?, ?>> result = new Response<Map<?, ?>>();
		result.setMessage("Success");
		result.setResponse(usaService.getState(new Long(stateId)));
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value="/precinctHover/{state}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response<StateDataResponse<?, ?>> onHoverPrecinct(@PathVariable(value="id") int precinctId, @PathVariable(value="state") String state) {
		System.err.println("Precinct ID: " + precinctId);
		
		Response<StateDataResponse<?, ?>> result = new Response<StateDataResponse<?, ?>>(); 
		if (state.equals("penn")) {
			StateDataResponse<PAVotesEntity, PADemographicsEntity> data = new StateDataResponse<PAVotesEntity, PADemographicsEntity>();
			data.setVotes(pennService.getPrecinctVoteData(new Long(precinctId)));
			data.setDemographics(pennService.getPrecinctDemographicData(new Long(precinctId)));
			result.setResponse(data);
		} 
		else if (state.equals("california")) {
			StateDataResponse<CAVotesEntity, CADemographicsEntity> data = new StateDataResponse<CAVotesEntity, CADemographicsEntity>();
			data.setVotes(caliService.getPrecinctVoteData(new Long(precinctId)));
			data.setDemographics(caliService.getPrecinctDemographicData(new Long(precinctId)));
			result.setResponse(data);
		}
		
		result.setMessage("Success");
		return result;
	}

}