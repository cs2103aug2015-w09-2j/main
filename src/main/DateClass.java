package main;

import java.text.ParseException;

/***
 *
 * @author Razali
 *
 *  If the date is 24/09/2015
 *  intDay = 24, intMonth = 9, intYear = 2015
 *  strDay = "Thursday", strMonth = "September", strYear = "2015"
 *
 *  Dependencies: DateHandler
 */

public class DateClass implements Comparable<DateClass> {

	// added by Teddy
	private int[] numOfDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // number of days in non-leap year

	private int intDay;
	private int intMonth;
	private int intYear;

	private String strDay;
	private String strMonth;
	private String strYear;


	public DateClass(int day, int month) throws NoSuchFieldException, ParseException{
		this(day, month, DateHandler.getIntYearNow());
	}

	public DateClass(int day, int month, int year) throws NoSuchFieldException, ParseException{
		intDay = day;
		intMonth = month;
		intYear = year;

		strDay = DateHandler.getStringDay(day, month, year);
		strMonth = DateHandler.getStringMonth(month);
		strYear = DateHandler.getStringYear(year);

	}

	public DateClass(String day, String month, String year) throws NoSuchFieldException, ParseException{

		this(DateHandler.getDate(Integer.valueOf(day), DateHandler.getIntMonth(month), DateHandler.getIntYear(year)));
	}


	public DateClass(String strFullDate) throws NoSuchFieldException, ParseException{
		String[] ddmmyy = strFullDate.split("/");

		intDay = Integer.valueOf(ddmmyy[0]);
		intMonth = Integer.valueOf(ddmmyy[1]);
		intYear = Integer.valueOf(ddmmyy[2]);

		strDay = DateHandler.getStringDay(intDay, intMonth, intYear);
		strMonth = DateHandler.getStringMonth(intMonth);
		strYear = DateHandler.getStringYear(intYear);
	}

	public int getIntDay() {
		return intDay;
	}

	public int getIntMonth() {
		return intMonth;
	}

	public int getIntYear() {
		return intYear;
	}

	public String getStrDay() {
		return strDay;
	}

	public String getStrMonth() {
		return strMonth;
	}

	public String getStrYear() {
		return strYear;
	}

	@Override
	public String toString(){
		return intDay + "/" + intMonth + "/" + intYear;
	}

	/*
	 * Codes added by Teddy to support GUI
	 */

	@Override
	public int compareTo(DateClass date) {
		if (this.getIntYear() == date.getIntYear()) {
			if (this.getIntMonth() == date.getIntMonth()) {
				return this.getIntDay() - date.getIntDay();
			} else {
				return this.getIntMonth() - date.getIntMonth();
			}
		} else {
			return this.getIntYear() - date.getIntYear();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DateClass) {
			DateClass date = (DateClass) obj;
			return (this.getIntYear() == date.getIntYear())
					&& (this.getIntMonth() == date.getIntMonth()
					&& (this.getIntDay() == date.getIntDay()));
		} else {
			return false;
		}
	}

	public boolean isToday() {
		int currDate = DateHandler.getIntDayNow();
		int currMonth = DateHandler.getIntMonthNow();
		int currYear = DateHandler.getIntYearNow();

		return (currDate == intDay) && (currMonth == intMonth) && (currYear == intYear);
	}

	public boolean isTomorrow() {
		int currDate = DateHandler.getIntDayNow();
		int currMonth = DateHandler.getIntMonthNow();
		int currYear = DateHandler.getIntYearNow();

		if (intYear != currYear)
			return false;
		else if (currMonth != intMonth) {
			if (intMonth < currMonth) {
				return false;
			} else {
				int currMonthDays = numOfDays[currMonth];

				if (currMonth == 2 && isLeapYear(currYear))
					currMonthDays++;

				return currDate + intDay - currMonthDays == 1;
			}
		} else if (intDay != (currDate +1)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isWithinAWeek() {
		int currDate = DateHandler.getIntDayNow();
		int currMonth = DateHandler.getIntMonthNow();
		int currYear = DateHandler.getIntYearNow();

		if (intYear != currYear)
			return false;
		else if (currMonth != intMonth) {
			if (intMonth < currMonth || intMonth - currMonth > 1) {
				return false;
			} else {
				int currMonthDays = numOfDays[currMonth];

				if (currMonth == 2 && isLeapYear(currYear))
					currMonthDays++;

				return intDay + currMonthDays - currDate <= 7;
			}
		} else if (intDay - currDate > 7) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isLeapYear(int year) {
		return (year %4 == 0 && year % 100 > 0) || (year % 400 == 0);
	}
}
