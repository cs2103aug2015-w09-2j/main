package main;

/***
 *
 * @author Razali
 *
 */
public class CommandType {

	//To be used by parser and logic
	public static enum Types{
		ADD_DEADLINE("-d"), ADD_EVENT("-e"), ADD_FLOATING("-f"),
		UPDATE("update"), DELETE("delete"), DISPLAY("display"), SEARCH("search"),
		UNDO("undo"),REDO("redo"),
		UNKNOWN("");

		private String strCommand;

		private Types(String strCommand){
			this.strCommand = strCommand;
		}


		public String toString(){
			return strCommand;
		}

	}

	//To be used by parser only
	public static enum TaskTypes{
		FLOATING("-f"), DEADLINE("-d"), EVENT("-e"), UNKNOWN("");

		private String strDelimeter;

		private TaskTypes(String strDelimeter){
			this.strDelimeter = strDelimeter;
		}

		public String toString(){
			return strDelimeter;
		}
	}
}
