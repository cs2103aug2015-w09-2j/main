package main;

public class Display extends Command{

	String strDisplayString;
	
	public Display(String strDisplayString){
		super(CommandType.DISPLAY);
		
		if(strDisplayString == null || strDisplayString.equals("")){
			this.strDisplayString = null;
		}
	}
	
	public String getDisplayString(){
		return strDisplayString;
	}
}
