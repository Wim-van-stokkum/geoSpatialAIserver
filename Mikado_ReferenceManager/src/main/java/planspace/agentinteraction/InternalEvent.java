package planspace.agentinteraction;

/* =========================================================================
De PlanSpace architectuur is gebaseerd op een multi-agent architectuur.
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

De class beschrijft dee specifieke eigenschappen van een intern Event
 ============================================================================*/


import java.util.UUID;

import planspace.instanceModel.DomainInstance;

public class InternalEvent extends AbstractEvent {

	// Interne events vinden plaats in een domein
	// Het eventType geeft aan om wat voor soort event het gaat  (vraag aanvullend gegeven, noitificatie case aangemaakt etc)
	// De categorie wordt gebruikt om de typen op te delen in groepen (bijv case gerelateerde events, impact gerelateerde events etc)
	
	//De typen en categorieen Interne events zullen naar verwachting afwijken van die van externe events. vandaar dat deze
	// op dit niveau gemodelleerd is.


	
	
	public InternalEvent() {
		super();
		
		// constructor: events krijgen hier een uniek id
		// de lifecycle wordt op created gezet
		
		_id = UUID.randomUUID().toString();
		this.sourceType = AbstractEvent.t_EventSourceType.INTERNAL;
		this.setEventStatus(t_EventStatus.CREATED);

	}

	// Getters en setters van de attributen De JsonGetter/JsonSetter instructies geven waar nodig aan naar welke
	// afwijkende Json namen het attribuut wordt gemapped.
	


	public String getMyID() {
		return _id;
	}
	
		
	
	
	
	public void duplicateContextToInternalEvent(InternalEvent anInternalEvent) {
		// Deze methode accepteerd een (ander) intern event: anInternalEvent
		// Deze methode kopieert de eigen Casecontext naar deze ontvangen andere intern event.
		
		// Hiermee kan een intern event qua context worden aangemaakt op basis van het 
		// eigen intern event.
		
		
		// Kopieer mijn case ID en domain code naar Intern Event ontvangen
		anInternalEvent.setCaseEventID(this.getCaseEventID());
		anInternalEvent.setCaseID(this.getCaseID());
		
		anInternalEvent.setDomainCode(this.getDomainCode());
		anInternalEvent.setTheEventCaseContext(this.getTheEventCaseContext());
		

		// Kopieer mijn scenario ID naar Intern Event ontvangen
		anInternalEvent.setForScenarioWhatIf(this.isForScenarioWhatIf());
		anInternalEvent.setForScenarioIsForReal(this.isForScenarioIsForReal());
		
		
		
		
	}

}
