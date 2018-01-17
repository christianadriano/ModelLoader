package de.mdelab.predictor.evaluator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * For each mismatch computes the difference between the actual and predicted ranking for each component.
 * Take the sum of the absolute value of these differences and divide by the maximum possible 
 * difference between two rankings of same size.
 *  
 * @author Christian M. Adriano
 *
 */
public class MismatchDistanceCoefficient extends RankingMetric{

	@Override
	public Double compute(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo) {

		if(mapOne.size()!=mapTwo.size())
			return null;

		RankingLists lists = new RankingLists(mapOne,mapTwo);
		double[] mistmatchDistances = lists.computeMismatchDistances();
		double sumOfDistances = sumArray(mistmatchDistances);

		double maximumDifference = computeMaximumDistance(mapOne.size());
	
		return 1- (sumOfDistances/maximumDifference);
	}	

	private double sumArray(double[] mistmatchDistances) {
		double total = 0.0;
		for(double item:mistmatchDistances){
			total = total + Math.abs(item);
		}
		return total;
	}

	/** 
	 * The maximum distance is when the two rankings are completely reversed, i.e., when
	 * the correlation between the two lists is -1.
	 * 
	 * @param size
	 * @return
	 */
	public double computeMaximumDistance(int size) {

		//This is because the low bit will always be set for an odd number.
		if ( (size & 1) == 0 ) {//even number

			return (size*size/2);
		} 
		else {//odd number 
			return ((size*size)-1)/2;
		}
	}

}
