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

	private ParserUtils putils;
	
	public Parser(){
		putils = new ParserUtils();
	}
	
	
	/*** Util METHODS ***/

	private String getWord(int intIndex, String strText) {
		String[] words = strText.split(" ");

		if (intIndex < 0 || intIndex >= words.length)
			return null;

		return words[intIndex];
	}

	private String removeNWords(int numOfWordsToRemove, String strText) {
		int intRemoved = 0;
		String strWord;

		while (intRemoved != numOfWordsToRemove) {
			strWord = getWord(0, strText);
			strText = strText.replaceFirst(strWord, "").trim();

			intRemoved++;
		}

		return strText;
	}

	private String getSearchString(String strCommand) {
		StringBuilder sb = new StringBuilder();

		String word = getWord(0, strCommand);

		while (!word.equals("-d") && !word.equals("-e") && !word.equals("-s")) {
			sb.append(word + " ");
			strCommand = removeNWords(1, strCommand);
			word = getWord(0, strCommand);
		}

		return sb.toString().trim();
	}

	private String getDescription(String strCommand) {
		StringBuilder sb = new StringBuilder();
		int intWordIndex = 0;

		String strNextWord = getWord(intWordIndex, strCommand);

		while (DateHandler.tryParse(strNextWord) == null) {
			sb.append(strNextWord + " ");
			intWordIndex++;
			strNextWord = getWord(intWordIndex, strCommand);
			if (strNextWord == null)
				break;
		}

		String strDescription = sb.toString().trim();

		return strDescription;
	}

	private int getNumberOfWords(String strText) {
		return strText.split(" ").length;
	}

	private String removeDelimiters(String strText) {
		strText = strText.replaceAll("-d", "").trim();
		strText = strText.replaceAll("-e", "").trim();
		strText = strText.replaceAll("-s", "").trim();

		return strText;
	}

	private DateClass getDate(String strCommand) {
		String parsedDate;

		String[] strSplitWords = strCommand.split(" ");

		for (String word : strSplitWords) {
			parsedDate = DateHandler.tryParse(word);
			if (parsedDate != null) {
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

	private String removeDate(String strCommand) {
		String parsedDate;
		Stack<String> dateBuffer = new Stack<String>();

		String[] strSplitWords = strCommand.split(" ");

		for (String word : strSplitWords) {
			parsedDate = DateHandler.tryParse(word);
			if (parsedDate != null) {
				dateBuffer.push(word);
			}
		}

		return strCommand.replace(dateBuffer.pop(), "").trim();
	}

	private TimeClass getTime(String strCommand) {
		String[] strSplitWords = strCommand.split(" ");
		TimeClass time = null;

		for (String word : strSplitWords) {
			if ((time = TimeHandler.parse(word)) != null) {
				return time;
			}
		}

		return time;
	}

	private String removeTime(String strCommand) {
		String[] strSplitWords = strCommand.split(" ");
		TimeClass time = null;
		Stack<String> timeBuffer = new Stack<String>();

		for (String word : strSplitWords) {
			if ((time = TimeHandler.parse(word)) != null) {
				timeBuffer.push(word);
			}
		}

		return strCommand.replace(timeBuffer.pop(), "").trim();
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
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("add")) {
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
		if (putils.containsWord("from", strCommand) &&  putils.containsWord("to", strCommand)) {
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
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("update")) {
			return true;
		}
		return false;
	}

	private boolean isAnExitCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("exit")) {
			return true;
		}
		return false;

	}
	
	private boolean isASearchCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("search")) {
			return true;
		}
		return false;
	}

	private boolean isAnUndoCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("undo")) {
			return true;
		}
		return false;
	}

	private boolean isARedoCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("redo")) {
			return true;
		}
		return false;
	}
	
	private boolean isADisplayCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = getWord(0, strCommand);
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
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("delete") || strFirstWord.equals("remove")) {
			return true;
		}
		return false;
	}

	private boolean isADoneCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("done")) {
			return true;
		}
		return false;
	}
	
	
	/* Parsing Methods */

	private Update parseUpdateCommand(String strCommand) {

		strCommand = removeNWords(1, strCommand);
		
		
		UpdateTask updateTask = new UpdateTask();

		/*
		 * get task id
		 */
		int taskID;
		try{
			taskID = Integer.valueOf(getWord(0, strCommand));
		}catch(NumberFormatException n){
			return null;
		}
		
		updateTask.setTaskID(taskID);
		
		//remove taskID
		strCommand = removeNWords(1, strCommand);
		
		while (!strCommand.equals("")) {
			// Get delimiter
			String delimiter = getWord(0, strCommand);
		
			if(putils.isDelimeter(delimiter) == false){
				break;
			}
			
			strCommand = removeNWords(1, strCommand);

			switch (delimiter) {
			case "-d":
				String strDescription = removeDelimiters(getDescription(strCommand));
				updateTask.setDescription(strDescription.equals("") ? null : strDescription);

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
		strCommand = removeNWords(1, strCommand);

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

				endDate = getDate(strEndDateAndEndTime);
				endTime = getTime(strEndDateAndEndTime);

				if (endDate == null && endTime == null) {
					// use natural language to try get DateClass
					PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strEndDateAndEndTime);
					endDate = prettyTime.getDate();
					endTime = prettyTime.getTime();
				} else if (endDate != null && endTime == null) {
					String strWithoutDate = removeDate(strEndDateAndEndTime);
					// use natural language to try get DateClass
					PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
					endTime = prettyTime.getTime();

				} else if (endDate == null && endTime != null) {
					String strWithoutTime = removeTime(strEndDateAndEndTime);
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

			startDate = getDate(strStartDateAndStartTime);
			startTime = getTime(strStartDateAndStartTime);

			if (startDate == null && startTime == null) {
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strStartDateAndStartTime);
				startDate = prettyTime.getDate();
				startTime = prettyTime.getTime();
			} else if (startDate != null && startTime == null) {
				String strWithoutDate = removeDate(strStartDateAndStartTime);
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
				startTime = prettyTime.getTime();

			} else if (startDate == null && startTime != null) {
				String strWithoutTime = removeTime(strCommand);
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

			endDate = getDate(strEndDateAndEndTime);
			endTime = getTime(strEndDateAndEndTime);

			if (endDate == null && endTime == null) {
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strEndDateAndEndTime);
				endDate = prettyTime.getDate();
				endTime = prettyTime.getTime();
			} else if (endDate != null && endTime == null) {
				String strWithoutDate = removeDate(strEndDateAndEndTime);
				// use natural language to try get DateClass
				PrettyTimeWrapper prettyTime = new PrettyTimeWrapper(strWithoutDate);
				endTime = prettyTime.getTime();

			} else if (endDate == null && endTime != null) {
				String strWithoutTime = removeTime(strEndDateAndEndTime);
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

			command = new Command(CommandType.ADD_FLOATING);

			command.setTask(new Floating(strDescription));
			return command;
		}

	}

	private Command parseSearchCommand(String strCommand){
		// remove "search" word
		String strSearchString = removeNWords(1, strCommand);
		
		String enclosedString = putils.getEnclosedDescription(strSearchString);
		
		enclosedString = enclosedString.equals("") == true ? null : enclosedString;
		
		if(enclosedString != null){
			strSearchString = strSearchString.substring(strSearchString.lastIndexOf("\"") + 1).trim();
			
			if(strSearchString == ""){
				return new Search(enclosedString);
			}
			
			//else find for dates
			if(strSearchString.matches("after .+") || strSearchString.matches("before .+") || strSearchString.matches("on .+")){
				String firstWord =  getWord(0, strSearchString);
				strSearchString = removeNWords(1, strSearchString);
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
		try {
			String strFirstDate = DateHandler.tryParse(text);
			firstDate = strFirstDate == null ? null : new DateClass(strFirstDate);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(firstDate == null){
			PrettyTimeWrapper ptw = new PrettyTimeWrapper(text);
			firstDate = ptw.getDate();
		}
		
		return firstDate == null ? null : firstDate;
	}
	
	private Command parseUndoCommand(String strCommand) {
		return new Undo();
	}

	private Command parseRedoCommand(String strCommand) {
		return new Redo();
	}

	private Command parseDisplayCommand(String strCommand) {
		String displayString = removeNWords(1, strCommand);
		return new Display(displayString);
	}

	private Command parseDeleteCommand(String strCommand) {
		strCommand = removeNWords(1, strCommand);
		
		String enclosedDescription = putils.getEnclosedDescription(strCommand);
		
		//Delete has "xxx" description
		if(enclosedDescription != null){
			return new Delete(enclosedDescription);
		}
		
		Set<Integer> taskIDs =  putils.getRangeSet(strCommand);
		
		//Has task ids
		if(taskIDs != null){
			return new Delete(taskIDs);
		}
		
		//Else if no task ids and no "xx", treat them as desc
		return new Delete(strCommand);
	}

	private Command parseDoneCommand(String strCommand) {
		strCommand = removeNWords(1, strCommand);

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

	private Command parseSaveCommand(String strCommand) {
		strCommand = removeNWords(1, strCommand);
		return new Save(strCommand);
	}
	
	private Command parseExitCommand(String strCommand) {
		return new Exit();
	}
	
	
	private Command parseClearCommand(String strCommand) {
		return new Clear();
	}

	private boolean isAClearCommand(String strCommand) {
		// If the first word is update
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("clear")) {
			return true;
		}
		return false;

	}

	private boolean isASaveCommand(String strCommand) {
		String strFirstWord = getWord(0, strCommand);
		if (strFirstWord.equals("save")) {
			return true;
		}
		return false;
	}

	
	public static void main(String[] args) throws NoSuchFieldException, ParseException {
		Parser p = new Parser();

		// String command = "update new swimming -d swimming";
		Command t;
		String command;
		command = "update -d hello";
		t = p.parse(command);
		command = "update -d";
		t = p.parse(command);
		command = "search shit after 20/11";
		t = p.parse(command);
		command = "search shit on 20/11";
		t = p.parse(command);
		
		System.out.println(((Event) t.getTask()).getEndTime().to12HourFormat());

	}

}
