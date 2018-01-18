package de.mdelab.predictor.evaluator;

import java.util.LinkedHashMap;

/**
 * Compute normalized difference in Discounter Cumulative Gain (DCG) of two sorted lists.
 * https://en.wikipedia.org/wiki/Discounted_cumulative_gain
 *
 *  The DCG which is the total gain accumulated by all items in a list. This total gain is 
 *  obtained by the sum of each component relevance discounted by the position of the component in the list.
 *  The lower the component is, larger the discount factor. Each component will have
 *  
 *  The current class implements a discount factor is the log base 2 of the relevance of the component, 
 *  i.e., DCG[i] = R[i]/log2(i). i = position (ranking) of the component in the list.
 *  R[i] =  relevance of component i.
 *  
 *  The relevance of the component is given by the position of the component at the actual ranking.
 *
 *	Example: 
 *	Consider a component that has actual rank position = 3 and was predicted to ranking at 4.
 *	The relevance of this component is 3 and the position "i" is 4. 
 *	This component  will have discount factor of log2(4) = 2 
 *  Therefore, the DCG of this component will be 3/2 = 1.5   
 * 
 * @author Christian M. Adriano
 *
 */
public class DiscountedCumulativeGain extends RankingMetric{

	@Override
	public Double compute(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo) {


		if(mapOne.size()!=mapTwo.size())
			return null;

		RankingLists lists = new RankingLists(mapOne,mapTwo);
		double[] relevance = lists.actual;

		double[] predictedDiscounted = computeDiscountedValues(lists.predicted,relevance);
		double[] actualDiscounted = computeDiscountedValues(lists.actual,relevance);

		double DCG_Total_predicted = sumArray(predictedDiscounted);
		double DCG_Total_actual = sumArray(actualDiscounted);
		return (1 - Math.abs(DCG_Total_actual-DCG_Total_predicted)/DCG_Total_actual);
	}

	private double[]computeDiscountedValues(double[] list, double[] relevance) {

		double[] DCG_list = new double[list.length];

		for(int i=0;i<list.length;i++){

			if(relevance[i]!=list[i]){//discount only if the component was mismatched
				DCG_list[i] = relevance[i] / Math.log(list[i]+1)/Math.log(2);
			}
			else{
				DCG_list[i] = relevance[i];
			}
		}
		return DCG_list;
	}


	private double sumArray(double[] mistmatchDistances) {
		double total = 0.0;
		for(double item:mistmatchDistances){
			total = total + Math.abs(item);
		}
		return total;
	}


	public static void main(String args[]){
		System.out.println("log natural:"+Math.log(2));
		System.out.println("log base 2:"+Math.log(5)/Math.log(2));
		System.out.println("log base 10:"+Math.log10(2));
	}


}
