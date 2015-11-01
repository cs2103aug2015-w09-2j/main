package main;

import java.text.ParseException;
import java.util.*;

import javafx.collections.*;

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
	// private static UserInterface UI = new UserInterface(); // [teddy] this
	// will
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
	private static ArrayList<Task> searchResultEvents;
	private static ArrayList<Task> searchResultDeadlines;
	private static ArrayList<Task> searchResultFloatingTasks;
	private static ArrayList<Task> searchResultTasks;
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
	}

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
		case DONE:
			done(inputCommand);
			success = true;
			break;
		case DELETE:
			deleteTask(inputCommand);
			success = true;
			break;
		case CLEAR:
			clearAllTask(inputCommand);
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
			display();
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

	private void clearAllTask(Command inputCommand) {
		Clear clearAllTask = (Clear) inputCommand;
		ArrayList<Task> taskCleared = new ArrayList<Task>();
		for (Task currentTask : allEvents) {
			fileStorage.deleteTask(currentTask);
			taskCleared.add(currentTask);
		}
		for (Task currentTask : allDeadlines) {
			fileStorage.deleteTask(currentTask);
			taskCleared.add(currentTask);
		}
		for (Task currentTask : allFloatingTasks) {
			fileStorage.deleteTask(currentTask);
			taskCleared.add(currentTask);
		}
		updateTaskLists();
		updateRespectiveGUICol("ALL");
		clearAllTask.setTaskCleared(taskCleared);
		undoCommandHistory.push(clearAllTask);
	}

	private void done(Command inputCommand) {
		updateTaskLists();
		Set<Integer> tasksDone = ((Done) inputCommand).getTaskIDs();
		ArrayList<Task> tasksSetDone = new ArrayList<Task>();
		for (Integer i : tasksDone) {
			tasksSetDone.add(markDone(i));
		}
		updateTaskLists();
		updateRespectiveGUICol("ALL");
		undoCommandHistory.push(new Done(tasksDone, tasksSetDone));
		System.out.println(Arrays.toString(tasksDone.toArray()));
	}

	private Task markDone(Integer i) {
		if (i <= allEvents.size()) {
			fileStorage.writeDoneTask(allEvents.get(i - 1));
			fileStorage.deleteTask(allEvents.get(i - 1));
			System.out.println("from event");
			return allEvents.get(i - 1);
		} else if (i <= allEvents.size() + allDeadlines.size()) {
			fileStorage.writeDoneTask(allDeadlines.get(i - allEvents.size() - 1));
			fileStorage.deleteTask(allDeadlines.get(i - allEvents.size() - 1));
			System.out.println("from deadline");
			return allDeadlines.get(i - allEvents.size() - 1);
		} else {
			fileStorage.writeDoneTask(allFloatingTasks.get(i - allEvents.size() - allDeadlines.size() - 1));
			fileStorage.deleteTask(allFloatingTasks.get(i - allEvents.size() - allDeadlines.size() - 1));
			System.out.println("from floatings");
			return allFloatingTasks.get(i - allEvents.size() - allDeadlines.size() - 1);
		}
	}

	private void display() {
		updateTaskLists();
		fillEvents();
		fillFloatings();
		fillDeadlines();
	}

	private void search(Command inputCommand) {
		Search searchCommand = (Search) inputCommand;
		ArrayList<String> searchCriterias = searchCommand.getSearchStrings();
		if (searchCriterias.size() == 1) {
			if(searchCriterias.get(0).equals("done")){
				ArrayList<Task> doneTasks = fileStorage.readDoneTask();
				allEvents.clear();
				allDeadlines.clear();
				allFloatingTasks.clear();
				for(Task currentTask: doneTasks){
					switch(currentTask.getClass().getName()){
					case "main.Floating":
						allFloatingTasks.add(currentTask);
						break;
					case "main.Deadline":
						allDeadlines.add(currentTask);
						break;
					case "main.Event":
						allEvents.add(currentTask);
						break;
					}
					updateRespectiveGUICol("ALL");
				}
				
			}else{
			allEvents = fileStorage.searchEventTask(searchCriterias.get(0));
			fillEvents();
			allDeadlines = fileStorage.searchDeadlineTask(searchCriterias.get(0));
			fillDeadlines();
			allFloatingTasks = fileStorage.searchFloatingTask(searchCriterias.get(0));
			fillFloatings();}
		} else if (searchCriterias.size() > 1) {
			allEvents = fileStorage.searchEventTask(searchCriterias.get(0), searchCriterias.get(1));
			fillEvents();
			allDeadlines = fileStorage.searchDeadlineTask(searchCriterias.get(0), searchCriterias.get(1));
			fillDeadlines();
			allFloatingTasks = fileStorage.searchFloatingTask(searchCriterias.get(0), searchCriterias.get(1));
			fillFloatings();
		} else {
			System.out.println("Incorrect Search string");
		}
	}

	private boolean redo() {
		if (redoCommandHistory.size() == 0)
			return false;
		Command redoCommand = redoCommandHistory.pop();
		Command.CommandType redoCommandType = redoCommand.getCommandType();
		switch (redoCommandType) {
		case ADD_EVENT:
		case ADD_DEADLINE:
		case ADD_FLOATING:
			undoCommandHistory.push(redoCommand);
			fileStorage.writeTask(redoCommand.getTask());
			updateTaskLists();
			updateRespectiveGUICol(redoCommand.getTask().getClass().getName());
			break;
		case DELETE:
			undoCommandHistory.push(redoCommand);
			fileStorage.deleteTask(((Delete) redoCommand).getTaskDeleted());
			updateTaskLists();
			updateRespectiveGUICol((((Delete) redoCommand).getTaskDeleted().getClass().getName()));
			break;
		case UPDATE:
			Update redoUpdate = ((Update) redoCommand);
			UpdateTask oldTaskUpdated = redoUpdate.getTaskToUpdate();
			UpdateTask newUpdatedTask = redoUpdate.getUpdatedTask();
			Update undoUpdate = new Update(taskToUpdateTask(newUpdatedTask), taskToUpdateTask(oldTaskUpdated));
			undoUpdate.setCurrentTask(redoUpdate.getCurrentTask());
			undoUpdate.setUpdateTask(redoUpdate.getUpdateTask());
			// redoCommandHistory.push(redoUpdate);
			undoCommandHistory.push(undoUpdate);
			// redoCommandHistory.push(undoUpdate);
			fileStorage.deleteTask(undoUpdate.getCurrentTask());
			fileStorage.writeTask(undoUpdate.getUpdateTask());
			// fileStorage.deleteTask((Task) newUpdatedTask);
			// fileStorage.writeTask((Task) oldTaskUpdated);
			updateTaskLists();
			updateRespectiveGUICol(newUpdatedTask.getClass().getName());
			updateRespectiveGUICol(oldTaskUpdated.getClass().getName());
			// updateTaskLists();
			break;
		case DONE:
			redoDone(redoCommand);
			undoCommandHistory.push(redoCommand);
			updateRespectiveGUICol("ALL");
			break;
		case CLEAR:
			redoClear(redoCommand);
			undoCommandHistory.push(redoCommand);
			updateRespectiveGUICol("ALL");
			break;
		default:
			break;
		}

		return false;
	}

	private void redoClear(Command redoCommand) {
		Clear redoClear = ((Clear) redoCommand);
		ArrayList<Task> taskCleared = redoClear.getTaskCleared();
		for (Task currentTask : taskCleared) {
			fileStorage.deleteTask(currentTask);
		}
		updateTaskLists();
	}

	private void redoDone(Command redoCommand) {
		ArrayList<Task> redoDoneTask = ((Done) redoCommand).getTasksSetDone();
		for (Task currentTask : redoDoneTask) {
			fileStorage.writeDoneTask(currentTask);
			fileStorage.deleteTask(currentTask);
			updateTaskLists();
		}
	}

	private boolean undo() {
		if (undoCommandHistory.size() == 0)
			return false;
		Command undoCommand = undoCommandHistory.pop();
		Command.CommandType undoCommandType = undoCommand.getCommandType();
		switch (undoCommandType) {
		case ADD_EVENT:
		case ADD_DEADLINE:
		case ADD_FLOATING:
			redoCommandHistory.push(undoCommand);
			fileStorage.deleteTask(undoCommand.getTask());
			updateTaskLists();
			updateRespectiveGUICol(undoCommand.getTask().getClass().getName());
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
			// fileStorage.deleteTask(undoUpdate.getUpdateTask());
			// undoUpdate.getCurrentTask().toString();
			System.out.println("Current " + undoUpdate.getCurrentTask().toString());
			System.out.println("update " + undoUpdate.getUpdateTask().toString());
			fileStorage.writeTask(undoUpdate.getCurrentTask());
			fileStorage.deleteTask(undoUpdate.getUpdateTask());
			// System.out.println(undoUpdate.getCurrentTask().toString());
			// System.out.println(undoUpdate.getUpdateTask().toString());
			updateTaskLists();
			updateRespectiveGUICol("ALL");
			break;
		case DONE:
			undoDone(undoCommand);
			redoCommandHistory.push(undoCommand);
			updateRespectiveGUICol("ALL");
			break;
		case CLEAR:
			undoClear(undoCommand);
			redoCommandHistory.push(undoCommand);
			updateRespectiveGUICol("ALL");
			break;
		default:
			break;
		}
		return true;
	}

	private void undoClear(Command undoCommand) {
		Clear undoClear = ((Clear) undoCommand);
		ArrayList<Task> taskCleared = undoClear.getTaskCleared();
		for (Task currentTask : taskCleared) {
			fileStorage.writeTask(currentTask);
		}
		updateTaskLists();
	}

	private void undoDone(Command undoCommand) {
		Done doneCommand = ((Done) undoCommand);
		for (Task currentTask : doneCommand.getTasksSetDone()) {
			fileStorage.deleteDoneTask(currentTask);
			fileStorage.writeTask(currentTask);
			updateTaskLists();
		}
		updateRespectiveGUICol("ALL");
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
			System.out.println("udpate is event");
		} else if (processUpdate.hasEndDate()) {
			updatedTask = new Deadline(processUpdate.getDescription(), processUpdate.getEndDate(),
					processUpdate.getEndTime());
			System.out.println("udpate is dealdine");
		} else {
			updatedTask = new Floating(processUpdate.getDescription());
			System.out.println("floating");
		}
		Update output = new Update(taskToUpdateTask(taskToUpdate), taskToUpdateTask(updatedTask));
		output.setCurrentTask(taskToUpdate);
		output.setUpdateTask(updatedTask);
		fileStorage.writeTask(updatedTask);
		updateTaskLists();
		updateRespectiveGUICol("ALL");
		undoCommandHistory.push(output);

	}

	/*
	 * private void update(Command inputCommand) { Update updateCommand =
	 * (Update) inputCommand; UpdateTask processUpdate = (UpdateTask)
	 * updateCommand.getTaskToUpdate();
	 * System.out.println(processUpdate.getSearchString()); Task taskToUpdate;
	 * taskToUpdate =
	 * fileStorage.absoluteSearch(processUpdate.getSearchString()).get(0);
	 * fileStorage.deleteTask(taskToUpdate); Task updatedTask ; String taskType
	 * = taskToUpdate.getClass().getName(); System.out.println(taskType);
	 * System.out.println("To update " + taskToUpdate.toString());
	 * switch(taskType){ case "main.Event" : updatedTask = taskToUpdate;
	 * System.out.println("To update " + taskToUpdate.toString() +
	 * "Task updated " + updatedTask.toString());
	 * if(processUpdate.hasStartDate()){ ((Event)
	 * updatedTask).setStartDate(processUpdate.getStartDate()); }
	 * if(processUpdate.hasEndDate()){ ((Event)
	 * updatedTask).setEndDate(processUpdate.getEndDate()); }
	 * if(processUpdate.hasDescription()){ ((Event)
	 * updatedTask).setDescription(processUpdate.getDescription()); }
	 * 
	 * //updatedTask = taskToUpdate; break; case "main.Deadline": updatedTask =
	 * taskToUpdate; if(processUpdate.hasStartDate()){ ((Event)
	 * updatedTask).setStartDate(processUpdate.getStartDate()); }
	 * if(processUpdate.hasEndDate()){ System.out.println("has end date");
	 * ((Deadline) updatedTask).setEndDate(processUpdate.getEndDate()); }
	 * if(processUpdate.hasDescription()){
	 * updatedTask.setDescription(processUpdate.getDescription()); }
	 * //updatedTask = taskToUpdate; System.out.println("To update " +
	 * taskToUpdate.toString() + "Task updated " + updatedTask.toString());
	 * break; case "main.Floating": updatedTask = taskToUpdate;
	 * if(processUpdate.hasStartDate()){ ((Event)
	 * updatedTask).setStartDate(processUpdate.getStartDate()); }
	 * if(processUpdate.hasEndDate()){ ((Deadline)
	 * updatedTask).setEndDate(processUpdate.getEndDate()); }
	 * if(processUpdate.hasDescription()){
	 * updatedTask.setDescription(processUpdate.getDescription()); }
	 * //updatedTask = taskToUpdate; System.out.println("To update " +
	 * taskToUpdate.toString() + "Task updated " + updatedTask.toString() +
	 * "within floaitng"); break; default: updatedTask = taskToUpdate; break; }
	 * 
	 * System.out.println("To update " + taskToUpdate.toString() +
	 * "Task updated " + updatedTask.toString() + "diff"); Update output = new
	 * Update(taskToUpdateTask(taskToUpdate), taskToUpdateTask(updatedTask));
	 * System.out.println("To update " + taskToUpdate.toString() +
	 * "Task updated " + updatedTask.toString());
	 * output.setCurrentTask(taskToUpdate);
	 * 
	 * output.setUpdateTask(updatedTask); fileStorage.writeTask(updatedTask);
	 * updateTaskLists(); updateRespectiveGUICol("ALL");
	 * undoCommandHistory.push(output); }
	 */

	private UpdateTask taskToUpdateTask(Task current) {
		UpdateTask convertedTask = new UpdateTask(current.getDescription());
		convertedTask.setDescription(current.getDescription());
		String taskType = current.getClass().getName();
		switch (taskType) {
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
		if (deleteCommand.hasTaskID()) {
			if (deleteCommand.getTaskID() <= allEvents.size()) {
				String taskDesc = allEvents.get(deleteCommand.getTaskID() - 1).getDescription();
				System.out.println(Arrays.toString(allEvents.toArray()));
				System.out.println(taskDesc);
				// System.out.println(fileStorage.absoluteSearch(taskDesc).toString());
				taskToDelete = fileStorage.absoluteSearch(taskDesc).get(0);
				// taskToDelete = fileStorage.searchAllTask(taskDesc).get(0);
			} else if (deleteCommand.getTaskID() <= allEvents.size() + allDeadlines.size()) {
				int indexToDelete = deleteCommand.getTaskID() - allEvents.size() - 1;
				taskToDelete = fileStorage.absoluteSearch(allDeadlines.get(indexToDelete).getDescription()).get(0);
				// taskToDelete =
				// fileStorage.searchAllTask(allDeadlines.get(indexToDelete).getDescription()).get(0);
			} else {
				int indexToDelete = deleteCommand.getTaskID() - allEvents.size() - allDeadlines.size() - 1;
				taskToDelete = fileStorage.absoluteSearch(allFloatingTasks.get(indexToDelete).getDescription()).get(0);
				// taskToDelete =
				// fileStorage.searchAllTask(allFloatingTasks.get(indexToDelete).getDescription()).get(0);
			}
		} else if (deleteCommand.hasDeleteString()) {
			taskToDelete = fileStorage.absoluteSearch(deleteCommand.getDeleteString()).get(0);
		}
		String deleteTaskType = taskToDelete.getClass().getName();
		fileStorage.deleteTask(taskToDelete);
		deleteCommand.setTaskDeleted(taskToDelete);
		updateTaskLists();
		updateRespectiveGUICol(deleteTaskType);
		undoCommandHistory.push(deleteCommand);
		updateRespectiveGUICol(taskToDelete.getClass().getName());
		updateTaskLists();
	}

	private void updateRespectiveGUICol(String taskType) {
		switch (taskType) {
		case "main.Event":
			fillEvents();
			break;
		case "main.Deadline":
			fillDeadlines();
			break;
		case "main.Floating":
			fillFloatings();
			break;
		default:
			fillFloatings();
			fillDeadlines();
			fillEvents();
			// System.out.println(taskType);
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
		// events.setAll(fileStorage.readEventTask());
		events.setAll(allEvents);
	}

	public void fillDeadlines() {
		// deadlines.setAll(fileStorage.readDeadlineTask());
		deadlines.setAll(allDeadlines);
	}

	public void fillFloatings() {
		// floatings.setAll(fileStorage.readFloatingTask());
		floatings.setAll(allFloatingTasks);
	}

	public void fillEvents(ArrayList<Task> searchResult) {
		// events.setAll(fileStorage.readEventTask());
		events.setAll(searchResult);
	}

	public void fillDeadlines(ArrayList<Task> searchResult) {
		// events.setAll(fileStorage.readEventTask());
		events.setAll(searchResult);
	}

	public void fillFloatings(ArrayList<Task> searchResult) {
		// floatings.setAll(fileStorage.readFloatingTask());
		floatings.setAll(searchResult);
	}
}
