package main;

public class Command {
	//To be used by parser and logic
	public static enum CommandType{
		ADD_DEADLINE("deadline"), ADD_FLOATING("floating"),ADD_EVENT("event"),
		UPDATE("update"), DELETE("delete"), CLEAR("clear"), EXIT("exit"), 
		DISPLAY("display"), SEARCH("search"),UNDO("undo"),REDO("redo"),
		UNKNOWN("");

		private String strCommand;

		private CommandType(String strCommand){
			this.strCommand = strCommand;
		}


		public String toString(){
			return strCommand;
		}

	}
		
	
	
	private CommandType commandType;
	private Task task;
	
	/**
	 * Description Superclass method to set the description of the Task.
	 * @param description
	 */
	public Command(Command.CommandType commandType){
		this.commandType = commandType;
		task = null;
	}

	public Task getTask(){
		return task;
	}


	public void setTask(Task task){
		this.task = task;
	}

	/**
	 * Description Superclass method to obtain the description of any of the task objects.
	 * @return strDescription
	 */
	public Command.CommandType getCommandType(){
		return commandType;
	}
}
