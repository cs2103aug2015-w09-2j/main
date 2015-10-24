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
import org.json.simple.parser.ParseException;

public class JsonFile {
	
	private static String filePath;
	private static JsonFileContent jsonContent;
	
	public JsonFile(){
		JsonFile.filePath = null;
		jsonContent = new JsonFileContent();
	}
	public JsonFile(String filePath){
		JsonFile.filePath = filePath;
		jsonContent = new JsonFileContent();
		
	}
	
	
	
	public void jsonWriteTask(Task task){
		
		String description,startDate, startTime, endDate, endTime;
		String taskType = task.getClass().getName();
		taskType = taskType.substring(5).toUpperCase();
		//System.out.println(taskType);
		description = task.getDescription();
		
		
		switch(taskType){
			case "EVENT":		
				
				startDate = ((Event)task).getStartDate().toString();
				startTime = ((Event)task).getStartTime().toString();
				endDate = ((Event)task).getEndDate().toString();
				endTime = ((Event)task).getEndTime().toString();
				writeEventTask(description, startDate, startTime, endDate, endTime);
				break;
			case "DEADLINE":
				endDate = ((Deadline)task).getEndDate().toString();
				endTime = ((Deadline)task).getEndTime().toString();
				writeDeadlineTask(description, endDate ,endTime);
				break;
			case "FLOATING":
				writeFloatingTask(description);
				break;
		}
	}
	
	//Check if file is not blank if yes write the empty json format
	public ArrayList<Task> getEventTask(){	
		isFileEmpty();
		return readTask("EVENT");
	}
	
	public ArrayList<Task> getDeadlineTask(){
		isFileEmpty();
		return readTask("DEADLINE");
	}
	
	public ArrayList<Task> getFloatingTask(){
		isFileEmpty();
		return readTask("FLOATING");
	}
	
	public ArrayList<Task> getAllTask(){
		isFileEmpty();
		return readTask("ALL");
	}
	
	private void isFileEmpty(){
		File file = new File(filePath);
		jsonContent.isJsonFileEmpty(file);
	}
	public ArrayList<ArrayList<Task>> search(String keyword){
		ArrayList<ArrayList<Task>> searchResult = new ArrayList<ArrayList<Task>>();
		ArrayList<JSONArray> content = getJsonFileContent();
		
		ArrayList<Task> event = searchEvent(keyword, content.get(0));
		ArrayList<Task> deadline = searchDeadline(keyword, content.get(1));
		ArrayList<Task> floating = searchFloating(keyword, content.get(2));
		
		searchResult.add(event);
		searchResult.add(deadline);
		searchResult.add(floating);
		
		return searchResult;
	}
	
	public Task delete(Task task){
		return deleteTask(task);
	}
	
	private Task deleteTask(Task task){
		String taskType = determineTaskType(task);
		
		switch(taskType){
			
		}
		//24/10/2015 1500
		return null;
	}
	
	private String determineTaskType(Task task){
		String startDate, endDate;
		
		startDate = ((Event)task).getStartDate().toString();
		endDate = ((Deadline)task).getEndDate().toString();
		
		if(startDate != null && endDate != null){
			return "EVENT";
		}else if(startDate== null && endDate != null){
			return "DEADLINE";
		}else{
			return "FLOATING";
		}
	}
	
	private ArrayList<Task> searchEvent(String keyword, JSONArray event){
		
		String description,startDate, startTime, endDate, endTime;
		ArrayList<Task> eventList = eventTaskArray(event);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<eventList.size(); i++){
			Task task = eventList.get(i);
			description = task.getDescription();
			startDate = ((Event)task).getStartDate().toString();
			startTime = ((Event)task).getStartTime().toString();
			endDate = ((Event)task).getEndDate().toString();
			endTime = ((Event)task).getEndTime().toString();
			
			String strTask = description + " " + startDate + " " + startTime + " " + endDate + " " + endTime;
			
			if(strTask.contains(keyword)){
				searchList.add(task);
			}
		}
		
