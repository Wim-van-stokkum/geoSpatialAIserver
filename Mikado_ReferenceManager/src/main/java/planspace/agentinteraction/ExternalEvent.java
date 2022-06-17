package planspace.agentinteraction;

//SHARED
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSetter;

import planspace.instanceModel.DomainInstance;

public class ExternalEvent extends AbstractEvent {

	/*
	 * =========================================================================
	 * 
	 * De PlanSpace architectuur is gebaseerd op een multi-agent architectuur.
	 * Agenten zijn de digitale twins van specialist-rollen. Agenten wisselen
	 * onderling berichten uit zodra er een gebeurtenis of event optreed.
	 * 
	 * Als agent wil ik op generiek (standaard) manier kunnen communiceren met mijn
	 * andere agenten. Hiervoor is een generieke definitie nodig van type event
	 * berichten met hun inhoud
	 * 
	 * Er zijn twee hoofcategorieen eventtypes: -Interne Events : deze worden
	 * aangemaakt door interne PlanSpace agents en hebben voor de "buitenwereld"
	 * geen betekenis of zichtbaarheid.
	 * 
	 * -Externe Events: deze komen van de buitenwereld (buiten planspace) of gaan
	 * daar naar toe en hebben daar ook betekenis.
	 * 
	 * De class beschrijft de specifieke eigenschappen van een Extern Event
	 * ============================================================================
	 */

	/*
	 * Externe events vinden plaats in een domein Het eventType geeft aan om wat
	 * voor soort event het gaat (vraag aanvullend gegeven, noitificatie case
	 * aangemaakt etc) De categorie wordt gebruikt om de typen op te delen in
	 * groepen (bijv case gerelateerde events, impact gerelateerde events etc)
	 * 
	 * De typen en categorieen Interne events zullen naar verwachting afwijken van
	 * die van interne events. vandaar dat deze op dit niveau gemodelleerd zijn.
	 */

	public ExternalEvent() {
		super();

		// constructor: events krijgen hier een uniek id
		// de lifecycle wordt op created gezet
		_id = UUID.randomUUID().toString();
		this.sourceType = AbstractEvent.t_EventSourceType.EXTERNAL;
		this.setEventStatus(t_EventStatus.CREATED);

		// de case context wordt geinitiatiseerd uit voorzorg wordt de context op
		// onbekend gezet.
		this.getTheEventCaseContext().setCaseEventID(_id);
		this.getTheEventCaseContext().setCaseID("unknown");

	}

	public void duplicateContextToInternalEvent(InternalEvent anInternalEvent) {
		// Deze methode accepteerd een (ander) intern event: anInternalEvent
		// Deze methode kopieert de eigen (externe) Casecontext naar deze ontvangen
		// andere intern event.

		// Tevens wordt de inhoudelijke event case context gekopieerd.

		// Hiermee kan een intern event qua context worden aangemaakt op basis van het
		// eigen extern event.

		DomainInstance anInstance;

		// De case ID wordt gekopieerd inclusief de domein referentie

		anInternalEvent.setCaseEventID(this.getCaseEventID());
		anInternalEvent.setCaseID(this.getCaseID());
		anInternalEvent.setDomainCode(this.getDomainCode());
		if (this.informalEventDescription != null) {
			anInternalEvent.setInformalEventDescription(this.informalEventDescription);
		}
		else
		{
			anInternalEvent.setInformalEventDescription("Event " + this.getCaseEventID());
		}

		// De EventContextDefinition beschrijft de inhoudelijke context/situatie
		// waarbinnen de gebeurtenis is ontstaan.
		// Waar gaat de gebeurtenis over. Deze wordt uitgedrukt in 5W+H Dus welke
		// activiteit (HOE) of welk subject (WAT),
		// is er geweest op welke moment (WANNEER) op welke locatie (WAAR) en welke
		// partij (WIE) is daarbij betrokken geweest.
		// met welk beoogd doel (WHY)
		// Hieronder wordt de inhoudelijke EventCOntextDefinition van het eigen externe
		// event overgezet naar de
		// ontvangen interne Event.

		// HOW
		anInstance = this.getTheEventContext().getTheActivityCausingEvent();
		if (anInstance != null) {
			anInternalEvent.getTheEventContext().setTheActivityCausingEvent(anInstance);
		}

		// WHAT

		anInstance = this.getTheEventContext().getTheSubjectInvolved();
		if (anInstance != null) {
			anInternalEvent.getTheEventContext().setTheSubjectInvolved(anInstance);

		}

		// WHERE
		anInstance = this.getTheEventContext().getTheLocation();
		if (anInstance != null) {
			anInternalEvent.getTheEventContext().setTheLocation(anInstance);

		}

		// WHO
		anInstance = this.getTheEventContext().getTheActor();
		if (anInstance != null) {
			anInternalEvent.getTheEventContext().setTheActor(anInstance);

		}

		// WHEN
		anInstance = this.getTheEventContext().getTheMoment();
		if (anInstance != null) {
			anInternalEvent.getTheEventContext().setTheMoment(anInstance);

		}

		// WHY
		anInstance = this.getTheEventContext().getTheGoal();
		if (anInstance != null) {
			anInternalEvent.getTheEventContext().setTheGoal(anInstance);

		}

		// SCENARIO
		anInternalEvent.setForScenarioWhatIf(this.isForScenarioWhatIf());
		anInternalEvent.setForScenarioIsForReal(this.isForScenarioIsForReal());

	}

}
