package com.maxminmajcdg.dto;

import java.util.Optional;

public class StateDataResponse {

	private Optional<?> votes;
	private Optional<?> demographics;
	//private Optional<MeausrmentsEntity> measurements;
	
	public Optional<?> getVotes() {
		return votes;
	}
	public void setVotes(Optional<?> votes) {
		this.votes = votes;
	}
	
	public Optional<?> getDemographics() {
		return demographics;
	}
	public void setDemographics(Optional<?> demographics) {
		this.demographics = demographics;
	}
	
}
