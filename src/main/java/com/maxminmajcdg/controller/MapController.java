package com.maxminmajcdg.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Response<StateDataResponse>> onHoverState(@RequestBody Map<String, Integer> state) {
		System.err.println("State ID: " + state.get("id"));
		
		Response<StateDataResponse> result = new Response<StateDataResponse>();
		result.setMessage("Success");
		result.setResponse(new StateDataResponse());
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(value="/precinctHover/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response<StateDataResponse>> onHoverPrecinct(@RequestBody Map<String, Integer> precinct) {
		System.err.println("State ID: " + precinct.get("id"));
		
		Response<StateDataResponse> result = new Response<StateDataResponse>();
		result.setMessage("Success");
		result.setResponse(new StateDataResponse());
		return ResponseEntity.ok(result);
	}

}