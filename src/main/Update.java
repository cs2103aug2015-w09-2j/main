//@@author A0133267H
package main;

public class Update extends Command {

	private UpdateTask taskToUpdate;
	private UpdateTask updatedTask;
	
	private Task currentTask ;
	private Task updateTask;
	

	/**
	 * @param searchString
	 */
	public Update(UpdateTask taskToUpdate) {
		super(Command.CommandType.UPDATE);

		this.taskToUpdate = taskToUpdate;
	}

	public Update(UpdateTask taskToUpdate, UpdateTask updatedTask) {
		super(Command.CommandType.UPDATE);

		this.taskToUpdate = taskToUpdate;
		this.updatedTask = updatedTask;
	}

	public UpdateTask getTaskToUpdate() {
		return taskToUpdate;
	}

	public void setTaskToUpdate(UpdateTask taskToUpdate){
		this.updatedTask = taskToUpdate;
	}

	public void setUpdatedTask(UpdateTask updatedTask){
		this.updatedTask = updatedTask;
	}
	
	public UpdateTask getUpdatedTask() {
		return updatedTask;
	}
	//@@author A0133869R
	public Task getCurrentTask() {
		return currentTask;
	}
	//@@author A0133869R
	public void setUpdateTask(Task updateTask){
		this.updateTask = updateTask;
	}
	//@@author A0133869R
	public void setCurrentTask(Task currentTask){
		this.currentTask = currentTask;
	}
	//@@author A0133869R
	public Task getUpdateTask() {
		return updateTask;
	}

}
