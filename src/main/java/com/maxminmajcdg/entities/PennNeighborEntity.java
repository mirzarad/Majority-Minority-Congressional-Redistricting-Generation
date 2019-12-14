package com.maxminmajcdg.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pa_neighbors_graph")
public class PennNeighborEntity extends NeighborEntity{
	
	@OneToMany
	@JoinColumn(name="geom_ID", referencedColumnName="src_ID")
	@JsonIgnore
	private List<PAVotesCong2016Entity> paVotesCong2016;
	
	//@OneToMany
	//@JoinColumn(name="geom_ID")
	//@JsonIgnore
	//private List<PAVotesCong2018Entity> paVotesCong2018;
	
	@OneToMany
	@JsonIgnore
	private List<PAVotesPres2016Entity> paVotesPres2016;
	
	@OneToMany
	@JsonIgnore
	private List<PADemographics2016Entity> paDemographics2016;
	
	@OneToMany
	@JsonIgnore
	private List<PADemographics2018Entity> paDemographics2018;

	@Override
	public Map<ElectionCategory, List<?>> getVotes() {
		Map<ElectionCategory, List<?>> votes = new HashMap<ElectionCategory, List<?>>();
		votes.put(ElectionCategory.CONGRESSIONAL2016, paVotesCong2016);
		votes.put(ElectionCategory.PRESIDENTIAL2016, paVotesPres2016);
		votes.put(ElectionCategory.CONGRESSIONAL2018, null);
		return votes;
	}

	@Override
	public Map<ElectionCategory, List<?>> getDemographics() {
		Map<ElectionCategory, List<?>> votes = new HashMap<ElectionCategory, List<?>>();
		votes.put(ElectionCategory.CONGRESSIONAL2016, paDemographics2016);
		votes.put(ElectionCategory.PRESIDENTIAL2016, paDemographics2016);
		votes.put(ElectionCategory.CONGRESSIONAL2018, paDemographics2018);
		return null;
	}
}
