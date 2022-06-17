package planspace.domainRules.RuleTemplates;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.google.gson.Gson;

import planspace.agentinteraction.AbstractEvent;
import planspace.agentinteraction.externalEventTypes.DomainSpecificExternalEvent;
import planspace.instanceModel.DomainInstance;
import planspace.utils.PlanSpaceLogger;

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

public class AbstractEventInterpretationRule {
	/* Elke regel heef uniek ID, een type en leeft in een domein */
	
	public enum t_ruleType  {
			RELEVANT_OBSERVED_EVENT, 
			HYPOTHECTICAL_IMPACT,
			PROVEN_IMPACT,
			HYPOTHETICAL_FACT,
			PROVEN_FACT,
			CLASSIFIED_IMPACT,
			REPAIR_TARGETSTATUS,
			REACH_TARGETSTATUS,
			PROVE_ACTION_FULLFILLMENT,
			UNKNOWN
	}

	String _id;
	String ruleType;
	String domainCode;

	/* De informele omschrijving bevat de regel in menselijk leesbare tekst */
	String informalDescription;

	/*
	 * Elke regel heeft een conditie, deze stelt eisen aan invulling 5W+ H aspecten
	 * in te verwerken bericht
	 */
	EventContextCondition theEventContextCondition;

	/*
	 * De regel kan als conclusie 1 of meerdere interpretaties maken hierbij wordt
	 * een COntex gemaakt op basis van een template
	 */
	List<EventContextDefinitionTemplate> myConclusionEventDefinitionContextTemplates;

	// SCENARIO
	boolean forScenarioWhatIf = false; // scenario context
	boolean forScenarioIsForReal = false;

	public AbstractEventInterpretationRule() {
		// constructor
		// Aanaken unieke ID en lege conditie en conclusies
		this._id = String.valueOf(UUID.randomUUID());
		theEventContextCondition = new EventContextCondition();
		myConclusionEventDefinitionContextTemplates = new ArrayList<EventContextDefinitionTemplate>();
	}

	public void add_goalCondition(ObjectDataPointValueCondition aDPV_Condition) {
		/*
		 * Hiermee kan ik de conditie toegevoegd worden die gesteld wordt zodra het
		 * WAAROM onderzocht wordt indien gezet, dan moet de inhoud WAAROM in te
		 * verwerken event voldoen aan deze conditie
		 */

		this.theEventContextCondition.goalConditions.add(aDPV_Condition);
	}

	public void add_subjectCondition(ObjectDataPointValueCondition aDPV_Condition) {
		/*
		 * Hiermee kan ik de conditie toegevoegd worden die gesteld wordt zodra het WAT
		 * onderzocht wordt indien gezet, dan moet de inhoud WAT in te verwerken event
		 * voldoen aan deze conditie
		 */

		this.theEventContextCondition.subjectConditions.add(aDPV_Condition);
	}

	public void add_locationCondition(ObjectDataPointValueCondition aDPV_Condition) {

		/*
		 * Hiermee kan ik de conditie toegevoegd worden die gesteld wordt zodra het WAAR
		 * onderzocht wordt indien gezet, dan moet de inhoud WAAR in te verwerken event
		 * voldoen aan deze conditie
		 */

		this.theEventContextCondition.locationConditions.add(aDPV_Condition);
	}

	public void add_momentCondition(ObjectDataPointValueCondition aDPV_Condition) {

		/*
		 * Hiermee kan ik de conditie toegevoegd worden die gesteld wordt zodra het
		 * WANNEER onderzocht wordt indien gezet, dan moet de inhoud WANNEER in te
		 * verwerken event voldoen aan deze conditie
		 */

		this.theEventContextCondition.momentConditions.add(aDPV_Condition);
	}

	public void add_ActorCondition(ObjectDataPointValueCondition aDPV_Condition) {

		/*
		 * Hiermee kan ik de conditie toegevoegd worden die gesteld wordt zodra het WIE
		 * onderzocht wordt indien gezet, dan moet de inhoud WIE in te verwerken event
		 * voldoen aan deze conditie
		 */
		this.theEventContextCondition.actorConditions.add(aDPV_Condition);
	}

