package main;

public class Update extends Command{

	private UpdateTask updateTask;
	
	
	/**
	 * @param searchString
	 */
	public Update(UpdateTask updateTask){
		super(null);
		
		this.updateTask = updateTask;
	}
	
	public UpdateTask getUpdateTask(){
		return updateTask;
	}

	
	
}
