package de.mdelab.predictor.loader.test;

import static org.junit.Assert.*;
import org.junit.Test;

import de.mdelab.predictor.loader.RegressionModel;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestDiscontinuousModel {

	@Test
	public void test() {		
		
		Float allowedPercentDeviation = new Float(10);


		RegressionModel lrm = new de.mdelab.predictor.loader.RegressionModel(
				RegressionModel.path, RegressionModel.discontinous_pmml_fileName);
		try {
			lrm.loadModel();
			System.out.println("Follow the model features:");
			lrm.showModelFeatures();

			System.out.println("Allow deviation from actual value= "+allowedPercentDeviation.toString()+"%");

			System.out.println("Running test");
			Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
			
				userArguments.put("CRITICALITY", new Double(10) );
				userArguments.put("RELIABILITY", new Double(0.5) );
				//userArguments.put("CONNECTIVITY", new Double(5) );
				userArguments.put("IMPORTANCE", new Double(14) );
				userArguments.put("REQUIRED_INTERFACE", new Double(5) );
				userArguments.put("PROVIDED_INTERFACE", new Double(0) );
				userArguments.put("REPLICA", new Double(21) );
				userArguments.put("REQUEST", new Double(731) );
				userArguments.put("ADT", new Double(1.457028155) );
				userArguments.put("PMax", new Double(7.68021606) );
				//userArguments.put("alpha", new Double(152.5462) );
				
				
				
				Double actual = 405.4297184;
				
				Double predicted = lrm.pointPrediction_GBM(userArguments);
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
           9         0.5            2          1                  1                  1      17
     REQUEST      ADT     PMax    alpha UTILITY_INCREASE
     618 		1.221895 9.853348 152.5462          5.78105*/ 

