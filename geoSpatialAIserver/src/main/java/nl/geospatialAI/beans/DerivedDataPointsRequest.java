package nl.geospatialAI.beans;

public class DerivedDataPointsRequest {
	int referenceID;
	AssessRequestContext.tUsertype usertype=AssessRequestContext.tUsertype.AANVRAGER;
	
	
	public int getReferenceID() {
		return referenceID;
	}
	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}
	public AssessRequestContext.tUsertype getUsertype() {
		return usertype;
	}
	public void setUsertype(AssessRequestContext.tUsertype usertype) {
		this.usertype = usertype;
	}

	
}
