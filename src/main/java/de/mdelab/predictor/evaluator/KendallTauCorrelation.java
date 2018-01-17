package de.mdelab.predictor.evaluator;

import org.apache.commons.math3.stat.correlation.*;

import java.util.ArrayList;
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

	//Array representations of the ranks of components
	double rankOne[];
	double rankTwo[];
	
	@Override
	public Double compute(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo) {
		
		this.convertToArrays(mapOne, mapTwo);
	
		Double correlation=0.0;
		
		KendallsCorrelation kendall = new KendallsCorrelation();
		try{
			//System.out.println("rankOne: "+rankOne.toString());
			//System.out.println("rankOne: "+rankTwo.toString());
			correlation = kendall.correlation(rankOne,rankTwo);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return correlation;
	}
	
	public void convertToArrays(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo){
		
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
		
		rankOne = new double[rankOneArray.size()];
		rankTwo = new double[rankOneArray.size()];
		
		for(int i=0;i<rankOneArray.size();i++){
			rankOne[i] = rankOneArray.get(i).doubleValue();
			rankTwo[i] = rankTwoArray.get(i).doubleValue();		
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
	 

//--------------------------------------------------------------------------------------	 
	 
		public Double manualCompute(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo) {
			
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
	//private Double computeNumeratorKendallTau(LinkedHashMap<Integer,String> mapOne, LinkedHashMap<Integer,String> mapTwo){
		
//		double numerator = 0.0;
//		
//		Iterator<Integer> oneIter = mapOne.keySet().iterator();
//		Integer keyOne = oneIter
//		mapOne.
//		
//		for(Map.Entry<Integer,String> entry:mapOne.entrySet()){
//			String value = (String) entry.getValue();
//			Integer key = (Integer) entry.getKey();
//			String value2 = mapTwo.get(key);
//			if(value.matches(value2))
//				concordantPairs++;
//		}
//		
//		numer := 0
//				for i:=2..N do
//				    for j:=1..(i-1) do
//				        numer := numer + sign(x[i] - x[j]) * sign(y[i] - y[j])
//				return numer
//	}
//
//	ArrayList<Integer> convertToArrayList(LinkedHashMap<Integer,String> map){
//		
//		ArrayList<Integer> list = new ArrayList<Integer>();
//	
//		for(Map.Entry<Integer,String> entry:mapOne.entrySet()){
//			String value = (String) entry.getValue();
//			Integer key = (Integer) entry.getKey();
//			
//	}
	
	
}
