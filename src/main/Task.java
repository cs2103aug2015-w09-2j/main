package main;

public abstract class Task implements Comparable<Task> {

	private String strDescription;
	private boolean isDone;

	public Task(String description) {
		strDescription = description;
		isDone = false;
	}

	public String getDescription() {
		return strDescription;
	}

	public void setDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
}