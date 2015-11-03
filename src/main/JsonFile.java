package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonFile {
	
	
	public JsonFile(){
		
	}
	
	/**
	 * Create a JSON file with the default JSON format
	 * @param file the file to be created
	 */
	public void createJsonFile(File file){
		
		//createFile(file);
		
		String open = "{";
		String event = "\"EVENT\":[],";
    	String deadline = "\"DEADLINE\":[],";
    	String floating = "\"FLOATING\":[]"; 
    	String close = "}";
    			
		FileWriter fileWriter;
		
		try {
			fileWriter = new FileWriter(file, false);
			BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

			bufferedWriter.write(open);
			bufferedWriter.newLine();
			bufferedWriter.write(event);
			bufferedWriter.newLine();
			bufferedWriter.write(deadline);
			bufferedWriter.newLine();
			bufferedWriter.write(floating);
			bufferedWriter.newLine();
			bufferedWriter.write(close);
			bufferedWriter.newLine();
			
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error writing to Jsonfile");
		}
		
	}
	
	/**
	 * Check is the JSON file is empty
	 * @param file the file to be checked
	 */
	public void isJsonFileEmpty(File file){
		
		if (file.length() == 0) {
			createJsonFile(file);
		} 

	}
	
	
	/**
	 * This method return the JSONArray of EVENT, DEADLINE, FLOATING
	 * @param fileType determine if the is a STORAGE_FILE, DONE_FILE or OVERDUE_FILE
	 * @return ArrayList of JSONArray containing EVENT, DEADLINE, FLOATING
	 */
	public ArrayList<JSONArray> getJsonFileContent(String fileType){
		FileStorage fs = new FileStorage();
		String filePath = "";
		switch(fileType){
			case "STORAGE_FILE":
				filePath = fs.getFilePath();
				break;
			case "DONE_FILE":
				filePath = "done.json";
				break;
			case "OVERDUE_FILE":
				filePath = "overdue.json";
				break;
			default:
				System.out.println("getJsonFileContent(String fileType) has a empty filePath");
		}
		
		ArrayList<JSONArray> contentList = new ArrayList<JSONArray>();
		
			
		try{
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
					
			// get an array from the JSON object
			contentList.add((JSONArray) jsonObject.get("EVENT"));
			contentList.add((JSONArray) jsonObject.get("DEADLINE"));
			contentList.add((JSONArray) jsonObject.get("FLOATING"));
						
			reader.close();
			
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return contentList;
	}
	
	/*
	public ArrayList<JSONArray> getJsonFileContent(){
		FileStorage fs = new FileStorage();
		String filePath = fs.getFilePath();
		ArrayList<JSONArray> contentList = new ArrayList<JSONArray>();
		
			
		try{
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			
			

			// get an array from the JSON object
			contentList.add((JSONArray) jsonObject.get("EVENT"));
			contentList.add((JSONArray) jsonObject.get("DEADLINE"));
			contentList.add((JSONArray) jsonObject.get("FLOATING"));
			
			
			
			reader.close();
			
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return contentList;
	}
	*/
	
}
