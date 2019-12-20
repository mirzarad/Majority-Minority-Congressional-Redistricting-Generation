package com.maxminmajcdg.dto;

import java.util.Map;
import java.util.Set;

import com.maxminmajcdg.DemographicCategory;
import com.maxminmajcdg.PartyCategory;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;

public class DistrictResponse {
	private int id;
	private int uniqueID;
	private Set<Integer> newPrecinct;
	private Set<Integer> precincts;
	private Map<PartyCategory, Double> votes;
	private Map<DemographicCategory, Double> demographics;
	
	public DistrictResponse(NeighborDistrictWrapper district, ElectionCategory election) {
		this.id = district.getNodeID();
		this.newPrecinct = district.getNewID();
		this.precincts = district.getPrecincts();
		this.votes = district.getVotes().get(election).getVotes();
		this.demographics = district.getDemographics().get(election).getTotalDemographics();
		this.uniqueID = district.getUniqueID();
	}
	
	public DistrictResponse() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<Integer> getPrecincts() {
		return precincts;
	}
	public void setPrecincts(Set<Integer> precincts) {
		this.precincts = precincts;
	}
	public Map<PartyCategory, Double> getVotes() {
		return votes;
	}
	public void setVotes(Map<PartyCategory, Double> votes) {
		this.votes = votes;
	}
	public Map<DemographicCategory, Double> getDemographics() {
		return demographics;
	}
	public void setDemographics(Map<DemographicCategory, Double> demographics) {
		this.demographics = demographics;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	public Set<Integer> getNewPrecinct() {
		return newPrecinct;
	}

	public void setNewPrecinct(Set<Integer> newPrecinct) {
		this.newPrecinct = newPrecinct;
	}
	
	
}
