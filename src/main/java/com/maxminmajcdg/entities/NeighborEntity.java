package com.maxminmajcdg.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NeighborEntity {
	
	
	//@Column(name="\ufeffOBJECTID")
	//private Long id;
	
	@Id
	@GeneratedValue
	@Column(name="src_ID")
	private Long nodeID;
	
	@Column(name="nbr_ID")
	@ElementCollection
	private List<Long> neighbors;
	
	//public Long getID() {
	//	return id;
	//}
	
	public Long getNodeID() {
		return nodeID;
	}
	
	public List<Long> getNeighbors() {
		return neighbors;
	}
	
	public abstract Map<ElectionCategory, List<?>> getVotes();
	public abstract Map<ElectionCategory, List<?>> getDemographics();
}
