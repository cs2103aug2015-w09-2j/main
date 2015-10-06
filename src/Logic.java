import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Logic {
	public String fileName;
	public Logic(String filename){
		fileName = filename;
	}
	public Logic(){
	}	
	private static Parser parser = new Parser();
	private static UserInterface UI = new UserInterface();
	private static FileStorage fileStorage = new FileStorage();
	private static CommandType.Types command; 
	
	public static boolean processCommand(String input) throws NoSuchFieldException, ParseException{
		TaskPair task = parser.parse(input);
		if(task.getType() == CommandType.Types.UNKNOWN){
			return false;
		}else{
			//executeCommand(,input);
			executeCommand(task.getType() , task.getTask() , input);
		}
		return true;
	}
	
	private static boolean executeCommand(CommandType.Types command,Task input , String inputString) throws NoSuchFieldException, ParseException{
		boolean success = false;
		switch(command){
		case ADD_EVENT :
			//Event event = createEvent(input);
			writeTaskTofile(input);
			success=true;
			break;
		case ADD_DEADLINE:
			//Deadline deadline = createDeadLine(input);
			//writeTaskTofile(deadline);
			writeTaskTofile(input);
			success=true;
			break;
		case ADD_FLOATING:
			//Floating floating = createFloating(input);
			//writeTaskTofile(floating);
			writeTaskTofile(input);
			success=true;
			break;
		case UPDATE:
			
			success=true;
			break;
		case DELETE:
			deleteTask(inputString);
			success=true;
			break;
		case SEARCH:
			UI.displayView(search(inputString.substring(7)));
			break;
		case DISPLAY:	
			//UI.displayView(FileStorage.readTaskFromFile());
			UI.displayView(stringToTask());
			success=true;
		default:
			break;		
		}
	return success;	
	}
/*
	private static Floating createFloating(String input) {
		String floatingDescription = Parser.getDescription(input.substring(6));
		Floating floating = new Floating(floatingDescription);
		return floating;
	}

	private static Deadline createDeadLine(String input) {
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
	}*/

	private static ArrayList<Task> search(String substring) {
		ArrayList<Task> output = new ArrayList<Task>();
		FileData data = fileStorage.search(substring);
		HashMap<Integer, String> displayMap = data.getDisplayMap();
		Iterator it = displayMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			output.add(getTaskFromString((String)pair.getValue()));			
		}
		return output;
	}
	private static void deleteTask(String index) {
		// TODO Auto-generated method stub
		String[] arr = index.split(" ");
		fileStorage.deleteTask(Integer.valueOf(arr[1]));
		
	}
	
	
	private static ArrayList<Task> stringToTask() {
		ArrayList<String> data =  new ArrayList<String>();
		ArrayList<Task> tasks = new ArrayList<Task>();
		data = FileStorage.readFile();
		for(String s :data){
			tasks.add(getTaskFromString(s));
		}	
		// TODO Auto-generated method stub
		return tasks;
	}
	private static Task getTaskFromString(String s) {
		// TODO Auto-generated method stub
		return parser.parse(s).getTask();
	}
	private static void writeTaskTofile(Task task) {
		String taskType = task.getClass().getName();
		//String writeToFile="";
		switch(taskType){
		case "Event":
			writeEventToFile(task, taskType);
			break;
		case "Deadline":
			writeDeadlineToFile(task, taskType);
			break;
		case "Floating":
			writeFloatingToFile(task, taskType);
			break;
		}
	}

	private static void writeFloatingToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = "add " + CommandType.TaskTypes.FLOATING + " " +task.getDescription();
		fileStorage.write(writeToFile);
	}

	private static void writeDeadlineToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile ="add " + CommandType.TaskTypes.DEADLINE + " " +task.getDescription() + " " +((Deadline) task).getEndDate().toString() + " " + ((Deadline) task).getEndTime().toString() ;
		fileStorage.write(writeToFile);
	}

	private static void writeEventToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = "add "+CommandType.TaskTypes.EVENT + " " +task.getDescription()+" " + ((Event) task).getStartDate().toString() +" " + ((Event) task).getStartTime().toString() + " " + ((Event) task).getEndDate().toString() + " " + ((Event) task).getEndTime().toString() ;
		fileStorage.write(writeToFile);
	}
}

