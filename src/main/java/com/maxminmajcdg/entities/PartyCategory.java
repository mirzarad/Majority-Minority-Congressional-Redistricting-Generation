package com.maxminmajcdg.entities;

public enum PartyCategory {
	 DEMOCRATIC("DEMOCRATIC"), 
	 REPUBLICAN("REPUBLICAN"), 
	 GREEN("GREEN"), 
	 INDEPENDENT("INDEPENDENT");
	 
	 private String val;
	
	private PartyCategory(String val) {
		this.val = val;
	}
	
	public static PartyCategory fromValue(String val) {
		for (PartyCategory d : values()) {
			if (d.val.equalsIgnoreCase(val)) {
				return d;
			}
		}
		throw new IllegalArgumentException("Unkown enum type " + val);
	}
}
