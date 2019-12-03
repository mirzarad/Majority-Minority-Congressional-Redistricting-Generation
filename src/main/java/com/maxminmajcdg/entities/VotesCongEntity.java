package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class VotesCongEntity implements VoteEntity {
	
	@Id
	@GeneratedValue
	@Column(name="geom_ID")
	private Long geomID;
	
	public Long getGeomID() {
		return geomID;
	}
	
	@Column(name="CONGD")
	@JsonIgnore
	private int congressionalDemocratic;
	
	@Column(name="CONGR")
	@JsonIgnore
	private int congressionalRepublican;
	
	@Column(name="CONGG")
	@JsonIgnore
	private int congressionalGreen;
	
	@Column(name="CONGI")
	@JsonIgnore
	private int congressionalIndependent;
	
	@JsonIgnore
	public Map<PartyCategory, Integer> getVotes() {
		Map<PartyCategory, Integer> votes = new HashMap<PartyCategory, Integer>();
		
		votes.put(PartyCategory.DEMOCRATIC, congressionalDemocratic);
		votes.put(PartyCategory.REPUBLICAN, congressionalRepublican);
		votes.put(PartyCategory.GREEN, congressionalGreen);
		votes.put(PartyCategory.INDEPENDENT, congressionalIndependent);

		return votes;
	}
	
	@Override
	public String toString() {
		return getVotes().toString();
	}
}
