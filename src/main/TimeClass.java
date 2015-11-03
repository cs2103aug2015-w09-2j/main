package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	//Added  by [Ravi] to check for overdue task
	public TimeClass(int hourNow, int minuteNow) {
		this.intHour = hourNow;
		this.intMin = minuteNow;

		this.strHour = hourNow + "";
		this.strMin = minuteNow+ "";

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

	public String to24HourFormat(){
		return toString() + "h";
	}

	public String to12HourFormat(){
		String str24hour = strHour + ":" + strMin;
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date date;
		try {
			date = df.parse(str24hour);
		} catch (ParseException e) {
			return null;
		}

		DateFormat df2 = new SimpleDateFormat("h:mma");
		String asd = df2.format(date);
		return asd;
	}
	/*
	 * Added for easier comparison [Teddy]
	 * Razali, later on make sure that you check the boundary of intHour and intMin
	 * By the way, why do we need intHour and stringHour, intMin and stringMin?
	 */
	@Override
	public int compareTo(TimeClass time) {
		if (this.getIntHour() == time.getIntHour()) {
			return this.getIntMin() - time.getIntMin();
		} else {
			return this.getIntHour() - time.getIntHour();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TimeClass) {
			TimeClass time = (TimeClass) obj;
			return (this.getIntHour() == time.getIntHour())
					&& (this.getIntMin() == time.getIntMin());
		} else {
			return false;
		}
	}
}
