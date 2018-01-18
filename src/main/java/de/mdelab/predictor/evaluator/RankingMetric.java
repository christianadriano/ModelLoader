package de.mdelab.predictor.evaluator;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class RankingMetric {

	public static final String JaccardCoefficient = "JaccardCoefficient";
	public static final String MismatchDistanceCoefficient = "MismatchDistanceCoefficient";
	public static final String KendallTauCorrelation = "KendallTauCorrelation";
	public static final String MismatchPositionCoefficient = "MismatchPositionCoefficient";
	public static final String DiscountedCumulativeGain = "DiscountedCumulativeGain";
	
	
	public static final String metricsList[] = {JaccardCoefficient,MismatchDistanceCoefficient,KendallTauCorrelation,MismatchPositionCoefficient,DiscountedCumulativeGain};
	
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
					if (metricType.matches(MismatchDistanceCoefficient))
						metric = new MismatchDistanceCoefficient();
					else
						if(metricType.matches(KendallTauCorrelation))
							metric = new KendallTauCorrelation();	
						else
							if(metricType.matches(MismatchPositionCoefficient))
								metric = new MismatchPositionCoefficient();	
							else
								if(metricType.matches(DiscountedCumulativeGain))
									metric = new DiscountedCumulativeGain();	
		return metric;
	}

	
	
	
}
