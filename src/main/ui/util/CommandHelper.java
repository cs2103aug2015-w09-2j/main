//@@author A0126518E
package main.ui.util;

public class CommandHelper {

	public static final String HELP_MAIN = "Available commands: add, delete, update, show, done, undo, redo, search, exit";
	public static final String HELP_ADD = "add <task description> [from/by/at] <date&time> [to] <date&time>";
	public static final String HELP_DELETE = "delete <number>, delete <number> [,/-] <number>, delete all";
	public static final String HELP_UPDATE = "update <number>, update <number> [,/-] <number>";
	public static final String HELP_SHOW = "show [ongoing/overdue/done]";
	public static final String HELP_DONE = "done <number>, done <number> [,/-] <number>";
	public static final String HELP_SEARCH = "search <keyword>, search <date&time>";

	enum CommandType {
		ADD, DELETE, UPDATE, SHOW, DONE, SEARCH, OTHERS // UNDO, REDO, EXIT doesn't have Help dialog
	};

	public static String respondTo(String text) {
		CommandType commandType = getCommandType(text);
		return chooseHelpDialog(commandType);
	}

	private static CommandType getCommandType(String text) {
		text = text.trim();
		int indexOfWhitespace = text.indexOf(" ");
		indexOfWhitespace = indexOfWhitespace > -1 ? indexOfWhitespace : text.length();

		for (CommandType commandType : CommandType.values()) {
			if (text.equals("")) {
				return CommandType.OTHERS;
			} else {
				if (commandType.toString().toLowerCase().startsWith(text.substring(0,indexOfWhitespace).toLowerCase())) {
					return commandType;
				}
			}
		}

		return CommandType.OTHERS;
	}

	private static String chooseHelpDialog(CommandType commandType) {
		String helpDialog;

		switch (commandType) {
		case ADD :
			helpDialog = HELP_ADD;
			break;
		case DELETE :
			helpDialog = HELP_DELETE;
			break;
		case UPDATE :
			helpDialog = HELP_UPDATE;
			break;
		case SHOW :
			helpDialog = HELP_SHOW;
			break;
		case DONE :
			helpDialog = HELP_DONE;
			break;
		case SEARCH :
			helpDialog = HELP_SEARCH;
			break;
		default :
			helpDialog = HELP_MAIN;
			break;
		}

		return helpDialog;
	}
}