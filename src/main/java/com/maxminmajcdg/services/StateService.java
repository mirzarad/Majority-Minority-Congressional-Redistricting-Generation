package com.maxminmajcdg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.maxminmajcdg.entities.DemographicCategory;
import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.PartyCategory;
import com.maxminmajcdg.entities.VoteEntity;

public abstract class StateService {
	
	public abstract List<?> getDemographics(ElectionCategory election);
	public abstract List<?> getVotes(ElectionCategory election);
	public abstract Optional<?> getPrecinctDemographicData(ElectionCategory election, Long geomID);
	public abstract Optional<?> getPrecinctVoteData(ElectionCategory election, Long geomID);
	public abstract List<?> getAllPrecincts();
	
	public List<?> getDemographicBloc(ElectionCategory election, float threshold) {
		List<?> demographics = getDemographics(election);
		List<DemographicsEntity> demographicBloc = new ArrayList<DemographicsEntity>();

		for (Object demographicObject : demographics) {
			DemographicsEntity demographic = (DemographicsEntity) demographicObject;
			
			DemographicCategory maxDemographic = demographic.getMaxVotingDemographic();
			if (demographic.doesVotingDemographicExceedThreshold(maxDemographic, threshold)) {
				demographicBloc.add(demographic);
			}
		}
		return demographicBloc;
	}
	
	public boolean votesAsBloc(ElectionCategory election, Long geomID, float threshold) {
		Optional<?> votesOptional = getPrecinctVoteData(election, geomID);

		if (votesOptional == null || !votesOptional.isPresent()) {
			return false;
		}
		
 		VoteEntity votes = (VoteEntity) votesOptional.get();
		PartyCategory maxVote = votes.getMaxVotingDemographics();
		
		return votes.doesVotingDemographicExceedThreshold(maxVote, threshold);
	}
}
