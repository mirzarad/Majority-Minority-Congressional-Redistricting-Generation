package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cali_neighbors_16")
public class CANeighbor2016Entity extends NeighborEntity{
	
	@ElementCollection
	@CollectionTable(name="cali_neighbors_16", joinColumns = { @JoinColumn(name = "src_ID") })
	@Column(name="nbr_ID")
	private List<Integer> neighbors;
	
	@OneToMany
	@JoinColumn(name="geom_ID", referencedColumnName="src_ID")
	@JsonIgnore
	private List<CAVotesCong2016Entity> caVotesCong2016;
	
	@OneToMany
	@JoinColumn(name="geom_ID", referencedColumnName="src_ID")
	@JsonIgnore
	private List<CAVotesPres2016Entity> caVotesPres2016;
	
	@OneToMany
	@JoinColumn(name="geom_ID", referencedColumnName="src_ID")
	@JsonIgnore
	private List<CADemographics2016Entity> caDemographics2016;

	@Override
	public Map<ElectionCategory, VotesWrapper> getVotes() {
		Map<ElectionCategory, VotesWrapper> votes = new HashMap<ElectionCategory, VotesWrapper>();
		votes.put(ElectionCategory.CONGRESSIONAL2016, caVotesCong2016.get(0));
		votes.put(ElectionCategory.PRESIDENTIAL2016, caVotesPres2016.get(0));
		votes.put(ElectionCategory.CONGRESSIONAL2018, null);
		return votes;
	}

	@Override
	public Map<ElectionCategory, DemographicsEntity> getDemographics() {
		Map<ElectionCategory, DemographicsEntity> demographics = new HashMap<ElectionCategory, DemographicsEntity>();
		demographics.put(ElectionCategory.CONGRESSIONAL2016, caDemographics2016.get(0));
		demographics.put(ElectionCategory.PRESIDENTIAL2016, caDemographics2016.get(0));
		demographics.put(ElectionCategory.CONGRESSIONAL2018, null);
		return demographics;
	}

	@Override
	public List<Integer> getNeighbors() {
		return neighbors;
	}
	
	public String toString() {
		return "[PrecinctID: " + getNodeID() +
				" Neighbors: " + getNeighbors().toString() + 
				" ,Votes: " + getVotes().toString() + 
				"]";
	}
}