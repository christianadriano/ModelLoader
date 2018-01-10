package de.mdelab.predictor.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import de.mdelab.predictor.loader.ReadWriteFile;

/**
 * Compute different ranking metrics for a set of cycles made available
 * @author Christian M. Adriano
 *
 */
public class EvaluatorController {

	/**
	 * 
	 * @param metricType see types in RankingMetric class
	 * @param path
	 * @param sourceFileName
	 * @return the cycleMap with the data loaded
	 */
	public  HashMap<Integer,Cycle> loadCyclesFromFile(String path, String sourceFileName){
		
		ArrayList<String> buffer = ReadWriteFile.readToBuffer(path, sourceFileName);
		
		//Skip header
		String header[] = buffer.get(0).split(",");
		buffer.remove(0);
		
		HashMap<Integer, Cycle> cycleMap = new HashMap<Integer, Cycle>();
		
		for(String line: buffer){
			String lineArray[] = line.split(",");
			Integer cycleKey = new Integer(lineArray[7]).intValue();
		
			Cycle cycle = cycleMap.get(cycleKey);
			if(cycle==null){
				cycle = new Cycle(cycleKey);
			}
			
			cycleMap.put(cycleKey, cycle);
		}
		
		return cycleMap;
	}
	
	/**
	 * 
	 * @param cycleMap
	 * @return the cycleMap with the metrics calculated for each cycle (see the HashMap inside the Class Cycle)
	 */
	public HashMap<Integer,Cycle> runMetrics(HashMap<Integer,Cycle> cycleMap){
		
		RankingMetric metric = RankingMetric.buildInstance(metricType);
		
		
		
		return cycleMap;
		
	}
}
