//@@author A0133267H
package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * @author  Razali
 */
public  class DateHandler {


	public static final int NOT_FOUND = -1;


	public static enum Days{
		SUNDAY("Sun", 1), MONDAY("Mon", 2), TUESDAY("Tue", 3), WEDNESDAY("Wed", 4),
		THURSDAY("Thu", 5), FRIDAY("Fri", 6),
		SATURDAY("Sat", 7);

		private int intDayOfWeek;
		private String strDay;

		private Days(String strDay, int intDayOfWeek){
			this.intDayOfWeek = intDayOfWeek;
			this.strDay = strDay;
		}

		/**
		 * @return The integer representation (1-7) of days
		 */
		public int getIntDayOfWeek(){
			return intDayOfWeek;
		}

		/**
		 *
		 * @param strDay String representation of a day. "Monday" to "Sunday"
		 * @return The integer representation (1-7) of days
		 * @throws NoSuchFieldException if strDay does not match
		 */
		public static int getIntDayOfWeek(String strDay) throws NoSuchFieldException{
			for(Days day : Days.values()){
				if(day.getStringDay().equals(strDay)){
					return day.getIntDayOfWeek();
				}
			}

			throw new NoSuchFieldException();
		}

		/**
		 *
		 * @return String representation of enum ranging from "Monday" to "Sunday"
		 */
		public String getStringDay(){
			return strDay;
		}

		/**
		 *
		 * @param intDayOfWeek Integer ranging from (1-7) where 1 represents "Sunday" and 7 represents "Saturday"
		 * @return String representation of enum ranging from "Monday" to "Sunday"
		 * @throws NoSuchFieldException if intDayofWeek is not within (1-7)
		 */
		public static String getStringDay(int intDayOfWeek) throws NoSuchFieldException{

			for(Days day : Days.values()){

				if(day.getIntDayOfWeek() == intDayOfWeek){
					return day.getStringDay();
				}
			}

			throw new NoSuchFieldException();
		}


	}

	/*** HANDLING OF DAYS ***/

	/**
	 * Gets today's date and returns the day of the month in numerical form
	 * Eg: 20/12/2015 returns 20
	 * @return The current day of the month in numerical form
	 */
	public static int getIntDayNow(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
		String currentDate = simpleDateFormat.format(new Date());
		int intDayNow = Integer.parseInt(currentDate);
		return intDayNow;
	}

	/**
	 * Gets today's date and returns the day of the week in String form
	 * Eg: 20/09/2015 returns "Sunday"
	 * @return The current day of the week in String form
	 */
	public static String getStringDayNow() throws NoSuchFieldException, ParseException{
		int intDayNow = getIntDayNow();
		int intMonthNow = getIntMonthNow();
		int intYearNow = getIntYearNow();
		String strDayNow = getStringDay(intDayNow, intMonthNow, intYearNow);
		return strDayNow;
	}

	/**
	 * Gets the current day given a date.
	 * @param intDayOfMonth The calendar days usually from 1-30
	 * @param strMonth The calendar months. Eg January/March
	 * @param intYear The calendar year. Eg 2015
	 * @return The day of the date. Eg "Monday" or "Tuesday"
	 * @throws ParseException Occurs when a date with "dd/MM/yyyy" cannot be formed
	 * @throws NoSuchFieldException Occurs when the day supplied could not
	 *         be found in the enum Days
	 */
	public static String getStringDay(int intDayOfMonth, String strMonth, int intYear) throws ParseException, NoSuchFieldException {

		return getStringDay(intDayOfMonth, getIntMonth(strMonth), intYear);
	}

	/**
	 *
	 * @param intDayOfMonth The calendar days usually from 1-30
	 * @param strMonth The calendar months. Eg January/March
	 * @param strYear The calendar year. Eg "2015"
	 * @return The day of the date. Eg "Monday" or "Tuesday"
	 * @throws ParseException Occurs when a date with "dd/MM/yyyy" cannot be formed
	 * @throws NoSuchFieldException Occurs when the day supplied could not
	 *         be found in the enum Days
	 */
	public static String getStringDay(int intDayOfMonth, String strMonth, String strYear) throws ParseException, NoSuchFieldException {

		return getStringDay(intDayOfMonth, getIntMonth(strMonth), getIntYear(strYear));
	}

	/**
	 *
	 * @param intintDayOfMonth The calendar days usually from 1-30DayOfMonth
	 * @param intMonth The calendar months. Eg 1 represents January
	 * @param strYear The calendar year. Eg "2015"
	 * @return The day of the date. Eg "Monday" or "Tuesday"
	 * @throws ParseException Occurs when a date with "dd/MM/yyyy" cannot be formed
	 * @throws NoSuchFieldException Occurs when the day supplied could not
	 *         be found in the enum Days
	 */
	public static String getStringDay(int intDayOfMonth, int intMonth, String strYear) throws ParseException, NoSuchFieldException {

		return getStringDay(intDayOfMonth, intMonth, getIntYear(strYear));
	}

	/**
	 *
	 * @param intDayOfMonth The calendar days usually from 1-30
	 * @param intMonth The calendar months. Eg 1 represents January
	 * @param intYear The calendar year. Eg 2015
	 * @return The day of the date. Eg "Monday" or "Tuesday"
	 * @throws ParseException Occurs when a date with "dd/MM/yyyy" cannot be formed
	 * @throws NoSuchFieldException Occurs when the day supplied could not
	 *         be found in the enum Days
	 */
	public static String getStringDay(int intDayOfMonth, int intMonth, int intYear) throws ParseException, NoSuchFieldException {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Date date = simpleDateFormat.parse(intDayOfMonth + "/" + intMonth + "/" + intYear);
		calendar.setTime(date);

		int intDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String strDay = Days.getStringDay(intDayOfWeek);

		return strDay;
	}


