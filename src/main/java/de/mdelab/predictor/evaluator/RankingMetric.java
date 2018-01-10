package de.mdelab.predictor.evaluator;

import java.util.LinkedHashMap;

public abstract class RankingMetric {

	public static final Integer JaccardDistance = 1;
	public static final Integer DiscountedCumulativeGain = 2;
	public static final Integer KendallTauCorrelation = 3;
	
	public static final Integer metricsList[] = {1,2,3};
	
	/**
	 * 
	 * @param mapOne HashMap with the ranking position as key and name of the element as String
	 * @param mapTwo HashMap with the ranking position as key and name of the element as String
	 * @return
	 */
	public abstract Double compute(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo);
	
	/**
	 * 
	 * @param metricType one of the three types available in this abstract class
	 */
	public static RankingMetric buildInstance(Integer metricType){
		RankingMetric metric = null;
		switch (metricType){
			case 1: metric = new JaccardCoefficient();
			case 2: metric = new DiscountedCumulativeGain();
			case 3: metric = new KendallTauCoefficient();
		}
		
		return metric;
	}
	
	
}
