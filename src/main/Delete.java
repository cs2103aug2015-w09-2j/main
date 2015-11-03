package main;

import java.util.Set;

public class Delete extends Command{

	String strDeleteString;
	Set<Integer> taskIDs;
	Task taskDeleted;
	
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
	
	public void setTaskDeleted(Task taskDeleted){
		this.taskDeleted = taskDeleted;
	}
	
	public Task getTaskDeleted(){
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
