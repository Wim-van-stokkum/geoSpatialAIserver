package planspace.domainRules.RuleTemplates;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.gson.Gson;

import planspace.domainRules.RuleTemplates.AspectTemplate.t_baseOnAspect;
import planspace.domainTypes.ObjectType;
import planspace.instanceModel.DomainInstance;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.utils.PlanSpaceLogger;


/* Planspace heeft een multi-agent architectuur
 * Een of meerdere agents luisteren naar gestandaardiseerde berichten waarin gebeurtenissen worden gecommuniceerd
 *  
 *  Elk event in planspace beschrijft de context waarin de gebeurtenis plaatsvindt:
 *  Dit wordt beschreven door 1 of meer van de volgende Event Context Aspecten:
 *  - HOE het event is ontstaan
 *  - WAT geraakt wordt door het event
 *  - WIE welke partij betrokken was
 *  - WANNEER het event optrad
 *  - WAAR het event heeft plaatsgevonden
 *  - WAAROM het event heeft plaats gevonden

 *  Niet elk aspect hoeft bekend te zijn of relevant te zijn.
 *  Elke aspect dat relevant is wordt beschreven middels een een EventContextDefinition
 *  Per aspect wordt beschrijven welk ObjectType wordt verwacht en eventuele eisen aan de properties daarvan
 */

public class EventContextCondition {

	/*
	 * * Elk event in planspace beschrijft de context waarin de gebeurtenis
	 * plaatsvindt: Dit wordt beschreven door 1 of meer van de volgende Event
	 * Context Aspecten: - HOE het event is ontstaan - WAT geraakt wordt door het
	 * event - WIE welke partij betrokken was - WANNEER het event optrad - WAAR het
	 * event heeft plaatsgevonden - WAAROM het event heeft plaats gevonden
	 */
	public enum t_EventContext_AspectType {
		HOW, WHERE, WHAT, WHEN, WHO, WHY
	}

	/*
	 * de informele omschrijving combineert de aspecten tot een voor mensen leesbare
	 * zin
	 */

	String informalDescription;

	@JsonIgnore
	public String generateInformalDescription() {
		String theDescription;

		theDescription = "";
		if (this.theActivityCausingEvent != null) {
			theDescription = theDescription + "Er is een gebeurtenis " + this.theActivityCausingEvent + ". ";
		} else {
			theDescription = theDescription + "Er gebeurt iets. ";
		}

		if (this.ObjectDefinitionOftheSubjectInvolved != null) {
			theDescription = theDescription + "Deze gebeurtenis raakt het concept " + this.theSubjectInvolved + ". ";
		} else {
			theDescription = theDescription + "de raakt willekeurig welk object in het domein. ";
		}

		if (this.getTheActor() != null) {
			theDescription = theDescription + "De betrokken actor is " + this.getTheActor() + ". ";
		} else {
			theDescription = theDescription + "De betrokken actor is niet relevant. ";
		}

		if (this.getTheMoment() != null) {
			theDescription = theDescription + "Het moment van gebeurtenis is " + this.getTheMoment() + ". ";
		} else {
			theDescription = theDescription + "Het moment van de gebeurtenis is niet van invloed. ";
		}

		if (this.getTheLocation() != null) {
			theDescription = theDescription + "De locatie van gebeurtenis is " + this.getTheLocation() + ". ";
		} else {
			theDescription = theDescription
					+ "De locatie waar de gebeurtenis optreed/optrad heeft voor de gebeurtenis geen betekenis. ";
		}

		if (this.getTheGoal() != null) {
			theDescription = theDescription + "De gebeurtenis vindt plaats vanwege " + this.getTheGoal() + ".";
		} else {
			theDescription = theDescription
					+ "De reden van waarom de gebeurtenis optreedt maakt in dit geval niet uit.";
		}

		return theDescription;

	}

	/*
	 * Elke aspect dat relevant is wordt beschreven middels een TypeName van een
	 * ObjectType in de domein onthologie die is opgesteld voor het domein
	 */

	// HOE

	String theActivityCausingEvent = null;
	List<ObjectDataPointValueCondition> activityConditions;
	// for performance optimalisation (aspect filtering specialism)
	ObjectType ObjectDefinitionOftheActivityCausingEvent = null;

