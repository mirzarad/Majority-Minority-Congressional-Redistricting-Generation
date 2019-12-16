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
	private Double congressionalDemocratic;
	
	@Column(name="CONGR")
	@JsonIgnore
	private Double congressionalRepublican;
	
	@Column(name="CONGG")
	@JsonIgnore
	private Double congressionalGreen;
	
	@Column(name="CONGI")
	@JsonIgnore
	private Double congressionalIndependent;
	
	@JsonIgnore
	@Override
	public Map<PartyCategory, Double> getVotes() {
		Map<PartyCategory, Double> votes = new HashMap<PartyCategory, Double>();
		
		votes.put(PartyCategory.DEMOCRATIC, congressionalDemocratic);
		votes.put(PartyCategory.REPUBLICAN, congressionalRepublican);
		votes.put(PartyCategory.GREEN, congressionalGreen);
		votes.put(PartyCategory.INDEPENDENT, congressionalIndependent);

		return votes;
	}

}
