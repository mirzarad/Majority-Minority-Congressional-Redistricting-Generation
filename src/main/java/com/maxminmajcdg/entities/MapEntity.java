package com.maxminmajcdg.entities;

import java.util.List;

public class MapEntity<T> {
	private List<T> features;
	
	public String getType() {
		return "FeatureCollection";
	}
	
	public List<T> getFeatures() {
		return features;
	}
	public void setFeatures(List<T> features) {
		this.features = features;
	}
}
