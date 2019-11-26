package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CAVotesEntity;

@Repository
public interface CAVotesRepository extends JpaRepository<CAVotesEntity, Long> {
	
}
