package planspace.interfaceToCaseManagerRepository.responses;

import java.util.List;

import planspace.caseModel.CaseType;

public class ResponseFoundCaseTypes {
	private List<CaseType> foundCaseTypes;
	private String domainCode;
	private String status;
	
	
	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CaseType> getFoundCaseTypes() {
		return foundCaseTypes;
	}

	public void setFoundCaseTypes(List<CaseType> foundCaseTypes) {
		this.foundCaseTypes = foundCaseTypes;
	}
	
	
}
