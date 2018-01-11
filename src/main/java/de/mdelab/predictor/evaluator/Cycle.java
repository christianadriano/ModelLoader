package de.mdelab.predictor.evaluator;

import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * A Cycle consists of a set of components ranked. 
 * For each cycle we can compute a set of Ranking metrics.
 * 
 * @author Christian M. Adriano
 *
 */
public class Cycle{

	String id;
	Integer size;

	/** Metrics computed for this cycle
	 * The key must come from one of the constants declared at RankingMetric class
	 * */
	LinkedHashMap<String,Double> metricTable = new LinkedHashMap<String,Double>();

	public Cycle(String id){ 
		this.id = id;
	}

	/** Integer is the rank value, String is unique ID of the component instance */
	LinkedHashMap<Integer,String> predictedRank = new LinkedHashMap<Integer,String>();
	LinkedHashMap<Integer,String> optimalRank = new LinkedHashMap<Integer,String>();

	/** String is unique ID of the component instance, Integer is the rank value */
	LinkedHashMap<String,Integer> predictedComponentRank = new LinkedHashMap<String,Integer>();
	LinkedHashMap<String,Integer> optimalComponentRank = new LinkedHashMap<String,Integer>();

	public void addEntry(String[] line){
		
		size = new Integer(line[8]);
		
		predictedRank.put(new Integer(line[0]), line[2]);
		predictedComponentRank.put(line[2],new Integer(line[0]));

		optimalRank.put(new Integer(line[9]), line[11]);
		optimalComponentRank.put(line[11],new Integer(line[9]));
	}

	public String printHeader(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("Cycle id, Cycle size,");
		for(Map.Entry<String, Double> entry: metricTable.entrySet()){
			buffer.append(entry.getKey());
			buffer.append(",");
		}
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		return buffer.toString();
	}

	/**
	 * Produces a line with the metric values separated by comma
	 * @return
	 */
	public String printMetricValues() {

		StringBuffer buffer = new StringBuffer();
		
		buffer.append(id.toString());
		buffer.append(",");
		buffer.append(size.toString());
		buffer.append(",");
		
		for(Map.Entry<String, Double> entry: metricTable.entrySet()){
			Double value = entry.getValue();
			if(value!=null){
				buffer.append(value.toString());
				buffer.append(",");
			}
		}
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		return buffer.toString();
	}




}