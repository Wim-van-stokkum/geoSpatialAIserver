package nl.geospatialAI.Case;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





public class CasesDAO {


	private HashMap<Integer, Case> allCasesByCaseNo;

	private List<Case> caseRecords;

	private static CasesDAO stdregd = null;

	private CasesDAO() {

		caseRecords = new ArrayList<Case>();
		allCasesByCaseNo = new HashMap<Integer, Case>();
	}

	public static CasesDAO getInstance() {

		if (stdregd == null) {

			stdregd = new CasesDAO();

			return stdregd;

		}

		else {

			return stdregd;

		}

	}

	public void add(Case aCase) {

		caseRecords.add(aCase);
		this.allCasesByCaseNo.put(aCase.getCaseNo(), aCase);
	}

	
	public List<Case> getCases() {

		return this.caseRecords;

	}
	
	public Case GetCaseByCaseNo(int aCaseNo) {

		return this.allCasesByCaseNo.get(aCaseNo);
	}

}

