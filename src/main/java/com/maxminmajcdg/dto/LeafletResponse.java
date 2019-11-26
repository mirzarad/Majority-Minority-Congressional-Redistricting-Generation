package com.maxminmajcdg.dto;

import com.maxminmajcdg.entities.MapEntity;

public class LeafletResponse<T> {
	
	private float[] view;
	private int level;
	private MapEntity<T> map;
	
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
	public MapEntity<T> getMap() {
		return map;
	}
	public void setMap(MapEntity<T> map) {
		this.map = map;
	}
}
