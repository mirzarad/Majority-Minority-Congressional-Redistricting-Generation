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
@Table(name="usa_geom")
public class USAEntity {
		
	@Id
	@GeneratedValue
	@JsonIgnore
	@Column(name="OGR_FID")
	private Long fid;
	
	@Column(name="geo_id")
	@JsonIgnore
	private String geoId;
	
	@Column(name="state")
	@JsonIgnore
	private String stateId;
	
	@Column(name="name")
	@JsonIgnore
	private String name;
	
	//@Column(name="lsad")
	//@JsonIgnore
	//private String lSad;
	
	@Column(name="censusarea")
	@JsonIgnore
	private double censusArea;
	
	@Column(name="shape")
	@Type(type="org.locationtech.jts.geom.Geometry")
	@JsonIgnore
	private Geometry shapeFile;
			
	public String getGeoId() {
		return geoId;
	}
	
	public Long getFID() {
		return fid;
	}
	
	public int getId() {
		return Integer.valueOf(stateId);
	}
	
	public String getStateId() {
		return stateId;
	} 
	
	public String getName() {
		return name;
	}
	
	//public String getLSad() {
	//	return lSad;
	//}

	public Geometry getShapeFile() {
		return shapeFile;
	}
	
	public String getType() {
		return "Feature";
	}
	
	public double getCensusArea() {
		return censusArea;
	}
	
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("name", name);
		properties.put("Census Area", Double.toString(censusArea));
		return properties;
	}
	
	public GeometryEntity getGeometry() {
		return GeoJSONParser.getGeometryEntity(shapeFile);
	}
	
	@Override
	public String toString() {
		return "StateEntity [id=" + fid +
				", geo_id=" + geoId + 
				", state=" + stateId + 
				", name=" + name +
				", census area=" + censusArea +
				//", lsad=" + lSad +
				", shape=" + shapeFile;
 	}
}

