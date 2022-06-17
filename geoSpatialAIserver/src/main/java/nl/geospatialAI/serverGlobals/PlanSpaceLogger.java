package nl.geospatialAI.serverGlobals;



public class PlanSpaceLogger {

	public enum t_LogLevel {
		HIGH_DETAIL
	}

	private static PlanSpaceLogger stdLogger = null;

	private t_LogLevel mylogLevel;

	public static PlanSpaceLogger getInstance() {

		if (stdLogger == null) {

			// Create Loghandler
			stdLogger = new PlanSpaceLogger();
			stdLogger.setLogLevel(PlanSpaceLogger.t_LogLevel.HIGH_DETAIL);

		}
		return stdLogger;

	}

	public t_LogLevel getLogLevel() {
		return mylogLevel;
	}

	public void setLogLevel(t_LogLevel mylogLevel) {
		this.mylogLevel = mylogLevel;
	}

	public void log(String theMessage) {
		if (this.getLogLevel() == PlanSpaceLogger.t_LogLevel.HIGH_DETAIL) {
			System.out.println(theMessage);
		}
	}

}
