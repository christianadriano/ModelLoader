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

import com.google.common.graph.ElementOrder.Type;

public class RegressionModel {

	//Instance attributes
	private String fileName;
	private PMML model;
	//private RegressionModelEvaluator evaluator;
	//private org.jpmml.evaluator.tree.TreeModelEvaluator evaluator;
	org.jpmml.evaluator.mining.MiningModelEvaluator evaluator;


	//Instance attributes
	public String path = "C://ML_models//"; // "pmml//"
	public String linear_pmml_fileName = "Linear9K-GBM.pmml";
	public String discontinuous_pmml_fileName = "Discontinuous9K-GBM.pmml";
	public String saturating_pmml_fileName = "Saturating9K-GBM.pmml";
	public String combined_pmml_filename = "Combined9K-GBM.pmml";

	//Method Types
	public static final String GBM = "GBM";
	public static final String RANDOM_FOREST = "RF";
	public static final String XBOOST_TREE = "xgb";

	//Utility Models
	public static final String Linear = "Linear";
	public static final String Saturating = "Saturating";
	public static final String Discontinuous = "Discontinuous";
	public static final String Combined = "Combined";

	//Dataset Sizes
	public static final String Size_1K = "1K";
	public static final String Size_3K = "3K";
	public static final String Size_9K = "9K";

	//Class attributes
//	public static String linear_pmml_fileName_9K_XGB = "Linear9K-xgb.pmml";
//	public static String linear_pmml_fileName_3K_XGB = "Linear3K-xgb.pmml";
//	public static String linear_pmml_fileName_1K_XGB = "Linear1K-xgb.pmml";
//	public static String linear_pmml_fileName_9K_GBM = "Linear9K-GBM.pmml";
//	public static String linear_pmml_fileName_9K_RF = "Linear9K-RF.pmml";
//
//	public static String discontinous_pmml_fileName_9K_XGB = "Discontinuous9K-xgb.pmml";
//	public static String discontinous_pmml_fileName_3K_XGB = "Discontinuous3K-xgb.pmml";
//	public static String discontinous_pmml_fileName_1K_XGB = "Discontinuous1K-xgb.pmml";
//	public static String discontinous_pmml_fileName_9K_GBM = "Discontinuous9K-GBM.pmml";
//	public static String discontinous_pmml_fileName_9K_RF = "Discontinuous9K-GBM.pmml";
//
//	public static String saturating_pmml_fileName_9K_XGB = "Saturating9K-xgb.pmml";
//	public static String saturating_pmml_fileName_3K_XGB = "Saturating3K-xgb.pmml";
//	public static String saturating_pmml_fileName_1K_XGB = "Saturating1K-xgb.pmml";
//	public static String saturating_pmml_fileName_9K_GBM = "Saturating9K-GBM.pmml";
//	public static String saturating_pmml_fileName_9K_RF = "Saturating9K-RF.pmml";
//
//	public static String combined_pmml_fileName_9K_XGB = "Combined9K-xgb.pmml";
//	public static String combined_pmml_fileName_3K_XGB = "Combined3K-xgb.pmml";
//	public static String combined_pmml_fileName_1K_XGB = "Combined1K-xgb.pmml";
//	public static String combined_pmml_fileName_9K_GBM = "Combined9K-GBM.pmml";
//	public static String combined_pmml_fileName_9K_RF = "Combined9K-RF.pmml";

	/**
	 * Selects the pmml file that will be used to run the regression model 
	 * @param methodType has to be one of the three Method types RF, GBM, or XGB (see class attributes)
	 * @param utilityModel has to be either "
	 */
	public RegressionModel(String methodType,String utilityModel, String datasetSize) {

		//Determined the method types
		this.combined_pmml_filename = "Combined"+datasetSize+"-"+methodType+".pmml";
		this.linear_pmml_fileName = "Linear"+ datasetSize + "-" +methodType+ ".pmml";
		this.discontinuous_pmml_fileName = "Discontinuous"+datasetSize+"-"+methodType+".pmml";
		this.saturating_pmml_fileName = "Saturating"+datasetSize+"-"+methodType+".pmml";

		//Choose the pmml file based on utility model
		if(utilityModel.matches(RegressionModel.Linear))
			this.fileName = path + this.linear_pmml_fileName;
		else
			if(utilityModel.matches(RegressionModel.Saturating)) {
				this.fileName = path + this.saturating_pmml_fileName;
			}
			else
				if(utilityModel.matches(RegressionModel.Discontinuous)) {
					this.fileName = path + this.discontinuous_pmml_fileName;
				}
				else
					if(utilityModel.matches(RegressionModel.Combined)) {
						this.fileName = path + this.combined_pmml_filename;
					}
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
	 * Makes one single prediction using GBM method
	 * @param userArguments one entry to make a single prediction
	 * @return one point estimate
	 */
	public Double pointPrediction_GBM(Map<String, ?> userArguments) {

		Map<FieldName, ?> outcomeMap = this.predict(userArguments);

		Collection<?> set = outcomeMap.values();
		for(Object value: set){
			return (Double) value;
		}

		return null;
	}

	/**
	 * Makes one single prediction using XGBoostTree method
	 * @param userArguments one entry to make a single prediction
	 * @return one point estimate
	 */
	public Float pointPrediction_XGB(Map<String, ?> userArguments) {

		Map<FieldName, ?> outcomeMap = this.predict(userArguments);

		Collection<?> set = outcomeMap.values();
		for(Object value: set){
			return (Float) value;
		}

		return null;
	}


	/**
	 * Makes one single prediction using RandomForest method
	 * @param userArguments one entry to make a single prediction
	 * @return one point estimate
	 */
	public Double pointPrediction_RF(Map<String, ?> userArguments) {

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



	public static void main(String args[]){

		RegressionModel lrm = new RegressionModel(
				RegressionModel.RANDOM_FOREST,
				RegressionModel.Linear,
				RegressionModel.Size_9K);
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
