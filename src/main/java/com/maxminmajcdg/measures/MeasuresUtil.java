package com.maxminmajcdg.measures;

import java.util.Map;

import com.maxminmajcdg.PartyCategory;
import com.maxminmajcdg.entities.ElectionCategory;
import com.maxminmajcdg.entities.NeighborDistrictWrapper;

public class MeasuresUtil {

    public static double calculateMeasure(Measure measure, NeighborDistrictWrapper district, Map<Integer, NeighborDistrictWrapper> districts, ElectionCategory election, int totalPopulation) {
        switch (measure) {
            case PARTISAN_FAIRNESS:
                return calculatePartisanFairness(district, districts, election);
            case COMPACTNESS:
                return calculateCompactness(district);
            case EFFICIENCY_GAP:
                return calculateEfficiencyGap(district, districts, election);
            case POPULATION_EQUALITY:
                return calculatePopulationEquality(district, districts, election, totalPopulation);
            case COMPETITIVENESS:
                return calculateCompetitiveness(district, election);
            case GERRYMANDER_REPUBLICAN:
                return calculateGerryManderGOP(district, election);
            case POPULATION_HOMOGENEITY:
                return calculatePopulationHomogeneity(district, districts, election);
            case GERRYMANDER_DEMOCRAT:
                return calculateGerryManderDEM(district, election);
            default:
                return -1;
        }
    }

    /*
            Partisan fairness:
            100% - underrepresented party's winning margin
            OR
            underrepresented party's losing margin
            (We want our underrepresented party to either win by a little or lose by a lot - fewer wasted votes)
        */
    public static double calculatePartisanFairness(NeighborDistrictWrapper d, Map<Integer, NeighborDistrictWrapper> districts, ElectionCategory election) {
        // Temporary section
        int totalVote = 0;
        int totalGOPvote = 0;
        int totalDistricts = 0;
        int totalGOPDistricts = 0;
        for (NeighborDistrictWrapper sd : districts.values()) {
            totalVote += sd.getVotes().get(election).getVotes().get(PartyCategory.REPUBLICAN);
            totalVote += sd.getVotes().get(election).getVotes().get(PartyCategory.DEMOCRATIC);
            totalGOPvote += sd.getVotes().get(election).getVotes().get(PartyCategory.REPUBLICAN);
            totalDistricts += 1;
            if (sd.getVotes().get(election).getVotes().get(PartyCategory.REPUBLICAN) > sd.getVotes().get(election).getVotes().get(PartyCategory.DEMOCRATIC)) {
                totalGOPDistricts += 1;
            }
        }
        int idealDistrictChange = ((int) Math.round(totalDistricts * ((1.0 * totalGOPvote) / totalVote))) - totalGOPDistricts;
        // End temporary section
        if (idealDistrictChange == 0) {
            return 1.0;
        }
        int gv = d.getVotes().get(election).getVotes().get(PartyCategory.REPUBLICAN).intValue();
        int dv = d.getVotes().get(election).getVotes().get(PartyCategory.DEMOCRATIC).intValue();
        int tv = gv + dv;
        int margin = gv - dv;
        if (tv == 0) {
            return 1.0;
        }
        int win_v = Math.max(gv, dv);
        int loss_v = Math.min(gv, dv);
        int inefficient_V;
        if (idealDistrictChange * margin > 0) {
            inefficient_V = win_v - loss_v;
        } else {
            inefficient_V = loss_v;
        }
        return 1.0 - ((inefficient_V * 1.0) / tv);
    }


    /*
    Compactness:
    perimeter / (circle perimeter for same area)
    */
    public static double calculateCompactness(NeighborDistrictWrapper d) {
        double internalEdges = d.getInternalEdges();
        double totalEdges = internalEdges + d.getExternalEdges();
        return internalEdges / totalEdges;
    }


