package planspace.interfaceToCaseManagerRepository.requests;

public class RequestFindCasetypeByTypeName {
	
	protected String domainCode;
	protected String caseTypeCode;
	
	
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	public String getCaseTypeCode() {
		return caseTypeCode;
	}
	public void setCaseTypeCode(String caseTypeCode) {
		this.caseTypeCode = caseTypeCode;
	}
	
	
}
