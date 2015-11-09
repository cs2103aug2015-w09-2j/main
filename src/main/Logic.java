package main;

import java.text.ParseException;
import java.util.*;

import javafx.beans.property.*;
import javafx.collections.*;
import main.Clear;
import main.Command;
import main.Deadline;
import main.Delete;
import main.Display;
import main.Done;
import main.Event;
import main.Floating;
import main.Save;
import main.Search;
import main.Task;
import main.Update;
import main.UpdateTask;
import main.FileStorage;
import main.DateClass;
import main.DateHandler;
import main.TimeClass;
import main.TimeHandler;
import main.Parser;
import main.ui.MainApp;
import main.ui.util.StatusHelper;

/**
 * @author Ravi
 *
 */
public class Logic {
	private static Logic oneLogic = null;
	private static final String ALLCOLLUMS = "ALL";
	private static final String EVENTS = "main.Event";
	private static final String DEADLINES = "main.Deadline";
	private static final String FLOATINGTASKS = "main.Floating";
	private static final String DISPLAY_ONGOING_TASKS = "ongoing";
	private static final String DISPLAY_DONE_TASKS = "done";
	private static final String DISPLAY_OVERDUE_TASKS = "overdue";
	private static int currentView;
	private static int newOverdueTask = 0;

	/**
	 * Description Constructor : Creates and instance of the Logic class with
	 * the fileName stated
	 *
	 * @param filename
	 */
	// @@author A0133869R
	public static Logic getInstance() {
		if (oneLogic == null) {
			oneLogic = new Logic();
		}
		return oneLogic;
	}

	/**
	 * Description Default Constructor
	 */
	// @@author A0133869R
	private Logic() { // [TEDDY] this is all that I initialize keeping in mind
						// the initial view of the user using the app
		hasNewOverdueTask = new SimpleBooleanProperty(Boolean.FALSE);// added
		displayStatusCode = new SimpleIntegerProperty(StatusHelper.Status.ONGOING.getCode());// added
		updateTaskLists();
		events = FXCollections.observableArrayList(allEvents);// added
		deadlines = FXCollections.observableArrayList(allDeadlines);// added
		floatings = FXCollections.observableArrayList(allFloatingTasks);// added
		undoCommandHistory = new Stack<Command>();
		redoCommandHistory = new Stack<Command>();
		currentView = StatusHelper.Status.ONGOING.getCode();

	}

	private static Parser parser = Parser.getInstance();
	private FileStorage fileStorage = new FileStorage();
	private static Stack<Command> undoCommandHistory;
	private static Stack<Command> redoCommandHistory;
	private static ArrayList<Task> allEvents;
	private static ArrayList<Task> allDeadlines;
	private static ArrayList<Task> allFloatingTasks;
	

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
	// @@author A0133869R
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

	// @@author A0133869R
	private void updateTaskLists() {
		allEvents = fileStorage.readEventTask();
		allDeadlines = fileStorage.readDeadlineTask();
		allFloatingTasks = fileStorage.readFloatingTask();
		allEvents = filterDoneTask(allEvents);
		allEvents = filterOverdueTask(allEvents);
		allDeadlines = filterDoneTask(allDeadlines);
		allDeadlines = filterOverdueTask(allDeadlines);
		System.out.println(fileStorage.readOverdueTask().size() + " " + hasNewOverdueTask.getValue());

		if (fileStorage.readOverdueTask().size() > newOverdueTask) {
			hasNewOverdueTask.setValue(Boolean.TRUE);
			System.out.println("notiictation update" + fileStorage.readOverdueTask().size() + " & state is"
					+ hasNewOverdueTask.getValue());
		} else {
			hasNewOverdueTask.setValue(Boolean.FALSE);
			System.out.println("notifation update to false" + fileStorage.readOverdueTask().size() + " & state is"
					+ hasNewOverdueTask.getValue());
		}
		Collections.sort(allEvents);
		Collections.sort(allDeadlines);
		Collections.sort(allFloatingTasks);
		

	}

