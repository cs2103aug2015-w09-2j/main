package main.ui;

import java.io.IOException;
import java.text.ParseException;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import main.ui.view.MainLayoutController;

import main.Logic;
import main.Task;

public class MainApp extends Application {

	private Stage primaryStage;
	private GridPane mainLayout;
	private ObservableList<Task> tasks; // previously was <String>
	private Logic logic;

	public MainApp() {
		tasks = FXCollections.observableArrayList();
	}

	public ObservableList<Task> getTasks() {
		return tasks;
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

		logic = new Logic();
		logic.setTasks(tasks); // >> at this point logic should fill the task

		showMainLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
}