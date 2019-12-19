package com.maxminmajcdg.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.javatuples.Pair;

import com.maxminmajcdg.DemographicCategory;
import com.maxminmajcdg.PartyCategory;
import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.VoteEntity;

public abstract class StateService {
	
	public abstract List<?> getDemographics(ElectionCategory election);
	public abstract List<?> getVotes(ElectionCategory election);
	public abstract List<?> getVotesIn(ElectionCategory election, Set<Long> geomID);
	public abstract Map<Integer, NeighborDistrictWrapper> getNeighbors(ElectionCategory election);
	public abstract Map<Integer, Double> getNeighborPopulations(ElectionCategory election);
	public abstract Optional<?> getPrecinctDemographicData(ElectionCategory election, Long geomID);
	public abstract Optional<?> getPrecinctVoteData(ElectionCategory election, Long geomID);
	public abstract List<?> getAllPrecincts(ElectionCategory election);
	public abstract Double getTotalPopulation(ElectionCategory election);
	
	public Map<Long, Pair<DemographicsEntity, DemographicCategory>> getDemographicBloc(ElectionCategory election, float threshold) {
		List<?> demographics = getDemographics(election);
		Map<Long, Pair<DemographicsEntity, DemographicCategory>> demographicBloc = new HashMap<Long, Pair<DemographicsEntity, DemographicCategory>>();
		
		for (Object demographicObject : demographics) {
			DemographicsEntity demographic = (DemographicsEntity) demographicObject;
			
			DemographicCategory maxDemographic = demographic.getMaxVotingDemographic();
			if (demographic.doesVotingDemographicExceedThreshold(maxDemographic, threshold)) {
				demographicBloc.put(demographic.getGeomID(), Pair.with(demographic, maxDemographic));
			}
		}
		return demographicBloc;
	}
	
	public Map<Long, Pair<VoteEntity, PartyCategory>> votesAsBloc(ElectionCategory election, Set<Long> geomID, float threshold) {
		List<?> votes = getVotesIn(election, geomID);
		Map<Long, Pair<VoteEntity, PartyCategory>> voteBlocs = new HashMap<Long, Pair<VoteEntity, PartyCategory>>();

		for (Object voteObject : votes) {
			VoteEntity vote = (VoteEntity) voteObject;
			
			PartyCategory maxVote = vote.getMaxVotingDemographics();
			if (vote.doesVotingDemographicExceedThreshold(maxVote, threshold)) {
				voteBlocs.put(vote.getGeomID(), Pair.with(vote, maxVote));
			}
		}
		
		return voteBlocs;
	}
}
