package com.maxminmajcdg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DemographicBlocForm {
	private float demographicBlocPercentage;
	private float voteBlocPercentage;
	
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
}
