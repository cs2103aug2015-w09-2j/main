package main;

public class TaskPair {

	private Task task;
	private CommandType.Types taskType;

	public TaskPair(Task task, CommandType.Types type){
		this.task = task;
		this.taskType = type;
	}

	public Task getTask(){
		return task;
	}

	public CommandType.Types getType(){
		return taskType;
	}

}