package com.maxminmajcdg.dto;

import java.util.Optional;

import com.maxminmajcdg.entities.DemographicsEntity;
import com.maxminmajcdg.entities.VotesEntity;

public class StateDataResponse<P extends VotesEntity, Q extends DemographicsEntity> {

	private Optional<P> votes;
	private Optional<Q> demographics;
	//private Optional<MeausrmentsEntity> measurements;
	
	public Optional<P> getVotes() {
		return votes;
	}
	public void setVotes(Optional<P> votes) {
		this.votes = votes;
	}
	
	public Optional<Q> getDemographics() {
		return demographics;
	}
	public void setDemographics(Optional<Q> demographics) {
		this.demographics = demographics;
	}
	
}
