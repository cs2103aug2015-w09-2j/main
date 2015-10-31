package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonDelete {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteTask(Task task, String fileType){
		JsonArray jsonArray = new JsonArray();
		JsonFile jsonFile = new JsonFile();
		Map obj =new LinkedHashMap();
		
		String taskType = determineTaskType(task);
		
		String description,startDate, startTime, endDate, endTime, strTask;
		ArrayList<JSONArray> contentList = jsonFile.getJsonFileContent(fileType);
		
		JSONArray eventArray =  jsonArray.eventJsonArray(contentList.get(0));
		JSONArray deadlineArray =  jsonArray.deadlineJsonArray(contentList.get(1));
		JSONArray floatingArray =  jsonArray.floatingJsonArray(contentList.get(2));
		
		description = task.getDescription();
		
		switch(taskType){
			case "EVENT":
				
				startDate = ((Event)task).getStartDate().toString();
				startTime = ((Event)task).getStartTime().toString();
				endDate = ((Event)task).getEndDate().toString();
				endTime = ((Event)task).getEndTime().toString();		
				strTask = description + " " + startDate + " " + startTime + " " + endDate + " " + endTime;
				//System.out.println(strTask);
				eventArray = deleteEventTask(eventArray, strTask);
				break;
			case "DEADLINE":
				endDate = ((Deadline)task).getEndDate().toString();
				endTime = ((Deadline)task).getEndTime().toString();
				strTask = description  + " " + endDate + " " + endTime;
				//System.out.println(strTask);
				deadlineArray = deleteDeadlineTask(deadlineArray, strTask);
				break;
			case "FLOATING":
				strTask = description;
				//System.out.println(strTask);
				floatingArray = deleteFloatingTask(floatingArray, strTask);
				break;
			default:
				System.out.println("Delete task in JsonFile.java do nothings");
			
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
	
	private String determineTaskType(Task task){
		
		String taskType = task.getClass().toString().substring(11).toUpperCase();
		
		return taskType;
			
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray deleteEventTask(JSONArray array, String deleteTask){
		//concept in eventTaskArray, deadlineTaskArray, floatingTaskArray
		
		JSONArray eventArray = new JSONArray();
		JSONParser jsonParser = new JSONParser();
			
		String strContent = array.toJSONString().substring(1, array.toJSONString().length()-1);
		
		String contentArr[] = strContent.split("\\}");
		
		for(int i=0; i<contentArr.length; i++){
			if(i!=0){
				contentArr[i] = contentArr[i].substring(1);
			}
			contentArr[i] += "}";
		}
		
		for(int i=0; i<array.size(); i++){
			
			try{
				
				//System.out.println(eArray.toJSONString().substring(1, eArray.toJSONString().length()-1));
				
				JSONObject obj = (JSONObject) jsonParser.parse(contentArr[i]);
				
				
				String description = obj.get("task").toString();
				String startDate = obj.get("start-date").toString();
				String endDate = obj.get("end-date").toString();
				String startTime = obj.get("start-time").toString();
				String endTime = obj.get("end-time").toString();
			
				
				
				String strTask = description + " " + startDate + " " + startTime + " " + endDate + " " + endTime;
				
				
				if(!strTask.equals(deleteTask)){
					Map eventMap = new LinkedHashMap();
					
					eventMap.put("task", description);
					eventMap.put("start-date", startDate);
					eventMap.put("start-time", startTime);
					eventMap.put("end-date", endDate);
					eventMap.put("end-time", endTime);
					
					eventArray.add(eventMap);
				}
			
				//If strTask == deletetask dun include in the new JSONArray
				
		
			}catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return eventArray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray deleteDeadlineTask(JSONArray array, String deleteTask){
		//concept in eventTaskArray, deadlineTaskArray, floatingTaskArray
		
		JSONArray deadlineArray = new JSONArray();
		JSONParser jsonParser = new JSONParser();
			
		String strContent = array.toJSONString().substring(1, array.toJSONString().length()-1);
		
		String contentArr[] = strContent.split("\\}");
		
		for(int i=0; i<contentArr.length; i++){
			if(i!=0){
				contentArr[i] = contentArr[i].substring(1);
			}
			contentArr[i] += "}";
		}
		
		for(int i=0; i<array.size(); i++){
			
			try{
				
				//System.out.println(eArray.toJSONString().substring(1, eArray.toJSONString().length()-1));
				
				JSONObject obj = (JSONObject) jsonParser.parse(contentArr[i]);
				
				
				String description = obj.get("task").toString();
				String endDate = obj.get("end-date").toString();
				String endTime = obj.get("end-time").toString();
			
				
				
				String strTask = description + " " + endDate + " " + endTime;
				
				if(!strTask.equals(deleteTask)){
					Map deadlineMap = new LinkedHashMap();
					
					deadlineMap.put("task", description);
					deadlineMap.put("end-date", endDate);
					deadlineMap.put("end-time", endTime);
					
					deadlineArray.add(deadlineMap);
				}
			
				//If strTask == deletetask dun include in the new JSONArray
				
		
			}catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return deadlineArray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray deleteFloatingTask(JSONArray array, String deleteTask){
		//concept in eventTaskArray, deadlineTaskArray, floatingTaskArray
		
		JSONArray floatingArray = new JSONArray();
		JSONParser jsonParser = new JSONParser();
			
		String strContent = array.toJSONString().substring(1, array.toJSONString().length()-1);
		
		String contentArr[] = strContent.split("\\}");
		
		for(int i=0; i<contentArr.length; i++){
			if(i!=0){
				contentArr[i] = contentArr[i].substring(1);
			}
			contentArr[i] += "}";
		}
		
		for(int i=0; i<array.size(); i++){
			
			try{
				
				//System.out.println(eArray.toJSONString().substring(1, eArray.toJSONString().length()-1));
				
				JSONObject obj = (JSONObject) jsonParser.parse(contentArr[i]);
				
				
				String description = obj.get("task").toString();
					
				
				String strTask = description;
				
				if(!strTask.equals(deleteTask)){
					Map floatingMap = new LinkedHashMap();
					
					floatingMap.put("task", description);
					
					floatingArray.add(floatingMap);
				}
			
				//If strTask == deletetask dun include in the new JSONArray
				
		
			}catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return floatingArray;
	}
}
