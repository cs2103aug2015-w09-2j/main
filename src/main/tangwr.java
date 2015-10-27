package main;

import java.text.ParseException;
import java.util.ArrayList;

public class tangwr {
	
	public static void main(String[] args) throws NoSuchFieldException, ParseException
	{
		FileStorage fs = new FileStorage();
		//fs.setFilePath("pikachu.txt");
		//System.out.println("Hello world");
		
		
		//JsonFile js = new JsonFile("data.json");
		
		DateClass date1 = new DateClass(10, 1);
		DateClass date2 = new DateClass(10, 1);
		TimeClass time1 = new TimeClass("1510");
		TimeClass time2 = new TimeClass("1700");
		
		Event e = new Event("jad", date1, time1, date2, time2);
		Floating f = new Floating("jade");
		Deadline d = new Deadline("eejade", date1, time1);
		
		
		//Event e = new Event("werewr", date1, time1, date2, time2);
		//Floating f = new Floating("234dff");
		//Deadline d = new Deadline("eewr323r", date1, time1);
		//Event r = null;
		
		//fs.writeTask(d);
		//fs.writeTask(f);
		//fs.writeTask(e);
		fs.deleteTask(e);
		
		/*
		ArrayList<Task> sss = fs.absoluteSearch("ra");
		for(int i=0; i<sss.size(); i++){
			Task t = sss.get(i);
			System.out.println(t);
		}
		*/
		/*
		ArrayList<Task> se = fs.searchEventTask("r");
		ArrayList<Task> sd = fs.searchDeadlineTask("r");
		ArrayList<Task> sf = fs.searchFloatingTask("r");
		ArrayList<Task> sa = fs.searchAllTask("r");
		
		
		for(int i=0; i<se.size(); i++){
			Task t = se.get(i);
			System.out.println(t);
		}
		for(int i=0; i<sd.size(); i++){
			Task t = sd.get(i);
			System.out.println(t);
		}
		for(int i=0; i<sf.size(); i++){
			Task t = sf.get(i);
			System.out.println(t);
		}
		for(int i=0; i<sa.size(); i++){
			Task t = sa.get(i);
			System.out.println(t);
		}
		*/
		/*
		ArrayList<ArrayList<Task>> tk = fs.search("ja");
		
		ArrayList<Task> t = tk.get(0);
		
		System.out.println("Event");
		for(int i = 0; i<t.size(); i++){
			Task k = t.get(i);
			System.out.println(k.getDescription() + " " + ((Event)k).getStartDate().toString() + " " + ((Event)k).getStartTime().toString()
					+ " " + ((Event)k).getEndDate().toString() + " " + ((Event)k).getEndTime().toString());
		}
		
		
		ArrayList<Task> dead = tk.get(1);
		System.out.println("Deadline");
		for(int i = 0; i<dead.size(); i++){
			Task k = dead.get(i);
			System.out.println(k.getDescription() + " " + ((Deadline)k).getEndDate().toString() + " " + ((Deadline)k).getEndTime().toString());
		}
		
		System.out.println("Floating");
		ArrayList<Task> ff = tk.get(2);
		for(int i = 0; i<ff.size(); i++){
			Task k = ff.get(i);
			System.out.println(k.getDescription());
		}
		*/
		//System.out.println("Hello");
		//js.search("he");
		//js.jsonWriteTask(event);
		//js.jsonWriteTask(f);
		//fs.writeTask(e);
		//js.jsonWriteTask(d);
		
		//js.getEventTask();
		
		/*
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
			System.out.println(k.getDescription() + " " + ((Deadline)k).getEndDate().toString() + " " + ((Deadline)k).getEndTime().toString());
		}
		
		ArrayList<Task> ff = fs.readFloatingTask();
		for(int i = 0; i<ff.size(); i++){
			Task k = ff.get(i);
			System.out.println(k.getDescription());
		}
		
		*/
		
		
	}

}
