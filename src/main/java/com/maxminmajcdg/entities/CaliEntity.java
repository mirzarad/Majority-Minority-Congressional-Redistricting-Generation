package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxminmajcdg.GeoJSONParser;

@Entity
@Table(name="ca_precincts_geom")
public class CaliEntity {
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
	
	@Column(name="shape")
	@Type(type="org.locationtech.jts.geom.Geometry")
	@JsonIgnore
	private Geometry shapeFile;
			
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
	
	public Geometry getShapeFile() {
		return shapeFile;
	}
	
	public String getType() {
		return "Feature";
	}
	
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("Name", mcdName);
		properties.put("County Name", countyName);
		return properties;
	}
	
	public GeometryEntity getGeometry() {
		return GeoJSONParser.getGeometryEntity(shapeFile);
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
				", vtdName=" + vtdName +
				", shape=" + shapeFile;
 	}
}
