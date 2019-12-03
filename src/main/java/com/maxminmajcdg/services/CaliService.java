package com.maxminmajcdg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.entities.CADemographics2016Entity;
import com.maxminmajcdg.entities.CaliEntity;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.repo.CADemographicsRepository;
import com.maxminmajcdg.repo.CAVotesCong2016Repository;
import com.maxminmajcdg.repo.CAVotesCong2018Repository;
import com.maxminmajcdg.repo.CAVotesPres2016Repository;
import com.maxminmajcdg.repo.CaliRepository;

@Service
public class CaliService {
	
	@Autowired
	private CaliRepository caliRepository;
	
	@Autowired
	private CAVotesCong2016Repository caVotesCong2016Repository;
	
	@Autowired
	private CAVotesPres2016Repository caVotesPres2016Repository;
	
	@Autowired
	private CAVotesCong2018Repository caVotesCong2018Repository;
	
	@Autowired
	private CADemographicsRepository caDemographicsRepository;
	
	public List<CaliEntity> getAllPrecincts() {
		return caliRepository.findAll();
	}

	public Optional<?> getPrecinctVoteData(ElectionCategory election, Long geomID) {
		switch(election) {
		case CONGRESSIONAL2016:
			return caVotesCong2016Repository.findById(geomID);
		case PRESIDENTIAL2016:
			return caVotesPres2016Repository.findById(geomID);
		case CONGRESSIONAL2018:
			return caVotesCong2018Repository.findById(geomID);
			default:
				return null;
		}
		
	}
	
	public Optional<?> getPrecinctDemographicData(ElectionCategory election, Long geomID) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			return caDemographicsRepository.findById(geomID);
		case CONGRESSIONAL2018:
			return caDemographicsRepository.findById(geomID);
			default:
				return null;
		}
	}
}
