package com.maxminmajcdg.dto;

import java.util.Map;

import com.maxminmajcdg.DemographicCategory;
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
public class GraphPartitioningForm {
	private float minDemographicBlocPercentage;
	private float maxDemographicBlocPercentage;
	private float voteBlocPercentage;
	private Map<DemographicCategory, Boolean> demographics;
	private String election;
	private String state;
	private int numberOfDistricts;
	
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
	public int getNumberOfDistricts() {
		return numberOfDistricts;
	}
	public void setNumberOfDistricts(int numberOfDistricts) {
		this.numberOfDistricts = numberOfDistricts;
	}
	public float getMinDemographicBlocPercentage() {
		return minDemographicBlocPercentage;
	}
	public void setMinDemographicBlocPercentage(float minDemographicBlocPercentage) {
		this.minDemographicBlocPercentage = minDemographicBlocPercentage;
	}
	public float getMaxDemographicBlocPercentage() {
		return maxDemographicBlocPercentage;
	}
	public void setMaxDemographicBlocPercentage(float maxDemographicBlocPercentage) {
		this.maxDemographicBlocPercentage = maxDemographicBlocPercentage;
	}
}