	// WAT

	String theSubjectInvolved = null;
	List<ObjectDataPointValueCondition> subjectConditions;
	// for performance optimalisation (aspect filtering specialism)
	ObjectType ObjectDefinitionOftheSubjectInvolved = null;

	// WAAR
	String theLocation = null;
	List<ObjectDataPointValueCondition> locationConditions;
	// for performance optimalisation (aspect filtering specialism)
	ObjectType ObjectDefinitionOftheLocation = null;

	// WIE
	String theActor = null;
	List<ObjectDataPointValueCondition> actorConditions;
	// for performance optimalisation (aspect filtering specialism)
	ObjectType ObjectDefinitionOftheActor = null;

	// WANNEER

	String theMoment = null;
	List<ObjectDataPointValueCondition> momentConditions;
	// for performance optimalisation (aspect filtering specialism)
	ObjectType ObjectDefinitionOftheMoment = null;

	// WAAROM
	String theGoal = null;
	List<ObjectDataPointValueCondition> goalConditions;
	// for performance optimalisation (aspect filtering specialism)
	ObjectType ObjectDefinitionOftheGoal = null;

	public EventContextCondition() {
		/*
		 * Voor het object in het aspect kunnen aanvullende condities gelden voor 1 of
		 * meer eigenschappen
		 */

		this.goalConditions = new ArrayList<ObjectDataPointValueCondition>();
		this.locationConditions = new ArrayList<ObjectDataPointValueCondition>();
		this.actorConditions = new ArrayList<ObjectDataPointValueCondition>();
		this.momentConditions = new ArrayList<ObjectDataPointValueCondition>();
		this.activityConditions = new ArrayList<ObjectDataPointValueCondition>();
		this.subjectConditions = new ArrayList<ObjectDataPointValueCondition>();
	}

	/* getters en setters */

	public String getInformalDescription() {

		return informalDescription;
	}

	public void setInformalDescription(String informalDescription) {
		this.informalDescription = informalDescription;
	}

	public String getTheActivityCausingEvent() {
		return theActivityCausingEvent;
	}

	public void setTheActivityCausingEvent(String forDomain, String theActivityCausingEvent) {

		InterfaceToDomainTypeRepository theDomainIFC;

		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();
		this.ObjectDefinitionOftheActivityCausingEvent = theDomainIFC.findObjectTypeByTypename(forDomain,
				theActivityCausingEvent);
		this.theActivityCausingEvent = theActivityCausingEvent;

	}

	public List<ObjectDataPointValueCondition> getActivityConditions() {
		return activityConditions;
	}

	public void setActivityConditions(List<ObjectDataPointValueCondition> activityConditions) {
		this.activityConditions = activityConditions;
	}

	public void addActivityCondition(ObjectDataPointValueCondition activityCondition) {
		if (this.activityConditions == null) {
			this.activityConditions = new ArrayList<ObjectDataPointValueCondition>();
		}

		this.activityConditions.add(activityCondition);
	}

	public String getTheSubjectInvolved() {

		return theSubjectInvolved;
	}

	public void setTheSubjectInvolved(String forDomain, String theSubjectInvolved) {

		InterfaceToDomainTypeRepository theDomainIFC;

		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();
		this.ObjectDefinitionOftheSubjectInvolved = theDomainIFC.findObjectTypeByTypename(forDomain,
				theSubjectInvolved);
		this.theSubjectInvolved = theSubjectInvolved;

	}

	public List<ObjectDataPointValueCondition> getSubjectConditions() {
		return subjectConditions;
	}

	public void setSubjectConditions(List<ObjectDataPointValueCondition> subjectConditions) {
		this.subjectConditions = subjectConditions;
	}

	public void addSubjectCondition(ObjectDataPointValueCondition subjectCondition) {
		if (this.subjectConditions == null) {
			this.subjectConditions = new ArrayList<ObjectDataPointValueCondition>();
		}

		this.subjectConditions.add(subjectCondition);
	}

	@JsonSetter("theLocation")
	public String getTheLocation() {
		return theLocation;
	}

	@JsonSetter("theLocation")
	public void setTheLocation(String forDomain, String theLocation) {
		// get definition

		InterfaceToDomainTypeRepository theDomainIFC;

		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();

		this.ObjectDefinitionOftheLocation = theDomainIFC.findObjectTypeByTypename(forDomain, theLocation);

		this.theLocation = theLocation;
	}

