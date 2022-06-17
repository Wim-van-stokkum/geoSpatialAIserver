package planspace.domainRules.RuleTemplates;
//shared

import planspace.instanceModel.DomainInstance;
/* Planspace heeft een multi-agent architectuur
 * Een of meerdere agents luisteren naar gestandaardiseerde berichten waarin gebeurtenissen worden gecommuniceerd
 * 
 * Afhankelijk van inhoud bericht besluiten ze of een gebeurtenis al dan niet interessant is voor hun doel
 * Mocht het interessant zijn dan kunnen ze een bericht interpreteren en een inkomend event classificeren als een 
 * geinterpreteerd event.  Regenen in Almere  wordt dan bijvoorbeeld geinterpreteerd als Wateroverlast in flevoland
 * 
 * 
 * Voor het interpreteren van events worden regels gebruikt. 
 * Deze class definieert zo'n regel. Een regel heeft een 
 * - Type (waarvoor wordt hij gebruikt): 
 * 		voor interpreteren externe events van type X
 * 		voor interpreteren interne events van type Y
 * 		voor interpreteren resultaat na aanroep Brein
 * 		
 *  Deze class Configureert het algemene gedrag van een Event Interpretatie regels 
 *  
 *  Deze rule accepteert een te onderzoeken Event. Vervolgens analyseert de rule de event context 
 *  waarin dit event is opgetreden.
 *  
 *  De event context beschrijft 1 of meer van de volgende Event Context Aspecten:
 *  - HOE het event is ontstaan
 *  - WAT geraakt wordt door het event
 *  - WIE welke partij betrokken was
 *  - WANNEER het event optrad
 *  - WAAR het event heeft plaatsgevonden
 *  - WAAROM het event heeft plaats gevonden
 *  
 *  De regels stelt voorwaarde aan bovenstaande aspecten.
 *  Deze moeten ingevuld zijn wil het event relevant gevonden worden.
 *  
 *  Vervolgens interpreteert de regels het event. Hieruit ontstaat de eventcontext na interpretatie
 *  - Deze kan gelijk zijn aan het bestudeerde event. 
 *  - Deze kan verschillen Gebeurtenis A wordt intern geinterpreteert als een Gebeurtenis B 
 *  
 *  Voor het aanmaken van de geinterpreteerde gebeurtenis wordt een EventContextDefinitionTemplate gebruikt 
 *  Deze beschrijft voor elk van de EventContext Aspecten: WIE, WAT, WAAR, WANNEER, WAAROM en HOE hoe
 *  deze moet worden ingevuld gegeven het inkomend event:
 *  -	Neem over van een (ander) Aspect uit bericht
 *  - 	Vul aspect in met ander concept
 */

public class EventContextDefinitionTemplate {

	/* De template bestaat uit een aspect Template per aspect 5W + H
	 * 
	 */
	//HOW
	AspectTemplate aspectTemplate_for_ActivityCausingEvent = null;

	//WHAT
	AspectTemplate aspectTemplate_for_SubjectInvolved = null;
	
	//WHERE
	AspectTemplate aspectTemplate_for_Location = null;

	
	//WHO
	AspectTemplate aspectTemplate_for_Actor = null;

	
	//WHEN
	AspectTemplate aspectTemplate_for_Moment = null;

	
	//WHY
	AspectTemplate aspectTemplate_for_Goal = null;


	//getters en setters
	public AspectTemplate getAspectTemplate_for_ActivityCausingEvent() {
		return aspectTemplate_for_ActivityCausingEvent;
	}


	public void setAspectTemplate_for_ActivityCausingEvent(AspectTemplate aspectTemplate_for_ActivityCausingEvent) {
		this.aspectTemplate_for_ActivityCausingEvent = aspectTemplate_for_ActivityCausingEvent;
	}


	public AspectTemplate getAspectTemplate_for_SubjectInvolved() {
		return aspectTemplate_for_SubjectInvolved;
	}


	public void setAspectTemplate_for_SubjectInvolved(AspectTemplate aspectTemplate_for_SubjectInvolved) {
		this.aspectTemplate_for_SubjectInvolved = aspectTemplate_for_SubjectInvolved;
	}


