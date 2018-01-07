package de.mdelab.predloader.test;

import static org.junit.Assert.*;

import org.jpmml.evaluator.InputField;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.mdelab.predloader.DataLoader;
import de.mdelab.predloader.RegressionModel;

public class TestLinearModel {

	private static String path = "C://Users//chris//MachineLearning//ModelLoader//src//test//resources//";
	private static String fileName = "Linear10K.csv";

	@Test
	public void testHardcoded() {

		Float allowedPercentDeviation = new Float(10);


		RegressionModel lrm = new de.mdelab.predloader.RegressionModel(
				RegressionModel.path, RegressionModel.linear_pmml_fileName);
		try {
			lrm.loadModel();
			System.out.println("Follow the model features:");
			lrm.showModelFeatures();

			System.out.println("Allow deviation from actual value= "+allowedPercentDeviation.toString()+"%");

			System.out.println("Running test");
			Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
			userArguments.put("REQUIRED_INTERFACE", new Double(1) );
			userArguments.put("PROVIDED_INTERFACE", new Double(0) );
			userArguments.put("CONNECTIVITY", new Double(5) );
			userArguments.put("CRITICALITY", new Double(10) );
			userArguments.put("RELIABILITY", new Double(0.5) );
			userArguments.put("PMax", new Double(7.680216) );
			userArguments.put("alpha", new Double(189.2081) );
			userArguments.put("ADT", new Double(1.28311) );
			userArguments.put("IMPORTANCE", new Double(10) );

			Double actual = new Double(25);

			Float predicted = lrm.pointPrediction(userArguments);
			System.out.println("Predicted="+predicted+", Actual="+actual);

			Double min = actual - actual*allowedPercentDeviation/100;
			Double max = actual + actual*allowedPercentDeviation/100;				
			assertTrue("Prediction" +predicted+ " is out of range for actual = "+ actual, predicted>= min && predicted <= max);

		} catch (Exception e) {
			e.printStackTrace();
//			Predicted=24.87759, Actual=25.0
		}
	}

	/* Data for testing
	 CRITICALITY RELIABILITY CONNECTIVITY IMPORTANCE PROVIDED_INTERFACE REQUIRED_INTERFACE REPLICA
	     10         0.5            5         10                  0                  5      14
	     REQUEST     ADT     PMax    alpha UTILITY_INCREASE
	         654 1.28311 7.680216 189.2081               25
	         
	         */

	//@Test
	public void testAutomated() {

		DataLoader dataLoader = new DataLoader(this.path,this.fileName);
		ArrayList<LinkedHashMap<String,String>> tupleMapList = dataLoader.load();

		Float allowedDeviation = new Float(0.15); //15% error
		System.out.println("Allow deviation from actual value= "+allowedDeviation.toString());		

		RegressionModel lrm = new de.mdelab.predloader.RegressionModel(RegressionModel.path, RegressionModel.linear_pmml_fileName);
		System.out.println("Running test");
		try {
			lrm.loadModel();
			System.out.println("Follow the model features:");
			lrm.showModelFeatures();

			List<InputField> requiredModelFeatures = lrm.getActiveFields();
			
			//Read each line and run a test.
			//for(LinkedHashMap<String,String> map: tupleMapList){
			for(int i=0;i< 2;i++){
				LinkedHashMap<String,String> map = tupleMapList.get(i);
				Double actual = new Double(map.get("UTILITY_INCREASE"));
				map.remove("UTILITY_INCREASE");
				
				Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
				for(InputField field: requiredModelFeatures){
					System.out.println("input field:" + field.getName().getValue());
					System.out.println("value: "+ map.get(field.getName()));
					userArguments.put(field.getName().getValue(), new Double(map.get(field.getName())) );
				}
				
				

				Float predicted = lrm.pointPrediction(userArguments);
				System.out.println("Predicted="+predicted+", Actual="+actual);

				Double min = actual - allowedDeviation;
				Double max = actual + allowedDeviation;				
				assertTrue("Prediction" +predicted+ " is out of range for actual = "+ actual, predicted>= min && predicted <= max);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}




