package de.mdelab.predictor.evaluator;

import java.util.HashMap;
import java.util.LinkedHashMap;

/** 
 * A Cycle consists of a set of components ranked. 
 * For each cycle we can compute a set of Ranking metrics.
 * 
 * @author Christian M. Adriano
 *
 */
public class Cycle{
	
	Integer cycle;
	
	/** Metrics computed for this cycle
	 * The key must come from one of the constants declared at RankingMetric class
	 * */
	HashMap<Integer,Double> metricTable = new HashMap<Integer,Double>();

	public Cycle(Integer cycle){ 
		this.cycle=cycle;
	}
	
	/** Integer is the rank value, String is unique ID of the component instance */
	LinkedHashMap<Integer,String> predictedRank = new LinkedHashMap<Integer,String>();
	LinkedHashMap<Integer,String> optimalRank = new LinkedHashMap<Integer,String>();
	
	/** String is unique ID of the component instance, Integer is the rank value */
	LinkedHashMap<String,Integer> predictedComponentRank = new LinkedHashMap<String,Integer>();
	LinkedHashMap<String,Integer> optimalComponentRank = new LinkedHashMap<String,Integer>();
	
	public void addEntry(String[] line){
		predictedRank.put(new Integer(line[0]), line[2]);
		predictedComponentRank.put(line[2],new Integer(line[0]));
		
		optimalRank.put(new Integer(line[8]), line[10]);
		optimalComponentRank.put(line[10],new Integer(line[8]));
	}
	
	
	
	
	
}