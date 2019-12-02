package com.maxminmajcdg.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.entities.USAEntity;
import com.maxminmajcdg.repo.USARepository;

@Service
public class USAService {
	
	@Autowired
	private USARepository usaRepository;
	
	public List<USAEntity> list() {
		return usaRepository.findAll();
	}
	
	public Map<String, String> getState(String id) {
		USAEntity usa = usaRepository.findByStateId(id);
		return (usa == null)? null : usa.getProperties();
	}
}
