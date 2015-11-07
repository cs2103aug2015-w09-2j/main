package main;

import java.util.ArrayList;
import org.json.simple.JSONArray;

public class JsonSearch {

	private static final String STORAGE_FILE = "STORAGE_FILE";
	
	/**
	 * Search for a list of event task which matches the keywords
	 * Return task if task contains at least one of the word in keyword
	 * @param keyword contains a list of keyword 
	 * @param event JSONArray of event task
	 * @return ArrayList<Task> of event task which matches the keyword
	 */
	public ArrayList<Task> searchEventTask(String keyword, JSONArray event){
		
		JsonTask jsonTask = new JsonTask();
		String description,startDate, startTime, endDate, endTime;
		
		ArrayList<Task> eventList = jsonTask.eventTaskArray(event);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		if(isStringNull(keyword)){
			return eventList;
		}
		String[] keywordArr = keyword.split(" ");
		
		for(int i=0; i<eventList.size(); i++){
			Task task = eventList.get(i);
			description = task.getDescription();
			startDate = ((Event)task).getStartDate().toString();
			startTime = ((Event)task).getStartTime().toString();
			endDate = ((Event)task).getEndDate().toString();
			endTime = ((Event)task).getEndTime().toString();
			
			String strTask = description + " " + startDate + " " + startTime + " " + endDate + " " + endTime;
			
			for(int j=0; j<keywordArr.length; j++){				
				if(strTask.contains(keywordArr[j])){
					searchList.add(task);
					break;
				}
			}
			
		}
		
		return searchList;
	}
  	
	/**
	 * Search for a list of deadline task which matches the keywords
	 * Return task if task contains at least one of the word in keyword
	 * @param keyword contains a list of keyword 
	 * @param deadline JSONArray of deadline task
	 * @return ArrayList<Task> of deadline task which matches the keyword
	 */
	public ArrayList<Task> searchDeadlineTask(String keyword, JSONArray deadline){
		JsonTask jsonTask = new JsonTask();
		String description,endDate, endTime;
		ArrayList<Task> deadlineList = jsonTask.deadlineTaskArray(deadline);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		if(isStringNull(keyword)){
			return deadlineList;
		}
		
		String[] keywordArr = keyword.split(" ");
		
		for(int i=0; i<deadlineList.size(); i++){
			Task task = deadlineList.get(i);
			description = task.getDescription();
			endDate = ((Deadline)task).getEndDate().toString();
			endTime = ((Deadline)task).getEndTime().toString();
			
			String strTask = description  + " " + endDate + " " + endTime;
			
			for(int j=0; j<keywordArr.length; j++){
				if(strTask.contains(keywordArr[j])){
					searchList.add(task);
					break;
				}
			}
		}
		
		return searchList;
	}
	
	/**
	 * Search for a list of floating task which matches the keywords
	 * Return task if task contains at least one of the word in keyword
	 * @param keyword contains a list of keyword 
	 * @param floating JSONArray of floating task
	 * @return ArrayList<Task> of floating task which matches the keyword
	 */
	public ArrayList<Task> searchFloatingTask(String keyword, JSONArray floating){
		JsonTask jsonTask = new JsonTask();
		String description;
		ArrayList<Task> floatingList = jsonTask.floatingTaskArray(floating);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		String[] keywordArr = keyword.split(" ");
		
		for(int i=0; i<floatingList.size(); i++){
			Task task = floatingList.get(i);
			description = task.getDescription();
			
			String strTask = description;
			
			for(int j=0; j<keywordArr.length; j++){
				if(strTask.contains(keywordArr[j])){
					searchList.add(task);
					break;
				}
			}
		}
		
		return searchList;
	}
	
