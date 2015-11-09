//@@author A0126518E
package main.test.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import main.ui.util.CommandHelper;

public class TestCommandHelper {

	@Test
	public void testCommandAdd() {
		String response;

		response = CommandHelper.respondTo("");
		assertNotSame(response, CommandHelper.HELP_ADD);
		response = CommandHelper.respondTo("del");
		assertNotSame(response, CommandHelper.HELP_ADD);
		response = CommandHelper.respondTo("a");
		assertEquals(response, CommandHelper.HELP_ADD);
		response = CommandHelper.respondTo("Ad");
		assertEquals(response, CommandHelper.HELP_ADD);
		response = CommandHelper.respondTo("   add ");
		assertEquals(response, CommandHelper.HELP_ADD);
		response = CommandHelper.respondTo("add something");
		assertEquals(response, CommandHelper.HELP_ADD);
	}

	@Test
	public void testCommandDelete() {
		String response;

		response = CommandHelper.respondTo("");
		assertNotSame(response, CommandHelper.HELP_DELETE);
		response = CommandHelper.respondTo("upd");
		assertNotSame(response, CommandHelper.HELP_DELETE);
		response = CommandHelper.respondTo("de");
		assertEquals(response, CommandHelper.HELP_DELETE);
		response = CommandHelper.respondTo("Del");
		assertEquals(response, CommandHelper.HELP_DELETE);
		response = CommandHelper.respondTo("   delete ");
		assertEquals(response, CommandHelper.HELP_DELETE);
		response = CommandHelper.respondTo("delete something");
		assertEquals(response, CommandHelper.HELP_DELETE);
	}

	@Test
	public void testCommandUpdate() {
		String response;

		response = CommandHelper.respondTo("");
		assertNotSame(response, CommandHelper.HELP_UPDATE);
		response = CommandHelper.respondTo("dis");
		assertNotSame(response, CommandHelper.HELP_UPDATE);
		response = CommandHelper.respondTo("up");
		assertEquals(response, CommandHelper.HELP_UPDATE);
		response = CommandHelper.respondTo("UPDAT");
		assertEquals(response, CommandHelper.HELP_UPDATE);
		response = CommandHelper.respondTo("   Update ");
		assertEquals(response, CommandHelper.HELP_UPDATE);
		response = CommandHelper.respondTo("UPDATE something");
		assertEquals(response, CommandHelper.HELP_UPDATE);
	}

	@Test
	public void testMainHelp() {
		String response;

		response = CommandHelper.respondTo("");
		assertEquals(response, CommandHelper.HELP_MAIN);
		response = CommandHelper.respondTo("    ");
		assertEquals(response, CommandHelper.HELP_MAIN);
		response = CommandHelper.respondTo("update");
		assertNotSame(response, CommandHelper.HELP_MAIN);
		response = CommandHelper.respondTo("   SEARCH ");
		assertNotSame(response, CommandHelper.HELP_MAIN);
		response = CommandHelper.respondTo(" RandomString");
		assertEquals(response, CommandHelper.HELP_MAIN);
	}

}
