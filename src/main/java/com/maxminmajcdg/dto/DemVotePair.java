package com.maxminmajcdg.dto;

import com.maxminmajcdg.DemographicCategory;
import com.maxminmajcdg.PartyCategory;
import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.VoteEntity;

public class DemVotePair {

	private VoteEntity votes;
	private DemographicsEntity demographics;
	private PartyCategory maxVote;
	private DemographicCategory maxDemographic;
	
	public VoteEntity getVotes() {
		return votes;
	}
	public void setVotes(VoteEntity votes) {
		this.votes = votes;
	}
	
	public DemographicsEntity getDemographics() {
		return demographics;
	}
	public void setDemographics(DemographicsEntity demographics) {
		this.demographics = demographics;
	}
	public PartyCategory getMaxVote() {
		return maxVote;
	}
	public void setMaxVote(PartyCategory maxVote) {
		this.maxVote = maxVote;
	}
	public DemographicCategory getMaxDemographic() {
		return maxDemographic;
	}
	public void setMaxDemographic(DemographicCategory maxDemographic) {
		this.maxDemographic = maxDemographic;
	}
	
}
