package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileContent {
	
	public JsonFileContent(){
		
	}

	public void createJsonFile(File file){
		
		//createFile(file);
		
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
			// TODO Auto-generated catch block
			System.out.println("Error writing to Jsonfile");
		}
		
	}
	
	public void isJsonFileEmpty(File file){
		
		if (file.length() == 0) {
			createJsonFile(file);
		} 

	}

	
}
