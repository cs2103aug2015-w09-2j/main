//@@author A0133267H
package main.Commands;

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

	// @@author A0133869R
	public boolean isDone() {
		return isDone;
	}

	// @@author A0133869R
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
}