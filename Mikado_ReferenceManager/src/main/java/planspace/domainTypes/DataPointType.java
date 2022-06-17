package planspace.domainTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.gson.Gson;

import planspace.domainTypes.ObjectType.usedToDescribeAspect;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_propertyIdentificationType;

/*Als DomeinBeheerder wil ik binnen mijn domein een eigenschap van een object  kunnen vastleggen zodat ik eigenschappen van een object kan vastleggen

Een DataPointType is de definitie van een eigenschap van een Object. Een DataPointType is een elemenair gegeven van type String, Boolean, ValueSelection, Number, Integer etc.

Daarnaast kan een DataPointType ook bestaan uit een referentie (relatie) naar een andere concept DomainType. Deze relatie werkt op basis van delegatie en is aanvullend op de IS_A overerving relatie in de DomainType taxonomie

Van een data point type wordt vastgelegd:
identificerende eigenschappen
Communicatie eigenschappen (vraag, display tekst)
Referentie naar bron
definitie van waarden die mogen worden aangenomen   (waaronder verwijzingen naar DomainTypes)
Maskers en presentatie stylen om presentatie te kunnen sturen
Eventuele default waarde(n)
Gedrag in communicatie (bijvoorbeeld mag worden gevraagd )

 */

public class DataPointType {
	public enum t_valueType {
		ELEMVALUE, OBJECTTYPE

	}

	/*
	 * Naast een technische identificatie (_id) kent een DataPointType een voor de
	 * beheerder betekenisvolle naam en omschrijving
	 * 
	 * De domeincode is een leesbare alternatieve unieke Identificatie. De
	 * leesbaarheid maakt debuggen en zoeken door mensen makkelijker dan gebruik van
	 * nietszeggende _id.
	 */
	String _id;
	String typeName;
	String description;
	List<String> synonyms = null;
	List<usedToDescribeAspect> describingAspects = null;

	/*
	 * De displayName bevat de default DisplayName. Deze wordt gebruikt indien er
	 * geen specifieke RoleBasedDisplayName gedefinieerd is voor een RoleType
	 */
	String displayName;

	@JsonIgnore()
	t_valueType myValueType = DataPointType.t_valueType.ELEMVALUE;

	/* Verwijzing naar het DomainObject (code) waarvan dit een eigenschap is */
	String myObjectType;

	/* verwijzing naar een elementaire waarde indien DP geen object verwijzing is */
	ElemValue myElemValue;

	/*
	 * dataPointCategory wordt gebruikt om binnen de bibliotheek van datapointtypes
	 * een datapoint te kunnen rubriceren. Puur voor beheerdoeleinden
	 */
	String dataPointCategory = "OTHER";

	/*
	 * Een optioneel masker geeft aan hoe een warde gepresenteerd moet worden en wat
	 * het format is voor invoer denk aan dd-mm-jjjj voor datum of IBAN voor een
	 * IBAN-rekeningnummer weergave
	 */
	String maskType = "NO_MASK";

	/*
	 * de optionele contentstype en presentationStyle bevatten instructies (strings)
	 * die in een style sheet kunnen worden gebruikt voor opmaak. -context stijl :
	 * waar komt het gegeven : in de header/body/ tabelrij etc -presentatie stijl:
	 * hoe wordt weergegevenL bold, italic, underlined etc
	 */
	String contentStyle = "STANDARD";
	String presentationStyle = "STANDARD";

	/*
	 * De datapointSource bevat een omschrijving van de bron van de waarde de
	 * databron : gebruiker, handelsregister, eigen administratie, planspace agent
	 * etc
	 */
	String datapointSource = "UNKNOWN";

	/*
	 * Het dataType beschrijft het datatype. Deze kan elementair zijn: text,
	 * boolean, valueSelection (enum) maar kan ook een referentie zijn naar een
	 * ObjectType (relatie).
	 */

	String dataType;

	/*
	 * Is askable geft aan of het gegeven gevraagd mag worden aan een menselijke
	 * gebruiker indien andere (externe bronnen) en/of planspace agenten zelf geen
	 * waarde kunnen aanleveren
	 */
	boolean isAskable = true;

	// * multi values beschrijft of de eigenschap max 1 waarde of een reeks van 0-N
	// waarden mag bevatten
	boolean multiValued = false;

	// * indien gevraagd aan een databron: indien required = true, dan moet die
	// databron een antwoord opleveren.
	boolean required = false;

