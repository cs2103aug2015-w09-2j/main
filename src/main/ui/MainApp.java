package main.ui;

import java.io.IOException;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import main.ui.view.MainLayoutController;

public class MainApp extends Application {

	private Stage primaryStage;
	private GridPane mainLayout;
	private ObservableList<String> tasks;
	// private Logic logic;

	public MainApp() {
		tasks = FXCollections.observableArrayList();

	}

	/*
	 * Getters
	 */
	public ObservableList<String> getTasks() {
		return tasks;
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

			// Show the scene containing the main layout
			Scene scene = new Scene(mainLayout, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.show();

			// Set up the controller
			MainLayoutController mainLayoutController = loader.getController();
			mainLayoutController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init() {
		/*
		 * This part should call
		 */
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Vodo");

		// MainApp should check for a config file (need to decide on name) << create a new Configuration class, tell logic to use?
		// If config file exists
		//	Get the specified storage location, setup Logic with storage location
		// Else
		//  Display a new dialog asking for location to store data
		//	setup Logic with the storage location

		// pass observable list of tasks to logic
		showMainLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
}