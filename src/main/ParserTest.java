package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {

	private Parser parser;
	
	public ParserTest(){
		parser = new Parser();
	}
	@Test
	public void testParsingFloating() {
		
		Command command = parser.parse("add description");
		Floating floating = (Floating)command.getTask();
		assertEquals("description", floating.getDescription());
	}

}
