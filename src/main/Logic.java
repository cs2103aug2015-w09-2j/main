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
		undoCommandHistory = new Stack<Command>();
		redoCommandHistory = new Stack<Command>();
	}

	private static Parser parser = new Parser();
	private static UserInterface UI = new UserInterface(); // [teddy] this will
															// be deleted once
															// we shift to GUI
	private FileStorage fileStorage = new FileStorage();
	// private static Command.CommandType command;
	// private static Command.CommandType undoCommand =
	// Command.CommandType.UNKNOWN; // most
	// recent
	// undo
	// command
	// private static Task undoTaskObject;
	// private static ArrayList<Task> taskHistory;
	private static Stack<Command> undoCommandHistory;
	private static Stack<Command> redoCommandHistory;
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
		// allEvents = new ArrayList<Task>();
		// allDeadlines = new ArrayList<Task>();
		// allFloatingTasks = new ArrayList<Task>();
		// allTasks = new ArrayList<Task>();

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
			undoCommandHistory.push(inputCommand);
			updateTaskLists();
			fillEvents();
			success = true;
			break;
		case ADD_DEADLINE:
			fileStorage.writeTask(inputCommand.getTask());
			undoCommandHistory.push(inputCommand);
			updateTaskLists();
			fillDeadlines();
			success = true;
			break;
		case ADD_FLOATING:
			fileStorage.writeTask(inputCommand.getTask());
			undoCommandHistory.push(inputCommand);
			updateTaskLists();
			fillFloatings();
			success = true;
			break;
		case UPDATE:
			update(inputCommand);
			updateTaskLists();
			success = true;
			break;
		case DELETE:
			deleteTask(inputCommand);
			success = true;
			break;
		case SEARCH:
			search(inputCommand);
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
		case EXIT:
			mainApp.exit();
			success = true;
			break;
		default:
			break;
		}
		return success;
	}


	private void search(Command inputCommand) {
		Search searchCommand = (Search)inputCommand;
		ArrayList<String> searchCriterias = searchCommand.getSearchStrings();
		fillEvents(fileStorage.searchEventTask(searchCriterias.get(0)));
		fillDeadlines(fileStorage.searchDeadlineTask(searchCriterias.get(0)));
		fillFloatings(fileStorage.searchFloatingTask(searchCriterias.get(0)));
	}

	private boolean redo() {
		if (redoCommandHistory.size()== 0)
			return false;
		Command redoCommand = redoCommandHistory.pop();
		Command.CommandType redoCommandType = redoCommand.getCommandType();
		switch (redoCommandType) {
		case ADD_EVENT:
		case ADD_DEADLINE:
		case ADD_FLOATING:
			undoCommandHistory.push(redoCommand);
			fileStorage.writeTask(redoCommand.getTask());
			updateRespectiveGUICol(redoCommand.getTask().getClass().getName());
			updateTaskLists();
			break;
		case DELETE:
			undoCommandHistory.push(redoCommand);
			fileStorage.deleteTask(((Delete) redoCommand).getTaskDeleted());
			updateRespectiveGUICol((((Delete) redoCommand).getTaskDeleted().getClass().getName()));
			updateTaskLists();
			break;
		case UPDATE:
			Update redoUpdate = ((Update) redoCommand);
			UpdateTask oldTaskUpdated = redoUpdate.getTaskToUpdate();
			UpdateTask newUpdatedTask = redoUpdate.getUpdatedTask();
			Update undoUpdate = new Update(taskToUpdateTask(newUpdatedTask), taskToUpdateTask(oldTaskUpdated));
			undoUpdate.setCurrentTask(redoUpdate.getCurrentTask());
			undoUpdate.setUpdateTask(redoUpdate.getUpdateTask());
			//redoCommandHistory.push(redoUpdate);	
			undoCommandHistory.push(undoUpdate);
			// redoCommandHistory.push(undoUpdate);
			fileStorage.deleteTask(undoUpdate.getCurrentTask());
			fileStorage.writeTask(undoUpdate.getUpdateTask());
			//fileStorage.deleteTask((Task) newUpdatedTask);
			//fileStorage.writeTask((Task) oldTaskUpdated);
			updateTaskLists();
			updateRespectiveGUICol(newUpdatedTask.getClass().getName());
			updateRespectiveGUICol(oldTaskUpdated.getClass().getName());
			updateTaskLists();
			break;
		default:
			break;
		}

		return false;
	}


	private boolean undo() {
		if (undoCommandHistory.size()==0)
			return false;
		Command undoCommand = undoCommandHistory.pop();
		Command.CommandType undoCommandType = undoCommand.getCommandType();
		switch (undoCommandType) {
		case ADD_EVENT:
		case ADD_DEADLINE:
		case ADD_FLOATING:
			redoCommandHistory.push(undoCommand);
			fileStorage.deleteTask(undoCommand.getTask());
			updateRespectiveGUICol(undoCommand.getTask().getClass().getName());
			updateTaskLists();
			break;
		case DELETE:
			redoCommandHistory.push(undoCommand);
			fileStorage.writeTask(((Delete) undoCommand).getTaskDeleted());
			updateTaskLists();
			updateRespectiveGUICol((((Delete) undoCommand).getTaskDeleted().getClass().getName()));
			break;
		case UPDATE:
			Update undoUpdate = ((Update) undoCommand);
			Task oldTaskUpdated = undoUpdate.getTaskToUpdate();
			Task newUpdatedTask = undoUpdate.getUpdatedTask();
			Update redoUpdate = new Update(taskToUpdateTask(newUpdatedTask), taskToUpdateTask(oldTaskUpdated));
			redoUpdate.setCurrentTask(undoUpdate.getCurrentTask());
			redoUpdate.setUpdateTask(undoUpdate.getUpdateTask());
			redoCommandHistory.push(redoUpdate);
			// redoCommandHistory.push(undoUpdate);
			//fileStorage.deleteTask(undoUpdate.getUpdateTask());
			fileStorage.writeTask(undoUpdate.getCurrentTask());
			fileStorage.deleteTask(undoUpdate.getUpdateTask());
			System.out.println(undoUpdate.getCurrentTask().toString());
			System.out.println(undoUpdate.getUpdateTask().toString());
			updateRespectiveGUICol(undoUpdate.getCurrentTask().getClass().getName());
			updateRespectiveGUICol(undoUpdate.getUpdateTask().getClass().getName());
			updateTaskLists();
			break;
		default:
			break;
		}
		return true;
	}

	
	private void update(Command inputCommand) {
		Update updateCommand = (Update) inputCommand;
		UpdateTask processUpdate = (UpdateTask) updateCommand.getTaskToUpdate();
		System.out.println(processUpdate.getSearchString());
		Task taskToUpdate;
		taskToUpdate = fileStorage.absoluteSearch(processUpdate.getSearchString()).get(0);
		fileStorage.deleteTask(taskToUpdate);
		updateRespectiveGUICol(taskToUpdate.getClass().getName());
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
		Update output = new Update( taskToUpdateTask(taskToUpdate),  taskToUpdateTask(updatedTask));
		output.setCurrentTask(taskToUpdate);
		output.setUpdateTask(updatedTask);
		fileStorage.writeTask(updatedTask);
		updateRespectiveGUICol(updatedTask.getClass().getName());
		undoCommandHistory.push(output);
		updateTaskLists();
	}

	private UpdateTask taskToUpdateTask(Task current){
		UpdateTask convertedTask = new UpdateTask(current.getDescription());
		convertedTask.setDescription(current.getDescription());
		String taskType = current.getClass().getName();
		switch(taskType){
		case "main.Event":
			convertedTask.setStartDate(((Event) current).getStartDate());
			convertedTask.setStartTime(((Event) current).getStartTime());
			convertedTask.setEndDate(((Event) current).getEndDate());
			convertedTask.setEndTime(((Event) current).getEndTime());
			break;
		case "main.Deadline":
			convertedTask.setEndDate(((Deadline) current).getEndDate());
			convertedTask.setEndTime(((Deadline) current).getEndTime());
			break;
		}
		return convertedTask;
	}
	
	private void deleteTask(Command inputCommand) {
		Delete deleteCommand = ((Delete) inputCommand);
		Task taskToDelete = null;
		updateTaskLists();
		if(deleteCommand.hasTaskID()){
			if(deleteCommand.getTaskID()<=allEvents.size()){
				String taskDesc = allEvents.get(deleteCommand.getTaskID()-1).getDescription();
				System.out.println(Arrays.toString(allEvents.toArray()));
				System.out.println(taskDesc);
				//System.out.println(fileStorage.absoluteSearch(taskDesc).toString());
				//taskToDelete = fileStorage.absoluteSearch(taskDesc).get(0);
				taskToDelete = fileStorage.searchAllTask(taskDesc).get(0);
			}else if(deleteCommand.getTaskID()<=allEvents.size()+allDeadlines.size()){
				int indexToDelete =deleteCommand.getTaskID() - allEvents.size()-1; 
				//taskToDelete = fileStorage.absoluteSearch(allDeadlines.get(indexToDelete).getDescription()).get(0);
				taskToDelete = fileStorage.searchAllTask(allDeadlines.get(indexToDelete).getDescription()).get(0);
			}else{
				int indexToDelete =deleteCommand.getTaskID() - allEvents.size() -allDeadlines.size()-1;
				//taskToDelete = fileStorage.absoluteSearch(allFloatingTasks.get(indexToDelete).getDescription()).get(0);
				taskToDelete = fileStorage.searchAllTask(allFloatingTasks.get(indexToDelete).getDescription()).get(0);
			}
		}else if(deleteCommand.hasDeleteString()){
			taskToDelete = fileStorage.absoluteSearch(deleteCommand.getDeleteString()).get(0);
		}
		String deleteTaskType = taskToDelete.getClass().getName();
		updateRespectiveGUICol(deleteTaskType);
		fileStorage.deleteTask(taskToDelete);
		deleteCommand.setTaskDeleted(taskToDelete);
		undoCommandHistory.push(deleteCommand);
		updateRespectiveGUICol(taskToDelete.getClass().getName());
		updateTaskLists();
	}

	private void updateRespectiveGUICol(String taskType) {
		switch(taskType){
		case "main.Event" :
			fillEvents();
			break;
		case "main.Deadline":
			fillDeadlines();
			break;
		case "main.Floating":
			fillFloatings();
			break;
		default :
			fillFloatings();
			fillDeadlines();
			fillEvents();
			//System.out.println(taskType);
			break;	
		}
	}


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
		//events.setAll(fileStorage.readEventTask());
		 events.setAll(allEvents);
	}

	public void fillDeadlines() {
		//deadlines.setAll(fileStorage.readDeadlineTask());
		 deadlines.setAll(allDeadlines);
	}

	public void fillFloatings() {
		//floatings.setAll(fileStorage.readFloatingTask());
		 floatings.setAll(allFloatingTasks);
	}
	
	public void fillEvents(ArrayList<Task> searchResult) {
		//events.setAll(fileStorage.readEventTask());
		 events.setAll(searchResult);
	}
	
	public void fillDeadlines(ArrayList<Task> searchResult) {
		//events.setAll(fileStorage.readEventTask());
		 events.setAll(searchResult);
	}
	
	public void fillFloatings(ArrayList<Task> searchResult) {
		//floatings.setAll(fileStorage.readFloatingTask());
		 floatings.setAll(searchResult);
	}
}
