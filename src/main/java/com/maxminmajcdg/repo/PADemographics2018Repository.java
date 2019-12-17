package com.maxminmajcdg.repo;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PADemographics2018Entity;

@Repository
public interface PADemographics2018Repository extends JpaRepository<PADemographics2018Entity, Long> {
	@Query(value = "SELECT SUM(TAPERSONS) FROM pa_demographics_2018", nativeQuery = true)
	Double getTotalPopulation();
	
	@Query(value = "SELECT geom_ID, TAPERSONS FROM pa_demographics_2018", nativeQuery = true)
	Map<Integer, Double> findAllPopulations();
}
