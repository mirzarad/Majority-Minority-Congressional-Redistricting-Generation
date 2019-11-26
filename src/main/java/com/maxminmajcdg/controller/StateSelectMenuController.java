package com.maxminmajcdg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maxminmajcdg.dto.Response;
import com.maxminmajcdg.entities.LeafletEntity;
import com.maxminmajcdg.entities.MapEntity;
import com.maxminmajcdg.entities.PennEntity;
import com.maxminmajcdg.entities.USAEntity;
import com.maxminmajcdg.services.PennService;


@RestController
@RequestMapping("/selectState")
public class StateSelectMenuController{
	private final float[] USA_VIEW = new float[]{37.8f, -96f};
	private final int USA_LEVEL = 4;
	
	@Autowired 
	PennService pennService;
	
	@RequestMapping(value="/california", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response<Object>> selectNewYork() {
		System.out.println("GOT California");
		Response<Object> result = new Response<Object>();
		result.setMessage("Success");
		return ResponseEntity.ok(result);
	}
	
	@GetMapping(value="/penn")
	@ResponseBody
	public Response<LeafletEntity<PennEntity>> selectPennsylvania() {
		System.err.println("GOT PENNSYLVANIA");
		LeafletEntity<PennEntity> leaflet = new LeafletEntity<PennEntity>();
		leaflet.setView(new float[]{22.0f, 2.2f});
		leaflet.setLevel(4);
		
		MapEntity<PennEntity> penn = new MapEntity<PennEntity>();
		penn.setFeatures(pennService.list());
		leaflet.setMap(penn);
		
		Response<LeafletEntity<PennEntity>> result = new Response<LeafletEntity<PennEntity>>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return result;
	}
	
	@RequestMapping(value="/full", method = RequestMethod.GET)
	@ResponseBody
	public Response<LeafletEntity<USAEntity>> selectFull() {
		System.err.println("GOT FULL MAP REQUEST");
		LeafletEntity<USAEntity> leaflet = new LeafletEntity<USAEntity>();
		leaflet.setView(USA_VIEW);
		leaflet.setLevel(USA_LEVEL);
		
		MapEntity<USAEntity> usa = new MapEntity<USAEntity>();
		leaflet.setMap(usa);
		
		Response<LeafletEntity<USAEntity>> result = new Response<LeafletEntity<USAEntity>>();
		result.setMessage("Success");
		result.setResponse(leaflet);
		return result;
	}
};