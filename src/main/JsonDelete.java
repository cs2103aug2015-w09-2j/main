package main;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import main.Deadline;
import main.Event;
import main.Task;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//@@author A0125531R
public class JsonDelete {

	/**
	 * Delete a task from storage, done or overdue file depending of the fileType
	 * @param task a task to be deleted
	 * @param fileType a STORAGE_FILE, DONE_FILE, OVERDUE_FILE
	 */
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

				eventArray = deleteEventTask(eventArray, strTask);
				break;
			case "DEADLINE":
				endDate = ((Deadline)task).getEndDate().toString();
				endTime = ((Deadline)task).getEndTime().toString();
				strTask = description  + " " + endDate + " " + endTime;

				deadlineArray = deleteDeadlineTask(deadlineArray, strTask);
				break;
			case "FLOATING":
				strTask = description;

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
			e1.printStackTrace();
		}
		   String jsonText = out.toString().replace("\\", "");

		FileStorage fs = new FileStorage();
		String filePath = "";
		switch(fileType){
			case "STORAGE_FILE":
				filePath = fs.getFilePath();
				break;
			case "DONE_FILE":
				filePath = fs.getDonePath();
				break;
			case "OVERDUE_FILE":
				filePath = fs.getOverduePath();
				break;
			default:
				System.out.println("writeToJsonFile(String taskType, String arr[], String fileType)" +
						" has a empty filePath");
		}
		jsonFile.JsonPrettyPrint(filePath, jsonText);

	}

	/**
	 * Determine if the task is an event, floating or deadline task
	 * @param task the task to be check
	 * @return the type of the task, either event, floating or deadline
	 */
	private String determineTaskType(Task task){

		String taskType = task.getClass().toString().substring(11).toUpperCase();

		return taskType;

	}

	/**
	 * Delete an event task from the storage file
	 * @param array JSONArray of event task
	 * @param deleteTask task to be deleted
	 * @return the JSONArray of the event task not deleted from the storage file
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray deleteEventTask(JSONArray array, String deleteTask){

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


			}catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		return eventArray;
	}

	/**
	 * Delete a deadline task from the storage file
	 * @param array JSONArray of deadline task
	 * @param deleteTask task to be deleted
	 * @return the JSONArray of the deadline task not deleted from the storage file
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray deleteDeadlineTask(JSONArray array, String deleteTask){

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

			}catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		return deadlineArray;
	}

	/**
	 * Delete a floating task from the storage file
	 * @param array JSONArray of floating task
	 * @param deleteTask task to be deleted
	 * @return the JSONArray of the floating task not deleted from the storage file
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray deleteFloatingTask(JSONArray array, String deleteTask){

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

				JSONObject obj = (JSONObject) jsonParser.parse(contentArr[i]);


				String description = obj.get("task").toString();


				String strTask = description;

				if(!strTask.equals(deleteTask)){
					Map floatingMap = new LinkedHashMap();

					floatingMap.put("task", description);

					floatingArray.add(floatingMap);
				}

			}catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		return floatingArray;
	}
}
