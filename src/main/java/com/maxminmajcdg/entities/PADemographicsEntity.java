package com.maxminmajcdg.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pa_demographics_2016")
public class PADemographicsEntity extends DemographicsEntity{
	@Id
	@GeneratedValue
	@Column(name="geom_ID")
	private Long geomID;
	
	public Long getGeomID() {
		return geomID;
	}
}