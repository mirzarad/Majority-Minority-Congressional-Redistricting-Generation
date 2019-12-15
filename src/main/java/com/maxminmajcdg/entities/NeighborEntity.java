package com.maxminmajcdg.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NeighborEntity {
	@Id
	@GeneratedValue
	@Column(name="src_ID")
	private Long nodeID;
	
	public Long getNodeID() {
		return nodeID;
	}
	
	public abstract List<Long> getNeighbors();
	public abstract Map<ElectionCategory, List<?>> getVotes();
	public abstract Map<ElectionCategory, List<?>> getDemographics();
}