	public AspectTemplate getAspectTemplate_for_Location() {
		return aspectTemplate_for_Location;
	}


	public void setAspectTemplate_for_Location(AspectTemplate aspectTemplate_for_Location) {
		this.aspectTemplate_for_Location = aspectTemplate_for_Location;
	}


	public AspectTemplate getAspectTemplate_for_Actor() {
		return aspectTemplate_for_Actor;
	}


	public void setAspectTemplate_for_Actor(AspectTemplate aspectTemplate_for_Person) {
		this.aspectTemplate_for_Actor = aspectTemplate_for_Person;
	}


	public AspectTemplate getAspectTemplate_for_Moment() {
		return aspectTemplate_for_Moment;
	}


	public void setAspectTemplate_for_Moment(AspectTemplate aspectTemplate_for_Moment) {
		this.aspectTemplate_for_Moment = aspectTemplate_for_Moment;
	}


	public AspectTemplate getAspectTemplate_for_Goal() {
		return aspectTemplate_for_Goal;
	}


	public void setAspectTemplate_for_Goal(AspectTemplate aspectTemplate_for_Goal) {
		this.aspectTemplate_for_Goal = aspectTemplate_for_Goal;
	}

	
	//FACTORY

	public EventContextDefinition createEventContextDefinition(EventContextDefinition theConditionEventContext) {
		/* Deze methode maakt een nieuwe EventContextDefinition aan op basis van de 
		 * instructies AspectTemplates per Aspect
		 * Voor elk van de EventContext Aspecten: WIE, WAT, WAAR, WANNEER, WAAROM en HOE specificeren deze aspectTemplates
          hoe deze op basis van aangeleverde gebeurtenis moeten worden samengesteld."
            -	Neem over van een (ander) Aspect uit bericht
   			- 	Vul aspect in met ander specifiek voorgedefinieerd concept (ObjectType)
		 */
		
		EventContextDefinition theEventContextDefinition;
		AspectTemplate anAspectTemplate;
		DomainInstance aDomainInstance;
		
		// Aanmaken nieuwe contextDefinitie
		theEventContextDefinition = new EventContextDefinition();
		
		//wat
		
		// ophalen aspectTemplate voor aspect
		anAspectTemplate = this.getAspectTemplate_for_SubjectInvolved();
		if (anAspectTemplate != null) {
			// Indien aspectTemplate is gespecifieerd: voor deze uit en maak het aspect aan conform template
			aDomainInstance = anAspectTemplate.createDomainInstance(theConditionEventContext);
			theEventContextDefinition.setTheSubjectInvolved(aDomainInstance);
		}
		
		//hoe
		
		anAspectTemplate = this.getAspectTemplate_for_ActivityCausingEvent();
		if (anAspectTemplate != null) {
			aDomainInstance = anAspectTemplate.createDomainInstance(theConditionEventContext);
			theEventContextDefinition.setTheActivityCausingEvent(aDomainInstance);
		}
		
		//wanneer
		
		anAspectTemplate = this.getAspectTemplate_for_Moment();
		if (anAspectTemplate != null) {
			aDomainInstance = anAspectTemplate.createDomainInstance(theConditionEventContext);
			theEventContextDefinition.setTheMoment(aDomainInstance);
		}
		
		//waar
		
		
		anAspectTemplate = this.getAspectTemplate_for_Location();
		if (anAspectTemplate != null) {
			aDomainInstance = anAspectTemplate.createDomainInstance(theConditionEventContext);
			theEventContextDefinition.setTheLocation(aDomainInstance);
		}
		
		//wie
		
		anAspectTemplate = this.getAspectTemplate_for_Actor();
		if (anAspectTemplate != null) {
			aDomainInstance = anAspectTemplate.createDomainInstance(theConditionEventContext);
			theEventContextDefinition.setTheActor(aDomainInstance);
		}
		//waarom
		
		anAspectTemplate = this.getAspectTemplate_for_Goal();
		if (anAspectTemplate != null) {
			aDomainInstance = anAspectTemplate.createDomainInstance(theConditionEventContext);
			theEventContextDefinition.setTheGoal(aDomainInstance);
		}
		
		
		
		return theEventContextDefinition;
	}

	

	
	
}
