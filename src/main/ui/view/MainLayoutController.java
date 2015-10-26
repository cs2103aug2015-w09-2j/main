package main.ui.view;

import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import main.ui.MainApp;

import main.Task;
import main.Event;
import main.Deadline;
import main.Floating;

public class MainLayoutController {

	private MainApp mainApp;

	// private ObservableList<Task> tasks; // task is retrieved from MainApp; events, deadlines, and floatings are use to separate tasks
	private ObservableList<Task> events;    // previously the ObservableList was type-aware (Event, Deadline, Floating)
	private ObservableList<Task> deadlines;
	private ObservableList<Task> floatings;


	// Fields for binding to UI components
	@FXML
	private ListView<Task> eventsListView; // previously the ListView was type-aware (Event, Deadline, Floating)
	@FXML
	private ListView<Task> deadlinesListView;
	@FXML
	private ListView<Task> floatingsListView;
	@FXML
	private TextField commandBox;

	public MainLayoutController() {
/*		events = FXCollections.observableArrayList();
		deadlines = FXCollections.observableArrayList();
		floatingTasks = FXCollections.observableArrayList();*/
	}

	public void initialize() {
		/*
		 * ListView cell factory can be used to customize the content
		 */
		eventsListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {

			@Override
			public ListCell<Task> call(ListView<Task> eventsListView) {
				return new TaskCell.EventCell();
			}

		});

		deadlinesListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {

			@Override
			public ListCell<Task> call(ListView<Task> deadlinesListView) {
				TaskCell.DeadlineCell.startIndex = events.size();
				return new TaskCell.DeadlineCell();
			}

		});

		floatingsListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {

			@Override
			public ListCell<Task> call(ListView<Task> floatingsListView) {
				TaskCell.FloatingCell.startIndex = events.size() + deadlines.size();
				return new TaskCell.FloatingCell();
			}

		});
	}

	/**
	 * Set the reference to MainApp and setup the view
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		getTasks();
		// separateTasks();
		setupListViews();
	}

	public void focusCommandBox() {
		commandBox.requestFocus();
	}

	private void getTasks() {
//		tasks = mainApp.getTasks();
		events = mainApp.getEvents();
		deadlines = mainApp.getDeadlines();
		floatings = mainApp.getFloatings();

		// System.out.println(tasks.size());
	}

	private void setupListViews() {

/*		tasks.addListener(new ListChangeListener<Task>() {
			@Override
			public void onChanged(Change<? extends Task> aChange) {
				while (aChange.next()) {
					if (aChange.wasAdded()) {
						System.out.println("from: " + aChange.getFrom());
						System.out.println("to: " + aChange.getTo());
					}
				}
			}
		});*/

		eventsListView.setItems(events);
		deadlinesListView.setItems(deadlines);
		floatingsListView.setItems(floatings);
	}

/*	private void separateTasks() {
		for (int i = 0; i < tasks.size(); i++) {
			Task aTask = tasks.get(i);
			if (aTask instanceof Event) {
				events.add((Event) aTask);
			}
			else if (aTask instanceof Deadline) {
				deadlines.add((Deadline) aTask);
			}
			else {
				floatings.add((Floating) aTask);
			}
		}
	}*/

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