package com.maxminmajcdg.services;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.entities.PADemographicsEntity;
import com.maxminmajcdg.entities.PAVotesEntity;
import com.maxminmajcdg.entities.PennEntity;
import com.maxminmajcdg.repo.PADemographicsRepository;
import com.maxminmajcdg.repo.PAVotesRepository;
import com.maxminmajcdg.repo.PennRepository;

@Service
public class PennService {
	
	@Autowired
	private PennRepository pennRepository;
	
	@Autowired
	private PAVotesRepository paVotesRepository;
	
	@Autowired
	private PADemographicsRepository paDemographicsRepository;
	
	public List<PennEntity> getAllPrecincts() {
		return pennRepository.findAll();
	}

	public Optional<PAVotesEntity> getPrecinctVoteData(Long geomID) {
		return paVotesRepository.findById(geomID);
	}
	
	public Optional<PADemographicsEntity> getPrecinctDemographicData(Long geomID) {
		return paDemographicsRepository.findById(geomID);
	}
}
