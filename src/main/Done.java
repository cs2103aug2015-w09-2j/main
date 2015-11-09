//@@author A0133267H
package main;

import java.util.*;

public class Done extends Command{
	
	Set<Integer> taskIDs;
	ArrayList<Task> tasksSetDone;
	
	public Done(Set<Integer> IDs){
		super(CommandType.DONE);
		taskIDs = IDs;
	}
	
	public Done(Set<Integer> IDs, ArrayList<Task> tasksSetDone){
		super(CommandType.DONE);
		taskIDs = IDs;
		this.tasksSetDone = tasksSetDone;
	}
	
	public Set<Integer> getTaskIDs(){
		return taskIDs;
	}
	
	public ArrayList<Task> getTasksSetDone(){
		return tasksSetDone;
	}
	
	
}