	/**
	 * Search for a list of event, deadline and floating task which matches the keywords
	 * Return task if task contains at least one of the word in keyword
	 * @param keyword contains a list of keyword 
	 * @return ArrayList<Task> of event, deadline and floating task which matches the keyword
	 */
	public ArrayList<Task> searchAllTask(String keyword){
		JsonFile jsonFile = new JsonFile();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		ArrayList<Task> event = searchEventTask(keyword, content.get(0));
		ArrayList<Task> deadline = searchDeadlineTask(keyword, content.get(1));
		ArrayList<Task> floating = searchFloatingTask(keyword, content.get(2));
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
	/**
	 * Search for the task in event, deadline and floating which matches the taskDetails and taskInfo
	 * @param taskDetails description of a task
	 * @param taskInfo either start time, start date, end time, end date of the task
	 * @return an ArrayList<task> of event, deadline and floating tasks which matches taskDetails and taskInfo
	 */
	public ArrayList<Task> absoluteSearchDescription(String taskDetails, String taskInfo){
		JsonTask jsonTask = new JsonTask();
		String description,startDate, startTime, endDate, endTime;
		JsonFile jsonFile = new JsonFile();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		
		ArrayList<Task> eventList = jsonTask.eventTaskArray(content.get(0));
		ArrayList<Task> deadlineList = jsonTask.deadlineTaskArray(content.get(1));
		ArrayList<Task> floatingList = jsonTask.floatingTaskArray(content.get(2));
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<eventList.size(); i++){
			Task task = eventList.get(i);
			description = task.getDescription();
			startDate = ((Event)task).getStartDate().toString();
			startTime = ((Event)task).getStartTime().toString();
			endDate = ((Event)task).getEndDate().toString();
			endTime = ((Event)task).getEndTime().toString();
					
			if(taskDetails.equals(taskInfo)){
				if(description.equals(taskDetails)){
					searchList.add(task);
				}
			}else{
				if(description.equals(taskDetails) && (startDate.contains(taskInfo) || startTime.equals(taskInfo) || 
						endDate.contains(taskInfo) || endTime.equals(taskInfo)))
					searchList.add(task);
			}
			
		}
		
		for(int i=0; i<deadlineList.size(); i++){
			Task task = deadlineList.get(i);
			description = task.getDescription();
			endDate = ((Deadline)task).getEndDate().toString();
			endTime = ((Deadline)task).getEndTime().toString();
				
			if(taskDetails.equals(taskInfo)){
				if(description.equals(taskDetails)){
					searchList.add(task);
				}
			}else{
				if(description.equals(taskDetails) && (endDate.contains(taskInfo) || endTime.equals(taskInfo)))
					searchList.add(task);
			}
		}
		
		for(int i=0; i<floatingList.size(); i++){
			Task task = floatingList.get(i);
			description = task.getDescription();
			
			if(taskDetails.equals(taskInfo)){
				if(description.equals(taskDetails)){
					searchList.add(task);
				}
			}else{
				if(description.equals(taskDetails) && description.equals(taskInfo)){			
					searchList.add(task);
				}
			}
		}
		
		return searchList;
	}
	
	/**
	 * Search for all event task before a specific end date
	 * @param date end date
	 * @param event JSONArray of event task
	 * @return an ArrayList<Task> of event tasks which ends before the specific end date
	 */
	public ArrayList<Task> searchEventTaskByDate(String keyword, DateClass date, JSONArray event, String option){
		//JsonTask jsonTask = new JsonTask();

		//ArrayList<Task> eventList = jsonTask.eventTaskArray(event);
		ArrayList<Task> eventList = searchEventTask(keyword, event);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<eventList.size(); i++){
			Task task = eventList.get(i);
			
			switch(option){
				case "BEFORE":
					if(((Event)task).getEndDate().compareTo(date) < 0){
						searchList.add(task);
					}
					break;
				case "ON":
					if(((Event)task).getEndDate().compareTo(date) == 0){
						searchList.add(task);
					}
					break;
				case "AFTER":
					if(((Event)task).getEndDate().compareTo(date) > 0){
						searchList.add(task);
					}
					break;
				default:
					break;
			}
			
					
		}
		
