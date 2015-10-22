package main.util;

import java.util.Comparator;

import main.Deadline;
import main.Event;
import main.Task;

/**
 * @author Teddy Hartanto
 *
 */

public class TaskComparator implements Comparator<Task>{

	@Override
	public int compare(Task aTask, Task anotherTask) {
		if (aTask.getClass().equals(anotherTask.getClass())) {

			if (aTask instanceof Event) {
				return compareEvents((Event) aTask, (Event) anotherTask);

			} else if (aTask instanceof Deadline) {
				return compareDeadlines((Deadline) aTask, (Deadline) anotherTask);

			} else {
				return 0;
			}

		} else if (aTask instanceof Event) {
			return 1;

		} else if (anotherTask instanceof Event) {
			return -1;

		} else if (aTask instanceof Deadline) {
			return 1;

		} else {
			return -1;

		}
	}

	public int compareEvents(Event anEvent, Event anotherEvent) {
		if (anEvent.getStartDate().equals(anotherEvent.getStartDate())) {
			return anEvent.getStartTime().compareTo(anotherEvent.getStartTime());
		} else {
			return anEvent.getStartDate().compareTo(anotherEvent.getStartDate());
		}
	}

	public int compareDeadlines(Deadline aDeadline, Deadline anotherDeadline) {
		if (aDeadline.getEndDate().equals(anotherDeadline.getEndDate())) {
			return aDeadline.getEndTime().compareTo(anotherDeadline.getEndTime());
		} else {
			return aDeadline.getEndDate().compareTo(anotherDeadline.getEndDate());
		}
	}

}
