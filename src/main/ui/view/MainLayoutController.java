package main.ui.view;

import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.ui.MainApp;

import main.Task;
import main.Event;
import main.Deadline;
import main.Floating;

public class MainLayoutController {

	private MainApp mainApp;

	private ObservableList<Task> tasks; // task is retrieved from MainApp; events, deadlines, and floatings are use to separate tasks


	// Fields for binding to UI components
	@FXML
	private TableView<Task> tasksTable;
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
		setupTableView();
	}

	private void getTasks() {
		tasks = mainApp.getTasks();
		System.out.println(tasks.size());
	}

	private void setupTableView() {

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

		tasksTable.setItems(tasks);
	}

	@FXML
	public void getCommand() throws NoSuchFieldException, ParseException { // exception will be handled by Logic later, remove this later
		String command = commandBox.getText(); // rename to entry

		// mainApp.processCommand(command);
		tasks.add(new Floating(command));
		commandBox.setText("");
	}

	public void hideHeader() {
        Pane header = (Pane) tasksTable.lookup("TableHeaderRow");
        if (header.isVisible()){
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }
	}

	/*
	 * Need to register a changeListener to task, and then get the last task
	 */
}