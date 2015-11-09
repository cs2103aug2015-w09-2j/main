//@@author A0126518E
package main.ui.view;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import main.Commands.Floating;
import main.Commands.Task;

public class FloatingCell extends ListCell<Task> {
	private static int startIndex;

	public static void setStartIndex(int index) {
		startIndex = index;
	}

	@Override
	protected void updateItem(Task item, boolean empty) {
		super.updateItem(item, empty);

		getStyleClass().add("task-cell");
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			item = (Floating) item;

			HBox anHBox = new HBox(2);
			Label indexLabel = new Label();
			Label descriptionLabel = new Label();

			indexLabel.setText(startIndex+this.getIndex()+1 + "");
			indexLabel.setPrefWidth(USE_COMPUTED_SIZE);
			indexLabel.setMinWidth(Control.USE_PREF_SIZE);
			indexLabel.getStyleClass().add("index-label");

			descriptionLabel.setText(item.getDescription());
			descriptionLabel.setWrapText(true);
			descriptionLabel.getStyleClass().add("description-label");

			anHBox.getChildren().addAll(indexLabel, descriptionLabel);

			setPrefWidth(200);
			setMaxWidth(200);
			setMinWidth(200);
			setGraphic(anHBox);
		}
	}

}