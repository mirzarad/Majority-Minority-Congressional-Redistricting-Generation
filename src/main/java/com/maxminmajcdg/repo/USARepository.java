package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.USAEntity;

@Repository
public interface USARepository extends JpaRepository<USAEntity, Long> {
	@Query(value = "SELECT * FROM usa_geom WHERE state = ?1", nativeQuery = true)
	USAEntity findByStateId(String name);
}
