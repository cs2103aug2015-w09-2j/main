package main;

import java.text.ParseException;

public class tangwr {
	public static void main(String[] args) throws NoSuchFieldException, ParseException
	{
		JsonFile js = new JsonFile("data.json");
		
		DateClass date1 = new DateClass(1, 1);
		DateClass date2 = new DateClass(11, 11);
		TimeClass time1 = new TimeClass("1111");
		TimeClass time2 = new TimeClass("2222");
		
		Event event = new Event("hello", date1, time1, date2, time2);
		
		
		js.jsonWriteTask(event);
		
		System.out.println("Hello");
	}
}
