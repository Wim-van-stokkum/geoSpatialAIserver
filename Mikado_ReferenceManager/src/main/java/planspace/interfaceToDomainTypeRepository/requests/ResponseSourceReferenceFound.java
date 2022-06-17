package planspace.interfaceToDomainTypeRepository.requests;

import planspace.chefModelTypes.ChefType;
import planspace.compliance.SourceReferenceData;

public class ResponseSourceReferenceFound {
	SourceReferenceData theSourceReferenceData;
	String status;

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
	

	
	
}
