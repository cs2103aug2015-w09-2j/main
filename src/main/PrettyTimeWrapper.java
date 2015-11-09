//@@author A0133267H
package main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.DateClass;
import main.TimeClass;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;


public class PrettyTimeWrapper {

	public static final String[][] prettyTimeMonths = {
			{"Jan", "January"},
			{"Feb", "February"},
			{"March", "March"},
			{"Apr", "April"},
			{"May", "May"},
			{"Jun", "June"},
			{"Jul", "July"},
			{"Aug", "August"},
			{"Sep", "September"},
			{"Oct", "October"},
			{"Nov", "November"},
			{"Dec", "December"},
	};

	private String parseString;
	private List<Date> parsedDates;
	private List<DateGroup> parsedDateGroups;
	private DateClass date;
	private TimeClass time;

	public String getParseString() {
		return parseString;
	}


	public String getParsedDate() {
		return parsedDates.get(0).toString();
	}

	public DateClass getDate(){
		return date;
	}

	public TimeClass getTime(){
		return time;
	}

	public List<String> getParsedDateGroup() {
		ArrayList<String> dateGroups = new ArrayList<String>();

		for(DateGroup dg : parsedDateGroups){
			dateGroups.add(dg.getText());
		}
		return dateGroups;
	}


	public PrettyTimeWrapper(String stringToParse) {
		parseString = stringToParse;
		parse();
	}


	private void parse(){
		PrettyTimeParser p = new PrettyTimeParser();
		parsedDates = p.parse(parseString);
		parsedDateGroups = p.parseSyntax(parseString);

		if(!parsedDates.isEmpty()){
			date = parseDate(parsedDates.get(0).toString());
			time = parseTime(parsedDates.get(0).toString());
		}

	}

	public TimeClass parseTime(String command){
		String[] words = command.split(" ");

		for(String word : words){
			if(word.contains(":")){
				String[] timeDetails = word.split(":");
				return new TimeClass(timeDetails[0], timeDetails[1]);
			}
		}

		return null;
	}

	public DateClass parseDate(String command){
		command = command.replace("[", "");
		command = command.replace("]", "");

		String[] words = command.split(" ");

		String day = words[2];
		String month = getMonth(words[1]);
		String year = words[words.length-1];

		try {
			return new DateClass(day, month, year);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getMonth(String month){
		for(int i = 0; i < 12; i++){
			if(prettyTimeMonths[i][0].equals(month)){
				return prettyTimeMonths[i][1];
			}
		}

		return null;
	}


}
