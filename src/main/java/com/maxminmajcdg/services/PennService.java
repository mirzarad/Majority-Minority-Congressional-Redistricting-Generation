package com.maxminmajcdg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxminmajcdg.entities.PennEntity;
import com.maxminmajcdg.repo.PennRepository;

@Service
public class PennService {
	
	@Autowired
	private PennRepository pennRepository;
	
	public List<PennEntity> list() {
		return pennRepository.findAll();
	}
}
