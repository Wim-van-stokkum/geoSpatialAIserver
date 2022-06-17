package planspace.agentinteraction;


/* ===========================================================================
De PlanSpace architectuur is gebaseerd op een multi-agent architectuur.
Agenten zijn de digitale twins van specialist-rollen.
Agenten wisselen onderling berichten uit zodra er een gebeurtenis of event optreed.
 
 Als agent wil ik op generiek (standaard) manier kunnen communiceren met mijn andere agenten. 
 Hiervoor is een generieke definitie nodig van type event berichten met hun inhoud

Er zijn twee hoofcategorieen eventtypes: 
-Interne Events : deze worden aangemaakt door interne PlanSpace agents en hebben voor de "buitenwereld"
 geen betekenis of zichtbaarheid.

-Externe Events: deze komen van de buitenwereld (buiten planspace) of gaan daar naar 
toe en hebben daar ook betekenis.

Beide zijn events. de AbstractEvent beschrijft de eigenschappen van beide hoofdcategorieen.

================================================================================*/

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import planspace.domainRules.RuleTemplates.EventContextDefinition;


public class AbstractEvent {

	// Elk event bevat een veld die aangeeft of het een intern of extern event is. 
	public enum t_EventSourceType{
		EXTERNAL,
		INTERNAL
}
	
	// Elk event heeft een lifecycle de fasering hierin wordt uitgedrukt met een status. 
	public enum t_EventStatus {
		CREATED,
		TO_PUBLISH,
		PUBLISHING,
		PUBLISHED
}
	
	public enum t_eventType {
		RELEVANT_EVENT_FOR_IMPACT_ANALYSIS, 
		HYPOTHECTICAL_IMPACT, 
		CLASSIFIED_IMPACT, DOMAIN_SPECIFIC_EXTERNAL_EVENTS,
		UNKNOWN

	}
	


	
	// De _id is een technisch ID waarmee een event uniek geidentificeerd kan worden. Heeft geen betekenis voor gebruikers
	protected String _id;
	protected String domainCode;
	
	// Is het een intern of extern event?
	protected t_EventSourceType sourceType;
	
	
	// Typering van Event
	protected String eventType;
	protected String eventTypeCategory;
	
	
	
	// De casecontext beschrijft in welke case (of keten van gebeurtenissen die ontstaan is obv een externe gebeurtenis) 
	// dit event is ontstaan
	protected EventCaseContext theEventCaseContext;
	
	// De inititor is de uniek ID van de agent of de persoon die verantwoordelijk is voor het communiceren van de gebeurtenis
	protected String initiatorID;
	
	// De EventContextDefinition beschrijft de inhoudelijke context/situatie waarbinnen de gebeurtenis is ontstaan.
	// Waar gaat de gebeurtenis over. Deze wordt uitgedrukt in 5W+H Dus welke activiteit (HOE) of welk subject (WAT),
	// is er geweest op welke moment (WANNEER) op welke locatie (WAAR) en welke partij (WIE) is daarbij betrokken geweest.
	// met welk beoogd doel  (WHY)
	
	protected EventContextDefinition theEventContext;
	
	

	public EventContextDefinition getTheEventContext() {
		//getter
		return theEventContext;
	}


	public void setTheEventContext(EventContextDefinition theEventContext) {
		//setter
		theEventContext.UpdateGeneratedDescription();
		theEventContext.UpdateDetailedGeneratedDescription();
		this.theEventContext = theEventContext;
		
	}

	

	// Het Scenario geeft aan of de EventContext die beschreven wordt in het event daarwerkelijk is gebeurd, of dat
	// deze zou kunnen gebeuren (What IF scenario)
	boolean forScenarioWhatIf = false; // scenario context
	boolean forScenarioIsForReal = false;
	
	String informalEventDescription;
	
	
	
	public void setInformalEventDescription(String informalEventDescription) {
		this.informalEventDescription = informalEventDescription;
	}

	@JsonIgnore
	public String getInformalEventDescription() {
		return informalEventDescription;
	}
	
	public t_EventSourceType getSourceType() {
		//getter
		return sourceType;
	}

	public AbstractEvent() {
		//constructor: standaard wordt er een CaseContext aangemaakt.
		theEventCaseContext = new EventCaseContext();

		
	}
	
	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		if (this.theEventCaseContext != null) {
			this.theEventCaseContext.setDomainCode(domainCode);
		}
		this.domainCode = domainCode;
	}
	// Getters en setters van de attributen De JsonGetter/JsonSetter instructies geven waar nodig aan naar welke
	// afwijkende Json namen het attribuut wordt gemapped.
	


	public String getEventType() {
		return eventType;
	}

    @JsonSetter("eventType")
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventTypeCategory() {
		return eventTypeCategory;
	}

    @JsonSetter("eventTypeCategory")
	public void setEventTypeCategory(String eventTypeCategory) {
		this.eventTypeCategory = eventTypeCategory;
	}


	public String getMyID() {
		return _id;
	}

    @JsonSetter("myID")
	public void  setMyID(String myID) {
		 _id = myID;
	}
    
	
	public boolean isForScenarioWhatIf() {
		//getter
		return forScenarioWhatIf;
	}

	public void setForScenarioWhatIf(boolean forScenarioWhatIf) {
		//setter
		this.forScenarioWhatIf = forScenarioWhatIf;
	}

	public boolean isForScenarioIsForReal() {
		//getter
		return forScenarioIsForReal;
	}

	public void setForScenarioIsForReal(boolean forScenarioIsForReal) {
		//setter
		this.forScenarioIsForReal = forScenarioIsForReal;
	}

	@JsonSetter("eventSourceType")
	public void setSourceType(t_EventSourceType sourceType) {
		//setter, in berichten wordt het JsonVeld eventSourceType hierop afgebeeld
		this.sourceType = sourceType;
	}

	
	// De status van bericht
	protected t_EventStatus eventStatus;
	
	
    @JsonGetter("eventSourceType")
	public t_EventSourceType getEventSourceType() {
    	//getter, in berichten wodt het JsonVeld eventSourceType hierbij aangemaakt
		return sourceType;
	}
	
	public t_EventStatus getEventStatus() {
		// getter
		return eventStatus;
	}

	
    @JsonSetter("eventStatus")
	public void setEventStatus(t_EventStatus eventStatus) {
    	//setter, in berichten wordt het JsonVeld eventStatus hierop afgebeeld
		this.eventStatus = eventStatus;
	}
   

	public EventCaseContext getTheEventCaseContext() {
		// getter
		return theEventCaseContext;
	}

	public void setTheEventCaseContext(EventCaseContext theEventCaseContext) {
		//setter
	
		this.theEventCaseContext = theEventCaseContext;
	}
	
	public String getCaseID() {
		//getter
		return this.theEventCaseContext.getCaseID();
	}


	
	public void setCaseID( String aNewCaseID) {
		//setter
		this.theEventCaseContext.setCaseID( aNewCaseID) ;
	}
	

	public String getCaseEventID() {
		return this.theEventCaseContext.getCaseEventID();	
	}


	
	public void setCaseEventID(String aCaseEventID) {
		// setter
		this.theEventCaseContext.setCaseEventID(aCaseEventID);
	}
	
	public String getInitiatorID() {
		// getter
		return initiatorID;
	}



	public void setInitiatorID(String initiatorID) {
		//setter
		this.initiatorID = initiatorID;
	}


    
}
