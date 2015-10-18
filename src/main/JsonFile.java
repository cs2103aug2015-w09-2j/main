package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class JsonFile {
	
	private static String filePath;
	
	public JsonFile(String filePath){
		JsonFile.filePath = filePath;
	}
	
	/**
	 * This method return the JSONArray of EVENT, DEADLINE, FLOATING
	 * @return ArrayList of JSONArray containing EVENT, DEADLINE, FLOATING
	 */
	public static ArrayList<JSONArray> getJsonFileContent(){
		ArrayList<JSONArray> contentList = new ArrayList<JSONArray>();
		
		try{
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			
			

			// get an array from the JSON object
			contentList.add((JSONArray) jsonObject.get("EVENT"));
			contentList.add((JSONArray) jsonObject.get("DEADLINE"));
			contentList.add((JSONArray) jsonObject.get("FLOATING"));
			
			
			
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
	
	public void writeJson(String text){
				
		String arr[] = getContentArray(text);
			
		switch(arr[1]){
			case "-e":
				writeEvent(newContentArray(arr, 5));				
				break;
			case "-d":
				writeDeadline(newContentArray(arr, 3));		
				break;
			case "-f":
				writeFloating(newContentArray(arr, 1));		
				break;
			default:
				System.out.println("Error");
		}
		
	}
	
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
	
	private static void writeEvent(String[] arr){
		
		writeToJsonFile("EVENT", arr);
	}
	
	private static void writeDeadline(String[] arr){
		writeToJsonFile("DEADLINE", arr);
	}
	
	private static void writeFloating(String[] arr){
		writeToJsonFile("FLOATING", arr);
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void writeToJsonFile(String taskType, String arr[]){
		Map obj =new LinkedHashMap();
		
		ArrayList<JSONArray> contentList = getJsonFileContent();
		
		JSONArray eventArray =  eventJsonArray(contentList.get(0));
		JSONArray deadlineArray =  deadlineJsonArray(contentList.get(1));
		JSONArray floatingArray =  floatingJsonArray(contentList.get(2));
		
		switch(taskType){
			case "EVENT":
				eventArray.add(newEvent(arr));		
				break;
			case "DEADLINE":
				deadlineArray.add(newDeadline(arr));		
				break;
			case "FLOATING":
				floatingArray.add(newFloating(arr));		
				break;
			default:
				System.out.println("Error");
		}
		
		obj.put("EVENT", eventArray);
		obj.put("DEADLINE", deadlineArray);
		obj.put("FLOATING", floatingArray);
		
		 StringWriter out = new StringWriter();
		   try {
			JSONValue.writeJSONString(obj, out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   String jsonText = out.toString().replace("\\", "");
		   //System.out.println(jsonText);
		try {

			FileWriter file = new FileWriter(filePath);//"test.txt");
			file.write(jsonText);
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newEvent(String arr[]){
		Map eventMap = new LinkedHashMap();
		
		eventMap.put("task", arr[0]);
		eventMap.put("start-date", arr[1]);
		eventMap.put("start-time", arr[2]);
		eventMap.put("end-date", arr[3]);
		eventMap.put("end-time", arr[4]);
				
		return eventMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newDeadline(String arr[]){
		Map deadlineMap = new LinkedHashMap();
		
		deadlineMap.put("task", arr[0]);
		deadlineMap.put("deadline", arr[1]);
		deadlineMap.put("end-time", arr[2]);
		
		return deadlineMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newFloating(String arr[]){
		Map floatingMap = new LinkedHashMap();
		
		floatingMap.put("task", arr[0]);
		
		return floatingMap;
	}

	/**
	 * This method split the string at each blank space and store it into an array
	 * @param text string to be splitted
	 * @return the array of string which had been splitted
	 */
	private static String[] getContentArray(String text){	
		String arr[] = text.split("\\s+");
		
		return arr;
	}
	
	/**
	 * This method remove the first two elements of the old array
	 * @param arr[] old array
	 * @return newArr the new array without the first two elements of the old array
	 */
	private static String[] newContentArray(String[] arr, int num){
		String newArr[] = new String[num];
		int index = 0;
		
		
		for(int i=2; i<=arr.length-num; i++){
			if(i==2){
				newArr[index] = arr[i]+  " ";
			}else if(i != arr.length-num){
				newArr[index] += arr[i] + " ";
			}else{
				newArr[index++] += arr[i];
			}
		}
		for(int j=arr.length-num+1; j<arr.length; j++){
			newArr[index++] = arr[j];
		}
		
		/*
		for(int i=0; i<newArr.length; i++){
			System.out.println(newArr[i]);
		}
		*/
		
		
		return newArr;
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static JSONArray eventJsonArray(JSONArray event){
		
		Map eventMap =new LinkedHashMap();
		JSONArray eventArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<event.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
				//System.out.println("Event: " + event.get(i).toString().replace("\\", ""));
			
				eventMap.put("task", jsonObject.get("task"));
				eventMap.put("start-date", jsonObject.get("start-date"));
				eventMap.put("start-time", jsonObject.get("start-time"));
				eventMap.put("end-date", jsonObject.get("end-date"));
				eventMap.put("end-time", jsonObject.get("end-time"));
				
				
			
				eventArray.add(eventMap);
			}
			
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eventArray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static JSONArray deadlineJsonArray(JSONArray deadline){
		Map deadlineMap =new LinkedHashMap();
		JSONArray deadlineArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<deadline.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(deadline.get(i).toString().replace("\\", ""));
				
			
				deadlineMap.put("task", jsonObject.get("task"));
				deadlineMap.put("deadline", jsonObject.get("deadline"));
				deadlineMap.put("end-time", jsonObject.get("end-time"));
			
				deadlineArray.add(deadlineMap);
			}
			
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deadlineArray;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static JSONArray floatingJsonArray(JSONArray floating){
		Map floatingMap =new LinkedHashMap();
		JSONArray floatingArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<floating.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(floating.get(i).toString().replace("\\", ""));
				
				
				floatingMap.put("task", jsonObject.get("task"));				
			
				floatingArray.add(floatingMap);
			}
			
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return floatingArray;
	}
	
	
	
	/**
	 * Keep for the sake of keeping cause I also don't know
	 */
	/*
	private static void readJsonFile(){
		
		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			// get an array from the JSON object
			JSONArray event = (JSONArray) jsonObject.get("EVENT");
			
			
			if(event.get(0).toString().equals("{}"))
				System.out.println("event:" + event.size());
			else
				System.out.println("pig" + " '" + event.get(0) + "'");
			
			// take the elements of the json array
			for(int i=0; i<event.size(); i++){
				System.out.println("The " + i + " event of the array: " + event.get(i));
			}
			
			// get an array from the JSON object
			JSONArray deadline = (JSONArray) jsonObject.get("DEADLINE");
			// take the elements of the json array
			for(int i=0; i<deadline.size(); i++){
				System.out.println("The " + i + " deadline of the array: " + deadline.get(i));
			}
			
			// get an array from the JSON object
			JSONArray floating = (JSONArray) jsonObject.get("FLOATING");
			// take the elements of the json array
			for(int i=0; i<floating.size(); i++){
				System.out.println("The " + i + " floating of the array: " + floating.get(i));
			}
						
			
			Iterator i = event.iterator();

			// take each value from the json array separately
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				System.out.println("language "+ innerObj.get("task") + 
						" with level " + innerObj.get("start-date"));
			}
			
			

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

}