		return searchList;
	}
  	
	private ArrayList<Task> searchDeadline(String keyword, JSONArray event){
		String description,endDate, endTime;
		ArrayList<Task> deadlineList = deadlineTaskArray(event);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<deadlineList.size(); i++){
			Task task = deadlineList.get(i);
			description = task.getDescription();
			endDate = ((Deadline)task).getEndDate().toString();
			endTime = ((Deadline)task).getEndTime().toString();
			
			String strTask = description  + " " + endDate + " " + endTime;
			
			if(strTask.contains(keyword)){
				searchList.add(task);
			}
		}
		
		return searchList;
	}
	
	private ArrayList<Task> searchFloating(String keyword, JSONArray event){
		String description;
		ArrayList<Task> floatingList = floatingTaskArray(event);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<floatingList.size(); i++){
			Task task = floatingList.get(i);
			description = task.getDescription();
			
			String strTask = description;
			
			if(strTask.contains(keyword)){
				searchList.add(task);
			}
		}
		
		return searchList;
	}
	
	private ArrayList<Task> readTask(String taskType){
		ArrayList<JSONArray> content = getJsonFileContent();
		ArrayList<Task> task = new ArrayList<Task>();
		
		
		switch(taskType){
			case "EVENT":
				task = eventTaskArray(content.get(0));
				break;
			case "DEADLINE":
				task = deadlineTaskArray(content.get(1));
				break;
			case "FLOATING":
				task = floatingTaskArray(content.get(2));
				break;
			case "ALL":
				task = allTaskArray(content);
				break;
			default:
				System.out.println("JsonFile.java ArrayList<Task> readTask error");
		}
		
		return task;
	}
	
	private ArrayList<Task> eventTaskArray(JSONArray event){
		
		ArrayList<Task> eventTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();
		
		for(int i=0; i<event.size(); i++){
			
			try{
				JSONObject obj = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
			
				String strStartDate = obj.get("start-date").toString();
				String strEndDate = obj.get("end-date").toString();
				String strStartTime = obj.get("start-time").toString();
				String strEndTime = obj.get("end-time").toString();
			
				String task = obj.get("task").toString();
				DateClass startDate = new DateClass(strStartDate);
				DateClass endDate = new DateClass(strEndDate);
				TimeClass startTime = new TimeClass(strStartTime);
				TimeClass endTime = new TimeClass(strEndTime);
						
				Event eventTask = new Event(task, startDate, startTime, endDate, endTime);
			
				eventTaskList.add(eventTask);
		
			}catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return eventTaskList;
	}
	
	private ArrayList<Task> deadlineTaskArray(JSONArray deadline){
		
		ArrayList<Task> deadlineTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();
		
		for(int i=0; i<deadline.size(); i++){
			
			try{
				JSONObject obj = (JSONObject) jsonParser.parse(deadline.get(i).toString().replace("\\", ""));
			
				
				String strEndDate = obj.get("end-date").toString();
				String strEndTime = obj.get("end-time").toString();
				
				String task = obj.get("task").toString();
				DateClass endDate = new DateClass(strEndDate);
				TimeClass endTime = new TimeClass(strEndTime);
						
				Deadline deadlineTask = new Deadline(task,endDate, endTime);
			
				deadlineTaskList.add(deadlineTask);
		
			}catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return deadlineTaskList;
	}
	
	private ArrayList<Task> floatingTaskArray(JSONArray event){
		ArrayList<Task> floatingTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();
		
		for(int i=0; i<event.size(); i++){
			
			try{
				JSONObject obj = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
						
				String task = obj.get("task").toString();
							
				Floating floatingTask = new Floating(task);
			
				floatingTaskList.add(floatingTask);
		
			}catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return floatingTaskList;
		
	}
	
	private ArrayList<Task> allTaskArray(ArrayList<JSONArray> content){
		
		ArrayList<Task> event = eventTaskArray(content.get(0));
		ArrayList<Task> deadline = deadlineTaskArray(content.get(1));
		ArrayList<Task> floating = floatingTaskArray(content.get(2));
		
		ArrayList<Task> allTask = new ArrayList<Task>();
		
		for(int i=0; i<event.size(); i++)
		{
			allTask.add(event.get(i));
		}
		for(int i=0; i<deadline.size(); i++)
		{
			allTask.add(deadline.get(i));
		}
		for(int i=0; i<floating.size(); i++)
		{
			allTask.add(floating.get(i));
		}
		
		return allTask;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void writeEventTask(String description, String startDate, String startTime, String endDate, String endTime){
		String arr[] = {description, startDate, startTime, endDate, endTime};
		writeToJsonFile("EVENT", arr);
	}
	
	private void writeDeadlineTask(String description, String endDate ,String endTime){
		String arr[] = {description, endDate, endTime};
		writeToJsonFile("DEADLINE", arr);
	}
	
	private void writeFloatingTask(String description){
		String arr[] = {description};
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

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static JSONArray eventJsonArray(JSONArray event){
		
		
		JSONArray eventArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<event.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
				
			
				//Solved
				Map eventMap =new LinkedHashMap();
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
		
		JSONArray deadlineArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<deadline.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(deadline.get(i).toString().replace("\\", ""));
				
				Map deadlineMap =new LinkedHashMap();
				deadlineMap.put("task", jsonObject.get("task"));
				deadlineMap.put("end-date", jsonObject.get("end-date"));
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
		
		JSONArray floatingArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<floating.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(floating.get(i).toString().replace("\\", ""));
				
				Map floatingMap =new LinkedHashMap();
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
	
	
	/*
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
	*/
	/*
	
	private static void writeEvent(String[] arr){
		
		writeToJsonFile("EVENT", arr);
	}
	
	private static void writeDeadline(String[] arr){
		writeToJsonFile("DEADLINE", arr);
	}
	
	private static void writeFloating(String[] arr){
		writeToJsonFile("FLOATING", arr);
	}
	*/
	
	
	/**
	 * This method split the string at each blank space and store it into an array
	 * @param text string to be splitted
	 * @return the array of string which had been splitted
	 */
	/*
	private static String[] getContentArray(String text){	
		String arr[] = text.split("\\s+");
		
		//for(int i=0; i<arr.length; i++)
		//	System.out.println(arr[i]);
		
		return arr;
		
	}
	*/
	/**
	 * This method remove the first two elements of the old array
	 * @param arr[] old array
	 * @return newArr the new array without the first two elements of the old array
	 */
	/*
	private static String[] newContentArray(String[] arr, int num){
		String newArr[] = new String[num];
		int index = 0;
		
		//System.out.println(arr.length-num);
		//System.out.println(arr[2]);
		
		if(arr.length-num != 2)
		{
			for(int i=2; i<=arr.length-num; i++){
				if(i==2){	
					newArr[index] = arr[i]+  " ";
				}else if(i != arr.length-num){
					newArr[index] += arr[i] + " ";
				}else{
					newArr[index++] += arr[i];
				}
			}
		}else{
			newArr[index++] = arr[2];
		}
		for(int j=arr.length-num+1; j<arr.length; j++){
			newArr[index++] = arr[j];
		}
		
		
		//for(int i=0; i<newArr.length; i++){
		//	System.out.println(newArr[i]);
		//}
		
		
		
		return newArr;
	}
	
	*/
	
	
	
	
	
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
