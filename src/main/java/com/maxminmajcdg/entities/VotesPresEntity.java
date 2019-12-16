package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxminmajcdg.PartyCategory;

@MappedSuperclass
public abstract class VotesPresEntity extends VoteEntity {
	@Id
	@GeneratedValue
	@Column(name="geom_ID")
	private Long geomID;
	
	@Override
	public Long getGeomID() {
		return geomID;
	}
	
	@Column(name="PRESD")
	@JsonIgnore
	private Double presidentialDemocratic;
	
	@Column(name="PRESR")
	@JsonIgnore
	private Double presidentialRepublican;
	
	@Column(name="PRESG")
	@JsonIgnore
	private Double presidentialGreen;
	
	@Column(name="PRESI")
	@JsonIgnore
	private Double presidentialIndependent;
	
	@JsonIgnore
	public Map<PartyCategory, Double> getVotes() {
		Map<PartyCategory, Double> votes = new HashMap<PartyCategory, Double>();

		votes.put(PartyCategory.DEMOCRATIC, presidentialDemocratic);
		votes.put(PartyCategory.REPUBLICAN, presidentialRepublican);
		votes.put(PartyCategory.GREEN, presidentialGreen);
		votes.put(PartyCategory.INDEPENDENT, presidentialIndependent);
		
		return votes;
	}
	
}