	// * Een default value wordt gebruikt indien geen andere databron een waarde kan
	// opleveren. Waarden zijn van class ElemValue
	ElemValue defaultValue = null;

	// * De volgende attributen defineren het optioneel numeriek bereik waarbinnen
	// een numerieke waarde moet vallen
	int maxValueInteger;
	int minValueInteger;
	float minValueNumber;
	float maxValueNumber;

	// * Indien het datatype ValueSelection is, dan is slechts een lijst van
	// toegevoegde waarden toegestaan.
	List<AllowedValue> allowedValueList;

	/*
	 * eventueel kan per UserRole voor dit datapointtype een userRole specifieke
	 * tekst worden vastgelegd voor de vraag, displaytext of vraagtoelichting
	 */

	List<RoleBasedQuestionText> questionTextPerRole;
	List<RoleBasedJustificationText> justificationTextPerRole;

	/*
	 * Veel DatapointType zullen elementaire datatypen zijn. Integer, String,
	 * Number, Date etc etc. Echter een DataPointType ook bestaan uit een referentie
	 * (relatie) naar een andere concept DomainType. Deze relatie werkt op basis van
	 * delegatie en is aanvullend op de IS_A overerving relatie in de DomainType
	 * taxonomie
	 * 
	 * my ObjectType
	 */
	public ObjectType theObjectTypeAsDataType;

	// Eigenschappen worden gedefinieerd binnen een domen
	String domainCode;
	
	// Identificatie
	private t_propertyIdentificationType myIdentityType;
	private List<String>  isIDcombinedWith;
	
	
	public DataPointType() {
		// Constructor. Deze geeft een unieke ID en initialiseerd een DataPointType
		this.questionTextPerRole = new ArrayList<RoleBasedQuestionText>();
		this.justificationTextPerRole = new ArrayList<RoleBasedJustificationText>();
		this.allowedValueList = new ArrayList<AllowedValue>();
		this.generateId();
		this.setDataPointType("undefined");
	
		// By default the DataPointType is considered to be an elementairy value
		this.myValueType = DataPointType.t_valueType.ELEMVALUE;
		this.describingAspects = new ArrayList<usedToDescribeAspect>();
		
		//ID
		this.myIdentityType = t_propertyIdentificationType.NO_ID;
		isIDcombinedWith = new ArrayList<String>();
	}

	// getters en setters met eventuele afwijkende Json labels

	@JsonGetter("myValueType")
	public t_valueType getMyValueType() {
		return myValueType;
	}

