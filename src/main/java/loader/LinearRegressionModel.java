package loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.dmg.pmml.mining.MiningModel;
import org.dmg.pmml.regression.RegressionModel;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.FieldValueUtil;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.mining.MiningModelEvaluator;
import org.jpmml.evaluator.regression.RegressionModelEvaluator;
import org.jpmml.model.ImportFilter;
import org.jpmml.model.JAXBUtil;
import org.xml.sax.InputSource;

public class LinearRegressionModel {

	public String path = "C://ML_models//"; //"C://Users//chris//OneDrive//Documentos//GitHub//ML_SelfHealingUtility//models//";
	public String fileName = "CriticalityConnectivityReliability_LM.pmml";
	private PMML model;
	private RegressionModelEvaluator evaluator;

	public static void main(String args[]){

		LinearRegressionModel lrm = new LinearRegressionModel();
		try {
			lrm.loadModel(lrm.path+lrm.fileName);
			lrm.showModelFeatures();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void showModelFeatures(){

		// create a ModelEvaluator, later being used for evaluation of the input data
		this.evaluator = new RegressionModelEvaluator(this.model);
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
	public void loadModel(final String file) throws Exception {

		File inputFilePath = new File( file );

		try( InputStream in = new FileInputStream( inputFilePath ) ){

			Source source = ImportFilter.apply(new InputSource(in));
			this.model = JAXBUtil.unmarshalPMML(source);

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
	public Map<FieldName, FieldValue> preparePredict(Evaluator evaluator, 
			Map<String, ?> userArguments){
	
		Map<FieldName, FieldValue> pmmlArguments = new LinkedHashMap<FieldName, FieldValue>();
		
		 List<InputField> activeFields = evaluator.getActiveFields();
		  for(InputField activeField : activeFields){
		    
		    Object userValue = userArguments.get(activeField.getName().getValue());

		    // The value type of the user arguments map is unknown.
		    // An Object is converted to a String using Object#toString().
		    // A missing value is represented by null.
		   // FieldValue pmmlValue = evaluator.prepare(activeField, (userValue != null ? userValue.toString() : null));

		    //pmmlArguments.put(activeField.getName(), pmmlValue);
		  }
		
		return pmmlArguments;
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
	public Double pointPrediction(Map<String, ?> userArguments) {

		Map<FieldName, ?> outcomeMap = this.predict(userArguments);
		
		Collection<?> set = outcomeMap.values();
		for(Object value: set){
			return (Double) value;
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


	

}
