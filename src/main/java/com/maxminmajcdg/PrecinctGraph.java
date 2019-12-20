package com.maxminmajcdg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import com.maxminmajcdg.dto.DistrictResponse;
import com.maxminmajcdg.entities.DemographicWrapper;
import com.maxminmajcdg.entities.District;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;
import com.maxminmajcdg.entities.VotesWrapper;
import com.maxminmajcdg.measures.Measure;
import com.maxminmajcdg.measures.MeasuresUtil;

public class PrecinctGraph {

	private Map<Integer, NeighborDistrictWrapper> districts;
	private Map<Integer, NeighborDistrictWrapper> precincts;
	private Queue<Integer> queue;
	private List<Integer> phase1IgnoreIndex;
	private Map<Integer, Double> liveDistricts;
	private States stateName;
	private int totalPopulation;
	private int phase1Iter;
	private int maxNumDistricts;
	private int numDistricts;
	private int successiveFails;
	private boolean isPhase1Done;
	private boolean isPhase2Done;
	private States state;
	private ElectionCategory election;
	private Map<DemographicCategory, Boolean> demographics;
	private float maxDemographicBlocPercentage;
	private float minDemographicBlocPercentage;
	private int newUniqueIDBase;
	
	private static SplittableRandom rand = new SplittableRandom();
	
	public PrecinctGraph(Map<Integer, NeighborDistrictWrapper> districts, Map<Integer, Double> liveDistricts, States state, ElectionCategory election, Map<DemographicCategory, Boolean> demographics, int totalPopulation, int maxNumDistricts, float maxDemographicBlocPercentage, float minDemographicBlocPercentage) {
		this.districts = districts;
		this.precincts = new HashMap<Integer, NeighborDistrictWrapper>(districts);
		this.liveDistricts = liveDistricts;
		this.setNewUniqueIDBase(0);
		liveDistricts.keySet().retainAll(districts.keySet());
		this.queue = new LinkedList<Integer>(liveDistricts.keySet());
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
		this.successiveFails = 0;
	}
	
	public PrecinctGraph(Map<Integer, NeighborDistrictWrapper> districts) {
		this.districts = districts;
		this.precincts = new HashMap<Integer, NeighborDistrictWrapper>(districts);
	}

	public void reset() {
		districts = new HashMap<Integer, NeighborDistrictWrapper>(precincts);;
		phase1IgnoreIndex = new ArrayList<Integer>();
		isPhase1Done = false;
		isPhase2Done = false;
		phase1Iter = 0;
		numDistricts = this.districts.size();
		successiveFails = 0;
	}
	
	public NeighborDistrictWrapper getRandomPrecinct() {		
		int index = -1;
		
		if (phase1IgnoreIndex.size() == districts.size() || phase1Iter >= Properties.MAX_ITERATIONS) {
			isPhase1Done = true;
			return null;
		}
		
		ArrayList<Integer> keys = new ArrayList<Integer>(liveDistricts.keySet());
		index = keys.get(rand.nextInt(keys.size()));
		NeighborDistrictWrapper precinct = (NeighborDistrictWrapper) districts.get(index);
		
		if (!precinct.isPhase1ThresholdMet(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage)) {
			phase1IgnoreIndex.add(index);
			successiveFails += 1;
			return null;
		}
		
		successiveFails = 0;
		return  precinct;
	}
	

