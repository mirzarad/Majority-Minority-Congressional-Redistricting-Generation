package com.maxminmajcdg.entities;

import java.util.List;
import java.util.Map;

public interface NeighborDistrictWrapper {

	public List<Integer> getNeighbors();

	public Integer getNodeID();

	public Map<ElectionCategory, VotesWrapper> getVotes();

	public List<Integer> getPrecincts();

	public Double getPopulation(ElectionCategory election);

}
