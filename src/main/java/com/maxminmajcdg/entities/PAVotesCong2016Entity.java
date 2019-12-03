package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="pa_votes_2016")
public class PAVotesCong2016Entity extends VotesCongEntity { 
	
	public Map<ElectionCategory, Map<PartyCategory, Integer>> getPresVotes() {
		Map<ElectionCategory, Map<PartyCategory, Integer>> election = new HashMap<ElectionCategory, Map<PartyCategory, Integer>>();
		election.put(ElectionCategory.CONGRESSIONAL2016, getVotes());
		return election;
	}
}
