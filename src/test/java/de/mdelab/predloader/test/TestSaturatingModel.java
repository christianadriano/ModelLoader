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
		
		
		RegressionModel lrm = new de.mdelab.predloader.RegressionModel(RegressionModel.path, RegressionModel.saturating_pmml_fileName);
			try {
				lrm.loadModel();
				System.out.println("Follow the model features:");
				lrm.showModelFeatures();
				
				System.out.println("Allow deviation from actual value= "+allowedDeviation.toString());
				
				System.out.println("Running test");
				Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
				userArguments.put("CRITICALITY", new Double(7) );
				userArguments.put("REQUIRED_INTERFACE", new Double(1) );
				userArguments.put("PROVIDED_INTERFACE", new Double(9) );
				userArguments.put("RELIABILITY", new Double(0.85) );
				userArguments.put("REPLICA", new Double(57) );
				userArguments.put("REQUEST", new Double(8975) );
				userArguments.put("PMax", new Double(5.107304) );
				userArguments.put("alpha", new Double(156.5920) );
				
				Double actual = 230.73344;
				
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

ID		CRITICALITY RELIABILITY REPLICA	REQUEST		PMax		alpha		UTILITY_INCREASE
2045       10       	0.5			9		397		7.680216	189.20813	192.00540
9937        8           0.5         22  	759		16.219138	143.37735	129.75310
4238        7           0.85		57		8975	5.107304	156.59209	230.73344
*/ 
