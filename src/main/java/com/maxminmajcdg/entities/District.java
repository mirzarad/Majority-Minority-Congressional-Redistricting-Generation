package com.maxminmajcdg.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.maxminmajcdg.DemographicCategory;

public class District implements NeighborDistrictWrapper{
	private Integer nodeID;
	private Map<ElectionCategory, DemographicWrapper> demographics;
	private Map<ElectionCategory, VotesWrapper>  votes;
	private List<Integer> neighbors;
	private List<Integer> precincts = new ArrayList<Integer>();
	
	public Map<ElectionCategory, DemographicWrapper> getDemographics() {
		return demographics;
	}

	public void setTotalDemographics(Map<ElectionCategory, DemographicWrapper> demographics) {
		this.demographics = demographics;
	}

	public Map<ElectionCategory, VotesWrapper> getVotes() {
		return votes;
	}

	public void setTotalVotes(Map<ElectionCategory, VotesWrapper> votes) {
		this.votes = votes;
	}

	public List<Integer> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<Integer> neighbors) {
		this.neighbors = neighbors;
	}

	@Override
	public Integer getNodeID() {
		return nodeID;
	}
	
	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}

	@Override
	public List<Integer> getPrecincts() {
		return precincts;
	}

	@Override
	public Double getPopulation(ElectionCategory election) {
		return demographics.get(election).getTotalDemographics().get(DemographicCategory.TOTAL);
	}

	public void addPrecincts(Integer nodeID) {
		precincts.add(nodeID);
	}

	@Override
	public boolean isThresholdMet(ElectionCategory election, Map<DemographicCategory, Boolean> demographics,
			float maxDemographicBlocPercentage, float minDemographicBlocPercentage)  {
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

	public String toString() {
		return "[PrecinctID: " + getNodeID() +
				", Neighbors: " + getNeighbors().toString() + 
				", Votes: " + getVotes().toString() + 
				", Demographics: " + getDemographics().toString() +
				"]";
	}

}
