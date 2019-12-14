package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PennNeighborEntity;

@Repository
public interface PennNeighborRepository extends JpaRepository<PennNeighborEntity, Long> {
	
}
