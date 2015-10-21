package main;

public class Floating extends Command{

	/**Description Constructor : Creates a floating task, which is a subclass of the Command abstract class.
	 * @param strDescription
	 */
	private String strDescription;
	
	public Floating (String strDescription){
		super(Command.CommandType.ADD_FLOATING);
		this.strDescription = strDescription; 
	}
	
	public String getDescription(){
		return strDescription;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String output = this.getDescription()+" ";
		return output;

	}
}
