//@@author A0126518E
package main.test.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import main.ui.util.StatusHelper;

public class TestStatusHelper {

	@Test
	public void test() {
		String statusText;
		statusText = StatusHelper.getStatusText(StatusHelper.Status.ONGOING.getCode());
		assertEquals(statusText, StatusHelper.ONGOING);
		statusText = StatusHelper.getStatusText(StatusHelper.Status.OVERDUE.getCode());
		assertEquals(statusText, StatusHelper.OVERDUE);
		statusText = StatusHelper.getStatusText(StatusHelper.Status.DONE.getCode());
		assertEquals(statusText, StatusHelper.DONE);
		statusText = String.format(StatusHelper.getStatusText(StatusHelper.Status.SEARCH.getCode()), "randomSearch");
		assertEquals(statusText, String.format(StatusHelper.SEARCH, "randomSearch"));
	}

}