	public List<ObjectDataPointValueCondition> getLocationConditions() {
		return locationConditions;
	}

	public void addLocationCondition(ObjectDataPointValueCondition locationCondition) {
		if (this.locationConditions == null) {
			this.locationConditions = new ArrayList<ObjectDataPointValueCondition>();
		}

		this.locationConditions.add(locationCondition);
	}

	public void setLocationConditions(List<ObjectDataPointValueCondition> locationConditions) {
		this.locationConditions = locationConditions;
	}

	public String getTheActor() {

		return theActor;
	}

	public void setTheActor(String forDomain, String theActor) {
		// get definition

		InterfaceToDomainTypeRepository theDomainIFC;

		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();

		this.ObjectDefinitionOftheActor = theDomainIFC.findObjectTypeByTypename(forDomain, theActor);
		this.theActor = theActor;
	}

	public List<ObjectDataPointValueCondition> getActorConditions() {
		return actorConditions;
	}

	public void setActorConditions(List<ObjectDataPointValueCondition> actorConditions) {
		this.actorConditions = actorConditions;
	}

	public void addActorCondition(ObjectDataPointValueCondition actorCondition) {
		if (this.actorConditions == null) {
			this.actorConditions = new ArrayList<ObjectDataPointValueCondition>();
		}

		this.actorConditions.add(actorCondition);
	}

	@JsonSetter("theMoment")
	public String getTheMoment() {
		return theMoment;
	}

	@JsonSetter("theMoment")

	public void setTheMoment(String forDomain, String theMoment) {
		// get definition

		InterfaceToDomainTypeRepository theDomainIFC;

		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();

		this.ObjectDefinitionOftheMoment = theDomainIFC.findObjectTypeByTypename(forDomain, theMoment);
		this.theMoment = theMoment;
	}

	public List<ObjectDataPointValueCondition> getMomentConditions() {
		return momentConditions;
	}

	public void setMomentConditions(List<ObjectDataPointValueCondition> momentConditions) {
		this.momentConditions = momentConditions;
	}

	public void addMomentCondition(ObjectDataPointValueCondition momentCondition) {
		if (this.momentConditions == null) {
			this.momentConditions = new ArrayList<ObjectDataPointValueCondition>();
		}

		this.momentConditions.add(momentCondition);
	}

	public String getTheGoal() {
		return theGoal;
	}

	public void setTheGoal(String forDomain, String theGoal) {
		// get definition

		InterfaceToDomainTypeRepository theDomainIFC;

		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();

		this.ObjectDefinitionOftheGoal = theDomainIFC.findObjectTypeByTypename(forDomain, theGoal);
		this.theGoal = theGoal;
	}

	public List<ObjectDataPointValueCondition> getGoalConditions() {
		return goalConditions;
	}

	public void setGoalConditions(List<ObjectDataPointValueCondition> goalConditions) {
		this.goalConditions = goalConditions;
	}

	// getters and setters of the ObjectDefinition (at specification time)
	// Adding this configuration prevents the logic (e.g. filtering rules based on
	// specialism)
	// for going to the database for each rule to het these definitions.

	public ObjectType getObjectDefinitionOftheActivityCausingEvent() {
		return ObjectDefinitionOftheActivityCausingEvent;
	}

	public void setObjectDefinitionOftheActivityCausingEvent(ObjectType objectDefinitionOftheActivityCausingEvent) {
		ObjectDefinitionOftheActivityCausingEvent = objectDefinitionOftheActivityCausingEvent;
	}

	public ObjectType getObjectDefinitionOftheSubjectInvolved() {
		return ObjectDefinitionOftheSubjectInvolved;
	}

	public void setObjectDefinitionOftheSubjectInvolved(ObjectType objectDefinitionOftheSubjectInvolved) {
		ObjectDefinitionOftheSubjectInvolved = objectDefinitionOftheSubjectInvolved;
	}

	public ObjectType getObjectDefinitionOftheLocation() {
		return ObjectDefinitionOftheLocation;
	}

