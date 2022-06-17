package planspace.interfaceToDomainTypeRepository.requests;

import planspace.compliance.SourceReferenceData;

public class RequestCreateSourceReference {

	SourceReferenceData newSourceReferenceData;
	String theDomain;

	public SourceReferenceData getNewSourceReferenceData() {
		return newSourceReferenceData;
	}

	public void setNewSourceReferenceData(SourceReferenceData newSourceReferenceData) {
		this.newSourceReferenceData = newSourceReferenceData;
	}

	public String getTheDomain() {
		return theDomain;
	}

	public void setTheDomain(String theDomain) {
		this.theDomain = theDomain;
	}
}
