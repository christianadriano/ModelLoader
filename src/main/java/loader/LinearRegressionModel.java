package loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.dmg.pmml.mining.MiningModel;
import org.dmg.pmml.regression.RegressionModel;
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
	private PMML PMML_model;

	public static void main(String args[]){

		LinearRegressionModel lmrObject = new LinearRegressionModel();
		try {
			lmrObject.PMML_model = lmrObject.loadModel(lmrObject.path+lmrObject.fileName);
			lmrObject.showModelFeatures();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void showModelFeatures(){

		// create a ModelEvaluator, later being used for evaluation of the input data
		ModelEvaluator<RegressionModel> modelEvaluator = new RegressionModelEvaluator(this.PMML_model);
		List<InputField> requiredModelFeatures = modelEvaluator.getActiveFields();
		for(InputField field : requiredModelFeatures){
			FieldName name = field.getName();
			System.out.println(name.toString());
		}
	}



	/**
	 * Load a PMML model from the file system.
	 *
	 * @param file
	 * @return PMML
	 * @throws Exception
	 */
	public PMML loadModel(final String file) throws Exception {

		PMML pmml = null;

		File inputFilePath = new File( file );

		try( InputStream in = new FileInputStream( inputFilePath ) ){

			Source source = ImportFilter.apply(new InputSource(in));
			pmml = JAXBUtil.unmarshalPMML(source);

		} catch( Exception e) {
			System.out.println( e.toString() );
			throw e;
		}
		return pmml;
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
