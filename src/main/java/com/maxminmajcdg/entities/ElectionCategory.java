package com.maxminmajcdg.entities;

public enum ElectionCategory {
	PRESIDENTIAL2016("PRESIDENTIAL2016"), 
	CONGRESSIONAL2016("CONGRESSIONAL2016"), 
	CONGRESSIONAL2018("CONGRESSIONAL2018");
	 
	private String val;
	
	private ElectionCategory(String val) {
		this.val = val;
	}
	
	public static ElectionCategory fromValue(String val) {
		for (ElectionCategory d : values()) {
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
