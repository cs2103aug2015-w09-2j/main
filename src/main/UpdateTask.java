package main;

public class UpdateTask extends Task{

	private static final int START_DATE = 0;
	private static final int START_TIME = 0;
	private static final int END_DATE = 1;
	private static final int END_TIME = 1;
	private static final int MAX_DATETIME_COUNT = 2;

	private DateClass[] dates;
	private TimeClass[] time;
	private String strDescription;
	private String strSearchString;

	
	public UpdateTask(String searchString){
		super(null);
		strDescription = null;
		strSearchString = searchString;
		dates = new DateClass[MAX_DATETIME_COUNT];
		time = new TimeClass[MAX_DATETIME_COUNT];
	}

	/**
	 * @return
	 */
	public int getDateCount(){
		int intCount = 0;
		for(int i = 0; i < MAX_DATETIME_COUNT; i++){
			if(dates[i] != null){
				intCount++;
			}
		}

		return intCount;
	}

	/**
	 * @return
	 */
	public int getTimeCount(){
		int intCount = 0;
		for(int i = 0; i < MAX_DATETIME_COUNT; i++){
			if(time[i] != null){
				intCount++;
			}
		}

		return intCount;
	}

	/**
	 * @return
	 */
	public boolean hasDescription(){
		return strDescription != null;
	}

	/**
	 * @return
	 */
	public boolean hasStartDate(){
		return dates[START_DATE] != null;
	}

	/**
	 * @return
	 */
	public boolean hasEndDate(){
		return dates[END_DATE] != null;
	}

	/**
	 * @return
	 */
	public boolean hasStartTime(){
		return time[START_TIME] != null;
	}

	/**
	 * @return
	 */
	public boolean hasEndTime(){
		return time[END_TIME] != null;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(DateClass startDate) {
		dates[START_DATE] = startDate;

	}

	/**
	 * @param endDate
	 */
	public void setEndDate(DateClass endDate) {
		dates[END_DATE] = endDate;
	}

	/**
	 * @param startTime
	 */
	public void setStartTime(TimeClass startTime) {
		time[START_TIME] = startTime;
	}

	/**
	 * @param endTime
	 */
	public void setEndTime(TimeClass endTime) {
		time[END_TIME] = endTime;
	}


	/**
	 * @param strDescription
	 */
	public void setDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	/**
	 * @return
	 */
	public DateClass getStartDate() {
		return dates[START_DATE];
	}

	/**
	 * @return
	 */
	public DateClass getEndDate() {
		return dates[END_DATE];
	}

	/**
	 * @return
	 */
	public TimeClass getStartTime() {
		return time[START_TIME];
	}

	/**
	 * @return
	 */
	public TimeClass getEndTime() {
		return time[END_TIME];
	}

	
	public String getDescription() {
		return strDescription;
	}

	/**
	 * @return
	 */
	public String getSearchString(){
		return strSearchString;
	}

	
}