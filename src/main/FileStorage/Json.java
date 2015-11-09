package main.FileStorage;


import java.io.File;
import java.util.ArrayList;

import main.Commands.Deadline;
import main.Commands.Event;
import main.Commands.Task;
import main.common.DateClass;

import org.json.simple.JSONArray;


//@@author A0125531R
public class Json {

	private static String filePath, donePath, overduePath;
	private static JsonFile jsonFile;
	private static JsonSearch jsonSearch;
	private static JsonRead jsonRead;
	private static JsonWrite jsonWrite;
	private static final String STORAGE_FILE = "STORAGE_FILE";
	private static final String DONE_FILE = "DONE_FILE";
	private static final String OVERDUE_FILE = "OVERDUE_FILE";
	private static final String BEFORE = "BEFORE";
	private static final String ON = "ON";
	private static final String AFTER = "AFTER";


	/**
	 * This constructor initialize all the method
	 * @param filePath the current file directory of the storage file
	 */
	public Json(String filePath, String donePath, String overduePath){
		Json.filePath = filePath;
		Json.donePath = donePath;
		Json.overduePath = overduePath;
		jsonFile = new JsonFile();
		jsonSearch = new JsonSearch();
		jsonRead = new JsonRead();
		jsonWrite = new JsonWrite();
	}

	/**
	 * Write a task into the storage file
	 * @param task a task to be written into the storage file
	 */
	public void writeTask(Task task){

		String description,startDate, startTime, endDate, endTime;
		String taskType = task.getClass().getName();
		taskType = taskType.substring(9).toUpperCase();
		description = task.getDescription();


		switch(taskType){
			case "EVENT":

				startDate = ((Event)task).getStartDate().toString();
				startTime = ((Event)task).getStartTime().toString();
				endDate = ((Event)task).getEndDate().toString();
				endTime = ((Event)task).getEndTime().toString();
				jsonWrite.writeEventTask(description, startDate, startTime, endDate, endTime, STORAGE_FILE);
				break;
			case "DEADLINE":
				endDate = ((Deadline)task).getEndDate().toString();
				endTime = ((Deadline)task).getEndTime().toString();
				jsonWrite.writeDeadlineTask(description, endDate ,endTime, STORAGE_FILE);
				break;
			case "FLOATING":
				jsonWrite.writeFloatingTask(description, STORAGE_FILE);
				break;
		}
	}

	/**
	 * Write a completed task into the done file
	 * @param task a task which has been completed
	 */
	public void writeDoneTask(Task task){
		writeToSpecificFile(task, DONE_FILE);
	}

	/**
	 * Write an overdue task into the overdue file
	 * @param task a task which has been overdue
	 */
	public void writeOverdueTask(Task task){
		writeToSpecificFile(task, OVERDUE_FILE);
	}


	/**
	 * Read and return a list of event tasks
	 * @return an ArrayList<Task> of event tasks
	 */
	public ArrayList<Task> readEventTask(){
		isFileEmpty(filePath);
		return jsonRead.readTask("EVENT");
	}

	/**
	 * Read and return a list of deadline tasks
	 * @return an ArrayList<Task> of deadline tasks
	 */
	public ArrayList<Task> readDeadlineTask(){
		isFileEmpty(filePath);
		return jsonRead.readTask("DEADLINE");
	}

	/**
	 * Read and return a list of floating tasks
	 * @return an ArrayList<Task> of floating tasks
	 */
	public ArrayList<Task> readFloatingTask(){
		isFileEmpty(filePath);
		return jsonRead.readTask("FLOATING");
	}

	/**
	 * Read and return a list of event, deadline and floating tasks
	 * @return an ArrayList<Task> of event, deadline and floating tasks
	 */
	public ArrayList<Task> readAllTask(){
		isFileEmpty(filePath);
		return jsonRead.readTask("ALL");
	}

	/**
	 * Read and return a list of done tasks
	 * @return an ArrayList<Task> of done tasks
	 */
	public ArrayList<Task> readDoneTask(){
		isFileEmpty(donePath);
		return jsonRead.readDoneTask();
	}

