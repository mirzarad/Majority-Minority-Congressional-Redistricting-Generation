package com.maxminmajcdg.entities;

public enum States {
	PENN("penn"),
	CALI("california");

	private String val;
	
	private States(String val) {
		this.val = val;
	}
	
	public static States fromValue(String val) {
		for (States s : values()) {
			if (s.val.equalsIgnoreCase(val)) {
				return s;
			}
		}
		throw new IllegalArgumentException("Unkown enum type " + val);
	}
	
	@Override
	public String toString() {
		return val;
	}
}