	public void setObjectDefinitionOftheLocation(ObjectType objectDefinitionOftheLocation) {
		ObjectDefinitionOftheLocation = objectDefinitionOftheLocation;
	}

	public ObjectType getObjectDefinitionOftheActor() {
		return ObjectDefinitionOftheActor;
	}

	public void setObjectDefinitionOftheActor(ObjectType objectDefinitionOftheActor) {
		ObjectDefinitionOftheActor = objectDefinitionOftheActor;
	}

	public ObjectType getObjectDefinitionOftheMoment() {
		return ObjectDefinitionOftheMoment;
	}

	public void setObjectDefinitionOftheMoment(ObjectType objectDefinitionOftheMoment) {
		ObjectDefinitionOftheMoment = objectDefinitionOftheMoment;
	}

	public ObjectType getObjectDefinitionOftheGoal() {
		return ObjectDefinitionOftheGoal;
	}

	public void setObjectDefinitionOftheGoal(ObjectType objectDefinitionOftheGoal) {
		ObjectDefinitionOftheGoal = objectDefinitionOftheGoal;
	}

	public void addGoalCondition(ObjectDataPointValueCondition goalCondition) {
		if (this.goalConditions == null) {
			this.goalConditions = new ArrayList<ObjectDataPointValueCondition>();
		}

		this.goalConditions.add(goalCondition);
	}

	/* Check of aspect is benoemd in de event context */
	@JsonIgnore()
	public boolean isInvolvingSpecificActor() {
		/* Check of aspect WIE is benoemd in de event context */
		boolean eval;

		eval = false;

		if (this.theActor != null) {
			eval = true;
		}
		return eval;
	}

	@JsonIgnore()
	public boolean isOnSpecificMoment() {
		/* Check of aspect WHEN is benoemd in de event context */
		boolean eval;

		eval = false;

		if (this.theMoment != null) {
			eval = true;
		}
		return eval;
	}

	@JsonIgnore()
	public boolean isAtSpecificLocation() {
		/* Check of aspect WHERE is benoemd in de event context */
		boolean eval;

		eval = false;
		if (this.theLocation != null) {
			eval = true;
		}
		return eval;
	}

	@JsonIgnore()
	public boolean isSpecificSubjectInvolved() {
		/* Check of aspect WHAT is benoemd in de event context */
		boolean eval;

		eval = false;

		if (this.theSubjectInvolved != null) {
			eval = true;
		}
		return eval;
	}

	@JsonIgnore()
	public boolean isOnSpecificActivityEvent() {
		/* Check of aspect HOW is benoemd in de event context */
		boolean eval;

		eval = false;

		if (this.theActivityCausingEvent != null) {
			eval = true;
		}
		return eval;
	}

	@JsonIgnore()
	public boolean isForSpecificGoal() {
		/* Check of aspect WHY is benoemd in de event context */
		boolean eval;

		eval = false;

		if (this.theGoal != null) {
			eval = true;
		}
		return eval;
	}

	public boolean evaluateAspectPropertyConditions(List<ObjectDataPointValueCondition> propertyConditions,
			/*
			 * This method is checking if the conditions set for the properties of the
			 * object involved in an Aspect in the event are met
			 */
			DomainInstance theInstanceOfEvent) {
		boolean allAspectConditionsOk, aSubCheck;
		ObjectDataPointValueCondition aConditionToCheck;

		int i;

		PlanSpaceLogger.getInstance().log_RuleDebug("=====> Start Evaluation Aspect Condition");

		allAspectConditionsOk = true;
		for (i = 0; i < propertyConditions.size(); i++) {
			aConditionToCheck = propertyConditions.get(i);
			PlanSpaceLogger.getInstance().log_RuleDebug("=====> Start check property condition");
			aSubCheck = aConditionToCheck.evaluateForDomainInstance(theInstanceOfEvent);
			PlanSpaceLogger.getInstance().log_RuleDebug("=====> Result check property condition" + aSubCheck);
			if (aSubCheck == false) {
				allAspectConditionsOk = false;
			}
		}
		PlanSpaceLogger.getInstance()
				.log_RuleDebug("=====> Result Evaluation Aspect Condition: " + allAspectConditionsOk);
		return allAspectConditionsOk;

	}

