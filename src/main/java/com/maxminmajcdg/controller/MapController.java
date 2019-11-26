package com.maxminmajcdg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.dto.StateDataResponse;

@Controller
@RequestMapping("/map")
public class MapController{

	@RequestMapping(value="/stateHover/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response<StateDataResponse>> onHoverState(@PathVariable(value="id") int stateId) {
		System.err.println("State ID: " + stateId);
		
		Response<StateDataResponse> result = new Response<StateDataResponse>();
		result.setMessage("Success");
		result.setResponse(new StateDataResponse());
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value="/precinctHover/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response<StateDataResponse>> onHoverPrecinct(@PathVariable(value="id") int precinctId) {
		System.err.println("Precinct ID: " + precinctId);
		
		Response<StateDataResponse> result = new Response<StateDataResponse>();
		result.setMessage("Success");
		result.setResponse(new StateDataResponse());
		return ResponseEntity.ok(result);
	}

}