	// Getters en setters
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getInformalDescription() {
		return informalDescription;
	}

	public void setInformalDescription(String informalDescription) {
		this.informalDescription = informalDescription;
		this.theEventContextCondition.setInformalDescription(informalDescription);
	}

	public void setForScenarioWhatIf(boolean forScenarioWhatIf) {
		this.forScenarioWhatIf = forScenarioWhatIf;
	}

	public boolean isForScenarioIsForReal() {
		return forScenarioIsForReal;
	}

	public void setForScenarioIsForReal(boolean forScenarioIsForReal) {
		this.forScenarioIsForReal = forScenarioIsForReal;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public EventContextCondition getTheEventContextCondition() {
		return theEventContextCondition;
	}

	public void setTheEventContextCondition(EventContextCondition theEventContextCondition) {
		this.theEventContextCondition = theEventContextCondition;
	}
	// Vaststellen of regel eisen/voorwaarden stelt aan aspecten



	public boolean isAtSpecificLocation() {
		// Vaststellen of regel eisen/voorwaarden stelt aan aspect WAAR

		return this.theEventContextCondition.isAtSpecificLocation();
	}

	public void setConditionEventContextDefinition(EventContextCondition theECD_condition) {
		// Methode voegt een conditie toe. Dit is een pattern op de event context van de
		// te onderzoeken gebeurtenis
		this.theEventContextCondition = theECD_condition;
	}

	public void addConclusion_EventContextDefinitionTemplate(
			// Methode voegt een conclusie deel toe. Dit is een ContextDefinitie Template
			EventContextDefinitionTemplate aConclusionEventDefinitionContext) {
		this.myConclusionEventDefinitionContextTemplates.add(aConclusionEventDefinitionContext);

	}

	public List<EventContextDefinitionTemplate> getMyConclusionTemplates() {

		return this.myConclusionEventDefinitionContextTemplates;
	}

	public boolean evaluateForEvent(AbstractEvent anEvent) {

		/*
		 * Deze methode evalueert de conditie van de regel
		 * 
		 * Deze methode accepteert een te onderzoeken Event. Vervolgens analyseert de
		 * rule de event context waarin dit event is opgetreden.
		 * 
		 * De event context beschrijft 1 of meer van de volgende Event Context Aspecten:
		 * - HOE het event is ontstaan - WAT geraakt wordt door het event - WIE welke
		 * partij betrokken was - WANNEER het event optrad - WAAR het event heeft
		 * plaatsgevonden - WAAROM het event heeft plaats gevonden
		 * 
		 * De regels stelt voorwaarde aan bovenstaande aspecten. Deze moeten ingevuld
		 * zijn wil het event relevant gevonden worden.
		 * 
		 */
		// All ok geeft aan dat of alle aspecten voldoen aan voorwaardeb
		// SubTest wordt gebruikt voor 1 aspect
		boolean allOk;
		boolean subTest;

		allOk = true; // domein klopt mits tegendeel bewezen
		// DOMEIN
		// Allereerst moet het domein waarin event is opgetreden overeenkomen met domein
		// waarvoor regel geldt
		if (allOk && (this.getDomainCode().equals("null") == false)) {
			PlanSpaceLogger.getInstance().log_RuleDebug("1.1 Start eval DOMAIN raising event: ");
			subTest = evaluateDomainForEvent(anEvent);

			PlanSpaceLogger.getInstance().log_RuleDebug("1.1 Result eval DOMAIN raising event: " + subTest);

			if (subTest == false) {
				allOk = false;
			}
		}

		/*
		 * HOE Er kunnen voorwaarden gesteld worden aan het HOW: welke activiteit
		 * (actie/onverwachte gebeurtenis) is aanleiding geweest voor ontstaan van de te
		 * onderzoeken gebeurtenis
		 */

		if (allOk && this.theEventContextCondition.isOnSpecificActivityEvent()) {

			PlanSpaceLogger.getInstance().log_RuleDebug("1.2 Start Eval ACTIVITY causing event: ");
			subTest = this.evaluateActivityForEvent(anEvent);

			PlanSpaceLogger.getInstance().log_RuleDebug("1.2 Result eval ACTIVITY causing event: " + subTest);
			if (subTest == false) {
				allOk = false;
			}
		}

		/*
		 * WAT Er kunnen voorwaarden gesteld worden aan het WAT: welke onderwerp
		 * (object) wordt geraakt door de te onderzoeken gebeurtenis
		 */
		if (allOk && this.theEventContextCondition.isSpecificSubjectInvolved()) {
			PlanSpaceLogger.getInstance().log_RuleDebug("1.3 Start eval SUBJECT involved: ");
			subTest = this.evaluateSubjectForEvent(anEvent);

			PlanSpaceLogger.getInstance().log_RuleDebug("1.3 Result eval SUBJECT involved: " + subTest);
			if (subTest == false) {
				allOk = false;
			}
		}

		/*
		 * WANNEER Er kunnen voorwaarden gesteld worden aan het WANNEER: op welke moment
		 * (datum/tijd) heeft de te onderzoeken gebeurtenis zich voorgedaan
		 */

		if (allOk && this.theEventContextCondition.isOnSpecificMoment()) {

			PlanSpaceLogger.getInstance().log_RuleDebug("1.4 Start eval MOMENT match: ");
			subTest = this.evaluateMomentForEvent(anEvent);

			PlanSpaceLogger.getInstance().log_RuleDebug("1.4 Result eval MOMENT match: " + subTest);
			if (subTest == false) {
				allOk = false;
			}
		}

		/*
		 * WIE Er kunnen voorwaarden gesteld worden aan het WIE: welke ACTOR (persoon
		 * (natuurlijk/niet natuurlijk of organisme) was betrokken/verantwoordelijk voor
		 * de te onderzoeken gebeurtenis
		 */

		if (allOk && this.theEventContextCondition.isInvolvingSpecificActor()) {
			PlanSpaceLogger.getInstance().log_RuleDebug("1.5 Start eval  ACTOR involved: ");
			subTest = this.evaluateActorForEvent(anEvent);

			PlanSpaceLogger.getInstance().log_RuleDebug("1.5 Result eval ACTOR involved: " + subTest);
			if (subTest == false) {
				allOk = false;
			}
		}

		/*
		 * WHERE Er kunnen voorwaarden gesteld worden aan het WANNEER: op welke locatie
		 * heeft de te onderzoeken gebeurtenis zich voorgedaan
		 */

		if (allOk && this.theEventContextCondition.isAtSpecificLocation()) {
			PlanSpaceLogger.getInstance().log_RuleDebug("1.6 Start eval LOCATION involved: ");
			subTest = this.evaluateLocationForEvent(anEvent);

			PlanSpaceLogger.getInstance().log_RuleDebug("1.6 Result eval LOCATION involved: " + subTest);
			if (subTest == false) {
				allOk = false;
			}
		}

		/*
		 * WAAROM Er kunnen voorwaarden gesteld worden aan het WAAROM: met welk doel is
		 * de te onderzoeken gebeurtenis ontstaan
		 */

		if (allOk && this.theEventContextCondition.isForSpecificGoal()) {
			PlanSpaceLogger.getInstance().log_RuleDebug("1.7 Start eval  GOAL match: ");
			subTest = this.evaluateGoalForEvent(anEvent);

			PlanSpaceLogger.getInstance().log_RuleDebug("1.7 Result eval  GOAL match: " + subTest);
			if (subTest == false) {
				allOk = false;
			}
		}

		return allOk;
	}

	// DOMAIN
	private boolean evaluateDomainForEvent(
			AbstractEvent anEvent) {

		/*
		 * Deze methode checked het domein aspect het domein in de te onderzoeken
		 * gebeurtenis moet overeenkomen met het domein in de regel
		 */
		boolean subTest = false;

		if (anEvent.getDomainCode().equals(this.getDomainCode())) {
			subTest = true;
		}

		return subTest;
	}

	// WHAT
	private boolean evaluateSubjectForEvent(
			AbstractEvent anEvent) {

		/*
		 * Deze methode checked het WAT aspect. In de gebeurtenis kan het WAT aspect
		 * ingevuld zijn door een DomeinInstance (van een ObjectType)
		 *
		 * Gecontroleerd of of het ObjectType van deze instantie overeenkomt met het
		 * ObjectType in de WAT voorwaarde van de Regel conditie.
		 *
		 * De functie check_I_Am onderzoekt of het object in de gebeurtenis gelijk is
		 * aan het ObjectType in de regel of dat deze overeenkomt met een van de ouder
		 * concepten in de taxonomy
		 * 
		 */
		boolean subTest = false;
		DomainInstance theInstanceOfEvent;
		String theObjectTypeOfRule ;
		EventContextDefinition the_ECDofEvent;
		EventContextCondition the_ECD_ofRule;
		List<ObjectDataPointValueCondition> propertyConditions;

		// get the EventContextCondition (filter) of the rule
		the_ECD_ofRule = this.theEventContextCondition;
		the_ECDofEvent = anEvent.getTheEventContext();

		// getting all subject (WHAT) specific data from both event and filter
		theObjectTypeOfRule = the_ECD_ofRule.getTheSubjectInvolved();
		propertyConditions = the_ECD_ofRule.getSubjectConditions();
		theInstanceOfEvent = the_ECDofEvent.getTheSubjectInvolved();

		// evaluate Aspect by mathing subject (wat) specific data in event against
		// filter in rule
		subTest = evaluateAspectForEvent(anEvent, theObjectTypeOfRule,
				propertyConditions, theInstanceOfEvent);

		return subTest;
	}

	// HOW
	private boolean evaluateActivityForEvent(
			AbstractEvent anEvent) {

		/*
		 * Deze methode checked het HOW aspect. In de gebeurtenis kan het HOW aspect
		 * ingevuld zijn door een DomeinInstance (van een ObjectType)
		 *
		 * Gecontroleerd of of het ObjectType van deze instantie overeenkomt met het
		 * ObjectType in de HOW voorwaarde van de Regel conditie.
		 *
		 * De functie check_I_Am onderzoekt of het object in de gebeurtenis gelijk is
		 * aan het ObjectType in de regel of dat deze overeenkomt met een van de ouder
		 * concepten in de taxonomy
		 * 
		 */

		boolean subTest = false;
		DomainInstance theInstanceOfEvent;
		String theObjectTypeOfRule;
		EventContextDefinition the_ECDofEvent;
		EventContextCondition the_ECD_ofRule;
		List<ObjectDataPointValueCondition> propertyConditions;

		// get the EventContextCondition (filter) of the rule
		the_ECD_ofRule = this.theEventContextCondition;
		the_ECDofEvent = anEvent.getTheEventContext();

		// getting all activity (HOW) specific data from both event and filter
		theObjectTypeOfRule = the_ECD_ofRule.getTheActivityCausingEvent();
		propertyConditions = the_ECD_ofRule.getActivityConditions();
		theInstanceOfEvent = the_ECDofEvent.getTheActivityCausingEvent();

		// evaluate Aspect by mathing subject (wat) specific data in event against
		// filter in rule
		subTest = evaluateAspectForEvent(anEvent, theObjectTypeOfRule,
				propertyConditions, theInstanceOfEvent);

		return subTest;
	}

	// WHO
	private boolean evaluateActorForEvent(
			AbstractEvent anEvent) {

		/*
		 * Deze methode checked het WHO aspect. In de gebeurtenis kan het WHO aspect
		 * ingevuld zijn door een DomeinInstance (van een ObjectType)
		 *
		 * Gecontroleerd of of het ObjectType van deze instantie overeenkomt met het
		 * ObjectType in de WHO voorwaarde van de Regel conditie.
		 *
		 * De functie check_I_Am onderzoekt of het object in de gebeurtenis gelijk is
		 * aan het ObjectType in de regel of dat deze overeenkomt met een van de ouder
		 * concepten in de taxonomy
		 * 
		 */

		boolean subTest = false;
		DomainInstance theInstanceOfEvent;
		String theObjectTypeOfRule;
		EventContextDefinition the_ECDofEvent;
		EventContextCondition the_ECD_ofRule;
		List<ObjectDataPointValueCondition> propertyConditions;

		// get the EventContextCondition (filter) of the rule
		the_ECD_ofRule = this.theEventContextCondition;
		the_ECDofEvent = anEvent.getTheEventContext();

		// getting all actor (WHO) specific data from both event and filter
		theObjectTypeOfRule = the_ECD_ofRule.getTheActor();
		propertyConditions = the_ECD_ofRule.getActorConditions();
		theInstanceOfEvent = the_ECDofEvent.getTheActor();

		// evaluate Aspect by mathing subject (wat) specific data in event against
		// filter in rule
		subTest = evaluateAspectForEvent(anEvent, theObjectTypeOfRule,
				propertyConditions, theInstanceOfEvent);

		return subTest;
	}

	// WHERE
	private boolean evaluateLocationForEvent(	AbstractEvent anEvent) {
			/*
			 * Deze methode checked het WHERE aspect. In de gebeurtenis kan het WHERE aspect
			 * ingevuld zijn door een DomeinInstance (van een ObjectType)
			 *
			 * Gecontroleerd of of het ObjectType van deze instantie overeenkomt met het
			 * ObjectType in de WHERE voorwaarde van de Regel conditie.
			 *
			 * De functie check_I_Am onderzoekt of het object in de gebeurtenis gelijk is
			 * aan het ObjectType in de regel of dat deze overeenkomt met een van de ouder
			 * concepten in de taxonomy
			 * 
			 */

			boolean subTest = false;
			DomainInstance theInstanceOfEvent;
			String theObjectTypeOfRule;
			EventContextDefinition the_ECDofEvent;
			EventContextCondition the_ECD_ofRule;
			List<ObjectDataPointValueCondition> propertyConditions;

			// get the EventContextCondition (filter) of the rule
			the_ECD_ofRule = this.theEventContextCondition;
			the_ECDofEvent = anEvent.getTheEventContext();

			// getting all location (WHERE) specific data from both event and filter
			theObjectTypeOfRule = the_ECD_ofRule.getTheLocation();
			propertyConditions = the_ECD_ofRule.getLocationConditions();
			theInstanceOfEvent = the_ECDofEvent.getTheLocation();

			// evaluate Aspect by mathing subject (wat) specific data in event against
			// filter in rule
			subTest = evaluateAspectForEvent(anEvent, theObjectTypeOfRule,
					propertyConditions, theInstanceOfEvent);
		return subTest;
	}

	// WHY
	private boolean evaluateGoalForEvent(	AbstractEvent anEvent) {

		/*
		 * Deze methode checked het WHY aspect. In de gebeurtenis kan het WHY aspect
		 * ingevuld zijn door een DomeinInstance (van een ObjectType)
		 *
		 * Gecontroleerd of of het ObjectType van deze instantie overeenkomt met het
		 * ObjectType in de WHY voorwaarde van de Regel conditie.
		 *
		 * De functie check_I_Am onderzoekt of het object in de gebeurtenis gelijk is
		 * aan het ObjectType in de regel of dat deze overeenkomt met een van de ouder
		 * concepten in de taxonomy
		 * 
		 */
		boolean subTest = false;
		DomainInstance theInstanceOfEvent;
		String theObjectTypeOfRule;
		EventContextDefinition the_ECDofEvent;
		EventContextCondition the_ECD_ofRule;
		List<ObjectDataPointValueCondition> propertyConditions;

		// get the EventContextCondition (filter) of the rule
		the_ECD_ofRule = this.theEventContextCondition;
		the_ECDofEvent = anEvent.getTheEventContext();

		// getting all goal (WHY) specific data from both event and filter
		theObjectTypeOfRule = the_ECD_ofRule.getTheGoal();
		propertyConditions = the_ECD_ofRule.getGoalConditions();
		theInstanceOfEvent = the_ECDofEvent.getTheGoal();

		// evaluate Aspect by mathing subject (wat) specific data in event against
		// filter in rule
		subTest = evaluateAspectForEvent(anEvent, theObjectTypeOfRule,
				propertyConditions, theInstanceOfEvent);
		return subTest;
	}

	// WHEN
	private boolean evaluateMomentForEvent(AbstractEvent anEvent) {

		/*
		 * Deze methode checked het WHEN aspect. In de gebeurtenis kan het WHEN aspect
		 * ingevuld zijn door een DomeinInstance (van een ObjectType)
		 *
		 * Gecontroleerd of of het ObjectType van deze instantie overeenkomt met het
		 * ObjectType in de WHEN voorwaarde van de Regel conditie.
		 *
		 * De functie check_I_Am onderzoekt of het object in de gebeurtenis gelijk is
		 * aan het ObjectType in de regel of dat deze overeenkomt met een van de ouder
		 * concepten in de taxonomy
		 * 
		 */
		boolean subTest = false;
		DomainInstance theInstanceOfEvent;
		String theObjectTypeOfRule;
		EventContextDefinition the_ECDofEvent;
		EventContextCondition the_ECD_ofRule;
		List<ObjectDataPointValueCondition> propertyConditions;

		// get the EventContextCondition (filter) of the rule
		the_ECD_ofRule = this.theEventContextCondition;
		the_ECDofEvent = anEvent.getTheEventContext();

		// getting all moment (WHEN) specific data from both event and filter
		theObjectTypeOfRule = the_ECD_ofRule.getTheMoment();
		propertyConditions = the_ECD_ofRule.getMomentConditions();
		theInstanceOfEvent = the_ECDofEvent.getTheMoment();

		// evaluate Aspect by mathing subject (wat) specific data in event against
		// filter in rule
		subTest = evaluateAspectForEvent(anEvent, theObjectTypeOfRule,
				propertyConditions, theInstanceOfEvent);
		return subTest;
	}

	public Document toMongoDocument() {

		/*
		 * Deze methode kan in instantie van deze class omzetten naar Json m.b.v de
		 * JackSon Library (zie POM.XML) Doel is deze Json op te slaan in een Mongo
		 * database. Mongo accepteert echter geen JSON als String, maar vereist de
		 * binaire versie daarvan BSON/Document
		 */
		Gson gsonParser;
		String meAsJson;
		Document meAsDocument;

		meAsDocument = null;
		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);
		meAsDocument = Document.parse(meAsJson);

		return meAsDocument;
	}

