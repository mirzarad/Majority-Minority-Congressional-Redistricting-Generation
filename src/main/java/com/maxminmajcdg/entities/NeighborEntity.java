package com.maxminmajcdg.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	public Integer getNodeID() {
		return nodeID;
	}
	
	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}
	
	@Transient
	private List<Integer> precincts = new ArrayList<Integer>();
	
	@Transient
	public List<Integer> getPrecincts() {
		List<Integer> allPrecincts = new ArrayList<Integer>();
		allPrecincts.add(nodeID);
		allPrecincts.addAll(precincts);
		return allPrecincts;
	}
	
	@JsonIgnore
	@Transient
	public void addPrecincts(Integer precinct) {
		precincts.add(precinct);
	}
	
	public abstract List<Integer> getNeighbors();
	public abstract Map<ElectionCategory, VotesWrapper> getVotes();
	public abstract Map<ElectionCategory, DemographicWrapper> getDemographics();
	
	public Double getPopulation(ElectionCategory election) {
		return getDemographics().get(election).getTotalDemographics().get(DemographicCategory.TOTAL);
	}

	@JsonIgnore
	public boolean isThresholdMet(ElectionCategory election, Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage,
			float minDemographicBlocPercentage) {
		Map<DemographicCategory, Double> demo = getDemographics().get(election).getTotalDemographics();
		
		double sum = 0, total = demo.get(DemographicCategory.TOTAL);
		for (DemographicCategory d : demographics.keySet()) {
			if (demographics.get(d)) {
				sum += demo.get(d);
			}
		}
		
		double percent = sum/total * 100;
		return percent >= minDemographicBlocPercentage && percent <= maxDemographicBlocPercentage;
	}
}
