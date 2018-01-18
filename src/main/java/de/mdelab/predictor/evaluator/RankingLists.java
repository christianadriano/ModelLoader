package de.mdelab.predictor.evaluator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Keep information about the two lists being compared by a RankingMetric
 * @author Christian M. Adriano
 *
 */
public class RankingLists {

	public double[] actual;
	public double[] predicted;

	public RankingLists(LinkedHashMap<Integer, String> mapOne, LinkedHashMap<Integer, String> mapTwo) {
		convertToArrays(mapOne,mapTwo);
	}

	/**
	 * The position i in the array corresponds to a unique component i. The cell actual[i]  contains the rank 
	 * value of component i, while the predicted rank value for component i is at predicted[i] cell.  
	 * Therefore, a mismatch consists of actual[i] != predicted[i].
	 * 
	 * @param mapOne
	 * @param mapTwo
	 */
	private void convertToArrays(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo){

		ArrayList<Double> rankOneArray = initialize (new ArrayList<Double>(),mapOne.size());
		ArrayList<Double> rankTwoArray = initialize (new ArrayList<Double>(),mapOne.size());

		/** For each all cycle maps compute the ranking metrics */
		for(Map.Entry<Integer, String> entry : mapOne.entrySet()){
			String componentName = entry.getValue();
			Integer componentRank = entry.getKey();
			rankOneArray.set(componentRank-1, new Double(componentRank));

			Integer predictedComponentRank = (Integer) getKeyFromValue(mapTwo,componentName);
			if(predictedComponentRank==null){ 
				predictedComponentRank=0;
				System.out.println("not found:"+ componentName);
			}
			rankTwoArray.set(componentRank-1, new Double(predictedComponentRank));
		}

		actual = new double[rankOneArray.size()];
		predicted = new double[rankOneArray.size()];

		for(int i=0;i<rankOneArray.size();i++){
			actual[i] = rankOneArray.get(i).doubleValue();
			predicted[i] = rankTwoArray.get(i).doubleValue();		
		}
	}

	private ArrayList<Double> initialize(ArrayList<Double> list, int size){

		for(int i=0; i<size;i++ ){
			list.add(0.0);
		}
		return list;
	}


	private static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
	

	public double[] computeMismatchDistances() {
		
		double[] mismatchDistances = new double[actual.length];
		
		for(int i=0;i<actual.length;i++){
			mismatchDistances[i] = actual[i]-predicted[i];
		}
		return mismatchDistances;
	}

	/** Takes the sum of the positions minus actual */
     public double sumOfMismatchedPositions() {
		
		double sumMismatchedPositions = 0.0;
		
		for(int i=0;i<actual.length;i++){
			if(actual[i]!=predicted[i]){
				int weight = actual.length-(i+1);
				sumMismatchedPositions= sumMismatchedPositions+ weight;
			}
		}
		return sumMismatchedPositions;
	}
	
	

}
