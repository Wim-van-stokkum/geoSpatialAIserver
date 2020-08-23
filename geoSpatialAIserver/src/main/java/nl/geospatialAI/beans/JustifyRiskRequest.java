package nl.geospatialAI.beans;

import nl.geospatialAI.beans.AssessRequestContext.tUsertype;

public class JustifyRiskRequest {
	int referenceID;
	int riskRefID;

	AssessRequestContext.tUsertype usertype=AssessRequestContext.tUsertype.AANVRAGER;

	public AssessRequestContext.tUsertype getUsertype() {
		return this.usertype;
	}

	public void setUsertype(AssessRequestContext.tUsertype aUserType) {
		this.usertype = aUserType;
	}

	public int getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}

	public int getRiskRefID() {
		return riskRefID;
	}

	public void setRiskRefID(int riskRefID) {
		this.riskRefID = riskRefID;
	}

}
