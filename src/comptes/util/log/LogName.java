package comptes.util.log;

public enum LogName {
	defaut(LogLevel.INFO),
	operation(LogLevel.INFO),
	echeancier(LogLevel.INFO),
	bnp(LogLevel.INFO),
	categorie(LogLevel.INFO),
	tiers(LogLevel.INFO), 
	rappro(LogLevel.INFO);
	
	private LogName(LogLevel level) {
		this.level = level;
	}

	private LogLevel level;

	public LogLevel getLogLevel() {
		return level;
	}
	
}
