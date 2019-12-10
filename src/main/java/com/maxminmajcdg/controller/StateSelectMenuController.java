package com.maxminmajcdg.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maxminmajcdg.Properties;
import com.maxminmajcdg.dto.LeafletResponse;
import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.entities.DistrictEntity;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.MapEntity;
import com.maxminmajcdg.entities.StateEntity;
import com.maxminmajcdg.entities.States;
import com.maxminmajcdg.entities.USAEntity;
import com.maxminmajcdg.services.CaliService;
import com.maxminmajcdg.services.DistrictService;
import com.maxminmajcdg.services.PennService;
import com.maxminmajcdg.services.StateService;
import com.maxminmajcdg.services.USAService;


@RestController
@RequestMapping("/selectState")
public class StateSelectMenuController{	
	@Autowired 
	PennService pennService;
	
	@Autowired 
	CaliService caliService;
	
	@Autowired 
	USAService usaService;
	
	@Autowired 
	DistrictService districtService;
	
	@GetMapping(value="/districts/{state}")
	@ResponseBody
	public Response<LeafletResponse> selectDistricts(@PathVariable(name="state") String state) {
		System.err.println("GOT DISTRICT " + state);
		LeafletResponse leaflet = new LeafletResponse();
		
		States stateEnum = States.fromValue(state);
		
		String id = stateEnum.getId();
		
		switch (stateEnum) {
		case CALI:
			leaflet.setView(Properties.CALI_VIEW);
			leaflet.setLevel(Properties.CALI_LEVEL);
			break;
		case PENN:
			leaflet.setView(Properties.PENN_VIEW);
			leaflet.setLevel(Properties.PENN_LEVEL);
			break;
		case USA:
			leaflet.setView(Properties.USA_VIEW);
			leaflet.setLevel(Properties.USA_LEVEL);
			break;
			default:
				return null;
		}
		
		
		MapEntity<DistrictEntity> districts = new MapEntity<DistrictEntity>();
		districts.setFeatures(districtService.getState(id));
		leaflet.setMap(districts);
		
		Response<LeafletResponse> result = new Response<LeafletResponse>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return (result);
	}
	
	@RequestMapping(value="/precincts/{election}/{state}", method = RequestMethod.GET)
	@ResponseBody
	@Async(value="asyncExecutor")
	public CompletableFuture<Response<LeafletResponse>> selectState(@PathVariable(name = "election") String election, @PathVariable(name = "state") String state) throws InterruptedException{
		System.err.println("GOT " + state);
		LeafletResponse leaflet = new LeafletResponse();
		
		States stateEnum = States.fromValue(state);
		ElectionCategory electionEnum = ElectionCategory.fromValue(election);
		
		MapEntity<?> mapEntity = new MapEntity<StateEntity>();
		
		StateService service;
		
		switch(stateEnum) {
		case PENN:
			service = pennService;
			leaflet.setView(Properties.PENN_VIEW);
			leaflet.setLevel(Properties.PENN_LEVEL);
			break;
		case CALI:
			service = caliService;
			leaflet.setView(Properties.CALI_VIEW);
			leaflet.setLevel(Properties.CALI_LEVEL);
			break;
		default:
			return null;
		}
		
		mapEntity.setFeatures(service.getAllPrecincts(electionEnum));
		leaflet.setMap(mapEntity);

		Response<LeafletResponse> result = new Response<LeafletResponse>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return CompletableFuture.completedFuture(result);
	}
	
	@RequestMapping(value="/usa", method = RequestMethod.GET)
	@ResponseBody
	public Response<LeafletResponse> selectFull() {
		System.err.println("GOT FULL MAP REQUEST");
		LeafletResponse leaflet = new LeafletResponse();
		leaflet.setView(Properties.USA_VIEW);
		leaflet.setLevel(Properties.USA_LEVEL);
		
		MapEntity<USAEntity> usa = new MapEntity<USAEntity>();
		usa.setFeatures(usaService.list());
		leaflet.setMap(usa);
		
		Response<LeafletResponse> result = new Response<LeafletResponse>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return result;
	}
};