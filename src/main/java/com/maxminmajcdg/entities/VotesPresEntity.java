package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class VotesPresEntity extends VoteEntity {
	@Id
	@GeneratedValue
	@Column(name="geom_ID")
	private Long geomID;
	
	public Long getGeomID() {
		return geomID;
	}
	
	@Column(name="PRESD")
	@JsonIgnore
	private int presidentialDemocratic;
	
	@Column(name="PRESR")
	@JsonIgnore
	private int presidentialRepublican;
	
	@Column(name="PRESG")
	@JsonIgnore
	private int presidentialGreen;
	
	@Column(name="PRESI")
	@JsonIgnore
	private int presidentialIndependent;
	
	@JsonIgnore
	public Map<PartyCategory, Integer> getVotes() {
		Map<PartyCategory, Integer> votes = new HashMap<PartyCategory, Integer>();

		votes.put(PartyCategory.DEMOCRATIC, presidentialDemocratic);
		votes.put(PartyCategory.REPUBLICAN, presidentialRepublican);
		votes.put(PartyCategory.GREEN, presidentialGreen);
		votes.put(PartyCategory.INDEPENDENT, presidentialIndependent);
		
		return votes;
	}
	
}