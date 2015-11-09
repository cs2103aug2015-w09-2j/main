//@@author A0133267H
package main;

public class Save extends Command {
	
	private String pathLocation;
	
	public Save(){
		super(CommandType.SAVE);
	}
	
	public Save(String pathLocation){
		super(CommandType.SAVE);
		this.pathLocation = pathLocation;
	}
	
	public String getPathLocation() {
		return pathLocation;
	}

	public void setPathLocation(String pathLocation) {
		this.pathLocation = pathLocation;
	}
	
	
	
}
