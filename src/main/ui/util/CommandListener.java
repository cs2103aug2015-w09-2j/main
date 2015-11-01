package main.ui.util;

public class CommandListener {

	public static final String HELP_MAIN = "Available commands: add/delete/update/display/done/undo/redo/search/exit";
	private static final String HELP_ADD = "add <task description> [from/by/at] <date&time> [to] <date&time>";
	private static final String HELP_DELETE = "delete <number>, delete <number> [,/-] <number>";
	private static final String HELP_UPDATE = "update <number>, update <number> [,/-] <number>";
	private static final String HELP_DISPLAY = "display [ongoing/overdue/done/events/deadlines/floatings]";
	private static final String HELP_DONE = "done <number>, done <number> [,/-] <number>";
	private static final String HELP_UNDO = "undo, undo [number of times]";
	private static final String HELP_REDO = "redo, redo [number of times]";
	private static final String HELP_SEARCH = "search <keyword>, search <date&time>";

	enum CommandType {
		ADD, DELETE, UPDATE, DISPLAY, DONE, UNDO, REDO, SHOW, SEARCH, OTHERS
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
		case DISPLAY :
			helpDialog = HELP_DISPLAY;
			break;
		case DONE :
			helpDialog = HELP_DONE;
			break;
		case UNDO :
			helpDialog = HELP_UNDO;
			break;
		case REDO :
			helpDialog = HELP_REDO;
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