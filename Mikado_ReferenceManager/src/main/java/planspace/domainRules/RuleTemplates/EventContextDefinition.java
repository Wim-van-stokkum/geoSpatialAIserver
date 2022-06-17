package planspace.domainRules.RuleTemplates;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import planspace.instanceModel.DomainInstance;

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
 *  Elke aspect dat betrokken is bij totstandkoming Event  wordt beschreven middels een DomainInstance van een ObjectType in de 
 *  domein onthologie. 
 */

public class EventContextDefinition {

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
	String generatedDescription;
	String generatedDetailedDescription;

	/*
	 * Elke aspect dat relevant is wordt beschreven middels een DomainInstance van
	 * een ObjectType in de domein onthologie die is opgesteld voor het domein
	 */

	// HOE
	DomainInstance theActivityCausingEvent = null;

	// WAT
	DomainInstance theSubjectInvolved = null;

	// WAAR
	DomainInstance theLocation = null;

	// WIE
	DomainInstance theActor = null;

	// WANNEER
	DomainInstance theMoment = null;

	// WAAROM
	DomainInstance theGoal = null;

	public EventContextDefinition() {
		/*
		 * Constructor
		 * 
		 */

		theActivityCausingEvent = null;
		theSubjectInvolved = null;
		theLocation = null;
		theActor = null;
		theMoment = null;
		theGoal = null;
		generatedDescription = "";
		generatedDetailedDescription = "";
	}

	/* getters en setters */
	public String getInformalDescription() {

		return informalDescription;
	}

	public void setInformalDescription(String informalDescription) {
		this.informalDescription = informalDescription;
	}

	public DomainInstance getTheActivityCausingEvent() {
		return theActivityCausingEvent;
	}

	public void setTheActivityCausingEvent(DomainInstance theActivityCausingEvent) {
		this.theActivityCausingEvent = theActivityCausingEvent;
	}

	public DomainInstance getTheSubjectInvolved() {
		return theSubjectInvolved;
	}

	public void setTheSubjectInvolved(DomainInstance theSubjectInvolved) {
		this.theSubjectInvolved = theSubjectInvolved;
	}

	@JsonSetter("theLocation")
	public DomainInstance getTheLocation() {
		return theLocation;
	}

	@JsonSetter("theLocation")
	public void setTheLocation(DomainInstance theLocation) {
		this.theLocation = theLocation;
	}

	public DomainInstance getTheActor() {
		return theActor;
	}

	public void setTheActor(DomainInstance theActor) {
		this.theActor = theActor;
	}

	@JsonSetter("theMoment")
	public DomainInstance getTheMoment() {
		return theMoment;
	}

	@JsonSetter("theMoment")

	public void setTheMoment(DomainInstance theMoment) {
		this.theMoment = theMoment;
	}

	public DomainInstance getTheGoal() {
		return theGoal;
	}

	public void setTheGoal(DomainInstance theGoal) {
		this.theGoal = theGoal;
	}

	public void UpdateGeneratedDescription() {
		String descriptionGenenerated;

		descriptionGenenerated = "Gebeurtenis:";
		if (this.isOnSpecificActivityEvent()) {
			descriptionGenenerated = descriptionGenenerated + " activiteit "
					+ this.getTheActivityCausingEvent().getMyObjectType();
		}

		if (this.isSpecificSubjectInvolved()) {
			descriptionGenenerated = descriptionGenenerated + " onderwerp "
					+ this.getTheSubjectInvolved().getMyObjectType();
		}

		if (this.isInvolvingSpecificActor()) {
			descriptionGenenerated = descriptionGenenerated + " door actor " + this.getTheActor().getMyObjectType();
		}

		if (this.isAtSpecificLocation()) {
			descriptionGenenerated = descriptionGenenerated + " op locatie "
					+ this.getTheLocation().getMyObjectType();
		}
		if (this.isOnSpecificMoment()) {
			descriptionGenenerated = descriptionGenenerated + " op moment " + this.getTheMoment().getMyObjectType();
		}

		if (this.isForSpecificGoal()) {
			descriptionGenenerated = descriptionGenenerated + " , met als doel "
					+ this.getTheGoal().getMyObjectType();
		}
		this.generatedDescription = descriptionGenenerated;

	}