	/**
	 * Read an return a list of overdue tasks
	 * @return an ArrayList<Task> of overdue tasks
	 */
	public ArrayList<Task> readOverdueTask(){
		isFileEmpty(overduePath);
		return jsonRead.readOverdueTask();
	}

	/**
	 * Search for all event task which contains the words in the string of keyword
	 * @param keyword the string which contains words
	 * @return an ArrayList<Task> of event tasks which contains the keyword
	 */
	public ArrayList<Task> searchEventTask(String keyword){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchEventTask(keyword, content.get(0));
	}


	/**
	 * Search for all deadline task which contains the words in the string of keyword
	 * @param keyword the string which contains words
	 * @return an ArrayList<Task> of deadline tasks which contains the keyword
	 */
	public ArrayList<Task> searchDeadlineTask(String keyword){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchDeadlineTask(keyword, content.get(1));
	}


	/**
	 * Search for all floating task which contains the words in the string of keyword
	 * @param keyword the string which contains words
	 * @return an ArrayList<Task> of floating tasks which contains the keyword
	 */
	public ArrayList<Task> searchFloatingTask(String keyword){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchFloatingTask(keyword, content.get(2));
	}


	/**
	 * Search for all event, deadline and floating task which contains the words in the string of keyword
	 * @param keyword the string which contains words
	 * @return an ArrayList<Task> of event, deadline and floating tasks which contains the keyword
	 */
	public ArrayList<Task> searchAllTask(String keyword){
		isFileEmpty(filePath);
		return jsonSearch.searchAllTask(keyword);
	}


	/**
	 * Search for event, deadline and floating task which contains the exact task description of the task
	 * @param task the exact task description of the task
	 * @return an ArrayList<Task> of event, deadline and floating task which contains the exact task description
	 */
	public ArrayList<Task>absoluteSearch(String task){
		isFileEmpty(filePath);
		return jsonSearch.absoluteSearchDescription(task, task);
	}

	/**
	 * Search for event, deadline and floating task which contains the exact task description of the task
	 * @param task the exact task description of the task
	 * @param taskInfo additional information such as start date, end date, start time, end time
	 * @return an ArrayList<Task> of event, deadline and floating task which contains the exact task description and taskInfo
	 */
	public ArrayList<Task>absoluteSearch(String task, String taskInfo){
		isFileEmpty(filePath);
		return jsonSearch.absoluteSearchDescription(task, taskInfo);
	}

	/**
	 * Search for all event task before a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of event task before a specified end date
	 */
	public ArrayList<Task>searchEventTaskBeforeDate(String keyword, DateClass date){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchEventTaskByDate(keyword, date, content.get(0), BEFORE);
	}

	/**
	 * Search for all deadline task before a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of deadline task before a specified end date
	 */
	public ArrayList<Task>searchDeadlineTaskBeforeDate(String keyword, DateClass date){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchDeadlineTaskByDate(keyword, date, content.get(1), BEFORE);
	}

	/**
	 * Search for all event and deadline task before a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of event and deadline task before a specified end date
	 */
	public ArrayList<Task> searchAllTaskBeforeDate(String keyword, DateClass date){
		isFileEmpty(filePath);
		return jsonSearch.searchAllTaskByDate(keyword, date, BEFORE);
	}


	/**
	 * Search for all event task on a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of event task on a specified end date
	 */
	public ArrayList<Task>searchEventTaskOnDate(String keyword, DateClass date){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchEventTaskByDate(keyword, date, content.get(0), ON);
	}

	/**
	 * Search for all deadline task on a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of deadline task on a specified end date
	 */
	public ArrayList<Task>searchDeadlineTaskOnDate(String keyword, DateClass date){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchDeadlineTaskByDate(keyword, date, content.get(1), ON);
	}

	/**
	 * Search for all event and deadline task on a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of event and deadline task on a specified end date
	 */
	public ArrayList<Task> searchAllTaskOnDate(String keyword, DateClass date){
		isFileEmpty(filePath);
		return jsonSearch.searchAllTaskByDate(keyword, date, ON);
	}

