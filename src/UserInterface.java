import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

	enum TaskType {
		EVENT, DEADLINE, FLOATING
	}

	private static final String MESSAGE_PROMPT = "Enter command: ";
	private static final String MESSAGE_WELCOME = "Hi! Welcome to Vodo!\n";
	private static final String MESSAGE_INVALID_COMMAND = "Sorry we can't recognize that command\n";
	private static final String MESSAGE_VALID_COMMAND = "Your command is successful!\n";
	private static final String HEADER_TASK_TYPE = "%1$sS:\n";
	private static final String ITEM_LISTING = "%1$s. %2$s\n";

	private Logic vodoLogic;

	public UserInterface(String fileName) {
		vodoLogic = new Logic(fileName);
	}

	public UserInterface() {
		// TODO Auto-generated constructor stub
	}

	public void promptForCommand() {
		printToUser(MESSAGE_PROMPT);
	}

	public String getCommand() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}

	public void processCommand(String command) throws NoSuchFieldException, ParseException {
		boolean isSuccessful = Logic.processCommand(command);
		if (isSuccessful) {
			printToUser(MESSAGE_VALID_COMMAND);
		} else {
			printToUser(MESSAGE_INVALID_COMMAND);
		}
	}

	public void printToUser(String text) {
		System.out.printf(text);
	}

	public void displayView(ArrayList<Task> tasks) {
		int count = 1;
		for (TaskType taskType : TaskType.values()) {
			printToUser(String.format(HEADER_TASK_TYPE, taskType));
			for (Task task : tasks) {
				if (task.getClass().getName().toUpperCase().equals(taskType.toString())) {
					printToUser(String.format(ITEM_LISTING, count, task));
					count++;
				}
			}
		}
	}

	public void run() throws NoSuchFieldException, ParseException {
		String command;
		while (true) {
			printToUser(MESSAGE_WELCOME);
			promptForCommand();
			command = getCommand();
			processCommand(command);
		}
	}
}
