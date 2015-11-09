package main;

import static org.junit.Assert.*;

import java.text.ParseException;

import main.Deadline;
import main.Event;
import main.Floating;
import main.DateClass;
import main.TimeClass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileStorageTest {

	String [] eventArr = {"Meeting with boss", "Project presentation", "Buffet lunch", "Meeting with client", "Discussion group"};
	String [] deadlineArr = {"Finished homework", "Buy a computer", "Finish drawing", "Book a flight", "Buy tickets"};
	String [] floatingArr = {"Sleep", "Struck lottery", "Learn dancing", "Wash the clothes", "Buy new clothes"};
	int [] day1 = {1, 2, 3, 4, 5};
	int [] day2 = {11, 12, 13, 14, 15};

	DateClass date1, date2;
	TimeClass time1 = new TimeClass("1000");
	TimeClass time2 = new TimeClass("1200");
	Event e;
	Deadline d;
	Floating f;

	FileStorage fs = new FileStorage();

	@Before
	public void testWriteTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], 10);
			date2 = new DateClass(day2[i], 10);
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			fs.writeTask(e);
			fs.writeTask(d);
			fs.writeTask(f);
		}
		assert(true);
	}

	@Before
	public void testWriteDoneTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], 10);
			date2 = new DateClass(day2[i], 10);
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			fs.writeTask(e);
			fs.writeTask(d);
			fs.writeTask(f);
		}
		assert(true);
	}

	@Before
	public void testWriteOverdueTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], 10);
			date2 = new DateClass(day2[i], 10);
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			fs.writeTask(e);
			fs.writeTask(d);
			fs.writeTask(f);
		}
		assert(true);
	}

	@Test
	public void testReadDoneTask() {
		assertNotNull(fs.readDoneTask());
	}

	@Test
	public void testReadOverdueTask() {
		assertNotNull(fs.readOverdueTask());
	}

	@Test
	public void testReadAllTask() {
		assertNotNull(fs.readAllTask());
	}

	@Test
	public void testSearchAllTask() {
		assertNotNull(fs.searchAllTask(eventArr[0]));
	}

	@Test
	public void testAbsoluteSearchStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchAllTaskBeforeDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchAllTaskOnDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchAllTaskAfterDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchAllTaskBetweenDates() {
		fail("Not yet implemented");
	}

	@After
	public void testDeleteTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], 10);
			date2 = new DateClass(day2[i], 10);
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			fs.deleteTask(e);
			fs.deleteTask(d);
			fs.deleteTask(f);
		}
		assert(true);
	}

	@After
	public void testDeleteDoneTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], 10);
			date2 = new DateClass(day2[i], 10);
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			fs.deleteDoneTask(e);
			fs.deleteDoneTask(d);
			fs.deleteDoneTask(f);
		}
		assert(true);
	}

	@After
	public void testDeletOverdueTask() throws NoSuchFieldException, ParseException {
		for(int i=0; i<5; i++){
			date1 = new DateClass(day1[i], 10);
			date2 = new DateClass(day2[i], 10);
			e = new Event(eventArr[i], date1, time1, date2, time2);
			d = new Deadline(deadlineArr[i], date2, time2);
			fs.deleteOverdueTask(e);
			fs.deleteOverdueTask(d);
			fs.deleteOverdueTask(f);
		}
		assert(true);
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
