package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PAVotesPres2016Entity;

@Repository
public interface PAVotesPres2016Repository extends JpaRepository<PAVotesPres2016Entity, Long> {
	
}
