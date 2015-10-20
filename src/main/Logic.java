package main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import main.ui.MainApp;

/**
 * @author Ravi
 *
 */
public class Logic {
	private String fileName;
	private static int numOfLogics = 0;
	/**
	 * Description Constructor : Creates and instance of the Logic class with the fileName stated
	 * @param filename
	 */
	/*public Logic(String filename) {
		fileName = filename;
	}*/
	
	public static Logic getInstance(){
		if(numOfLogics==0){
			numOfLogics++;
			return new Logic();
		}else{
			return null;
		}
	}

	/**
	 * Description Default Constructor
	 */
	private Logic() {
	}

	private static Parser parser = new Parser();
	private static UserInterface UI = new UserInterface(); // [teddy] this will be deleted once we shift to GUI
	private static FileStorage fileStorage = new FileStorage();
	private static CommandType.Types command;
	private MainApp mainApp; // [teddy] reference to UI
	private ObservableList<Task> tasks; // [teddy] just fill the tasks
	private static CommandType.Types undoCommand = CommandType.Types.UNKNOWN;
	private static Task undoTaskObject;
	/**
	 * Description Takes in the command as a string from the user input and processes the command and executes the command if its in the correct format
	 * @param input
	 * @return
	 * @throws NoSuchFieldException
	 * @throws ParseException
	 */
	@SuppressWarnings("finally")
	public boolean processCommand(String input) throws NoSuchFieldException, ParseException {
		boolean output=false;
		try{
		TaskPair task = parser.parse(input);
		if (task.getType() == CommandType.Types.UNKNOWN) {
			output=false;
		} else {
			// executeCommand(,input);
			executeCommand(task.getType(), task.getTask(), input);
			output =true;
		}
		}catch(NoSuchFieldException e){
			System.out.println("There is no such field, so the command entered is incorrect" + "  "+e);
		}catch(ParseException e){
			System.out.println("There was an exepction which was caused while parsing" + " " + e);
		}finally{
			return output;
		}
	}

