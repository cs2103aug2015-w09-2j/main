package main.ui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import main.ui.MainApp;

public class MainLayoutController {

/*	enum TaskType {
		EVENT, DEADLINE, FLOATING
	}*/

	private MainApp mainApp;

/*	private ObservableList<Task> tasks;
	private ObservableList<Event> events;
	private ObservableList<Deadline> deadlines;
	private ObservableList<Floating> floatingTasks;

	// Indices of events, deadlines, and floatingTasks in tasks
	private int firstEventIndex
	private int lastEventIndex
	private int firstDeadlineIndex
	private int lastDeadlineIndex
	private int firstFloatingIndex
	private int lastFloatingIndex*/

	// Fields for binding to UI components
	@FXML
	private ListView<String> eventsListView;
	@FXML
	private ListView<String> deadlinesListView;
	@FXML
	private ListView<String> floatingTasksListView;
	@FXML
	private TextField commandBox;

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
		// tasks = mainApp.getTasks(); >> after ObservableList<String> change to ObservableList<Task>
		// categorize(tasks);
		// sortByDate(
		eventsListView.setItems(mainApp.getTasks());
	}

	@FXML
	public void getCommand() {
		String entry = commandBox.getText(); // rename to entry
		// mainApp.processCommand(command); // pass command to logic
		eventsListView.getItems().add(entry);
		commandBox.setText("");
	}

	/*
	 * Need to register a changeListener to task, and then get the last task
	 */
}