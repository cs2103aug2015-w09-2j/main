import java.text.ParseException;

public class Logic {
	public String fileName;
	public Logic(String filename){
		fileName = filename;
	}
	public Logic(){
	}	
	private static UserInterface UI = new UserInterface();
	private static FileStorage fileStorage = new FileStorage();
	private static CommandType.Types command; 
	
	public static boolean processCommand(String input) throws NoSuchFieldException, ParseException{
		command = Parser.parse(input);
		if(command == CommandType.Types.UNKNOWN){
			return false;
		}else{
			executeCommand(command,input);
		}
		return true;
	}
	
	private static boolean executeCommand(CommandType.Types command,String input) throws NoSuchFieldException, ParseException{
		boolean success = false;
		switch(command){
		case ADD_EVENT :
			Event event = createEvent(input);
			writeTaskTofile(event);
			success=true;
			break;
		case ADD_DEADLINE:
			DeadLine deadline = createDeadLine(input);
			writeTaskTofile(deadline);
			success=true;
			break;
		case ADD_FLOATING:
			Floating floating = createFloating(input);
			writeTaskTofile(floating);
			success=true;
			break;
		case UPDATE:
			success=true;
			break;
		case DELETE:	
			success=true;
			break;
		case DISPLAY:	
			UI.displayView(FileStorage.readTaskFromFile());
			success=true;
		default:
			break;		
		}
	return success;	
	}

	private static Floating createFloating(String input) {
		String floatingDescription = Parser.getDescription(input.substring(6));
		Floating floating = new Floating(floatingDescription);
		return floating;
	}

	private static DeadLine createDeadLine(String input) {
		String deadlineDescription = Parser.getDescription(input.substring(6));
		DateClass endDeadline = Parser.getDate(input.substring(6));
		DeadLine deadline = new DeadLine(deadlineDescription,endDeadline);
		return deadline;
	}

	private static Event createEvent(String input) {
		String eventDescription = Parser.getDescription(input.substring(6));
		DateClass endDate = Parser.getDate(input.substring(6));
		Event event = new Event(eventDescription,endDate);
		return event;
	}

	private static void writeTaskTofile(Task task) {
		String taskType = task.getClass().getName();
		String writeToFile;
		switch(taskType){
		case "Event":
			writeEventToFile(task, taskType);
			break;
		case "DeadlLine":
			writeDeadlineToFile(task, taskType);
			break;
		case "Floating":
			writeFloatingToFile(task, taskType);
			break;
		}
	}

	private static void writeFloatingToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = taskType + " " +task.getDescription();
		fileStorage.write(writeToFile);
	}

	private static void writeDeadlineToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = taskType + " " +task.getDescription() + " " +((DeadLine) task).getEndDate().toString();
		fileStorage.write(writeToFile);
	}

	private static void writeEventToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = taskType + " " +task.getDescription()+" " + ((Event) task).getStartDate().toString() +" " + ((Event) task).getEndDate().toString();
		fileStorage.write(writeToFile);
	}
}

