package comptes.util.log;

	public enum LogLevel {

		FATAL(0),ERROR(1),WARNING(2), INFO(3),DEBUG(4) ;
		
		private int lvl;
		private LogLevel(int level) {
			this.lvl = level;
		}
		
		public int getLevel(){
			return lvl;
		}
	}