		return searchList;
	}
	
	/**
	 * Search for all deadline task before a specific end date
	 * @param date end date
	 * @param deadline JSONArray of deadline task
	 * @return an ArrayList<Task> of deadline tasks which ends before the specific end date
	 */
	public ArrayList<Task> searchDeadlineTaskByDate(String keyword, DateClass date, JSONArray deadline, String option){
		//JsonTask jsonTask = new JsonTask();
		
		//ArrayList<Task> deadlineList = jsonTask.deadlineTaskArray(deadline);
		ArrayList<Task> deadlineList = searchDeadlineTask(keyword, deadline);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<deadlineList.size(); i++){
			Task task = deadlineList.get(i);
	
			switch(option){
				case "BEFORE":
					if(((Deadline)task).getEndDate().compareTo(date) < 0){
						searchList.add(task);
					}
					break;
				case "ON":
					if(((Deadline)task).getEndDate().compareTo(date) == 0){
						searchList.add(task);
					}
					break;
				case "AFTER":
					if(((Deadline)task).getEndDate().compareTo(date) > 0){
						searchList.add(task);
					}
					break;
				default:
					break;
			}
		}
		
		return searchList;
	}

	/**
	 * Search for all event and deadline task before a specific end date
	 * @param date end date
	 * @return an ArrayList<Task> of event and deadline tasks which ends before the specific end date
	 */
	public ArrayList<Task> searchAllTaskByDate(String keyword, DateClass date, String option){
		JsonFile jsonFile = new JsonFile();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		ArrayList<Task> event = searchEventTaskByDate(keyword, date, content.get(0), option);
		ArrayList<Task> deadline = searchDeadlineTaskByDate(keyword, date, content.get(1), option);
		ArrayList<Task> allTasks = new ArrayList<Task>();
		
		for(int i=0; i<event.size(); i++){
			allTasks.add(event.get(i));
		}
		for(int i=0; i<deadline.size(); i++){
			allTasks.add(deadline.get(i));
		}
	
		return allTasks;
	}
	
	public ArrayList<Task> searchEventTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate, JSONArray event){
		//JsonTask jsonTask = new JsonTask();
		
		//ArrayList<Task> eventList = jsonTask.eventTaskArray(event);
		ArrayList<Task> eventList = searchEventTask(keyword, event);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<eventList.size(); i++){
			Task task = eventList.get(i);
	
			
			if(((Event)task).getStartDate().compareTo(startDate) >= 0 && ((Event)task).getEndDate().compareTo(endDate) <= 0 ){
				searchList.add(task);
			}
				
		}
		
		return searchList;
	}
	
	public ArrayList<Task> searchDeadlineTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate, JSONArray deadline){
		//JsonTask jsonTask = new JsonTask();
		
		//ArrayList<Task> deadlineList = jsonTask.deadlineTaskArray(deadline);
		ArrayList<Task> deadlineList = searchDeadlineTask(keyword, deadline);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<deadlineList.size(); i++){
			Task task = deadlineList.get(i);
	
			
			if(((Deadline)task).getEndDate().compareTo(startDate) >= 0 && ((Deadline)task).getEndDate().compareTo(endDate) <= 0 ){
				searchList.add(task);
			}
				
		}
		
		return searchList;
	}
	
	public ArrayList<Task> searchAllTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		JsonFile jsonFile = new JsonFile();
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		ArrayList<Task> event = searchEventTaskBetweenDates(keyword, startDate, endDate, content.get(0));
		ArrayList<Task> deadline = searchDeadlineTaskBetweenDates(keyword, startDate, endDate, content.get(1));
		ArrayList<Task> allTasks = new ArrayList<Task>();
		
		for(int i=0; i<event.size(); i++){
			allTasks.add(event.get(i));
		}
		for(int i=0; i<deadline.size(); i++){
			allTasks.add(deadline.get(i));
		}
	
		return allTasks;
	}
	
	private boolean isStringNull(String word){
		if(word==null || word.equals("")){
			return true;
		}
		else{
			return false;
		}
	}
}
