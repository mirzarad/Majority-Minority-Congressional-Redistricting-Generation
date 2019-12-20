package com.maxminmajcdg.entities;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.maxminmajcdg.DemographicCategory;

public class District extends NeighborEntity{
	private Integer nodeID;
	private Integer uniqueID;
	private Set<Integer> newID;
	private Map<ElectionCategory, DemographicWrapper> demographics;
	private Map<ElectionCategory, VotesWrapper>  votes;
	private Set<Integer> neighbors;
	private Set<Integer> precincts = new HashSet<Integer>();
	private double internalEdges;
	private double externalEdges;
	
	public District(District district) {
		this.nodeID = district.getNodeID();
		this.newID = district.getNewID();
		this.demographics = district.getDemographics();
		this.votes = district.getVotes();
		this.neighbors = district.getNeighbors();
		this.precincts = district.getPrecincts();
		this.internalEdges = district.getInternalEdges();
		this.externalEdges = district.getExternalEdges();
		this.uniqueID = district.uniqueID;
	}

	public District() {}

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

	public Set<Integer> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Set<Integer> neighbors) {
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
	public Set<Integer> getPrecincts() {
		return precincts;
	}

	@Override
	public Double getPopulation(ElectionCategory election) {
		return demographics.get(election).getTotalDemographics().get(DemographicCategory.TOTAL);
	}

	public void addPrecincts(Integer nodeID) {
		precincts.add(nodeID);
	}

	public void addPrecincts(Set<Integer> nodeIDs) {
		precincts.addAll(nodeIDs);
	}

	public void setInternalEdges(double internalEdges) {
		this.internalEdges = internalEdges;
	}
	
	@Override
	public double getInternalEdges() {
		return internalEdges;
	}
	
	public void setExternalEdges(double externalEdges) {
		this.externalEdges = externalEdges;
	}

	@Override
	public double getExternalEdges() {
		return externalEdges;
	}
	
	public Integer getUniqueID() {
		return uniqueID;
	}
	
	@Override
	public void setUniqueID(Integer uniqueID) {
		this.uniqueID = uniqueID;
	}

	@Override
	public Set<Integer> getNewID() { 
		return newID;
	}

	@Override
	public void setNewID(Set<Integer> newID) {
		this.newID = newID;
	}

}
