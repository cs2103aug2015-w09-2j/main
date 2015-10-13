package main;

public class Event extends Task{


	private DateClass startDate;
	private DateClass endDate;
	private TimeClass startTime;
	private TimeClass endTime;

	/**
	 * Description Constructor : Creates a event task, which is a subclass of the Task abstract class.
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

	/**
	 * Description Gets the endDate of the event
	 * @return
	 */
	public DateClass getEndDate() {
		return endDate;
	}

	/**
	 * Description Gets the startTime of the event
	 * @return
	 */
	public TimeClass getStartTime() {
		return startTime;
	}

	/**
	 * Description Gets the endTime of the event
	 * @return
	 */
	public TimeClass getEndTime() {
		return endTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String output = "";
		output = this.getDescription() + " " + this.getStartDate().toString() + " " + this.getStartTime().toString()+ " "  + this.getEndDate().toString() + " " + this.getEndTime().toString();
		return output;
	}
}