    /*
    Wasted votes:
    Statewide: abs(Winning party margin - losing party votes)
    */
    public static double calculateEfficiencyGap(NeighborDistrictWrapper d, Map<Integer, NeighborDistrictWrapper> districts, ElectionCategory election) {
        int iv_g = 0;
        int iv_d = 0;
        int tv = 0;
        for (NeighborDistrictWrapper sd : districts.values()) {
            int gv = sd.getVotes().get(election).getVotes().get(PartyCategory.REPUBLICAN).intValue();
            int dv = sd.getVotes().get(election).getVotes().get(PartyCategory.DEMOCRATIC).intValue();
            if (gv > dv) {
                iv_d += dv;
                iv_g += (gv - dv);
            } else if (dv > gv) {
                iv_g += gv;
                iv_d += (dv - gv);
            }
            tv += gv;
            tv += dv;
        }
        return 1.0 - ((Math.abs(iv_g - iv_d) * 1.0) / tv);
    }


    public static double calculatePopulationEquality(NeighborDistrictWrapper d, Map<Integer, NeighborDistrictWrapper> districts, ElectionCategory election, int totalPopulation) {
        int idealPopulation = totalPopulation / districts.size();
        int truePopulation = d.getPopulation(election).intValue();
        if (idealPopulation >= truePopulation) {
            return ((double) truePopulation) / idealPopulation;
        }
        return ((double) idealPopulation) / truePopulation;
    }


    /*
    COMPETITIVENESS:
    1.0 - margin of victory
    */
    public static double calculateCompetitiveness(NeighborDistrictWrapper d, ElectionCategory election) {
        int gv = d.getVotes().get(election).getVotes().get(PartyCategory.REPUBLICAN).intValue();
        int dv = d.getVotes().get(election).getVotes().get(PartyCategory.DEMOCRATIC).intValue();
        if ((gv + dv) == 0) {
        	return 0;
        }
        return 1.0 - (Math.abs(gv - dv) / (gv + dv));
    }


    /*
        GERRYMANDER_REPUBLICAN:
        Partisan fairness, but always working in the GOP's favor
    */
    public static double calculateGerryManderGOP(NeighborDistrictWrapper d, ElectionCategory election) {
        int gv = d.getVotes().get(election).getVotes().get(PartyCategory.REPUBLICAN).intValue();
        int dv = d.getVotes().get(election).getVotes().get(PartyCategory.DEMOCRATIC).intValue();
        int tv = gv + dv;
        int margin = gv - dv;
        if (tv == 0) {
            return 1.0;
        }
        int win_v = Math.max(gv, dv);
        int loss_v = Math.min(gv, dv);
        int inefficient_V;
        if (margin > 0) {
            inefficient_V = win_v - loss_v;
        } else {
            inefficient_V = loss_v;
        }
        return 1.0 - ((inefficient_V * 1.0) / tv);
    }

    //calculate square error of population, normalized to 0,1
    public static double calculatePopulationHomogeneity(NeighborDistrictWrapper district, Map<Integer, NeighborDistrictWrapper> districts, ElectionCategory election) {
        if (district.getPrecincts().size() == 0)
            return 0;
        double sum = 0;
        for (Integer p : district.getPrecincts()) {
        	sum += districts.get(p).getPopulation(election);
        }
        final double mean = sum / district.getPrecincts().size();
        double sqError = district.getPrecincts()
                .stream().mapToDouble(
                        (precinct) -> (Math.pow(districts.get(precinct).getPopulation(election) - mean, 2))
                ).sum();
        sqError /= (district.getPrecincts().size());
        double averagePopulation = 2000;


        return Math.tanh(sqError / (averagePopulation * 200));
    }


    /*
            GERRYMANDER_DEMOCRAT:
            Partisan fairness, but always working in the DNC's favor
    */
    public static double calculateGerryManderDEM(NeighborDistrictWrapper d, ElectionCategory election) {
    	int gv = d.getVotes().get(election).getVotes().get(PartyCategory.REPUBLICAN).intValue();
        int dv = d.getVotes().get(election).getVotes().get(PartyCategory.DEMOCRATIC).intValue();
        int tv = gv + dv;
        int margin = dv - gv;
        if (tv == 0) {
            return 1.0;
        }
        int win_v = Math.max(gv, dv);
        int loss_v = Math.min(gv, dv);
        int inefficient_V;
        if (margin > 0) {
            inefficient_V = win_v - loss_v;
        } else {
            inefficient_V = loss_v;
        }
        return 1.0 - ((inefficient_V * 1.0) / tv);
    }

}
