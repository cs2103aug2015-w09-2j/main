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

	@Test
	public void testParsingUpdate(){
		String strCommand;
		Update update;
		UpdateTask updateTask;
		
		/* If user just enters "update", should be null */
		strCommand = "update";
		update = (Update) parser.parse(strCommand);
		assertEquals(update, null);
		
		/* If user just enters "update " with a space in front, should be null */
		strCommand = "update ";
		update = (Update) parser.parse(strCommand);
		assertEquals(update, null);
		
		/* If user just enters "update -x" with a wrong delimeter*/
		strCommand = "update -x";
		update = (Update) parser.parse(strCommand);
		assertEquals(update, null);
		
		/* If user just enters "update -d" with a correct delimiter but no option*/
		strCommand = "update -d";
		update = (Update) parser.parse(strCommand);
		assertEquals(update, null);
		strCommand = "update -s";
		update = (Update) parser.parse(strCommand);
		assertEquals(update, null);
		strCommand = "update -e";
		update = (Update) parser.parse(strCommand);
		assertEquals(update, null);
		
		/* If user just enters taskid*/
		strCommand = "update 1";
		update = (Update) parser.parse(strCommand);
		assertEquals(update, null);
		
		/* If user just enters only description */
		strCommand = "update 1 -d mydescription";
		update = (Update) parser.parse(strCommand);
		updateTask = update.getTaskToUpdate();
		assertEquals(updateTask.getDescription(),"mydescription");
		assertEquals(updateTask.getEndDate(),null);
		assertEquals(updateTask.getStartDate(),null);
		assertEquals(updateTask.getStartTime(),null);
		assertEquals(updateTask.getEndTime(),null);
		
		/* If user just enters only start date and time */
		strCommand = "update 1 -s 11/12/2018 1105";
		update = (Update) parser.parse(strCommand);
		updateTask = update.getTaskToUpdate();
		assertEquals(updateTask.getDescription(),null);
		assertEquals(updateTask.getEndDate(),null);
		assertEquals(updateTask.getStartDate().toString(),"11/12/2018");
		assertEquals(updateTask.getStartTime().toString(),"1105");
		assertEquals(updateTask.getEndTime(),null);
		
		/* If user just enters only enddate and time */
		strCommand = "update 1 -e 11/12/2018 1105";
		update = (Update) parser.parse(strCommand);
		updateTask = update.getTaskToUpdate();
		assertEquals(updateTask.getDescription(),null);
		assertEquals(updateTask.getEndDate().toString(), "11/12/2018");
		assertEquals(updateTask.getEndTime().toString(), "1105");
		assertEquals(updateTask.getStartDate(), null);
		assertEquals(updateTask.getStartTime(), null);
		
		/* If user just enters "update startdatetime and enddatetime" */
		strCommand = "update 1 -s 11/12/2018 1105 -e 11/12/2018 1205";
		update = (Update) parser.parse(strCommand);
		updateTask = update.getTaskToUpdate();
		assertEquals(updateTask.getDescription(),null);
		assertEquals(updateTask.getEndDate().toString(),"11/12/2018");
		assertEquals(updateTask.getEndTime().toString(), "1205");
		assertEquals(updateTask.getStartDate().toString(),"11/12/2018");
		assertEquals(updateTask.getStartTime().toString(),"1105");
		
		/* If user just enters "update description, startdatetime and enddatetime" */
		strCommand = "update 1 -d mydescription -s 11/12/2018 1105 -e 11/12/2018 1205";
		update = (Update) parser.parse(strCommand);
		updateTask = update.getTaskToUpdate();
		assertEquals(updateTask.getDescription(), "mydescription");
		assertEquals(updateTask.getEndDate().toString(),"11/12/2018");
		assertEquals(updateTask.getEndTime().toString(), "1205");
		assertEquals(updateTask.getStartDate().toString(),"11/12/2018");
		assertEquals(updateTask.getStartTime().toString(),"1105");
	}



}
