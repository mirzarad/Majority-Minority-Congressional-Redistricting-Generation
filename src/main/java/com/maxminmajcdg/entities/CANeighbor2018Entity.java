package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cali_neighbors_18")
public class CANeighbor2018Entity extends NeighborEntity{
	
	@ElementCollection
	@CollectionTable(name="cali_neighbors_18", joinColumns = { @JoinColumn(name = "src_ID") })
	@Column(name="nbr_ID")
	private List<Integer> neighbors;
	
	@OneToMany
	@JoinColumn(name="geom_ID", referencedColumnName="src_ID")
	@JsonIgnore
	private List<CAVotesCong2018Entity> caVotesCong2018;

	@OneToMany
	@JoinColumn(name="geom_ID", referencedColumnName="src_ID")
	@JsonIgnore
	private List<CADemographics2018Entity> caDemographics2018;

	@Override
	public Map<ElectionCategory, VotesWrapper> getVotes() {
		Map<ElectionCategory, VotesWrapper> votes = new HashMap<ElectionCategory, VotesWrapper>();
		votes.put(ElectionCategory.CONGRESSIONAL2016, null);
		votes.put(ElectionCategory.PRESIDENTIAL2016, null);
		votes.put(ElectionCategory.CONGRESSIONAL2018, caVotesCong2018.get(0));
		return votes;
	}

	@Override
	public Map<ElectionCategory, DemographicWrapper> getDemographics() {
		Map<ElectionCategory, DemographicWrapper> demographics = new HashMap<ElectionCategory, DemographicWrapper>();
		demographics.put(ElectionCategory.CONGRESSIONAL2016, null);
		demographics.put(ElectionCategory.PRESIDENTIAL2016, null);
		demographics.put(ElectionCategory.CONGRESSIONAL2018, caDemographics2018.get(0));
		return demographics;
	}

	@Override
	public Set<Integer> getNeighbors() {
		return new HashSet<Integer>(neighbors);
	}
	
	@Override
	public double getInternalEdges() {
		return 0;
	}

	@Override
	public double getExternalEdges() {
		return getNeighbors().size();
	}
}