	public void UpdateDetailedGeneratedDescription() {
		String detailedDescriptionGenenerated;
		String valueDescription;

		detailedDescriptionGenenerated = "Gebeurtenis:";
		if (this.isOnSpecificActivityEvent()) {
			valueDescription = this.getTheActivityCausingEvent().getValueDescriptions();
			if (valueDescription.equals("")) {
				detailedDescriptionGenenerated = detailedDescriptionGenenerated + " activiteit "
						+ this.getTheActivityCausingEvent().getMyObjectType();
			} else {
				detailedDescriptionGenenerated = detailedDescriptionGenenerated + " activiteit "
						+ this.getTheActivityCausingEvent().getMyObjectType() + " met " + valueDescription;
			}
		}

		if (this.isSpecificSubjectInvolved()) {
			valueDescription = this.getTheSubjectInvolved().getValueDescriptions();
			if (valueDescription.equals("")) {
			detailedDescriptionGenenerated = detailedDescriptionGenenerated + " onderwerp "
					+ this.getTheSubjectInvolved().getMyObjectType();
			} else {
				detailedDescriptionGenenerated = detailedDescriptionGenenerated + " activiteit "
						+ this.getTheSubjectInvolved().getMyObjectType() + "  met " + valueDescription;
			}
		}

		if (this.isInvolvingSpecificActor()) {
			valueDescription = this.getTheActor().getValueDescriptions();
			if (valueDescription.equals("")) {
			detailedDescriptionGenenerated = detailedDescriptionGenenerated + " door actor "
					+ this.getTheActor().getMyObjectType();
			} else {
				detailedDescriptionGenenerated = detailedDescriptionGenenerated + " activiteit "
						+ this.getTheActor().getMyObjectType() + " met " + valueDescription;
			}
		}

		if (this.isAtSpecificLocation()) {
			valueDescription = this.getTheLocation().getValueDescriptions();
			if (valueDescription.equals("")) {
			detailedDescriptionGenenerated = detailedDescriptionGenenerated + " op locatie "
					+ this.getTheLocation().getMyObjectType();
			} else {
				detailedDescriptionGenenerated = detailedDescriptionGenenerated + " activiteit "
						+ this.getTheLocation().getMyObjectType() + " met " + valueDescription;
			}
			
		}
		if (this.isOnSpecificMoment()) {
			valueDescription = this.getTheMoment().getValueDescriptions();
			if (valueDescription.equals("")) {
			detailedDescriptionGenenerated = detailedDescriptionGenenerated + " op moment "
					+ this.getTheMoment().getMyObjectType();
			} else {
				detailedDescriptionGenenerated = detailedDescriptionGenenerated + " activiteit "
						+ this.getTheMoment().getMyObjectType() + " met " + valueDescription;
			}
		}

		if (this.isForSpecificGoal()) {
			valueDescription = this.getTheGoal().getValueDescriptions();
			if (valueDescription.equals("")) {
			detailedDescriptionGenenerated = detailedDescriptionGenenerated + " , met als doel "
					+ this.getTheGoal().getMyObjectType();
			} else {
				detailedDescriptionGenenerated = detailedDescriptionGenenerated + " activiteit "
						+ this.getTheGoal().getMyObjectType() + " met " + valueDescription;
			}
		}
		this.generatedDetailedDescription = detailedDescriptionGenenerated;

	}

	/* Generation description */
	@JsonGetter("generatedDescription")
	public String GetGeneratedDescription() {

		this.UpdateGeneratedDescription();
	
		return this.generatedDescription;
	}

	@JsonSetter("generatedDescription")
	public void generatedDescription(String description) {
		if (this.informalDescription == null) {
			this.informalDescription = description;
		} else if (this.informalDescription.equals("")) {
			this.informalDescription = description;
		}
		this.generatedDescription = description;
	}
	
	@JsonGetter("generatedDetailedDescription")
	public String GetGeneratedDetailedDescription() {

		this.UpdateDetailedGeneratedDescription();
	
		return this.generatedDetailedDescription;
	}

	@JsonSetter("generatedDetailedDescription")
	public void generatedDetailedDescription(String description) {

		this.generatedDetailedDescription = description;
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

}