	public void storeYourSelf() {
		/*
		 * Deze methode kan de regel met zijn eigenschappen op slaan in de configuratie
		 * repository De interface naar de configuratie repository wordt opgehaald. Er
		 * is een centrale repository in planspace.
		 */

		// abstract per type regel anders
	}

	public String toJson() {
		Gson gsonParser;
		String meAsJson;

		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);

		return meAsJson;
	}

	/*********************
	 * TO DO
	 */
	private boolean evaluateAspectForEvent(
			AbstractEvent anEvent, String theObjectTypeOfRule,
			List<ObjectDataPointValueCondition> propertyConditions, DomainInstance theInstanceOfEvent) {

		/*
		 * TO DO Deze methode checkt een aspect
		 * 
		 * 
		 * In de gebeurtenis kan het aspect ingevuld zijn door een DomeinInstance (van
		 * een ObjectType) * De instantie van het aspect is in de argumenten mee
		 * gegeven.
		 *
		 * Gecontroleerd of of het ObjectType van deze instantie overeenkomt met het
		 * ObjectType in de voorwaarde van de Regel conditie.
		 *
		 * De functie check_I_Am onderzoekt of het object in de gebeurtenis gelijk is
		 * aan het ObjectType in de regel of dat deze overeenkomt met een van de ouder
		 * concepten in de taxonomy
		 * 
		 */
		boolean subTest = false;

		EventContextDefinition the_ECDofEvent;
		EventContextCondition the_ECD_ofRule;

		// Get the EventCondition
		the_ECD_ofRule = this.theEventContextCondition;

		if (theObjectTypeOfRule != null) {
			/* Er is in de regel een voorwaarde */
			the_ECDofEvent = anEvent.getTheEventContext();

		
			if (theInstanceOfEvent != null) {
				/* In de gebeurtenis is een instantie voor het Aspect */

				if (theInstanceOfEvent.getMyObjectType().check_I_AM_by_type(theObjectTypeOfRule)) {
					/*
					 * In object in regel komt overeen met object van instantie of van een van de
					 * parent Objecten
					 */

					if (the_ECD_ofRule.evaluateAspectPropertyConditions(propertyConditions, theInstanceOfEvent)) {
						subTest = true;
					}

				}
			}
		}
		return subTest;
	}
}
