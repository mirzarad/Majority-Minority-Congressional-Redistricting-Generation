package com.maxminmajcdg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.DistrictEntity;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
	@Query(value = "SELECT * FROM congress_districts_boundaries WHERE statefp = ?1", nativeQuery = true)
	List<DistrictEntity> findByStateFp(String name);
}
