//@@author A0126518E
package main.ui.view;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import main.Task;
import main.Logger;
import main.ui.MainApp;
import main.ui.util.CommandHelper;
import main.ui.util.StatusHelper;

public class MainLayoutController {

	private static String LOG_STRING = "User typed in: \"%1$s\"";

	private MainApp mainApp;
	private Logger logger;

	private ObservableList<Task> events;
	private ObservableList<Task> deadlines;
	private ObservableList<Task> floatings;

	private IntegerProperty displayState;
	private BooleanProperty hasNewOverdueTask;

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
		logger = Logger.getInstance();
		displayState = new SimpleIntegerProperty();
	}

	/**
	 * This method is automatically called after the FXML is loaded
	 */
	@FXML
	public void initialize() {
		customizeEventCellFactory();
		customizeDeadlineCellFactory();
		customizeFloatingCellFactory();

		// initial help dialog should be MAIN
		helpLabel.setText(CommandHelper.HELP_MAIN);

		// initial status should be ONGOING
		displayState.setValue(StatusHelper.Status.ONGOING.getCode());
		displayStatusLabel.setText(StatusHelper.getStatusText(displayState.getValue()));
	}

	/**
	 * Set the reference to MainApp and do the initial setup
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		getTasks();
		setupListViews();
		initialize();
		mainApp.setDisplayState(displayState);
		setHasNewOverdueTask();
		checkInitialHasNewOverdueTask();
		setListeners();
	}

	public void focusCommandBox() {
		commandBox.requestFocus();
	}

	private void checkInitialHasNewOverdueTask() {
		if (hasNewOverdueTask.getValue().equals(Boolean.TRUE)) {
			overdueLabel.setVisible(true);

			FadeTransition fadeInAndOut = new FadeTransition(Duration.millis(800), overdueLabel);
			fadeInAndOut.setFromValue(0.2);
			fadeInAndOut.setToValue(1);
			fadeInAndOut.setCycleCount(Animation.INDEFINITE);
			fadeInAndOut.setAutoReverse(true);
			fadeInAndOut.play();
		}
	}

	private void customizeEventCellFactory() {
		eventsListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			@Override
			public ListCell<Task> call(ListView<Task> eventsListView) {
				return new EventCell();
			}
		});
	}

	private void customizeDeadlineCellFactory() {
		deadlinesListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			@Override
			public ListCell<Task> call(ListView<Task> deadlinesListView) {
				DeadlineCell.setStartIndex(events.size());
				return new DeadlineCell();
			}
		});
	}

	private void customizeFloatingCellFactory() {
		floatingsListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			@Override
			public ListCell<Task> call(ListView<Task> floatingsListView) {
				FloatingCell.setStartIndex(events.size() + deadlines.size());
				return new FloatingCell();
			}
		});
	}

	private void setupListViews() {
		eventsListView.setItems(events);
		deadlinesListView.setItems(deadlines);
		floatingsListView.setItems(floatings);
	}

	private void setHasNewOverdueTask() {
		this.hasNewOverdueTask = mainApp.getHasNewOverdueTask();
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
				String statusText = StatusHelper.getStatusText(newState);
				if (newState == StatusHelper.Status.SEARCH.getCode() || newState == StatusHelper.Status.NEWSEARCH.getCode()) {
					String keyword = mainApp.getSearchKeyword();
					if (keyword.length() > 12) {
						keyword = keyword.substring(0,12) + "...";
					}
					displayStatusLabel.setText(String.format(statusText, keyword));
				} else {
					displayStatusLabel.setText(statusText);
				}

				FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), displayStatusLabel);
				fadeIn.setFromValue(0);
				fadeIn.setToValue(1);
				fadeIn.play();
			}

		});

		hasNewOverdueTask.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> hasNewOverdueTask, Boolean oldValue, Boolean newValue) {
				System.out.println(newValue);
				if (newValue.equals(Boolean.TRUE)) {
					overdueLabel.setVisible(true);

					FadeTransition fadeInAndOut = new FadeTransition(Duration.millis(1000), overdueLabel);
					fadeInAndOut.setFromValue(0.2);
					fadeInAndOut.setToValue(1);
					fadeInAndOut.setCycleCount(Animation.INDEFINITE);
					fadeInAndOut.setAutoReverse(true);
					fadeInAndOut.play();

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

	@FXML
	private void getCommand() {
		boolean isSuccessful;
		String command = commandBox.getText();
		logger.write(String.format(LOG_STRING, command));

		isSuccessful = mainApp.processCommand(command);
		respondTo(command, isSuccessful);

		commandBox.setText("");
	}

	@FXML
	private void listenToKeyTyped() {
		String textTyped = commandBox.getText();
		String response = CommandHelper.respondTo(textTyped);
		helpLabel.setText(response);
	}

	private void respondTo(String command, boolean isSuccessful) {
		if (isSuccessful) {
			responseBox.setId("response-box-success");
			responseLabel.setText("\"" + command + "\"" + " was successful");
		} else {
			responseBox.setId("response-box-fail");
			responseLabel.setText("\"" + command + "\"" + " was unsuccessful");
		}

		ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), responseBox);
		scaleIn.setFromY(0);
		scaleIn.setToY(1);

		ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), responseBox);
		scaleOut.setFromY(1);
		scaleOut.setToY(0);

		SequentialTransition sequence = new SequentialTransition();
		sequence.getChildren().addAll(scaleIn, new PauseTransition(Duration.millis(2000)), scaleOut);
		sequence.play();
	}

}