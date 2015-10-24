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
	 * Description Constructor : Creates and instance of the Logic class with
	 * the fileName stated
	 * 
	 * @param filename
	 */
	/*
	 * public Logic(String filename) { fileName = filename; }
	 */

	public static Logic getInstance() {
		if (numOfLogics == 0) {
			numOfLogics++;
			return new Logic();
		} else {
			return null;
		}
	}

	/**
	 * Description Default Constructor
	 */
	private Logic() {
	}

	private static Parser parser = new Parser();
	private static UserInterface UI = new UserInterface(); // [teddy] this will
															// be deleted once
															// we shift to GUI
	private FileStorage fileStorage = new FileStorage();
	private static Command.CommandType command;
	private MainApp mainApp; // [teddy] reference to UI
	private ObservableList<Task> tasks; // [teddy] just fill the tasks
	private static Command.CommandType undoCommand = Command.CommandType.UNKNOWN;
	private static Task undoTaskObject;

	/**
	 * Description Takes in the command as a string from the user input and
	 * processes the command and executes the command if its in the correct
	 * format
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchFieldException
	 * @throws ParseException
	 */

	public boolean processCommand(String input) {
		boolean output = true;
		Command task = parser.parse(input);
		if (task.getCommandType().equals(Command.CommandType.UNKNOWN)) {
			return false;
		} else {
			output= true;
			output = executeCommand(task.getCommandType(), task.getTask(), input);
			return output;
		}
	}

	/*
	 * @SuppressWarnings("finally") public boolean processCommand(String input)
	 * throws NoSuchFieldException, ParseException { boolean output = false; try
	 * { Command task = parser.parse(input); if
	 * (task.getCommandType().equals(Command.CommandType.UNKNOWN)) { output =
	 * false; } else { //output= true; output =
	 * executeCommand(task.getCommandType(), task.getTask(), input); } } catch
	 * (NoSuchFieldException e) { System.out.println(
	 * "There is no such field, so the command entered is incorrect" + "  " +
	 * e); } catch (ParseException e) { System.out.println(
	 * "There was an exepction which was caused while parsing" + " " + e); }
	 * finally { return output; } }
	 */

	/**
	 * Description Executes the command
	 * 
	 * @param command
	 * @param input
	 * @param inputString
	 * @return
	 * @throws NoSuchFieldException
	 * @throws ParseException
	 */
	private boolean executeCommand(Command.CommandType command, Task input, String inputString) {
		boolean success = false;
		switch (command) {
		case ADD_EVENT:
			fileStorage.writeTask(input);
			undoCommand = command;
			undoTaskObject = input;
			fillTasks(); // [teddy]
			success = true;
			break;
		case ADD_DEADLINE:
			fileStorage.writeTask(input);
			undoCommand = command;
			undoTaskObject = input;
			fillTasks();
			success = true;
			break;
		case ADD_FLOATING:
			fileStorage.writeTask(input);
			undoCommand = command;
			undoTaskObject = input;
			fillTasks();
			success = true;
			break;
		case UPDATE:
		//	updateTask((UpdateTask) input);
			undoCommand = command;
			undoTaskObject = null; // need to search for the Task object with
									// the updated object description
			fillTasks();
			success = true;
			break;
		case DELETE:
			deleteTask(inputString);
			undoCommand = command;
			undoTaskObject = null; // need to search for the Task object with
									// the updated object description
			fillTasks();
			success = true;
			break;
		case SEARCH:
			//UI.displayView(search(inputString.substring(7)));
			success = true;
			break;
		case UNDO:
		//	success = undo();
			success = true;
			break;
		case DISPLAY:
			success = true;
			UI.displayView(fileStorage.readAllTask());
		default:
			break;
		}
		return success;
	}

	/*private boolean undo() {
		// TODO Auto-generated method stub
		boolean isUndoSuccessful = false;
		switch (undoCommand) {
		case ADD_EVENT:
		case ADD_DEADLINE:
		case ADD_FLOATING:
			fileStorage.delete("1", fileStorage.search(undoTaskObject.getDescription()));
			isUndoSuccessful = true;
			break;
		case DELETE:

			break;
		case UPDATE:
			updateTask((UpdateTask) undoTaskObject);
			break;
		}
		return isUndoSuccessful;
	}*/

	/**
	 * Description Method used to update the task
	 * 
	 * @param input
	 */
	/*private void updateTask(UpdateTask input) {
		FileData data = fileStorage.search(input.getSearchString());
		fileStorage.delete("1", data);
		if (input.hasStartDate()) {
			Event event = new Event(input.getDescription(), input.getStartDate(), input.getStartTime(),
					input.getEndDate(), input.getEndTime());
			fileStorage.writeTask(event);
		} else if (input.hasEndDate()) {
			Deadline deadline = new Deadline(input.getDescription(), input.getEndDate(), input.getEndTime());
			fileStorage.writeTask(deadline);
		} else {
			Floating floating = new Floating(input.getDescription());
			fileStorage.writeTask(floating);
		}
	}*/

	/**
	 * @param substring
	 * @return
	 */
	/*private ArrayList<Task> search(String substring) {
		ArrayList<Task> output = new ArrayList<Task>();
		FileData data = fileStorage.search(substring);
		HashMap<Integer, String> displayMap = data.getDisplayMap();
		Iterator it = displayMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			output.add(getTaskFromString((String) pair.getValue()));
		}
		return output;
	}*/

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
	/*private ArrayList<Task> stringToTask() {
		ArrayList<String> data = new ArrayList<String>();
		ArrayList<Task> tasks = new ArrayList<Task>();
		data = FileStorage.readFile();
		for (String s : data) {
			tasks.add(getTaskFromString(s));
		}
		// TODO Auto-generated method stub
		return tasks;
	}*/

	/**
	 * @param s
	 * @return
	 */
	/*private Task getTaskFromString(String s) {
		Task a = parser.parse(s).getTask();
		// System.out.println(a.toString());
		return parser.parse(s).getTask();
	}*/

	/**
	 * Set the reference to MainApp
	 * 
	 * @param mainApp
	 *
	 *            Added by teddy
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Initialize the tasks
	 * 
	 * @param tasks
	 *
	 *            Added by teddy
	 */
	public void setTasks(ObservableList<Task> tasks) {
		this.tasks = tasks;
		fillTasks();
	}

	/**
	 * Fill in the ObservableList<Task>
	 *
	 * Added by Teddy Ravi, you can just call this method every time user
	 * add/delete/edit the to-do list
	 */
	public void fillTasks() {
		tasks = FXCollections.observableList(fileStorage.readAllTask());
	}
}
