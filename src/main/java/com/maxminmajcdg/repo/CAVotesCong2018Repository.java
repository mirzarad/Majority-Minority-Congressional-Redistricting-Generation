package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CAVotesPres2016Entity;

@Repository
public interface CAVotesCong2018Repository extends JpaRepository<CAVotesPres2016Entity, Long> {
	
}
