package comptes.util.log;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Logger {

	private static final LogName name = LogName.defaut;
	
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

	protected static void printOutput(LogLevel logLevel, String msg, LogName logName) {
		if (logLevel.getLevel() <= logName.getLogLevel().getLevel()) {
			StackTraceElement e = Thread.currentThread().getStackTrace()[3];
			printOutput(e.getFileName(), e.getClassName(), e.getMethodName(), e.getLineNumber(), logLevel,
					msg);
		}
	}

	private static void printOutput(String fileName, String className, String methodName, int lineNumber,
			LogLevel logLevel, String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append(logLevel).append(" ").append(className).append(".").append(methodName).append("(").append(fileName)
				.append(":").append(lineNumber).append(") : ").append(msg);
		String finalMsg = sb.toString();
		
		Color color = BLACK;
		switch(logLevel) {
		case DEBUG:
			color = CYAN;
			break;
		case ERROR:
			color = RED;
			break;
		case FATAL:
			color = RED;
			break;
		case INFO:
			color = BLACK;
			break;
		case WARNING:
			color = MAGENTA;
			break;
		default:
			break;
		
		}
			finalMsg = ansi().eraseScreen().fg(color).a(finalMsg).reset().toString();
		System.out.println(finalMsg);

	}
	
	public static void main(String[] args) {
		logDebug("Pouet");
		logInfo("Pouet");
		logWarning("Pouet");
		logError("Pouet");
		logFatal("Pouet");
		LogOperation.logDebug("héhé");
		
	}
}


//UTILISATION
//Logger.logDebug("format specifié: " + date);
//Logger.logInfo("format specifié: " + date);
//Logger.logWarning("format specifié: " + date);
//Logger.logError("format specifié: " + date);
//Logger.logFatal("format specifié: " + date);
