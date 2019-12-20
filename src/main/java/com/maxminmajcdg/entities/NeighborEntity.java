package com.maxminmajcdg.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxminmajcdg.DemographicCategory;

@MappedSuperclass
public abstract class NeighborEntity implements NeighborDistrictWrapper{
	@Id
	@GeneratedValue
	@Column(name="src_ID")
	private Integer nodeID;
	
	@JsonIgnore
	@Transient
	private boolean isPhase1Calculated;
	
	@JsonIgnore
	@Transient
	private boolean isPhase1ThresholdMet;
	
	@JsonIgnore
	@Transient
	private int uniqueID;
	
	@JsonIgnore
	@Transient
	private Set<Integer> newID;
	
	public Integer getNodeID() {
		return nodeID;
	}
	
	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}
	
	@Transient
	private List<Integer> precincts = new ArrayList<Integer>();
	
	@Transient
	public Set<Integer> getPrecincts() {
		List<Integer> allPrecincts = new ArrayList<Integer>();
		allPrecincts.add(nodeID);
		allPrecincts.addAll(precincts);
		return new HashSet<Integer>(allPrecincts);
	}
	
	@JsonIgnore
	@Transient
	public void addPrecincts(Integer precinct) {
		precincts.add(precinct);
	}
	
	@Override
	public void setNewID(Set<Integer> newID) {
		this.newID = newID;
	}
	
	@JsonIgnore
	@Transient
	@Override
	public Set<Integer> getNewID() {
		return new HashSet<Integer>(nodeID);
	}
	
	public abstract Set<Integer> getNeighbors();
	public abstract Map<ElectionCategory, VotesWrapper> getVotes();
	public abstract Map<ElectionCategory, DemographicWrapper> getDemographics();
	
	public Double getPopulation(ElectionCategory election) {
		return getDemographics().get(election).getTotalDemographics().get(DemographicCategory.TOTAL);
	}

	@JsonIgnore
	public boolean isThresholdMet(ElectionCategory election, Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage,
			float minDemographicBlocPercentage) {
		Map<DemographicCategory, Double> demo = getDemographics().get(election).getTotalDemographics();
		
		double total = demo.get(DemographicCategory.TOTAL);
		double sum = demo.entrySet().stream().filter(d -> demographics.keySet().contains(d.getKey()) && demographics.get(d.getKey())).mapToDouble(e->(e==null)? 0 : e.getValue()).sum();
		
		double percent = sum/total * 100;
		return percent >= minDemographicBlocPercentage && percent <= maxDemographicBlocPercentage;
	}
	
	@JsonIgnore
	@Override
	public boolean isPhase1ThresholdMet(ElectionCategory election, Map<DemographicCategory, Boolean> demographics,
			float maxDemographicBlocPercentage, float minDemographicBlocPercentage) {
		if (isPhase1Calculated) {
			return isPhase1ThresholdMet;
		}
		isPhase1ThresholdMet = isThresholdMet(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage);
		isPhase1Calculated = true;
		return isPhase1ThresholdMet;
	}
	
	@JsonIgnore
	@Override
	public Integer getUniqueID() {
		return -1;
	}
	
	public void setUniqueID(Integer uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	public String toString() {
		return "[PrecinctID: " + getNodeID() +
				", Precincts: " + getPrecincts().toString() + 
				", Neighbors: " + getNeighbors().toString() + 
				", Votes: " + getVotes().toString() + 
				", Demographics: " + getDemographics().toString() +
				", Internal Edges: " + getInternalEdges() +
				", External Edges: " + getExternalEdges() +
				"]";
	}
}
