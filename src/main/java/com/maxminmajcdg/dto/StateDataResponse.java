package com.maxminmajcdg.dto;

import java.util.Map;

import com.maxminmajcdg.entities.PartyCategory;

public class StateDataResponse {
	private long stateID;
	private Map<PartyCategory, Integer> votes;
	private String majorityParty;
	private float representativePercentage;
	private float polsbyPopperRatio;
	private float schwartzbergRatio;
	private float convexHullRatio;
	private float reockRatio;
	
	public Map<PartyCategory, Integer> getVotes() {
		return votes;
	}
	public void setVotes(Map<PartyCategory, Integer> votes) {
		this.votes = votes;
	}
	public String getMajorityParty() {
		return majorityParty;
	}
	public void setMajorityParty(String majorityParty) {
		this.majorityParty = majorityParty;
	}
	public float getRepresentativePercentage() {
		return representativePercentage;
	}
	public void setRepresentativePercentage(float representativePercentage) {
		this.representativePercentage = representativePercentage;
	}
	public float getPolsbyPopperRatio() {
		return polsbyPopperRatio;
	}
	public void setPolsbyPopperRatio(float polsbyPopperRatio) {
		this.polsbyPopperRatio = polsbyPopperRatio;
	}
	public float getSchwartzbergRatio() {
		return schwartzbergRatio;
	}
	public void setSchwartzbergRatio(float schwartzbergRatio) {
		this.schwartzbergRatio = schwartzbergRatio;
	}
	public float getConvexHullRatio() {
		return convexHullRatio;
	}
	public void setConvexHullRatio(float convexHullRatio) {
		this.convexHullRatio = convexHullRatio;
	}
	public float getReockRatio() {
		return reockRatio;
	}
	public void setReockRatio(float reockRatio) {
		this.reockRatio = reockRatio;
	}
	public long getStateID() {
		return stateID;
	}
	public void setStateID(long stateID) {
		this.stateID = stateID;
	}
}
