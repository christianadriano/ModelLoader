package de.mdelab.predictor.evaluator;

import java.util.LinkedHashMap;

public abstract class RankingMetric {

	public static final String JaccardCoefficient = "JaccardCoefficient";
	public static final String DiscountedCumulativeGain = "DiscountedCumulativeGain";
	public static final String KendallTauCorrelation = "KendallTauCorrelation";
	
	public static final String metricsList[] = {JaccardCoefficient,DiscountedCumulativeGain,KendallTauCorrelation};
	
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
	public static RankingMetric buildInstance(String metricType){
		RankingMetric metric = null;
		if (metricType.matches(JaccardCoefficient))
				metric = new JaccardCoefficient();
				else
					if (metricType.matches(DiscountedCumulativeGain))
						metric = new DiscountedCumulativeGain();
					else
						if(metricType.matches(KendallTauCorrelation))
							metric = new KendallTauCorrelation();	
		return metric;
	}
	
	
}
