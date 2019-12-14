package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class VotesCongEntity extends VoteEntity {
	
	@Id
	@GeneratedValue
	@Column(name="geom_ID")
	private Long geomID;
	
	@Override
	public Long getGeomID() {
		return geomID;
	}
	
	@Column(name="CONGD")
	@JsonIgnore
	private Integer congressionalDemocratic;
	
	@Column(name="CONGR")
	@JsonIgnore
	private Integer congressionalRepublican;
	
	@Column(name="CONGG")
	@JsonIgnore
	private Integer congressionalGreen;
	
	@Column(name="CONGI")
	@JsonIgnore
	private Integer congressionalIndependent;
	
	@JsonIgnore
	@Override
	public Map<PartyCategory, Integer> getVotes() {
		Map<PartyCategory, Integer> votes = new HashMap<PartyCategory, Integer>();
		
		votes.put(PartyCategory.DEMOCRATIC, congressionalDemocratic);
		votes.put(PartyCategory.REPUBLICAN, congressionalRepublican);
		votes.put(PartyCategory.GREEN, congressionalGreen);
		votes.put(PartyCategory.INDEPENDENT, congressionalIndependent);

		return votes;
	}

}
