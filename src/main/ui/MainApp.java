//@@author A0126518E
package main.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Task;
import main.ui.view.MainLayoutController;
import main.Logic;

public class MainApp extends Application {

	private Logic logic;

	private Stage primaryStage;
	private GridPane mainLayout;

	public MainApp() {
	}

	public ObservableList<Task> getEvents() {
		return logic.getEvents();
	}

	public ObservableList<Task> getDeadlines() {
		return logic.getDeadlines();
	}

	public ObservableList<Task> getFloatings() {
		return logic.getFloatings();
	}

	public BooleanProperty getHasNewOverdueTask() {
		return logic.getHasNewOverdueTask();
	}

	public String getSearchKeyword() {
		return logic.getSearchKeyword();
	}

	public void setDisplayState(IntegerProperty statusCode) {
		logic.setDisplayState(statusCode);
	}

	public boolean processCommand(String command) {
		return logic.processCommand(command);
	}

	public void exit() {
		Platform.exit();
	}

	/**
	 * Initializes the scene and display the stage
	 */
	private void showMainLayout() {
		 try {
			FXMLLoader loader = new FXMLLoader();
			System.out.println(MainApp.class.getResource("").getPath());
			loader.setLocation(MainApp.class.getResource("view/MainLayout.fxml"));
			mainLayout = (GridPane) loader.load();

			MainLayoutController mainLayoutController = loader.getController();
			mainLayoutController.setMainApp(this);

			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

			mainLayoutController.focusCommandBox();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	//@@author generated
	@Override
	public void init() {
	}

	//@@author A0126518E
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Vodo");

		logic = Logic.getInstance();
		logic.setMainApp(this);

		showMainLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
}