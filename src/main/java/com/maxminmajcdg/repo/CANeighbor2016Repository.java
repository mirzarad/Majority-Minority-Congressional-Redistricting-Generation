package com.maxminmajcdg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CANeighbor2016Entity;

@Repository
public interface CANeighbor2016Repository extends JpaRepository<CANeighbor2016Entity, Long> {
	@Query(value = "SELECT DISTINCT src_ID FROM cali_geom_16", nativeQuery = true)
	List<CANeighbor2016Entity> findAllDistinct();
}
