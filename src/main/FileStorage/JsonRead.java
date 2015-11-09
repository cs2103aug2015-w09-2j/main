package main.FileStorage;

import java.util.ArrayList;

import main.Commands.Task;

import org.json.simple.JSONArray;

//@@author A0125531R
public class JsonRead {
	
	private static final String STORAGE_FILE = "STORAGE_FILE";
	private static final String DONE_FILE = "DONE_FILE";
	private static final String OVERDUE_FILE = "OVERDUE_FILE";

	/**
	 * Read and return a list of event, deadline and/or floating task specified by the taskType
	 * @param taskType the type of task event, deadline, floating or all tasks
	 * @return an ArrayList<Task> of the specified taskType
	 */
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
	
	/**
	 * Read and return a list of done tasks
	 * @return an ArrayList<Task> of done tasks
	 */
	public ArrayList<Task> readDoneTask(){

		JsonFile jsonFile = new JsonFile();
		JsonTask jsonTask = new JsonTask();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(DONE_FILE);
		ArrayList<Task> task = new ArrayList<Task>();
	
		task = jsonTask.allTaskArray(content);
		
		return task;
	}
	
	/**
	 * Read and return a list overdue tasks
	 * @return an ArrayList<Task> of overdue tasks
	 */
	public ArrayList<Task> readOverdueTask(){
		
		JsonFile jsonFile = new JsonFile();
		JsonTask jsonTask = new JsonTask();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(OVERDUE_FILE);
		ArrayList<Task> task = new ArrayList<Task>();
	
		task = jsonTask.allTaskArray(content);
		
		return task;
	}
	
	
}