	/*** HANDLING OF MONTHS ***/

	/**
	 * Gets the current month given a date.
	 * @return The current month in numerical form. (1-12)
	 */
	public static int getIntMonthNow(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
		String currentDate = simpleDateFormat.format(new Date());
		int intMonthNow = Integer.parseInt(currentDate);
		return intMonthNow;
	}

	/**
	 * Gets the current month given a date
	 * @return The current month in string form. (January-December)
	 */
	public static String getStringMonthNow(){
		int intMonthNow = getIntMonthNow();
		String strMonthNow = getStringMonth(intMonthNow);
		return strMonthNow;
	}

	/**
	 * Converts a month in string form to numerical form
	 * @param strMonth "January" to "December"
	 * @return Month in numerical form (1-12)
	 */
	public static int getIntMonth(String strMonth) {
		strMonth = strMonth.toUpperCase();
		return java.time.Month.valueOf(strMonth).getValue();
	}

	/**
	 * Converts a month in numerical form to string form
	 * @param intMonth Month in numberical form (1-12)
	 * @return Month in string form ("January" to "December")
	 */
	public static String getStringMonth(int intMonth) {
		return java.time.Month.of(intMonth).toString();
	}

	/*** HANDLING OF YEARS ***/

	/**
	 * Gets the current year
	 * @return Integer representation of the year. Eg 2015
	 */
	public static int getIntYearNow(){
		String strYearNow = getStringYearNow();
		int intYearNow = getIntYear(strYearNow);
		return intYearNow;
	}

	/**
	 * Gets the current year
	 * @return Integer representation of the year. Eg "2015"
	 */
	public static String getStringYearNow(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		String strYearNow = simpleDateFormat.format(new Date());

		return strYearNow;
	}

	/**
	 * Converts the year to an Integer
	 * @param String representation of year
	 * @return Integer representation of the year. Eg "2015" to 2015
	 */
	public static int getIntYear(String strYear) {
		return Integer.valueOf(strYear);
	}

	/**
	 * Converts the year to a String
	 * @param intYear Integer representation of year
	 * @return String representation of the year. Eg 2015 to "2015"
	 */
	public static String getStringYear(int intYear) {
		return intYear + "";
	}


	/*** HANDLING OF DATES ***/

	/**
	 * Gets the current full date
	 * @return Date in "dd/MM/yyyy" form
	 */
	public static String getDateNow(){

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = simpleDateFormat.format(new Date());

		return currentDate;

	}

	/**
	 * Gets the full date
	 * @param strDay Numerical Day from ("1" to "31?");
	 * @param strMonth Numerical month from ("1" to "12");
	 * @param strYear Numerical Year Eg "2015"
	 * @return Date in "dd/MM/yyyy" form
	 */
	public static String getDate(String strDay, String strMonth, String strYear) {
		return strDay + "/" + strMonth + "/" + strYear;
	}

	/**
	 *  Gets the full date
	 * @param intDayOfMonth Numerical Day from (1 to 31);
	 * @param intMonth Numerical month from (1 to 12);
	 * @param intYear Numerical Year Eg 2015
	 * @return Date in "dd/MM/yyyy" form
	  * @throws ParseException Occurs when a date with "dd/MM/yyyy" cannot be formed
	 * @throws NoSuchFieldException Occurs when the day supplied could not
	 *         be found in the enum Days
	 */
	public static String getDate(int intDayOfMonth, int intMonth, int intYear) throws ParseException, NoSuchFieldException {

		return getDate(intDayOfMonth + "", intMonth + "", intYear + "");
	}


	/**
	 * Try to parse a string to an date format of dd/MM/YYYY. If year is not
	 * specified, the current year is used.
	 * @param strDate dd/MM or dd/MM/YYYY string format of a date
	 * @return Null if match not found, else returns dd/MM/YYYY as a string
	 */
	public static String tryParse(String strDate){

		String[] splitDate = strDate.split("/");
		int intLengthOfSplit = splitDate.length;
		if(intLengthOfSplit == 0 || intLengthOfSplit == 1){
			return null;
		} 	else if(intLengthOfSplit == 2){//probably only has day and month, missing year
			String strYear = getStringYearNow();
			return strDate + "/" + strYear;
		}	else if(intLengthOfSplit == 3){
			return strDate;
		}



		return null;
	}

	/**
	 * Determines if a month is within range
	 * @param intMonth Integer representation of a month. (1-12)
	 * @return true if it is within range, false otherwise
	 */
	public static boolean isMonthWithinRange(int intMonth){
		return intMonth >= 1 && intMonth <= 12;
	}

	public static boolean isBeforeToday(DateClass date){
		String strDateNow = getDateNow();
		DateClass dateNow;
		try {
			dateNow = new DateClass(strDateNow);
		} catch (NoSuchFieldException e) {
			return false;
		} catch (ParseException e) {
			return false;
		}
		
		return date.compareTo(dateNow) < 0 ? true : false;
	}
	
	public static boolean isToday(DateClass date){
		String strDateNow = getDateNow();
		DateClass dateNow;
		try {
			dateNow = new DateClass(strDateNow);
		} catch (NoSuchFieldException e) {
			return false;
		} catch (ParseException e) {
			return false;
		}
		
		return date.compareTo(dateNow) == 0 ? true : false;
	}
}
