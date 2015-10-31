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
import main.ui.util.CommandListener;
import main.Task;
import main.Event;
import main.Deadline;
import main.Floating;

public class MainLayoutController {

	private MainApp mainApp;

	private ObservableList<Task> events;
	private ObservableList<Task> deadlines;
	private ObservableList<Task> floatings;


	// Fields for binding to UI components
	@FXML
	private ListView<Task> eventsListView;
	@FXML
	private ListView<Task> deadlinesListView;
	@FXML
	private ListView<Task> floatingsListView;
	@FXML
	private TextField commandBox;

	public MainLayoutController() {
	}

	/**
	 * Set the reference to MainApp and setup the view
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		getTasks();
		setupListViews();
		initializeCustomCellFactory();
		setListeners();
	}

	public void focusCommandBox() {
		commandBox.requestFocus();
	}

	public void showHelpDialog(String helpDialog) {
		System.out.println(helpDialog);
	}

	private void initializeCustomCellFactory() {
		customizeEventCellFactory();
		customizeDeadlineCellFactory();
		customizeFloatingCellFactory();
	}

	private void customizeEventCellFactory() {
		eventsListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			@Override
			public ListCell<Task> call(ListView<Task> eventsListView) {
				return new TaskCell.EventCell();
			}
		});
	}

	private void customizeDeadlineCellFactory() {
		deadlinesListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			@Override
			public ListCell<Task> call(ListView<Task> deadlinesListView) {
				TaskCell.DeadlineCell.startIndex = events.size();
				return new TaskCell.DeadlineCell();
			}
		});
	}

	private void customizeFloatingCellFactory() {
		floatingsListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			@Override
			public ListCell<Task> call(ListView<Task> floatingsListView) {
				TaskCell.FloatingCell.startIndex = events.size() + deadlines.size();
				return new TaskCell.FloatingCell();
			}
		});
	}

	private void setListeners() {
		events.addListener(new ListChangeListener<Task>() {
			@Override
			public void onChanged(Change <? extends Task> aChange) {
				while (aChange.next()) {
					customizeDeadlineCellFactory();
					customizeFloatingCellFactory();
					break;
				}
			}
		});

		deadlines.addListener(new ListChangeListener<Task>() {
			@Override
			public void onChanged(Change <? extends Task> aChange) {
				while (aChange.next()) {
					customizeFloatingCellFactory();
					break;
				}
			}
		});
	}

	private void getTasks() {
		events = mainApp.getEvents();
		deadlines = mainApp.getDeadlines();
		floatings = mainApp.getFloatings();
	}

	private void setupListViews() {
		eventsListView.setItems(events);
		deadlinesListView.setItems(deadlines);
		floatingsListView.setItems(floatings);
	}

	@FXML
	private void getCommand() throws NoSuchFieldException, ParseException { // exception will be handled by Logic later, remove this later
		String command = commandBox.getText();

		mainApp.processCommand(command);
		commandBox.setText("");
	}

	@FXML
	private void listenToKeyTyped() {
		String textTyped = commandBox.getText();
		String response = CommandListener.respondTo(textTyped);
		System.out.println(response);
	}

}