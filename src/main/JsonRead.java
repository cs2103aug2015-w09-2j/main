package main;

import java.util.ArrayList;
import org.json.simple.JSONArray;

public class JsonRead {

	public ArrayList<Task> readTask(String taskType){
		JsonFile jsonFile = new JsonFile();
		JsonTask jsonTask = new JsonTask();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent();
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

		//task = jsonTask.doneTaskArray(content);
		//Different file;
		//Create a get done file in json
	
		return null;
	}
	
	public ArrayList<Task> readOverdueTask(){
		return null;
	}
	
	
}
