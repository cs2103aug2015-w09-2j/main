package main.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.ui.MainApp;

public class MainLayoutController {

	/**
	 * Fields to point to UI components
	 */
	@FXML
	private ListView<String> eventsListView;
	@FXML
	private ListView<String> deadlinesListView;
	@FXML
	private ListView<String> floatingTasksListView;
	@FXML
	private TextField commandBox;

	// Reference to Main App
	private MainApp mainApp;

	public MainLayoutController() {
	}

	public void initialize() {
		/*
		 * ListView cell factory can be used to customize the content
		 */
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add Observable List data to eventsListView
		eventsListView.setItems(mainApp.getTasks());
	}

	@FXML
	public void getCommand() {
		String entry = commandBox.getText(); // rename to entry
		// mainApp.processCommand(command); // pass command to logic
		eventsListView.getItems().add(entry);
		commandBox.setText("");
	}
}