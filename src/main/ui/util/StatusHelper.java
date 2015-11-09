//@@author A0126518E
package main.ui.util;

public class StatusHelper {

	public static final String ONGOING = "Ongoing tasks";
	public static final String OVERDUE = "Overdue tasks";
	public static final String DONE = "Completed tasks";
	public static final String SEARCH = "Search results for '%s'";
	public static final String NEWSEARCH = "Search results for '%s'";

	public enum Status {
		ONGOING(0), DONE(1), OVERDUE(2), SEARCH(3), NEWSEARCH(4);

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
			statusText = NEWSEARCH;
			break;
		default :
			statusText = ONGOING;
			break;
		}
		return statusText;
	}

}