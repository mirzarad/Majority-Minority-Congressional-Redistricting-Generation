package com.maxminmajcdg.entities;

public enum DemographicCategory {
	 AFRICAN_AMERICAN("AFRICAN_AMERICAN"), 
	 NATIVE_AMERICAN("NATIVE_AMERICAN"), 
	 ASIAN("ASIAN"), 
	 NATIVE_HAWAIIAN("NATIVE_HAWAIIAN"), 
	 HISPANIC("HISPANIC"), 
	 WHITE("WHITE");
	 
	 private String val;
	
	private DemographicCategory(String val) {
		this.val = val;
	}
	
	public static DemographicCategory fromValue(String val) {
		for (DemographicCategory d : values()) {
			if (d.val.equalsIgnoreCase(val)) {
				return d;
			}
		}
		throw new IllegalArgumentException("Unkown enum type " + val);
	}
	
	@Override
	public String toString() {
		return val;
	}
}
