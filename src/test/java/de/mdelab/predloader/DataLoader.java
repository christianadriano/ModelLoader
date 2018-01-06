package de.mdelab.predloader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/* 
 * Loads a set of lines of test data from a provided csv file
 */
public class DataLoader {
	
	private String path;
	private String fileName;
	
	public DataLoader(String path, String fileName){
		this.fileName = fileName;
		this.path = path;
	}
	
	/**
	 * 
	 */
	public ArrayList<LinkedHashMap<String,String>> load(){
		
		ArrayList<LinkedHashMap<String,String>> tupleMapList =  new ArrayList<LinkedHashMap<String,String>>();
		
		ArrayList<String> fileContentList = ReadWriteFile.readToBuffer(this.path, this.fileName);		
		
		LinkedHashMap<String,String> templateTupleMap = initializeHeader(fileContentList.get(0));		
		fileContentList.remove(0); //Skip header
		
		for(String tuple : fileContentList){
			String[] contentArray = tuple.split(",");
			int i = 0;
			LinkedHashMap<String,String> valueTupleMap = new LinkedHashMap<String,String>();//Each line of the file
			for(Map.Entry<String,String> entry : templateTupleMap.entrySet()){
				String name = entry.getKey();
				String value = contentArray[i];
				valueTupleMap.put(name, value);
				i++;
			}
			tupleMapList.add(valueTupleMap); //Accumulate lines
		}
		
		return tupleMapList;
	
	}

	
	private LinkedHashMap<String,String> initializeHeader(String headerNames){

		LinkedHashMap<String,String> templateTuple = new LinkedHashMap<String,String>();
		String[] contentArray = headerNames.split(",");
		for(String value: contentArray){
			templateTuple.put(value, "");
		}
		return templateTuple;
	}
	
}
