package de.mdelab.predictor.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import de.mdelab.predictor.loader.ReadWriteFile;

/**
 * Compute different metrics
 * @author Christian M. Adriano
 *
 */
public class EvaluatorController {

	public class Cycle{
		String cycle;
		LinkedHashMap<Integer,String> predictedMap;
		LinkedHashMap<Integer,String> optimalMap;
	}
	
	
	public void run(Integer metricType, String path, String sourceFileName){
		RankingMetric metric = RankingMetric.buildInstance(metricType);
		ArrayList<String> buffer = ReadWriteFile.readToBuffer(path, sourceFileName);
		
		//Skip header
		String header[] = buffer.get(0).split(",");
		buffer.remove(0);
		
		HashMap<String, Cycle> cycleMap = new HashMap<String, Cycle>();
		
		String lineArray[] =  buffer.get(1).split(",");
		int cycle = new Integer(lineArray[7]).intValue();
		int previousCycle=cycle;
		
		for(String line: buffer){
					
			while
				
				String lineArray[] =  line.split(",");
		}
		
	}
}
