package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CAVotesCong2016Entity;

@Repository
public interface CAVotesCong2016Repository extends JpaRepository<CAVotesCong2016Entity, Long> {
	
}
