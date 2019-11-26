package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PennEntity;

@Repository
public interface PennRepository extends JpaRepository<PennEntity, Long> {
	
}
