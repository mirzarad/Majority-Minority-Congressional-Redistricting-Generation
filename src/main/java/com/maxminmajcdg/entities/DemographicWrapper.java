package com.maxminmajcdg.entities;

import java.util.Map;

import com.maxminmajcdg.DemographicCategory;

public class DemographicWrapper {
	private Map<DemographicCategory, Double>  totalDemographics;

	public Map<DemographicCategory, Double> getTotalDemographics() {
		return totalDemographics;
	}

	public void setDemographics(Map<DemographicCategory, Double> totalDemographics) {
		this.totalDemographics = totalDemographics;
	}

	public String toString() {
		return getTotalDemographics().toString();
	}
	
}