	// @@author A0133869R
	private ArrayList<Task> filterDoneTask(ArrayList<Task> tasks) {
		ArrayList<Task> output = new ArrayList<Task>();
		for (Task currentTask : tasks) {
			if (currentTask.isDone()) {
				fileStorage.deleteTask(currentTask);
				fileStorage.writeDoneTask(currentTask);
			} else {
				output.add(currentTask);
			}
		}
		return output;
	}

	// @@author A0133869R
	private ArrayList<Task> filterOverdueTask(ArrayList<Task> tasks) {
		ArrayList<Task> taskList = new ArrayList<Task>();
		for (Task currentTask : tasks) {
			switch (currentTask.getClass().getName()) {
			case EVENTS:
				int compareTo = 0;

				filterOverdueTask(taskList, currentTask, compareTo);
				break;
			case DEADLINES:
				compareTo = 0;

				filterDeadlineTask(taskList, currentTask, compareTo);
				break;
			default:
				break;
			}
		}
		return taskList;
	}

	// @@author A0133869R
	private void filterDeadlineTask(ArrayList<Task> taskList, Task currentTask, int compareTo) {
		try {
			compareTo = ((Deadline) currentTask).getEndDate()
					.compareTo(new DateClass(DateHandler.getIntDayNow(), DateHandler.getIntMonthNow()));
		} catch (NoSuchFieldException | ParseException e) {
			// TODO Auto-generated catch block
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
				deleteTaskFromCurrentView(currentTask);
			}
		} else {
			fileStorage.writeOverdueTask(currentTask);
			deleteTaskFromCurrentView(currentTask);
		}
	}

	// @@author A0133869R
	private void filterOverdueTask(ArrayList<Task> taskList, Task currentTask, int compareTo) {
		try {
			compareTo = ((Event) currentTask).getEndDate()
					.compareTo(new DateClass(DateHandler.getIntDayNow(), DateHandler.getIntMonthNow()));
		} catch (NoSuchFieldException | ParseException e) {

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
				deleteTaskFromCurrentView(currentTask);
			}
		} else {
			fileStorage.writeOverdueTask(currentTask);
			deleteTaskFromCurrentView(currentTask);
		}
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
	// @@author A0133869R
	private boolean executeCommand(Command inputCommand) {
		boolean success = false;
		switch (inputCommand.getCommandType()) {
		case ADD_EVENT:
			success = addEvent(inputCommand);
			displayStatusCode.set(StatusHelper.Status.ONGOING.getCode());
			currentView = StatusHelper.Status.ONGOING.getCode();
			break;
		case ADD_DEADLINE:
			success = addDeadline(inputCommand);
			displayStatusCode.set(StatusHelper.Status.ONGOING.getCode());
			currentView = StatusHelper.Status.ONGOING.getCode();
			break;
		case ADD_FLOATING:
			success = addFloating(inputCommand);
			displayStatusCode.set(StatusHelper.Status.ONGOING.getCode());
			currentView = StatusHelper.Status.ONGOING.getCode();
			break;
		case UPDATE:
			success = update(inputCommand);
			currentView = StatusHelper.Status.ONGOING.getCode();
			// updateTaskLists();
			break;
		case DONE:

			if (currentView == StatusHelper.Status.DONE.getCode()) {
				return false;
			} else {
				success = done(inputCommand);
				displayStatusCode.set(StatusHelper.Status.ONGOING.getCode());
				currentView = StatusHelper.Status.ONGOING.getCode();
			}
			break;
		case DELETE:
			success = deleteTask(inputCommand);
			displayStatusCode.set(StatusHelper.Status.ONGOING.getCode());
			currentView = StatusHelper.Status.ONGOING.getCode();
			break;
		case CLEAR:
			success = clearAllTask(inputCommand);
			displayStatusCode.set(StatusHelper.Status.ONGOING.getCode());
			break;
		case SEARCH:
			success = search(inputCommand);
			currentView = StatusHelper.Status.ONGOING.getCode();
			if (success) {
				if (displayStatusCode.get() == StatusHelper.Status.SEARCH.getCode()) {
					displayStatusCode.set(StatusHelper.Status.NEWSEARCH.getCode());
				} else {
					displayStatusCode.set(StatusHelper.Status.SEARCH.getCode());
				}
			}
			break;
		case UNDO:
			success = undo();
			break;
		case REDO:
			success = redo();
			break;
		case DISPLAY:
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

	// @@author A0133869R
	private boolean save(Command inputCommand) {
		fileStorage.setFilePath(((Save) inputCommand).getPathLocation());
		return true;
	}

	// @@author A0133869R
	private boolean addFloating(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		System.out.println("i'm here");
		undoCommandHistory.push(inputCommand);
		redoCommandHistory.clear();
		updateTaskLists();
		fillFloatings();
		return true;
	}

	// @@author A0133869R
	private boolean addDeadline(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		undoCommandHistory.push(inputCommand);
		redoCommandHistory.clear();
		updateTaskLists();
		fillDeadlines();
		return true;
	}

	// @@author A0133869R
	private boolean addEvent(Command inputCommand) {
		fileStorage.writeTask(inputCommand.getTask());
		undoCommandHistory.push(inputCommand);
		redoCommandHistory.clear();
		updateTaskLists();
		fillEvents();
		return true;
	}

	// @@author A0133869R
	private boolean clearAllTask(Command inputCommand) {
		Clear clearAllTask = (Clear) inputCommand;
		ArrayList<Task> taskCleared = new ArrayList<Task>();

		for (Task currentTask : allEvents) {
			deleteTaskFromCurrentView(currentTask);
			taskCleared.add(currentTask);
		}
		for (Task currentTask : allDeadlines) {
			deleteTaskFromCurrentView(currentTask);
			taskCleared.add(currentTask);
		}
		for (Task currentTask : allFloatingTasks) {
			deleteTaskFromCurrentView(currentTask);
			taskCleared.add(currentTask);
		}
		newOverdueTask = fileStorage.readOverdueTask().size();
		updateTaskLists();
		updateRespectiveGUICol(ALLCOLLUMS);
		clearAllTask.setTaskCleared(taskCleared);
		undoCommandHistory.push(clearAllTask);
		return true;
	}

	// @@author A0133869R
	private void deleteTaskFromCurrentView(Task currentTask) {
		if (currentView == StatusHelper.Status.DONE.getCode()) {
			fileStorage.deleteDoneTask(currentTask);
		} else if (currentView == StatusHelper.Status.OVERDUE.getCode()) {
			fileStorage.deleteOverdueTask(currentTask);
		} else {
			fileStorage.deleteTask(currentTask);
		}
	}

	// @@author A0133869R
	private boolean done(Command inputCommand) {
		// updateTaskLists();
		Set<Integer> tasksDone = ((Done) inputCommand).getTaskIDs();
		ArrayList<Task> tasksSetDone = new ArrayList<Task>();
		for (Integer i : tasksDone) {
			if (i > 0 && i <= (allEvents.size() + allDeadlines.size() + allFloatingTasks.size())) {
				// deleteSingleTask(i);
				tasksSetDone.add(markDone(i));
				// return true;
			} else {
				return false;
			}
		}
		updateTaskLists();
		updateRespectiveGUICol(ALLCOLLUMS);
		undoCommandHistory.push(new Done(tasksDone, tasksSetDone));
		redoCommandHistory.clear();
		System.out.println(Arrays.toString(tasksDone.toArray()));
		return true;
	}

	// @@author A0133869R
	private Task markDone(Integer i) {
		if (i <= allEvents.size()) {
			fileStorage.writeDoneTask(allEvents.get(i - 1));
			deleteTaskFromCurrentView(allEvents.get(i - 1));
			System.out.println("from event");
			allEvents.get(i - 1).setDone(true);
			return allEvents.get(i - 1);
		} else if (i <= allEvents.size() + allDeadlines.size()) {
			fileStorage.writeDoneTask(allDeadlines.get(i - allEvents.size() - 1));
			deleteTaskFromCurrentView(allDeadlines.get(i - allEvents.size() - 1));
			System.out.println("from deadline");
			allDeadlines.get(i - allEvents.size() - 1).setDone(true);
			return allDeadlines.get(i - allEvents.size() - 1);
		} else {
			fileStorage.writeDoneTask(allFloatingTasks.get(i - allEvents.size() - allDeadlines.size() - 1));
			deleteTaskFromCurrentView(allFloatingTasks.get(i - allEvents.size() - allDeadlines.size() - 1));
			System.out.println("from floatings");
			allFloatingTasks.get(i - allEvents.size() - allDeadlines.size() - 1).setDone(true);
			return allFloatingTasks.get(i - allEvents.size() - allDeadlines.size() - 1);
		}
	}

	// @@author A0133869R
	private boolean display(Command inputCommand) {
		Display displayCommand = (Display) inputCommand;
		if (displayCommand.getDisplayString() == null) {
			return false;
		}
		if (displayCommand.getDisplayString().equals(DISPLAY_ONGOING_TASKS)) {
			updateTaskLists();
			fillEvents();
			fillFloatings();
			fillDeadlines();
			updateRespectiveGUICol(ALLCOLLUMS);
			currentView = StatusHelper.Status.ONGOING.getCode();
			displayStatusCode.set(StatusHelper.Status.ONGOING.getCode());
			return true;
		} else if (displayCommand.getDisplayString().equals(DISPLAY_DONE_TASKS)) {
			readDone();
			currentView = StatusHelper.Status.DONE.getCode();
			displayStatusCode.set(StatusHelper.Status.DONE.getCode());
			return true;
		} else if (displayCommand.getDisplayString().equals(DISPLAY_OVERDUE_TASKS)) {
			readOverdue();
			hasNewOverdueTask.set(Boolean.FALSE);
			newOverdueTask = fileStorage.readOverdueTask().size();
			System.out.println(fileStorage.readOverdueTask().size() + " & state is" + hasNewOverdueTask.getValue());
			currentView = StatusHelper.Status.OVERDUE.getCode();
			displayStatusCode.set(StatusHelper.Status.OVERDUE.getCode());
			return true;
		} else {
			return false;
		}
	}

	// @@author A0133869R
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

	// @@author A0133869R
	private boolean searchForDescriptionBetween(String strSearchString, DateClass firstDate, DateClass secondDate) {
		allEvents.clear();
		allDeadlines.clear();
		// allFloatingTasks.clear();
		if (strSearchString != null) {
			searchKeyword = strSearchString + " between " + firstDate.toString() + " " + secondDate.toString();
		} else {
			searchKeyword = "between " + firstDate.toString() + " " + secondDate.toString();
		}
		allEvents = fileStorage.searchEventTaskBetweenDates(strSearchString, firstDate, secondDate);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTaskBetweenDates(strSearchString, firstDate, secondDate);
		fillDeadlines();
		return true;
	}

	// @@author A0133869R
	private boolean searchForDescriptionAfter(String strSearchString, DateClass afterDate) {
		allEvents.clear();
		allDeadlines.clear();
		// allFloatingTasks.clear();
		if (strSearchString != null) {
			searchKeyword = strSearchString + " after " + afterDate.toString();
		} else {
			searchKeyword = "after " + afterDate.toString();
		}
		allEvents = fileStorage.searchEventTaskAfterDate(strSearchString, afterDate);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTaskAfterDate(strSearchString, afterDate);
		fillDeadlines();
		return true;
	}

	// @@author A0133869R
	private boolean searchForDescriptionBefore(String strSearchString, DateClass beforeDate) {
		allEvents.clear();
		allDeadlines.clear();
		// allFloatingTasks.clear();
		if (strSearchString != null) {
			searchKeyword = strSearchString + " before " + beforeDate.toString();
		} else {
			searchKeyword = "before " + beforeDate.toString();
		}
		allEvents = fileStorage.searchEventTaskBeforeDate(strSearchString, beforeDate);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTaskBeforeDate(strSearchString, beforeDate);
		fillDeadlines();
		return true;
	}

	// @@author A0133869R
	private boolean searchForDescriptionOnDate(String strSearchString, DateClass date) {
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		if (strSearchString != null) {
			searchKeyword = strSearchString + " on " + date.toString();
		} else {
			searchKeyword = "on " + date.toString();
		}
		allEvents = fileStorage.searchEventTaskOnDate(strSearchString, date);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTaskOnDate(strSearchString, date);
		fillDeadlines();
		allFloatingTasks = fileStorage.readFloatingTask();
		fillFloatings();
		return true;
	}

	// @@author A0133869R
	private boolean searchForDescription(String strSearchString) {
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		if (strSearchString == null || strSearchString.equals("")) {
			return false;
		}
		searchKeyword = strSearchString;
		allEvents = fileStorage.searchEventTask(strSearchString);
		fillEvents();
		allDeadlines = fileStorage.searchDeadlineTask(strSearchString);
		fillDeadlines();
		allFloatingTasks = fileStorage.searchFloatingTask(strSearchString);
		fillFloatings();
		return true;
	}

	// @@author A0133869R
	private void readDone() {
		ArrayList<Task> doneTasks = fileStorage.readDoneTask();
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		for (Task currentTask : doneTasks) {
			switch (currentTask.getClass().getName()) {
			case FLOATINGTASKS:
				allFloatingTasks.add(currentTask);
				break;
			case DEADLINES:
				allDeadlines.add(currentTask);
				break;
			case EVENTS:
				allEvents.add(currentTask);
				break;
			}
			updateRespectiveGUICol(ALLCOLLUMS);
		}
		updateRespectiveGUICol(ALLCOLLUMS);
	}

	// @@author A0133869R
	private void readOverdue() {
		ArrayList<Task> overdueTasks = fileStorage.readOverdueTask();
		allEvents.clear();
		allDeadlines.clear();
		allFloatingTasks.clear();
		for (Task currentTask : overdueTasks) {
			switch (currentTask.getClass().getName()) {
			case DEADLINES:
				allDeadlines.add(currentTask);
				break;
			case EVENTS:
				allEvents.add(currentTask);
				break;
			}
			updateRespectiveGUICol(ALLCOLLUMS);
		}
		updateRespectiveGUICol(ALLCOLLUMS);
	}

	// @@author A0133869R
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
			updateRespectiveGUICol(ALLCOLLUMS);
			break;
		case CLEAR:
			redoClear(redoCommand);
			undoCommandHistory.push(redoCommand);
			updateRespectiveGUICol(ALLCOLLUMS);
			break;
		default:
			break;
		}
		return true;
	}

	// @@author A0133869R
	private void redoDeleteCommand(Command redoCommand) {
		undoCommandHistory.push(redoCommand);
		for (Task currentTask : ((Delete) redoCommand).getTaskDeleted()) {
			deleteTaskFromCurrentView(currentTask);
		}
		updateTaskLists();
		updateRespectiveGUICol(ALLCOLLUMS);
	}

	// @@author A0133869R
	private void redoAddCommand(Command redoCommand) {
		undoCommandHistory.push(redoCommand);
		fileStorage.writeTask(redoCommand.getTask());
		updateTaskLists();
		updateRespectiveGUICol(redoCommand.getTask().getClass().getName());
	}

	// @@author A0133869R
	private void redoClear(Command redoCommand) {
		Clear redoClear = ((Clear) redoCommand);
		ArrayList<Task> taskCleared = redoClear.getTaskCleared();
		for (Task currentTask : taskCleared) {
			deleteTaskFromCurrentView(currentTask);
		}
		updateTaskLists();
	}

	// @@author A0133869R
	private void redoDone(Command redoCommand) {
		ArrayList<Task> redoDoneTask = ((Done) redoCommand).getTasksSetDone();
		for (Task currentTask : redoDoneTask) {
			fileStorage.writeDoneTask(currentTask);
			deleteTaskFromCurrentView(currentTask);
			updateTaskLists();
		}
	}

	// @@author A0133869R
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
			updateRespectiveGUICol(ALLCOLLUMS);
			break;
		case CLEAR:
			undoClear(undoCommand);
			redoCommandHistory.push(undoCommand);
			updateRespectiveGUICol(ALLCOLLUMS);
			break;
		default:
			break;
		}
		return true;
	}

	// @@author A0133869R
	private void undoUpdateCommand(Command undoCommand) {
		Update undoUpdate = (Update) undoCommand;
		fileStorage.writeTask(undoUpdate.getCurrentTask());
		fileStorage.deleteTask(undoUpdate.getUpdateTask());// check again how
		redoCommandHistory.push(undoUpdate);
		updateTaskLists();
		updateRespectiveGUICol(ALLCOLLUMS);
	}

	// @@author A0133869R
	private void redoUpdateCommand(Command undoCommand) {
		Update redoUpdate = (Update) undoCommand;
		fileStorage.deleteTask(redoUpdate.getCurrentTask());/// check again how
															/// to do
		fileStorage.writeTask(redoUpdate.getUpdateTask());
		redoCommandHistory.push(redoUpdate);
		updateTaskLists();
		updateRespectiveGUICol(ALLCOLLUMS);
	}

	// @@author A0133869R
	private void undoDeleteCommand(Command undoCommand) {
		redoCommandHistory.push(undoCommand);
		for (Task currentTask : ((Delete) undoCommand).getTaskDeleted()) {
			fileStorage.writeTask(currentTask);
		}
		// fileStorage.writeTask(((Delete) undoCommand).getTaskDeleted());
		updateTaskLists();
		updateRespectiveGUICol(ALLCOLLUMS);
	}

	// @@author A0133869R
	private void undoAddCommand(Command undoCommand) {
		redoCommandHistory.push(undoCommand);
		fileStorage.deleteTask(undoCommand.getTask());
		updateTaskLists();
		updateRespectiveGUICol(undoCommand.getTask().getClass().getName());
	}

	// @@author A0133869R
	private void undoClear(Command undoCommand) {
		Clear undoClear = ((Clear) undoCommand);
		ArrayList<Task> taskCleared = undoClear.getTaskCleared();
		for (Task currentTask : taskCleared) {
			fileStorage.writeTask(currentTask);
		}
		updateTaskLists();
	}

	// @@author A0133869R
	private void undoDone(Command undoCommand) {
		Done doneCommand = ((Done) undoCommand);
		for (Task currentTask : doneCommand.getTasksSetDone()) {
			fileStorage.deleteDoneTask(currentTask);
			fileStorage.writeTask(currentTask);
			updateTaskLists();
		}
		updateRespectiveGUICol(ALLCOLLUMS);
	}

	// @@author A0133869R
	private boolean update(Command inputCommand) {
		Update updateCommand = (Update) inputCommand;
		UpdateTask processUpdate = (UpdateTask) updateCommand.getTaskToUpdate();
		System.out.println(updateCommand.getTaskToUpdate().getDescription() + " ");
		// System.out.println(processUpdate);
		Task taskToUpdate;
		if (processUpdate.getTaskID() > 0
				&& processUpdate.getTaskID() <= (allEvents.size() + allDeadlines.size() + allFloatingTasks.size())) {
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
		case EVENTS:
			updatedTask = updateEventTask(processUpdate, taskToUpdate);
			break;
		case DEADLINES:
			updatedTask = updateDeadlineTask(processUpdate, taskToUpdate);
			break;
		case FLOATINGTASKS:
			updatedTask = updateFloatingTask(processUpdate, taskToUpdate);
			break;
		default:
			break;
		}

		if (updatedTask != null) {
			fileStorage.writeTask(updatedTask);
			updateCommand.setCurrentTask(taskToUpdate);
			updateCommand.setUpdateTask(updatedTask);
			undoCommandHistory.push(updateCommand);
			redoCommandHistory.clear();
			updateTaskLists();
			updateRespectiveGUICol(ALLCOLLUMS);
			return true;
		} else {
			System.out.println("Its null");
			return false;
		}
	}

	// @@author A0133869R
	private Task updateFloatingTask(UpdateTask processUpdate, Task taskToUpdate) {
		Task updatedTask;
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
		return updatedTask;
	}

	// @@author A0133869R
	private Task updateDeadlineTask(UpdateTask processUpdate, Task taskToUpdate) {
		Task updatedTask;
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
		return updatedTask;
	}

	// @@author A0133869R
	private Task updateEventTask(UpdateTask processUpdate, Task taskToUpdate) {
		Task updatedTask;
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
		return updatedTask;
	}

	// @@author A0133869R
	private boolean deleteTask(Command inputCommand) {
		Delete deleteCommand = ((Delete) inputCommand);
		ArrayList<Task> taskToDelete = new ArrayList<Task>();
		if (deleteCommand.hasTaskIDs()) {
			for (Integer i : deleteCommand.getTaskIDs()) {
				if (i > 0 && i <= (allEvents.size() + allDeadlines.size() + allFloatingTasks.size())) {
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
					deleteTaskFromCurrentView(taskDelete);
					taskToDelete.add(taskDelete);
					return true;
				} else {
					return false;
				}
			}
		}
		if (taskToDelete.size() != 0) {
			newOverdueTask = fileStorage.readOverdueTask().size();
			deleteCommand.setTaskDeleted(taskToDelete);
			updateTaskLists();
			undoCommandHistory.push(deleteCommand);
			redoCommandHistory.clear();
			updateRespectiveGUICol(ALLCOLLUMS);
			return true;
		} else {
			return false;
		}
	}

	// @@author A0133869R
	private Task deleteSingleTask(int taskIndex) {
		if (taskIndex <= allEvents.size()) {
			deleteTaskFromCurrentView(allEvents.get(taskIndex - 1));
			return allEvents.get(taskIndex - 1);
		} else if (taskIndex <= (allEvents.size() + allDeadlines.size())) {
			int indexToDelete = taskIndex - allEvents.size() - 1;
			deleteTaskFromCurrentView(allDeadlines.get(indexToDelete));
			return allDeadlines.get(indexToDelete);
		} else {
			int indexToDelete = taskIndex - allEvents.size() - allDeadlines.size() - 1;
			deleteTaskFromCurrentView(allFloatingTasks.get(indexToDelete));
			return allFloatingTasks.get(indexToDelete);
		}
	}

	// @@author A0133869R
	private void updateRespectiveGUICol(String taskType) {
		switch (taskType) {
		case EVENTS:
			fillEvents();
			break;
		case DEADLINES:
			fillDeadlines();
			break;
		case FLOATINGTASKS:
			fillFloatings();
			break;
		case ALLCOLLUMS:
			fillFloatings();
			fillDeadlines();
			fillEvents();
			break;
		default:
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

	// @@author A0126518E
	public void fillEvents() {
		events.setAll(allEvents);
	}

	public void fillDeadlines() {
		deadlines.setAll(allDeadlines);
	}

	public void fillFloatings() {
		floatings.setAll(allFloatingTasks);
	}

	public ObservableList<Task> getEvents() {
		return events;
	}

	public ObservableList<Task> getDeadlines() {
		return deadlines;
	}

	public ObservableList<Task> getFloatings() {
		return floatings;
	}

	public void setDisplayState(IntegerProperty displayStatusCode) {
		this.displayStatusCode = displayStatusCode;
	}

	public BooleanProperty getHasNewOverdueTask() {
		return hasNewOverdueTask;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}
}
