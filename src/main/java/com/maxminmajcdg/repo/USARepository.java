package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.USAEntity;

@Repository
public interface USARepository extends JpaRepository<USAEntity, Long> {
	
}
