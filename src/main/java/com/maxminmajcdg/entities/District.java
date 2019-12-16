package com.maxminmajcdg.entities;

import java.util.List;
import java.util.Map;

import com.maxminmajcdg.DemographicCategory;
import com.maxminmajcdg.PartyCategory;

public class District implements NeighborDistrictWrapper{
	private Integer nodeID;
	private Map<DemographicCategory, Double> totalDemographics;
	private Map<ElectionCategory, VotesWrapper>  votes;
	private List<Integer> neighbors;
	private List<Integer> precincts;

	public Map<DemographicCategory, Double> getTotalDemographics() {
		return totalDemographics;
	}

	public void setTotalDemographics(Map<DemographicCategory, Double> totalDemographics) {
		this.totalDemographics = totalDemographics;
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
		return totalDemographics.get(DemographicCategory.TOTAL);
	}

	public void addPrecincts(Integer nodeID) {
		precincts.add(nodeID);
	}

}
