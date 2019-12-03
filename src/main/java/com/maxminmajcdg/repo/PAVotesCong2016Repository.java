package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PAVotesCong2016Entity;

@Repository
public interface PAVotesCong2016Repository extends JpaRepository<PAVotesCong2016Entity, Long> {
	
}
