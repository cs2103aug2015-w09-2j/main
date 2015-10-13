package main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Ravi
 *
 */
public class Logic {
	private String fileName;

	/**
	 * Description Constructor : Creates and instance of the Logic class with the fileName stated
	 * @param filename
	 */
	public Logic(String filename) {
		fileName = filename;
	}

	/**
	 * Description Default Constructor
	 */
	public Logic() {
	}

	private static Parser parser = new Parser();
	private static UserInterface UI = new UserInterface();
	private static FileStorage fileStorage = new FileStorage();
	private static CommandType.Types command;

	/**
	 * Description Takes in the command as a string from the user input and processes the command and executes the command if its in the correct format
	 * @param input
	 * @return
	 * @throws NoSuchFieldException
	 * @throws ParseException
	 */
	public static boolean processCommand(String input) throws NoSuchFieldException, ParseException {
		TaskPair task = parser.parse(input);
		if (task.getType() == CommandType.Types.UNKNOWN) {
			return false;
		} else {
			// executeCommand(,input);
			executeCommand(task.getType(), task.getTask(), input);
		}
		return true;
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
	private static boolean executeCommand(CommandType.Types command, Task input, String inputString)
			throws NoSuchFieldException, ParseException {
		boolean success = false;
		switch (command) {
		case ADD_EVENT:
			// Event event = createEvent(input);
			writeTaskTofile(input);
			success = true;
			break;
		case ADD_DEADLINE:
			// Deadline deadline = createDeadLine(input);
			// writeTaskTofile(deadline);
			writeTaskTofile(input);
			success = true;
			break;
		case ADD_FLOATING:
			// Floating floating = createFloating(input);
			// writeTaskTofile(floating);
			writeTaskTofile(input);
			success = true;
			break;
		case UPDATE:
			updateTask((Update) input);
			success = true;
			break;
		case DELETE:
			deleteTask(inputString);
			success = true;
			break;
		case SEARCH:
			UI.displayView(search(inputString.substring(7)));
			success = true;
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

	/**
	 * Description Method used to update the task
	 * @param input
	 */
	private static void updateTask(Update input) {
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
	private static ArrayList<Task> search(String substring) {
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
	private static void deleteTask(String index) {
		// TODO Auto-generated method stub
		String[] arr = index.split(" ");
		fileStorage.deleteTask(Integer.valueOf(arr[1]));

	}

	/**
	 * @return
	 */
	private static ArrayList<Task> stringToTask() {
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
	private static Task getTaskFromString(String s) {
		Task a = parser.parse(s).getTask();
		//System.out.println(a.toString());
		return parser.parse(s).getTask();
	}

	/**
	 * @param task
	 */
	private static void writeTaskTofile(Task task) {
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
	private static void writeFloatingToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = "add " + CommandType.TaskTypes.FLOATING + " " + task.getDescription();
		fileStorage.write(writeToFile);
	}

	/**
	 * @param task
	 * @param taskType
	 */
	private static void writeDeadlineToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = "add " + CommandType.TaskTypes.DEADLINE + " " + task.getDescription() + " "
				+ ((Deadline) task).getEndDate().toString() + " " + ((Deadline) task).getEndTime().toString();
		fileStorage.write(writeToFile);
	}

	/**
	 * @param task
	 * @param taskType
	 */
	private static void writeEventToFile(Task task, String taskType) {
		String writeToFile;
		writeToFile = "add " + CommandType.TaskTypes.EVENT + " " + task.getDescription() + " "
				+ ((Event) task).getStartDate().toString() + " " + ((Event) task).getStartTime().toString() + " "
				+ ((Event) task).getEndDate().toString() + " " + ((Event) task).getEndTime().toString();
		fileStorage.write(writeToFile);
	}
}
