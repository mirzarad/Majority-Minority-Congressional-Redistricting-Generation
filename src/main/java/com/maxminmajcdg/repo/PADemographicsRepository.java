package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PADemographicsEntity;

@Repository
public interface PADemographicsRepository extends JpaRepository<PADemographicsEntity, Long> {
	
}
