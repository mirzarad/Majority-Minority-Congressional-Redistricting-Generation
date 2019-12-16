package com.maxminmajcdg;

public enum States {
	PENN("pennsylvania", "42"),
	CALI("california", "06"),
	USA("usa", "-1");

	private String val;
	private String id;
	
	private States(String val, String id) {
		this.val = val;
		this.id = id;
	}
	
	public String getId() {
		return id;
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
