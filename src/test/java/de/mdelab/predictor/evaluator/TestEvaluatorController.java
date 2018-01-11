package de.mdelab.predictor.evaluator;

import static org.junit.Assert.*;
import java.util.LinkedHashMap;
import org.junit.Test;

public class TestEvaluatorController {

	@Test
	public void testSameComponent() {
			
		//Run test
		EvaluatorController controller =  new EvaluatorController();
		LinkedHashMap<String, Cycle> executedCycleMap = controller.loadCyclesFromFile(
				"C://Users//chris//OneDrive//Documentos//GitHub//ML_SelfHealingUtility//data//Validation//",
				"Combined_Evaluation.csv");
		
		String predictedComponentID =  "_SEuUO-cdEeet0YmmfbMwkw";
		String actualComponentID = executedCycleMap.get(1).predictedRank.get(1);
		
		assertTrue("Components did not match: predicted:"+predictedComponentID+", actual:"+actualComponentID, predictedComponentID.matches(actualComponentID));	
	}
	
	@Test
	public void testSameRanking() {
	
		//Run test
		EvaluatorController controller =  new EvaluatorController();
		LinkedHashMap<String, Cycle> executedCycleMap = controller.loadCyclesFromFile(
				"C://Users//chris//OneDrive//Documentos//GitHub//ML_SelfHealingUtility//data//Validation//",
				"Combined_Evaluation.csv");
		
		Integer expectedRank=  1;
		String componentAtTwo = "_SEu7mecdEeet0YmmfbMwkw";
		Cycle predictedCycle = executedCycleMap.get(2);
		Integer actualRank = predictedCycle.predictedComponentRank.get(componentAtTwo);
		
		assertTrue("Ranks did not match, expecteRank: "+expectedRank+", actual:"+actualRank, expectedRank.intValue() == actualRank.intValue());	
	}
	

}