	public boolean isMatchingWithOtherEventCondition(String inDomain,
			EventContextCondition anOtherEventContextCondition) {
		/*
		 * This method will match this EventContextConditions with
		 * anOtherEventContextCondition
		 * 
		 * Voor alle aspecten moet gelden: - Indien een van beide of beide aspecten niet
		 * zijn gedefinieerd, dan staat dat voor "alle" en dus is er een match per
		 * definitie
		 * 
		 * - Indien beide aspecten zijn ingevuld, dan moet object van een passen in
		 * ander of andersom - De property condities worden nog niet meegenomen, deze
		 * worden in de evaluatie van de regels beoordeeld (kans is iets te veel match,
		 * maar dat is niet erg
		 * 
		 */
		boolean isMatching, aspectMatchResult;
		String myAspectValue, aspectValueOfOther;
		ObjectType myAspectObjectType, aspectObjectTypeOfOther;

		isMatching = false;

		// Check HOW
		aspectMatchResult = false;
		myAspectValue = this.getTheActivityCausingEvent();

		myAspectObjectType = this.getObjectDefinitionOftheActivityCausingEvent();
		aspectObjectTypeOfOther = anOtherEventContextCondition.getObjectDefinitionOftheActivityCausingEvent();

		aspectValueOfOther = anOtherEventContextCondition.getTheActivityCausingEvent();
		aspectMatchResult = checkMatchOfAspect(inDomain, myAspectValue, aspectValueOfOther, myAspectObjectType,
				aspectObjectTypeOfOther);
		isMatching = aspectMatchResult;

		if (isMatching) {
			// we still have to check other aspects as well

			// Check WHAT
			aspectMatchResult = false;
			myAspectValue = this.getTheSubjectInvolved();

			myAspectObjectType = this.getObjectDefinitionOftheSubjectInvolved();
			aspectObjectTypeOfOther = anOtherEventContextCondition.getObjectDefinitionOftheSubjectInvolved();

			aspectValueOfOther = anOtherEventContextCondition.getTheSubjectInvolved();
			aspectMatchResult = checkMatchOfAspect(inDomain, myAspectValue, aspectValueOfOther, myAspectObjectType,
					aspectObjectTypeOfOther);
			isMatching = aspectMatchResult;

		}

		if (isMatching) {
			// we still have to check other aspects as well

			// Check WHERE
			aspectMatchResult = false;
			myAspectValue = this.getTheLocation();

			myAspectObjectType = this.getObjectDefinitionOftheLocation();
			aspectObjectTypeOfOther = anOtherEventContextCondition.getObjectDefinitionOftheLocation();

			aspectValueOfOther = anOtherEventContextCondition.getTheLocation();
			aspectMatchResult = checkMatchOfAspect(inDomain, myAspectValue, aspectValueOfOther, myAspectObjectType,
					aspectObjectTypeOfOther);
			isMatching = aspectMatchResult;
		}
		if (isMatching) {
			// Check WHEN
			aspectMatchResult = false;
			myAspectValue = this.getTheMoment();

			myAspectObjectType = this.getObjectDefinitionOftheMoment();
			aspectObjectTypeOfOther = anOtherEventContextCondition.getObjectDefinitionOftheMoment();

			aspectValueOfOther = anOtherEventContextCondition.getTheMoment();
			aspectMatchResult = checkMatchOfAspect(inDomain, myAspectValue, aspectValueOfOther, myAspectObjectType,
					aspectObjectTypeOfOther);
			isMatching = aspectMatchResult;
		}
		if (isMatching) {

			// Check WHO
			aspectMatchResult = false;
			myAspectValue = this.getTheActor();

			myAspectObjectType = this.getObjectDefinitionOftheActor();
			aspectObjectTypeOfOther = anOtherEventContextCondition.getObjectDefinitionOftheActor();

			aspectValueOfOther = anOtherEventContextCondition.getTheActor();
			aspectMatchResult = checkMatchOfAspect(inDomain, myAspectValue, aspectValueOfOther, myAspectObjectType,
					aspectObjectTypeOfOther);
			isMatching = aspectMatchResult;
		}
		if (isMatching) {

			// Check WHY
			aspectMatchResult = false;
			myAspectValue = this.getTheGoal();

			myAspectObjectType = this.getObjectDefinitionOftheGoal();
			aspectObjectTypeOfOther = anOtherEventContextCondition.getObjectDefinitionOftheGoal();

			aspectValueOfOther = anOtherEventContextCondition.getTheGoal();
			aspectMatchResult = checkMatchOfAspect(inDomain, myAspectValue, aspectValueOfOther, myAspectObjectType,
					aspectObjectTypeOfOther);
			isMatching = aspectMatchResult;
		}

		// other
		return isMatching;
	}

