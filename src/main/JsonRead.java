package main;

import java.util.ArrayList;
import org.json.simple.JSONArray;

public class JsonRead {
	
	private static final String STORAGE_FILE = "STORAGE_FILE";
	private static final String DONE_FILE = "DONE_FILE";
	private static final String OVERDUE_FILE = "OVERDUE_FILE";

	public ArrayList<Task> readTask(String taskType){
		JsonFile jsonFile = new JsonFile();
		JsonTask jsonTask = new JsonTask();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		ArrayList<Task> task = new ArrayList<Task>();
		
		
		switch(taskType){
			case "EVENT":
				task = jsonTask.eventTaskArray(content.get(0));
				break;
			case "DEADLINE":
				task = jsonTask.deadlineTaskArray(content.get(1));
				break;
			case "FLOATING":
				task = jsonTask.floatingTaskArray(content.get(2));
				break;
			case "ALL":
				task = jsonTask.allTaskArray(content);
				break;
			default:
				System.out.println("JsonFile.java ArrayList<Task> readTask error");
		}
		
		return task;
	}
	
	public ArrayList<Task> readDoneTask(){

		JsonFile jsonFile = new JsonFile();
		JsonTask jsonTask = new JsonTask();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(DONE_FILE);
		ArrayList<Task> task = new ArrayList<Task>();
	
		task = jsonTask.allTaskArray(content);
		
		return task;
	}
	
	public ArrayList<Task> readOverdueTask(){
		
		JsonFile jsonFile = new JsonFile();
		JsonTask jsonTask = new JsonTask();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(OVERDUE_FILE);
		ArrayList<Task> task = new ArrayList<Task>();
	
		task = jsonTask.allTaskArray(content);
		
		return task;
	}
	
	
}
