package comptes.util.log;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Logger {

	protected LogName name = LogName.defaut;
	protected static Logger logger;
	
	
	public static void logFatal(String msg) {
		printOutput(LogLevel.FATAL, msg);
	}

	public static void logError(String msg) {
		printOutput(LogLevel.ERROR, msg);
	}

	public static void logError(String msg, Exception e) {
		printOutput(LogLevel.ERROR, msg + "\n" + e.getMessage());
	}

	public static void logWarning(String msg) {
		printOutput(LogLevel.WARNING, msg);
	}

	public static void logInfo(String msg) {
		printOutput(LogLevel.INFO, msg);
	}

	public static void logDebug(String msg) {
		printOutput(LogLevel.DEBUG, msg);
	}

	private static void printOutput(LogLevel logLevel, String msg) {
		init();
		if (logLevel.getLevel() <= logger.name.getLogLevel().getLevel()) {
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
	
	public static void init() {
		if(logger == null) {
			logger = new Logger();
		}
	}
	public static void main(String[] args) {
		logDebug("Pouet");
		logInfo("Pouet");
		logWarning("Pouet");
		logError("Pouet");
		logFatal("Pouet");
		
	}
}


//UTILISATION
//Logger.logDebug("format specifié: " + date);
//Logger.logInfo("format specifié: " + date);
//Logger.logWarning("format specifié: " + date);
//Logger.logError("format specifié: " + date);
//Logger.logFatal("format specifié: " + date);
