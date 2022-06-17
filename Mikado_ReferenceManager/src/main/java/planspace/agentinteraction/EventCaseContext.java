package planspace.agentinteraction;


/*	De casecontext beschrijft in welke case (of keten van gebeurtenissen die ontstaan is obv een externe gebeurtenis) 
    dit event is ontstaan

 */
public class EventCaseContext {
	
	// Een caseID verwijst naar unieke (externe ID) van een case in het/een casemanagement systeem 
	// wordt vaak pas bekend nadat case is aangemaakt
	protected String caseID;
	
	
	// Een caseID verwijst naar unieke (externe ID) van een extern event dat de trigger is voor het afhandelproces
	// voor dit event wordt ook (later) een case (met caseID) aangemaakt in  het/een casemanagement systeem 
	// Dit eventID wordt doorgegeven in alle vervolg events die ontstaan tijdens de afhandeling van het externe event
	// De CaseManager agent zal dit ook gebruiken om relevante events/messages in dossier (case) op te gaan slaan
	
	protected String caseEventID;
	
	protected String domainCode;
	
	public EventCaseContext() {
		this.setCaseID("unknown");
		this.setCaseEventID("unknown");
		this.setDomainCode("unknown");
	}
	
	
	//getters en setters
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String aCaseID) {
		this.caseID = aCaseID;
	}
	public String getCaseEventID() {
		return caseEventID;
	}
	public void setCaseEventID(String aCaseEventID) {
		caseEventID = aCaseEventID;
	}


	public String getDomainCode() {
		return domainCode;
	}


	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	
	
	
}
