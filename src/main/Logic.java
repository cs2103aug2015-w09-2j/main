package main;

import java.text.ParseException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import main.ui.MainApp;

/**
 * @author Ravi
 *
 */
public class Logic {
	private String fileName;
	private static Logic oneLogic = null;

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
		if (oneLogic == null) {
			oneLogic = new Logic();
		}
		return oneLogic;
	}

	/**
	 * Description Default Constructor
	 */
	private Logic() {
		updateTaskLists();
	}

	private static Parser parser = new Parser();
	private static UserInterface UI = new UserInterface(); // [teddy] this will
															// be deleted once
															// we shift to GUI
	private FileStorage fileStorage = new FileStorage();
	//private static Command.CommandType command;
	//private static Command.CommandType undoCommand = Command.CommandType.UNKNOWN; // most
																					// recent
																					// undo
																					// command
	//private static Task undoTaskObject;
	//private static ArrayList<Task> taskHistory;
	private static Stack<Command> commandHistory;
	private static ArrayList<Task> allEvents;
	private static ArrayList<Task> allDeadlines;
	private static ArrayList<Task> allFloatingTasks;
	private static ArrayList<Task> allTasks;
	private MainApp mainApp; // [teddy] reference to UI
	// private ObservableList<Task> tasks; // [teddy] just fill the tasks
	private ObservableList<Task> events;
	private ObservableList<Task> deadlines;
	private ObservableList<Task> floatings;

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
		Command command = parser.parse(input);
		if (command.getCommandType().equals(Command.CommandType.UNKNOWN)) {
			return false;
		} else {
			output = true;
			output = executeCommand(command);
			return output;
		}
	}

	private void updateTaskLists() {
		allEvents = fileStorage.readEventTask();
		allDeadlines = fileStorage.readDeadlineTask();
		allFloatingTasks = fileStorage.readFloatingTask();
		allTasks = fileStorage.readAllTask();
		// TODO Auto-generated method stub
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
	private boolean executeCommand(Command inputCommand) {
		boolean success = false;
		switch (inputCommand.getCommandType()) {
		case ADD_EVENT:
			fileStorage.writeTask(inputCommand.getTask());
			//undoCommand = inputCommand.getCommandType();
			//undoTaskObject = inputCommand.getTask();
			commandHistory.push(inputCommand);
			updateTaskLists();
			// fillTasks(); // [teddy]
			fillEvents();
			success = true;
			break;
		case ADD_DEADLINE:
			fileStorage.writeTask(inputCommand.getTask());
			//undoCommand = inputCommand.getCommandType();
			//undoTaskObject = inputCommand.getTask();
			commandHistory.push(inputCommand);
			updateTaskLists();
			// fillTasks();
			fillDeadlines();
			success = true;
			break;
		case ADD_FLOATING:
			fileStorage.writeTask(inputCommand.getTask());
			//undoCommand = inputCommand.getCommandType();
			//undoTaskObject = inputCommand.getTask();
			commandHistory.push(inputCommand);
			updateTaskLists();
			// fillTasks();
			fillFloatings();
			success = true;
			break;
		case UPDATE:
			// updateTask((UpdateTask) input);
			update(inputCommand);
			// commandHistory.push(inputCommand);
			updateTaskLists();
			//undoCommand = inputCommand.getCommandType();
			//undoTaskObject = null; // need to search for the Task object with
									// the updated object description
			// fillTasks();
			success = true;
			break;
		case DELETE:
			deleteTask(inputCommand);
			//undoCommand = inputCommand.getCommandType();
			//undoTaskObject = null; // need to search for the Task object with
									// the updated object description
			// fillTasks();
			success = true;
			break;
		case SEARCH:
			// UI.displayView(search(inputString.substring(7)));
			success = true;
			break;
		case UNDO:
			success = undo();
			success = true;
			break;
		case REDO:
			success = redo();
			success = true;
			break;
		case DISPLAY:
			success = true;
			UI.displayView(fileStorage.readAllTask());
			break;
		//case EXIT:
		//	System.exit(0);
		default:
			break;
		}
		return success;
	}

	/*
	 * private boolean undo() { // TODO Auto-generated method stub boolean
	 * isUndoSuccessful = false; switch (undoCommand) { case ADD_EVENT: case
	 * ADD_DEADLINE: case ADD_FLOATING: fileStorage.delete("1",
	 * fileStorage.search(undoTaskObject.getDescription())); isUndoSuccessful =
	 * true; break; case DELETE:
	 * 
	 * break; case UPDATE: updateTask((UpdateTask) undoTaskObject); break; }
	 * return isUndoSuccessful; }
	 */

	private boolean redo() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Description Method used to update the task
	 *
	 * @param input
	 */
	/*
	 * private void updateTask(UpdateTask input) { FileData data =
	 * fileStorage.search(input.getSearchString()); fileStorage.delete("1",
	 * data); if (input.hasStartDate()) { Event event = new
	 * Event(input.getDescription(), input.getStartDate(), input.getStartTime(),
	 * input.getEndDate(), input.getEndTime()); fileStorage.writeTask(event); }
	 * else if (input.hasEndDate()) { Deadline deadline = new
	 * Deadline(input.getDescription(), input.getEndDate(), input.getEndTime());
	 * fileStorage.writeTask(deadline); } else { Floating floating = new
	 * Floating(input.getDescription()); fileStorage.writeTask(floating); } }
	 */

	private boolean undo() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param substring
	 * @return
	 */
	/*
	 * private ArrayList<Task> search(String substring) { ArrayList<Task> output
	 * = new ArrayList<Task>(); FileData data = fileStorage.search(substring);
	 * HashMap<Integer, String> displayMap = data.getDisplayMap(); Iterator it =
	 * displayMap.entrySet().iterator(); while (it.hasNext()) { Map.Entry pair =
	 * (Map.Entry) it.next(); output.add(getTaskFromString((String)
	 * pair.getValue())); } return output; }
	 */

	private void update(Command inputCommand) {
		Update updateCommand = (Update) inputCommand;
		UpdateTask processUpdate = (UpdateTask) updateCommand.getTask();
		ArrayList<ArrayList<Task>> searchResult = fileStorage.search(processUpdate.getSearchString());
		Task taskToUpdate;
		if (searchResult.get(0) != null)
			taskToUpdate = (Event) searchResult.get(0).get(0);
		else if (searchResult.get(1) != null)
			taskToUpdate = (Deadline) searchResult.get(0).get(0);
		else if (searchResult.get(2) != null)
			taskToUpdate = (Floating) searchResult.get(0).get(0);
		else
			taskToUpdate = null;
		// updateCommand.setTaskToUpdate((UpdateTask) taskToUpdate);
		fileStorage.deleteTask(updateCommand.getTaskToUpdate());
		Task updatedTask;
		if (processUpdate.hasStartDate()) {
			updatedTask = new Event(processUpdate.getDescription(), processUpdate.getStartDate(),
					processUpdate.getStartTime(), processUpdate.getEndDate(), processUpdate.getEndTime());
		} else if (processUpdate.hasEndDate()) {
			updatedTask = new Deadline(processUpdate.getDescription(), processUpdate.getEndDate(),
					processUpdate.getEndTime());
		} else {
			updatedTask = new Floating(processUpdate.getDescription());
		}
		fileStorage.writeTask(updatedTask);
		commandHistory.push(new Update((UpdateTask) taskToUpdate, (UpdateTask) updatedTask));
		updateTaskLists();
	}

	/**
	 * @param index
	 */
	private void deleteTask(Command inputCommand) {
		Delete deleteCommand = ((Delete) inputCommand);
		Task taskToDelete = null;
		if (deleteCommand.hasDeleteString()) {
			ArrayList<ArrayList<Task>> searchResult = fileStorage.search(deleteCommand.getDeleteString());
			if (searchResult.get(0) != null)
				taskToDelete = (Event) searchResult.get(0).get(0);
			else if (searchResult.get(1) != null)
				taskToDelete = (Deadline) searchResult.get(0).get(0);
			else if (searchResult.get(2) != null)
				taskToDelete = (Floating) searchResult.get(0).get(0);
			else
				taskToDelete = null;
		}
		fileStorage.deleteTask(taskToDelete);
		deleteCommand.setTaskDeleted(taskToDelete);
		commandHistory.push(deleteCommand);
		updateTaskLists();
	}

	/**
	 * @return
	 */
	/*
	 * private ArrayList<Task> stringToTask() { ArrayList<String> data = new
	 * ArrayList<String>(); ArrayList<Task> tasks = new ArrayList<Task>(); data
	 * = FileStorage.readFile(); for (String s : data) {
	 * tasks.add(getTaskFromString(s)); } // TODO Auto-generated method stub
	 * return tasks; }
	 */

	/**
	 * @param s
	 * @return
	 */
	/*
	 * private Task getTaskFromString(String s) { Task a =
	 * parser.parse(s).getTask(); // System.out.println(a.toString()); return
	 * parser.parse(s).getTask(); }
	 */

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
	/*
	 * public void setTasks(ObservableList<Task> tasks) { this.tasks = tasks;
	 * fillTasks(); }
	 */

	public void setTasks(ObservableList<Task> events, ObservableList<Task> deadlines, ObservableList<Task> floatings) {
		this.events = events;
		this.deadlines = deadlines;
		this.floatings = floatings;

		fillEvents();
		fillDeadlines();
		fillFloatings();

	}

	/**
	 * Fill in the ObservableList<Task>
	 *
	 * Added by Teddy Ravi, you can just call this method every time user
	 * add/delete/edit the to-do list
	 */

	/*
	 * public void fillTasks() { tasks.setAll(fileStorage.readAllTask()); }
	 */

	public void fillEvents() {
		events.setAll(fileStorage.readEventTask());
	}

	public void fillDeadlines() {
		deadlines.setAll(fileStorage.readDeadlineTask());
	}

	public void fillFloatings() {
		floatings.setAll(fileStorage.readFloatingTask());
		System.out.println(floatings.size());
	}
}
