package main.ui.view;

import java.text.ParseException;

import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import main.ui.MainApp;

import main.Task;
import main.Event;
import main.Deadline;
import main.Floating;

public class MainLayoutController {

	private MainApp mainApp;

	private ObservableList<Task> tasks;


	// Fields for binding to UI components
	@FXML
	private ListView<Event> eventsListView;
	@FXML
	private ListView<Deadline> deadlinesListView;
	@FXML
	private ListView<Floating> floatingTasksListView;
	@FXML
	private TextField commandBox;

	public MainLayoutController() {
	}

	public void initialize() {
		/*
		 * ListView cell factory can be used to customize the content
		 */
	}

	/**
	 * Set the reference to MainApp and setup the view
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		getTasks();
		setupListView();

		// Add Observable List data to eventsListView
		// tasks = mainApp.getTasks(); >> after ObservableList<String> change to ObservableList<Task>
		// categorize(tasks);
		// sortByDate(
		// eventsListView.setItems(mainApp.getTasks());
	}

	public void getTasks() {
		tasks = mainApp.getTasks();
	}

	public void setupListView() {
		for (int i = 0; i < tasks.size(); i++) {
			Task aTask = tasks.get(i);
			if (aTask instanceof Event) {
				eventsListView.getItems().add((Event) aTask);
			}
			else if (aTask instanceof Deadline) {
				deadlinesListView.getItems().add((Deadline) aTask);
			}
			else {
				floatingTasksListView.getItems().add((Floating) aTask);
			}
		}
	}

	@FXML
	public void getCommand() throws NoSuchFieldException, ParseException { // exception will be handled by Logic later, remove this later
		String command = commandBox.getText(); // rename to entry
		mainApp.processCommand(command);
		commandBox.setText("");
	}

	/*
	 * Need to register a changeListener to task, and then get the last task
	 */
}