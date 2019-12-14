package com.maxminmajcdg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxminmajcdg.entities.CaliEntity2016;

@Repository
public interface CaliRepository2016 extends JpaRepository<CaliEntity2016, Long> {
	
}
