package com.maxminmajcdg.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.entities.CaliEntity;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.repo.CADemographics2016Repository;
import com.maxminmajcdg.repo.CADemographics2018Repository;
import com.maxminmajcdg.repo.CAVotesCong2016Repository;
import com.maxminmajcdg.repo.CAVotesCong2018Repository;
import com.maxminmajcdg.repo.CAVotesPres2016Repository;
import com.maxminmajcdg.repo.CaliRepository;

@Service
public class CaliService extends StateService{
	
	@Autowired
	private CaliRepository caliRepository;
	
	@Autowired
	private CAVotesCong2016Repository caVotesCong2016Repository;
	
	@Autowired
	private CAVotesPres2016Repository caVotesPres2016Repository;
	
	@Autowired
	private CAVotesCong2018Repository caVotesCong2018Repository;
	
	@Autowired
	private CADemographics2016Repository caDemographics2016Repository;
	
	@Autowired
	private CADemographics2018Repository caDemographics2018Repository;
	
	@Override
	public List<CaliEntity> getAllPrecincts(ElectionCategory election) {
		switch(election) {
		case PRESIDENTIAL2016:
		case CONGRESSIONAL2016:
			return caliRepository.findAll();
		case CONGRESSIONAL2018:
			return caliRepository.findAll();
			default:
				return null;
			
		}
	}

	@Override
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
	
	@Override
	public Optional<?> getPrecinctDemographicData(ElectionCategory election, Long geomID) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			return caDemographics2016Repository.findById(geomID);
		case CONGRESSIONAL2018:
			return caDemographics2018Repository.findById(geomID);
			default:
				return null;
		}
	}

	@Override
	public List<?> getDemographics(ElectionCategory election) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			System.out.println(caDemographics2016Repository.findAll().get(0));
			return caDemographics2016Repository.findAll();
		case CONGRESSIONAL2018:
			return caDemographics2018Repository.findAll();
			default:
				return null;
		}
	}

	@Override
	public List<?> getVotes(ElectionCategory election) {
		switch(election) {
		case CONGRESSIONAL2016:
			return caVotesCong2016Repository.findAll();
		case PRESIDENTIAL2016:
			return caVotesPres2016Repository.findAll();
		case CONGRESSIONAL2018:
			return caVotesCong2018Repository.findAll();
			default:
				return null;
		}
	}

	@Override
	public List<?> getVotesIn(ElectionCategory election, Set<Long> geomID) {
		switch(election) {
		case CONGRESSIONAL2016:
			return caVotesCong2016Repository.findAllById(geomID);
		case PRESIDENTIAL2016:
			return caVotesPres2016Repository.findAllById(geomID);
		case CONGRESSIONAL2018:
			return caVotesCong2018Repository.findAllById(geomID);
			default:
				return null;
		}
	}
}
