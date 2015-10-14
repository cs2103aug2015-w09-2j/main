package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeHandler {

	public static final String PATTERN_24_HOUR = "([01][0-9]|2[0-3]):?[0-5][0-9][h]?";

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

	private static int[] parseHourAndMin(String strHHMM){
		String strHour = "" + strHHMM.charAt(0) + strHHMM.charAt(1);
		String strMin = "" + strHHMM.charAt(2) + strHHMM.charAt(3);

		int[] intHourAndMin = new int[2];
		intHourAndMin[0] = Integer.valueOf(strHour);
		intHourAndMin[1] = Integer.valueOf(strMin);

		return intHourAndMin;
	}

///public static void main(String[] args){
///	String time = "2344h";
///	int[] t = parse(time);
///
///	System.out.println(String.format("%d %d", t[0], t[1]));
///}
///
	public static TimeClass parse(String strTime){

		int[] intHourAndMin = new int[2];
		String[] strHourAndMin = new String[2];

		if(strTime.matches(PATTERN_24_HOUR)){
			strTime = strTime.replace(":", "");
			strTime = strTime.replace("h", "");
			strHourAndMin[0] = "" + strTime.charAt(0) + strTime.charAt(1);
			strHourAndMin[1] = "" + strTime.charAt(2) + strTime.charAt(3);
			intHourAndMin = parseHourAndMin(strTime);
			int intHour = intHourAndMin[0];
			int intMin = intHourAndMin[1];

			TimeClass time = new TimeClass(strHourAndMin[0], strHourAndMin[1]);

			return time;

		}
		else
			return null;

	}
}