package com.maxminmajcdg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.entities.CaliEntity;
import com.maxminmajcdg.repo.CaliRepository;

@Service
public class CaliService {
	
	@Autowired
	private CaliRepository caliRepository;
	
	public List<CaliEntity> list() {
		return caliRepository.findAll();
	}
}
