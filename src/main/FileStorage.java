package main;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileStorage {

	private static String filePath;
	private static String pathDir;
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
		//pathDir = System.getProperty("user.home") + "\\VODO_Path";
		//pathName = "\\path.txt";
		jsonFile = new JsonFile();
		pathDir = "";
		pathName = "vodoPath";
		filePath = currFilePath();
		json = new Json(filePath);
		
	
	}
	
	/**
	 * Write a new task into the storage file
	 * @param task the task to be written into the storage file
	 */
	public void writeTask(Task task){
		File file = new File(filePath);
		if(!file.exists()){
			createFile(file);
			//Check file if it is in the json empty format
			jsonFile.createJsonFile(file);
		}
		jsonFile.isJsonFileEmpty(file);
		json.writeTask(task);
	}
	
	/**
	 * Write a new task into the done file
	 * @param task the task to be written into the done file
	 */
	public void writeDoneTask(Task task){
		//File file = new File("done.json");
		File file = new File(globalDonePath);
		if(!file.exists()){
			createFile(file);
			//Check file if it is in the json empty format
			jsonFile.createJsonFile(file);
		}
		jsonFile.isJsonFileEmpty(file);
		json.writeDoneTask(task);
	}
	
	/**
	 * Write a new task into the overdue file
	 * @param task the task to be written into the overdue file
	 */
	public void writeOverdueTask(Task task){
		//File file = new File("overdue.json");
		File file = new File(globalOverduePath);
		if(!file.exists()){
			createFile(file);
			//Check file if it is in the json empty format
			jsonFile.createJsonFile(file);
		}
		jsonFile.isJsonFileEmpty(file);
		json.writeOverdueTask(task);
	}
	
	/**
	 * Read and return all the done tasks in the done file
	 * @return ArrayList<Task> of done tasks
	 */
	public ArrayList<Task> readDoneTask(){
		return json.readDoneTask();
	}
	
	/**
	 * Read and return all the overdue tasks in the done file
	 * @return ArrayList<Task> of overdue tasks
	 */
	public ArrayList<Task> readOverdueTask(){
		return json.readOverdueTask();
	}
	
	/**
	 * Read and return all the event tasks in the storage file
	 * @return ArrayList<Task> of event tasks
	 */
	public ArrayList<Task> readEventTask(){
		return json.readEventTask();
	}
	
	/**
	 * Read and return all the deadline tasks in the storage file
	 * @return ArrayList<Task> of deadline tasks
	 */
	public ArrayList<Task> readDeadlineTask(){
		return json.readDeadlineTask();
	}
	
	/**
	 * Read and return all the floating tasks in the storage file
	 * @return ArrayList<Task> of floating tasks
	 */
	public ArrayList<Task> readFloatingTask(){
		return json.readFloatingTask();
	}
	
	/**
	 * Read and return all event, deadline and floating tasks in the storage file
	 * @return ArrayList<Task> of event, deadline and floating tasks
	 */
	public ArrayList<Task> readAllTask(){
		return json.readAllTask();
	}
	
	/**
	 * Search and return all events tasks which contains the keyword
	 * @param keyword keyword of a task
	 * @return ArrayList<Task> of event tasks which contains the keyword
	 */
	public ArrayList<Task>searchEventTask(String keyword){
		return json.searchEventTask(keyword);
	}
		
	/**
	 * Search and return all deadline tasks which contains the keyword
	 * @param keyword keyword of a task
	 * @return ArrayList<Task> of deadline tasks which contains the keyword
	 */
	public ArrayList<Task>searchDeadlineTask(String keyword){
		return json.searchDeadlineTask(keyword);
	}
	
	/**
	 * Search and return all floating tasks which contains the keyword
	 * @param keyword keyword of a task
	 * @return ArrayList<Task> of floating tasks which contains the keyword
	 */
	public ArrayList<Task>searchFloatingTask(String keyword){
		return json.searchFloatingTask(keyword);
	}
	
	/**
	 * Search and return all event, deadline and floating tasks which contains the keyword
	 * @param keyword keyword of a task
	 * @return ArrayList<Task> of event, deadline and floating tasks which contains the keyword
	 */
	public ArrayList<Task>searchAllTask(String keyword){
		return json.searchAllTask(keyword);
	}
	
	/**
	 * Search and return all event, deadline and floating tasks which matches the task exactly
	 * @param task the complete description of a task
	 * @return ArrayList<Task> of event, deadline and floating tasks which matches the task exactly
	 */
	public ArrayList<Task>absoluteSearch(String task){
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
		return json.absoluteSearch(task, taskInfo);
	}
	
	/**
	 * Search and return all event tasks which ends before or on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event tasks ends before the given date
	 */
	public ArrayList<Task>searchEventTaskBeforeDate(String keyword, DateClass date){
		return json.searchEventTaskBeforeDate(keyword, date);
	}
	
	/**
	 * Search and return all deadline tasks which ends before or on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of deadline tasks ends before the given date
	 */
	public ArrayList<Task>searchDeadlineTaskBeforeDate(String keyword, DateClass date){
		return json.searchDeadlineTaskBeforeDate(keyword, date);
	}
	
	/**
	 * Search and return all event and deadline tasks which ends before or on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event, deadline and tasks ends before the given date
	 */
	public ArrayList<Task>searchAllTaskBeforeDate(String keyword, DateClass date){
		return json.searchAllTaskBeforeDate(keyword, date);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Search and return all event tasks which falls on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event tasks which falls on the given date
	 */
	public ArrayList<Task>searchEventTaskOnDate(String keyword, DateClass date){
		return json.searchEventTaskOnDate(keyword, date);
	}
	
	/**
	 * Search and return all deadline tasks which falls on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of deadline tasks which falls the given date
	 */
	public ArrayList<Task>searchDeadlineTaskOnDate(String keyword, DateClass date){
		return json.searchDeadlineTaskOnDate(keyword, date);
	}
	
	/**
	 * Search and return all event and deadline tasks which falls on the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event and deadline tasks which falls on the given date
	 */
	public ArrayList<Task>searchAllTaskOnDate(String keyword, DateClass date){
		return json.searchAllTaskOnDate(keyword, date);
	}
	
	/**
	 * Search and return all event tasks which are after the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event tasks which are after the given date
	 */
	public ArrayList<Task>searchEventTaskAfterDate(String keyword, DateClass date){
		return json.searchEventTaskAfterDate(keyword, date);
	}
	
	/**
	 * Search and return all deadline tasks which are after the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of deadline tasks which are after the given date
	 */
	public ArrayList<Task>searchDeadlineTaskAfterDate(String keyword, DateClass date){
		return json.searchDeadlineTaskAfterDate(keyword, date);
	}
	
	/**
	 * Search and return all event and deadline tasks which are after the given date
	 * @param date the date in DateClass
	 * @return ArrayList<Task> of event and deadline tasks which are after the given date
	 */
	public ArrayList<Task>searchAllTaskAfterDate(String keyword, DateClass date){
		return json.searchAllTaskAfterDate(keyword, date);
	}
	
	/**
	 * Search and return all event tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of event tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchEventTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		return json.searchEventTaskBetweenDates(keyword, startDate, endDate);
	}
	
	/**
	 * Search and return all deadline tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of deadline tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchDeadlineTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		return json.searchDeadlineTaskBetweenDates(keyword, startDate, endDate);
	}
	
	/**
	 * Search and return all event and deadline tasks which are from startDate to endDate
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return ArrayList<Task> of event and deadline tasks which are from startDate to endDate
	 */
	public ArrayList<Task>searchAllTaskBetweenDates(String keyword, DateClass startDate, DateClass endDate){
		return json.searchAllTaskBetweenDates(keyword, startDate, endDate);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Delete a task from the storage file
	 * @param task task to be deleted
	 */
	public void deleteTask(Task task){
		json.deleteFromStorageFile(task);
	}
	
	/**
	 * Delete a task from the done file
	 * @param task task to be deleted
	 */
	public void deleteDoneTask(Task task){
		json.deleteFromDoneFile(task);
	}
	
	/**
	 * Delete a task from the overdue file
	 * @param task task to be deleted
	 */
	public void deletOverdueTask(Task task){
		json.deleteFromOverdueFile(task);
	}
	/**
	 * This methods updates and set the new storage file path
	 * @param newPath the new storage location which the user wants to store his data
	 */
	public void setFilePath(String newPath){
		
		//Split to get the home directory
		int index = newPath.lastIndexOf("\\");
		String dir = newPath.substring(0, index);
		
		String content = "";
		if(newPath.contains(".json")){
			content = newPath + ";" + dir + "\\done.json;" + dir + "\\overdue.json;";
		}else{
			content = newPath + ".json;" + dir + "\\done.json;" + dir + "\\overdue.json;";
		}
		
		newPath = appendJsonFileType(newPath);
		writeFile(content, pathDir + pathName, false, false);
		File newFile = new File(newPath);
		createFile(newFile);
		
		
		copyFile(newPath);
		filePath = newPath;
		
		
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

		//String defaultDataPath = System.getProperty("user.home") + "\\VODO\\data.txt";
		String defaultDataPath = "data.json";//"data.txt";  //THE WRITE PATH IS HERE!!!!!!!!!!!!!
		String donePath = "done.json";
		String overduePath = "overdue.json"; 
		File pathFile = new File(pathDir + pathName);
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
			//hideFolder(pathDir);
			String content = defaultDataPath + ";" + donePath + ";" + overduePath + ";";
			
			writeFile(content, pathDir + pathName, false, false);
		}
		filePath = readPath(pathDir + pathName);
		//filePath array to get storage, done and overdue file String.split.(",");
		//doneFilePath = array[1];
		//overduePath = array[2];
		//New Code
		
		
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
		     //file.getParentFile().mkdirs();
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
			// TODO Auto-generated catch block
			System.out.println("readPath function error");
		}

        return path;
	}

	/**
	 * This methods copy the content of the old file to a new file
	 * @param newPath the directory of the new storage file
	 */
	private static void copyFile(String newPath){
		
		int index = newPath.lastIndexOf("\\");
		String dir = newPath.substring(0, index);
		newPath = appendJsonFileType(newPath);
		File oldDataFile = new File(filePath);
		File newDataFile = new File(newPath);
		File oldDoneFile = new File(globalDonePath);
		File newDoneFile = new File(dir + "\\done.json");
		File oldOverdueFile = new File(globalOverduePath);
		File newOverdueFile = new File(dir + "\\overdue.json");
		
		try {
			Files.copy(oldDataFile.toPath(), newDataFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(oldDoneFile.toPath(), newDoneFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(oldOverdueFile.toPath(), newOverdueFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			//System.out.println("copied");
		} catch (IOException e) {
			System.out.println("Copy file fail");
		} finally {

		}
	}
	
	private static String appendJsonFileType(String newPath){
		if(newPath.contains(".json")){
			return newPath;
		}
		return  newPath + ".json";
		
	}
	
	
	/**
	 * Search and return all events tasks which contains both keyword1 and keyword2
	 * @param keyword1 first keyword of a task
	 * @param keyword2 second keyword of a task
	 * @return ArrayList<Task> of event tasks which contains both keyword1 and keyword2
	 */
	/*
	public ArrayList<Task>searchEventTask(String keyword1, String keyword2){
		return json.searchEventTask(keyword1, keyword2);
	}
	*/
	
	/**
	 * Search and return all deadline tasks which contains both keyword1 and keyword2
	 * @param keyword1 first keyword of a task
	 * @param keyword2 second keyword of a task
	 * @return ArrayList<Task> of deadline tasks which contains both keyword1 and keyword2
	 */
	/*
	public ArrayList<Task>searchDeadlineTask(String keyword1, String keyword2){
		return json.searchDeadlineTask(keyword1, keyword2);
	}
	*/
	
	/**
	 * Search and return all event, deadline and floating tasks which contains both keyword1 and keyword2
	 * @param keyword1 first keyword of a task
	 * @param keyword2 second keyword of a task
	 * @return ArrayList<Task> of event, deadline and floating tasks which contains both keyword1 and keyword2
	 */
	/*
	public ArrayList<Task>searchAllTask(String keyword1, String keyword2){
		return json.searchAllTask(keyword1, keyword2);
	}
	*/
	
	/**
	 * Search and return all floating tasks which contains both keyword1 and keyword2
	 * @param keyword1 first keyword of a task
	 * @param keyword2 second keyword of a task
	 * @return ArrayList<Task> of floating tasks which contains both keyword1 and keyword2
	 */
	/*
	public ArrayList<Task>searchFloatingTask(String keyword1, String keyword2){
		return json.searchFloatingTask(keyword1, keyword2);
	}
	*/
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////OLD CODE NOT IN USE//////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/*
	/**
	 * ArrayList.get(0) --> ArrayList<Task> [Events]
	 * ArrayList.get(1) --> ArrayList<Task> [Deadline]
	 * ArrayList.get(2) --> ArrayList<Task> [Floating]
	 * @param newPath the new storage location which the user wants to store his data
	 */
	/*
	public ArrayList<ArrayList<Task>> search(String keyword){
		return json.search(keyword);
	}
	*/
	/*
	/**
	 * This methods hides the folder
	 * @param hiddenPath contains the directory path of the folder to be hidden
	 */
	/*
	private static void hideFolder(String hiddenPath){
		String hiddenFolder[] = {"attrib","+h",hiddenPath};

		try {
			Runtime.getRuntime().exec(hiddenFolder);
		} catch (IOException e) {
			System.out.println("hideFile function error");
		}
	}
	*/
	
	
	/*
	public static ArrayList<Task> readTaskFromFile() throws NoSuchFieldException, ParseException{

		String line = null;
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {

            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);


			while((line = bufferedReader.readLine()) != null) {
				String taskLine[] = line.split(" ");
				switch(taskLine[0]){
				case "Event":
					Event event = new Event(taskLine[1],new DateClass(taskLine[2]),new TimeClass(taskLine[3]) , new DateClass(taskLine[4]) , new TimeClass(taskLine[5]));
					tasks.add(event);
					break;
				case "Deadline":
					Deadline deadline = new Deadline(taskLine[1], new DateClass(taskLine[2]) , new TimeClass(taskLine[3]));
					tasks.add(deadline);
					break;
				case "Floating":
					Floating floating = new Floating(taskLine[1]);
					tasks.add(floating);
					break;
				}
			}
            bufferedReader.close();


        }catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        }catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }finally{

        }

        return tasks;
	}

	*/
	/*
	/**
	 * This method writes the user's input text data into the storage file
	 * @param text data to be written into file
	 */
	/*
	public void write(String text){
		
		//writeFile(text, filePath, true, true);
		File file = new File(filePath);
		if(!file.exists()){
			createFile(file);
			//jsonContent.createJsonFile(file);
		}
		//json.writeJson(text);
		writeFile(text, filePath, true, true);
	
	}
	*/
	/*
	/**
	 * This methods retrieve and returns the content of the storage file
	 * which contains a particular key word
	 * @param keyword the key word to find a specific content in the storage file
	 * @return the content of the storage file which contains only the key word
	 */
	
	/*
	public FileData search(String keyword){
		FileData list = searchFile(keyword);

		return list;
	}
	*/
	
	/*
	/**
	 * This methods delete a specific line of the content in the storage file
	 * @param line the line number of the line which is to be deleted
	 * @param data the FileData which contains the hash map that links the line
	 * number to content of the storage file
	 */
	/*
	public void delete(String line, FileData data){

		String fileName = filePath;

		HashMap<String, Integer> originalMap = data.getOriginalMap();
		HashMap<Integer, String> displayMap = data.getDisplayMap();

		try{
			int numLine = Integer.parseInt(line);
			String key = displayMap.get(numLine);
			int deleteNum = originalMap.get(key);

			deleteLine(deleteNum, fileName);

		}catch(NumberFormatException e){

			System.out.println("Error deleting");
		}

	}
	*/
	
	/*
	/**
	 * Retrieve and return the content of the storage file
	 * The content of the storage file is stored in a ArrayList<String>
	 * @return the ArrayList<String> which contains the content of the storage file
	 */
	/*
	public static ArrayList<String> readFile(){

		String line = null;
        ArrayList<String> list = new ArrayList<String>();
        try {

            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);


			while((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
            bufferedReader.close();


        }catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        }catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }finally{

        }

        return list;
	}
	*/
	/*
	/**
	 * This methods retrieve and returns the content of the storage file
	 * @return the content of the storage file
	 */
	/*
	public FileData display(){
		FileData data = getData();

		return data;

	}
	*/
	/*
	/**
	 * This methods gets the content of the storage file
	 * Create a hash map to map the original content of the storage file to its original sequence
	 * Create a hash map to map the content in its new sequence which is sorted in ascending order
	 * Create a new FileData to store the two hash map
	 * @return the FileData which contents two hash map
	 */
	/*
	public FileData getData(){
		ArrayList<String> list = readFile();
		HashMap<String, Integer> originalMap = new HashMap<String, Integer>();

		for(int i=0; i<list.size(); i++){
			originalMap.put(list.get(i), i+1);
		}
		Collections.sort(list);
		HashMap<Integer, String> displayMap = new HashMap<Integer, String>();
		for(int i=0; i<list.size(); i++){
			displayMap.put(i+1,list.get(i));
		}

		FileData data = new FileData(originalMap, displayMap);
		return data;
	}
	*/
	/*
	/**
	 * This methods retrieve and returns the content of the storage file which contains a particular key word
	 * Create a hash map to map the original content of the storage file to its original sequence
	 * Create a hash map to map the content which contains the key word in ascending order
	 * Create a new FileData to store the two hash map
	 * @param keyword the key word to find a specific content in the storage file
	 * @return the FileData which contains the two hash map
	 */
	/*
	private static FileData searchFile(String keyword){

		String line = null;
		ArrayList<String> list = readFile();
		ArrayList<String> keywordList = new ArrayList<String>();
		HashMap<String, Integer> originalMap = new HashMap<String, Integer>();
		HashMap<Integer, String> displayMap = new HashMap<Integer, String>();
		FileData data;

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				if(line.substring(7).contains(keyword)){
					keywordList.add(line);
				}
			}
            bufferedReader.close();

        }catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        }catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }finally{
        	Collections.sort(keywordList);
        }

        for(int i=0; i<list.size(); i++){
			originalMap.put(list.get(i), i+1);
		}

        for(int i=0; i<keywordList.size(); i++){
			displayMap.put(i+1, keywordList.get(i));
		}

        data = new FileData(originalMap, displayMap);

		return data;
	}
	*/
	/*
	public void deleteTask(int taskNumber){
		deleteLine(taskNumber,filePath);
	}
	*/
	/*
	/**
	 * This methods delete the specific line in the storage file
	 * @param numLine	the line number to be deleted
	 * @param fileName	the directory of the storage file
	 */
	/*
	private static void deleteLine(int numLine, String fileName){

		File currFile = new File(fileName);
		File tempFile = new File(currFile.getAbsolutePath() + ".tmp");
		String line = null;
		int count = 0;

		try{
			FileWriter fileWriter = new FileWriter(tempFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			FileReader fileReader = new FileReader(fileName);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        while((line = bufferedReader.readLine()) != null) {
	        	++count;

				if(count == numLine){
					continue;
				}else{
					 bufferedWriter.write(line);
					 bufferedWriter.newLine();
				}
			}
	        bufferedWriter.close();
	        bufferedReader.close();

	        //Delete the original file
	        if (!currFile.delete()) {
	          System.out.println("Could not delete file");
	        }
	        //Rename the new file to the filename the original file had.
	        if (!tempFile.renameTo(currFile)){
	          System.out.println("Could not rename file");
	        }
		}catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

	}
	
	*/
	
	

}


