package main;


import java.io.File;
import java.util.ArrayList;
import org.json.simple.JSONArray;



public class Json {
	
	private static String filePath;
	private static JsonFile jsonFile;
	private static JsonSearch jsonSearch;
	private static JsonRead jsonRead;
	private static JsonWrite jsonWrite;
	
	public Json(){
		Json.filePath = null;
		jsonFile = new JsonFile();
		jsonSearch = new JsonSearch();
		jsonRead = new JsonRead();
		jsonWrite = new JsonWrite();
	}
	public Json(String filePath){
		Json.filePath = filePath;
		jsonFile = new JsonFile();
		jsonSearch = new JsonSearch();
		jsonRead = new JsonRead();
		jsonWrite = new JsonWrite();
		
	}
	
	
	
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
				jsonWrite.writeEventTask(description, startDate, startTime, endDate, endTime);
				break;
			case "DEADLINE":
				endDate = ((Deadline)task).getEndDate().toString();
				endTime = ((Deadline)task).getEndTime().toString();
				jsonWrite.writeDeadlineTask(description, endDate ,endTime);
				break;
			case "FLOATING":
				jsonWrite.writeFloatingTask(description);
				break;
		}
	}
	
	//Check if file is not blank if yes write the empty json format
	public ArrayList<Task> readEventTask(){	
		isFileEmpty();
		return jsonRead.readTask("EVENT");
	}
	
	public ArrayList<Task> readDeadlineTask(){
		isFileEmpty();
		return jsonRead.readTask("DEADLINE");
	}
	
	public ArrayList<Task> readFloatingTask(){
		isFileEmpty();
		return jsonRead.readTask("FLOATING");
	}
	
	public ArrayList<Task> readAllTask(){
		isFileEmpty();
		return jsonRead.readTask("ALL");
	}
	
	public ArrayList<Task> searchEventTask(String keyword){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(); 
		isFileEmpty();
		return jsonSearch.searchEvent(keyword, keyword, content.get(0));
	}
	
	public ArrayList<Task> searchEventTask(String keyword1, String keyword2){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent(); 
		isFileEmpty();
		return jsonSearch.searchEvent(keyword1, keyword2, content.get(0));
	}
	
	public ArrayList<Task> searchDeadlineTask(String keyword){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent();
		isFileEmpty();
		return jsonSearch.searchDeadline(keyword, keyword, content.get(1));
	}
	
	public ArrayList<Task> searchDeadlineTask(String keyword1, String keyword2){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent();
		isFileEmpty();
		return jsonSearch.searchDeadline(keyword1, keyword2, content.get(1));
	}
	
	public ArrayList<Task> searchFloatingTask(String keyword){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent();
		isFileEmpty();
		return jsonSearch.searchFloating(keyword, keyword, content.get(2));
	}
	
	public ArrayList<Task> searchFloatingTask(String keyword1, String keyword2){
		ArrayList<JSONArray> content = jsonFile.getJsonFileContent();
		isFileEmpty();
		return jsonSearch.searchFloating(keyword1, keyword2, content.get(2));
	}
	
	public ArrayList<Task> searchAllTask(String keyword){
		isFileEmpty();
		return jsonSearch.searchAll(keyword, keyword);
	}
	
	public ArrayList<Task> searchAllTask(String keyword1, String keyword2){
		isFileEmpty();
		return jsonSearch.searchAll(keyword1, keyword2);
	}
	
	public ArrayList<Task>absoluteSearch(String task){
		isFileEmpty();
		return jsonSearch.absoluteSearchDescription(task, task);
	}
	
	public ArrayList<Task>absoluteSearch(String task, String taskInfo){
		isFileEmpty();
		return jsonSearch.absoluteSearchDescription(task, taskInfo);
	}
	

	public void delete(Task task){
		File file = new File(filePath);
		JsonDelete jsonDelete = new JsonDelete();
		if(task != null){
			if(file.exists()){
				jsonDelete.deleteTask(task);
			}
		}
	}
	
	
	
	private void isFileEmpty(){
		File file = new File(filePath);
		
		jsonFile.isJsonFileEmpty(file);
	}
	
	
	
		
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	

	
	
	
	
	
	
	/*
	public ArrayList<ArrayList<Task>> search(String keyword){
		isFileEmpty();
		
		ArrayList<ArrayList<Task>> searchResult = new ArrayList<ArrayList<Task>>();
		ArrayList<JSONArray> content = getJsonFileContent();
		
		ArrayList<Task> event = searchEvent(keyword, content.get(0));
		ArrayList<Task> deadline = searchDeadline(keyword, content.get(1));
		ArrayList<Task> floating = searchFloating(keyword, content.get(2));
		
		searchResult.add(event);
		searchResult.add(deadline);
		searchResult.add(floating);
		
		return searchResult;
	}
	*/
	
	/*
	public void writeJson(String text){
				
		String arr[] = getContentArray(text);
			
		switch(arr[1]){
			case "-e":
				writeEvent(newContentArray(arr, 5));				
				break;
			case "-d":
				writeDeadline(newContentArray(arr, 3));		
				break;
			case "-f":
				writeFloating(newContentArray(arr, 1));		
				break;
			default:
				System.out.println("Error");
		}
		
	}
	*/
	/*
	
	private static void writeEvent(String[] arr){
		
		writeToJsonFile("EVENT", arr);
	}
	
	private static void writeDeadline(String[] arr){
		writeToJsonFile("DEADLINE", arr);
	}
	
	private static void writeFloating(String[] arr){
		writeToJsonFile("FLOATING", arr);
	}
	*/
	
	/*
	/**
	 * This method split the string at each blank space and store it into an array
	 * @param text string to be splitted
	 * @return the array of string which had been splitted
	 */
	/*
	private static String[] getContentArray(String text){	
		String arr[] = text.split("\\s+");
		
		//for(int i=0; i<arr.length; i++)
		//	System.out.println(arr[i]);
		
		return arr;
		
	}
	*/
	/*
	/**
	 * This method remove the first two elements of the old array
	 * @param arr[] old array
	 * @return newArr the new array without the first two elements of the old array
	 */
	/*
	private static String[] newContentArray(String[] arr, int num){
		String newArr[] = new String[num];
		int index = 0;
		
		//System.out.println(arr.length-num);
		//System.out.println(arr[2]);
		
		if(arr.length-num != 2)
		{
			for(int i=2; i<=arr.length-num; i++){
				if(i==2){	
					newArr[index] = arr[i]+  " ";
				}else if(i != arr.length-num){
					newArr[index] += arr[i] + " ";
				}else{
					newArr[index++] += arr[i];
				}
			}
		}else{
			newArr[index++] = arr[2];
		}
		for(int j=arr.length-num+1; j<arr.length; j++){
			newArr[index++] = arr[j];
		}
		
		
		//for(int i=0; i<newArr.length; i++){
		//	System.out.println(newArr[i]);
		//}
		
		
		
		return newArr;
	}
	
	*/
	
	
	
	
	/*
	/**
	 * Keep for the sake of keeping cause I also don't know
	 */
	/*
	private static void readJsonFile(){
		
		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			// get an array from the JSON object
			JSONArray event = (JSONArray) jsonObject.get("EVENT");
			
			
			if(event.get(0).toString().equals("{}"))
				System.out.println("event:" + event.size());
			else
				System.out.println("pig" + " '" + event.get(0) + "'");
			
			// take the elements of the json array
			for(int i=0; i<event.size(); i++){
				System.out.println("The " + i + " event of the array: " + event.get(i));
			}
			
			// get an array from the JSON object
			JSONArray deadline = (JSONArray) jsonObject.get("DEADLINE");
			// take the elements of the json array
			for(int i=0; i<deadline.size(); i++){
				System.out.println("The " + i + " deadline of the array: " + deadline.get(i));
			}
			
			// get an array from the JSON object
			JSONArray floating = (JSONArray) jsonObject.get("FLOATING");
			// take the elements of the json array
			for(int i=0; i<floating.size(); i++){
				System.out.println("The " + i + " floating of the array: " + floating.get(i));
			}
						
			
			Iterator i = event.iterator();

			// take each value from the json array separately
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				System.out.println("language "+ innerObj.get("task") + 
						" with level " + innerObj.get("start-date"));
			}
			
			

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

}
