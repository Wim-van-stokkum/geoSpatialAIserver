package planspace.utils;

import org.springframework.beans.factory.annotation.Value;

/* Dit is utility/helper class. Deze wordt binnen de PlanceSpace componten gebruikt om 
 * zaken te loggen.
 * 
 *  In huidige vom 18/2/2021 is deze component zeer rudimentaire qua implementatie. Berichten worden nu
 *  op System.out publiceert.
 *  
 *  Doel si echter een interface te hebben voor loggingen zodat we deze kunnen uitbreiden in toekomst naar:
 *  - Loggen functioneel/technische fouten
 *  - Log levels 
 *  - wegschrijven naar log files
 *  etc etc...
 */

public class PlanSpaceLogger {
	


	public enum t_LogLevel {
		HIGH_DETAIL
	}

	private static PlanSpaceLogger stdLogger = null;

	private t_LogLevel mylogLevel;

	/* Types of logmessages can be set */
	/* by enabling these debugs will be shown per category */
	private boolean showRuleDebug = true;
	private boolean showError = true;
	private boolean showRegularDebug = true;
	private boolean showconfigIO = true;
	private boolean showKafkaIO = true;
	private boolean showSetUp = true;
	private boolean publishTraceEvent = true;


	private boolean showLUI = true;

	private String idOfMyComponent = "null";

	public static PlanSpaceLogger getInstance() {

		if (stdLogger == null) {

			// Create Loghandler
			System.out.println("CREATING LOGGER");
			stdLogger = new PlanSpaceLogger();
			stdLogger.setLogLevel(PlanSpaceLogger.t_LogLevel.HIGH_DETAIL);

		}
		return stdLogger;

	}

	
	



	public boolean isPublishTraceEvent() {
		return publishTraceEvent;
	}

	public void setPublishTraceEvent(boolean publishTraceEvent) {
		this.publishTraceEvent = publishTraceEvent;
	}

	public String getIdOfMyComponent() {
		return idOfMyComponent;
	}

	public void setIdOfMyComponent(String idOfMyComponent) {
		this.idOfMyComponent = idOfMyComponent;
	}

	public boolean isShowLUI() {
		return showLUI;
	}

	public void setShowLUI(boolean showLUI) {
		this.showLUI = showLUI;
	}

	public boolean isShowconfigIO() {
		return showconfigIO;
	}

	public boolean isShowSetUp() {
		return showSetUp;
	}

	public void setShowSetUp(boolean showSetUp) {
		this.showSetUp = showSetUp;
	}

	public void setShowconfigIO(boolean showconfigIO) {
		this.showconfigIO = showconfigIO;
	}

	public boolean isShowKafkaIO() {
		return showKafkaIO;
	}

	public void setShowKafkaIO(boolean showKafkaIO) {
		this.showKafkaIO = showKafkaIO;
	}

	public boolean isShowError() {
		return showError;
	}

	public void setShowError(boolean showError) {
		this.showError = showError;
	}

	public boolean isShowRegularDebug() {
		return showRegularDebug;
	}

	public void setShowRegularDebug(boolean showRegularDebug) {
		this.showRegularDebug = showRegularDebug;
	}

	public boolean isShowRuleDebug() {
		return showRuleDebug;
	}

	public void setShowRuleDebug(boolean showRuleDebug) {
		this.showRuleDebug = showRuleDebug;
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

	public void log_Debug(String theMessage) {
		if (this.showRegularDebug == true) {
			if (this.getLogLevel() == PlanSpaceLogger.t_LogLevel.HIGH_DETAIL) {
				System.out.println("[DEBUG planspace]:" + theMessage);
			}
		}
	}

	public void log_Error(String theMessage) {
		if (this.showError == true) {
			if (this.getLogLevel() == PlanSpaceLogger.t_LogLevel.HIGH_DETAIL) {
				System.out.println("[ERROR planspace]:" + theMessage);
			}
		}

	}

	public void log_RuleDebug(String theMessage) {
		/* Logging rule evaluation details */
		if (this.showRuleDebug == true) {
			if (this.getLogLevel() == PlanSpaceLogger.t_LogLevel.HIGH_DETAIL) {
				System.out.println("[RULE DEBUG planspace]:" + theMessage);
			}
		}
	}

	public void log_ConfigIO(String theMessage) {
		/* Logging IO to config databasw evaluation details */
		if (this.showconfigIO == true) {
			if (this.getLogLevel() == PlanSpaceLogger.t_LogLevel.HIGH_DETAIL) {
				System.out.println("[CONFIG DB IO planspace]:" + theMessage);
			}
		}
	}

	public void log_KafkaIO(String theMessage) {
		/* Logging IO to config databasw evaluation details */
		if (this.showKafkaIO == true) {
			if (this.getLogLevel() == PlanSpaceLogger.t_LogLevel.HIGH_DETAIL) {
				System.out.println("[KAFKA IO planspace]:" + theMessage);
			}
		}
	}

	public void log_DebugSetup(String theMessage) {
		/* Logging IO to config databasw evaluation details */
		if (this.showSetUp == true) {
			if (this.getLogLevel() == PlanSpaceLogger.t_LogLevel.HIGH_DETAIL) {
				System.out.println("[Agent set up]:" + theMessage);
			}
		}

	}

	public void log_LUI(String theMessage) {
		/* Logging IO to Natural Language Parsing messages */
		if (this.showLUI == true) {
			if (this.getLogLevel() == PlanSpaceLogger.t_LogLevel.HIGH_DETAIL) {
				System.out.println("[NLP parsing]:" + theMessage);
			}
		}
	}






	
	
}
