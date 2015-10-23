package main;

import java.text.ParseException;
import java.util.ArrayList;

public class tangwr {
	public static void main(String[] args) throws NoSuchFieldException, ParseException
	{
		JsonFile js = new JsonFile("data.json");
		FileStorage fs = new FileStorage();
		DateClass date1 = new DateClass(11, 11);
		DateClass date2 = new DateClass(12, 11);
		TimeClass time1 = new TimeClass("1121");
		TimeClass time2 = new TimeClass("2212");
		
		Event e = new Event("pig", date1, time1, date2, time2);
		Floating f = new Floating("hello");
		Deadline d = new Deadline("dog", date1, time2);
		
		
		//js.jsonWriteTask(event);
		//js.jsonWriteTask(f);
		//fs.writeTask(e);
		//js.jsonWriteTask(d);
		
		//js.getEventTask();
		
		ArrayList<Task> t = fs.readEventTask();
		
		for(int i = 0; i<t.size(); i++){
			Task k = t.get(i);
			System.out.println(k.getDescription() + " " + ((Event)k).getStartDate().toString() + " " + ((Event)k).getStartTime().toString()
					+ " " + ((Event)k).getEndDate().toString() + " " + ((Event)k).getEndTime().toString());
		}
		
		ArrayList<Task> dead = fs.readDeadlineTask();
		System.out.println(dead.size());
		for(int i = 0; i<dead.size(); i++){
			Task k = dead.get(i);
			System.out.println(k.getDescription() + " " + ((Event)k).getEndDate().toString() + " " + ((Event)k).getEndTime().toString());
		}
		
		ArrayList<Task> ff = fs.readFloatingTask();
		for(int i = 0; i<ff.size(); i++){
			Task k = ff.get(i);
			System.out.println(k.getDescription());
		}
		System.out.println("Hello");
	}
}
