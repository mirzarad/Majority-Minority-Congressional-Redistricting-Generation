package com.maxminmajcdg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PennNeighborEntity;

@Repository
public interface PennNeighborRepository extends JpaRepository<PennNeighborEntity, Long> {
	@Query(value = "SELECT DISTINCT src_ID FROM pa_neighbors_graph", nativeQuery = true)
	List<PennNeighborEntity> findAllDistinct();
}
