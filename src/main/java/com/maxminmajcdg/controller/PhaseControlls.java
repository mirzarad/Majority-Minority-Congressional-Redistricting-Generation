package com.maxminmajcdg.controller;

public enum PhaseControlls {
	RUN("RUN"), 
	PAUSE("PAUSE"), 
	RESUME("RESUME"),
	KILL("KILL");
	 
	private String val;
	
	private PhaseControlls(String val) {
		this.val = val;
	}
	
	public static PhaseControlls fromValue(String val) {
		for (PhaseControlls p : values()) {
			if (p.val.equalsIgnoreCase(val)) {
				return p;
			}
		}
		throw new IllegalArgumentException("Unkown enum type " + val);
	}
	
	@Override
	public String toString() {
		return val;
	}
}
