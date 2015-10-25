package main;

public class Delete extends Command{

	String strDeleteString;
	Integer intTaskID;
	Task taskDeleted;
	
	public Delete(String input){
		super(CommandType.DELETE);
		
		if(input == null || input.equals("")){
			this.strDeleteString = null;
			intTaskID = null;
		}else{
			
			try{
				intTaskID = Integer.valueOf(input);
			}catch(NumberFormatException nexp){
				intTaskID = null;
				strDeleteString = input;
			}
		}
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
	
	public int getTaskID(){
		return intTaskID;
	}
	
	public boolean hasTaskID(){
		return intTaskID != null;
	}
	
	public boolean hasDeleteString(){
		return strDeleteString != null;
	}
	
}
