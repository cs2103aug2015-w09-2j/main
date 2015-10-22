package main.util;

import java.util.Comparator;

import main.Deadline;
import main.Event;
import main.Task;

public class TaskTypeComparator implements Comparator<Task>{

	@Override
	public int compare(Task aTask, Task anotherTask) {
		if (aTask.getClass().equals(anotherTask.getClass()))
			return 0;
		else if (aTask instanceof Event)
			return 1;
		else if (anotherTask instanceof Event)
			return -1;
		else if (aTask instanceof Deadline)
			return 1;
		else
			return -1;
	}

}
