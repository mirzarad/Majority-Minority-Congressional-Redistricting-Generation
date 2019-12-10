package com.maxminmajcdg.entities;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class VoteEntity {
	
	public Map<PartyCategory, Integer> getVotes() {
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
		Map<PartyCategory, Integer> votes = getVotes();
		Map.Entry<PartyCategory, Integer> maxVote = null;
		
		for (Map.Entry<PartyCategory, Integer> vote : votes.entrySet()) {
			if (maxVote == null || vote.getValue().compareTo(maxVote.getValue()) > 0) {
				maxVote = vote;
			}
		}
		return maxVote.getKey();
	}
	
	@JsonIgnore
	public double getVotePercentage(PartyCategory party) {
		Map<PartyCategory, Integer> votes = getVotes();
		int total = 0;
		
		for(Integer i : votes.values()) {
			total += i;
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
