//@@author A0133267H
package main;

import java.util.ArrayList;

import main.DateClass;

public class Search extends Command {

	private String strSearchString;
	private DateClass firstDate, secondDate;
	private boolean before,after,between, on;

	public String getStrSearchString() {
		return strSearchString;
	}

	public boolean isBefore() {
		return before;
	}

	public boolean isOn() {
		return on;
	}

	public boolean isAfter() {
		return after;
	}

	public boolean isBetween() {
		return between;
	}

	public void setBefore(boolean before) {
		this.before = before;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public void setAfter(boolean after) {
		this.after = after;
	}

	public void setBetween(boolean between) {
		this.between = between;
	}

	public Search(String searchString, DateClass firstDate, DateClass secondDate){
		super(CommandType.SEARCH);
		strSearchString = searchString;
		this.firstDate = firstDate;
		this.secondDate = secondDate;

	}

	public Search(String searchString){
		this(searchString, null, null);
	}

	public Search(String searchString, DateClass firstDate){
		this(searchString, firstDate, null);
	}

	public Search(DateClass firstDate){
		this(null, firstDate, null);
	}

	public Search(DateClass firstDate, DateClass secondDate){
		this(null, firstDate, secondDate);
	}

	public String getSearchStrings(){
		return strSearchString;
	}


	public DateClass getFirstDate(){
		return firstDate;
	}

	public DateClass getSecondDate(){
		return secondDate;
	}

}
