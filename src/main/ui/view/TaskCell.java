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
import main.Deadline;
import main.Floating;

public class TaskCell {

	protected static class EventCell extends ListCell<Task> {

		@Override
		protected void updateItem(Task item, boolean empty) {
			super.updateItem(item, empty);

			if (empty || item == null) {
				setText(null);
				setGraphic(null);
			} else {
				String startDay = ((Event) item).getStartDate().getStrDay();
				String startMonth = ((Event) item).getStartDate().getStrMonth();
				String startTime = ((Event) item).getStartTime().to12HourFormat();

				String endDay = ((Event) item).getEndDate().getStrDay();
				String endMonth = ((Event) item).getEndDate().getStrMonth();
				String endTime = ((Event) item).getEndTime().to12HourFormat();

				HBox anHBox = new HBox(2);
				VBox aVBox = new VBox(2);
				Label indexLabel = new Label();
				Label timeLabel = new Label();
				Label descriptionLabel = new Label();

				indexLabel.setText(this.getIndex()+1 + "");
				indexLabel.setPrefWidth(USE_COMPUTED_SIZE);
				indexLabel.setTextFill(Color.WHITE);

				if (startDay.equals(endDay)) {
					timeLabel.setText(startDay + " " + startTime + "-" + endTime);
				} else
					timeLabel.setText(startDay + " " + startTime + " - " + endDay + " " + endTime);
				timeLabel.setWrapText(true);
				timeLabel.setPadding(new Insets(0, 2, 0, 2));
				timeLabel.setBackground(new Background(new BackgroundFill(Color.web("#69f0ae"), new CornerRadii(4), Insets.EMPTY)));

				descriptionLabel.setText(item.getDescription());
				descriptionLabel.setWrapText(true);
				descriptionLabel.setTextFill(Color.WHITE);

				aVBox.getChildren().addAll(timeLabel, descriptionLabel);
				anHBox.getChildren().addAll(indexLabel, aVBox);

				// setText(this.getIndex()+1 + ") " + day + " " + time + " " + item.getDescription());
				setPrefWidth(200);
				setMaxWidth(200);
				setMinWidth(200);
				setGraphic(anHBox);
				setBackground(new Background(new BackgroundFill(Color.web("#303f9f"), new CornerRadii(4), new Insets(1))));
			}
		}
	}

	protected static class DeadlineCell extends ListCell<Task> {
		public static int startIndex;

		@Override
		protected void updateItem(Task item, boolean empty) {
			super.updateItem(item, empty);

			if (empty || item == null) {
				setText(null);
				setGraphic(null);
			} else {
				String day = ((Deadline) item).getEndDate().getStrDay();
				String month = ((Deadline) item).getEndDate().getStrMonth();
				String time = ((Deadline) item).getEndTime().to12HourFormat();

				HBox anHBox = new HBox(2);
				VBox aVBox = new VBox(2);
				Label indexLabel = new Label();
				Label timeLabel = new Label();
				Label descriptionLabel = new Label();

				indexLabel.setText(startIndex+this.getIndex()+1 + "");
				indexLabel.setPrefWidth(USE_COMPUTED_SIZE);
				timeLabel.setText(day + " " + time);
				timeLabel.setWrapText(true);
				descriptionLabel.setText(item.getDescription());
				descriptionLabel.setWrapText(true);

				aVBox.getChildren().addAll(timeLabel, descriptionLabel);
				anHBox.getChildren().addAll(indexLabel, aVBox);

				// setText(startIndex + this.getIndex() + 1 + ") " + day + " " + time + " " + item.getDescription());
				setPrefWidth(200);
				setMaxWidth(200);
				setMinWidth(200);
				setGraphic(anHBox);
			}
		}

	}

	protected static class FloatingCell extends ListCell<Task> {
		public static int startIndex;

		@Override
		protected void updateItem(Task item, boolean empty) {
			super.updateItem(item, empty);

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
				descriptionLabel.setText(item.getDescription());
				descriptionLabel.setWrapText(true);

				anHBox.getChildren().addAll(indexLabel, descriptionLabel);

				// setText(startIndex + this.getIndex() + 1 + ") " + item.toString());
				setPrefWidth(200);
				setMaxWidth(200);
				setMinWidth(200);
				setGraphic(anHBox);
			}
		}

	}
}
