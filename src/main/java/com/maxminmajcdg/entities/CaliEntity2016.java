package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cali_geom_16")
public class CaliEntity2016 extends StateEntity{
	
	@Id
	@GeneratedValue
	@Column(name="OGR_FID")
	private Long id;
	
	@Column(name="fid")
	@JsonIgnore
	private int fid;
	
	public Long getID() {
		return id;
	}
	
	public int getFID() {
		return fid;
	}
	
	public String getType() {
		return "Feature";
	}
	
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("id", id.toString());
		return properties;
	}
	
	@Override
	public String toString() {
		return "StateEntity [fid=" + fid + "]";

 	}
}
