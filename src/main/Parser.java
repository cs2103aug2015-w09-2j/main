package main;

import java.text.ParseException;

/***
 * 
 * @author Razali
 *
 */

public class Parser {
	
	

	/*** METHODS ***/
	
	
	private  String getWord(int intIndex, String strText){
		String[] words = strText.split(" ");
		
		if(intIndex < 0 || intIndex >= words.length)
			return null;
		
		return words[intIndex];
	}
	
	private  String removeNWords(int numOfWordsToRemove, String strText){
		int intRemoved = 0;
		String strWord;
		
		while(intRemoved != numOfWordsToRemove){
			strWord = getWord(0, strText);
			strText = strText.replaceFirst(strWord, "").trim();
			
			intRemoved++;
		}
		
		return strText;
	}
	
	private  int getNumberOfWords(String strText){
		return strText.split(" ").length;
	}
	
	/**
	 * 
	 * @param strCommand A command to parse to an task
	 * @return TaskPair o
	 */
	public  TaskPair parse(String strCommand){
		
		CommandType.Types commandType = getCommandType(strCommand);
		String strDescription = null;
		DateClass startDate = null, endDate = null;
		TimeClass startTime = null, endTime = null;
		Task task = null;
		
		switch(commandType){
			case ADD_DEADLINE:
				//Remove "add -d"
				strCommand = removeNWords(2, strCommand);
				strDescription = getDescription(strCommand);
				//Remove description
				strCommand = removeNWords(getNumberOfWords(strDescription), strCommand);
				//Get Date
				endDate = getDate(strCommand);
				strCommand = removeNWords(1, strCommand);
				
				endTime = getTime(strCommand);
				
				task = new Deadline(strDescription, endDate, endTime);
				return new TaskPair(task, commandType);
				
			case ADD_EVENT:
				//Remove "add -e"
				strCommand = removeNWords(2, strCommand);
				strDescription = getDescription(strCommand);
				//Remove description
				strCommand = removeNWords(getNumberOfWords(strDescription), strCommand);

				startDate = getDate(strCommand);
				strCommand = removeNWords(1, strCommand);
				
				startTime = getTime(strCommand);
				strCommand = removeNWords(1, strCommand);
				
				endDate = getDate(strCommand);
				strCommand = removeNWords(1, strCommand);
				
				endTime = getTime(strCommand);
				strCommand = removeNWords(1, strCommand);
				
				task = new Event(strDescription, startDate, startTime, endDate, endTime);
				return new TaskPair(task, commandType);
				
			case ADD_FLOATING:
				//Remove "add -f"
				strCommand = removeNWords(2, strCommand);
				strDescription = strCommand; //rest of the command string is description
				
				task = new Floating(strDescription);
				return new TaskPair(task, commandType);
			
			case SEARCH:
				
				return new TaskPair(null, commandType);
				
			
			
			case UPDATE:
				strCommand = removeNWords(1, strCommand);
				Update update = parseUpdateCommand(strCommand); 
				return new TaskPair(update, commandType);

			
			default:
				return new TaskPair(null, commandType);
		}
		
	}


	//ublic static void main(String[] args){
	//	Parser p = new Parser();
	//	String command = "update new swimming -d swimming";
	//
	//	TaskPair t = p.parse(command);
	//	System.out.println(((Update)(t.getTask())).getDescription());
	//
	//

