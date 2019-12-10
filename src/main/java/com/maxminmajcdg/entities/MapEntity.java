package com.maxminmajcdg.entities;

import java.util.List;

public class MapEntity<T> {
	private List<?> features;
	
	public String getType() {
		return "FeatureCollection";
	}
	
	public List<?> getFeatures() {
		return features;
	}
	public void setFeatures(List<?> list) {
		this.features = list;
	}
}
