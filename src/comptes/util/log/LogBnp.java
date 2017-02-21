package comptes.util.log;

public class LogBnp extends Logger {

private static final LogName name = LogName.bnp;
	
	public static void logFatal(String msg) {
		printOutput(LogLevel.FATAL, msg, name);
	}

	public static void logError(String msg) {
		printOutput(LogLevel.ERROR, msg, name);
	}

	public static void logError(String msg, Exception e) {
		printOutput(LogLevel.ERROR, msg + "\n" + e.getMessage(), name);
	}

	public static void logWarning(String msg) {
		printOutput(LogLevel.WARNING, msg, name);
	}

	public static void logInfo(String msg) {
		printOutput(LogLevel.INFO, msg, name);
	}

	public static void logDebug(String msg) {
		printOutput(LogLevel.DEBUG, msg, name);
	}
}
