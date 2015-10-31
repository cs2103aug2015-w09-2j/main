package main;

public class Deadline extends Task {

	private DateClass endDate;
	private TimeClass endTime;
	private boolean isDone ;
	/**
	 * Description Constructor : Creates a deadline task, which is a subclass of the Deadline abstract class.
	 * @param description
	 * @param endDate
	 * @param endTime
	 */
	public Deadline(String description, DateClass endDate, TimeClass endTime){
		super(description);
		this.endDate = endDate;
		this.endTime = endTime;
	}

	/**
	 * Description Gets the endDate of the deadline
	 * @return endDate
	 */
	public DateClass getEndDate(){
		return endDate;
	}

	/**
	 * Description Gets the endTime of the deadline
	 * @return endTime
	 */
	public TimeClass getEndTime(){
		return endTime;
	}
	
	public void setEndTime(TimeClass endTime) {
		this.endTime = endTime;
	}
	
	public void setEndDate(DateClass endDate) {
		this.endDate = endDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String output = "";
		output = this.getDescription() + " " + this.getEndDate().toString() + " " + this.getEndTime().toString();
		return output;
	}

	@Override
	public int compareTo(Task anotherDeadline) {
		if (this.getEndDate().equals(((Deadline) anotherDeadline).getEndDate())) {
			return this.getEndTime().compareTo(((Deadline) anotherDeadline).getEndTime());
		} else {
			return this.getEndDate().compareTo(((Deadline) anotherDeadline).getEndDate());
		}
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
}
