package planspace.interfaceToCaseManagerRepository.responses;

import java.util.List;

import planspace.caseModel.Case;

public class ResponseFoundCases {
	private List<Case> foundCases;
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

	public List<Case> getFoundCases() {
		return foundCases;
	}

	public void setFoundCases(List<Case> foundCases) {
		this.foundCases = foundCases;
	}

	
	
}
