
public class Deadline extends Task{
	
	private DateClass endDate;
	
	public Deadline(String description, DateClass endDate){
		super(description);
		this.endDate = endDate;
	}
	public DateClass getEndDate(){
		return endDate;
	}
}
