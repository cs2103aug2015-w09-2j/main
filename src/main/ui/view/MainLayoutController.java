package main.ui.view;

import java.text.ParseException;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import main.ui.MainApp;
import main.ui.util.CommandListener;
import main.ui.util.StatusListener;
import main.ui.util.StatusListener.Status;
import main.Task;
import main.Event;
import main.Deadline;
import main.Floating;

public class MainLayoutController {

	private MainApp mainApp;

	private ObservableList<Task> events;
	private ObservableList<Task> deadlines;
	private ObservableList<Task> floatings;
	private IntegerProperty displayState;
	private BooleanProperty hasNewOverdueTask;

	// Fields for binding to UI components
	@FXML
	private ListView<Task> eventsListView;
	@FXML
	private ListView<Task> deadlinesListView;
	@FXML
	private ListView<Task> floatingsListView;
	@FXML
	private TextField commandBox;
	@FXML
	private Label helpLabel;
	@FXML
	private Label displayStatusLabel;
	@FXML
	private Label overdueLabel;
	@FXML
	private Label responseLabel;
	@FXML
	private AnchorPane responseBox;


	public MainLayoutController() {
		displayState = new SimpleIntegerProperty();
		hasNewOverdueTask = new SimpleBooleanProperty();
	}

	/**
	 * This method is automatically called after the FXML is loaded
	 */
	@FXML
	public void initialize() {
		customizeEventCellFactory();
		customizeDeadlineCellFactory();
		customizeFloatingCellFactory();

		// initial help dialog is MAIN
		helpLabel.setText(CommandListener.HELP_MAIN);

		// initial status is ONGOING
		displayState.setValue(StatusListener.Status.ONGOING.getCode());
		displayStatusLabel.setText(StatusListener.getStatusText(displayState.getValue()));

		hasNewOverdueTask.setValue(Boolean.FALSE);
		overdueLabel.setVisible(false);
	}

	/**
	 * Set the reference to MainApp and setup the view
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		getTasks();
		setupListViews();
		initialize();
		setListeners();
		mainApp.setDisplayState(displayState);
		this.hasNewOverdueTask = mainApp.getHasNewOverdueTask();
	}

	public void focusCommandBox() {
		commandBox.requestFocus();
	}

	public void showhelpLabel(String helpLabel) {
		System.out.println(helpLabel);
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
				}
			}
		});

		deadlines.addListener(new ListChangeListener<Task>() {
			@Override
			public void onChanged(Change <? extends Task> aChange) {
				while (aChange.next()) {
					customizeFloatingCellFactory();
				}
			}
		});

		displayState.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> displayState, Number oldValue, Number newValue) {
				int newState = newValue.intValue();
				String statusText = StatusListener.getStatusText(newState);
				if (newState == StatusListener.Status.SEARCH.getCode() || newState == StatusListener.Status.NEWSEARCH.getCode()) {
					displayStatusLabel.setText(String.format(statusText, mainApp.getSearchKeyword()));
				} else {
					displayStatusLabel.setText(statusText);
				}
			}

		});

		hasNewOverdueTask.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> hasNewOverdueTask, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.TRUE)) {
					overdueLabel.setVisible(true);
				} else {
					overdueLabel.setVisible(false);
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

	private void respondTo(String command, boolean isSuccessful) {
		if (isSuccessful) {
			responseBox.setId("response-box-success");
			responseLabel.setText("\"" + command + "\"" + " was successful");
		} else {
			responseBox.setId("response-box-fail");
			responseLabel.setText("\"" + command + "\"" + " was unsuccessful");
		}
		ScaleTransition st1 = new ScaleTransition(Duration.millis(200), responseBox);
		st1.setFromY(0);
		st1.setToY(1);

		ScaleTransition st2 = new ScaleTransition(Duration.millis(200), responseBox);
		st2.setFromY(1);
		st2.setToY(0);

		SequentialTransition st3 = new SequentialTransition();
		st3.getChildren().addAll(st1, new PauseTransition(Duration.millis(2000)), st2);
		st3.play();
	}

	@FXML
	private void getCommand() throws NoSuchFieldException, ParseException { // exception will be handled by Logic later, remove this later
		boolean isSuccessful;
		String command = commandBox.getText();

		isSuccessful = mainApp.processCommand(command);
		respondTo(command, isSuccessful);

		commandBox.setText("");
	}

	@FXML
	private void listenToKeyTyped() {
		String textTyped = commandBox.getText();
		String response = CommandListener.respondTo(textTyped);
		helpLabel.setText(response);
	}

}