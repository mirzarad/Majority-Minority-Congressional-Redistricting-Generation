package com.maxminmajcdg.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NeighborWrapper {
	
	@Column(name="nbr_ID")
	private Long neighbor;
}

