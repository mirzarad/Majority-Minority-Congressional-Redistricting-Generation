package com.maxminmajcdg.entities;

import java.util.List;

public class PolygonEntity implements GeometryEntity{
	
	private List<List<double[]>> coordinates;

	public List<List<double[]>> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<List<double[]>> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String getType() {
		return "Polygon";
	}
}
