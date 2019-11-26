package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CaliEntity;

@Repository
public interface CaliRepository extends JpaRepository<CaliEntity, Long> {
	
}
