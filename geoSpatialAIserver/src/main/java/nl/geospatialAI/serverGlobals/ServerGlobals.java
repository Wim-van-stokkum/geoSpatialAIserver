package nl.geospatialAI.serverGlobals;

import nl.geospatialAI.Assessment.PolicyLibrary;
import nl.geospatialAI.Case.CasesDAO;

public class ServerGlobals {

	private static ServerGlobals stdServ = null;
	private PolicyLibrary thePolicyLibrary;
	private CasesDAO caseRegistration;

	public static ServerGlobals getInstance() {

		if (stdServ == null) {

			stdServ = new ServerGlobals();
			stdServ.caseRegistration =   CasesDAO.getInstance();

			return stdServ;

		}

		else {

			return stdServ;

		}

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

}
