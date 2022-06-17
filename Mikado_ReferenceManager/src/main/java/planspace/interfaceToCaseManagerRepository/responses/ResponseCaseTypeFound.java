package planspace.interfaceToCaseManagerRepository.responses;

import planspace.caseModel.CaseType;

public class ResponseCaseTypeFound {
    CaseType theCaseType;
	String status;
	String domainCode;
	
	
	
	public CaseType getTheCaseType() {
		return theCaseType;
	}
	public void setTheCaseType(CaseType theCaseType) {
		this.theCaseType = theCaseType;
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
