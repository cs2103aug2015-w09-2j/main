package main;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class LogicTest {
	Logic myLogic = Logic.getInstance();
	@Test
	public final void testProcessCommand() {
		try {//boundary case to detect if the function is able to detect that this is not a valid method 
			assertFalse(myLogic.processCommand("ad -f this is an incorrect input"));
		} catch (NoSuchFieldException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {//boundary case minor change made so that the ad => add  
			assertTrue(myLogic.processCommand("add -f this is an incorrect input"));
		} catch (NoSuchFieldException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			assertFalse(myLogic.processCommand(""));
		} catch (NoSuchFieldException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
