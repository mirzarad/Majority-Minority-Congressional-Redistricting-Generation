package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class VotesEntity {
	
	@Column(name="T16PRESD")
	@JsonIgnore
	private int presidentialDemocratic2016;
	
	@Column(name="T16PRESR")
	@JsonIgnore
	private int presidentialRepublican2016;
	
	@Column(name="T16PRESOTH")
	@JsonIgnore
	private int presidentialOther2016;
	
	@Column(name="T16CONGD")
	@JsonIgnore
	private int congressionalDemocratic2016;
	
	@Column(name="T16CONGR")
	@JsonIgnore
	private int congressionalRepublican2016;
	
	//@Column(name="T18CONGD")
	//@JsonIgnore
	//private int congressionalDemocratic2018;
	
	//@Column(name="T18CONGR")
	//@JsonIgnore
	//private int congressionalRepublican2018;
	
	public Map<ElectionCategory, Map<PartyCategory, Integer>> getVotes() {
		Map<ElectionCategory, Map<PartyCategory, Integer>> votes = new HashMap<ElectionCategory, Map<PartyCategory, Integer>>();
		Map<PartyCategory, Integer> presMap2016 = new HashMap<PartyCategory, Integer>();
		Map<PartyCategory, Integer> congMap2016 = new HashMap<PartyCategory, Integer>();
		//Map<PartyCategory, Integer> congMap2018 = new HashMap<PartyCategory, Integer>();

		presMap2016.put(PartyCategory.DEMOCRATIC, presidentialDemocratic2016);
		presMap2016.put(PartyCategory.REPUBLICAN, presidentialRepublican2016);
		presMap2016.put(PartyCategory.OTHER, presidentialOther2016);
		
		congMap2016.put(PartyCategory.DEMOCRATIC, congressionalDemocratic2016);
		congMap2016.put(PartyCategory.REPUBLICAN, congressionalRepublican2016);
		
		//congMap2018.put(PartyCategory.DEMOCRATIC, presidentialDemocratic2016);
		//congMap2018.put(PartyCategory.REPUBLICAN, presidentialRepublican2016);
		
		votes.put(ElectionCategory.PRESIDENTIAL2016, presMap2016);
		votes.put(ElectionCategory.CONGRESSIONAL2016, congMap2016);
		//votes.put(ElectionCategory.CONGRESSIONAL2018, congMap2018);
		return votes;
	}
	
	@Override
	public String toString() {
		return getVotes().toString();
	}
}
