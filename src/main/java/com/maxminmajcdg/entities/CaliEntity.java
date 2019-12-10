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
@Table(name="cali_geom_2016")
public class CaliEntity extends StateEntity{
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="OGR_FID")
	@JsonIgnore
	private int fid;
	
//	@Column(name="prec_key")
//	@JsonIgnore
//	private String precinctKey;
//	
//	@Column(name="precinct")
//	@JsonIgnore
//	private String precinct;
	
	@Column(name="election")
	@JsonIgnore
	private String election;
	
	@Column(name="area")
	@JsonIgnore
	private double area;
			
	public Long getId() {
		return id;
	}
	
	public int getFID() {
		return fid;
	}
	
//	public String getPrecinctKey() {
//		return precinctKey;
//	}
//	
//	public String getPrecinct() {
//		return precinct;
//	}
	
	public String getElection() {
		return election;
	}
	
	public String getType() {
		return "Feature";
	}
	
	public double getArea() {
		return area;
	}
	
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
//		properties.put("Name", precinct);
//		properties.put("Precinct Key", precinctKey);
		properties.put("Election", election);
		return properties;
	}
	
	@Override
	public String toString() {
		return "StateEntity [id=" + id +
				", fid=" + fid + 
//				", precinct=" + precinct + 
//				", precinct key=" + precinctKey + 
				", area=" + area +
				", election=" + election;
 	}
}
