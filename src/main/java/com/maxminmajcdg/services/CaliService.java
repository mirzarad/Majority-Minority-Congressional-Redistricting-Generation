package com.maxminmajcdg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.entities.CADemographicsEntity;
import com.maxminmajcdg.entities.CAVotesEntity;
import com.maxminmajcdg.entities.CaliEntity;
import com.maxminmajcdg.repo.CADemographicsRepository;
import com.maxminmajcdg.repo.CAVotesRepository;
import com.maxminmajcdg.repo.CaliRepository;

@Service
public class CaliService {
	
	@Autowired
	private CaliRepository caliRepository;
	
	@Autowired
	private CAVotesRepository caVotesRepository;
	
	@Autowired
	private CADemographicsRepository caDemographicsRepository;
	
	public List<CaliEntity> getAllPrecincts() {
		return caliRepository.findAll();
	}

	public Optional<CAVotesEntity> getPrecinctVoteData(Long geomID) {
		return caVotesRepository.findById(geomID);
	}
	
	public Optional<CADemographicsEntity> getPrecinctDemographicData(Long geomID) {
		return caDemographicsRepository.findById(geomID);
	}
}
