package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PAVotesCong2018Entity;

@Repository
public interface PAVotesCong2018Repository extends JpaRepository<PAVotesCong2018Entity, Long> {
	
}
