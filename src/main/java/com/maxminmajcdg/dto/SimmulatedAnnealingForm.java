package com.maxminmajcdg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimmulatedAnnealingForm {
	private float polsbyPopper;
	private float graphCompactness;
	private float schwartzberg;
	private float populationDifference;
	private float efficiencyGap;
	private float lopsidedMargins;
	private float meanMedianDifference;
	
	public float getPolsbyPopper() {
		return polsbyPopper;
	}
	public void setPolsbyPopper(float polsbyPopper) {
		this.polsbyPopper = polsbyPopper;
	}
	public float getGraphCompactness() {
		return graphCompactness;
	}
	public void setGraphCompactness(float graphCompactness) {
		this.graphCompactness = graphCompactness;
	}
	public float getSchwartzberg() {
		return schwartzberg;
	}
	public void setSchwartzberg(float schwartzberg) {
		this.schwartzberg = schwartzberg;
	}
	public float getPopulationDifference() {
		return populationDifference;
	}
	public void setPopulationDifference(float populationDifference) {
		this.populationDifference = populationDifference;
	}
	public float getEfficiencyGap() {
		return efficiencyGap;
	}
	public void setEfficiencyGap(float efficiencyGap) {
		this.efficiencyGap = efficiencyGap;
	}
	public float getLopsidedMargins() {
		return lopsidedMargins;
	}
	public void setLopsidedMargins(float lopsidedMargins) {
		this.lopsidedMargins = lopsidedMargins;
	}
	public float getMeanMedianDifference() {
		return meanMedianDifference;
	}
	public void setMeanMedianDifference(float meanMedianDifference) {
		this.meanMedianDifference = meanMedianDifference;
	}
	
}
