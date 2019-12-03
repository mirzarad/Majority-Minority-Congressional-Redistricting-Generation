package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PADemographics2016Entity;

@Repository
public interface PADemographics2016Repository extends JpaRepository<PADemographics2016Entity, Long> {
	
}
