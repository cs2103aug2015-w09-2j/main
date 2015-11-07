package main;

import java.text.ParseException;
import java.util.*;

import org.ocpsoft.prettytime.shade.com.joestelmach.natty.generated.DateParser.friendly_meridian_indicator_return;

import main.Command.CommandType;

/***
 * 
 * @author Razali
 *
 */

public class Parser {

	private ParserUtils parserUtils;
	
	public Parser(){
		parserUtils = new ParserUtils();
	}
	
	/* Command verifying methods */

	/**
	 * 
	 * @param strCommand
	 *            A command to parse to an task
	 * @return TaskPair o
	 * @throws ParseException
	 * @throws NoSuchFieldException
	 */
	private boolean isAnAddCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("add")) {
			return true;
		}
		return false;
	}
	
	private boolean isAClearCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("clear")) {
			return true;
		}
		return false;

	}

	private boolean isASaveCommand(String strCommand) {
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("save")) {
			return true;
		}
		return false;
	}


	private boolean isAnEventCommand(String strCommand) {
		

		// else find for "at" and make sure it comes before "by" which is for
		// evenet

		String[] splitWords = strCommand.split(" ");

		for (int i = splitWords.length - 1; i >= 0; i--) {
			if (splitWords[i].equals("by") || splitWords[i].equals("in") || splitWords[i].equals("due")) {
				return false;
			} else if (splitWords[i].equals("at")) {
				return true;
			}
		}
		
		// If strCommand has "from" and "to", its an event!
		if (parserUtils.containsWord("from", strCommand) &&  parserUtils.containsWord("to", strCommand)) {
			return true;
		}
		return false;
	}

	private boolean isADeadlineCommand(String strCommand) {
		// If strCommand has "by", its a deadline!
		String[] splitWords = strCommand.split(" ");

		for (int i = splitWords.length - 1; i >= 0; i--) {
			if (splitWords[i].equals("by") || splitWords[i].equals("in") || splitWords[i].equals("due")) {
				return true;
			}
		}

		return false;
	}

	private String preprocessNLP(String strNLP) {
		strNLP = strNLP.replace("tmr", "tomorrow");
		strNLP = strNLP.replace("nxt", "next");
		strNLP = strNLP.replace("wk", "week");

		return strNLP;
	}

	private boolean isAnUpdateCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("update")) {
			return true;
		}
		return false;
	}

	private boolean isAnExitCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("exit")) {
			return true;
		}
		return false;

	}
	
	private boolean isASearchCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("search")) {
			return true;
		}
		return false;
	}

	private boolean isAnUndoCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("undo")) {
			return true;
		}
		return false;
	}

	private boolean isARedoCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("redo")) {
			return true;
		}
		return false;
	}
	
	private boolean isADisplayCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("display") || strFirstWord.equals("show")) {
			return true;
		}
		return false;
	}
	
	private String replaceLast(String string, String substring, String replacement) {
		int index = string.lastIndexOf(substring);
		if (index == -1)
			return string;
		return string.substring(0, index) + replacement + string.substring(index + substring.length());
	}

	private boolean isADeleteCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("delete") || strFirstWord.equals("remove")) {
			return true;
		}
		return false;
	}

	private boolean isADoneCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = parserUtils.getWord(0, strCommand);
		if (strFirstWord.equals("done")) {
			return true;
		}
		return false;
	}
	
	
	/* Parsing Methods */

	private Update parseUpdateCommand(String strCommand) {

		strCommand = parserUtils.removeNWords(1, strCommand);
		
		UpdateTask updateTask = new UpdateTask();

		/*
		 * get task id
		 */
		int taskID;
		try{
			taskID = Integer.valueOf(parserUtils.getWord(0, strCommand));
		}catch(NumberFormatException n){
			return null;
		}
		
		updateTask.setTaskID(taskID);
		
		//remove taskID
		strCommand = parserUtils.removeNWords(1, strCommand);
		
		while (!strCommand.equals("")) {
			// Get delimiter
			String delimiter = parserUtils.getWord(0, strCommand);
		
			if(parserUtils.isDelimeter(delimiter) == false){
				break;
			}
			
			strCommand = parserUtils.removeNWords(1, strCommand);

			switch (delimiter) {
			case "-d":
				String strDescription = parserUtils.removeDelimiters(parserUtils.getDescription(strCommand));
				updateTask.setDescription(strDescription.equals("") ? null : strDescription);

				strCommand = parserUtils.removeNWords(strDescription.split(" ").length, strCommand);
				break;
			case "-e":
				String strEndDateTime = parserUtils.getUpdateEndDateTimeString(strCommand).trim();
				String strEndDateTimeCopy = new String(strEndDateTime);
				DateClass endDate = parseDate(strEndDateTimeCopy);
				strEndDateTimeCopy = parserUtils.removeDate(strEndDateTimeCopy);
				TimeClass endTime = parseTime(strEndDateTimeCopy);
				
				if(endDate == null || endTime == null){
					return null;
				}
				
				strCommand = strCommand.replace(strEndDateTime, "").trim();
				
				updateTask.setEndDate(endDate);
				updateTask.setEndTime(endTime);

				break;
			case "-s":
				String strStartDateTime = parserUtils.getUpdateStartDateTimeString(strCommand).trim();
				String strStartDateTimeCopy = new String(strStartDateTime);
				DateClass startDate = parseDate(strStartDateTimeCopy);
				strStartDateTimeCopy = parserUtils.removeDate(strStartDateTimeCopy);
				TimeClass startTime = parseTime(strStartDateTimeCopy);
				
				if(startDate == null || startTime == null){
					return null;
				}
				
				strCommand = strCommand.replace(strStartDateTime, "").trim();
				
				updateTask.setStartDate(startDate);
				updateTask.setStartTime(startTime);

				break;
				
			
			}
		}
		
		//Incorrect update format?? By this point should have parsed everything
		if(strCommand.equals("") == false){
			
			//Check if it is a task id
			return null;
		}
		
		//parsed nothing!
		if(strCommand.equals("") && updateTask.getDescription() == null && updateTask.getEndDate() == null && updateTask.getStartDate() == null){
			return null;
		}
		
		return new Update(updateTask);
	}

	private Command parseAddCommand(String strCommand) {

		String strDescription;
		String strStartDateAndStartTime = null;
		TimeClass startTime = null, endTime = null;
		DateClass startDate = null, endDate = null;
		Command command;

		// 1. Remove "add" from command
		strCommand = parserUtils.removeNWords(1, strCommand);

		// Add command can be for Deadline, Floating or Event,

		if (isAnEventCommand(strCommand) == true) {
			/*
			 * Required Event Syntax for successful parsing: Description from
			 * startdate, starttime to enddate endtime
			 */

			// <------------------Handling End Date and Time---------->
			String strEndDateAndEndTime = null;
			if (strCommand.contains("from") && strCommand.contains("to")) {
				strEndDateAndEndTime = strCommand.substring(strCommand.lastIndexOf("to"));

				strEndDateAndEndTime = preprocessNLP(strEndDateAndEndTime);

				endDate = parserUtils.getDate(strEndDateAndEndTime);
				endTime = parserUtils.getTime(strEndDateAndEndTime);

				if (endDate == null && endTime == null) {
					// use natural language to try get DateClass
					PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strEndDateAndEndTime);
					endDate = prettyTime.getDate();
					endTime = prettyTime.getTime();
				} else if (endDate != null && endTime == null) {
					String strWithoutDate = parserUtils.removeDate(strEndDateAndEndTime);
					// use natural language to try get DateClass
					PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
					endTime = prettyTime.getTime();

				} else if (endDate == null && endTime != null) {
					String strWithoutTime = parserUtils.removeTime(strEndDateAndEndTime);
					// use natural language to try get DateClass
					PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutTime);
					endDate = prettyTime.getDate();
				}
				strCommand = replaceLast(strCommand, strEndDateAndEndTime, "").trim();
				strStartDateAndStartTime = strCommand.substring(strCommand.lastIndexOf("from"));
			} else if (strCommand.contains("at")) {
				strStartDateAndStartTime = strCommand.substring(strCommand.lastIndexOf("at"));

			}

			// <-----------------Handling Start Date and Time--------->

			strStartDateAndStartTime = preprocessNLP(strStartDateAndStartTime);

			startDate = parserUtils.getDate(strStartDateAndStartTime);
			startTime = parserUtils.getTime(strStartDateAndStartTime);

			if (startDate == null && startTime == null) {
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strStartDateAndStartTime);
				startDate = prettyTime.getDate();
				startTime = prettyTime.getTime();
			} else if (startDate != null && startTime == null) {
				String strWithoutDate = parserUtils.removeDate(strStartDateAndStartTime);
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
				startTime = prettyTime.getTime();

			} else if (startDate == null && startTime != null) {
				String strWithoutTime = parserUtils.removeTime(strCommand);
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutTime);
				startDate = prettyTime.getDate();
			}
			strCommand = strCommand.replace(strStartDateAndStartTime, "").trim();

			if (endDate == null) {
				endDate = startDate;
			}
			if (endTime == null) {
				endTime = new TimeClass("2359");
			}
			if (startTime == null) {
				startTime = new TimeClass("0000");
			}
			strDescription = strCommand;

			// NO description found!
			if (strDescription.replaceAll(" ", "") == "")
				return null;

			// No startdatetime or enddatetime
			if (startDate == null && startTime == null || endDate == null && endTime == null)
				return null;

			if (startDate == null) {
				try {
					startDate = new DateClass(DateHandler.getDateNow());
				} catch (NoSuchFieldException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (endDate == null) {
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
		} else if (isADeadlineCommand(strCommand) == true) {
			/*
			 * Required Event Syntax for successful parsing: Description by
			 * enddate endtime
			 */
			// <------------------Handling End Date and Time---------->
			String strEndDateAndEndTime = null;
			String[] splitWords = strCommand.split(" ");

			for (int i = splitWords.length - 1; i >= 0; i--) {
				if (splitWords[i].equals("by")) {
					strEndDateAndEndTime = strCommand.substring(strCommand.lastIndexOf("by"));
					break;
				} else if (splitWords[i].equals("in")) {
					strEndDateAndEndTime = strCommand.substring(strCommand.lastIndexOf("in"));
					break;
				} else if (splitWords[i].equals("due")) {
					strEndDateAndEndTime = strCommand.substring(strCommand.lastIndexOf("due"));
					break;
				}
			}

			// PreProcess
			strEndDateAndEndTime = preprocessNLP(strEndDateAndEndTime);

			endDate = parserUtils.getDate(strEndDateAndEndTime);
			endTime = parserUtils.getTime(strEndDateAndEndTime);

			if (endDate == null && endTime == null) {
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strEndDateAndEndTime);
				endDate = prettyTime.getDate();
				endTime = prettyTime.getTime();
			} else if (endDate != null && endTime == null) {
				String strWithoutDate = parserUtils.removeDate(strEndDateAndEndTime);
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
				endTime = prettyTime.getTime();

			} else if (endDate == null && endTime != null) {
				String strWithoutTime = parserUtils.removeTime(strEndDateAndEndTime);
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutTime);
				endDate = prettyTime.getDate();
			}
			strCommand = strCommand.replace(strEndDateAndEndTime, "").trim();

			strDescription = strCommand;

			// NO description found!
			if (strDescription.replaceAll(" ", "") == "")
				return null;

			// No enddatetime
			if (endDate == null && endTime == null)
				return null;

			if (endDate == null) {
				try {
					endDate = new DateClass(DateHandler.getDateNow());
				} catch (NoSuchFieldException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (endTime == null) {
				endTime = new TimeClass(TimeHandler.getHourNow() + "", TimeHandler.getMinuteNow() + "");
			}

			command = new Command(CommandType.ADD_DEADLINE);

			command.setTask(new Deadline(strDescription, endDate, endTime));
			return command;
		} else {// Floating task
			/*
			 * Required Event Syntax for successful parsing: Description
			 */
			strDescription = strCommand;
			
			if(strDescription.equals("")){
				return null;
			}
			
			command = new Command(CommandType.ADD_FLOATING);

			command.setTask(new Floating(strDescription));
			return command;
		}

	}

	private Command parseSearchCommand(String strCommand){
		// remove "search" word
		String strSearchString = parserUtils.removeNWords(1, strCommand);
		
		String enclosedString = parserUtils.getEnclosedDescription(strSearchString);
		
		
		
		if(enclosedString != null){
			enclosedString = enclosedString.equals("") == true ? null : enclosedString;
			strSearchString = strSearchString.substring(strSearchString.lastIndexOf("\"") + 1).trim();
			
			if(strSearchString == ""){
				return new Search(enclosedString);
			}
			
			//else find for dates
			if(strSearchString.matches("after .+") || strSearchString.matches("before .+") || strSearchString.matches("on .+")){
				String firstWord =  parserUtils.getWord(0, strSearchString);
				strSearchString = parserUtils.removeNWords(1, strSearchString);
				DateClass firstDate = parseDate(strSearchString);
				
				if(firstDate == null){
					return null;
				}
				
				Search search = new Search(enclosedString, firstDate);
				if(firstWord.equals("after")){
					search.setAfter(true);
				} else if (firstWord.equals("before")){
					search.setBefore(true);
				} else {
					search.setOn(true);
				}
				return search;
			} else if (strSearchString.matches("from .+ to .+")){
				
				int indexOfTo = strSearchString.lastIndexOf("to");
				String strTo = strSearchString.substring(indexOfTo).replace("to", "").trim();
				
				int indexOfFrom = strSearchString.lastIndexOf("from");
				String strFrom = strSearchString.substring(indexOfFrom, indexOfTo).replace("from", "").trim();
				
				DateClass firstDate, secondDate;
				firstDate = parseDate(strFrom);
				secondDate = parseDate(strTo);
				
				if(firstDate == null || secondDate == null){
					return null;
				}
				
				Search search = new Search(enclosedString, firstDate, secondDate);
				search.setBetween(true);
				return search;
			} else{
				return null;
			}
			
		}
		
		//find to/after/before/on
		String[] splitWords = strSearchString.split(" ");
		String firstFoundWord = null;
		
		for(int i = splitWords.length -1; i >=0; i--){
			if(splitWords[i].equals("to") || splitWords[i].equals("after") || splitWords[i].equals("before") || splitWords[i].equals("on")){
				firstFoundWord = splitWords[i];
				break;
			}
		}
		
		if(firstFoundWord == null){
			return new Search(strSearchString);
		} else if(firstFoundWord.equals("to")){
			int indexOfTo = strSearchString.lastIndexOf("to");
			String strTo = strSearchString.substring(indexOfTo).replace("to", "").trim();
			
			int indexOfFrom = strSearchString.lastIndexOf("from");
			String strFrom = strSearchString.substring(indexOfFrom, indexOfTo).replace("from", "").trim();
			
			DateClass firstDate, secondDate;
			firstDate = parseDate(strFrom);
			secondDate = parseDate(strTo);
			
			if(firstDate == null || secondDate == null){
				return null;
			}
			
			strSearchString = strSearchString.substring(0,indexOfFrom).trim();
			strSearchString = strSearchString.equals("") || strSearchString.equals("\"\"") ? null : strSearchString;
			
			Search search = new Search(strSearchString, firstDate, secondDate);
			search.setBetween(true);
			return search;
		} else {//before//after//on
			String[] strSplit =  strSearchString.split(firstFoundWord);
			if(strSplit.length != 2){
				return null;
			}
			
			DateClass firstDate = parseDate(strSplit[1].trim());
			
			if(firstDate == null){
				return null;
			}
			
			strSearchString = strSplit[0].trim();
			strSearchString = strSearchString.equals("") || strSearchString.equals("\"\"") ? null : strSearchString;
			
			Search search = new Search(strSearchString, firstDate);
			if(firstFoundWord.equals("after")){
				search.setAfter(true);
			} else if (firstFoundWord.equals("before")){
				search.setBefore(true);
			} else {
				search.setOn(true);
			}
			return search;
		}
		
		
	}
	
	private DateClass parseDate(String text){
		DateClass firstDate = null;
		String[] splitWords = text.split(" ");
		
		for(String word:splitWords){
		
			try {
				String strFirstDate = DateHandler.tryParse(word);
				firstDate = strFirstDate == null ? null : new DateClass(strFirstDate);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(firstDate != null)
				return firstDate;
		}
		
		if(firstDate == null){
			PrettyTimeWrapper ptw = new PrettyTimeWrapper(text);
			firstDate = ptw.getDate();
		}
		
		return firstDate == null ? null : firstDate;
	}
	
	private TimeClass parseTime(String text){
		TimeClass time = null;
		String[] splitWords = text.split(" ");
		
		for(String word:splitWords){
		
			time = TimeHandler.parse(word);
				
			if(time != null)
				return time;
		}
		
		if(time == null){
			PrettyTimeWrapper ptw = new PrettyTimeWrapper(text);
			time = ptw.getTime();
		}
		
		return time == null ? null : time;
	}
	
 	private Command parseUndoCommand(String strCommand) {
		return new Undo();
	}

	private Command parseRedoCommand(String strCommand) {
		return new Redo();
	}

	private Command parseDisplayCommand(String strCommand) {
		String displayString = parserUtils.removeNWords(1, strCommand);
		return new Display(displayString);
	}

	private Command parseDeleteCommand(String strCommand) {
		strCommand = parserUtils.removeNWords(1, strCommand);
		
		String enclosedDescription = parserUtils.getEnclosedDescription(strCommand);
		
		//Delete has "xxx" description
		if(enclosedDescription != null){
			return new Delete(enclosedDescription);
		}
		
		Set<Integer> taskIDs =  parserUtils.getRangeSet(strCommand);
		
		//Has task ids
		if(taskIDs != null){
			return new Delete(taskIDs);
		}
		
		//Else if no task ids and no "xx", treat them as desc
		return new Delete(strCommand);
	}

	private Command parseDoneCommand(String strCommand) {
		strCommand = parserUtils.removeNWords(1, strCommand);

		String[] strSplit = strCommand.split(",");
		Set<Integer> parsedIDs = new TreeSet<Integer>();

		for (String str : strSplit) {
			str = str.replaceAll(" ", ""); // remove all white spaces

			if (str.matches("\\d+-\\d+")) {
				int firstDigit = Integer.valueOf(str.split("-")[0]);
				int secondDigit = Integer.valueOf(str.split("-")[1]);

				// make first smaller than second
				if (firstDigit > secondDigit) {
					firstDigit ^= secondDigit;
					secondDigit = firstDigit;
					firstDigit ^= secondDigit;

				}

				for (int i = firstDigit; i <= secondDigit; i++) {
					parsedIDs.add(i);
				}
			} else if (str.matches("\\d+")) {
				parsedIDs.add(Integer.valueOf(str));
			}
		}

		if (parsedIDs.isEmpty()) {
			return null;
		} else {
			Done doneCommand = new Done(parsedIDs);
			return doneCommand;
		}
	}

	private Command parseSaveCommand(String strCommand) {
		strCommand = parserUtils.removeNWords(1, strCommand);
		return new Save(strCommand);
	}
	
	private Command parseExitCommand(String strCommand) {
		return new Exit();
	}
	
	private Command parseClearCommand(String strCommand) {
		return new Clear();
	}

	public Command parse(String strCommand) {

		Command parsedCommand;

		if (isAnAddCommand(strCommand)) {
			parsedCommand = parseAddCommand(strCommand);
		} else if (isAnUpdateCommand(strCommand)) {
			parsedCommand = parseUpdateCommand(strCommand);
		} else if (isASearchCommand(strCommand)) {
			parsedCommand = parseSearchCommand(strCommand);
		} else if (isAnUndoCommand(strCommand)) {
			parsedCommand = parseUndoCommand(strCommand);
		} else if (isARedoCommand(strCommand)) {
			parsedCommand = parseRedoCommand(strCommand);
		} else if (isADisplayCommand(strCommand)) {
			parsedCommand = parseDisplayCommand(strCommand);
		} else if (isADeleteCommand(strCommand)) {
			parsedCommand = parseDeleteCommand(strCommand);
		} else if (isADoneCommand(strCommand)) {
			parsedCommand = parseDoneCommand(strCommand);
		} else if (isAnExitCommand(strCommand)) {
			parsedCommand = parseExitCommand(strCommand);
		} else if (isAClearCommand(strCommand)) {
			parsedCommand = parseClearCommand(strCommand);
		} else if (isASaveCommand(strCommand)) {
			parsedCommand = parseSaveCommand(strCommand);
		} else {
			parsedCommand = null;
		}

		return parsedCommand;
	}
	
	public static void main(String[] args) throws NoSuchFieldException, ParseException {
		Parser p = new Parser();

		// String command = "update new swimming -d swimming";
		Command t;
		String command;
		command = "";
		t = p.parse(command);
		
		System.out.println(((Event) t.getTask()).getEndTime().to12HourFormat());

	}

}
