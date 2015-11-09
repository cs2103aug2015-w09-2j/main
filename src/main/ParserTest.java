package main;

import static org.junit.Assert.*;
import main.Command;
import main.Exit;
import main.Floating;
import main.Redo;
import main.Undo;
import main.Update;
import main.UpdateTask;

import org.junit.Test;

public class ParserTest {

	/*
	 * 	ADD_DEADLINE("deadline"), ADD_FLOATING("floating"),ADD_EVENT("event"),
		 DELETE("delete"), CLEAR("clear"),
		DISPLAY("display"), SEARCH("search"), DONE("done"),
		SAVE("save"),
		UNKNOWN("");
	 */

	private Parser parser;

	public ParserTest(){
		parser = Parser.getInstance();
	}

	@Test
	public void testParsingUpdate(){
		String strCommand;
		Update update;
		UpdateTask updateTask;
		Command command;

		/* If user just enters "update", should be unknown */
		strCommand = "update";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

		/* If user just enters "update " with a space in front, should be null */
		strCommand = "update ";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

		/* If user just enters "update -x" with a wrong delimeter*/
		strCommand = "update -x";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

		/* If user just enters "update -d" with a correct delimiter but no option*/
		strCommand = "update -d";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);
		strCommand = "update -s";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);
		strCommand = "update -e";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

		/* If user just enters taskid*/
		strCommand = "update 1";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

		/* If user just enters only description */
		strCommand = "update 1 -d mydescription 123";
		update = (Update) parser.parse(strCommand);
		updateTask = update.getTaskToUpdate();
		assertEquals(updateTask.getDescription(),"mydescription 123");
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

		/* If user just enters startdate nlp and 24hr time */
		strCommand = "update 1 -s tomorrow 1105";
		update = (Update) parser.parse(strCommand);
		updateTask = update.getTaskToUpdate();
		assertEquals(updateTask.getDescription(), null);
		assertEquals(updateTask.getEndDate(), null);
		assertEquals(updateTask.getEndTime(), null);
		assertEquals(updateTask.getStartDate().toString(),new PrettyTimeWrapper("tomorrow").getDate().toString());
		assertEquals(updateTask.getStartTime().toString(),"1105");

	}

	@Test
	public void testParsingUndo(){
		String strCommand;
		Undo undo;
		Command command;

		/* If user just enters "undo" */
		strCommand = "undo";
		undo = (Undo) parser.parse(strCommand);
		assertEquals(undo.getCommandType(), Command.CommandType.UNDO);

		/* If user just enters a spaceinfront */
		strCommand = " undo";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

		/* If user just enters characters to the back, its still ok!*/
		strCommand = "undo asd";
		undo = (Undo) parser.parse(strCommand);
		assertEquals(undo.getCommandType(), Command.CommandType.UNDO);
	}

	@Test
	public void testParsingRedo(){
		String strCommand;
		Redo redo;
		Command command;

		/* If user just enters "redo" */
		strCommand = "redo";
		redo = (Redo) parser.parse(strCommand);
		assertEquals(redo.getCommandType(), Command.CommandType.REDO);

		/* If user just enters a spaceinfront */
		strCommand = " redo";
		command =  parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

		/* If user just enters characters to the back, its still ok!*/
		strCommand = "redo asd";
		redo = (Redo) parser.parse(strCommand);
		assertEquals(redo.getCommandType(), Command.CommandType.REDO);
	}

	@Test
	public void testParsingExit(){
		String strCommand;
		Exit exit;
		Command command;

		/* If user just enters "redo" */
		strCommand = "exit";
		exit = (Exit) parser.parse(strCommand);
		assertEquals(exit.getCommandType(), Command.CommandType.EXIT);

		/* If user just enters a spaceinfront */
		strCommand = " exit";
		command = parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

		/* If user just enters characters to the back, its still ok!*/
		strCommand = "exit asd";
		exit = (Exit) parser.parse(strCommand);
		assertEquals(exit.getCommandType(), Command.CommandType.EXIT);
	}

	@Test
	public void testParsingFloating(){
		String strCommand;
		Command command;

		/* If user just enters "add" */
		strCommand = "add";
		command = parser.parse(strCommand);
		assertEquals(command.getCommandType(), Command.CommandType.UNKNOWN);

	}

}
