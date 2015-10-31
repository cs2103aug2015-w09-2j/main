package main;

public class Floating extends Task{

	/**Description Constructor : Creates a floating task, which is a subclass of the Command abstract class.
	 * @param strDescription
	 */
	private boolean isDone; 
	
	public Floating (String strDescription){
		super(strDescription); 
		isDone = false;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String output = this.getDescription()+" ";
		return output;

	}


	@Override
	public int compareTo(Task o) {
		// TODO Auto-generated method stub
		return 0;
	}


	public boolean isDone() {
		return isDone;
	}


	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
}
