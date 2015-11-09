package main;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import main.Task;
import main.DateClass;

//@@author A0125531R
public class FileStorage {

	private static String filePath;
/*	private static FileStorage oneFileStorage = null;*/
	private static String pathName;
	private static Json json;
	private static JsonFile jsonFile;
	private static String globalDonePath;
	private static String globalOverduePath;

	/**
	 * This constructor will get and stores the directory of the
	 * file storage into "filePath when the application is initialize
	 */
	public FileStorage(){
		jsonFile = new JsonFile();
		pathName = "vodoPath";
		filePath = currFilePath();
		json = new Json(filePath, globalDonePath, globalOverduePath);

	}

/*	public static FileStorage getInstance(){
		if(oneFileStorage==null){
			oneFileStorage = new FileStorage();
		}
		return oneFileStorage;
	}*/
	/**
	 * Write a new task into the storage file
	 * @param task the task to be written into the storage file
	 */
	public void writeTask(Task task){
		File file = new File(filePath);
		checkFileExist();
		jsonFile.isJsonFileEmpty(file);
		json.writeTask(task);
	}

	/**
	 * Write a new task into the done file
	 * @param task the task to be written into the done file
	 */
	public void writeDoneTask(Task task){
		File file = new File(globalDonePath);
		checkFileExist();
		jsonFile.isJsonFileEmpty(file);
		json.writeDoneTask(task);
	}

	/**
	 * Write a new task into the overdue file
	 * @param task the task to be written into the overdue file
	 */
	public void writeOverdueTask(Task task){
		File file = new File(globalOverduePath);
		checkFileExist();
		jsonFile.isJsonFileEmpty(file);
		json.writeOverdueTask(task);
	}

	/**
	 * Read and return all the done tasks in the done file
	 * @return ArrayList<Task> of done tasks
	 */
	public ArrayList<Task> readDoneTask(){
		checkFileExist();
		return json.readDoneTask();
	}

	/**
	 * Read and return all the overdue tasks in the done file
	 * @return ArrayList<Task> of overdue tasks
	 */
	public ArrayList<Task> readOverdueTask(){
		checkFileExist();
		return json.readOverdueTask();
	}

	/**
	 * Read and return all the event tasks in the storage file
	 * @return ArrayList<Task> of event tasks
	 */
	public ArrayList<Task> readEventTask(){
		checkFileExist();
		return json.readEventTask();
	}

	/**
	 * Read and return all the deadline tasks in the storage file
	 * @return ArrayList<Task> of deadline tasks
	 */
	public ArrayList<Task> readDeadlineTask(){
		checkFileExist();
		return json.readDeadlineTask();
	}

	/**
	 * Read and return all the floating tasks in the storage file
	 * @return ArrayList<Task> of floating tasks
	 */
	public ArrayList<Task> readFloatingTask(){
		checkFileExist();
		return json.readFloatingTask();
	}

	/**
	 * Read and return all event, deadline and floating tasks in the storage file
	 * @return ArrayList<Task> of event, deadline and floating tasks
	 */
	public ArrayList<Task> readAllTask(){
		checkFileExist();
		return json.readAllTask();
	}

	/**
	 * Search and return all events tasks which contains the keyword
	 * @param keyword keyword of a task
	 * @return ArrayList<Task> of event tasks which contains the keyword
	 */
	public ArrayList<Task>searchEventTask(String keyword){
		checkFileExist();
		return json.searchEventTask(keyword);
	}

	/**
	 * Search and return all deadline tasks which contains the keyword
	 * @param keyword keyword of a task
	 * @return ArrayList<Task> of deadline tasks which contains the keyword
	 */
	public ArrayList<Task>searchDeadlineTask(String keyword){
		checkFileExist();
		return json.searchDeadlineTask(keyword);
	}

	/**
	 * Search and return all floating tasks which contains the keyword
	 * @param keyword keyword of a task
	 * @return ArrayList<Task> of floating tasks which contains the keyword
	 */
	public ArrayList<Task>searchFloatingTask(String keyword){
		checkFileExist();
		return json.searchFloatingTask(keyword);
	}

	/**
	 * Search and return all event, deadline and floating tasks which contains the keyword
	 * @param keyword keyword of a task
	 * @return ArrayList<Task> of event, deadline and floating tasks which contains the keyword
	 */
	public ArrayList<Task>searchAllTask(String keyword){
		checkFileExist();
		return json.searchAllTask(keyword);
	}

