package nl.geospatialAI.serverGlobals;

import nl.geospatialAI.Assessment.PolicyLibrary;
import nl.geospatialAI.Case.CasesDAO;

public class ServerGlobals {
	public enum t_LogLevel {
		HIGH_DETAIL
	}

	private static ServerGlobals stdServ = null;
	private PolicyLibrary thePolicyLibrary;
	private CasesDAO caseRegistration;
	private t_LogLevel logLevel;

	public static ServerGlobals getInstance() {

		if (stdServ == null) {

			stdServ = new ServerGlobals();
			stdServ.caseRegistration = CasesDAO.getInstance();
			stdServ.setLogLevel(ServerGlobals.t_LogLevel.HIGH_DETAIL);
			return stdServ;

		}

		else {

			return stdServ;

		}

	}

	public t_LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(t_LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public CasesDAO getCaseRegistration() {
		return caseRegistration;
	}

	public PolicyLibrary getPolicyLibrary() {

		if (thePolicyLibrary == null) {

			thePolicyLibrary = new PolicyLibrary();

			return thePolicyLibrary;

		}

		else {

			return thePolicyLibrary;

		}

	}

	public void log(String theMessage) {
		if (this.getLogLevel() == ServerGlobals.t_LogLevel.HIGH_DETAIL) {
			System.out.println(theMessage);
		}
	}

}