	public NeighborDistrictWrapper getOptimalPrecinct(NeighborDistrictWrapper randomPrecinct) {
		if (randomPrecinct == null) {
			return null;
		}
		
		Set<Integer> neighbors = randomPrecinct.getNeighbors();
		if (neighbors == null) {
			return null;
		}
		
		Map<Integer, Double> neighborVals = neighbors.stream()
		.filter(e -> districts.get(e).isPhase1ThresholdMet(election, demographics, maxDemographicBlocPercentage, minDemographicBlocPercentage))
		.collect(Collectors.toMap(Function.identity(), e -> 
				//MeasuresUtil.calculateMeasure(Measure.COMPETITIVENESS, districts.get(e), precicnts, election, totalPopulation) 
				//+
				MeasuresUtil.calculateMeasure(Measure.GERRYMANDER_DEMOCRAT, districts.get(e), precincts, election, totalPopulation)
				+
				MeasuresUtil.calculateMeasure(Measure.GERRYMANDER_REPUBLICAN, districts.get(e), precincts, election, totalPopulation)
				+
				MeasuresUtil.calculateMeasure(Measure.COMPACTNESS, districts.get(e), precincts, election, totalPopulation)
				));
		if (neighborVals.size() == 0) {
			successiveFails += 1;
			return null;
		}
		
		int optimalNeighbor = Collections.max(neighborVals.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();

		return districts.get(optimalNeighbor);
	}
	
	public Pair<NeighborDistrictWrapper, DistrictResponse> join(NeighborDistrictWrapper a, NeighborDistrictWrapper b) {
		if(a == null || b == null) {
			return null;
		}
		int max = (a.getNodeID() > b.getNodeID())? a.getNodeID() : b.getNodeID();
		int min = (a.getNodeID() < b.getNodeID())? a.getNodeID() : b.getNodeID();
		
		int uniqueID = a.getUniqueID();
		if (uniqueID == -1) {
			uniqueID = b.getUniqueID();
			if (uniqueID == -1) {
				uniqueID = newUniqueIDBase;
				++newUniqueIDBase;
			}
		}
		
		District newDistrict = new District();
		newDistrict.setNodeID(min);
		newDistrict.setUniqueID(uniqueID);
		a.setUniqueID(uniqueID);
		b.setUniqueID(uniqueID);
		newDistrict.addPrecincts(a.getNodeID());
		newDistrict.addPrecincts(b.getNodeID());
		newDistrict.addPrecincts(a.getPrecincts());
		newDistrict.addPrecincts(b.getPrecincts());
		newDistrict.setNewID(b.getPrecincts());
		Set<Integer> newPrecincts = newDistrict.getPrecincts();
		int internalEdges = (int) newPrecincts.stream().filter(precinct -> districts.get(precinct).getNeighbors().contains(b.getNodeID())).count();
		newDistrict.setInternalEdges(a.getInternalEdges() + internalEdges/2);
		
		Set<Integer> aNeighbors = a.getNeighbors();
		Set<Integer> bNeighbors = b.getNeighbors();
		Set<Integer> totalNeighbors = new HashSet<Integer>(aNeighbors);
		Set<Integer> bCopy = new HashSet<Integer>(bNeighbors);		
		bCopy.removeAll(aNeighbors);
		totalNeighbors.addAll(bCopy);
		totalNeighbors.removeAll(a.getPrecincts());
		totalNeighbors.removeAll(b.getPrecincts());
		totalNeighbors.remove(a.getNodeID());
		totalNeighbors.remove(b.getNodeID());
		totalNeighbors = totalNeighbors.stream().filter(k -> districts.get(k).getNodeID() != a.getNodeID() && districts.get(k).getNodeID() != b.getNodeID()).collect(Collectors.toSet());
		newDistrict.setNeighbors(totalNeighbors);
		newDistrict.setExternalEdges(totalNeighbors.size());
		
		Map<DemographicCategory, Double>  aDemographics = a.getDemographics().get(election).getTotalDemographics();
		Map<DemographicCategory, Double>  abDemographics = b.getDemographics().get(election).getTotalDemographics();
		Map<ElectionCategory, DemographicWrapper>  totalDemographics = new HashMap<ElectionCategory, DemographicWrapper>();
		aDemographics.forEach((k, v) -> abDemographics.merge(k, v, Double::sum));
		
		DemographicWrapper abDemographicWrap = new DemographicWrapper();
		abDemographicWrap.setDemographics(abDemographics);
		totalDemographics.put(election, abDemographicWrap);
		newDistrict.setTotalDemographics(totalDemographics);
		
		Map<PartyCategory, Double> aVotes = a.getVotes().get(election).getVotes();
		Map<PartyCategory, Double> votes = b.getVotes().get(election).getVotes();
		Map<ElectionCategory, VotesWrapper> totalVotes = new HashMap<ElectionCategory, VotesWrapper>();
		aVotes.forEach((k,v) -> votes.merge(k, v, Double::sum));
		VotesWrapper newVotes = new VotesWrapper();
		newVotes.setVotes(votes);
		totalVotes.put(election, newVotes);
		
		newDistrict.setTotalVotes(totalVotes);
		
		District bDistrict = new District(newDistrict);
		bDistrict.setNodeID(max);
		
		districts.remove(a.getNodeID());
		districts.remove(b.getNodeID());
		districts.put(min, newDistrict);
		districts.put(max, bDistrict);
		liveDistricts.remove(a.getNodeID());
		liveDistricts.remove(b.getNodeID());
		liveDistricts.put(min, newDistrict.getPopulation(election));
		liveDistricts.put(max, bDistrict.getPopulation(election));
		numDistricts -= 1;
		
		
		return Pair.with(newDistrict, new DistrictResponse(newDistrict, election));
	}
	
	public Pair<NeighborDistrictWrapper, DistrictResponse> finalizeDistricts() {
		int index = queue.remove();
		NeighborDistrictWrapper precinct = districts.get(index);
		queue.add(index);
		
		Set<Integer> neighbors = precinct.getNeighbors();
		ArrayList<Integer> neighborKeys = new ArrayList<Integer>(neighbors);
		int smallestNeighborKey = neighborKeys.get(rand.nextInt(neighbors.size()));
		NeighborDistrictWrapper smallestNeighbor = districts.get(smallestNeighborKey);
		return join(precinct, smallestNeighbor);
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

	public Map<Integer, NeighborDistrictWrapper> getPrecicnts() {
		return precincts;
	}

	public void setPrecicnts(Map<Integer, NeighborDistrictWrapper> precicnts) {
		this.precincts = precicnts;
	}

	public int getSuccessiveFails() {
		return successiveFails;
	}

	public void setSuccessiveFails(int successiveFails) {
		this.successiveFails = successiveFails;
	}

	public void updatePhase1Iter() {
		phase1Iter += 1;
	}

	public void setElection(ElectionCategory election) {
		this.election = election;
	}

	public void setDeomographics(Map<DemographicCategory, Boolean> demographics) {
		this.demographics = demographics;
	}

	public void setMaxDemographicBlocPercentage(float maxDemographicBlocPercentage) {
		this.maxDemographicBlocPercentage = maxDemographicBlocPercentage;
	}

	public void setMinDemographicBlocPercentage(float minDemographicBlocPercentage) {
		this.minDemographicBlocPercentage = minDemographicBlocPercentage;
	}

	public void setPrecinctsPopulation(Map<Integer, Double> precinctsPopulation) {
		this.liveDistricts = precinctsPopulation;
		liveDistricts.keySet().retainAll(districts.keySet());
		this.queue = new LinkedList<Integer>(liveDistricts.keySet());
	}

	public ElectionCategory getElection() {
		return election;
	}

	public States getStateName() {
		return stateName;
	}

	public void setStateName(States stateName) {
		this.stateName = stateName;
	}

	public int getNewUniqueIDBase() {
		return newUniqueIDBase;
	}

	public void setNewUniqueIDBase(int newUniqueIDBase) {
		this.newUniqueIDBase = newUniqueIDBase;
	}
	
}