	/**
	 * Search and return all event, deadline and floating tasks which matches the task exactly
	 * @param task the complete description of a task
	 * @return ArrayList<Task> of event, deadline and floating tasks which matches the task exactly
	 */
	public ArrayList<Task>absoluteSearch(String task){
		checkFileExist();
		return json.absoluteSearch(task);
	}

	/**
	 * Search and return all event, deadline and floating tasks which matches
	 * both the task and taskInfo exactly. taskInfo can be the start date, start time,
	 * end date or end time of the task itself
	 * @param description the complete description of a task
	 * @param description the complete description of a task
	 * @return ArrayList<Task> of event, deadline and floating tasks which matches
	 * both the task and taskInfo exactly
	 */
	public ArrayList<Task>absoluteSearch(String task, String taskInfo){
		checkFileExist();
		return json.absoluteSearch(task, taskInfo);
	}

	/**
	 * Search and return all event tasks which ends before or on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event tasks ends before the given date
	 */
	public ArrayList<Task>searchEventTaskBeforeDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchEventTaskBeforeDate(keyword, date);
	}

	/**
	 * Search and return all deadline tasks which ends before or on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of deadline tasks ends before the given date
	 */
	public ArrayList<Task>searchDeadlineTaskBeforeDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchDeadlineTaskBeforeDate(keyword, date);
	}

	/**
	 * Search and return all event and deadline tasks which ends before or on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event, deadline and tasks ends before the given date
	 */
	public ArrayList<Task>searchAllTaskBeforeDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchAllTaskBeforeDate(keyword, date);
	}

	/**
	 * Search and return all event tasks which falls on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event tasks which falls on the given date
	 */
	public ArrayList<Task>searchEventTaskOnDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchEventTaskOnDate(keyword, date);
	}

	/**
	 * Search and return all deadline tasks which falls on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of deadline tasks which falls the given date
	 */
	public ArrayList<Task>searchDeadlineTaskOnDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchDeadlineTaskOnDate(keyword, date);
	}

	/**
	 * Search and return all event and deadline tasks which falls on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event and deadline tasks which falls on the given date
	 */
	public ArrayList<Task>searchAllTaskOnDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchAllTaskOnDate(keyword, date);
	}

	/**
	 * Search and return all event tasks which are after the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event tasks which are after the given date
	 */
	public ArrayList<Task>searchEventTaskAfterDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchEventTaskAfterDate(keyword, date);
	}

	/**
	 * Search and return all deadline tasks which are after the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of deadline tasks which are after the given date
	 */
	public ArrayList<Task>searchDeadlineTaskAfterDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchDeadlineTaskAfterDate(keyword, date);
	}

	/**
	 * Search and return all event and deadline tasks which are after the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event and deadline tasks which are after the given date
	 */
	public ArrayList<Task>searchAllTaskAfterDate(String keyword, DateClass date){
		checkFileExist();
		return json.searchAllTaskAfterDate(keyword, date);
	}

	/**
	 * Search and return all event tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of event tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchEventTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		checkFileExist();
		return json.searchEventTaskBetweenDates(keyword, startDate, endDate);
	}

	/**
	 * Search and return all deadline tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of deadline tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchDeadlineTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		checkFileExist();
		return json.searchDeadlineTaskBetweenDates(keyword, startDate, endDate);
	}

	/**
	 * Search and return all event and deadline tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of event and deadline tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchAllTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		checkFileExist();
		return json.searchAllTaskBetweenDates(keyword, startDate, endDate);
	}


	/**
	 * Delete a task from the storage file
	 * @param task task to be deleted
	 */
	public void deleteTask(Task task){
		checkFileExist();
		json.deleteFromStorageFile(task);
	}

	/**
	 * Delete a task from the done file
	 * @param task task to be deleted
	 */
	public void deleteDoneTask(Task task){
		checkFileExist();
		json.deleteFromDoneFile(task);
	}

	/**
	 * Delete a task from the overdue file
	 * @param task task to be deleted
	 */
	public void deleteOverdueTask(Task task){
		checkFileExist();
		json.deleteFromOverdueFile(task);
	}
	/**
	 * This methods updates and set the new storage file path
	 * @param newPath the new storage location which the user wants to store his data
	 */
	public void setFilePath(String dir){

		String content = dir + "\\data.json;" + dir + "\\done.json;" + dir + "\\overdue.json;";

		writeFile(content, pathName, false, false);
		File newFile = new File(dir + "\\data.json");
		createFile(newFile);


		copyFile(dir);
		filePath = dir + "\\data.json";

	}

	/**
	 * The methods retrieve and return the current directory of the storage file
	 * @return the directory of the storage file
	 */
	public String getFilePath(){
		return filePath;
	}

	/**
	 * The methods retrieve and return the current directory of the storage file
	 * @return the directory of the storage file
	 */
	public String getDonePath(){
		return globalDonePath;
	}

	/**
	 * The methods retrieve and return the current directory of the storage file
	 * @return the directory of the storage file
	 */
	public String getOverduePath(){
		return globalOverduePath;
	}


	/**
	 * This method retrieves and return the current directory of the storage file
	 * @return the current directory of the storage file
	 */
	private static String currFilePath(){

		String defaultDataPath = "data.json";
		String donePath = "done.json";
		String overduePath = "overdue.json";
		File pathFile = new File(pathName);
		File dataFile = new File(defaultDataPath);
		File doneFile = new File(donePath);
		File overdueFile = new File(overduePath);
		String filePath;

		if(!pathFile.exists()){
			createFile(pathFile);
			createFile(dataFile);
			createFile(doneFile);
			createFile(overdueFile);
			jsonFile.createJsonFile(dataFile);
			jsonFile.createJsonFile(doneFile);
			jsonFile.createJsonFile(overdueFile);

			String content = defaultDataPath + ";" + donePath + ";" + overduePath + ";";

			writeFile(content, pathName, false, false);
		}
		filePath = readPath(pathName);

		String[] pathArr = filePath.split(";");

		filePath = pathArr[0];
		globalDonePath = pathArr[1].substring(1);
		globalOverduePath = pathArr[2].substring(1);


		return filePath;
	}

	/**
	 * This methods create a new file and folder it they do not exist in the system
	 * @param file contains the information of the file name to be created
	 */
	private static void createFile(File file){
		if(!file.exists()){
		     try {
				file.createNewFile();
		     }catch (IOException e) {
		    	 System.out.println("Create File Fail");
		     }finally{

		     }
		  }
	}

	/**
	 * This methods writes the date into the storage file
	 * @param text		the content to be written into the storage file
	 * @param pathName	the directory path of the storage file
	 * @param isAppend	true: append the data in the existing file / false: overwrite the existing file
	 * @param isNewLine true: add a new line to the file / false: does not add a new line to the file
	 */
	private static void writeFile(String text, String pathName, boolean isAppend, boolean isNewLine){
		File file = new File(pathName);

		if(!file.exists()){
			createFile(file);
		}

		try {
			FileWriter fileWriter = new FileWriter(file, isAppend);
			BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

			StringBuilder sb = new StringBuilder(text);
			int index = sb.indexOf(";");
			while (index >=0){

				if(index != -1){
			    	sb.insert(index+1, "\n");
			    }
				index = sb.indexOf(";", index+1);

			}

			bufferedWriter.write(sb.toString());

			if(isNewLine == true){
				bufferedWriter.newLine();
			}

			bufferedWriter.close();
		}catch(IOException ex){
			System.out.println("Error writing to file '"+ filePath + "'");
		}finally{

		}
	}

	/**
	 * Retrieve and returns the current path directory of the storage file
	 * which is store in path.txt
	 * @param fileName the directory path that stores the path.txt
	 * @return the directory path of the storage file
	 */
	private static String readPath(String fileName){

		String path = null;

		try {
			path = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			System.out.println("readPath function error");
		}

        return path;
	}

	/**
	 * This methods copy the content of the old file to a new file
	 * @param newPath the directory of the new storage file
	 */
	private static void copyFile(String dir){

		File oldDataFile = new File(filePath);
		File newDataFile = new File(dir + "\\data.json");
		File oldDoneFile = new File(globalDonePath);
		File newDoneFile = new File(dir + "\\done.json");
		File oldOverdueFile = new File(globalOverduePath);
		File newOverdueFile = new File(dir + "\\overdue.json");

		try {
			Files.copy(oldDataFile.toPath(), newDataFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(oldDoneFile.toPath(), newDoneFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(oldOverdueFile.toPath(), newOverdueFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			System.out.println("Copy file fail");
		} finally {

		}
	}

	private static void checkFileExist(){
		File storageFile = new File(filePath);
		File doneFile = new File(globalDonePath);
		File overdueFile = new File(globalOverduePath);

		if(!storageFile.exists()){
			createFile(storageFile);
			jsonFile.createJsonFile(storageFile);
		}
		if(!doneFile.exists()){
			createFile(doneFile);
			jsonFile.createJsonFile(doneFile);
		}
		if(!overdueFile.exists()){
			createFile(overdueFile);
			jsonFile.createJsonFile(overdueFile);
		}
	}
}
