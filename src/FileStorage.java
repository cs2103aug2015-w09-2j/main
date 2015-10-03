import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;


public class FileStorage {
	
	private static String filePath;
	private static String pathDir;
	private static String pathName;
	
	/**
	 * This constructor gets the file storage path at the start of the application
	 */
	public FileStorage(){
		pathDir = System.getProperty("user.home") + "\\VODO_Path";
		pathName = "\\path.txt";
		filePath = currFilePath();

	}
	
	/**
	 * This method writes the user's input text data into the storage file
	 * @param text data to be written into file
	 */
	public void write(String text){
		writeFile(text, filePath, true, true);
	}
	
	/**
	 * This methods updates and set the new storage file path
	 * @param newPath the new storage location which the user wants to store his data
	 */
	public void setFilePath(String newPath){
		writeFile(newPath, pathDir + pathName, false, false);
		
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
	 * This methods copy the content of the old file to a new file
	 * @param newPath the directory of the new storage file
	 */
	public void copyFile(String newPath){
		
		File oldFile = new File(filePath);
		File newFile = new File(newPath);
		try {
			Files.copy(oldFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("Copy file fail");			
		} finally {
			
		}
	}
	
	/**
	 * This methods retrieve and returns the content of the storage file
	 * @return the content of the storage file
	 */
	public FileData display(){
		FileData data = getData();
		
		return data;
		
	}
	
	/**
	 * This methods retrieve and returns the content of the storage file
	 * which contains a particular key word
	 * @param keyword the key word to find a specific content in the storage file
	 * @return the content of the storage file which contains only the key word
	 */
	public FileData search(String keyword){
		FileData list = searchFile(keyword);
		
		return list;
	}
	
	/**
	 * This methods delete a specific line of the content in the storage file
	 * @param line the line number of the line which is to be deleted
	 * @param data the FileData which contains the hash map that links the line 
	 * number to content of the storage file
	 */
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
	
	/**
	 * This methods gets the content of the storage file
	 * Create a hash map to map the original content of the storage file to its original sequence
	 * Create a hash map to map the content in its new sequence which is sorted in ascending order
	 * Create a new FileData to store the two hash map
	 * @return the FileData which contents two hash map
	 */
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
	
	
	/**
	 * This method retrieves and return the current directory of the storage file
	 * @return the current directory of the storage file
	 */
	private static String currFilePath(){
		
		String defaultDataPath = System.getProperty("user.home") + "\\VODO\\data.txt";
		
		File pathFile = new File(pathDir + pathName);
		File dataFile = new File(defaultDataPath);
		String filePath;
				
		if(!pathFile.exists()){				
			createFile(pathFile);
			createFile(dataFile);
			hideFolder(pathDir);
			writeFile(defaultDataPath, pathDir + pathName, false, false);				
		}
	
		filePath = readPath(pathDir + pathName);
		
		return filePath;
	}
	
	/**
	 * This methods create a new file and folder it they do not exist in the system
	 * @param file contains the information of the file name to be created
	 */
	private static void createFile(File file){
		if(!file.exists()){	 
		     file.getParentFile().mkdirs();
		     try {
				file.createNewFile();			
		     }catch (IOException e) {
		    	 System.out.println("Create File Fail");
		     }finally{
		    	 
		     }
		  }
	}
	
	/**
	 * This methods hides the folder
	 * @param hiddenPath contains the directory path of the folder to be hidden
	 */
	private static void hideFolder(String hiddenPath){
		String hiddenFolder[] = {"attrib","+h",hiddenPath};
	
		try {
			Runtime.getRuntime().exec(hiddenFolder);
		} catch (IOException e) {
			System.out.println("hideFile function error");
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
				
			bufferedWriter.write(text);
			
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
	 * Retrieve and return the content of the storage file
	 * The content of the storage file is stored in a ArrayList<String>
	 * @return the ArrayList<String> which contains the content of the storage file
	 */
	private static ArrayList<String> readFile(){
		
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
	
	
	@SuppressWarnings("unused")
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
				case "DeadLine":
					Deadline deadline = new Deadline(taskLine[1],new DateClass(taskLine[2]));
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
	
	

	/**
	 * This methods retrieve and returns the content of the storage file which contains a particular key word
	 * Create a hash map to map the original content of the storage file to its original sequence
	 * Create a hash map to map the content which contains the key word in ascending order
	 * Create a new FileData to store the two hash map
	 * @param keyword the key word to find a specific content in the storage file
	 * @return the FileData which contains the two hash map
	 */
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
				if(line.contains(keyword)){
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
	
		
	/**
	 * This methods delete the specific line in the storage file
	 * @param numLine	the line number to be deleted
	 * @param fileName	the directory of the storage file
	 */
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
		
}


