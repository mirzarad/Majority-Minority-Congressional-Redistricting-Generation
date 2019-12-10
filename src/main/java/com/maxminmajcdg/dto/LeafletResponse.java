package com.maxminmajcdg.dto;

import com.maxminmajcdg.entities.MapEntity;

public class LeafletResponse {
	
	private float[] view;
	private int level;
	private MapEntity<?> map;
	
	public float[] getView() {
		return view;
	}
	public void setView(float[] view) {
		this.view = view;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public MapEntity<?> getMap() {
		return map;
	}
	public void setMap(MapEntity<?> map) {
		this.map = map;
	}
}
