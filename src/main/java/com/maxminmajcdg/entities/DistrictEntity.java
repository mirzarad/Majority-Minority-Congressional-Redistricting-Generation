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
@Table(name="congress_districts_boundaries")
public class DistrictEntity {

	@Id
	@GeneratedValue
	@Column(name="OGR_FID")
	private Long id;
	
	@Column(name="statefp")
	private String name;
	
	@Column(name="shape")
	@Type(type="org.locationtech.jts.geom.Geometry")
	@JsonIgnore
	private Geometry shapeFile;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Geometry getShapeFile() {
		return shapeFile;
	}
	
	public String getType() {
		return "Feature";
	}
	
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("id", id.toString());
		return properties;
	}
	
	public GeometryEntity getGeometry() {
		return GeoJSONParser.getGeometryEntity(shapeFile);
	}
	
}
