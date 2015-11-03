package main;

import java.text.ParseException;
import java.util.*;

import javafx.beans.property.IntegerProperty;
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

	// ------------------- To interact with GUI [added by teddy]
	// ------------------
	private MainApp mainApp;
	private IntegerProperty displayStatusCode; // represent current display
												// status
	private String searchKeyword; // represent search keyword -> change this
									// whenever a search is done
	private ObservableList<Task> events;
	private ObservableList<Task> deadlines;
	private ObservableList<Task> floatings;

	// ----------------------------------------------------------------------------
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
			// output = true;
			output = executeCommand(command);
			return output;
		}
	}

	private void updateTaskLists() {
		allEvents = fileStorage.readEventTask();
		allDeadlines = fileStorage.readDeadlineTask();
		allFloatingTasks = fileStorage.readFloatingTask();
		allEvents = filterOverdueTask(allEvents);
		allDeadlines = filterOverdueTask(allDeadlines);
		Collections.sort(allEvents);
		Collections.sort(allDeadlines);
		Collections.sort(allFloatingTasks);
		allTasks = fileStorage.readAllTask();

	}

	private ArrayList<Task> filterOverdueTask(ArrayList<Task> tasks) {
		ArrayList<Task> taskList = new ArrayList<Task>();
		for (Task currentTask : tasks) {
			switch (currentTask.getClass().getName()) {
			case "main.Event":
				int compareTo = 0;
				try {
					compareTo = ((Event) currentTask).getEndDate()
							.compareTo(new DateClass(DateHandler.getIntDayNow(), DateHandler.getIntMonthNow()));
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (compareTo > 0) {
					taskList.add(currentTask);
				} else if (compareTo == 0) {
					compareTo = ((Event) currentTask).getEndTime()
							.compareTo(new TimeClass(TimeHandler.getHourNow(), TimeHandler.getMinuteNow()));
					if (compareTo > 0) {
						taskList.add(currentTask);
					} else {
						fileStorage.writeOverdueTask(currentTask);
						fileStorage.deleteTask(currentTask);
					}
				} else {
					fileStorage.writeOverdueTask(currentTask);
					fileStorage.deleteTask(currentTask);
				}
				break;
			case "main.Deadline":
				compareTo = 0;
				try {
					compareTo = ((Deadline) currentTask).getEndDate()
							.compareTo(new DateClass(DateHandler.getIntDayNow(), DateHandler.getIntMonthNow()));
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (compareTo > 0) {
					taskList.add(currentTask);
				} else if (compareTo == 0) {
					compareTo = ((Deadline) currentTask).getEndTime()
							.compareTo(new TimeClass(TimeHandler.getHourNow(), TimeHandler.getMinuteNow()));
					if (compareTo > 0) {
						taskList.add(currentTask);
					} else {
						fileStorage.writeOverdueTask(currentTask);
						fileStorage.deleteTask(currentTask);
					}
				} else {
					fileStorage.writeOverdueTask(currentTask);
					fileStorage.deleteTask(currentTask);
				}
				break;
			default:
				break;
			}
		}
		return taskList;
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
			addEvent(inputCommand);
			success = true;
			break;
		case ADD_DEADLINE:
			addDeadline(inputCommand);
			success = true;
			break;
		case ADD_FLOATING:
			addFloating(inputCommand);
			success = true;
			break;
		case UPDATE:
			update(inputCommand);
			updateTaskLists();
			success = true;
			break;
		case DONE:
			done(inputCommand);
			displayStatusCode.set(1);
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
			displayStatusCode.set(3);
			success = true;
			break;
		case UNDO:
			success = undo();
			break;
		case REDO:
			success = redo();
			break;
		case DISPLAY:
			display();
			displayStatusCode.set(0);
			success = true;
			break;
		case SAVE:
			success = save(inputCommand);
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

	private boolean save(Command inputCommand) {
		fileStorage.setFilePath(((Save) inputCommand).getPathLocation());
		return true;
	}

	private void addFloating(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		undoCommandHistory.push(inputCommand);
		updateTaskLists();
		fillFloatings();
	}

	private void addDeadline(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		undoCommandHistory.push(inputCommand);
		updateTaskLists();
		fillDeadlines();
	}

	private void addEvent(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		undoCommandHistory.push(inputCommand);
		updateTaskLists();
		fillEvents();
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
			if (searchCriterias.get(0).equals("done")) {
				searchDone();
			} else {
				searchForDescription(searchCriterias);
			}
		} else if (searchCriterias.size() > 1) {
			searchForDescriptionAndDate(searchCriterias);
		} else {
			System.out.println("Incorrect Search string");
		}
		System.out.println("Sizes after search: " + "Events" + allEvents.size() + "Deadlines" + allDeadlines.size()
				+ "floatings" + allFloatingTasks.size());
	}

	private void searchForDescriptionAndDate(ArrayList<String> searchCriterias) {
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		searchKeyword = searchCriterias.get(1) + " on " + searchCriterias.get(0);
		allEvents = fileStorage.searchEventTask(searchCriterias.get(0), searchCriterias.get(1));
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTask(searchCriterias.get(0), searchCriterias.get(1));
		fillDeadlines();
		allFloatingTasks = fileStorage.searchFloatingTask(searchCriterias.get(0), searchCriterias.get(1));
		fillFloatings();
	}

	private void searchForDescription(ArrayList<String> searchCriterias) {
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		searchKeyword = searchCriterias.get(0);
		allEvents = fileStorage.searchEventTask(searchCriterias.get(0));
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTask(searchCriterias.get(0));
		fillDeadlines();
		allFloatingTasks = fileStorage.searchFloatingTask(searchCriterias.get(0));
		fillFloatings();
	}

	private void searchDone() {
		ArrayList<Task> doneTasks = fileStorage.readDoneTask();
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		for (Task currentTask : doneTasks) {
			switch (currentTask.getClass().getName()) {
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
			redoAddCommand(redoCommand);
			break;
		case DELETE:
			redoDeleteCommand(redoCommand);
			break;
		case UPDATE:
			redoUpdateCommand(redoCommand);
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

	private void redoUpdateCommand(Command redoCommand) {
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
	}

	private void redoDeleteCommand(Command redoCommand) {
		undoCommandHistory.push(redoCommand);
		for (Task currentTask : ((Delete) redoCommand).getTaskDeleted()) {
			fileStorage.deleteTask(currentTask);
		}
		updateTaskLists();
		updateRespectiveGUICol("ALL");
	}

	private void redoAddCommand(Command redoCommand) {
		undoCommandHistory.push(redoCommand);
		fileStorage.writeTask(redoCommand.getTask());
		updateTaskLists();
		updateRespectiveGUICol(redoCommand.getTask().getClass().getName());
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
			undoAddCommand(undoCommand);
			break;
		case DELETE:
			undoDeleteCommand(undoCommand);
			break;
		case UPDATE:
			undoUpdateCommand(undoCommand);
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

	private void undoUpdateCommand(Command undoCommand) {
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
	}

	private void undoDeleteCommand(Command undoCommand) {
		redoCommandHistory.push(undoCommand);
		for (Task currentTask : ((Delete) undoCommand).getTaskDeleted()) {
			fileStorage.writeTask(currentTask);
		}
		// fileStorage.writeTask(((Delete) undoCommand).getTaskDeleted());
		updateTaskLists();
		updateRespectiveGUICol("ALL");
	}

	private void undoAddCommand(Command undoCommand) {
		redoCommandHistory.push(undoCommand);
		fileStorage.deleteTask(undoCommand.getTask());
		updateTaskLists();
		updateRespectiveGUICol(undoCommand.getTask().getClass().getName());
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
		ArrayList<Task> taskToDelete = new ArrayList<Task>();
		if (deleteCommand.hasTaskIDs()) {
			System.out.println(Arrays.toString(deleteCommand.getTaskIDs().toArray()));
			for (Integer i : deleteCommand.getTaskIDs()) {
				// System.out.print(i+" ");
				Task a = deleteSingleTask(i);
				System.out.println(i + " " + a.toString() + " ");
				taskToDelete.add(a);
			}
		} else if (deleteCommand.hasDeleteString()) {
			Task taskDelete = fileStorage.absoluteSearch(deleteCommand.getDeleteString()).get(0);
			fileStorage.deleteTask(taskDelete);
			taskToDelete.add(taskDelete);
		}
		deleteCommand.setTaskDeleted(taskToDelete);
		updateTaskLists();
		undoCommandHistory.push(deleteCommand);
		updateRespectiveGUICol("ALL");
	}

	private Task deleteSingleTask(int taskIndex) {
		if (taskIndex <= allEvents.size()) {
			fileStorage.deleteTask(allEvents.get(taskIndex - 1));
			return allEvents.get(taskIndex - 1);
		} else if (taskIndex <= (allEvents.size() + allDeadlines.size())) {
			int indexToDelete = taskIndex - allEvents.size() - 1;
			fileStorage.deleteTask(allDeadlines.get(indexToDelete));
			return allDeadlines.get(indexToDelete);
		} else {
			int indexToDelete = taskIndex - allEvents.size() - allDeadlines.size() - 1;
			fileStorage.deleteTask(allFloatingTasks.get(indexToDelete));
			return allFloatingTasks.get(indexToDelete);
		}
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
	 * Codes below are to interact with GUI Added by teddy
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Initialize the tasks
	 * 
	 * @param tasks
	 *            Added by teddy
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

	/*
	 * displayStatusCode is an integer representing the state of the display:
	 * State = {ONGOING, DONE, OVERDUE, SEARCH, EVENTS, DEADLINES, FLOATINGS}
	 *
	 * Change the displayStatusCode accordingly with the commands executed:
	 * displayStatusCode.setValue(StatusListener.Status.<State>.getCode());
	 *
	 * Replace State by one of the state in the set above
	 */
	public void setDisplayState(IntegerProperty displayStatusCode) {
		this.displayStatusCode = displayStatusCode;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}
}
