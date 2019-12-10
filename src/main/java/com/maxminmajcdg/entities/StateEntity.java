package com.maxminmajcdg.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxminmajcdg.GeoJSONParser;

@MappedSuperclass
public class StateEntity {
	
	@Column(name="shape")
	@Type(type="org.locationtech.jts.geom.Geometry")
	@JsonIgnore
	private Geometry shapeFile;
	
	public Geometry getShapeFile() {
		return shapeFile;
	}
	
	public GeometryEntity getGeometry() {
		return GeoJSONParser.getGeometryEntity(shapeFile);
	}
}
