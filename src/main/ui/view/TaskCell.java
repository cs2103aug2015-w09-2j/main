/*
 * Comment on this class:
 * Consider extracting the individual cell class from TaskCell
 */
package main.ui.view;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.Task;
import main.Event;
import main.DateClass;
import main.Deadline;
import main.Floating;

public class TaskCell {

	protected static class EventCell extends ListCell<Task> {

		@Override
		protected void updateItem(Task item, boolean empty) {
			super.updateItem(item, empty);

			getStyleClass().add("task-cell");
			if (empty || item == null) {
				setText(null);
				setGraphic(null);
				// getStyleClass().add("task-cell-empty");
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
				// indexLabel.setTextFill(Color.WHITE);

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
				// timeLabel.setPadding(new Insets(0, 2, 0, 2));
				// timeLabel.setBackground(new Background(new BackgroundFill(Color.web("#69f0ae"), new CornerRadii(4), Insets.EMPTY)));

				descriptionLabel.setText(item.getDescription());
				descriptionLabel.setWrapText(true);
				descriptionLabel.getStyleClass().add("description-label");
				// descriptionLabel.setTextFill(Color.WHITE);

				aVBox.getChildren().addAll(timeLabel, descriptionLabel);
				anHBox.getChildren().addAll(indexLabel, aVBox);

				// setText(this.getIndex()+1 + ") " + day + " " + time + " " + item.getDescription());
				setPrefWidth(200);
				setMaxWidth(200);
				setMinWidth(200);
				setGraphic(anHBox);
				// getStyleClass().add("task-cell-filled");
				// setBackground(new Background(new BackgroundFill(Color.web("#303f9f"), new CornerRadii(4), new Insets(1))));
			}
		}
	}

	protected static class DeadlineCell extends ListCell<Task> {
		public static int startIndex;

		@Override
		protected void updateItem(Task item, boolean empty) {
			super.updateItem(item, empty);

			getStyleClass().add("task-cell");
			if (empty || item == null) {
				setText(null);
				setGraphic(null);
				// getStyleClass().add("task-cell-empty");
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

				// setText(startIndex + this.getIndex() + 1 + ") " + day + " " + time + " " + item.getDescription());
				setPrefWidth(200);
				setMaxWidth(200);
				setMinWidth(200);
				setGraphic(anHBox);
				// getStyleClass().add("task-cell-filled");
			}
		}

	}

	protected static class FloatingCell extends ListCell<Task> {
		public static int startIndex;

		@Override
		protected void updateItem(Task item, boolean empty) {
			super.updateItem(item, empty);

			getStyleClass().add("task-cell");
			if (empty || item == null) {
				setText(null);
				setGraphic(null);
				// getStyleClass().add("task-cell-empty");
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

				// setText(startIndex + this.getIndex() + 1 + ") " + item.toString());
				setPrefWidth(200);
				setMaxWidth(200);
				setMinWidth(200);
				setGraphic(anHBox);
				// getStyleClass().add("task-cell-filled");
			}
		}

	}
}
