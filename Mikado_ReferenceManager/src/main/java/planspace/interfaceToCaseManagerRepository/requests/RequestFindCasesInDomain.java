package planspace.interfaceToCaseManagerRepository.requests;

public class RequestFindCasesInDomain {
	
	protected String domainCode;
	protected String caseTypeID;
	
	
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	public String getCaseTypeID() {
		return caseTypeID;
	}
	public void setCaseTypeID(String caseTypeID) {
		this.caseTypeID = caseTypeID;
	}

	
	
	
	
}
