import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.dmg.pmml.PMML;
import org.junit.Test;

import loader.LinearRegressionModel;

public class TestLinearRegressionModel {

	@Test
	public void test() {
		
		String path = "C://ML_models//"; //"C://Users//chris//OneDrive//Documentos//GitHub//ML_SelfHealingUtility//models//";
		String fileName = "CriticalityConnectivityReliability_LM.pmml";

		Double allowedDeviation = new Double(10);
		
		
		LinearRegressionModel lrm = new LinearRegressionModel();
			try {
				lrm.loadModel(path+fileName);
				System.out.println("Follow the model features:");
				lrm.showModelFeatures();
				
				System.out.println("Allow deviation from actual value= "+allowedDeviation.toString());
				
				System.out.println("Running test");
				Map<String,Double> userArguments = new LinkedHashMap<String,Double>();
				userArguments.put("CRITICALITY", new Double(18) );
				userArguments.put("CONNECTIVITY", new Double(4) );
				userArguments.put("RELIABILITY", new Double(0.5) );
				
				Double actual = 79.2;
				
				Double predicted = lrm.pointPrediction(userArguments);
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
FAILURE_NAME	AFFECTED_COMPONENT		CRITICALITY	CONNECTIVITY	RELIABILITY		UTILITY_INCREASE	
CF1				Item Management Service		18			4				0.5				79.2	
CF3				Authentication Service		21			5				0.85			187.425	
CF3				Authentication Service		17			5				0.85			160.65	
CF1				Last Second Sales 			17			2				0.5				34	
*/


