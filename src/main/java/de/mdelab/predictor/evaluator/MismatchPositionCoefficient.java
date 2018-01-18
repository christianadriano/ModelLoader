package de.mdelab.predictor.evaluator;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * For each mismatch adds the actual position.
 * Take the sum of the actual positions of components that were incorrectly ranked (e.g., actual[i] != predicted[i]
 * Divide this sum of the all the positions in the rank (which would be the maximum size of the mismatch for this
 * metric). 
 * @author Christian M. Adriano
 *
 */
public class MismatchPositionCoefficient extends RankingMetric{

	@Override
	public Double compute(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo) {

		if(mapOne.size()!=mapTwo.size())
			return null;

		RankingLists lists = new RankingLists(mapOne,mapTwo);
		double sumOfDistances = lists.sumOfMismatchedPositions();

		double maximumDifference = computeMaximum(mapOne.size());
	
		return 1- (sumOfDistances/maximumDifference);
	}	

	
	/** 
	 * The maximum distance is when the two rankings are completely reversed, i.e., when
	 * the correlation between the two lists is -1.
	 * 
	 * @param size
	 * @return
	 */
	public double computeMaximum(int size) {

		return (size*(size+1)/2);
	}


}
