package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PADemographics2018Entity;

@Repository
public interface PADemographics2018Repository extends JpaRepository<PADemographics2018Entity, Long> {
	
}
