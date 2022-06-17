package planspace.interfaceToCaseManagerRepository.requests;

public class RequestFindCaseByID {
	
	protected String domainCode;
	protected String caseID;
	
	
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	
	
	
	
}
