package de.mdelab.predictor.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
	public  LinkedHashMap<String, Cycle> loadCyclesFromFile(String path, String sourceFileName){

		ArrayList<String> buffer = ReadWriteFile.readToBuffer(path, sourceFileName);

		//Skip header
		String header[] = buffer.get(0).split(",");
		buffer.remove(0);

		LinkedHashMap<String, Cycle> cycleMap = new LinkedHashMap<String, Cycle>();

		for(String line: buffer){
			String lineArray[] = line.split(",");
			String cycleKey = lineArray[7];

			Cycle cycle = cycleMap.get(cycleKey);
			if(cycle==null){
				cycle = new Cycle(cycleKey);
			}
			cycle.addEntry(lineArray);
			cycleMap.put(cycleKey, cycle);
		}
		return cycleMap;
	}

	/**
	 * 
	 * @param cycleMap
	 * @return the cycleMap with the metrics calculated for each cycle (see the HashMap inside the Class Cycle)
	 */
	public LinkedHashMap<String, Cycle>  computeMetrics(LinkedHashMap<String, Cycle>  cycleMap){

		/** For each all cycle maps compute the ranking metrics */
		for(Map.Entry<String, Cycle> entry : cycleMap.entrySet()){
			Cycle cycle = entry.getValue();
					
			/** for each cycle map compute all metrics*/
			for(String metricType:RankingMetric.metricsList){
				RankingMetric metric = RankingMetric.buildInstance(metricType);
				Double value = metric.compute(cycle.optimalRank, cycle.predictedRank);
				cycle.metricTable.put(metricType, value);
			}
		}
		return cycleMap;
	}


	public void saveCycleMapToFile(LinkedHashMap<String, Cycle> cycleMap,String path, String destFileName){
		
		ArrayList<String> fileContent = new ArrayList<String>();
		
		Cycle cycle = cycleMap.entrySet().iterator().next().getValue();
		fileContent.add(cycle.printHeader());
		
		/** For each all cycle maps compute the ranking metrics */
		for(Map.Entry<String, Cycle> entry : cycleMap.entrySet()){
			cycle = entry.getValue();
			fileContent.add(cycle.printMetricValues());
		}
		
		ReadWriteFile.writeBackToBuffer(fileContent, path, destFileName);
	}

	public static void main(String args[]){
		 
		EvaluatorController controller =  new EvaluatorController();
		String path = "C://Users//chris//OneDrive//Documentos//GitHub//ML_SelfHealingUtility//data//Validation//5-25-50//";
		String outcomeFile = "Saturating-6-Metric.csv";
		String inputFile = "Saturating-6.csv";
		LinkedHashMap<String, Cycle> cycleMap = controller.loadCyclesFromFile(path, inputFile);		
		cycleMap = controller.computeMetrics(cycleMap);
		controller.saveCycleMapToFile(cycleMap,path,outcomeFile);
	}
	
}
