package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pa_neighbors_graph")
public class PennNeighborEntity extends NeighborEntity{
	
	@ElementCollection
	@CollectionTable(name="pa_neighbors_graph", joinColumns = { @JoinColumn(name = "src_ID") })
	@Column(name="nbr_ID")
	private List<Integer> neighbors;
	
	@OneToMany(mappedBy="geomID", cascade = {CascadeType.PERSIST})
	@JsonIgnore
	private List<PAVotesCong2016Entity> paVotesCong2016;
	
	//@OneToOne(mappedBy="geomID")
	//@JsonIgnore
	//private PAVotesCong2018Entity paVotesCong2018;
	
	@OneToMany(mappedBy="geomID", cascade = {CascadeType.PERSIST})
	@JsonIgnore
	private List<PAVotesPres2016Entity> paVotesPres2016;
	
	@OneToMany(mappedBy="geomID", cascade = {CascadeType.PERSIST})
	@JsonIgnore
	private List<PADemographics2016Entity> paDemographics2016;
	
//	@OneToMany(mappedBy="geomID")
//	@JsonIgnore
//	private List<PADemographics2018Entity> paDemographics2018;

	@Override
	public Map<ElectionCategory, VotesWrapper> getVotes() {
		Map<ElectionCategory, VotesWrapper> votes = new HashMap<ElectionCategory, VotesWrapper>();
		votes.put(ElectionCategory.CONGRESSIONAL2016, paVotesCong2016.get(0));
		votes.put(ElectionCategory.PRESIDENTIAL2016, paVotesPres2016.get(0));
		votes.put(ElectionCategory.CONGRESSIONAL2018, null);
		return votes;
	}

	@Override
	public Map<ElectionCategory, DemographicsEntity> getDemographics() {
		Map<ElectionCategory, DemographicsEntity> demographics = new HashMap<ElectionCategory, DemographicsEntity>();
		demographics.put(ElectionCategory.CONGRESSIONAL2016, paDemographics2016.get(0));
		demographics.put(ElectionCategory.PRESIDENTIAL2016, paDemographics2016.get(0));
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