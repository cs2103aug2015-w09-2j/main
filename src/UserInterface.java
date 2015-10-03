import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

	private static final String MESSAGE_PROMPT = "Enter command: ";
	private static final String MESSAGE_WELCOME = "Hi! Welcome to Vodo!\n";
	private static final String MESSAGE_INVALID_COMMAND = "Sorry we can't recognize that command\n";
	private static final String MESSAGE_VALID_COMMAND = "Your command is successful!\n";


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
		for (Task t : tasks) {
			printToUser(t + "\n");
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
