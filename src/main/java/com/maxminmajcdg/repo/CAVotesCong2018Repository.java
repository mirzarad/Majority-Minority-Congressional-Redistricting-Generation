package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CAVotesCong2018Entity;

@Repository
public interface CAVotesCong2018Repository extends JpaRepository<CAVotesCong2018Entity, Long> {
	
}
