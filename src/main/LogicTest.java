package main;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class LogicTest {
	Logic myLogic = Logic.getInstance();
	FileStorage fileStorage = new FileStorage();
	Parser parser = new Parser();
	@Test
	public final void testProcessCommand() {
		// boundary case to detect if the function is able to detect that this
		// is not a valid method
		assertTrue(myLogic.processCommand("add this is an incorrect input"));

		// boundary case minor change made so that the ad => add
		assertTrue(myLogic.processCommand("add this is a correct input"));
		
		//assertEquals(myLogic)
		assertFalse(myLogic.processCommand(""));
		
		
		
	}
}