	/**
	 * Search for all event task after a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of event task after a specified end date
	 */
	public ArrayList<Task>searchEventTaskAfterDate(String keyword, DateClass date){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchEventTaskByDate(keyword, date, content.get(0), AFTER);
	}

	/**
	 * Search for all deadline task after a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of deadline task after a specified end date
	 */
	public ArrayList<Task>searchDeadlineTaskAfterDate(String keyword, DateClass date){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchDeadlineTaskByDate(keyword, date, content.get(1), AFTER);
	}

	/**
	 * Search for all event and deadline task after a specified end date
	 * @param date a end date
	 * @return an ArrayList<Task> of event and deadline task after a specified end date
	 */
	public ArrayList<Task> searchAllTaskAfterDate(String keyword, DateClass date){
		isFileEmpty(filePath);
		return jsonSearch.searchAllTaskByDate(keyword, date, AFTER);
	}

	/**
	 * Search and return all event tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of event tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchEventTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchEventTaskBetweenDates(keyword, startDate, endDate, content.get(0));
	}

	/**
	 * Search and return all deadline tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of deadline tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchDeadlineTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(STORAGE_FILE);
		isFileEmpty(filePath);
		return jsonSearch.searchDeadlineTaskBetweenDates(keyword, startDate, endDate, content.get(1));
	}

	/**
	 * Search and return all event and deadline tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of event and deadline tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchAllTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		isFileEmpty(filePath);
		return jsonSearch.searchAllTaskBetweenDates(keyword, startDate, endDate);
	}


	/**
	 * Delete a specific task from the storage file
	 * @param task a task to be deleted
	 */
	public void deleteFromStorageFile(Task task){
		File file = new File(filePath);
		JsonDelete jsonDelete = new JsonDelete();
		if(task != null){
			if(file.exists()){
				jsonDelete.deleteTask(task, STORAGE_FILE);
			}
		}
	}

	/**
	 * Delete a specific task from the done file
	 * @param task a task to be deleted
	 */
	public void deleteFromDoneFile(Task task){
		File file = new File(donePath);
		JsonDelete jsonDelete = new JsonDelete();
		if(task != null){
			if(file.exists()){
				jsonDelete.deleteTask(task, DONE_FILE);
			}
		}
	}

	/**
	 * Delete a specific task from the overdue file
	 * @param task a task to be deleted
	 */
	public void deleteFromOverdueFile(Task task){
		File file = new File(overduePath);
		JsonDelete jsonDelete = new JsonDelete();
		if(task != null){
			if(file.exists()){
				jsonDelete.deleteTask(task, OVERDUE_FILE);
			}
		}
	}

	/**
	 * Write a task either to the storage, done or overdue file
	 * @param task a task to be written into a file
	 * @param fileType a STORAGE_FILE, DONE_FILE, OVERDUE_FILE
	 */
	private void writeToSpecificFile(Task task, String fileType){
		String description,startDate, startTime, endDate, endTime;
		String taskType = task.getClass().getName();
		taskType = taskType.substring(5).toUpperCase();
		description = task.getDescription();


		switch(taskType){
			case "EVENT":

				startDate = ((Event)task).getStartDate().toString();
				startTime = ((Event)task).getStartTime().toString();
				endDate = ((Event)task).getEndDate().toString();
				endTime = ((Event)task).getEndTime().toString();
				jsonWrite.writeEventTask(description, startDate, startTime, endDate, endTime, fileType);
				break;
			case "DEADLINE":
				endDate = ((Deadline)task).getEndDate().toString();
				endTime = ((Deadline)task).getEndTime().toString();
				jsonWrite.writeDeadlineTask(description, endDate ,endTime, fileType);
				break;
			case "FLOATING":
				jsonWrite.writeFloatingTask(description, fileType);
				break;
		}
	}

	/**
	 * Check if content of an existing file is empty
	 */
	private void isFileEmpty(String path){
		File file = new File(path);

		jsonFile.isJsonFileEmpty(file);
	}
}