	private boolean checkMatchOfAspect(String inDomain, String myAspectValue, String aspectValueOfOther,
			ObjectType myAspectObjectType, ObjectType aspectObjectTypeOfOther) {
		/*
		 * Deze method check of twee aspect values met elkaar matchen - Als beide leeg
		 * dan ok (leeg staat hier voor alle) - Als een van beide leeg dan ook ok (leeg
		 * staat hier voor alle) - Als beide een definitie dan moet concept 1 passen in
		 * concept 2 en vice versa
		 */
		boolean aspectIsMatching, aspectsAreForall, matchingMyType, matchingOtherType;
		ObjectType objectDefinitionOfMyAspect, objectDefinitionOfOtherAspect;
		
		InterfaceToDomainTypeRepository theDomainIFC;

		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();

		// By Default no match
		aspectIsMatching = false;

		PlanSpaceLogger.getInstance()
				.log_RuleDebug("\n Check Mathing focus value " + myAspectValue + " against " + aspectValueOfOther);

		// First check if one or both aspects are defined as forall
		aspectsAreForall = false;
		aspectsAreForall = checkAspectValueIsForall(myAspectValue);
		if (aspectsAreForall == false) {
			aspectsAreForall = checkAspectValueIsForall(aspectValueOfOther);
		}

		// Debug info
		if (aspectsAreForall) {
			PlanSpaceLogger.getInstance()
					.log_RuleDebug("At least one is forall:  " + myAspectValue + " against " + aspectValueOfOther);

		} else {
			PlanSpaceLogger.getInstance()
					.log_RuleDebug("None is forall:  " + myAspectValue + " against " + aspectValueOfOther);

		}

		/*
		 * If not defined as forall: check if concepts match in othology For this we
		 * need the read the objectdefinitions
		 * 
		 * The ObjectDefinition will be taken from the definition of the
		 * EventContextCondition If not available, the configuration repository will be
		 * asked to get the values
		 */

		if (aspectsAreForall == false) {
			/*
			 * The ObjectDefinition will be taken from the definition of the
			 * EventContextCondition If not available, the configuration repository will be
			 * asked to get the values
			 */
			objectDefinitionOfMyAspect = myAspectObjectType;
			if (objectDefinitionOfMyAspect == null) {
				
				objectDefinitionOfMyAspect = theDomainIFC.findObjectTypeByTypename(inDomain, myAspectValue);
			}

			objectDefinitionOfOtherAspect = aspectObjectTypeOfOther;
			if (objectDefinitionOfOtherAspect == null) {

				objectDefinitionOfOtherAspect = theDomainIFC.findObjectTypeByTypename(inDomain, aspectValueOfOther);
			}

			matchingMyType = objectDefinitionOfMyAspect.check_I_AM_by_type(aspectValueOfOther);
			matchingOtherType = objectDefinitionOfOtherAspect.check_I_AM_by_type(myAspectValue);

			if (matchingMyType) {
				aspectIsMatching = true;
				PlanSpaceLogger.getInstance()
						.log_RuleDebug("my Aspect " + myAspectValue + " is a  " + aspectValueOfOther);
			} else {
				PlanSpaceLogger.getInstance()
						.log_RuleDebug("my Aspect " + myAspectValue + " is NOT a  " + aspectValueOfOther);
			}
			if (matchingOtherType) {
				aspectIsMatching = true;
				PlanSpaceLogger.getInstance()
						.log_RuleDebug("other AspectValue " + aspectValueOfOther + " is a  " + myAspectValue);
			} else

			{
				PlanSpaceLogger.getInstance()
						.log_RuleDebug("other AspectValue " + aspectValueOfOther + " is NOT a  " + myAspectValue);
			}

		} else {
			// if one or both for all then match ok
			aspectIsMatching = true;
		}

		/* Debug of result */
		if (aspectIsMatching) {
			// no matching based in semantics in onthology
			PlanSpaceLogger.getInstance()
					.log_RuleDebug("aspect values ARE matching " + aspectValueOfOther + " is a  " + myAspectValue);
		} else {
			PlanSpaceLogger.getInstance()
					.log_RuleDebug("aspect values ARE NOT matching " + aspectValueOfOther + " is a  " + myAspectValue);

		}
		PlanSpaceLogger.getInstance().log_Debug("\n\n");

		return aspectIsMatching;
	}

