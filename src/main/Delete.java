//@@author A0133267H
package main;

import java.util.*;

public class Delete extends Command{

	private String strDeleteString;
	private Set<Integer> taskIDs;
	private ArrayList<Task> taskDeleted;
	
	public Delete(String input){
		super(CommandType.DELETE);
		strDeleteString = input;
		taskDeleted = null;
		taskIDs = null;
	}
	
	public Delete(Set<Integer> taskIDs){
		super(CommandType.DELETE);
		this.taskIDs = taskIDs;
		strDeleteString = null;
		taskDeleted = null;
	}
	
	public String getDeleteString(){
		return strDeleteString;
	}
	
	public void setTaskDeleted(ArrayList<Task> taskDeleted){
		this.taskDeleted = taskDeleted;
	}
	
	public ArrayList<Task> getTaskDeleted(){
		return taskDeleted;
	}
	
	public Set<Integer> getTaskIDs(){
		return taskIDs;
	}
	
	public boolean hasTaskIDs(){
		return taskIDs != null ;
	}
	
	public boolean hasDeleteString(){
		return strDeleteString != null;
	}
	
}
