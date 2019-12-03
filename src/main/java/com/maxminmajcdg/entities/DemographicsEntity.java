package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class DemographicsEntity {
	
	@Id
	@GeneratedValue
	@Column(name="geom_ID")
	private Long geomID;
	
	@Column(name="TAPERSONS")
	@JsonIgnore
	private double totalPopulation;
	
	@Column(name="TAHISPANIC")
	@JsonIgnore
	private double totalHispanicPopulation;
	
	@Column(name="TAWHITEALN")
	@JsonIgnore
	private double totalWhitePopulation;
	
	@Column(name="TABLACKALN")
	@JsonIgnore
	private double totalBlackPopulation;
	
	@Column(name="TAAMINDALN")
	@JsonIgnore
	private double totalNativePopulation;
	
	@Column(name="TAOTHERALN")
	@JsonIgnore
	private double totalOtherPopulation;
	
	@Column(name="TANHPOALN")
	@JsonIgnore
	private double totalNativeHawiianPopulation;
	
	@Column(name="TAASIANALN")
	@JsonIgnore
	private double totalAsianPopulation;
	
	@Column(name="VAPERSONS")
	@JsonIgnore
	private double votingPopulation;
	
	@Column(name="VAHISPANIC")
	@JsonIgnore
	private double votingHispanicPopulation;
	
	@Column(name="VAWHITEALN")
	@JsonIgnore
	private double votingWhitePopulation;
	
	@Column(name="VABLACKALN")
	@JsonIgnore
	private double votingBlackPopulation;
	
	@Column(name="VAAIANALN")
	@JsonIgnore
	private double votingNativePopulation;
	
	@Column(name="VAOTHERALN")
	@JsonIgnore
	private double votingOtherPopulation;
	
	@Column(name="VANHPOALN")
	@JsonIgnore
	private double votingNativeHawiianPopulation;
	
	@Column(name="VAASIANALN")
	@JsonIgnore
	private double votingAsianPopulation;
	
	public Map<DemographicCategory, Double> getTotalDemographics() {
		Map<DemographicCategory, Double> demographics = new HashMap<DemographicCategory, Double>();
		demographics.put(DemographicCategory.AFRICAN_AMERICAN, totalBlackPopulation);
		demographics.put(DemographicCategory.ASIAN, totalAsianPopulation);
		demographics.put(DemographicCategory.HISPANIC, totalHispanicPopulation);
		demographics.put(DemographicCategory.NATIVE_AMERICAN, totalNativePopulation);
		demographics.put(DemographicCategory.NATIVE_HAWAIIAN, totalNativeHawiianPopulation);
		demographics.put(DemographicCategory.WHITE, totalWhitePopulation);
		demographics.put(DemographicCategory.TOTAL, totalPopulation);
		demographics.put(DemographicCategory.OTHER, totalOtherPopulation);
		return demographics;
	}
	
	public Map<DemographicCategory, Double> getVotingDemographics() {
		Map<DemographicCategory, Double> demographics = new HashMap<DemographicCategory, Double>();
		demographics.put(DemographicCategory.AFRICAN_AMERICAN, votingBlackPopulation);
		demographics.put(DemographicCategory.ASIAN, votingAsianPopulation);
		demographics.put(DemographicCategory.HISPANIC, votingHispanicPopulation);
		demographics.put(DemographicCategory.NATIVE_AMERICAN, votingNativePopulation);
		demographics.put(DemographicCategory.NATIVE_HAWAIIAN, votingNativeHawiianPopulation);
		demographics.put(DemographicCategory.WHITE, votingWhitePopulation);
		demographics.put(DemographicCategory.TOTAL, votingPopulation);
		demographics.put(DemographicCategory.OTHER, votingOtherPopulation);
		return demographics;
	}
	
	@JsonIgnore
	public DemographicCategory getMaxVotingDemographic() {
		Map<DemographicCategory, Double> demographics = getTotalDemographics();
		Map.Entry<DemographicCategory, Double> maxDemographic = null;
		
		for (Map.Entry<DemographicCategory, Double> demographic : demographics.entrySet()) {
			if (demographic.getKey() == DemographicCategory.TOTAL) {
				continue;
			}
			if (maxDemographic == null || demographic.getValue().compareTo(maxDemographic.getValue()) > 0) {
				maxDemographic = demographic;
			}
		}
		return maxDemographic.getKey();
	}
	
	@JsonIgnore
	public double getVotingDemographicPercentage(DemographicCategory demographic) {
		return getTotalDemographics().get(demographic)/totalPopulation * 100;
	}
	
	@JsonIgnore
	public boolean doesVotingDemographicExceedThreshold(DemographicCategory demographic, float threshold) {
		return getVotingDemographicPercentage(demographic) >= threshold;
	}
	
	public Long getGeomID() {
		return geomID;
	}
}
