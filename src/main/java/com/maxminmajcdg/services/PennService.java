package com.maxminmajcdg.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxminmajcdg.DemographicCategory;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.PennEntity;
import com.maxminmajcdg.repo.PADemographics2016Repository;
import com.maxminmajcdg.repo.PADemographics2018Repository;
import com.maxminmajcdg.repo.PAVotesCong2016Repository;
import com.maxminmajcdg.repo.PAVotesCong2018Repository;
import com.maxminmajcdg.repo.PAVotesPres2016Repository;
import com.maxminmajcdg.repo.PennNeighborRepository;
import com.maxminmajcdg.repo.PennRepository;

@Service
@Transactional(readOnly=true)
public class PennService extends StateService{
	
	@Autowired
	private PennRepository pennRepository;
	
	@Autowired
	private PennNeighborRepository pennNeighborRepository;
	
	@Autowired
	private PAVotesCong2016Repository paVotesCong2016Repository;
	
	@Autowired
	private PAVotesPres2016Repository paVotesPres2016Repository;
	
	@Autowired
	private PAVotesCong2018Repository paVotesCong2018Repository;
	
	@Autowired
	private PADemographics2016Repository paDemographics2016Repository;
	
	@Autowired
	private PADemographics2018Repository paDemographics2018Repository;
	
	@Override
	public List<PennEntity> getAllPrecincts(ElectionCategory election) {
		switch(election) {
		case PRESIDENTIAL2016:
		case CONGRESSIONAL2016:
			return pennRepository.findAll();
		case CONGRESSIONAL2018:
			return pennRepository.findAll();
			default:
				return null;		
		}
	}
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public Map<Integer, NeighborDistrictWrapper> getNeighbors(ElectionCategory election) {
		List<NeighborDistrictWrapper> r = entityManager.createQuery("SELECT DISTINCT p FROM pa_neighbors_graph p", NeighborDistrictWrapper.class).getResultList();
		Map<Integer, NeighborDistrictWrapper> result = r.stream()
				.collect(Collectors.toMap(NeighborDistrictWrapper::getNodeID, Function.identity()));
		r.forEach(e -> entityManager.persist(e));
		entityManager.close();
		return result;
	}
	
	@Override
	public Map<Integer, Double> getNeighborPopulations(ElectionCategory election) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			return  (Map<Integer, Double>) paDemographics2016Repository.findAll().stream().collect(Collectors.toMap(e -> e.getGeomID().intValue(), e -> e.getTotalDemographics().get(DemographicCategory.TOTAL)));
		case CONGRESSIONAL2018:
			return (Map<Integer, Double>) paDemographics2018Repository.findAll().stream().collect(Collectors.toMap(e -> e.getGeomID().intValue(), e -> e.getTotalDemographics().get(DemographicCategory.TOTAL)));
			default:
				return null;
		}
	}
	
	@Override
	public Optional<?> getPrecinctVoteData(ElectionCategory election, Long geomID) {
		switch(election) {
		case CONGRESSIONAL2016:
			return paVotesCong2016Repository.findById(geomID);
		case PRESIDENTIAL2016:
			return paVotesPres2016Repository.findById(geomID);
		case CONGRESSIONAL2018:
			return paVotesCong2018Repository.findById(geomID);
			default:
				return null;
		}
		
	}
	
	@Override
	public Optional<?> getPrecinctDemographicData(ElectionCategory election, Long geomID) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			return paDemographics2016Repository.findById(geomID);
		case CONGRESSIONAL2018:
			return paDemographics2018Repository.findById(geomID);
			default:
				return null;
		}
	}
	
	@Override
	public List<?> getDemographics(ElectionCategory election) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			return paDemographics2016Repository.findAll();
		case CONGRESSIONAL2018:
			return paDemographics2018Repository.findAll();
			default:
				return null;
		}
	}
	
	@Override
	public List<?> getVotes(ElectionCategory election) {
		switch(election) {
		case CONGRESSIONAL2016:
			return paVotesCong2016Repository.findAll();
		case PRESIDENTIAL2016:
			return paVotesPres2016Repository.findAll();
		case CONGRESSIONAL2018:
			return paVotesCong2018Repository.findAll();
			default:
				return null;
		}
	}

	@Override
	public List<?> getVotesIn(ElectionCategory election, Set<Long> geomID) {
		switch(election) {
		case CONGRESSIONAL2016:
			return paVotesCong2016Repository.findAllById(geomID);
		case PRESIDENTIAL2016:
			return paVotesPres2016Repository.findAllById(geomID);
		case CONGRESSIONAL2018:
			return paVotesCong2018Repository.findAllById(geomID);
			default:
				return null;
		}
	}

	@Override
	public Double getTotalPopulation(ElectionCategory election) {
		switch(election) {
		case CONGRESSIONAL2016:
		case PRESIDENTIAL2016:
			return paDemographics2016Repository.getTotalPopulation();
		case CONGRESSIONAL2018:
			return paDemographics2018Repository.getTotalPopulation();
			default:
				return null;
		}
	}


	
}
