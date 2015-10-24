package main.ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList; // remove later

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import main.ui.view.MainLayoutController;
import main.*; // remove later
import main.Logic;
import main.Task;

public class MainApp extends Application {

	private Stage primaryStage;
	private GridPane mainLayout;
	private ObservableList<Task> tasks; // previously was <String>
	private ObservableList<Task> dummyTasks; // for testing because the app doesn't work
	private Logic logic;

	public MainApp() throws NoSuchFieldException, ParseException { // remove throws later
		tasks = FXCollections.observableArrayList();
		ArrayList<Task> dummyArr = new ArrayList<Task>();
		dummyArr.add(new Floating("Laundry"));
		dummyArr.add(new Deadline("Do homework", new DateClass(20,10), new TimeClass("23","59")));
		dummyArr.add(new Event("Meet someone", new DateClass(21,10), new TimeClass("16","00"), new DateClass(21,10), new TimeClass("18","00")));
		dummyTasks = FXCollections.observableList(dummyArr);
	}

	public ObservableList<Task> getTasks() {
		return tasks;
		// return dummyTasks;
	}

	public void processCommand(String command) throws NoSuchFieldException, ParseException { // exception will be handled by Logic later, remove this later
		boolean isSuccessful = logic.processCommand(command);
		// response by giving a message to controller to display
	}

	/**
	 * Initializes the scene and display the stage
	 */
	public void showMainLayout() {
		try {
			// Load the scene graph from MainLayout.fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainLayout.fxml"));
			mainLayout = (GridPane) loader.load();

			// Set up the controller
			MainLayoutController mainLayoutController = loader.getController();
			mainLayoutController.setMainApp(this); // pass tasks to controller

			// Show the scene containing the main layout
			Scene scene = new Scene(mainLayout, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.show();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init() {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Vodo");
		//[Ravi] made the use of singleton logic
		logic = Logic.getInstance();
		logic.setTasks(tasks); // >> at this point logic should fill the task

		showMainLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
}