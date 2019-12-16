package com.maxminmajcdg.dto;

import com.maxminmajcdg.States;
import com.maxminmajcdg.entities.ElectionCategory;

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
	private String election;
	private String state;
	
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
	public ElectionCategory getElection() {
		return ElectionCategory.fromValue(election);
	}
	public void setElection(String election) {
		this.election = election;
	}
	public States getState() {
		return States.fromValue(state);
	}
	public void setState(String state) {
		this.state = state;
	}
}
