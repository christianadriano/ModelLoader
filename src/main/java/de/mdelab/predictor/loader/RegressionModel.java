package de.mdelab.predictor.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Source;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.FieldValueUtil;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.regression.RegressionModelEvaluator;
import org.jpmml.evaluator.tree.TreeModelEvaluator;
import org.jpmml.evaluator.mining.MiningModelEvaluator;
import org.jpmml.model.ImportFilter;
import org.jpmml.model.JAXBUtil;
import org.xml.sax.InputSource;

public class RegressionModel {

	//Instance attributes
	private String fileName = "Linear10K-xgb.pmml";//"CriticalityConnectivityReliability_LM.pmml";
	private PMML model;
	//private RegressionModelEvaluator evaluator;
	//private org.jpmml.evaluator.tree.TreeModelEvaluator evaluator;
	org.jpmml.evaluator.mining.MiningModelEvaluator evaluator;
	

	//Class attributes
	public static String path = "C://ML_models//"; 
	public static String linear_pmml_fileName = "Linear3K-xgb.pmml";
	public static String discontinous_pmml_fileName = "Discontinuous3K-xgb.pmml";
	public static String saturating_pmml_fileName = "Saturating1K-xgb.pmml";
	public static String all_pmml_fileName = "ALL3K-xgb.pmml";
	
	
	public RegressionModel(String path, String pmml_fileName) {
		this.fileName = path + pmml_fileName;
		
	}


	public void showModelFeatures(){

		List<InputField> requiredModelFeatures = evaluator.getActiveFields();
		for(InputField field : requiredModelFeatures){
			FieldName name = field.getName();
			String value = name.getValue();
			System.out.println(value);
		}
	}

	

	/**
	 * Load a PMML model from the file system.
	 *
	 * @param file
	 * @return PMML
	 * @throws Exception
	 */
	public void loadModel() throws Exception {

		File inputFilePath = new File( this.fileName );

		try( InputStream in = new FileInputStream( inputFilePath ) ){

			Source source = ImportFilter.apply(new InputSource(in));
			this.model = JAXBUtil.unmarshalPMML(source);
			
			// create a ModelEvaluator, later being used for evaluation of the input data
			this.evaluator = new MiningModelEvaluator(this.model);

		} catch( Exception e) {
			System.out.println( e.toString() );
			throw e;
		}
	}
	

	
	/**
	 * Prepare the input data that will be used to produce a prediction with the 
	 * model that was loaded from R.
	 * @param evaluator the model that will called to make the prediction
	 * @param inputMap variables that will be used to compute the prediction
	 * @return 
	 */
	public Map<FieldName, ?> predict(Map<String, ?> userArguments){
		
		Map<FieldName, FieldValue> inputMap = new LinkedHashMap<FieldName, FieldValue>();
		
		 List<InputField> activeFields = this.evaluator.getActiveFields();
		  for(InputField activeField : activeFields){
			  
			Object userValue = userArguments.get(activeField.getName().getValue());
		    FieldValue fieldValue= FieldValueUtil.create(activeField.getField(), userValue);
		    
		    inputMap.put(activeField.getName(),fieldValue);
		  }

		return this.evaluator.evaluate(inputMap);
	}
	
	/**
	 * Makes one single prediction.
	 * @param userArguments one entry to make a single prediction
	 * @return one point estimate
	 */
	public Float pointPrediction(Map<String, ?> userArguments) {

		Map<FieldName, ?> outcomeMap = this.predict(userArguments);
		
		Collection<?> set = outcomeMap.values();
		for(Object value: set){
			return (Float) value;
		}
		
		return null;
	}
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public static void main(String args[]){
		
		RegressionModel lrm = new RegressionModel(path, linear_pmml_fileName);
		try {
			lrm.loadModel();
			lrm.showModelFeatures();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public List<InputField> getActiveFields() {
		return this.evaluator.getActiveFields();
	}

	

}
