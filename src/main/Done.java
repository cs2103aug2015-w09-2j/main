package main;

import java.util.Set;

public class Done extends Command{
	
	Set<Integer> taskIDs;
	
	public Done(Set<Integer> IDs){
		super(CommandType.DONE);
		taskIDs = IDs;
	}
	
	public Set<Integer> getTaskIDs(){
		return taskIDs;
	}
}
