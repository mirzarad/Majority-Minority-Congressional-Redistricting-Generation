package com.maxminmajcdg.dto;

import java.util.Map;

import com.maxminmajcdg.entities.DemographicCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GraphPartitioningForm {
	private float demographicBlocPercentage;
	private float voteBlocPercentage;
	private Map<DemographicCategory, Boolean> demographics;
	
	public float getDemographicBlocPercentage() {
		return demographicBlocPercentage;
	}
	public void setDemographicBlocPercentage(float demographicBlocPercentage) {
		this.demographicBlocPercentage = demographicBlocPercentage;
	}
	public float getVoteBlocPercentage() {
		return voteBlocPercentage;
	}
	public void setVoteBlocPercentage(float voteBlocPercentage) {
		this.voteBlocPercentage = voteBlocPercentage;
	}
	public Map<DemographicCategory, Boolean> getDemographics() {
		return demographics;
	}
	public void setDemographics(Map<DemographicCategory, Boolean> demographics) {
		this.demographics = demographics;
	}
}