	private boolean checkAspectValueIsForall(String anAspectValue) {

		/*
		 * Deze method check of een aspect values ongedefinieerd is. indien zo dan staat
		 * dat in de conditie voor "VOOR ALLE"
		 */
		boolean aspectIsForall;

		aspectIsForall = false;
		if (anAspectValue == null) {
			aspectIsForall = true;
		} else if ((anAspectValue.equals("null")) || (anAspectValue.equals(""))) {
			aspectIsForall = true;

		}
		if (aspectIsForall) {
			PlanSpaceLogger.getInstance().log_RuleDebug("aspect value " + anAspectValue + " is FOR ALL");
		} else {
			PlanSpaceLogger.getInstance().log_RuleDebug("aspect value " + anAspectValue + " is NOT FOR ALL");
		}

		return aspectIsForall;
	}

	@JsonIgnore
	public String toJson() {
		/*
		 * Deze methode kan in instantie van deze class omzetten naar Json m.b.v de
		 * JackSon Library (zie POM.XML) Doel is deze Json op te slaan in een Mongo
		 * database. Mongo accepteert echter geen JSON als String, maar vereist de
		 * binaire versie daarvan BSON/Document
		 */

		Gson gsonParser;
		String meAsJson;

		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);

		return meAsJson;
	}

	public List<t_baseOnAspect> getCorrespondingAspectsForConcept(String relevantConcept) {
		List<t_baseOnAspect> matchingAspects;

		// check if concept being matched corresponds with the aspects in the conditions

		String myAspectValue;
		ObjectType myAspectObjectType;
		boolean isMatch;
		String AspectValueOfRelevantConcept;

		matchingAspects = new ArrayList<t_baseOnAspect>();
		if (!relevantConcept.isEmpty()) {

			AspectValueOfRelevantConcept = relevantConcept;
			// Check HOW
			isMatch = false;
			myAspectValue = this.theActivityCausingEvent;
			if (myAspectValue != null) {
				myAspectObjectType = this.getObjectDefinitionOftheActivityCausingEvent();
				if (myAspectObjectType != null) {
					// the ObjectType or one of ot;s children should match the object type in
					// relevant concept
					if (myAspectObjectType.getCode().equals(AspectValueOfRelevantConcept)) {
						isMatch = true;
					} else {
						if (myAspectObjectType.getCodesOfMyChildren() != null) {
							if (myAspectObjectType.getCodesOfMyChildren().contains(AspectValueOfRelevantConcept)) {
								isMatch = true;
							}
						}
					}
					if (isMatch) {
						matchingAspects.add(t_baseOnAspect.HOW);
					}
				}
			}

			// Check WHAT
			isMatch = false;
			myAspectValue = this.theSubjectInvolved;
			if (myAspectValue != null) {
				myAspectObjectType = this.getObjectDefinitionOftheSubjectInvolved();
				if (myAspectObjectType != null) {
					// the ObjectType or one of ot;s children should match the object type in
					// relevant concept
					if (myAspectObjectType.getCode().equals(AspectValueOfRelevantConcept)) {
						isMatch = true;
					} else {
						if (myAspectObjectType.getCodesOfMyChildren() != null) {
							if (myAspectObjectType.getCodesOfMyChildren().contains(AspectValueOfRelevantConcept)) {
								isMatch = true;
							}
						}
					}
					if (isMatch) {
						matchingAspects.add(t_baseOnAspect.WHAT);
					}
				}
			}

			// Check WHO
			isMatch = false;
			myAspectValue = this.theActor;
			if (myAspectValue != null) {
				myAspectObjectType = this.getObjectDefinitionOftheActor();
				if (myAspectObjectType != null) {
					// the ObjectType or one of its children should match the object type in
					// relevant concept
					if (myAspectObjectType.getCode().equals(AspectValueOfRelevantConcept)) {
						isMatch = true;
					} else {
						if (myAspectObjectType.getCodesOfMyChildren() != null) {
							if (myAspectObjectType.getCodesOfMyChildren().contains(AspectValueOfRelevantConcept)) {
								isMatch = true;
							}
						}
					}
					if (isMatch) {
						matchingAspects.add(t_baseOnAspect.WHO);
					}
				}
			}

			// Check WHEN
			isMatch = false;
			myAspectValue = this.theMoment;
			if (myAspectValue != null) {
				myAspectObjectType = this.getObjectDefinitionOftheMoment();
				if (myAspectObjectType != null) {
					// the ObjectType or one of its children should match the object type in
					// relevant concept
					if (myAspectObjectType.getCode().equals(AspectValueOfRelevantConcept)) {
						isMatch = true;
					} else {
						if (myAspectObjectType.getCodesOfMyChildren() != null) {
							if (myAspectObjectType.getCodesOfMyChildren().contains(AspectValueOfRelevantConcept)) {
								isMatch = true;
							}
						}
					}
					if (isMatch) {
						matchingAspects.add(t_baseOnAspect.WHEN);
					}
				}
			}

			// Check WHERE
			isMatch = false;
			myAspectValue = this.theLocation;
			if (myAspectValue != null) {
				myAspectObjectType = this.getObjectDefinitionOftheLocation();
				if (myAspectObjectType != null) {
					// the ObjectType or one of its children should match the object type in
					// relevant concept
					if (myAspectObjectType.getCode().equals(AspectValueOfRelevantConcept)) {
						isMatch = true;
					} else {
						if (myAspectObjectType.getCodesOfMyChildren() != null) {
							if (myAspectObjectType.getCodesOfMyChildren().contains(AspectValueOfRelevantConcept)) {
								isMatch = true;
							}
						}
					}
					if (isMatch) {
						matchingAspects.add(t_baseOnAspect.WHERE);
					}
				}
			}

			// Check WHY
			isMatch = false;
			myAspectValue = this.theGoal;
			if (myAspectValue != null) {
				myAspectObjectType = this.getObjectDefinitionOftheGoal();
				if (myAspectObjectType != null) {
					// the ObjectType or one of its children should match the object type in
					// relevant concept
					if (myAspectObjectType.getCode().equals(AspectValueOfRelevantConcept)) {
						isMatch = true;
					} else {
						if (myAspectObjectType.getCodesOfMyChildren() != null) {
							if (myAspectObjectType.getCodesOfMyChildren().contains(AspectValueOfRelevantConcept)) {
								isMatch = true;
							}
						}
					}
					if (isMatch) {
						matchingAspects.add(t_baseOnAspect.WHY);
					}
				}
			}
			PlanSpaceLogger.getInstance().log("\n\n[MATCH IN CONDITION] : " + AspectValueOfRelevantConcept
					+ " is matching aspects " + matchingAspects);
		}
		return matchingAspects;
	}
	public ObjectType getObjectTypeOfAspect(t_baseOnAspect the_AspectType) {
		ObjectType theObjectOfAspect;

		theObjectOfAspect = null;
		if (the_AspectType.equals(t_baseOnAspect.HOW)) {
			theObjectOfAspect = this.getObjectDefinitionOftheActivityCausingEvent();
		} else if (the_AspectType.equals(t_baseOnAspect.WHAT)) {
			theObjectOfAspect = this.getObjectDefinitionOftheSubjectInvolved();
		} else if (the_AspectType.equals(t_baseOnAspect.WHO)) {
			theObjectOfAspect = this.getObjectDefinitionOftheActor();
		} else if (the_AspectType.equals(t_baseOnAspect.WHEN)) {
			theObjectOfAspect = this.getObjectDefinitionOftheMoment();
		} else if (the_AspectType.equals(t_baseOnAspect.WHERE)) {
			theObjectOfAspect = this.getObjectDefinitionOftheLocation();
		} else if (the_AspectType.equals(t_baseOnAspect.WHY)) {
			theObjectOfAspect = this.getObjectDefinitionOftheGoal();
		}

		return theObjectOfAspect;
	}

}
