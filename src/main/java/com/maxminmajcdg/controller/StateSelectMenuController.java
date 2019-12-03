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

import com.maxminmajcdg.dto.LeafletResponse;
import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.entities.CaliEntity;
import com.maxminmajcdg.entities.DistrictEntity;
import com.maxminmajcdg.entities.MapEntity;
import com.maxminmajcdg.entities.PennEntity;
import com.maxminmajcdg.entities.USAEntity;
import com.maxminmajcdg.services.CaliService;
import com.maxminmajcdg.services.DistrictService;
import com.maxminmajcdg.services.PennService;
import com.maxminmajcdg.services.USAService;


@RestController
@RequestMapping("/selectState")
public class StateSelectMenuController{
	private final float[] USA_VIEW = new float[]{37.8f, -96f};
	private final int USA_LEVEL = 4;
	
	private final float[] CALI_VIEW = new float[]{36.77f, -119.41f};
	private final int CALI_LEVEL = 5;
	
	private final float[] PENN_VIEW = new float[]{40.54f, -77.49f};
	private final int PENN_LEVEL = 5;
	
	private final String PENN_ID = "42";
	private final String CALI_ID = "6";
	private final String CALI_CORRECT_ID = "06";
	
	@Autowired 
	PennService pennService;
	
	@Autowired 
	CaliService caliService;
	
	@Autowired 
	USAService usaService;
	
	@Autowired 
	DistrictService districtService;
	
	@GetMapping(value="/districts/{id}")
	@ResponseBody
	public Response<LeafletResponse<DistrictEntity>> selectDistricts(@PathVariable(name="id") String id) {
		System.err.println("GOT DISTRICT " + id);
		LeafletResponse<DistrictEntity> leaflet = new LeafletResponse<DistrictEntity>();
		
		switch (id) {
		case CALI_ID:
			id = CALI_CORRECT_ID;
			leaflet.setView(CALI_VIEW);
			leaflet.setLevel(CALI_LEVEL);
			break;
		case PENN_ID:
			leaflet.setView(PENN_VIEW);
			leaflet.setLevel(PENN_LEVEL);
			break;
		default:
			leaflet.setView(USA_VIEW);
			leaflet.setLevel(USA_LEVEL);
			break;
		}
		
		
		MapEntity<DistrictEntity> districts = new MapEntity<DistrictEntity>();
		districts.setFeatures(districtService.getState(id));
		leaflet.setMap(districts);
		
		Response<LeafletResponse<DistrictEntity>> result = new Response<LeafletResponse<DistrictEntity>>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return (result);
	}
	
	@RequestMapping(value="/california", method = RequestMethod.GET)
	@ResponseBody
	@Async(value="asyncExecutor")
	public CompletableFuture<Response<LeafletResponse<CaliEntity>>> selectNewYork() throws InterruptedException{
		System.err.println("GOT California");
		LeafletResponse<CaliEntity> leaflet = new LeafletResponse<CaliEntity>();
		//leaflet.setView(CALI_VIEW);
		//leaflet.setLevel(CALI_LEVEL);
		
		MapEntity<CaliEntity> cali = new MapEntity<CaliEntity>();
		cali.setFeatures(caliService.getAllPrecincts());
		leaflet.setMap(cali);
		
		Response<LeafletResponse<CaliEntity>> result = new Response<LeafletResponse<CaliEntity>>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return CompletableFuture.completedFuture(result);
	}
	
	@GetMapping(value="/penn")
	@ResponseBody
	@Async(value="asyncExecutor")
	public CompletableFuture<Response<LeafletResponse<PennEntity>>> selectPennsylvania() {
		System.err.println("GOT PENNSYLVANIA");
		LeafletResponse<PennEntity> leaflet = new LeafletResponse<PennEntity>();
		//leaflet.setView(PENN_VIEW);
		//leaflet.setLevel(PENN_LEVEL);
		
		MapEntity<PennEntity> penn = new MapEntity<PennEntity>();
		penn.setFeatures(pennService.getAllPrecincts());
		leaflet.setMap(penn);
		
		Response<LeafletResponse<PennEntity>> result = new Response<LeafletResponse<PennEntity>>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return CompletableFuture.completedFuture(result);
	}
	
	@RequestMapping(value="/full", method = RequestMethod.GET)
	@ResponseBody
	public Response<LeafletResponse<USAEntity>> selectFull() {
		System.err.println("GOT FULL MAP REQUEST");
		LeafletResponse<USAEntity> leaflet = new LeafletResponse<USAEntity>();
		leaflet.setView(USA_VIEW);
		leaflet.setLevel(USA_LEVEL);
		
		MapEntity<USAEntity> usa = new MapEntity<USAEntity>();
		usa.setFeatures(usaService.list());
		leaflet.setMap(usa);
		
		Response<LeafletResponse<USAEntity>> result = new Response<LeafletResponse<USAEntity>>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return result;
	}
};