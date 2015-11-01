package main;

import java.text.ParseException;
import java.util.*;

import main.Command.CommandType;


/***
 * 
 * @author Razali
 *
 */

public class Parser {
	
	

	/*** Util METHODS ***/
	
	
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
	
	
	private  int getNumberOfWords(String strText){
		return strText.split(" ").length;
	}
	
	private String removeDelimiters(String strText){
		strText = strText.replaceAll("-d", "").trim();
		strText = strText.replaceAll("-e", "").trim();
		strText = strText.replaceAll("-s", "").trim();
		
		return strText;
	}
	
	private  DateClass getDate(String strCommand){
		String parsedDate;
		
		String[] strSplitWords = strCommand.split(" ");
		
		for(String word : strSplitWords){
			parsedDate = DateHandler.tryParse(word);
			if(parsedDate != null){
				try {
					return new DateClass(parsedDate);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	private String removeDate(String strCommand){
		String parsedDate;
		Stack<String> dateBuffer = new Stack<String>();
			
		String[] strSplitWords = strCommand.split(" ");
		
		for(String word : strSplitWords){
			parsedDate = DateHandler.tryParse(word);
			if(parsedDate != null){
				dateBuffer.push(word);
			}
		}
			
		return strCommand.replace(dateBuffer.pop(), "").trim();
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

	private  String removeTime(String strCommand){
		String[] strSplitWords = strCommand.split(" ");
		TimeClass time = null;
		Stack<String> timeBuffer = new Stack<String>();
		
		for(String word : strSplitWords){
			if((time = TimeHandler.parse(word)) != null){
				timeBuffer.push(word);
			}
		}
		
		return strCommand.replace(timeBuffer.pop(), "").trim();
	}
	
	
	/* Command verifying methods */
	
	/**
	 * 
	 * @param strCommand A command to parse to an task
	 * @return TaskPair o
	 * @throws ParseException 
	 * @throws NoSuchFieldException 
	 */
	private boolean isAnAddCommand(String strCommand){
		//If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if(strFirstWord.equals("add")){
			return true;
		}
		return false;
	}
	

	private boolean isAnEventCommand(String strCommand){
		//If strCommand has "from" and "to", its an event!
		if(strCommand.contains("from") && strCommand.contains("to")){
			return true;
		}
		
		//else find for "at" and make sure it comes before "by" which is for evenet
		
		String[] splitWords = strCommand.split(" ");
		
		for(int i = splitWords.length - 1; i >= 0; i--){
			if(splitWords[i].equals("by") || splitWords[i].equals("in")){
				return false;
			} else if(splitWords[i].equals("at")){
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isADeadlineCommand(String strCommand){
		//If strCommand has "by", its a deadline!
		String[] splitWords = strCommand.split(" ");
		
		for(int i = splitWords.length-1; i >=0; i--){
			if(splitWords[i].equals("by") || splitWords[i].equals("in") || splitWords[i].equals("due")){
				return true;
			}
		}
		
		return false;
	}
	
	private String preprocessNLP(String strNLP){
		strNLP = strNLP.replace("tmr", "tomorrow");
		strNLP = strNLP.replace("nxt", "next");
		strNLP = strNLP.replace("wk", "week");
		
		return strNLP;
	}

	private boolean isAnUpdateCommand(String strCommand){
		//If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if(strFirstWord.equals("update")){
			return true;
		}
		return false;
	}
	
	private boolean isASearchCommand(String strCommand){
		//If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if(strFirstWord.equals("search")){
			return true;
		}
		return false;
	}
	
	private boolean isAnUndoCommand(String strCommand){
		//If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if(strFirstWord.equals("undo")){
			return true;
		}
		return false;
	}
	
	private boolean isARedoCommand(String strCommand){
		//If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if(strFirstWord.equals("redo")){
			return true;
		}
		return false;
	}
	
	private boolean isADisplayCommand(String strCommand){
		//If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if(strFirstWord.equals("display") || strFirstWord.equals("show")){
			return true;
		}
		return false;
	}
	
	private String replaceLast(String string, String substring, String replacement)
	{
	  int index = string.lastIndexOf(substring);
	  if (index == -1)
	    return string;
	  return string.substring(0, index) + replacement
	          + string.substring(index+substring.length());
	}
	
	private boolean isADeleteCommand(String strCommand){
		//If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if(strFirstWord.equals("delete") || strFirstWord.equals("remove")){
			return true;
		}
		return false;
	}
	
	private boolean isADoneCommand(String strCommand){
		//If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if(strFirstWord.equals("done")){
			return true;
		}
		return false;	
	}
	/* Parsing Methods */
	
