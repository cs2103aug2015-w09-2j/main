//@@author A0133267H
package main.parser;

import java.text.ParseException;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import main.common.DateClass;
import main.common.DateHandler;
import main.common.TimeClass;
import main.common.TimeHandler;

public class ParserUtils {

	private static ParserUtils oneParserUtils =null;

	private ParserUtils(){

	}
	public static ParserUtils getInstance(){
		if(oneParserUtils == null){
			oneParserUtils = new ParserUtils();
		}
		return oneParserUtils;
	}

	public String getEnclosedDescription(String text){
		int firstOccurrence, lastOccurrence;

		firstOccurrence = text.indexOf('"');
		lastOccurrence = text.lastIndexOf('"');

		if(firstOccurrence == -1 || lastOccurrence == -1){
			return null;
		}

		String subString = text.substring(firstOccurrence+1, lastOccurrence);

		return subString;
	}

	public Set<Integer> getRangeSet(String text){
		String[] strSplit = text.split(",");
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

		return parsedIDs.size() == 0 ? null : parsedIDs;
	}

	public boolean containsWord(String word, String text){
		String[] splitWord = text.split(" ");

		for(String string : splitWord){
			if(string.equals(word)){
				return true;
			}
		}

		return false;
	}

	public String getWord(int intIndex, String strText) {
		String[] words = strText.split(" ");

		if (intIndex < 0 || intIndex >= words.length)
			return null;

		return words[intIndex];
	}

	public String removeNWords(int numOfWordsToRemove, String strText) {
		int intRemoved = 0;
		String strWord;

		while (intRemoved != numOfWordsToRemove) {
			strWord = getWord(0, strText);
			strText = strText.replaceFirst(strWord, "").trim();

			intRemoved++;
		}

		return strText;
	}

	public int getNumberOfWords(String strText) {
		return strText.split(" ").length;
	}

	public boolean isDelimeter(String word){
		return word.equals("-e") || word.equals("-s") || word.equals("-d");
	}

	public String removeDelimiters(String strText) {
		strText = strText.replaceAll("-d", "").trim();
		strText = strText.replaceAll("-e", "").trim();
		strText = strText.replaceAll("-s", "").trim();

		return strText;
	}


	public String getDescription(String strCommand) {
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


	public DateClass getDate(String strCommand) {
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

	public String getUpdateStartDateTimeString(String strCommand){
		String[] splitWords = strCommand.split(" ");
		StringBuilder sb = new StringBuilder();

		for(String word : splitWords){
			if(word.equals("-d") || word.equals("-e"))
				break;
			sb.append(word + " ");
		}

		return sb.toString().trim();
	}

	public String getUpdateEndDateTimeString(String strCommand){
		String[] splitWords = strCommand.split(" ");
		StringBuilder sb = new StringBuilder();

		for(String word : splitWords){
			if(word.equals("-d") || word.equals("-s"))
				break;
			sb.append(word + " ");
		}

		return sb.toString().trim();
	}
	public String removeDate(String strCommand) {
		String parsedDate;
		Stack<String> dateBuffer = new Stack<String>();

		String[] strSplitWords = strCommand.split(" ");

		for (String word : strSplitWords) {
			parsedDate = DateHandler.tryParse(word);
			if (parsedDate != null) {
				dateBuffer.push(word);
			}
		}


		return dateBuffer.size() != 0 ? strCommand.replace(dateBuffer.pop(), "").trim() : strCommand;
	}

	public TimeClass getTime(String strCommand) {
		String[] strSplitWords = strCommand.split(" ");
		TimeClass time = null;

		for (String word : strSplitWords) {
			if ((time = TimeHandler.parse(word)) != null) {
				return time;
			}
		}

		return time;
	}

	public String removeTime(String strCommand) {
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

}
