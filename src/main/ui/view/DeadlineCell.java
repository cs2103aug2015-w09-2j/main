//@@author A0126518E
package main.ui.view;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Commands.Deadline;
import main.Commands.Task;
import main.common.DateClass;

public class DeadlineCell extends ListCell<Task> {
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
			DateClass date = ((Deadline) item).getEndDate();
			String day, month = null;
			String time = ((Deadline) item).getEndTime().to12HourFormat();

			HBox anHBox = new HBox(2);
			VBox aVBox = new VBox(2);
			Label indexLabel = new Label();
			Label timeLabel = new Label();
			Label descriptionLabel = new Label();

			indexLabel.setText(startIndex+this.getIndex()+1 + "");
			indexLabel.setPrefWidth(USE_COMPUTED_SIZE);
			indexLabel.setMinWidth(USE_PREF_SIZE);
			indexLabel.getStyleClass().add("index-label");

			if (date.isToday()) {
				day = "Today";
			} else if (date.isTomorrow()) {
				day = "Tomorrow";
			} else if (date.isWithinAWeek()) {
				day = date.getStrDay();
			} else {
				day = String.valueOf(date.getIntDay());
				month = date.getStrMonth();
				month = date.getStrMonth().substring(0,3);
				month = month.substring(0,1) + month.substring(1,3).toLowerCase();
			}

			if (month == null) {
				timeLabel.setText(day + " " + time);
			} else {
				timeLabel.setText(day + " " + month + " " + time);
			}

			timeLabel.setWrapText(true);
			timeLabel.getStyleClass().add("time-label");

			descriptionLabel.setText(item.getDescription());
			descriptionLabel.setWrapText(true);
			descriptionLabel.getStyleClass().add("description-label");

			aVBox.getChildren().addAll(timeLabel, descriptionLabel);
			anHBox.getChildren().addAll(indexLabel, aVBox);

			setPrefWidth(200);
			setMaxWidth(200);
			setMinWidth(200);
			setGraphic(anHBox);
		}
	}

}