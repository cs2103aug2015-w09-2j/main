package main;

public abstract class Task {
	private String strDescription;

	/**
	 * Description Superclass method to set the description of the Task.
	 * @param description
	 */
	public Task(String description){
		strDescription = description;
	}


	/**
	 * Description Superclass method to obtain the description of any of the task objects.
	 * @return strDescription
	 */
	public String getDescription(){
		return strDescription;
	}
}
