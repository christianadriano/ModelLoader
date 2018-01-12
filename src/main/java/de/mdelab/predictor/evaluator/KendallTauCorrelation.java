package de.mdelab.predictor.evaluator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Computes the Kendall tau correlation between two rankings
 * https://en.wikipedia.org/wiki/Kendall_rank_correlation_coefficient
 * 
 * @author Christian M. Adriano
 *
 */
public class KendallTauCorrelation extends RankingMetric {

	@Override
	public Double compute(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo) {
		
		if(mapOne.size()!=mapTwo.size())
			return null;
		
		double size = mapOne.size();
		double concordantPairs = 0;
				
		for(Map.Entry<Integer,String> entry:mapOne.entrySet()){
			String value = (String) entry.getValue();
			Integer key = (Integer) entry.getKey();
			String value2 = mapTwo.get(key);
			if(value.matches(value2))
				concordantPairs++;
		}
		
		double discordantPairs = size - concordantPairs;
		double kendallTau = (concordantPairs - discordantPairs)/(size*(size-1)/2);
		
		return kendallTau;
	}
	
	/**
	 * concordant pairs minus discordant pairs.
	 * See example here: http://www.statisticshowto.com/kendalls-tau/
	 * @param mapOne
	 * @param mapTwo
	 * @return 
	 */
	private Double computeNumeratorKendallTau(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo){
		
		double numerator = 0.0;
		
		Iterator<Integer> oneIter = mapOne.keySet().iterator();
		Integer keyOne = oneIter
		mapOne.
		
		for(Map.Entry<Integer,String> entry:mapOne.entrySet()){
			String value = (String) entry.getValue();
			Integer key = (Integer) entry.getKey();
			String value2 = mapTwo.get(key);
			if(value.matches(value2))
				concordantPairs++;
		}
		
		numer := 0
				for i:=2..N do
				    for j:=1..(i-1) do
				        numer := numer + sign(x[i] - x[j]) * sign(y[i] - y[j])
				return numer
	}

	ArrayList<Integer> convertToArrayList(LinkedHashMap<Integer,String> map){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
	
		for(Map.Entry<Integer,String> entry:mapOne.entrySet()){
			String value = (String) entry.getValue();
			Integer key = (Integer) entry.getKey();
			
	}
	
	
}
