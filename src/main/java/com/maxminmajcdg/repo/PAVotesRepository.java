package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.PAVotesEntity;

@Repository
public interface PAVotesRepository extends JpaRepository<PAVotesEntity, Long> {
	
}
