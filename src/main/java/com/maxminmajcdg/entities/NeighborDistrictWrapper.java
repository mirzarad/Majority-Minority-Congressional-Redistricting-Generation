package com.maxminmajcdg.entities;

import java.util.Map;
import java.util.Set;

import com.maxminmajcdg.DemographicCategory; 

public interface NeighborDistrictWrapper {

	public Set<Integer> getNeighbors();

	public Integer getNodeID();

	public Map<ElectionCategory, VotesWrapper> getVotes();

	public Set<Integer> getPrecincts();

	public Double getPopulation(ElectionCategory election);

	public Map<ElectionCategory, DemographicWrapper> getDemographics();
	
	public double getInternalEdges();
	
	public double getExternalEdges();
	
	public boolean isThresholdMet(ElectionCategory election, Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage,
			float minDemographicBlocPercentage);
	
	public boolean isPhase1ThresholdMet(ElectionCategory election, Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage,
			float minDemographicBlocPercentage);

	public Integer getUniqueID();
	
	public void setUniqueID(Integer uniqueID);

	public Set<Integer> getNewID();
	
	public void setNewID(Set<Integer> newID);
}
