//@@author A0126518E
/*package main.test.ui;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import main.ui.util.CommandHelper;
import main.ui.util.StatusHelper;

public class TestMainLayoutController extends GuiTest {

	@Override
	protected Parent getRootNode() {
		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("../../ui/view/MainLayout.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parent;
	}

	@Test
	public void testInitialState() {
		Label displayStatusLabel = find("#status-label");
		Label helpLabel = find("#help-label");
		assertEquals(displayStatusLabel.getText(), StatusHelper.ONGOING);
		assertEquals(helpLabel.getText(), CommandHelper.HELP_MAIN);
	}

}
*/