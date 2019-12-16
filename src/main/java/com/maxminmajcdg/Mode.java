package com.maxminmajcdg;

public enum Mode {
	
	INIT("INIT"),
	ITERATE("ITERATE"),
	FULL("FULL");
	
	private String val;
	
	private Mode(String val) {
		this.val = val;
	}
	
	public static Mode fromValue(String val) {
		for (Mode m : values()) {
			if (m.val.equalsIgnoreCase(val)) {
				return m;
			}
		}
		throw new IllegalArgumentException("Unkown enum type " + val);
	}
}
