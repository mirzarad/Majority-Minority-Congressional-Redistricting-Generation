package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CAVotesPres2016Entity;

@Repository
public interface CAVotesPres2016Repository extends JpaRepository<CAVotesPres2016Entity, Long> {
	
}
