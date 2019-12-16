package com.maxminmajcdg.entities;

import java.util.Map;

import com.maxminmajcdg.PartyCategory;

public class VotesWrapper {
	private Map<PartyCategory, Double>  votes;

	public Map<PartyCategory, Double> getVotes() {
		return votes;
	}

	public void setVotes(Map<PartyCategory, Double> votes) {
		this.votes = votes;
	}
	
	public String toString() {
		return getVotes().toString();
	}
}
