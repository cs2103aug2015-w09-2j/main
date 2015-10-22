package main;

public abstract class Task {
	
	private String strDescription;
	
	public Task(String description){
		strDescription = description;
	}
	public String getDescription(){
		return strDescription;
	}
}
