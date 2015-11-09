//@@author A0133267H
package main;

import main.DateClass;
import main.TimeClass;

public class Event extends Task {


	private DateClass startDate;
	private DateClass endDate;
	private TimeClass startTime;
	private TimeClass endTime;


	/**
	 * Description Constructor : Creates a event task, which is a subclass of the Command abstract class.
	 * @param strDescription
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 */
	public Event(String strDescription, DateClass startDate, TimeClass startTime, DateClass  endDate, TimeClass endTime){

		super(strDescription);
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;

	}

	/**
	 * Description Gets the startDate of the event
	 * @return startDate
	 */
	public DateClass getStartDate() {
		return startDate;
	}

	public void setStartDate(DateClass startDate){
		this.startDate = startDate;
	}
	/**
	 * Description Gets the endDate of the event
	 * @return
	 */
	public DateClass getEndDate() {
		return endDate;
	}

	public void setEndDate(DateClass endDate) {
		this.endDate = endDate;
	}

	/**
	 * Description Gets the startTime of the event
	 * @return
	 */
	public TimeClass getStartTime() {
		return startTime;
	}

	public void setStartTime(TimeClass startTime) {
		this.startTime = startTime;
	}

	/**
	 * Description Gets the endTime of the event
	 * @return
	 */
	public TimeClass getEndTime() {
		return endTime;
	}

	public void setEndTime(TimeClass endTime) {
		this.endTime = endTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String output = "";
		output = this.getDescription() + " " + this.getStartDate().toString() + " " + this.getStartTime().toString()+ " "  + this.getEndDate().toString() + " " + this.getEndTime().toString();
		return output;
	}

	//@@author A0126518E
	@Override
	public int compareTo(Task anotherEvent) {
		if (this.getStartDate().equals(((Event) anotherEvent).getStartDate())) {
			return this.getStartTime().compareTo(((Event) anotherEvent).getStartTime());
		} else {
			return this.getStartDate().compareTo(((Event) anotherEvent).getStartDate());
		}
	}


}
