package com.maxminmajcdg.entities;

import java.util.List;

public class MultiPolygonEntity implements GeometryEntity{
	
	private List<List<List<double[]>>> coordinates;

	public List<List<List<double[]>>> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<List<List<double[]>>> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String getType() {
		return "MultiPolygon";
	}
}
