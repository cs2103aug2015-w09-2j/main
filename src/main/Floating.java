package main;

public class Floating extends Task{

	/**Description Constructor : Creates a floating task, which is a subclass of the Command abstract class.
	 * @param strDescription
	 */
	
	public Floating (String strDescription){
		super(strDescription); 
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String output = this.getDescription()+" ";
		return output;

	}
}