	private Update parseUpdateCommand(String strCommand){
		
		strCommand = removeNWords(1, strCommand);
		String strSearchString = getSearchString(strCommand);
		
		UpdateTask updateTask = new UpdateTask(strSearchString);
		
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
					updateTask.setDescription(strDescription);
					
					strCommand = removeNWords(strDescription.split(" ").length, strCommand);
					break;
				case "-e":
					DateClass endDate = getDate(strCommand);
					strCommand = removeNWords(1, strCommand);
					TimeClass endTime = getTime(strCommand);
					strCommand = removeNWords(1, strCommand);
					updateTask.setEndDate(endDate);
					updateTask.setEndTime(endTime);
					
					break;
				case "-s":
					DateClass startDate = getDate(strCommand);
					strCommand = removeNWords(1, strCommand);
					TimeClass startTime = getTime(strCommand);
					strCommand = removeNWords(1, strCommand);
					updateTask.setStartDate(startDate);
					updateTask.setStartTime(startTime);
					
					break;
			}
		}
		
		return new Update(updateTask);
	}

	private Command parseAddCommand(String strCommand){
		
		String strDescription;
		String strStartDateAndStartTime = null;
		TimeClass startTime = null, endTime = null;
		DateClass startDate = null, endDate = null;
		Command command;
		
		//1. Remove "add" from command
		strCommand = removeNWords(1, strCommand);
		
		//Add command can be for Deadline, Floating or Event,
		
		if(isAnEventCommand(strCommand) == true){
			/*
			 * Required Event Syntax for successful parsing:
			 * Description from startdate, starttime to enddate endtime
			 */
			
			//<------------------Handling End Date and Time---------->
			String strEndDateAndEndTime = null;
			if(strCommand.contains("from") && strCommand.contains("to")){
				strEndDateAndEndTime = strCommand.substring(strCommand.lastIndexOf("to"));
				
				strEndDateAndEndTime = preprocessNLP(strEndDateAndEndTime);
				
				endDate = getDate(strEndDateAndEndTime);
				endTime = getTime(strEndDateAndEndTime);
				
				if(endDate == null && endTime == null){
					//use natural language to try get DateClass
					PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strEndDateAndEndTime);
					endDate = prettyTime.getDate();
					endTime = prettyTime.getTime();
				}
				else if(endDate != null && endTime == null){
					String strWithoutDate = removeDate(strEndDateAndEndTime);
					//use natural language to try get DateClass
					PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
					endTime = prettyTime.getTime();
					
				} else if(endDate == null && endTime != null){
					String strWithoutTime = removeTime(strEndDateAndEndTime);
					//use natural language to try get DateClass
					PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutTime);
					endDate = prettyTime.getDate();
				}
				strCommand = replaceLast(strCommand, strEndDateAndEndTime, "").trim();
				strStartDateAndStartTime = strCommand.substring(strCommand.lastIndexOf("from"));	
			} else if(strCommand.contains("at")){
				strStartDateAndStartTime = strCommand.substring(strCommand.lastIndexOf("at"));
				
			}
			
			
			
			//<-----------------Handling Start Date and Time--------->
			
			strStartDateAndStartTime = preprocessNLP(strStartDateAndStartTime);
			
			startDate = getDate(strStartDateAndStartTime);
			startTime = getTime(strStartDateAndStartTime);
			
			if(startDate == null && startTime == null){
				//use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strStartDateAndStartTime);
				startDate = prettyTime.getDate();
				startTime = prettyTime.getTime();
			}
			else if(startDate != null && startTime == null){
				String strWithoutDate = removeDate(strStartDateAndStartTime);
				//use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
				startTime = prettyTime.getTime();
				
			} else if(startDate == null && startTime != null){
				String strWithoutTime = removeTime(strCommand);
				//use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutTime);
				startDate = prettyTime.getDate();
			}
			strCommand = strCommand.replace(strStartDateAndStartTime, "").trim();
			
			if(endDate == null){
					endDate = startDate;
			}
			if(endTime == null){
				endTime = new TimeClass("2359");
			}
			if(startTime == null){
				startTime = new TimeClass("0000");
			}
			strDescription = strCommand;
			
			//NO description found!
			if(strDescription.replaceAll(" ", "") == "" )
				return null;
		
			//No startdatetime or enddatetime
			if(startDate == null && startTime == null || endDate==null && endTime == null)
				return null;
			
			if(startDate == null){
				try {
					startDate = new DateClass(DateHandler.getDateNow());
				} catch (NoSuchFieldException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(endDate == null){
				try {
					endDate = new DateClass(DateHandler.getDateNow());
				} catch (NoSuchFieldException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			command = new Command(CommandType.ADD_EVENT);
			
			command.setTask(new Event(strDescription, startDate, startTime, endDate, endTime));
			
			return command;
		} else if(isADeadlineCommand(strCommand) == true){
			/*
			 * Required Event Syntax for successful parsing:
			 * Description by enddate endtime
			 */
			//<------------------Handling End Date and Time---------->
			String strEndDateAndEndTime= null;
			String[] splitWords = strCommand.split(" ");
			
			for(int i = splitWords.length-1; i>=0; i--){
				if(splitWords[i].equals("by")){
					strEndDateAndEndTime = strCommand.substring(strCommand.lastIndexOf("by"));
					break;
				}else if(splitWords[i].equals("in")){
					strEndDateAndEndTime = strCommand.substring(strCommand.lastIndexOf("in"));
					break;
				}else if(splitWords[i].equals("due")){
					strEndDateAndEndTime = strCommand.substring(strCommand.lastIndexOf("due"));
					break;
				}
			}
			
			
			//PreProcess
			strEndDateAndEndTime = preprocessNLP(strEndDateAndEndTime);
			
			
			endDate = getDate(strEndDateAndEndTime);
			endTime = getTime(strEndDateAndEndTime);
			
			if(endDate == null && endTime == null){
				//use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strEndDateAndEndTime);
				endDate = prettyTime.getDate();
				endTime = prettyTime.getTime();
			}
			else if(endDate != null && endTime == null){
				String strWithoutDate = removeDate(strEndDateAndEndTime);
				//use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
				endTime = prettyTime.getTime();
				
			} else if(endDate == null && endTime != null){
				String strWithoutTime = removeTime(strEndDateAndEndTime);
				//use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutTime);
				endDate = prettyTime.getDate();
			}
			strCommand = strCommand.replace(strEndDateAndEndTime, "").trim();
			
			strDescription = strCommand;

			//NO description found!
			if(strDescription.replaceAll(" ", "") == "" )
				return null;
		
			//No enddatetime
			if(endDate==null && endTime == null)
				return null;
			
		
			if(endDate == null){
				try {
					endDate = new DateClass(DateHandler.getDateNow());
				} catch (NoSuchFieldException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(endTime == null){
				endTime = new TimeClass(TimeHandler.getHourNow() + "", TimeHandler.getMinuteNow() + "");
			}
			
			command = new Command(CommandType.ADD_DEADLINE);
			
			command.setTask(new Deadline(strDescription, endDate, endTime));
			return command;
		} else {//Floating task
			/*
			 * Required Event Syntax for successful parsing:
			 * Description
			 */
			strDescription = strCommand;

			command = new Command(CommandType.ADD_FLOATING);
			
			command.setTask(new Floating(strDescription));
			return command;
		}
		
		
	}
	
	private Command parseSearchCommand(String strCommand){
		//remove "search" word
		String strSearchString = removeNWords(1, strCommand);
		Search searchObj = new Search();
		
		//Get all dates
		while(strSearchString != null || strSearchString != ""){
			DateClass date = getDate(strSearchString);
			
			if(date!= null){
				searchObj.addSearchString(date.toString());
				strSearchString = removeDate(strSearchString);
			}else{
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strSearchString);
				date = prettyTime.getDate();
				
				if(date == null){
					break;
				} else{
					searchObj.addSearchString(date.toString());
					strSearchString =  strSearchString.replace(prettyTime.getParsedDateGroup().get(0), "").trim();
				}
			}
		}
		
		searchObj.addSearchString(strSearchString);
		return searchObj;
	}
	
	private Command parseUndoCommand(String strCommand){
		return new Undo();
	}

	private Command parseRedoCommand(String strCommand){
		return new Redo();
	}
	
	private Command parseDisplayCommand(String strCommand){
		String displayString = removeNWords(1, strCommand);
		return new Display(displayString);
	}
	
	private Command parseDeleteCommand(String strCommand){
		strCommand = removeNWords(1, strCommand);
		return new Delete(strCommand);
	}
	
	private Command parseDoneCommand(String strCommand){
		strCommand = removeNWords(1, strCommand);
		
		String[] strSplit = strCommand.split(",");
		Set<Integer> parsedIDs = new TreeSet<Integer>();
		
		for(String str : strSplit){
			str = str.replaceAll(" ", ""); //remove all white spaces
			
			if(str.matches("\\d+-\\d+")){
				int firstDigit = Integer.valueOf(str.split("-")[0]);
				int secondDigit = Integer.valueOf(str.split("-")[1]);
				
				//make first smaller than second
				if(firstDigit > secondDigit){
					firstDigit ^= secondDigit;
					secondDigit = firstDigit;
					firstDigit ^= secondDigit;
					
				}
				
				for(int i = firstDigit; i <= secondDigit; i++){
					parsedIDs.add(i);
				}
			} else if(str.matches("\\d+")){
				parsedIDs.add(Integer.valueOf(str));
			} 
		}
		
		if(parsedIDs.isEmpty()){
			return null;
		} else{
			Done doneCommand = new Done(parsedIDs);
			return doneCommand;
		}
	}
	
	public  Command parse(String strCommand){
		
		Command parsedCommand;
		
		if(isAnAddCommand(strCommand)){
			parsedCommand = parseAddCommand(strCommand);
		} else if(isAnUpdateCommand(strCommand)){
			parsedCommand = parseUpdateCommand(strCommand);
		} else if(isASearchCommand(strCommand)){
			parsedCommand = parseSearchCommand(strCommand);
		} else if(isAnUndoCommand(strCommand)){
			parsedCommand = parseUndoCommand(strCommand);
		} else if(isARedoCommand(strCommand)){
			parsedCommand = parseRedoCommand(strCommand);
		} else if(isADisplayCommand(strCommand)){
			parsedCommand = parseDisplayCommand(strCommand);
		} else if(isADeleteCommand(strCommand)){
			parsedCommand = parseDeleteCommand(strCommand);
		} else if (isADoneCommand(strCommand)){
			parsedCommand = parseDoneCommand(strCommand);
		}else{
			parsedCommand = null;
		}
		
		return parsedCommand;
	}


	public static void main(String[] args) throws NoSuchFieldException, ParseException{
		Parser p = new Parser();
		
		//String command = "update new swimming -d swimming";
		String command = "done 1,2,4-5";
		Command t = p.parse(command);
		
		
		System.out.println(((Event)t.getTask()).getEndTime().to12HourFormat());
	
	}


	
	
	

	
	
}
