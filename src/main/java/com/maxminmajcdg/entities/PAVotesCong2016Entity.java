package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.maxminmajcdg.PartyCategory;

@Entity
@Table(name="pa_votes_2016")
public class PAVotesCong2016Entity extends VotesCongEntity { 
	
	public Map<ElectionCategory, Map<PartyCategory, Double>> getPresVotes() {
		Map<ElectionCategory, Map<PartyCategory, Double>> election = new HashMap<ElectionCategory, Map<PartyCategory, Double>>();
		election.put(ElectionCategory.CONGRESSIONAL2016, getVotes());
		return election;
	}
}
