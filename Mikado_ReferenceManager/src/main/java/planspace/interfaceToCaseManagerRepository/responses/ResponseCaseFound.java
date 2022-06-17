package planspace.interfaceToCaseManagerRepository.responses;

import planspace.caseModel.Case;

public class ResponseCaseFound {
	String status;
	Case theCase;
	String domainCode;
	
	
	
	
	public Case getTheCase() {
		return theCase;
	}
	public void setTheCase(Case theCase) {
		this.theCase = theCase;
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
