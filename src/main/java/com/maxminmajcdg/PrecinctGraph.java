package com.maxminmajcdg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	private Map<Integer, Double> liveDistricts;
	private int totalPopulation;
	private int phase1Iter;
	private int maxNumDistricts;
	private int numDistricts;
	private boolean isPhase1Done;
	private boolean isPhase2Done;
	private States state;
	private ElectionCategory election;
	private Map<DemographicCategory, Boolean> demographics;
	private float maxDemographicBlocPercentage;
	private float minDemographicBlocPercentage;
	
	private static SplittableRandom rand = new SplittableRandom();
	
	public PrecinctGraph(Map<Integer, NeighborDistrictWrapper> districts, Map<Integer, Double> liveDistricts, States state, ElectionCategory election, Map<DemographicCategory, Boolean> demographics, int totalPopulation, int maxNumDistricts, float maxDemographicBlocPercentage, float minDemographicBlocPercentage) {
		this.districts = districts;
		this.liveDistricts = liveDistricts;
		liveDistricts.keySet().retainAll(districts.keySet());
		this.phase1IgnoreIndex = new ArrayList<Integer>();
		this.isPhase1Done = false;
		this.isPhase2Done = false;
		this.phase1Iter = 0;
		this.totalPopulation = totalPopulation;
		this.maxNumDistricts = maxNumDistricts;
		this.numDistricts = this.districts.size();
		this.election = election;
		this.state = state;
		this.demographics = demographics;
		this.maxDemographicBlocPercentage = maxDemographicBlocPercentage;
		this.minDemographicBlocPercentage = minDemographicBlocPercentage;
	}
	
	public NeighborDistrictWrapper getRandomPrecinct() {		
		int index = -1;
		
		if (phase1IgnoreIndex.size() == districts.size() || phase1Iter >= Properties.MAX_ITERATIONS) {
			isPhase1Done = true;
			return null;
		}
		
		ArrayList<Integer> liveKeys = new ArrayList<Integer>(liveDistricts.keySet());
		index = liveKeys.get(rand.nextInt(liveKeys.size()));
		if (phase1IgnoreIndex.contains(index)) {
			return null;
		}
		//while(phase1IgnoreIndex.contains(index =  liveKeys.get(rand.nextInt(liveKeys.size()))));
		NeighborDistrictWrapper precinct = (NeighborDistrictWrapper) districts.get(index);
		
		phase1Iter += 1;
		if (!precinct.isThresholdMet(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage)) {
			phase1IgnoreIndex.add(index);
			return null;
		}
		return  precinct;
	}
	

	public NeighborDistrictWrapper getOptimalPrecinct(NeighborDistrictWrapper randomPrecinct) {
		if (randomPrecinct == null) {
			return null;
		}
		
		List<Integer> neighbors = randomPrecinct.getNeighbors();
		if (neighbors == null) {
			return null;
		}
		
		Map<Integer, Double> neighborVals = neighbors.stream()
		.filter(e -> districts.get(e) != null && districts.get(e).isThresholdMet(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage))
		.collect(Collectors.toMap(Function.identity(), e -> 
				MeasuresUtil.calculateMeasure(Measure.COMPETITIVENESS, districts.get(e), districts, election, totalPopulation) 
				+
				MeasuresUtil.calculateMeasure(Measure.GERRYMANDER_DEMOCRAT, districts.get(e), districts, election, totalPopulation)
				+
				MeasuresUtil.calculateMeasure(Measure.GERRYMANDER_REPUBLICAN, districts.get(e), districts, election, totalPopulation)
				+
				MeasuresUtil.calculateMeasure(Measure.COMPACTNESS, districts.get(e), districts, election, totalPopulation)
				));
		
		if (neighborVals.size() == 0) {
			return null;
		}
		
		int optimalNeighbor = Collections.max(neighborVals.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();

		return districts.get(optimalNeighbor);
	}
	
	public NeighborDistrictWrapper join(NeighborDistrictWrapper a, NeighborDistrictWrapper b) {
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
		newDistrict.addPrecincts(a.getPrecincts());
		newDistrict.addPrecincts(b.getPrecincts());
		List<Integer> newPrecincts = newDistrict.getPrecincts();
		int internalEdges = (int) newPrecincts.stream().filter(precinct -> districts.get(precinct).getNeighbors().contains(b.getNodeID())).count();
		newDistrict.setInternalEdges(a.getInternalEdges() + internalEdges/2);
		
		List<Integer> aNeighbors = a.getNeighbors();
		List<Integer> bNeighbors = b.getNeighbors();
		List<Integer> totalNeighbors = new ArrayList<Integer>(aNeighbors);
		List<Integer> bCopy = new ArrayList<Integer>(bNeighbors);		
		bCopy.removeAll(aNeighbors);
		totalNeighbors.addAll(bCopy);
		totalNeighbors.removeAll(a.getPrecincts());
		totalNeighbors.removeAll(b.getPrecincts());
		newDistrict.setNeighbors(totalNeighbors);
		newDistrict.setExternalEdges(totalNeighbors.size());
		
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
		liveDistricts.remove(a.getNodeID());
		liveDistricts.remove(b.getNodeID());
		liveDistricts.put(newDistrict.getNodeID(), newDistrict.getPopulation(election));
		numDistricts -= 1;
		return newDistrict;
	}
	
	public NeighborDistrictWrapper finalizeDistricts() {
		int smallestKey = liveDistricts.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
		NeighborDistrictWrapper smallestDistrict = districts.get(smallestKey);
		List<Integer> neighbors = smallestDistrict.getNeighbors();
		int smallestNeighborKey = liveDistricts.entrySet().stream().filter(key-> neighbors.contains(key.getKey())).min(Map.Entry.comparingByValue()).get().getKey();
		NeighborDistrictWrapper smallestNeighbor = districts.get(smallestNeighborKey);
		NeighborDistrictWrapper newDistrict = join(smallestDistrict, smallestNeighbor);
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

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}

	public Map<Integer, Double> getLiveDistricts() {
		return liveDistricts;
	}

	public void setLiveDistricts(Map<Integer, Double> liveDistricts) {
		this.liveDistricts = liveDistricts;
	}
	
}
