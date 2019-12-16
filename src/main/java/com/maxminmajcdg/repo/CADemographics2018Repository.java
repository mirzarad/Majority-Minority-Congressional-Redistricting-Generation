package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CADemographics2018Entity;

@Repository
public interface CADemographics2018Repository extends JpaRepository<CADemographics2018Entity, Long> {
	@Query(value = "SELECT SUM(TAPERSONS) FROM cali_demographics18", nativeQuery = true)
	Double getTotalPopulation();
}
