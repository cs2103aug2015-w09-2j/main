package main;

import static org.junit.Assert.*;



import org.junit.Test;

import main.FileStorage;
import main.Parser;
import main.ui.MainApp;

public class LogicTest {
	Logic myLogic = Logic.getInstance();
	FileStorage fileStorage = new FileStorage();
	Parser parser = Parser.getInstance();
	MainApp main;
	// myLogic.setMainApp(main);

	@Test
	public final void testAddCommand() {
		// boundary case to detect if the function is able to detect that this
		// is not a valid method
		assertTrue(myLogic.processCommand("save ./"));
		assertTrue(myLogic.processCommand("clear"));
		assertTrue(myLogic.processCommand("show done"));
		assertTrue(myLogic.processCommand("clear"));
		assertTrue(myLogic.processCommand("show overdue"));
		assertTrue(myLogic.processCommand("clear"));

		assertTrue(myLogic.processCommand("show ongoing"));
		assertFalse(myLogic.processCommand(""));
		assertFalse(myLogic.processCommand("show nothing"));
		assertFalse(myLogic.processCommand("show "));
		assertFalse(myLogic.processCommand("delete 1"));
		assertFalse(myLogic.processCommand("delete 0"));
		assertTrue(myLogic.processCommand("show overdue"));
		assertFalse(myLogic.processCommand("delete 1"));

		assertTrue(myLogic.processCommand("add get laundry"));

		// boundary case minor change made so that the ad => add
		assertTrue(myLogic.processCommand("add get detergent"));

		// assertEquals(myLogic)
		assertFalse(myLogic.processCommand("add progress report by 5/11 2pm"));

		assertFalse(myLogic.processCommand("add proposal plant by 8/11 2359"));
		assertTrue(myLogic.processCommand("add planning report by tomorrow"));
		assertTrue(myLogic.processCommand("add finish software demo by tomorrow 12pm"));
		//assertTrue(myLogic.processCommand("add finish printing app by tomorrow 2pm"));
		assertTrue(myLogic.processCommand("add finish annual report 2014/15 in 2 weeks"));
		assertTrue(myLogic.processCommand("add fill in application form in 2 days"));
		assertTrue(myLogic.processCommand("add pay for housing by next mon"));
		assertFalse(myLogic.processCommand("add annual customer meetings from 5/11 to 6/11"));
		assertTrue(myLogic.processCommand("add bank clearings from tomorrow to next week"));
		//assertTrue(myLogic.processCommand("add summer camp from 6/11 9am to next month"));
		assertTrue(myLogic.processCommand("add meeting with potential clients at tomorrow"));
		assertTrue(myLogic.processCommand("update 6 -d proposal plan -e 9/11 2359"));
		assertTrue(myLogic.processCommand("undo"));
		assertTrue(myLogic.processCommand("redo"));
		assertTrue(myLogic.processCommand("update 9 -d collect laundry from shop -e 9/11 2200"));
		assertTrue(myLogic.processCommand("done 1"));
		assertFalse(myLogic.processCommand("done 0"));
		assertTrue(myLogic.processCommand("done 1-3"));
		assertTrue(myLogic.processCommand("show done"));
		assertFalse(myLogic.processCommand("done 1"));
		assertTrue(myLogic.processCommand("show ongoing"));
		assertTrue(myLogic.processCommand("done 5,2"));
		assertTrue(myLogic.processCommand("delete 1-3"));
		assertTrue(myLogic.processCommand("undo"));
		assertTrue(myLogic.processCommand("redo"));
		//assertTrue(myLogic.processCommand("add summer camp from 6/11 9am to next month"));
		//assertTrue(myLogic.processCommand("add new latest ipad by 8/11 0100"));
		//assertTrue(myLogic.processCommand("delete 1-1"));
		assertFalse(myLogic.processCommand("search "));
		assertTrue(myLogic.processCommand("search on 7/11"));
		assertTrue(myLogic.processCommand("search after 9/11"));
		assertTrue(myLogic.processCommand("search before 7/12"));
		assertTrue(myLogic.processCommand("search from 7/11 to 7/12"));
		assertTrue(myLogic.processCommand("search m on 7/11"));
		assertTrue(myLogic.processCommand("search m after 9/11"));
		assertTrue(myLogic.processCommand("search m before 7/12"));
		assertTrue(myLogic.processCommand("search m from 7/11 to 7/12"));
		assertTrue(myLogic.processCommand("search m"));
		//assertTrue(myLogic.processCommand("save /Users/Ravi/Desktop/CS1010"));
	}
}
