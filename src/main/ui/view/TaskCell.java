/*
 * Comment on this class:
 * Consider extracting the individual cell class from TaskCell
 */
package main.ui.view;

import javafx.scene.control.ListCell;
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
				String day = ((Event) item).getStartDate().getStrDay();
				String month = ((Event) item).getStartDate().getStrMonth();
				String time = ((Event) item).getStartTime().to12HourFormat();

				setText(this.getIndex()+1 + ") " + day + " " + time + " " + item.getDescription());
				setGraphic(null);
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

				setText(startIndex + this.getIndex() + 1 + ") " + day + " " + time + " " + item.getDescription());
				setGraphic(null);
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
				setText(startIndex + this.getIndex() + 1 + ") " + item.toString());
				setGraphic(null);
			}
		}

	}
}
