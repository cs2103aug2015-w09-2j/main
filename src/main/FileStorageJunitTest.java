package main;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileStorageJunitTest {

	FileStorage fs = FileStorage.getInstance();
	String [] eventArr = {"Meeting with boss", "Skype meeting", "Dental appoinment", "Lunch with client", "Projet presentation"};
	String [] deadlineArr = {"Finish project", "Recruit new member", "Collect files", "Maths homework", "Return books"};
	String [] floatingArr = {"Buy new car", "Clean the house", "Visit korea", "Watch movie", "Drinking with buddy"};
	int [] day1 = {5,7,3,2,1};
	int [] day2 = {22,30,16,25,18};
	int month = 12;
	String[] t1 = {"0800", "0900", "1000", "1030", "1100"};
	String[] t2 = {"1300", "1400", "1500", "1630", "1800"};
	Event e;
	Deadline d;
	Floating f;
	DateClass date1, date2;
	TimeClass time1, time2;
	
	@Test
	public void testWriteTask() throws NoSuchFieldException, ParseException {
		
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], month);
			date2 = new DateClass(day2[i], month);
			time1 = new TimeClass(t1[i]);
			time2 = new TimeClass(t2[i]);
			
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			f = new Floating(floatingArr[i]);
			
			fs.writeTask(e);
			fs.writeTask(d);
			fs.writeTask(f);
			
		
		}
		
	}

	@Test
	public void testWriteDoneTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], month);
			date2 = new DateClass(day2[i], month);
			time1 = new TimeClass(t1[i]);
			time2 = new TimeClass(t2[i]);
			
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			f = new Floating(floatingArr[i]);
			
			fs.writeTask(e);
			fs.writeTask(d);
			fs.writeTask(f);
			
		
		}
	}

	@Test
	public void testWriteOverdueTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], month);
			date2 = new DateClass(day2[i], month);
			time1 = new TimeClass(t1[i]);
			time2 = new TimeClass(t2[i]);
			
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			f = new Floating(floatingArr[i]);
			
			
			fs.writeTask(e);
			fs.writeTask(d);
			fs.writeTask(f);
			
		
		}
		
	}

	@Test
	public void testReadDoneTask() {
		ArrayList<Task> a = fs.readDoneTask();
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	@Test
	public void testReadOverdueTask() {
		ArrayList<Task> a = fs.readOverdueTask();
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	@Test
	public void testReadEventTask() {
		ArrayList<Task> a = fs.readEventTask();
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	@Test
	public void testReadDeadlineTask() {
		ArrayList<Task> a = fs.readDeadlineTask();
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	@Test
	public void testReadFloatingTask() {
		ArrayList<Task> a = fs.readFloatingTask();
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	@Test
	public void testReadAllTask() {
		ArrayList<Task> a = fs.readFloatingTask();
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	
	@Test
	public void testSearchAllTask() {
		ArrayList<Task> a = fs.searchAllTask(eventArr[0]);
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	
	@Test
	public void testAbsoluteSearchStringString() throws NoSuchFieldException, ParseException {
		date1 = new DateClass(day1[0], month);
		ArrayList<Task> a = fs.absoluteSearch(eventArr[0], date1.toString());
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	
	@Test
	public void testSearchAllTaskBeforeDate() throws NoSuchFieldException, ParseException {
		date1 = new DateClass(day2[0], month);
		ArrayList<Task> a = fs.searchAllTaskBeforeDate(null, date1);
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}



	@Test
	public void testSearchAllTaskOnDate() throws NoSuchFieldException, ParseException {
		date1 = new DateClass(day1[0], month);
		ArrayList<Task> a = fs.searchAllTaskBeforeDate(null, date1);
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	

	@Test
	public void testSearchAllTaskAfterDate() throws NoSuchFieldException, ParseException {
		date1 = new DateClass(day1[0], month);
		ArrayList<Task> a = fs.searchAllTaskBeforeDate(null, date1);
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	

	@Test
	public void testSearchAllTaskBetweenDates() throws NoSuchFieldException, ParseException {
		date1 = new DateClass(day1[0], month);
		date2 = new DateClass(day2[0], month);
		ArrayList<Task> a = fs.searchAllTaskBetweenDates(null, date1, date2);
		for(int i=0; i<a.size(); i++){
			assertNotNull(a.get(i).toString());
		}
	}

	@After
	public void testDeleteTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], month);
			date2 = new DateClass(day2[i], month);
			time1 = new TimeClass(t1[i]);
			time2 = new TimeClass(t2[i]);
			
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			f = new Floating(floatingArr[i]);
			
			fs.deleteTask(e);
			fs.deleteTask(d);
			fs.deleteTask(f);
			
		
		}
		
	}

	@After
	public void testDeleteDoneTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], month);
			date2 = new DateClass(day2[i], month);
			time1 = new TimeClass(t1[i]);
			time2 = new TimeClass(t2[i]);
			
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			f = new Floating(floatingArr[i]);
			
			fs.deleteDoneTask(e);
			fs.deleteDoneTask(d);
			fs.deleteDoneTask(f);
			
		
		}
	}

	@After
	public void testDeleteOverdueTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], month);
			date2 = new DateClass(day2[i], month);
			time1 = new TimeClass(t1[i]);
			time2 = new TimeClass(t2[i]);
			
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			f = new Floating(floatingArr[i]);
			
			fs.deleteOverdueTask(e);
			fs.deleteOverdueTask(d);
			fs.deleteOverdueTask(f);
			
		
		}
	}

	

	@Test
	public void testGetFilePath() {
		assertNotNull(fs.getFilePath());
	}

	@Test
	public void testGetDonePath() {
		assertNotNull(fs.getDonePath());
	}

	@Test
	public void testGetOverduePath() {
		assertNotNull(fs.getOverduePath());
	}

}
