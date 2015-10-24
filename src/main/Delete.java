package main;

public class Delete extends Command{

	String strDeleteString;
	Integer intTaskID;
	
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
	}
	
	public String getDeleteString(){
		return strDeleteString;
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
