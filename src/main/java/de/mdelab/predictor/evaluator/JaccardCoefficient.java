package de.mdelab.predictor.evaluator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Compute the Tanimoto coefficient between two lists.
 * 
 *  
 * @author Christian M. Adriano
 *
 */
public class JaccardCoefficient extends RankingMetric{

	@Override
	public Double compute(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo) {
		
		if(mapOne.size()!=mapTwo.size())
			return null;
		
		double union = mapOne.size();
		double intersecction = 0;
		
		for(Map.Entry<Integer,String> entry:mapOne.entrySet()){
			String value = (String) entry.getValue();
			Integer key = (Integer) entry.getKey();
			String value2 = mapTwo.get(key);
			if(value.matches(value2))
				intersecction++;
		}
		
		return intersecction/union;
	}	
	
}
