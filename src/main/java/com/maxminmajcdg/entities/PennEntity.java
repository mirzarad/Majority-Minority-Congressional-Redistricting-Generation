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
@Table(name="pa_precincts_geom")
public class PennEntity extends StateEntity{
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="OGR_FID")
	@JsonIgnore
	private int fid;
	
	@Column(name="objectid_1")
	@JsonIgnore
	private int objectId;
	
	@Column(name="namelsad")
	@JsonIgnore
	private String nameLSad;
	
	@Column(name="correctstf")
	@JsonIgnore
	private String correctSTF;
	
	@Column(name="countyname")
	@JsonIgnore
	private String countyName;
	
	@Column(name="mcd_name")
	@JsonIgnore
	private String mcdName;
	
	@Column(name="vtd_name")
	@JsonIgnore
	private String vtdName;
			
	public Long getId() {
		return id;
	}
	
	public int getFID() {
		return fid;
	}
	
	public int getObjectId() {
		return objectId;
	}
	
	public String getNameLSad() {
		return nameLSad;
	}
	
	public String getCorrectSTF() {
		return correctSTF;
	}
	
	public String getCountyName() {
		return countyName;
	}
	
	public String getMcdName() {
		return mcdName;
	}
	
	public String getVtdName() {
		return vtdName;
	}
	
	public String getType() {
		return "Feature";
	}
	
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("id", id.toString());
		properties.put("Name", mcdName);
		properties.put("County Name", countyName);
		return properties;
	}
	
	@Override
	public String toString() {
		return "StateEntity [id=" + id +
				", fid=" + fid + 
				", objectId=" + objectId + 
				", namelsad=" + nameLSad + 
				", correctstf=" + correctSTF +
				", countyname=" + countyName +
				", mcdName=" + mcdName +
				", vtdName=" + vtdName;
 	}
}
