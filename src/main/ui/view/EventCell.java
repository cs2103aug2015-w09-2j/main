//@@author A0126518E
package main.ui.view;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Commands.Event;
import main.Commands.Task;
import main.common.DateClass;

public class EventCell extends ListCell<Task> {

	@Override
	protected void updateItem(Task item, boolean empty) {
		super.updateItem(item, empty);

		getStyleClass().add("task-cell");
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			DateClass startDate = ((Event) item).getStartDate();
			DateClass endDate = ((Event) item).getEndDate();

			String startDay, startMonth = null;
			String endDay, endMonth = null;

			String startTime = ((Event) item).getStartTime().to12HourFormat();
			String endTime = ((Event) item).getEndTime().to12HourFormat();

			HBox anHBox = new HBox(2);
			VBox aVBox = new VBox(2);
			Label indexLabel = new Label();
			Label timeLabel = new Label();
			Label descriptionLabel = new Label();

			indexLabel.setText(this.getIndex()+1 + "");
			indexLabel.setPrefWidth(USE_COMPUTED_SIZE);
			indexLabel.setMinWidth(USE_PREF_SIZE);
			indexLabel.getStyleClass().add("index-label");

			if (startDate.isToday()) {
				startDay = "Today";
			} else if (startDate.isTomorrow()) {
				startDay = "Tomorrow";
			} else if (startDate.isWithinAWeek()) {
				startDay = startDate.getStrDay();
			} else {
				startDay = String.valueOf(startDate.getIntDay());
				startMonth = startDate.getStrMonth();
				startMonth = startDate.getStrMonth().substring(0,3);
				startMonth = startMonth.substring(0,1) + startMonth.substring(1,3).toLowerCase();
			}

			if (endDate.isToday()) {
				endDay = "Today";
			} else if (endDate.isTomorrow()) {
				endDay = "Tomorrow";
			} else if (endDate.isWithinAWeek()) {
				endDay = endDate.getStrDay();
			} else {
				endDay = String.valueOf(endDate.getIntDay());
				endMonth = endDate.getStrMonth().substring(0,3);
				endMonth = endMonth.substring(0,1) + endMonth.substring(1,3).toLowerCase();
			}

			if (startDay.equals(endDay) && (startMonth == null)) {
				timeLabel.setText(startDay + " " + startTime + "-" + endTime);
			} else if (!startDay.equals(endDay) && startMonth == null) {
				if (endMonth == null) {
					timeLabel.setText(startDay + " " + startTime + " - " + endDay + " " + endTime);
				} else {
					timeLabel.setText(startDay + " " + startTime + " - " + endDay + " " + endMonth + " " + endTime);
				}
			} else {
				timeLabel.setText(startDay + " " + startMonth + " " + startTime + " - " + endDay + " " + endMonth + " " + endTime);
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