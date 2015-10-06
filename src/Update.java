
public class Update extends Task{

	private static final int START_DATE = 0;
	private static final int START_TIME = 0;
	private static final int END_DATE = 1;
	private static final int END_TIME = 1;
	private static final int MAX_DATETIME_COUNT = 2;
	
	private DateClass[] dates;
	private TimeClass[] time;
	private String strDescription;
	
	public Update(){
		super(null);
		strDescription = null;
		dates = new DateClass[MAX_DATETIME_COUNT];
		time = new TimeClass[MAX_DATETIME_COUNT];
	}
	
	public int getDateCount(){
		int intCount = 0;
		for(int i = 0; i < MAX_DATETIME_COUNT; i++){
			if(dates[i] != null){
				intCount++;
			}
		}
		
		return intCount;
	}
	
	public int getTimeCount(){
		int intCount = 0;
		for(int i = 0; i < MAX_DATETIME_COUNT; i++){
			if(time[i] != null){
				intCount++;
			}
		}
		
		return intCount;
	}
	
	public boolean hasDescription(){
		return strDescription != null;
	}
	
	public boolean hasStartDate(){
		return dates[START_DATE] != null;
	}
	
	public boolean hasEndDate(){
		return dates[END_DATE] != null;
	}
	
	public boolean hasStartTime(){
		return time[START_TIME] != null;
	}
	
	public boolean hasEndTime(){
		return time[END_TIME] != null;
	}
	
	public void setStartDate(DateClass startDate) {
		dates[START_DATE] = startDate;
		
	}
	
	public void setEndDate(DateClass endDate) {
		dates[END_DATE] = endDate;
	}
	
	public void setStartTime(TimeClass startTime) {
		time[START_TIME] = startTime;
	}
	
	public void setEndTime(TimeClass endTime) {
		time[END_TIME] = endTime;
	}

	
	public void setDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	
	public DateClass getStartDate() {
		return dates[START_DATE];
	}
	
	public DateClass getEndDate() {
		return dates[END_DATE];
	}

	public TimeClass getStartTime() {
		return time[START_TIME];
	}

	public TimeClass getEndTime() {
		return time[END_TIME];
	}
	
	@Override 
	public String getDescription() {
		return strDescription;
	}
	
}
