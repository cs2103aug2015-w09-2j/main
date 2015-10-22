package main.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.UserInterface;

public class TestUserInterface {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}

	/* This is the boundary case when processCommand from logic returns true */
	@Test
	public void testProcessCommandTrue() {
		UserInterfaceStub UI = new UserInterfaceStub();
		UI.processCommand("true");
		assertEquals(outContent.toString(), UI.MESSAGE_VALID_COMMAND);

		outContent.reset();
	}

	/* This is the boundary case when processCommand from logic returns false */
	@Test
	public void testProcessCommandFalse() {
		UserInterfaceStub UI = new UserInterfaceStub();
		UI.processCommand("false");
		assertEquals(outContent.toString(), UI.MESSAGE_INVALID_COMMAND);

		outContent.reset();
	}
}
