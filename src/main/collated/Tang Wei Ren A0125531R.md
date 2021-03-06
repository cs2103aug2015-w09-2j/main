# Tang Wei Ren A0125531R
###### FileStorage.java
``` java
public class FileStorage {

	private static String filePath;
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
	public void deletOverdueTask(Task task){
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
```
###### Json.java
``` java
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
		taskType = taskType.substring(5).toUpperCase();
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
	
	
		
	
	
	
	
	
	
```
###### JsonArray.java
``` java
public class JsonArray {

	/**
	 * Arrange and sort the JSONArray in the format of {"task", "start-date", "start-time", "end-date", "end-time"} 
	 * and remove the '\' appending in front of '/'
	 * @param event the JSONArray to be formatted
	 * @return a JSONArray in the format of {"task", "start-date", "start-time", "end-date", "end-time"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray eventJsonArray(JSONArray event){
		
		JSONArray eventArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<event.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
				
				Map eventMap =new LinkedHashMap();
				eventMap.put("task", jsonObject.get("task"));
				eventMap.put("start-date", jsonObject.get("start-date"));
				eventMap.put("start-time", jsonObject.get("start-time"));
				eventMap.put("end-date", jsonObject.get("end-date"));
				eventMap.put("end-time", jsonObject.get("end-time"));
								
				eventArray.add(eventMap);				
			}		
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	
		return eventArray;
	}
	
	/**
	 * Arrange and sort the JSONArray in the format of {"task", "end-date", "end-time"} 
	 * and remove the '\' appending in front of '/'
	 * @param deadline the JSONArray to be formatted
	 * @return a JSONArray in the format of {"task", "end-date", "end-time"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray deadlineJsonArray(JSONArray deadline){
		
		JSONArray deadlineArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<deadline.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(deadline.get(i).toString().replace("\\", ""));
				
				Map deadlineMap =new LinkedHashMap();
				deadlineMap.put("task", jsonObject.get("task"));
				deadlineMap.put("end-date", jsonObject.get("end-date"));
				deadlineMap.put("end-time", jsonObject.get("end-time"));
			
				deadlineArray.add(deadlineMap);
			}
			
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		return deadlineArray;
	}
	
	/**
	 * Arrange and sort the JSONArray in the format of {"task"} and remove the '\' appending in front of '/'
	 * @param floating the JSONArray to be formatted
	 * @return a JSONArray in the format of {"task"}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONArray floatingJsonArray(JSONArray floating){
		
		JSONArray floatingArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<floating.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(floating.get(i).toString().replace("\\", ""));
				
				Map floatingMap =new LinkedHashMap();
				floatingMap.put("task", jsonObject.get("task"));				
			
				floatingArray.add(floatingMap);
			}
			
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		return floatingArray;
	}
}
```
###### JsonDelete.java
``` java
public class JsonDelete {

	/**
	 * Delete a task from storage, done or overdue file
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
```
###### JsonFile.java
``` java
public class JsonFile {
	
	
	public JsonFile(){
		
	}
	
	/**
	 * Create a JSON file with the default JSON format
	 * @param file the file to be created
	 */
	public void createJsonFile(File file){
		
		
		
		String open = "{";
		String event = "\"EVENT\":[],";
    	String deadline = "\"DEADLINE\":[],";
    	String floating = "\"FLOATING\":[]"; 
    	String close = "}";
    			
		FileWriter fileWriter;
		
		try {
			fileWriter = new FileWriter(file, false);
			BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

			bufferedWriter.write(open);
			bufferedWriter.newLine();
			bufferedWriter.write(event);
			bufferedWriter.newLine();
			bufferedWriter.write(deadline);
			bufferedWriter.newLine();
			bufferedWriter.write(floating);
			bufferedWriter.newLine();
			bufferedWriter.write(close);
			bufferedWriter.newLine();
			
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to Jsonfile");
		}
		
	}
	
	/**
	 * Check is the JSON file is empty
	 * @param file the file to be checked
	 */
	public void isJsonFileEmpty(File file){
		
		if (file.length() == 0) {
			createJsonFile(file);
		} 

	}
	
	
	/**
	 * This method return the JSONArray of EVENT, DEADLINE, FLOATING
	 * @param fileType determine if the is a STORAGE_FILE, DONE_FILE or OVERDUE_FILE
	 * @return ArrayList of JSONArray containing EVENT, DEADLINE, FLOATING
	 */
	public ArrayList<JSONArray> getJsonFileContent(String fileType){
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
				System.out.println("getJsonFileContent(String fileType) has a empty filePath");
		}
		
		ArrayList<JSONArray> contentList = new ArrayList<JSONArray>();
		
			
		try{
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
		
			contentList.add((JSONArray) jsonObject.get("EVENT"));
			contentList.add((JSONArray) jsonObject.get("DEADLINE"));
			contentList.add((JSONArray) jsonObject.get("FLOATING"));
						
			reader.close();
			
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
		
			e.printStackTrace();
		}
				
		return contentList;
	}
	
	public void JsonPrettyPrint(String filePath, String jsonText){
		try {
			FileWriter file = new FileWriter(filePath, false);
			

			StringBuilder sb = new StringBuilder(jsonText);

			
			sb.insert(sb.indexOf("\"EVENT\":["), "\n\t");
			sb.insert(sb.indexOf("\"EVENT\":[")+9, "\n\t\t");
			sb.insert(sb.indexOf("\"DEADLINE\":["), "\n\t");
			sb.insert(sb.indexOf("\"DEADLINE\":[")+12, "\n\t\t");
			sb.insert(sb.indexOf("\"FLOATING\":["), "\n\t");
			sb.insert(sb.indexOf("\"FLOATING\":[")+12, "\n\t\t");
			sb.insert(sb.length()-2,  "\n\t");
			sb.insert(sb.length()-1,  "\n");
			
			int index = sb.indexOf("{");
			
			while (index >=0){
				index = sb.indexOf("{", index+1);
			    if(index != -1){
			    	sb.insert(index+1, "\n\t\t\t");
			    }
			}
			
			index = sb.indexOf(",\"");
			while (index >=0){
				index = sb.indexOf(",\"", index+1);
			    if(index != -1){
			    	sb.insert(index+1, "\n\t\t\t");
			    }
			    
			}		
			
			index = sb.indexOf("\"}");
			while (index >=0){
				if(index != -1){
			    	sb.insert(index+1, "\n\t\t");
			    }
				index = sb.indexOf("\"}", index+1);	
				
			}
			
			
			
			file.write(sb.toString());
			file.flush();
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
	
	
```
###### JsonRead.java
``` java
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
```
###### JsonSearch.java
``` java
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

		ArrayList<Task> eventList = searchEventTask(keyword, event);
		ArrayList<Task> searchList = new ArrayList<Task>();
		
		for(int i=0; i<eventList.size(); i++){
			Task task = eventList.get(i);
			
			switch(option){
				case "BEFORE":
					if(((Event)task).getStartDate().compareTo(date) < 0 || ((Event)task).getEndDate().compareTo(date) < 0){
						searchList.add(task);
					}
					break;
				case "ON":
					if(((Event)task).getStartDate().compareTo(date) <= 0 && ((Event)task).getEndDate().compareTo(date) >= 0){
						searchList.add(task);
					}
					break;
				case "AFTER":
					if(((Event)task).getStartDate().compareTo(date) > 0 || ((Event)task).getEndDate().compareTo(date) > 0){
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
```
###### JsonTask.java
``` java
public class JsonTask {

	/**
	 * Covert a JSONArray to an ArrayList<Task>
	 * @param event JSONArray of event task to be converted
	 * @return ArrayList<Task> of event tasks
	 */
	public ArrayList<Task> eventTaskArray(JSONArray event){
		
		ArrayList<Task> eventTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();
		
		for(int i=0; i<event.size(); i++){
			
			try{			
				JSONObject obj = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
							
				String strStartDate = obj.get("start-date").toString();
				String strEndDate = obj.get("end-date").toString();
				String strStartTime = obj.get("start-time").toString();
				String strEndTime = obj.get("end-time").toString();
			
				String task = obj.get("task").toString();
				DateClass startDate = new DateClass(strStartDate);
				DateClass endDate = new DateClass(strEndDate);
				TimeClass startTime = new TimeClass(strStartTime);
				TimeClass endTime = new TimeClass(strEndTime);
						
				Event eventTask = new Event(task, startDate, startTime, endDate, endTime);
			
				eventTaskList.add(eventTask);
		
			}catch (ParseException e1) {
			
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				
				e1.printStackTrace();
			} catch (java.text.ParseException e1) {
			
				e1.printStackTrace();
			}
		}
		Collections.sort(eventTaskList);
		return eventTaskList;
	}
	
	/**
	 * Covert a JSONArray to an ArrayList<Task>
	 * @param deadline JSONArray of deadline task to be converted
	 * @return ArrayList<Task> of deadline tasks
	 */
	public ArrayList<Task> deadlineTaskArray(JSONArray deadline){
		
		ArrayList<Task> deadlineTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();
		
		for(int i=0; i<deadline.size(); i++){
			
			try{
				JSONObject obj = (JSONObject) jsonParser.parse(deadline.get(i).toString().replace("\\", ""));
			
				
				String strEndDate = obj.get("end-date").toString();
				String strEndTime = obj.get("end-time").toString();
				
				String task = obj.get("task").toString();
				DateClass endDate = new DateClass(strEndDate);
				TimeClass endTime = new TimeClass(strEndTime);
						
				Deadline deadlineTask = new Deadline(task,endDate, endTime);
			
				deadlineTaskList.add(deadlineTask);
		
			}catch (ParseException e1) {
			
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				
				e1.printStackTrace();
			} catch (java.text.ParseException e1) {
			
				e1.printStackTrace();
			}
		}
		Collections.sort(deadlineTaskList);
		return deadlineTaskList;
	}
	
	/**
	 * Covert a JSONArray to an ArrayList<Task>
	 * @param floating JSONArray of event task to be converted
	 * @return ArrayList<Task> of floating tasks
	 */
	public ArrayList<Task> floatingTaskArray(JSONArray event){
		ArrayList<Task> floatingTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();
		
		for(int i=0; i<event.size(); i++){
			
			try{
				JSONObject obj = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
						
				String task = obj.get("task").toString();
							
				Floating floatingTask = new Floating(task);
			
				floatingTaskList.add(floatingTask);
		
			}catch (ParseException e1) {
			
				e1.printStackTrace();
			}
		}
		Collections.sort(floatingTaskList);
		return floatingTaskList;
		
	}
	
	/**
	 * Convert all JSONArray event, deadline, floating to an ArrayList<Task>
	 * @param content ArrayList<JSONArray> contain event, deadline, floating JSONArray
	 * @return ArrayList<Task> of event, deadline and floating tasks
	 */
	public ArrayList<Task> allTaskArray(ArrayList<JSONArray> content){
		
		ArrayList<Task> event = eventTaskArray(content.get(0));
		ArrayList<Task> deadline = deadlineTaskArray(content.get(1));
		ArrayList<Task> floating = floatingTaskArray(content.get(2));
		
		ArrayList<Task> allTask = new ArrayList<Task>();
		
		for(int i=0; i<event.size(); i++)
		{
			allTask.add(event.get(i));
		}
		for(int i=0; i<deadline.size(); i++)
		{
			allTask.add(deadline.get(i));
		}
		for(int i=0; i<floating.size(); i++)
		{
			allTask.add(floating.get(i));
		}
		
		return allTask;
	}
}
```
###### JsonWrite.java
``` java
public class JsonWrite {

	/**
	 * Write an event task into the storage file
	 * @param description description of the event task
	 * @param startDate start date of the event task
	 * @param startTime start time of the event task
	 * @param endDate end date of the event task
	 * @param endTime end time of the event task
	 * @param fileType STORAGE, DONE or OVERDUE file
	 */
	public void writeEventTask(String description, String startDate, String startTime, 
			String endDate, String endTime, String fileType){
		String arr[] = {description, startDate, startTime, endDate, endTime};
		writeToJsonFile("EVENT", arr, fileType);
	}
	
	/**
	 * Write a deadline task to the storage file
	 * @param description description of the deadline task
	 * @param endDate end date of the deadline task
	 * @param endTime end time of the deadline task
	 * @param fileType STORAGE, DONE or OVERDUE file
	 */
	public void writeDeadlineTask(String description, String endDate ,String endTime, String fileType){
		String arr[] = {description, endDate, endTime};
		writeToJsonFile("DEADLINE", arr, fileType);
	}
	
	/**
	 * Write a floating task to the storage file
	 * @param description description of the floating task
	 * @param fileType STORAGE, DONE or OVERDUE file
	 */
	public void writeFloatingTask(String description, String fileType){
		String arr[] = {description};
		writeToJsonFile("FLOATING", arr, fileType);
		
	}
	
	/**
	 * Write a task to the storage file
	 * @param taskType type of the task event, deadline or floating
	 * @param arr array of string containing the details of each section of a task
	 * @param fileType STORAGE, DONE or OVERDUE file
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void writeToJsonFile(String taskType, String arr[], String fileType){
		
		JsonFile jsonFile = new JsonFile();
		JsonArray jsonArray = new JsonArray();
		Map obj =new LinkedHashMap();
		
		ArrayList<JSONArray> contentList = jsonFile.getJsonFileContent(fileType);
		
		JSONArray eventArray =  jsonArray.eventJsonArray(contentList.get(0));
		JSONArray deadlineArray =  jsonArray.deadlineJsonArray(contentList.get(1));
		JSONArray floatingArray =  jsonArray.floatingJsonArray(contentList.get(2));
		
		
		switch(taskType){
			case "EVENT":
				eventArray.add(newEvent(arr));		
				break;
			case "DEADLINE":
				deadlineArray.add(newDeadline(arr));		
				break;
			case "FLOATING":
				floatingArray.add(newFloating(arr));		
				break;
			default:
				System.out.println("Error");
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
	 * Create a event task an input it in JSON format
	 * @param arr array of string containing each section of a task
	 * @return Map which contains the JSON format {"task", "start-date", "start-time", "end-date", "end-time"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newEvent(String arr[]){
		Map eventMap = new LinkedHashMap();
		
		eventMap.put("task", arr[0]);
		eventMap.put("start-date", arr[1]);
		eventMap.put("start-time", arr[2]);
		eventMap.put("end-date", arr[3]);
		eventMap.put("end-time", arr[4]);
		
		return eventMap;
	}
	
	/**
	 * Create a deadline task an input it in JSON format
	 * @param arr array of string containing each section of a task
	 * @return Map which contains the JSON format {"task", "end-date", "end-time"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newDeadline(String arr[]){
		Map deadlineMap = new LinkedHashMap();
		
		deadlineMap.put("task", arr[0]);
		deadlineMap.put("end-date", arr[1]);
		deadlineMap.put("end-time", arr[2]);
		
		
		return deadlineMap;
	}
	
	/**
	 * Create a floating task an input it in JSON format
	 * @param arr array of string containing each section of a task
	 * @return Map which contains the JSON format {"task"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map newFloating(String arr[]){
		Map floatingMap = new LinkedHashMap();
		
		floatingMap.put("task", arr[0]);
		
		return floatingMap;
	}
}
	
```