	/**
	 * Description Executes the command
	 * @param command
	 * @param input
	 * @param inputString
	 * @return
	 * @throws NoSuchFieldException
	 * @throws ParseException
	 */
	private boolean executeCommand(CommandType.Types command, Task input, String inputString)
			throws NoSuchFieldException, ParseException {
		boolean success = false;
		switch (command) {
		case ADD_EVENT:
			// Event event = createEvent(input);
			writeTaskTofile(input);
			undoCommand = command ; 
			undoTaskObject = input;
			fillTasks(); // [teddy]
			success = true;
			break;
		case ADD_DEADLINE:
			// Deadline deadline = createDeadLine(input);
			// writeTaskTofile(deadline);
			writeTaskTofile(input);
			undoCommand = command ; 
			undoTaskObject = input;
			fillTasks();
			success = true;
			break;
		case ADD_FLOATING:
			// Floating floating = createFloating(input);
			// writeTaskTofile(floating);
			writeTaskTofile(input);
			undoCommand = command ; 
			undoTaskObject = input;
			fillTasks();
			success = true;
			break;
		case UPDATE:
			updateTask((Update) input);
			undoCommand = command ; 
			undoTaskObject = null; // need to search for the Task object with the updated object description
			fillTasks();
			success = true;
			break;
		case DELETE:
			deleteTask(inputString);
			undoCommand = command ; 
			undoTaskObject = null ; // need to search for the Task object with the updated object description 
			fillTasks();
			success = true;
			break;
		case SEARCH:
			UI.displayView(search(inputString.substring(7)));
			success = true;
			break;
		case UNDO:
			success = undo();
			success=true;
			break;
		case DISPLAY:
			UI.displayView(stringToTask());
			success = true;
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

	private boolean undo() {
		// TODO Auto-generated method stub
		boolean isUndoSuccessful = false;
		switch(undoCommand){
		case ADD_EVENT:
		case ADD_DEADLINE:
		case ADD_FLOATING:
			//FileData searchTask = fileStorage.search(undoTaskObject.getDescription());
			fileStorage.delete("1",fileStorage.search(undoTaskObject.getDescription()));
			isUndoSuccessful =true;
			break;
		case DELETE:
			
			break;
		case UPDATE:
			updateTask((Update) undoTaskObject);
			break;
		}
		return isUndoSuccessful;
	}

	/**
	 * Description Method used to update the task
	 * @param input
	 */
	private void updateTask(Update input) {
		FileData data = fileStorage.search(input.getSearchString());
		fileStorage.delete("1", data);
		if (input.hasStartDate()) {
			Event event = new Event(input.getDescription(), input.getStartDate(), input.getStartTime(),
					input.getEndDate(), input.getEndTime());
			writeTaskTofile(event);
		} else if (input.hasEndDate()) {
			Deadline deadline = new Deadline(input.getDescription(), input.getEndDate(), input.getEndTime());
			writeTaskTofile(deadline);
		} else {
			Floating floating = new Floating(input.getDescription());
			writeTaskTofile(floating);
		}
	}

	/**
	 * @param substring
	 * @return
	 */
	private ArrayList<Task> search(String substring) {
		ArrayList<Task> output = new ArrayList<Task>();
		FileData data = fileStorage.search(substring);
		HashMap<Integer, String> displayMap = data.getDisplayMap();
		Iterator it = displayMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			output.add(getTaskFromString((String) pair.getValue()));
		}
		return output;
	}

	/**
	 * @param index
	 */
	private void deleteTask(String index) {
		// TODO Auto-generated method stub
		String[] arr = index.split(" ");
		fileStorage.deleteTask(Integer.valueOf(arr[1]));

	}

	/**
	 * @return
	 */
	private ArrayList<Task> stringToTask() {
		ArrayList<String> data = new ArrayList<String>();
		ArrayList<Task> tasks = new ArrayList<Task>();
		data = FileStorage.readFile();
		for (String s : data) {
			tasks.add(getTaskFromString(s));
		}
		// TODO Auto-generated method stub
		return tasks;
	}

	/**
	 * @param s
	 * @return
	 */
	private Task getTaskFromString(String s) {
		Task a = parser.parse(s).getTask();
		//System.out.println(a.toString());
		return parser.parse(s).getTask();
	}

	/**
	 * @param task
	 */
	private void writeTaskTofile(Task task) {
		String taskType = task.getClass().getName().substring(5);
		//taskType = taskType.
		// String writeToFile="";
		//System.out.println(taskType);
		switch (taskType) {
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

	/**
	 * @param task
	 * @param taskType
	 */
	private void writeFloatingToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = "add " + CommandType.TaskTypes.FLOATING + " " + task.getDescription();
		fileStorage.write(writeToFile);
	}

	/**
	 * @param task
	 * @param taskType
	 */
	private void writeDeadlineToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = "add " + CommandType.TaskTypes.DEADLINE + " " + task.getDescription() + " "
				+ ((Deadline) task).getEndDate().toString() + " " + ((Deadline) task).getEndTime().toString();
	
		fileStorage.write(writeToFile);
	}

	/**
	 * @param task
	 * @param taskType
	 */
	private void writeEventToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = "add " + CommandType.TaskTypes.EVENT + " " + task.getDescription() + " "
				+ ((Event) task).getStartDate().toString() + " " + ((Event) task).getStartTime().toString() + " "
				+ ((Event) task).getEndDate().toString() + " " + ((Event) task).getEndTime().toString();
		fileStorage.write(writeToFile);
	}

	/**
	 * Set the reference to MainApp
	 * @param mainApp
	 *
	 * Added by teddy
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Initialize the tasks
	 * @param tasks
	 *
	 * Added by teddy
	 */
	public void setTasks(ObservableList<Task> tasks) {
		this.tasks = tasks;
		fillTasks();
	}

	/**
	 * Fill in the ObservableList<Task>
	 *
	 * Added by Teddy
	 * Ravi, you can just call this method every time user add/delete/edit the to-do list
	 */
	public void fillTasks() {
		tasks = FXCollections.observableList(stringToTask());
	}
}
