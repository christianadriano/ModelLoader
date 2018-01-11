package de.mdelab.predictor.loader.test;

import static org.junit.Assert.*;
import org.junit.Test;

import de.mdelab.predictor.loader.RegressionModel;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestALLModel {

	@Test
	public void test() {
		
		Float allowedPercentDeviation = new Float(10);


		RegressionModel lrm = new de.mdelab.predictor.loader.RegressionModel(
				RegressionModel.path, RegressionModel.all_pmml_fileName);
		try {
			lrm.loadModel();
			System.out.println("Follow the model features:");
			lrm.showModelFeatures();

			System.out.println("Allow deviation from actual value= "+allowedPercentDeviation.toString()+"%");

			System.out.println("Running test");
			Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
			
				userArguments.put("CRITICALITY", new Double(8) );
				userArguments.put("CONNECTIVITY", new Double(4) );
				userArguments.put("IMPORTANCE", new Double(7) );
				userArguments.put("REQUIRED_INTERFACE", new Double(4) );
				userArguments.put("PROVIDED_INTERFACE", new Double(0) );
				userArguments.put("RELIABILITY", new Double(0.5) );
				userArguments.put("REPLICA", new Double(32) );
				userArguments.put("REQUEST", new Double(3160) );
				userArguments.put("ADT", new Double(1.211267) );
				userArguments.put("PMax", new Double(14.02723) );
				//userArguments.put("alpha", new Double(151.5789) );
				
				Double actual = 1777.455;
				
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
      CRITICALITY RELIABILITY CONNECTIVITY IMPORTANCE PROVIDED_INTERFACE REQUIRED_INTERFACE REPLICA
2608       8         0.5            4          7                  0                  4      32
     	REQUEST      ADT     	PMax    	alpha 			UTILITY_INCREASE
2608    3160 		1.211267 	14.02723 	151.5789         1777.455			

*/ 
