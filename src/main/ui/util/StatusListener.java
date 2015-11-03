package main.ui.util;

public class StatusListener {

	public static final String ONGOING = "Ongoing tasks";
	public static final String OVERDUE = "Overdue tasks";
	public static final String DONE = "Completed tasks";
	public static final String SEARCH = "Search results for '%s'";
	public static final String EVENTS = "All events";
	public static final String DEADLINES = "All deadlines";
	public static final String FLOATINGS = "All floatings";

	public enum Status {
		ONGOING(0), DONE(1), OVERDUE(2), SEARCH(3), EVENTS(4), DEADLINES(5), FLOATINGS(6);

		private int code;
		Status(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}
	}

	public static String getStatusText(int code) {
		String statusText;

		switch (code) {
		case 0 :
			statusText = ONGOING;
			break;
		case 1 :
			statusText = DONE;
			break;
		case 2 :
			statusText = OVERDUE;
			break;
		case 3 :
			statusText = SEARCH;
			break;
		case 4 :
			statusText = EVENTS;
			break;
		case 5 :
			statusText =DEADLINES;
			break;
		case 6 :
			statusText = FLOATINGS;
			break;
		default :
			statusText = ONGOING;
			break;
		}
		return statusText;
	}

}