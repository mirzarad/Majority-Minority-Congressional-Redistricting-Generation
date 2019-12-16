package com.maxminmajcdg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.maxminmajcdg.entities.DemographicWrapper;
import com.maxminmajcdg.entities.District;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.VotesWrapper;
import com.maxminmajcdg.measures.Measure;
import com.maxminmajcdg.measures.MeasuresUtil;

public class PrecinctGraph {

	private Map<Integer, NeighborDistrictWrapper> districts;
	private List<Integer> phase1IgnoreIndex;
	private int totalPopulation;
	private int phase1Iter;
	private int maxNumDistricts;
	private int numDistricts;
	private boolean isPhase1Done;
	private boolean isPhase2Done;
	
	public PrecinctGraph(Map<Integer, NeighborDistrictWrapper> districts, States state, ElectionCategory election, int totalPopulation, int maxNumDistricts) {
		this.districts = districts;
		this.phase1IgnoreIndex = new ArrayList<Integer>();
		this.isPhase1Done = false;
		this.isPhase2Done = false;
		this.phase1Iter = 0;
		this.totalPopulation = totalPopulation;
		this.setMaxNumDistricts(maxNumDistricts);
		this.numDistricts = this.districts.size();
	}
	
	public NeighborDistrictWrapper getRandomPrecinct(ElectionCategory election, Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage,
			float minDemographicBlocPercentage) {
		Random rand = new Random();
		int index = -1;
		
		if (phase1IgnoreIndex.size() == districts.size() || phase1Iter >= Properties.MAX_ITERATIONS) {
			isPhase1Done = true;
			return null;
		}
		
		while(phase1IgnoreIndex.contains(index =  (int) districts.keySet().toArray()[rand.nextInt(districts.keySet().size())]));
		NeighborDistrictWrapper precinct = (NeighborDistrictWrapper) districts.get(index);
		phase1Iter += 1;
				
		if (!precinct.isThresholdMet(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage)) {
			phase1IgnoreIndex.add(index);
			return getRandomPrecinct(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage);
		}
		return  precinct;
	}
	

	public NeighborDistrictWrapper getOptimalPrecinct(NeighborDistrictWrapper randomPrecinct, ElectionCategory election,
			Map<DemographicCategory, Boolean> demographics, float maxDemographicBlocPercentage, float minDemographicBlocPercentage) {
		if (randomPrecinct == null) {
			return null;
		}
		
		List<Integer> neighbors = randomPrecinct.getNeighbors();
		
		double maxScore = -1;
		NeighborDistrictWrapper optimalNeighbor = null;
		for (int n : neighbors) {
			NeighborDistrictWrapper neighbor = (NeighborDistrictWrapper) districts.get(n);
			if (neighbor == null) {
				continue;
			}

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
	
	public NeighborDistrictWrapper join(NeighborDistrictWrapper a, NeighborDistrictWrapper b, States state, ElectionCategory election) {
		if(a == null) {
			return null;
		}
		if(b == null) {
			phase1IgnoreIndex.add(a.getNodeID());
			return null;
		}
		
		District newDistrict = new District();
		int newKey = Collections.max(districts.keySet()) + 1;
		newDistrict.setNodeID(newKey);
		newDistrict.addPrecincts(a.getNodeID());
		newDistrict.addPrecincts(b.getNodeID());
		
		List<Integer> aNeighbors = a.getNeighbors();
		List<Integer> bNeighbors = b.getNeighbors();
		List<Integer> totalNeighbors = new ArrayList<Integer>(aNeighbors);
		List<Integer> bCopy = new ArrayList<Integer>(bNeighbors);		
		bCopy.removeAll(aNeighbors);
		totalNeighbors.addAll(bCopy);
		newDistrict.setNeighbors(totalNeighbors);
		
		Map<DemographicCategory, Double>  aDemographics = a.getDemographics().get(election).getTotalDemographics();
		Map<DemographicCategory, Double>  bDemographics = b.getDemographics().get(election).getTotalDemographics();
		Map<DemographicCategory, Double>  abDemographics = new HashMap<DemographicCategory, Double>();
		Map<ElectionCategory, DemographicWrapper>  totalDemographics = new HashMap<ElectionCategory, DemographicWrapper>();
		
		for (DemographicCategory d : DemographicCategory.values()) {
			abDemographics.put(d, aDemographics.get(d) + bDemographics.get(d)); 
		}
		DemographicWrapper abDemographicWrap = new DemographicWrapper();
		abDemographicWrap.setDemographics(abDemographics);
		totalDemographics.put(election, abDemographicWrap);
		newDistrict.setTotalDemographics(totalDemographics);
		
		Map<PartyCategory, Double> aVotes = a.getVotes().get(election).getVotes();
		Map<PartyCategory, Double> bVotes = b.getVotes().get(election).getVotes();
		Map<ElectionCategory, VotesWrapper>  totalVotes = new HashMap<ElectionCategory, VotesWrapper>();
		Map<PartyCategory, Double>  votes = new HashMap<PartyCategory, Double>();
		VotesWrapper newVotes = new VotesWrapper();
		
		for (PartyCategory p : PartyCategory.values()) {
			Double aV = aVotes.get(p);
			Double bV = bVotes.get(p);
			if (aV == null) {
				aV = new Double(0);
			}
			if (bV == null) {
				aV = new Double(0);
			}
			if(p == PartyCategory.OTHER) {
				votes.put(p, new Double(0));
			}
			else {
				votes.put(p, aV + bV); 
			}
		}
		newVotes.setVotes(votes);
		totalVotes.put(election, newVotes);
		
		newDistrict.setTotalVotes(totalVotes);
		
		phase1IgnoreIndex.remove(a.getNodeID());
		phase1IgnoreIndex.remove(b.getNodeID());
		
		districts.remove(a.getNodeID());
		districts.remove(b.getNodeID());
		districts.put(newKey, newDistrict);
		districts.put(a.getNodeID(), newDistrict);
		districts.put(b.getNodeID(), newDistrict);
		numDistricts -= 1;
		return newDistrict;
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

	public int getNumDistricts() {
		return numDistricts;
	}

	public void setNumDistricts(int numDistricts) {
		this.numDistricts = numDistricts;
	}

	public int getMaxNumDistricts() {
		return maxNumDistricts;
	}

	public void setMaxNumDistricts(int maxNumDistricts) {
		this.maxNumDistricts = maxNumDistricts;
	}

	public boolean isFinished() {
		return maxNumDistricts == numDistricts;
	}
	
}
