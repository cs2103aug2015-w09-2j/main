package main;

public abstract class Task implements Comparable<Task> {

	private String strDescription;

	public Task(String description){
		strDescription = description;
		
	}
	public String getDescription(){
		return strDescription;
	}
	public void setDescription(String strDescription){
		this.strDescription = strDescription;
	}
}