	@JsonSetter("myValueType")
	public void setMyValueType(String myValueType) {
		this.myValueType = DataPointType.t_valueType.valueOf(myValueType);
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public List<usedToDescribeAspect> getDescribingAspects() {
		return describingAspects;
	}

	public void setDescribingAspects(List<usedToDescribeAspect> describingAspects) {
		this.describingAspects = describingAspects;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public t_propertyIdentificationType getMyIdentityType() {
		return myIdentityType;
	}

	public void setMyIdentityType(t_propertyIdentificationType myIdentityType) {
		this.myIdentityType = myIdentityType;
	}

	public List<String> getIsIDcombinedWith() {
		return isIDcombinedWith;
	}

	public void setIsIDcombinedWith(List<String> isIDcombinedWith) {
		this.isIDcombinedWith = isIDcombinedWith;
	}

	public String get_id() {
		return _id;
	}

	public String getDataType() {

		return dataType;
	}

	public List<AllowedValue> getAllowedValueList() {
		return this.allowedValueList;
	}

	public ObjectType getTheObjectTypeAsDataType() {
		return theObjectTypeAsDataType;
	}

	public void setTheObjectTypeAsDataType(ObjectType theObjectTypeasType) {
		if (theObjectTypeasType != null) {
			this.myValueType = DataPointType.t_valueType.OBJECTTYPE;
			this.dataType = "ObjectType";
			this.theObjectTypeAsDataType = theObjectTypeasType;
		}
	}

	public String getDomainCode() {
		return domainCode;
	}

	public String getMaskType() {
		return maskType;
	}

	public void setMaskType(String maskType) {
		this.maskType = maskType;
	}

	public boolean isMultiValued() {
		return multiValued;
	}

	public void setMultiValued(boolean multiValued) {
		this.multiValued = multiValued;
	}

	public int getMaxValueInteger() {
		return maxValueInteger;
	}

	public void setMaxValueInteger(int maxValueInteger) {
		this.maxValueInteger = maxValueInteger;
	}

	public int getMinValueInteger() {
		return minValueInteger;
	}

	public void setMinValueInteger(int minValueInteger) {
		this.minValueInteger = minValueInteger;
	}

	public float getMinValueNumber() {
		return minValueNumber;
	}

	public void setMinValueNumber(float minValueNumber) {
		this.minValueNumber = minValueNumber;
	}

	public float getMaxValueNumber() {
		return maxValueNumber;
	}

	public void setMaxValueNumber(float maxValueNumber) {
		this.maxValueNumber = maxValueNumber;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	protected void generateId() {
		_id = UUID.randomUUID().toString();
	}

	public String getMyObjectType() {
		return myObjectType;
	}

	public void setMyObjectType(String myObjectType) {
		this.myObjectType = myObjectType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setQuestionTextForRole(String roleType, String theQuestionText) {
		RoleBasedQuestionText newTextforRole;

		newTextforRole = new RoleBasedQuestionText(roleType, theQuestionText);
		this.questionTextPerRole.add(newTextforRole);

	}

	public void setDataPointCategory(String aCategory) {
		this.dataPointCategory = aCategory;

	}

	public void setDataType(String aDataType) {
		this.dataType = aDataType;

	}

	public void setDataPointType(String aDataPointType) {
		this.typeName = aDataPointType;

	}

	public void addAllowedValue(AllowedValue newAllowedValue) {
		this.allowedValueList.add(newAllowedValue);

	}

	public void setExplainTextForRole(String roleType, String theJustificationtext) {

		RoleBasedJustificationText newJustificationtextforRole;

		newJustificationtextforRole = new RoleBasedJustificationText(roleType, theJustificationtext);
		this.justificationTextPerRole.add(newJustificationtextforRole);

	}

	@JsonIgnore
	public void setDefaultValueAsString(String aDefaultValue) {

		ElemValue theDefaultValue;

		if (this.myValueType.equals(t_valueType.ELEMVALUE)) {
			theDefaultValue = new ElemValue();
			theDefaultValue.setValueForStringDataTypeAsString(this.dataType, aDefaultValue);
			this.defaultValue = theDefaultValue;
		}
	}

	@JsonIgnore
	public String getDefaultValueAsString() {

		String theDefaultValue;

		theDefaultValue = "";
		if (this.defaultValue != null) {
			if (this.myValueType.equals(t_valueType.ELEMVALUE)) {
				theDefaultValue = this.defaultValue.getValueAsString();

			}
		}
		return theDefaultValue;
	}

	@JsonIgnore
	public ElemValue getDefaultValueAsElemValue() {

		ElemValue theDefaultValue;

		theDefaultValue = null;
		if (this.defaultValue != null) {

			theDefaultValue = this.defaultValue;

		}
		return theDefaultValue;
	}

	public void setAskable(boolean b) {

		this.isAskable = b;
	}

	// methods

	public void addSynonym(String aSynonym) {
		if (this.synonyms == null) {
			this.synonyms = new ArrayList<String>();
		}
		this.synonyms.add(aSynonym);

	}

	public void addDescribingAspects(usedToDescribeAspect anAspect) {
		if (this.describingAspects == null) {
			this.describingAspects = new ArrayList<usedToDescribeAspect>();
		}
		this.describingAspects.add(anAspect);
	}

	

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

	public boolean isMatching(String analyzedWord) {
		boolean match;
		String toMatch;
		String myTypeToMatch;
		String aSynonym;
		int i;

		match = false;
		if (analyzedWord != null) {
			toMatch = prepareForMatch(analyzedWord);
			myTypeToMatch = prepareForMatch(this.typeName);
			
			if (myTypeToMatch.equals(toMatch)) {
				match = true;
			} else {
				// use synonyms
				if (this.getSynonyms() != null) {
					for (i = 0; i < this.getSynonyms().size(); i++) {
						aSynonym = prepareForMatch(this.getSynonyms().get(i));
						if  (aSynonym.equals(toMatch)) {
							match = true;
							break;
						}
					}
				}
				
			}
		}
		return match;
	}
	

	private String prepareForMatch(String toMatch) {
		String preparedString;

		preparedString = null;
		if (toMatch != null) {
			preparedString = toMatch.strip();
			preparedString = preparedString.toLowerCase();
		}
		return preparedString;
	}

	public void setIdentityType(t_propertyIdentificationType theIdentityType) {
	    this.myIdentityType = theIdentityType;
		
	}

}
