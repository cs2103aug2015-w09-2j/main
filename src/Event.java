
public class Event extends Task{


	private DateClass startDate;
	private DateClass endDate;
	private TimeClass startTime;
	private TimeClass endTime;
	
	public Event(String strDescription, DateClass startDate, TimeClass startTime, DateClass  endDate, TimeClass endTime){
		
		super(strDescription);
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public DateClass getStartDate() {
		return startDate;
	}
	
	public DateClass getEndDate() {
		return endDate;
	}
	
	public TimeClass getStartTime() {
		return startTime;
	}
	
	public TimeClass getEndTime() {
		return endTime;
	}
}
