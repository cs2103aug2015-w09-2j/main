//@@author A0133267H
package main.Commands;

import java.util.*;

public class Clear extends Command{

	private ArrayList<Task> taskCleared ;
	
	public Clear(){
		super(CommandType.CLEAR);
	}
	public Clear(ArrayList<Task> taskCleared){
		super(CommandType.CLEAR);
		this.setTaskCleared(taskCleared);
	}
	public ArrayList<Task> getTaskCleared() {
		return taskCleared;
	}
	public void setTaskCleared(ArrayList<Task> taskCleared) {
		this.taskCleared = taskCleared;
	}
	
	
}
