//@@author A0126518E
package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private Path logFile;
	private String fileName = ".logfile";
	private PrintWriter writer;
	private static Logger thisInstance;

	private Logger() {
		try {
			getFile();
			writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)), true);
		} catch (IOException anException) {
			anException.printStackTrace();
		}
	}

	private void getFile() throws IOException {
		logFile = Paths.get(System.getProperty("user.dir"), fileName);

		if (!Files.exists(logFile)) {
			logFile = Files.createFile(logFile);
		}
	}

	public void write(String logText) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date now = new Date();
		writer.println("[" + dateFormatter.format(now) + "] " + logText);
	}

	public static Logger getInstance() {
		if (thisInstance == null) {
			thisInstance = new Logger();
		}
		return thisInstance;
	}
}