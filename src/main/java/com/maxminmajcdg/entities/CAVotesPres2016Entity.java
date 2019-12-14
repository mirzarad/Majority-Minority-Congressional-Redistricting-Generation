package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="cali_election16")
public class CAVotesPres2016Entity extends VotesPresEntity { 
	
	public Map<ElectionCategory, Map<PartyCategory, Integer>> getPresVotes() {
		Map<ElectionCategory, Map<PartyCategory, Integer>> election = new HashMap<ElectionCategory, Map<PartyCategory, Integer>>();
		election.put(ElectionCategory.PRESIDENTIAL2016, getVotes());
		return election;
	}
}
