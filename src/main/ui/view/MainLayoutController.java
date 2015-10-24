package main.ui.view;

import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
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

	private ObservableList<Task> tasks; // task is retrieved from MainApp; events, deadlines, and floatings are use to separate tasks
	private ObservableList<Event> events;
	private ObservableList<Deadline> deadlines;
	private ObservableList<Floating> floatingTasks;


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
		events = FXCollections.observableArrayList();
		deadlines = FXCollections.observableArrayList();
		floatingTasks = FXCollections.observableArrayList();
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
		separateTasks();
		setupListViews();
	}

	private void getTasks() {
		tasks = mainApp.getTasks();
		System.out.println(tasks.size());
	}

	private void setupListViews() {

		tasks.addListener(new ListChangeListener<Task>() {
			@Override
			public void onChanged(Change<? extends Task> aChange) {
				while (aChange.next()) {
					if (aChange.wasAdded()) {
						System.out.println("from: " + aChange.getFrom());
						System.out.println("to: " + aChange.getTo());
					}
				}
			}
		});

		eventsListView.setItems(events);
		deadlinesListView.setItems(deadlines);
		floatingTasksListView.setItems(floatingTasks);
	}

	private void separateTasks() {
		for (int i = 0; i < tasks.size(); i++) {
			Task aTask = tasks.get(i);
			if (aTask instanceof Event) {
				events.add((Event) aTask);
			}
			else if (aTask instanceof Deadline) {
				deadlines.add((Deadline) aTask);
			}
			else {
				floatingTasks.add((Floating) aTask);
			}
		}
	}

	@FXML
	public void getCommand() throws NoSuchFieldException, ParseException { // exception will be handled by Logic later, remove this later
		String command = commandBox.getText(); // rename to entry

		mainApp.processCommand(command);
		// tasks.add(new Floating(command));
		commandBox.setText("");
	}

	/*
	 * Need to register a changeListener to task, and then get the last task
	 */
}