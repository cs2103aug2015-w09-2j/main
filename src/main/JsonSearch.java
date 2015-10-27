package main;

import java.util.ArrayList;

import org.json.simple.JSONArray;

public class JsonSearch {

	public ArrayList<Task> searchEvent(String keyword, JSONArray event){
		
		JsonTask jsonTask = new JsonTask();
		String description,startDate, startTime, endDate, endTime;
		
		ArrayList<Task> eventList = jsonTask.eventTaskArray(event);
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
  	
	public ArrayList<Task> searchDeadline(String keyword, JSONArray deadline){
		JsonTask jsonTask = new JsonTask();
		String description,endDate, endTime;
		ArrayList<Task> deadlineList = jsonTask.deadlineTaskArray(deadline);
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
	
	public ArrayList<Task> searchFloating(String keyword, JSONArray floating){
		JsonTask jsonTask = new JsonTask();
		String description;
		ArrayList<Task> floatingList = jsonTask.floatingTaskArray(floating);
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
	
	public ArrayList<Task> absoluteSearchDescription(String keyword){
		JsonTask jsonTask = new JsonTask();
		String description;
		JsonFile jsonFile = new JsonFile();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent();
		
		ArrayList<Task> eventList = jsonTask.eventTaskArray(content.get(0));
		ArrayList<Task> deadlineList = jsonTask.deadlineTaskArray(content.get(1));
		ArrayList<Task> floatingList = jsonTask.floatingTaskArray(content.get(2));
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<eventList.size(); i++){
			Task task = eventList.get(i);
			description = task.getDescription();
					
			if(description.equals(keyword)){
				searchList.add(task);
			}
			
		}
		
		for(int i=0; i<deadlineList.size(); i++){
			Task task = deadlineList.get(i);
			description = task.getDescription();
				
			if(description.equals(keyword)){
				searchList.add(task);
			}
		}
		
		for(int i=0; i<floatingList.size(); i++){
			Task task = floatingList.get(i);
			description = task.getDescription();
				
			if(description.equals(keyword)){
				searchList.add(task);
			}
		}
		
		return searchList;
	}
	
	public ArrayList<Task> searchAll(String keyword){
		JsonFile jsonFile = new JsonFile();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent();
		ArrayList<Task> event = searchEvent(keyword, content.get(0));
		ArrayList<Task> deadline = searchDeadline(keyword, content.get(1));
		ArrayList<Task> floating = searchFloating(keyword, content.get(2));
		ArrayList<Task> allTasks = new ArrayList<Task>();
		
		for(int i=0; i<event.size(); i++){
			allTasks.add(event.get(i));
		}
		for(int i=0; i<deadline.size(); i++){
			allTasks.add(deadline.get(i));
		}
		for(int i=0; i<floating.size(); i++){
			allTasks.add(floating.get(i));
		}
		
		return allTasks;
	}
	
}
