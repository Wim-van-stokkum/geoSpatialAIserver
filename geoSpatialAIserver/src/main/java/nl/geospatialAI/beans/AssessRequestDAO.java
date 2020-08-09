package nl.geospatialAI.beans;

import java.util.ArrayList;

import java.util.List;

public class AssessRequestDAO {

	private List<AssessRequest> assessRequestRecords;

	private static AssessRequestDAO stdregd = null;

	private AssessRequestDAO() {

		assessRequestRecords = new ArrayList<AssessRequest>();

	}

	public static AssessRequestDAO getInstance() {

		if (stdregd == null) {

			stdregd = new AssessRequestDAO();

			return stdregd;

		}

		else {

			return stdregd;

		}

	}

	public void add(AssessRequest req) {

		assessRequestRecords.add(req);

	}

	public String upDateRequest(AssessRequest std) {

		for (int i = 0; i < assessRequestRecords.size(); i++)

		{

			AssessRequest aReq = assessRequestRecords.get(i);

			if (aReq.getTheContext().getReferenceID().equals(aReq.getTheContext().getReferenceID())) {

				assessRequestRecords.set(i, aReq);// update the new record

				return "Update request successful";

			}

		}

		return "Update un-successful";

	}

	public String deleteRequest(String refId) {

		for (int i = 0; i < assessRequestRecords.size(); i++)

		{

			AssessRequest aReq = assessRequestRecords.get(i);

			if (aReq.getTheContext().getReferenceID().equals(refId)) {

				assessRequestRecords.remove(i);// delete the record

				return "Delete successful";

			}

		}

		return "Delete un-successful";

	}

	public List<AssessRequest> getAssessRequests() {

		return assessRequestRecords;

	}

}