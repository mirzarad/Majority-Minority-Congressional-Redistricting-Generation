package com.maxminmajcdg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.entities.DistrictEntity;
import com.maxminmajcdg.repo.DistrictRepository;

@Service
public class DistrictService {
	
	@Autowired
	private DistrictRepository districtRepository;
	
	public List<DistrictEntity> getState(String name) {
		return districtRepository.findByStateFp(name);
	}
}
