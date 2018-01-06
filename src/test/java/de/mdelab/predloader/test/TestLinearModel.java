package de.mdelab.predloader.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import de.mdelab.predloader.DataLoader;
import de.mdelab.predloader.RegressionModel;

public class TestLinearModel {

	private static String path = "..\\resources";
	private static String fileName = "Linear10K.csv";
	
	@Test
	public void testHardcoded() {
		
		Float allowedDeviation = new Float(0.1);
		
		
		RegressionModel lrm = new de.mdelab.predloader.RegressionModel(
				RegressionModel.path, RegressionModel.linear_pmml_fileName);
			try {
				lrm.loadModel();
				System.out.println("Follow the model features:");
				lrm.showModelFeatures();
				
				System.out.println("Allow deviation from actual value= "+allowedDeviation.toString());
				
				System.out.println("Running test");
				Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
				userArguments.put("REQUIRED_INTERFACE", new Double(1) );
				userArguments.put("PROVIDED_INTERFACE", new Double(1) );
				userArguments.put("CRITICALITY", new Double(7) );
				userArguments.put("RELIABILITY", new Double(0.5) );
				userArguments.put("PMax", new Double(11.61201) );
				userArguments.put("alpha", new Double(170.6764) );
				
				Double actual = new Double(7);
				
				Float predicted = lrm.pointPrediction(userArguments);
				System.out.println("Predicted="+predicted+", Actual="+actual);
				 
				Double min = actual - allowedDeviation;
				Double max = actual + allowedDeviation;				
				assertTrue("Prediction" +predicted+ " is out of range for actual = "+ actual, predicted>= min && predicted <= max);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	
	
	//@Test
	public void testAutomated() {
		
		DataLoader dataLoader = new DataLoader(this.path,this.fileName);
		ArrayList<LinkedHashMap<String,String>> tupleMapList = dataLoader.load();
		
		Float allowedDeviation = new Float(10);
		
		
		RegressionModel lrm = new de.mdelab.predloader.RegressionModel(RegressionModel.path, RegressionModel.linear_pmml_fileName);
			try {
				
				lrm.loadModel();
				System.out.println("Follow the model features:");
				lrm.showModelFeatures();
				
				System.out.println("Allow deviation from actual value= "+allowedDeviation.toString());
				
				System.out.println("Running test");
				Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
				userArguments.put("CRITICALITY", new Double(18) );
				userArguments.put("CONNECTIVITY", new Double(4) );
				userArguments.put("RELIABILITY", new Double(0.5) );
				
				Double actual = new Double(79.2);
				
				Float predicted = lrm.pointPrediction(userArguments);
				System.out.println("Predicted="+predicted+", Actual="+actual);
				 
				Double min = actual - allowedDeviation;
				Double max = actual + allowedDeviation;				
				assertTrue("Prediction" +predicted+ " is out of range for actual = "+ actual, predicted>= min && predicted <= max);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	
	
	}

/* Data for testing
FAILURE_NAME	AFFECTED_COMPONENT	CRITICALITY	UTILITY_INCREASE	CONNECTIVITY	RELIABILITY	IMPORTANCE	PROVIDED_INTERFACE	REQUIRED_INTERFACE	ADT	RULE	 PMax	alpha	REPLICA	REQUEST
CF3				Recomm. Item Filter		9			9				2				0.5			12			1					1					1.208279251	HwRedeployComponent	9.853347561	152.5462346	8	271
CF3	 			Persistence Service		7			17.5			5				0.5			8			5					0					1.142992741	HwRedeployComponent	14.73108942	93.8030449	9	201
CF1			 	Comment Item Filter		7			7				2				0.5			12			1					1					1.366570528	RestartComponent	13.4881317	158.2886289	13	495
CF2	 			Region Item Filter		6			6				2				0.5			8			1					1					1.34484166	HwRedeployComponent	17.16943865	93.91547799	42	983
*/


