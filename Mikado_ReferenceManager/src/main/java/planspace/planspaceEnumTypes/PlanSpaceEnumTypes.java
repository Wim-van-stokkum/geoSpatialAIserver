package planspace.planspaceEnumTypes;

public class PlanSpaceEnumTypes {
	public enum t_agentObjectiveType {
		CLASSIFY_EXTERNAL_EVENTS, DETERMINE_HYPOTHETICAL_IMPACT, CLASSIFICATION_POTENTIAL_IMPACT,

		// * TO DO
		PROVE_HYPOTHETICAL_IMPACT, CHECK_FACTS
	}

	public enum t_elemExpressionOperator {
		// is_subset_of,
		IS_EQUAL, IS_GREATER, IS_GREATER_EQUAL, IS_LESS, IS_LESS_EQUAL

	}

	public enum t_language {
		NL, UK
	}

	public enum t_adjectivetype {
		NO_ADJECTIVE, SIZE, VOLUME, AGE, POS_EMOTION, NEG_EMOTION, STATE, TIME, COLOR
	}

	public enum t_itemTypeDropped {
		SOURCE_REFERENCE, TAXONOMY_ITEM, UNKNOWN
	}
	
	/* The aspects of the context definition */

	public enum t_aspectInSentence {
		WHO, WHAT, WHERE, HOW, WHEN, WHY, UNKNOWN
	}
	
	public enum t_contextAspect {
		HOW, WHERE, WHAT, WHEN, WHO, WHY
	}
	
	public enum t_YesNoUnknownNVT {
		yes, no, unknown, na
	}
	
	/*
	 * Dit is een initiele lijst van elementaire datatypen die PlanSpace
	 * ondersteund. Toelichting elementaire datatypen:
	 *  - een geheel getal(INTEGERVALUE) : 156 
	 *  - een numeriek getal met decimalen (NUMBER): 156.56 
	 *  - Een textuele waarde : "Wim" 
	 *  - Een currency van een type geld ( Currency_Euro): euro 123.45 
	 *  - Een pad naar een bestand of URL 
	 *  - Een Ja/Nee/Onbekend waarde (Thruth Value) 
	 *  - Een selectie van vooraf gedefinieerd waarden (ALlowedValues)
	 * - Een tekst met newlines en andere opmaak : Memo 
	 * - Een datum - Een onbekende waarde: UNKNOWN
	 */

	public enum t_elemDataType {
		TEXT, INTEGERVALUE, NUMBER, CURRENCY_EURO, FILE_URL, FILE_PATH, TRUTHVALUE, VALUESELECTION, MEMO, UNKNOWN,
		DATE_EURO

		// to de defined or extented in pilot
	}
	
	
	/********************
	 * 
	 *TASKS
	 *
	 */
	public enum t_taskCategory{ MANAGE_RULE, MANAGE_DOMAIN, MANAGE_NEWDOMAIN, MANAGE_EVENT}  
	
	public enum t_taskType { 
		ADD_CONCEPTS, ADD_CONCEPT, DESCRIBE_CONCEPT, SELECT_POTENTIAL_SPECIFIC_CONCEPT, 
		SELECT_OPTIONAL_SUBCONCEPTS_TO_TRANSFER, ADD_CONCEPT_TO_REPOSITORY,
		ADD_CONCEPTS_PROPERTIES, ADD_CONCEPT_PROPERTY, SPECIFIC_PROPERTY_CHARACTERISTICS,
		ADD_CONCEPT_PROPERTY_TO_REPOSITORY, ADD_NEW_DOMAIN, ADD_NEW_RULE, REGISTER_EVENT, CREATE_AND_SENT_EVENT,
		CREATE_RULE_CONCLUSION, STORE_NEW_RULE
		
	}
	
	public enum t_taskStatus { 
		OPEN, STARTED, POSTPONED, FINISHED, CANCELLED
	}
	
	public enum t_taskPosition {
		atTOP, atEND
	}
	

	/*************concept properties
	 * 
	 *
	 */
	
	public enum t_propertyType{
		IDENTICATION, DATAVALUE, STATE
	}
	
	public enum t_propertyDynamic {
		GIVEN_FACT, DYNAMIC_VALUE
	}
	
	public enum t_propertyIdentificationType {
		SOLE, COMBINED, NO_ID
	}
	
	
	/** Word classifications
	 * 
	 */
	public enum t_EntityCategory {
		PERSON, NATURAL_PERSONTYPE, NATURAL_PERSONROLETYPE, LOCATION, ORGANIZATION, EVENT, PRODUCT, SKILL, ADDRESS,
		PHONENUMBER, EMAIL, DATETIME, QUANTITY, OBJECT_BELONGING_TO, UNKNOWN, TIME, DATE, IDENTITY, THING, DIMENSION,
		MOTIVATION, CONCEPT_PROPERTY, PARENT_CONCEPT, ACTION, CONDITIONOPERATOR, 
		MOMENT_REFERENCE, LOCATION_REFERENCE, ACTOR_REFERENCE, REASON_REFERENCE,EVENT_REFERENCE
	}

	public enum t_WordGrammarType {
		ARTICLE /* lidwoord */, NOUN, /* zelfstandig naamwoord */
		ADJECTIVE /* bijvoegelijk naamwoord */, VERB /* werkwoord */, NOISE /* ruiswoord */, COUNT_WORD /* telwoord */,
		DEMONSTRATIVE_ANOUN /* aanwijzend voornaamwoord */, ADVERB /* BIJWOORD */
	}

	public enum t_Sentiment {
		NEUTRAL, POSITIVE, NEGATIVE
	}

	public enum t_ActionProcessRequirement {
		MUST_DO, CAN_DO, SHOULD_DO, NONE
	}
	 
	public enum t_provenType { UNKNOWN, UNPROVEN, PROVEN}
	
	/* Annotation */
	public enum t_chefType {
		GOAL, RISK, OPPORTUNITY, MEASUREMENT, FACT, RULE, EVENT, NORM,  DATA, DATASOURCE,  UNKNOWN
	}

	public enum t_annotationType {
		UNHIGHLIGHTED_TEXT, HIGHLIGHTED_TEXT, UNKNOWN_TYPE, FREETEXT, LINK
	}

	
	public enum t_goalArcheType {
		STIMULATE, DISCOURAGE, MAINTAIN, FORBID, REGULATE, UNKNOWN
	}
	
	public enum t_sourceType {
		DOCUMENT, MANUAL
	}

	public enum t_impact {
		UNKNOWN, LOW, NEUTRAL, MEDIUM, HIGH, UNACCEPTABLE
	}
	
	public enum t_monitoringState {
		UNKNOWN, LOW, NEUTRAL, MEDIUM, HIGH, UNACCEPTABLE, TRUE, FALSE, AVAILABLE, UNAVAILABLE, ASSIGNED, EXECUTED, DUE
		// to de defined or extented in pilot
	}
	
	public enum t_BayBoolean { TRUE, FALSE, UNKNOWN} 
	
	public enum t_stateRegime { LEVELED, BINARY, AVAILABLE, TASKSTATE }
	
	public enum t_ChefTypeConditiontype {
		CHEFTYPE_HAS_STATE, ALL_UNDERLYING, ONE_UNDERLYING
	}
	
	
	public enum t_caseArcheType { 
		REQUEST, PROJECT, CONCERN		
	}
	
}
