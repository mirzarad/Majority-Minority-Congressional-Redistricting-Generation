package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxminmajcdg.DemographicCategory;

@MappedSuperclass
public abstract class DemographicsEntity extends DemographicWrapper{
	
	@Id
	@GeneratedValue
	@Column(name="geom_ID")
	private Long geomID;
	
	@Column(name="TAPERSONS")
	@JsonIgnore
	private Double totalPopulation;
	
	@Column(name="TAHISPANIC")
	@JsonIgnore
	private Double totalHispanicPopulation;
	
	@Column(name="TAWHITEALN")
	@JsonIgnore
	private Double totalWhitePopulation;
	
	@Column(name="TABLACKALN")
	@JsonIgnore
	private Double totalBlackPopulation;
	
	@Column(name="TAAMINDALN")
	@JsonIgnore
	private Double totalNativePopulation;
	
	@Column(name="TAOTHERALN")
	@JsonIgnore
	private Double totalOtherPopulation;
	
	@Column(name="TANHPOALN")
	@JsonIgnore
	private Double totalNativeHawiianPopulation;
	
	@Column(name="TAASIANALN")
	@JsonIgnore
	private Double totalAsianPopulation;
	
	@Column(name="VAPERSONS")
	@JsonIgnore
	private Double votingPopulation;
	
	@Column(name="VAHISPANIC")
	@JsonIgnore
	private Double votingHispanicPopulation;
	
	@Column(name="VAWHITEALN")
	@JsonIgnore
	private Double votingWhitePopulation;
	
	@Column(name="VABLACKALN")
	@JsonIgnore
	private Double votingBlackPopulation;
	
	@Column(name="VAAIANALN")
	@JsonIgnore
	private Double votingNativePopulation;
	
	@Column(name="VAOTHERALN")
	@JsonIgnore
	private Double votingOtherPopulation;
	
	@Column(name="VANHPOALN")
	@JsonIgnore
	private Double votingNativeHawiianPopulation;
	
	@Column(name="VAASIANALN")
	@JsonIgnore
	private Double votingAsianPopulation;
	
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
	
	public String toString() {
		return "[TotalDemographics: " + getTotalDemographics().toString() +
				", VotingDemographics: " + getVotingDemographics().toString() + "]";
	}
}
