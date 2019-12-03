package com.maxminmajcdg.dto;

import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.VoteEntity;

public class DemVotePair {

	private VoteEntity votes;
	private DemographicsEntity demographics;
	//private Optional<MeausrmentsEntity> measurements;
	
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
	
}
