
public class Deadline extends Task{
	
	private DateClass endDate;
	private TimeClass endTime;
	
	public Deadline(String description, DateClass endDate, TimeClass endTime){
		super(description);
		this.endDate = endDate;
		this.endTime = endTime;
	}
	public DateClass getEndDate(){
		return endDate;
	}
	
	public TimeClass getEndTime(){
		return endTime;
	}
	public String toString(){
		String output = "";
		output = this.getDescription() + " " + this.getEndDate().toString() + " " + this.getEndTime().toString(); 
		return output;
		
	}
}
