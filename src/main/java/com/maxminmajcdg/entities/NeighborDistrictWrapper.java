package com.maxminmajcdg.entities;

import java.util.List;
import java.util.Map;

import com.maxminmajcdg.DemographicCategory;

public interface NeighborDistrictWrapper {

	public List<Integer> getNeighbors();

	public Integer getNodeID();

	public Map<ElectionCategory, VotesWrapper> getVotes();

	public List<Integer> getPrecincts();

	public Double getPopulation(ElectionCategory election);

	public Map<ElectionCategory, DemographicWrapper> getDemographics();
	
	public boolean isThresholdMet(ElectionCategory election, Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage,
			float minDemographicBlocPercentage);

}
