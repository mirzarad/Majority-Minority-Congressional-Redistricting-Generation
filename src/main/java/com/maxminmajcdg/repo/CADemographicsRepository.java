package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CADemographicsEntity;

@Repository
public interface CADemographicsRepository extends JpaRepository<CADemographicsEntity, Long> {
	
}
