//@@author A0133267H
package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeHandler {

	public static final String PATTERN_24_HOUR = "([01][0-9]|2[0-3]):?[0-5][0-9][h]?";
	public static final String PATTERN_12_HOUR = "^(0?[1-9]|1[012])(:?[0-5]\\d)?[AaPp][Mm]";
	
	public static int getHourNow(){
		DateFormat dateFormat = new SimpleDateFormat("HH");
		Date date = new Date();
		String strHour = dateFormat.format(date);
		int intHour = Integer.valueOf(strHour);

		return intHour;
	}

	public static int getMinuteNow(){
		DateFormat dateFormat = new SimpleDateFormat("mm");
		Date date = new Date();
		String strMinute = dateFormat.format(date);
		int intMinute = Integer.valueOf(strMinute);

		return intMinute;
	}

	@SuppressWarnings("unused")
	private static int[] parseHourAndMin(String strHHMM){
		String strHour = "" + strHHMM.charAt(0) + strHHMM.charAt(1);
		String strMin = "" + strHHMM.charAt(2) + strHHMM.charAt(3);

		int[] intHourAndMin = new int[2];
		intHourAndMin[0] = Integer.valueOf(strHour);
		intHourAndMin[1] = Integer.valueOf(strMin);

		return intHourAndMin;
	}

	public static TimeClass parse(String strTime){

		String[] strHourAndMin = new String[2];
		
		if(strTime.matches(PATTERN_24_HOUR)){
			strTime = strTime.replace(":", "");
			strTime = strTime.replace("h", "");
			strHourAndMin[0] = "" + strTime.charAt(0) + strTime.charAt(1);
			strHourAndMin[1] = "" + strTime.charAt(2) + strTime.charAt(3);
			

			TimeClass time = new TimeClass(strHourAndMin[0], strHourAndMin[1]);

			return time;

		} 
		else
			return null;

	}

	public static boolean isBeforeNow(TimeClass time){
		TimeClass currentTime = new TimeClass(TimeHandler.getHourNow(), TimeHandler.getMinuteNow());
		
		return time.compareTo(currentTime) < 0 ? true : false;
	}
	
	public static boolean isNow(TimeClass time){
		TimeClass currentTime = new TimeClass(TimeHandler.getHourNow(), TimeHandler.getMinuteNow());
		
		return time.compareTo(currentTime) == 0 ? true : false;
	}
}
