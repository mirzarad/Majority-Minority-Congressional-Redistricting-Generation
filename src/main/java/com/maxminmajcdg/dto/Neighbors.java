package com.maxminmajcdg.dto;

import java.util.List;

import com.maxminmajcdg.entities.NeighborDistrictWrapper;

public class Neighbors {
	private List<NeighborDistrictWrapper> neighbors;

	public List<NeighborDistrictWrapper> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<NeighborDistrictWrapper> neighbors) {
		this.neighbors = neighbors;
	}
}
