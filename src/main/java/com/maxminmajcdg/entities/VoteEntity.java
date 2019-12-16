package com.maxminmajcdg.entities;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxminmajcdg.PartyCategory;

public class VoteEntity extends VotesWrapper{
	
	public Map<PartyCategory, Double> getVotes() {
		return null;
	}
	
	public Long getGeomID() {
		return null;
	}
	
	public String toString() {
		return getVotes().toString();
	}
	
	@JsonIgnore
	public PartyCategory getMaxVotingDemographics() {
		Map<PartyCategory, Double> votes = getVotes();
		Map.Entry<PartyCategory, Double> maxVote = null;
		
		for (Map.Entry<PartyCategory, Double> vote : votes.entrySet()) {
			if (vote.getValue() != null && (maxVote == null || vote.getValue().compareTo(maxVote.getValue()) > 0)) {
				maxVote = vote;
			}
		}
		return maxVote.getKey();
	}
	
	@JsonIgnore
	public double getVotePercentage(PartyCategory party) {
		Map<PartyCategory, Double> votes = getVotes();
		int total = 0;
		
		for(Double i : votes.values()) {
			if (i != null) {
				total += i;
			}
		}
		
		
		if(total == 0) {
			return 0;
		}

		return ((double) votes.get(party))/total * 100;
	}
	
	@JsonIgnore
	public boolean doesVotingDemographicExceedThreshold(PartyCategory party, float threshold) {
		return getVotePercentage(party) >= threshold;
	}
}
