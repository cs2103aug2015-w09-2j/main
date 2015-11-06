package main;

import java.text.ParseException;
import java.util.*;

import javafx.beans.property.BooleanProperty;
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
	private BooleanProperty hasNewOverdueTask;
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
			success = addEvent(inputCommand);
			break;
		case ADD_DEADLINE:
			success = addDeadline(inputCommand);
			break;
		case ADD_FLOATING:
			success = addFloating(inputCommand);
			break;
		case UPDATE:
			success = update(inputCommand);
			// updateTaskLists();
			break;
		case DONE:
			success = done(inputCommand);
			// displayStatusCode.set(1);
			// success = true;
			break;
		case DELETE:
			success = deleteTask(inputCommand);
			break;
		case CLEAR:
			success = clearAllTask(inputCommand);
			break;
		case SEARCH:
			success = search(inputCommand);
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
			// displayStatusCode.set(0);
			success = display(inputCommand);
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

	private boolean addFloating(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		undoCommandHistory.push(inputCommand);
		updateTaskLists();
		fillFloatings();
		return true;
	}

	private boolean addDeadline(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		undoCommandHistory.push(inputCommand);
		updateTaskLists();
		fillDeadlines();
		return true;
	}

	private boolean addEvent(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		undoCommandHistory.push(inputCommand);
		updateTaskLists();
		fillEvents();
		return true;
	}

	private boolean clearAllTask(Command inputCommand) {
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
		return true;
	}

	private boolean done(Command inputCommand) {
		updateTaskLists();
		Set<Integer> tasksDone = ((Done) inputCommand).getTaskIDs();
		ArrayList<Task> tasksSetDone = new ArrayList<Task>();
		for (Integer i : tasksDone) {
			if (i <= (allEvents.size() + allDeadlines.size() + allFloatingTasks.size())) {
				tasksSetDone.add(markDone(i));
				// return true;
			} else {
				return false;
			}
		}
		updateTaskLists();
		updateRespectiveGUICol("ALL");
		undoCommandHistory.push(new Done(tasksDone, tasksSetDone));
		System.out.println(Arrays.toString(tasksDone.toArray()));
		return true;
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

	private boolean display(Command inputCommand) {
		Display displayCommand = (Display) inputCommand;
		if (displayCommand.getDisplayString().equals("ongoing")) {
			updateTaskLists();
			fillEvents();
			fillFloatings();
			fillDeadlines();
			displayStatusCode.set(0);
			return true;
		} else if (displayCommand.getDisplayString().equals("done")) {
			readDone();
			displayStatusCode.set(1);
			return true;
		} else if (displayCommand.getDisplayString().equals("overdue")) {
			readOverdue();
			displayStatusCode.set(2);
			return true;
		} else {
			return false;
		}
	}

	private boolean search(Command inputCommand) {
		Search searchCommand = (Search) inputCommand;
		if (searchCommand.isBetween()) {
			return searchForDescriptionBetween(searchCommand.getStrSearchString(), searchCommand.getFirstDate(),
					searchCommand.getSecondDate());
		} else if (searchCommand.isAfter()) {
			return searchForDescriptionAfter(searchCommand.getStrSearchString(), searchCommand.getFirstDate());
		} else if (searchCommand.isBefore()) {
			return searchForDescriptionBefore(searchCommand.getStrSearchString(), searchCommand.getFirstDate());
		} else if (searchCommand.isOn()) {
			return searchForDescriptionOnDate(searchCommand.getStrSearchString(), searchCommand.getFirstDate());
		} else {
			return searchForDescription(searchCommand.getStrSearchString());
		}
	}

	private boolean searchForDescriptionBetween(String strSearchString, DateClass firstDate, DateClass secondDate) {
		allEvents.clear();
		allDeadlines.clear();
		// allFloatingTasks.clear();
		searchKeyword = strSearchString + "between " + firstDate.toString() + " " + secondDate.toString();
		allEvents = fileStorage.searchEventTaskBetweenDates(strSearchString, firstDate, secondDate);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTaskBetweenDates(strSearchString, firstDate, secondDate);
		fillDeadlines();
		return true;
	}

	private boolean searchForDescriptionAfter(String strSearchString, DateClass afterDate) {
		allEvents.clear();
		allDeadlines.clear();
		// allFloatingTasks.clear();
		searchKeyword = "after " + afterDate.toString();
		allEvents = fileStorage.searchEventTaskAfterDate(strSearchString, afterDate);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTaskAfterDate(strSearchString, afterDate);
		fillDeadlines();
		return true;
	}

	private boolean searchForDescriptionBefore(String searchString, DateClass beforeDate) {
		allEvents.clear();
		allDeadlines.clear();
		// allFloatingTasks.clear();
		searchKeyword = "before " + beforeDate.toString();
		allEvents = fileStorage.searchEventTaskBeforeDate(searchString, beforeDate);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTaskBeforeDate(searchString, beforeDate);
		fillDeadlines();
		return true;
	}

	private boolean searchForDescriptionOnDate(String searchString, DateClass date) {
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		searchKeyword = searchString + " on " + date;
		allEvents = fileStorage.searchEventTaskOnDate(searchString, date);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTaskOnDate(searchString, date);
		fillDeadlines();
		allFloatingTasks = fileStorage.readFloatingTask();
		fillFloatings();
		return true;
	}

	private boolean searchForDescription(String searchCriterias) {
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		searchKeyword = searchCriterias;
		allEvents = fileStorage.searchEventTask(searchCriterias);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTask(searchCriterias);
		fillDeadlines();
		allFloatingTasks = fileStorage.searchFloatingTask(searchCriterias);
		fillFloatings();
		return true;
	}

	private void readDone() {
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

	private void readOverdue() {
		ArrayList<Task> overdueTasks = fileStorage.readOverdueTask();
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		for (Task currentTask : overdueTasks) {
			switch (currentTask.getClass().getName()) {
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
		Update undoUpdate = (Update) undoCommand;
		fileStorage.writeTask(undoUpdate.getCurrentTask());
		fileStorage.deleteTask(undoUpdate.getUpdateTask());
		redoCommandHistory.push(undoUpdate);
		updateTaskLists();
		updateRespectiveGUICol("ALL");
	}

	private void redoUpdateCommand(Command undoCommand) {
		Update redoUpdate = (Update) undoCommand;
		fileStorage.deleteTask(redoUpdate.getCurrentTask());
		fileStorage.writeTask(redoUpdate.getUpdateTask());
		redoCommandHistory.push(redoUpdate);
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

	private boolean update(Command inputCommand) {
		Update updateCommand = (Update) inputCommand;
		UpdateTask processUpdate = (UpdateTask) updateCommand.getTaskToUpdate();
		System.out.println(updateCommand.getTaskToUpdate().getDescription() + " ");
		// System.out.println(processUpdate);
		Task taskToUpdate;
		if (processUpdate.getTaskID() <= (allEvents.size() + allDeadlines.size() + allFloatingTasks.size())) {
			taskToUpdate = deleteSingleTask(processUpdate.getTaskID());
		} else {
			return false;
		}

		// System.out.println(taskToUpdate.toString());
		Task updatedTask = null;
		// updatedTask = taskToUpdate;
		System.out.println(taskToUpdate.getClass().getName());
		// System.out.println(taskToUpdate.toString());
		switch (taskToUpdate.getClass().getName()) {
		case "main.Event":
			updatedTask = new Event(((Event) taskToUpdate).getDescription(), ((Event) taskToUpdate).getStartDate(),
					((Event) taskToUpdate).getStartTime(), ((Event) taskToUpdate).getEndDate(),
					((Event) taskToUpdate).getEndTime());
			if (processUpdate.hasDescription())
				updatedTask.setDescription(processUpdate.getDescription());
			if (processUpdate.hasEndDate())
				((Event) updatedTask).setEndDate(processUpdate.getEndDate());
			if (processUpdate.hasEndTime())
				((Event) updatedTask).setEndTime(processUpdate.getEndTime());
			if (processUpdate.hasStartDate())
				((Event) updatedTask).setStartDate(processUpdate.getStartDate());
			if (processUpdate.hasStartTime())
				((Event) updatedTask).setStartTime(processUpdate.getStartTime());
			break;
		case "main.Deadline":
			if (processUpdate.hasStartDate()) {
				if (processUpdate.hasDescription()) {
					if (processUpdate.hasEndDate()) {
						updatedTask = new Event(processUpdate.getDescription(), processUpdate.getStartDate(),
								processUpdate.getStartTime(), processUpdate.getEndDate(), processUpdate.getEndTime());
					} else {
						updatedTask = new Event(processUpdate.getDescription(), processUpdate.getStartDate(),
								processUpdate.getStartTime(), ((Event) taskToUpdate).getEndDate(),
								((Event) taskToUpdate).getEndTime());
					}
				} else {
					if (processUpdate.hasEndDate()) {
						updatedTask = new Event(taskToUpdate.getDescription(), processUpdate.getStartDate(),
								processUpdate.getStartTime(), processUpdate.getEndDate(), processUpdate.getEndTime());
					} else {
						updatedTask = new Event(taskToUpdate.getDescription(), processUpdate.getStartDate(),
								processUpdate.getStartTime(), ((Deadline) taskToUpdate).getEndDate(),
								((Deadline) taskToUpdate).getEndTime());
					}
				}
			} else {
				updatedTask = new Deadline(((Deadline) taskToUpdate).getDescription(),
						((Deadline) taskToUpdate).getEndDate(), ((Deadline) taskToUpdate).getEndTime());
				if (processUpdate.hasDescription())
					updatedTask.setDescription(processUpdate.getDescription());
				if (processUpdate.hasEndDate())
					((Deadline) updatedTask).setEndDate(processUpdate.getEndDate());
				if (processUpdate.hasEndTime())
					((Deadline) updatedTask).setEndTime(processUpdate.getEndTime());
			}

			break;
		case "main.Floating":
			System.out.println(processUpdate.getDescription());
			System.out.println(taskToUpdate.toString());
			if (processUpdate.hasStartDate()) {
				if (processUpdate.hasDescription()) {
					updatedTask = new Event(processUpdate.getDescription(), processUpdate.getStartDate(),
							processUpdate.getStartTime(), processUpdate.getEndDate(), processUpdate.getEndTime());
					System.out.println(processUpdate.getDescription());
				} else {
					updatedTask = new Event(taskToUpdate.getDescription(), processUpdate.getStartDate(),
							processUpdate.getStartTime(), processUpdate.getEndDate(), processUpdate.getEndTime());
				}
			} else if (processUpdate.hasEndDate()) {
				if (processUpdate.hasDescription()) {
					updatedTask = new Deadline(processUpdate.getDescription(), processUpdate.getEndDate(),
							processUpdate.getEndTime());
					System.out.println(processUpdate.getDescription());
				} else {
					updatedTask = new Deadline(taskToUpdate.getDescription(), processUpdate.getEndDate(),
							processUpdate.getEndTime());
				}
			} else {
				System.out.println(processUpdate.getDescription());
				updatedTask = new Floating(processUpdate.getDescription());
				System.out.println(processUpdate.getDescription());
				System.out.println(updatedTask.toString());
			}
			break;
		default:
			break;
		}

		if (updatedTask != null) {
			fileStorage.writeTask(updatedTask);
			updateCommand.setCurrentTask(taskToUpdate);
			updateCommand.setUpdateTask(updatedTask);
			undoCommandHistory.push(updateCommand);
			updateTaskLists();
			updateRespectiveGUICol("ALL");
			return true;
		} else {
			System.out.println("Its null");
			return false;
		}
	}

	private boolean deleteTask(Command inputCommand) {
		Delete deleteCommand = ((Delete) inputCommand);
		ArrayList<Task> taskToDelete = new ArrayList<Task>();
		if (deleteCommand.hasTaskIDs()) {
			System.out.println(Arrays.toString(deleteCommand.getTaskIDs().toArray()));
			for (Integer i : deleteCommand.getTaskIDs()) {
				// System.out.print(i+" ");
				if (i <= (allEvents.size() + allDeadlines.size() + allFloatingTasks.size())) {
					Task a = deleteSingleTask(i);
					System.out.println(i + " " + a.toString() + " ");
					taskToDelete.add(a);
				} else {
					return false;
				}
			}
		} else if (deleteCommand.hasDeleteString()) {
			if (deleteCommand.getDeleteString().equals("all")) {
				return clearAllTask(new Clear());
			} else {
				Task taskDelete = fileStorage.absoluteSearch(deleteCommand.getDeleteString()).get(0);
				if (taskDelete != null) {
					fileStorage.deleteTask(taskDelete);
					taskToDelete.add(taskDelete);
					return true;
				} else {
					return false;
				}
			}
		}
		if (taskToDelete.size() != 0) {
			deleteCommand.setTaskDeleted(taskToDelete);
			updateTaskLists();
			undoCommandHistory.push(deleteCommand);
			updateRespectiveGUICol("ALL");
			return true;
		} else {
			return false;
		}
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

	public void setHasNewOverdueTask(BooleanProperty hasNewOverdueTask) {
		this.hasNewOverdueTask = hasNewOverdueTask;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}
}
