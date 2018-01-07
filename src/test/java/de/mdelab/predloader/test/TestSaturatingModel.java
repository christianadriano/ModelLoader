package de.mdelab.predloader.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import de.mdelab.predloader.RegressionModel;

public class TestSaturatingModel {

	@Test
	public void test() {
		
		Double allowedDeviation = new Double(10);
		
		
		Float allowedPercentDeviation = new Float(10);


		RegressionModel lrm = new de.mdelab.predloader.RegressionModel(
				RegressionModel.path, RegressionModel.saturating_pmml_fileName);
		try {
			lrm.loadModel();
			System.out.println("Follow the model features:");
			lrm.showModelFeatures();

			System.out.println("Allow deviation from actual value= "+allowedPercentDeviation.toString()+"%");

			System.out.println("Running test");
			Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
			
				userArguments.put("CRITICALITY", new Double(8) );
				userArguments.put("CONNECTIVITY", new Double(4) );
				userArguments.put("IMPORTANCE", new Double(13) );
				userArguments.put("REQUIRED_INTERFACE", new Double(4) );
				userArguments.put("PROVIDED_INTERFACE", new Double(0) );
				userArguments.put("RELIABILITY", new Double(0.5) );
				userArguments.put("REPLICA", new Double(61) );
				userArguments.put("REQUEST", new Double(8456) );
				userArguments.put("PMax", new Double(17.63671044) );
				userArguments.put("alpha", new Double(128.1755686) );
				
				Double actual = 205.4556905;
				
				Float predicted = lrm.pointPrediction(userArguments);
				System.out.println("Predicted="+predicted+", Actual="+actual);

				Double min = actual - actual*allowedPercentDeviation/100;
				Double max = actual + actual*allowedPercentDeviation/100;				
				assertTrue("Prediction" +predicted+ " is out of range for actual = "+ actual, predicted>= min && predicted <= max);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	}

/*
 * FAILURE_NAME	UTILITY_DROP	AFFECTED_COMPONENT	CRITICALITY	UTILITY_INCREASE	COSTS	CONNECTIVITY	RELIABILITY	IMPORTANCE	PROVIDED_INTERFACE	REQUIRED_INTERFACE	ADT	RULE	 PMax	alpha	REPLICA	REQUEST
CF5	25.59375349	User Management Service	8	205.4556905	0.5	4	0.5	13	0	4	1.034634314	AddReplica	17.63671044	128.1755686	61	8456
*/ 
