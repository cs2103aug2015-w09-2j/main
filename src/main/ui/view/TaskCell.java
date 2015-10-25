/*
 * Comment on this class:
 * 1. Consider extracting the individual cell class from TaskCell
 * 2. Consider just implementing TaskCell if there're not type-specific customization
 * 3. If there is a type-specific customization, consider combining Event and Deadline together
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
				item = (Event) item;
				setText(this.getIndex()+1 + ") " + item.toString());
				setGraphic(null);
			}
		}
	}

	protected static class DeadlineCell extends ListCell<Task> {
		private int startIndex;

		protected DeadlineCell(int startIndex) {
			this.startIndex = startIndex;
		}


		@Override
		protected void updateItem(Task item, boolean empty) {
			super.updateItem(item, empty);

			if (empty || item == null) {
				setText(null);
				setGraphic(null);
			} else {
				item = (Deadline) item;
				setText(startIndex + this.getIndex() + 1 + ") " + item.toString());
				setGraphic(null);
			}
		}

	}

	protected static class FloatingCell extends ListCell<Task> {
		private int startIndex;

		protected FloatingCell(int startIndex) {
			this.startIndex = startIndex;
		}
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
