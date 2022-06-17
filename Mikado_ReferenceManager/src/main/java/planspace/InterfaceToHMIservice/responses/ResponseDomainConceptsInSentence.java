package planspace.InterfaceToHMIservice.responses;

import java.util.List;

import speechInterface.WordClassification;

public class ResponseDomainConceptsInSentence {
	String status;
	String domainCode;
	

	String analyzedSentence;
	private List<WordClassification> conceptPropertiesFound;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getAnalyzedSentence() {
		return analyzedSentence;
	}

	public void setAnalyzedSentence(String analyzedSentence) {
		this.analyzedSentence = analyzedSentence;
	}

	public List<WordClassification> getConceptPropertiesFound() {
		return conceptPropertiesFound;
	}

	public void setConceptPropertiesFound(List<WordClassification> conceptPropertiesFound) {
		this.conceptPropertiesFound = conceptPropertiesFound;
	}







}