	/***
	 * Given the input command, returns you the type of command.
	 * @param strCommand Input command to parse
	 * @return An enum element from CommandType.Types
	 */
	private  CommandType.Types getCommandType(String strCommand){
		String strFirstWordFromCommand = getWord(0, strCommand);
		strCommand = strCommand.replace(strFirstWordFromCommand, "").trim();
		
		switch(strFirstWordFromCommand){
		
			//After "add", we can have -e(Event) -f(Floating) or -d(DeadLine)
			//We need to parse them by getting the 2nd word
			case "add":
				String strNextWord = getWord(0, strCommand);
				CommandType.TaskTypes taskType = getTaskType(strNextWord);
				
				//Match CommandType.TaskTypes to CommandType.Types
				for(CommandType.Types type: CommandType.Types.values()){
					if(type.toString().equals(taskType.toString())){
						return type;
					}
				}
				//Mismatch
				return CommandType.Types.UNKNOWN;
			
				
			case "delete":
				return CommandType.Types.DELETE;
				
			case "display":
				return CommandType.Types.DISPLAY;
				
			case "update":
				return CommandType.Types.UPDATE;
				
			case "search":
				return CommandType.Types.SEARCH;
				
			default:
				return CommandType.Types.UNKNOWN;
			
		}
		
		
	}
	
	
	/***
	 * This function returns the type of task from a given delimiter such as
	 * "-d", "-e" or "-f".   An empty string represents unknown.
	 * @param strDelimeter Comprises of "-d" for deadline, "-e" for event or "-f" for float.
	 * @return Returns the Task type
	 */
	private  CommandType.TaskTypes getTaskType(String strDelimiter){
		for(CommandType.TaskTypes taskType : CommandType.TaskTypes.values()){
			String strTaskType = taskType.toString();
			
			if(strTaskType.equals(strDelimiter)){
				return taskType;
			}
		}
		
		return CommandType.TaskTypes.UNKNOWN;
	}
	
	
	private String getSearchString(String strCommand){
		StringBuilder sb = new StringBuilder();
		
		String word = getWord(0, strCommand);
		
		while(!word.equals("-d") && !word.equals("-e") && !word.equals("-s")){
			sb.append(word + " ");
			strCommand = removeNWords(1, strCommand);
			word = getWord(0, strCommand);
		}
		
		return sb.toString().trim();
	}
	
	private  String getDescription(String strCommand){
		StringBuilder sb = new StringBuilder();
		int intWordIndex = 0;
		
		String strNextWord = getWord(intWordIndex, strCommand);
		
		while(DateHandler.tryParse(strNextWord) == null){
			sb.append(strNextWord + " ");
			intWordIndex++;
			strNextWord = getWord(intWordIndex, strCommand);
			if(strNextWord == null)
				break;
		}
		
		String strDescription = sb.toString().trim();
		
		return strDescription;
	}
	
	private Update parseUpdateCommand(String strCommand){
		
		String strSearchString = getSearchString(strCommand);
		
		Update update = new Update(strSearchString);
		
		strCommand = removeNWords(getNumberOfWords(strSearchString), strCommand);
		
		/*
		 * Try to get date followed by time
		 */
		
		
		while(!strCommand.equals("")){
			//Get delimiter
			String delimiter = getWord(0, strCommand);
			strCommand = removeNWords(1, strCommand);
			
			switch(delimiter){
				case "-d":
					String strDescription = removeDelimiters(getDescription(strCommand));
					update.setDescription(strDescription);
					
					strCommand = removeNWords(strDescription.split(" ").length, strCommand);
					break;
				case "-e":
					DateClass endDate = getDate(strCommand);
					strCommand = removeNWords(1, strCommand);
					TimeClass endTime = getTime(strCommand);
					strCommand = removeNWords(1, strCommand);
					update.setEndDate(endDate);
					update.setEndTime(endTime);
					
					break;
				case "-s":
					DateClass startDate = getDate(strCommand);
					strCommand = removeNWords(1, strCommand);
					TimeClass startTime = getTime(strCommand);
					strCommand = removeNWords(1, strCommand);
					update.setStartDate(startDate);
					update.setStartTime(startTime);
					
					break;
			}
		}
		
		return update;
	}
	
	private String removeDelimiters(String strText){
		strText = strText.replaceAll("-d", "").trim();
		strText = strText.replaceAll("-e", "").trim();
		strText = strText.replaceAll("-s", "").trim();
		
		return strText;
	}
	
	private  DateClass getDate(String strCommand){
		String strDate = getWord(0, strCommand);
		
		strDate = DateHandler.tryParse(strDate);
		
		try {
			if(strDate != null){
				return new DateClass(strDate);
			}
		} catch (NoSuchFieldException | ParseException e) {
			e.printStackTrace();
		} 
		
		return null;
		
	}
	
	private  TimeClass getTime(String strCommand){
		String[] strSplitWords = strCommand.split(" ");
		TimeClass time = null;
		
		for(String word : strSplitWords){
			if((time = TimeHandler.parse(word)) != null){
				return time;
			}
		}
		
		return time;
	}

	
	
}