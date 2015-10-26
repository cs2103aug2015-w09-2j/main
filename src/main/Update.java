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
	
	public Task getCurrentTask() {
		return taskToUpdate;
	}

	public void setUpdateTask(Task updateTask){
		this.updateTask = updateTask;
	}

	public void setCurrentTask(Task currentTask){
		this.currentTask = currentTask;
	}
	
	public Task getUpdateTask() {
		return updateTask;
	}

}
