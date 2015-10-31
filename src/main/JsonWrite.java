package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;


public class JsonWrite {

	/*
	public void writeEventTask(String description, String startDate, String startTime, 
			String endDate, String endTime){
		String arr[] = {description, startDate, startTime, endDate, endTime};
		writeToJsonFile("EVENT", arr);
	}
	
	public void writeDeadlineTask(String description, String endDate ,String endTime){
		String arr[] = {description, endDate, endTime};
		writeToJsonFile("DEADLINE", arr);
	}
	
	public void writeFloatingTask(String description){
		String arr[] = {description};
		writeToJsonFile("FLOATING", arr);
		
	}
	*/
	
	public void writeEventTask(String description, String startDate, String startTime, 
			String endDate, String endTime, String fileType){
		String arr[] = {description, startDate, startTime, endDate, endTime};
		writeToJsonFile("EVENT", arr, fileType);
	}
	
	public void writeDeadlineTask(String description, String endDate ,String endTime, String fileType){
		String arr[] = {description, endDate, endTime};
		writeToJsonFile("DEADLINE", arr, fileType);
	}
	
	public void writeFloatingTask(String description, String fileType){
		String arr[] = {description};
		writeToJsonFile("FLOATING", arr, fileType);
		
	}
	
	/*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void writeToJsonFile(String taskType, String arr[]){
		
		JsonFile jsonFile = new JsonFile();
		JsonArray jsonArray = new JsonArray();
		Map obj =new LinkedHashMap();
		
		ArrayList<JSONArray> contentList = jsonFile.getJsonFileContent();
		
		JSONArray eventArray =  jsonArray.eventJsonArray(contentList.get(0));
		JSONArray deadlineArray =  jsonArray.deadlineJsonArray(contentList.get(1));
		JSONArray floatingArray =  jsonArray.floatingJsonArray(contentList.get(2));
		
		//System.out.println("Write:" + eventArray.toJSONString());
		
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
		
		
		//System.out.println("obj: " + obj);
		
		StringWriter out = new StringWriter();
		
		try {
			JSONValue.writeJSONString(obj, out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String jsonText = out.toString().replace("\\", "");
		
		try {
			FileStorage fs = new FileStorage();
			String filePath = fs.getFilePath();
			FileWriter file = new FileWriter(filePath);//"test.txt");
			file.write(jsonText);
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void writeToJsonFile(String taskType, String arr[], String fileType){
		
		JsonFile jsonFile = new JsonFile();
		JsonArray jsonArray = new JsonArray();
		Map obj =new LinkedHashMap();
		
		ArrayList<JSONArray> contentList = jsonFile.getJsonFileContent(fileType);
		
		JSONArray eventArray =  jsonArray.eventJsonArray(contentList.get(0));
		JSONArray deadlineArray =  jsonArray.deadlineJsonArray(contentList.get(1));
		JSONArray floatingArray =  jsonArray.floatingJsonArray(contentList.get(2));
		
		//System.out.println("Write:" + eventArray.toJSONString());
		
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
		
		
		//System.out.println("obj: " + obj);
		
		StringWriter out = new StringWriter();
		
		try {
			JSONValue.writeJSONString(obj, out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String jsonText = out.toString().replace("\\", "");
		
		try {
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
					System.out.println("writeToJsonFile(String taskType, String arr[], String fileType)" +
							" has a empty filePath");
			}
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
		deadlineMap.put("end-date", arr[1]);
		deadlineMap.put("end-time", arr[2]);
		
		
		return deadlineMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newFloating(String arr[]){
		Map floatingMap = new LinkedHashMap();
		
		floatingMap.put("task", arr[0]);
		
		return floatingMap;
	}
	
	
}
