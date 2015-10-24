package main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
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
		//[Ravi] made the use of singleton logic
		vodoLogic =  Logic.getInstance();
	}

	public UserInterface() {
		// TODO Auto-generated constructor stub
	}

	public void displayView(ArrayList<Task> tasks) {
		for (TaskType taskType : TaskType.values()) {
			printToUser(String.format(HEADER_TASK_TYPE, taskType));
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				if (task.getClass().getName().substring(5).toUpperCase().equals(taskType.toString())) {
					printToUser(String.format(ITEM_LISTING, i+1, task));
				}
			}
			printToUser("\n");
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

	private void promptForCommand() {
		printToUser(MESSAGE_PROMPT);
	}

	private String getCommand() {
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		return command;
	}

	private void processCommand(String command) {
		boolean isSuccessful = false;

		//try {
			isSuccessful = vodoLogic.processCommand(command);
		//}
		/*catch (Exception exception) {
			if (exception instanceof NoSuchFieldException) {
				printToUser("No such field");
			}
			if (exception instanceof ParseException) {
				printToUser(MESSAGE_INVALID_COMMAND);
			}
		}*/

		if (isSuccessful) {
			printToUser(MESSAGE_VALID_COMMAND);
		} else {
			printToUser(MESSAGE_INVALID_COMMAND);
		}
	}

	private void printToUser(String text) {
		System.out.printf(text);
	}


}
