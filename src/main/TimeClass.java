package main;

public class TimeClass implements Comparable<TimeClass>{

	private int intHour;
	private int intMin;
	private String strHour;
	private String strMin;

	public TimeClass(String hour, String min){
		this.strHour = hour;
		this.strMin = min;

		this.intHour = Integer.valueOf(hour);
		this.intMin = Integer.valueOf(min);
	}

	public TimeClass(String strhhMM){
		TimeClass time = TimeHandler.parse(strhhMM);
		intHour = time.intHour;
		intMin = time.intMin;

		this.strHour = time.getStringHour();
		this.strMin = time.getStringMin();

	}

	public int getIntHour(){
		return intHour;
	}

	public int getIntMin(){
		return intMin;
	}
	public String getStringHour(){
		return strHour;
	}
	public String getStringMin(){
		return strMin;
	}

	@Override
	public String toString(){
		return strHour + "" + strMin;
	}

	/*
	 * Added for easier comparison [Teddy]
	 * Razali, later on make sure that you check the boundary of intHour and intMin
	 * By the way, why do we need intHour and stringHour, intMin and stringMin?
	 */
	@Override
	public int compareTo(TimeClass time) {
		if (this.getIntMin() == time.getIntMin()) {
			return this.getIntMin() - time.getIntMin();
		} else {
			return this.getIntHour() - time.getIntHour();
		}
	}
}
