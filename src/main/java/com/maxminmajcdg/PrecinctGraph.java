package com.maxminmajcdg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.maxminmajcdg.entities.CANeighbor2016Entity;
import com.maxminmajcdg.entities.CANeighbor2018Entity;
import com.maxminmajcdg.entities.District;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.NeighborEntity;
import com.maxminmajcdg.entities.PennNeighborEntity;
import com.maxminmajcdg.entities.VotesWrapper;
import com.maxminmajcdg.measures.Measure;
import com.maxminmajcdg.measures.MeasuresUtil;

public class PrecinctGraph {

	private Map<Integer, NeighborDistrictWrapper> districts;
	private List<Integer> phase1IgnoreIndex;
	private int totalPopulation;
	private int phase1Iter;
	private boolean isPhase1Done;
	private boolean isPhase2Done;
	
	public PrecinctGraph(Map<Integer, NeighborDistrictWrapper> districts, States state, ElectionCategory election, int totalPopulation) {
		this.districts = districts;
		this.phase1IgnoreIndex = new ArrayList<Integer>();
		this.isPhase1Done = false;
		this.isPhase2Done = false;
		this.phase1Iter = 0;
		this.totalPopulation = totalPopulation;
	}
	
	public NeighborEntity getRandomPrecinct(ElectionCategory election, Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage,
			float minDemographicBlocPercentage) {
		Random rand = new Random();
		int index = -1;
		
		if (phase1IgnoreIndex.size() == districts.size() || phase1Iter >= Properties.MAX_ITERATIONS) {
			isPhase1Done = true;
			return null;
		}
		
		while(phase1IgnoreIndex.contains(index =  (int) districts.keySet().toArray()[rand.nextInt(districts.keySet().size())]));
		NeighborEntity precinct = (NeighborEntity) districts.get(index);
		phase1Iter += 1;
				
		if (!precinct.isThresholdMet(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage)) {
			phase1IgnoreIndex.add(index);
			return getRandomPrecinct(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage);
		}
		return  precinct;
	}
	

	public NeighborEntity getOptimalPrecinct(NeighborDistrictWrapper randomPrecinct, ElectionCategory election,
			Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage, float minDemographicBlocPercentage) {
		
		List<Integer> neighbors = randomPrecinct.getNeighbors();
		
		double maxScore = -1;
		NeighborEntity optimalNeighbor = null;
		
		for (int n : neighbors) {
			NeighborEntity neighbor = (NeighborEntity) districts.get(n);
			if (neighbor.isThresholdMet(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage)) {
				double score = 0;
				score += .2 * MeasuresUtil.calculateMeasure(Measure.COMPETITIVENESS, neighbor, districts, election, totalPopulation);
				score += .2 * MeasuresUtil.calculateMeasure(Measure.GERRYMANDER_DEMOCRAT, neighbor, districts, election, totalPopulation);
				score += .2 * MeasuresUtil.calculateMeasure(Measure.GERRYMANDER_REPUBLICAN, neighbor, districts, election, totalPopulation);
				score += .2 * MeasuresUtil.calculateMeasure(Measure.POPULATION_EQUALITY, neighbor, districts, election, totalPopulation);
				score += .2 * MeasuresUtil.calculateMeasure(Measure.COMPACTNESS, neighbor, districts, election, totalPopulation);
				
				if (optimalNeighbor == null || score > maxScore) {
					optimalNeighbor = neighbor;
					maxScore = score;
				}
			}
		}
		
		if (optimalNeighbor == null) {
			phase1IgnoreIndex.add(randomPrecinct.getNodeID());
		}
		
		return optimalNeighbor;
	}
	
	public NeighborEntity join(NeighborEntity a, NeighborEntity b, States state, ElectionCategory election) {
		NeighborEntity ab;
		District newDistrict = new District();
		switch(state) {
		case PENN:
			ab = new PennNeighborEntity();
			break;
		case CALI:
			switch(election) {
			case CONGRESSIONAL2016:
			case PRESIDENTIAL2016:
				ab = new CANeighbor2016Entity();
			case CONGRESSIONAL2018:
				ab = new CANeighbor2018Entity();
				default:
					return null;
			}
			default:
				return null;
		}
		
		int newKey = Collections.max(districts.keySet()) + 1;
		newDistrict.setNodeID(newKey);
		newDistrict.addPrecincts(a.getNodeID());
		newDistrict.addPrecincts(b.getNodeID());
		ab.setNodeID(newKey);
		
		List<Integer> aNeighbors = a.getNeighbors();
		List<Integer> bNeighbors = b.getNeighbors();
		List<Integer> totalNeighbors = new ArrayList<Integer>(aNeighbors);
		totalNeighbors.addAll(bNeighbors);
		newDistrict.setNeighbors(totalNeighbors);
		
		Map<DemographicCategory, Double>  aDemographics = a.getDemographics().get(election).getTotalDemographics();
		Map<DemographicCategory, Double>  bDemographics = b.getDemographics().get(election).getTotalDemographics();
		Map<DemographicCategory, Double>  totalDemographics = new HashMap<DemographicCategory, Double>();
		
		for (DemographicCategory d : DemographicCategory.values()) {
			totalDemographics.put(d, aDemographics.get(d) + bDemographics.get(d)); 
		}
		
		newDistrict.setTotalDemographics(totalDemographics);
		
		Map<PartyCategory, Double> aVotes = a.getVotes().get(election).getVotes();
		Map<PartyCategory, Double> bVotes = b.getVotes().get(election).getVotes();
		Map<ElectionCategory, VotesWrapper>  totalVotes = new HashMap<ElectionCategory, VotesWrapper>();
		Map<PartyCategory, Double>  votes = new HashMap<PartyCategory, Double>();
		VotesWrapper newVotes = new VotesWrapper();
		
		for (PartyCategory p : PartyCategory.values()) {
			
			votes.put(p, aVotes.get(p) + bVotes.get(p)); 
		}
		newVotes.setVotes(votes);
		totalVotes.put(election, newVotes);
		
		newDistrict.setTotalVotes(totalVotes);
		
		phase1IgnoreIndex.add(a.getNodeID());
		phase1IgnoreIndex.add(b.getNodeID());
		
		return a;
	}
	
	public List<Integer> getPhase1IgnoreIndex() {
		return phase1IgnoreIndex;
	}

	public Map<Integer, ?> getPrecincts() {
		return districts;
	}

	public void setPrecincts(Map<Integer, NeighborDistrictWrapper> precincts) {
		this.districts = precincts;
	}

	public boolean isPhase1Done() {
		return isPhase1Done;
	}

	public void setPhase1Done(boolean isPhase1Done) {
		this.isPhase1Done = isPhase1Done;
	}

	public boolean isPhase2Done() {
		return isPhase2Done;
	}

	public void setPhase2Done(boolean isPhase2Done) {
		this.isPhase2Done = isPhase2Done;
	}

	public int getPhase1Iter() {
		return phase1Iter;
	}

	public void setPhase1Iter(int phase1Iter) {
		this.phase1Iter = phase1Iter;
	}

	public int getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}
	
}
