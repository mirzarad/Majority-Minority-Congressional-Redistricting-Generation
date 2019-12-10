package com.maxminmajcdg.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.maxminmajcdg.entities.DemographicCategory;
import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.PartyCategory;
import com.maxminmajcdg.entities.VoteEntity;

public abstract class StateService {
	
	public abstract List<?> getDemographics(ElectionCategory election);
	public abstract List<?> getVotes(ElectionCategory election);
	public abstract List<?> getVotesIn(ElectionCategory election, Set<Long> geomID);
	public abstract Optional<?> getPrecinctDemographicData(ElectionCategory election, Long geomID);
	public abstract Optional<?> getPrecinctVoteData(ElectionCategory election, Long geomID);
	public abstract List<?> getAllPrecincts(ElectionCategory election);
	
	public Map<Long, DemographicsEntity> getDemographicBloc(ElectionCategory election, float threshold) {
		List<?> demographics = getDemographics(election);
		Map<Long, DemographicsEntity> demographicBloc = new HashMap<Long, DemographicsEntity>();
		
		for (Object demographicObject : demographics) {
			DemographicsEntity demographic = (DemographicsEntity) demographicObject;
			
			DemographicCategory maxDemographic = demographic.getMaxVotingDemographic();
			if (demographic.doesVotingDemographicExceedThreshold(maxDemographic, threshold)) {
				demographicBloc.put(demographic.getGeomID(), demographic);
			}
		}
		return demographicBloc;
	}
	
	public Map<Long, VoteEntity> votesAsBloc(ElectionCategory election, Set<Long> geomID, float threshold) {
		List<?> votes = getVotesIn(election, geomID);
		Map<Long, VoteEntity> voteBlocs = new HashMap<Long, VoteEntity>();

		for (Object voteObject : votes) {
			VoteEntity vote = (VoteEntity) voteObject;
			
			PartyCategory maxVote = vote.getMaxVotingDemographics();
			if (vote.doesVotingDemographicExceedThreshold(maxVote, threshold)) {
				voteBlocs.put(vote.getGeomID(), vote);
			}
		}
		
		return voteBlocs;
	}
}
