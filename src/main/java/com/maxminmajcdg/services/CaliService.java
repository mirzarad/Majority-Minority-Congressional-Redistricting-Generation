package com.maxminmajcdg.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.DemographicCategory;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.NeighborEntity;
import com.maxminmajcdg.repo.CADemographics2016Repository;
import com.maxminmajcdg.repo.CADemographics2018Repository;
import com.maxminmajcdg.repo.CANeighbor2016Repository;
import com.maxminmajcdg.repo.CANeighbor2018Repository;
import com.maxminmajcdg.repo.CAVotesCong2016Repository;
import com.maxminmajcdg.repo.CAVotesCong2018Repository;
import com.maxminmajcdg.repo.CAVotesPres2016Repository;
import com.maxminmajcdg.repo.CaliRepository2016;
import com.maxminmajcdg.repo.CaliRepository2018;

@Service
public class CaliService extends StateService{
	
	@Autowired
	private CaliRepository2016 cali2016Repository;
	
	@Autowired
	private CaliRepository2018 cali2018Repository;
	
	@Autowired
	private CANeighbor2016Repository caNeighbor2016Repository;
	
	@Autowired
	private CANeighbor2018Repository caNeighbor2018Repository;
	
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
	public List<?> getAllPrecincts(ElectionCategory election) {
		switch(election) {
		case PRESIDENTIAL2016:
		case CONGRESSIONAL2016:
			return cali2016Repository.findAll();
		case CONGRESSIONAL2018:
			return cali2018Repository.findAll();
			default:
				return null;	
		}
	}

	@Override
	public Map<Integer, NeighborDistrictWrapper> getNeighbors(ElectionCategory election) {
		switch(election) {
		case PRESIDENTIAL2016:
		case CONGRESSIONAL2016:
			return caNeighbor2016Repository.findAllDistinct().stream()
					.collect(Collectors.toMap(NeighborEntity::getNodeID, Function.identity()));
		case CONGRESSIONAL2018:
			return caNeighbor2018Repository.findAllDistinct().stream()
					.collect(Collectors.toMap(NeighborEntity::getNodeID, Function.identity()));
			default:
				return null;		
		}
	}
	
	@Override
	public Map<Integer, Double> getNeighborPopulations(ElectionCategory election) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			return (Map<Integer, Double>) caDemographics2016Repository.findAll().stream().collect(Collectors.toMap(e -> e.getGeomID().intValue(), e -> e.getTotalDemographics().get(DemographicCategory.TOTAL)));
		case CONGRESSIONAL2018:
			return (Map<Integer, Double>) caDemographics2018Repository.findAll().stream().collect(Collectors.toMap(e -> e.getGeomID().intValue(), e -> e.getTotalDemographics().get(DemographicCategory.TOTAL)));
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

	@Override
	public Double getTotalPopulation(ElectionCategory election) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			return caDemographics2016Repository.getTotalPopulation();
		case CONGRESSIONAL2018:
			return caDemographics2018Repository.getTotalPopulation();
			default:
				return null;
		}
	}
}
