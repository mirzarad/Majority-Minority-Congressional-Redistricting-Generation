package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CaliEntity2018;

@Repository
public interface CaliRepository2018 extends JpaRepository<CaliEntity2018, Long> {
	
}
