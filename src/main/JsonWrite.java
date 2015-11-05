package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class JsonWrite {

	/**
	 * Write an event task into the storage file
	 * @param description description of the event task
	 * @param startDate start date of the event task
	 * @param startTime start time of the event task
	 * @param endDate end date of the event task
	 * @param endTime end time of the event task
	 * @param fileType STORAGE, DONE or OVERDUE file
	 */
	public void writeEventTask(String description, String startDate, String startTime, 
			String endDate, String endTime, String fileType){
		String arr[] = {description, startDate, startTime, endDate, endTime};
		writeToJsonFile("EVENT", arr, fileType);
	}
	
	/**
	 * Write a deadline task to the storage file
	 * @param description description of the deadline task
	 * @param endDate end date of the deadline task
	 * @param endTime end time of the deadline task
	 * @param fileType STORAGE, DONE or OVERDUE file
	 */
	public void writeDeadlineTask(String description, String endDate ,String endTime, String fileType){
		String arr[] = {description, endDate, endTime};
		writeToJsonFile("DEADLINE", arr, fileType);
	}
	
	/**
	 * Write a floating task to the storage file
	 * @param description description of the floating task
	 * @param fileType STORAGE, DONE or OVERDUE file
	 */
	public void writeFloatingTask(String description, String fileType){
		String arr[] = {description};
		writeToJsonFile("FLOATING", arr, fileType);
		
	}
	
	/**
	 * Write a task to the storage file
	 * @param taskType type of the task event, deadline or floating
	 * @param arr array of string containing the details of each section of a task
	 * @param fileType STORAGE, DONE or OVERDUE file
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
		
		//try {
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
			JsonPrettyPrint(filePath, jsonText);
			//FileWriter file = new FileWriter(filePath);//"test.txt");

			//file.write(jsonText);
			//file.flush();
			//file.close();

		//} //catch (IOException e) {
		//	e.printStackTrace();
		//}
		
	}
	
	private static void JsonPrettyPrint(String filePath, String jsonText){
		try {
			FileWriter file = new FileWriter(filePath, false);
			//BufferedWriter bufferedWriter =new BufferedWriter(file);

			StringBuilder sb = new StringBuilder(jsonText);

			
			sb.insert(sb.indexOf("\"EVENT\":["), "\n\t");
			sb.insert(sb.indexOf("\"EVENT\":[")+9, "\n\t\t");
			sb.insert(sb.indexOf("\"DEADLINE\":["), "\n\t");
			sb.insert(sb.indexOf("\"DEADLINE\":[")+12, "\n\t\t");
			sb.insert(sb.indexOf("\"FLOATING\":["), "\n\t");
			sb.insert(sb.indexOf("\"FLOATING\":[")+12, "\n\t\t");
			sb.insert(sb.length()-2,  "\n\t");
			sb.insert(sb.length()-1,  "\n");
			
			int index = sb.indexOf("{");
			
			while (index >=0){
				index = sb.indexOf("{", index+1);
			    if(index != -1){
			    	sb.insert(index+1, "\n\t\t\t");
			    }
			}
			
			index = sb.indexOf(",\"");
			while (index >=0){
				index = sb.indexOf(",\"", index+1);
			    if(index != -1){
			    	sb.insert(index+1, "\n\t\t\t");
			    }
			    
			}
			/*
			index = sb.indexOf(",");
			while (index >=0){
				index = sb.indexOf(",", index+1);
			    if(index != -1){
			    	sb.insert(index+1, "\n\t\t");
			    }
			    
			}
			*/
			
			
			index = sb.indexOf("\"}");
			while (index >=0){
				if(index != -1){
			    	sb.insert(index+1, "\n\t\t");
			    }
				index = sb.indexOf("\"}", index+1);	
				
			}
			
			
			
			file.write(sb.toString());
			file.flush();
			file.close();
			/*
			String strArray[] = jsonText.split(",");
			for(int k=0; k<strArray.length; k++){
				
				if(k== strArray.length-1){
					bufferedWriter.write(strArray[k]);
					bufferedWriter.newLine();
				}
				else{
					bufferedWriter.write(strArray[k] + ",");
					bufferedWriter.newLine();
				}
				
			
			}
			bufferedWriter.close();
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Create a event task an input it in JSON format
	 * @param arr array of string containing each section of a task
	 * @return Map which contains the JSON format {"task", "start-date", "start-time", "end-date", "end-time"}
	 */
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
	
	/**
	 * Create a deadline task an input it in JSON format
	 * @param arr array of string containing each section of a task
	 * @return Map which contains the JSON format {"task", "end-date", "end-time"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newDeadline(String arr[]){
		Map deadlineMap = new LinkedHashMap();
		
		deadlineMap.put("task", arr[0]);
		deadlineMap.put("end-date", arr[1]);
		deadlineMap.put("end-time", arr[2]);
		
		
		return deadlineMap;
	}
	
	/**
	 * Create a floating task an input it in JSON format
	 * @param arr array of string containing each section of a task
	 * @return Map which contains the JSON format {"task"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newFloating(String arr[]){
		Map floatingMap = new LinkedHashMap();
		
		floatingMap.put("task", arr[0]);
		
		return floatingMap;
	}
	
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
	
}
