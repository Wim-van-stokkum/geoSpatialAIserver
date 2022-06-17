package planspace.beans.sourceReferences;

import planspace.chefModelTypes.ChefType;
import planspace.compliance.SourceReferenceData;

public class ResponseSourceReferenceFound {
	SourceReferenceData theSourceReferenceData;
	String status;
	String domainCode;
	public SourceReferenceData getTheSourceReferenceData() {
		return theSourceReferenceData;
	}
	public void setTheSourceReferenceData(SourceReferenceData theSourceReferenceData) {
		this.theSourceReferenceData = theSourceReferenceData;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	
	
}
