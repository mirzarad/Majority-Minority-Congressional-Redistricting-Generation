package com.maxminmajcdg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CANeighbor2018Entity;

@Repository
public interface CANeighbor2018Repository extends JpaRepository<CANeighbor2018Entity, Long> {
	@Query(value = "SELECT DISTINCT src_ID FROM cali_geom_18", nativeQuery = true)
	List<CANeighbor2018Entity> findAllDistinct();
}
