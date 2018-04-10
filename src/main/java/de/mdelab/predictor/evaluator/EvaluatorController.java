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

		/** For each all cycle maps write the metrics values to a line in a file */
		for(Map.Entry<String, Cycle> entry : cycleMap.entrySet()){
			cycle = entry.getValue();
			fileContent.add(cycle.printMetricValues());
		}
		ReadWriteFile.writeBackToBuffer(fileContent, path, destFileName);
	}


	public void run(String datasetSize,String cycleSize, String methodType){
		
		String path = "C://Users//Chris//Documents//GitHub//ML_SelfHealingUtility//data//2nd_Experiment//Ranking//" + datasetSize+"//"+cycleSize+"//"+methodType+"//";
		String names[] = {"Discontinuous"};//{"Linear","Saturating","Discontinuous","Combined"};
		
		for(String name: names){
			String outcomeFile = name+"_"+datasetSize+"_"+cycleSize+"-Metrics.csv";	
			String inputFile = name+".csv";			
			LinkedHashMap<String, Cycle> cycleMap = this.loadCyclesFromFile(path, inputFile);		
			cycleMap = this.computeMetrics(cycleMap);
			this.saveCycleMapToFile(cycleMap,path,outcomeFile);
		}
	}

	public static void main(String args[]){

		EvaluatorController controller =  new EvaluatorController();
		String datasetSizes[] = {"1K","3K","9K"};
		String cycleSizes[] = {"5-25-50","FullTrace"};
		String methodType[] = {"XGB","GBM","RF"};
	//	for(int i=0;i<datasetSizes.length;i++){
	//		for(int j=0;j<cycleSizes.length;j++){
				controller.run(datasetSizes[2],cycleSizes[0],methodType[2]);
	//		}
		//}
	}

}
