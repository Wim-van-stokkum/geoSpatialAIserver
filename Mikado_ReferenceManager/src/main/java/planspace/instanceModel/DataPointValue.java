package planspace.instanceModel;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import planspace.domainTypes.DataPointType;
import planspace.domainTypes.ElemValue;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.utils.PlanSpaceLogger;

/* Een DataPointValue 
Als PlanSpace component moet ik gegevens kunnen begrijpen in relatie tot hun domein definitie, z
odat ik kan opereren in een domein.

Een DomeinBeheerder maakt de definities van concepten in het domein. In de operatie ontstaat binnen het domein gegevens en gebeurtenissen.  
Deze gegevens en gebeurtenissen woden uitgedrukt in termen conform domein.
Voor het kunnen beschrijven van een instantie of voorkomen van een concept is er een Instantiemodel nodig. 
Dit instantiemodel zal in de interactie tussen agenten worden gebruikt en zal worden gebruikt om 
hierop bewerkingen en verrijkingen te doen


Als PlanSpaceAgent moet ik voor een DomainInstance de waarden voor eigenschappen kunnen lezen 
vastleggen en delen,  zodat ik binnen een domein gegevens kan uitwisselen

De DataPointValueis een waarde voor een DataPointType van een ObjectType
en houdt een waarde vast voor dit DataPointType. 

Afhankelijk van de definitie van het DataPointType kan de waarde  elementair van aard zijn (boolean, string, integer etc),
of kan het een instantie zijn van een ObjectType. In het laatste geval is er dan sprake van een delegate relatie aanvullend op de IS_A 
standaard overerving relatie in de ObjectType taxonomy

 */

public class DataPointValue {

	// Een waarde heeft een technische unieke ID.
	private String _id;

	// het gaat om een waarde voor een DataPointType eigenschap in een domein
	

	private String domainCode;
	private String dataPointCode;
	/* 
	 * Afhankelijk van de definitie van het DataPointType kan de waarde elementair
	 * van aard zijn (boolean, string, integer etc), of kan het een instantie zijn
	 * van een ObjectType. In het laatste geval is er dan sprake van een delegate
	 * relatie aanvullend op de IS_A standaard overerving relatie in de ObjectType
	 * taxonomy
	 */

	ElemValue elemValueAsValue = null;
	DomainInstance instanceAsValue = null;

	private DataPointType myDataPointDefinition;
	
	//private transient DataPointType myDataPointDefinition;

	// constructor

	public DataPointValue() {
		// Er wordt een uniek ID gegenereerd
		this.generateId();
		
		// Er is nog geen definitie of waarde
		this.myDataPointDefinition = null;
		this.instanceAsValue = null;
		this.elemValueAsValue = null;

	}

	protected void generateId() {
		_id = UUID.randomUUID().toString();
	}

	
	// Als de waarde een Elementaire waarde (boolean, string, date etc) is, dan zijn
	// onderstaande methods van toepassing



	public boolean isElemValue() {
		// Deze methode geeft terug of de waarde elementair van aard is (boolean, string, data, number etc
		
		boolean isElem = false;

		if (this.getMyDataPointDefinition() != null) {
			if (this.myDataPointDefinition.getMyValueType().equals(DataPointType.t_valueType.ELEMVALUE)) {
				isElem = true;
			}
		}
		return isElem;
	}

	
	

	

	@JsonGetter("elemValue")
	public ElemValue getElemValueAsValue() {
		/* Deze methode geeft het object van de elementaire waarde terug. 
			Met dit object kan de waarde worden benaderd, maar kunnen ook bewerkingen en vergelijkingen worden gedaan*/ 
		return elemValueAsValue;
	}

	@JsonSetter("elemValue")
	public void setElemValueAsValue(ElemValue elemValueAsValue) {
		/* Deze methode zet een elementaire waarde als object */ 
		this.elemValueAsValue = elemValueAsValue;
	}

	public String getElemValueAsString() {
		/* Deze methode geeft de waarde als string terug, ongeacht dataType 
		 * handig voor afdrukken of communiceren */
		String s_value;

		s_value = "unknown";
		if (this.isElemValue() && (this.getElemValueAsValue() != null)) {
			s_value = this.getElemValueAsValue().getValueAsString();
		}
		return s_value;
	}

	public void setElemValueAsString(String elemValue) {
		/* Deze methode accepteerd een elementaire waarde als String 
		 * In PlanSpace is een waarde echter een Object. Door het
		 * datatype te combineren met de opgegeven waarde kan het object worden aangemaakt en gekoppeld als waarde
		 */
		String dataTypeName;
		ElemValue theValue;

		if (this.myDataPointDefinition != null) {
			dataTypeName = myDataPointDefinition.getDataType();
			theValue = new ElemValue(dataTypeName, elemValue);
			this.setElemValueAsValue(theValue);
		} else {
			PlanSpaceLogger.getInstance().log("ERROR: setting value as string voor unknown datapointtype");
		}

	}
	
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getDataPointCode() {
		return dataPointCode;
	}

	public void setDataPointCode(String dataPointCode) {
		this.dataPointCode = dataPointCode;
	}

	/*
	 * =============================================================================
	 * ================== Als de waarde een referentie is naar een Object is, dan
	 * zijn onderstaande methods van toepassing
	 * =============================================================================
	 * =================
	 */

	@JsonIgnore()
	public boolean isObjectTypeValue() {
		/*
		 * middels deze methode kan worden opgevraagd of de DataPointValue wordt gevormd
		 * door een referentie naar een ObjectType. Er is dan sprake van een delegate
		 * relatie aanvullend op de IS_A overerving relatie in de ObjectType taxonomy
		 */
		boolean isObjectType = false;

		if (this.myDataPointDefinition != null) {
			if (this.myDataPointDefinition.getMyValueType().equals(DataPointType.t_valueType.OBJECTTYPE)) {
				isObjectType = true;
			}
		}
		return isObjectType;
	}

	public DomainInstance getInstanceAsValue() {
		/* Indien het DataPointType een ObjectType referentie als waare heeft, dan wordt de waarde gevormd
		 * door een instantie van dit ObjectType. Deze methode geeft de waarde (de Instantie) terug.
		 * 
		 */
		DomainInstance theInstValue;
		
		theInstValue = null;
		if (this.isObjectTypeValue()) {

			theInstValue = this.instanceAsValue;
		}
		
		return theInstValue;
	}

	public boolean setInstanceAsValue(DomainInstance anInstanceAsValue) {
		boolean ok;

		/* Indien het DataPointType een ObjectType referentie als waare heeft, dan wordt de waarde gevormd
		 * door een instantie van dit ObjectType. Deze methode zet de instantie die als Argument wordt meegegeven
		 * als waarde mits het DataPointType dit toestaat 
		 * 
		 */
		
		ok = false;
		if (anInstanceAsValue != null) {

			ok = false;
			if (this.myDataPointDefinition != null) {
				if (this.isObjectTypeValue() ) {
					if (this.myDataPointDefinition.getTypeName().equals(anInstanceAsValue.getMyObjectType())) {
						ok = true;
						this.instanceAsValue = anInstanceAsValue;
					}
				} else {
					PlanSpaceLogger.getInstance()
							.log("Error setting Instance for dataPointtype being elementairy value");

				}
			}
			if (ok == false) {
				PlanSpaceLogger.getInstance().log("Error setting Instance as value to datapoint");
			}
		} else {
			// accept null
			this.instanceAsValue = null;
			ok = true;
		}

		return ok;
	}

	
	/*
	 * De volgende methodes ontsluiten de definitie van het DatapPointType
	 * 
	 */

	public DataPointType getMyDataPointDefinition() {
	
		return this.myDataPointDefinition ;
	}

	public void setMyDataPointDefinition(DataPointType myDataPointDefinition) {
		// Methode koppelt de definitie van de eigenschap waarvoor een waarde wordt opgegeven.
		this.myDataPointDefinition = myDataPointDefinition;
	}

	@JsonIgnore()
	public String getDataPointTypeCode() {
		// Methode geeft de code van het DataPointType definitie
		String theCode = "null";

		if (this.myDataPointDefinition != null) {
			theCode = this.myDataPointDefinition.getTypeName();

		}
		return theCode;
	}

	@JsonIgnore()
	public String getDataPointTypeID() {
		// Methode geeft de unieke technische ID van het DataPointType definitie
		String theID = "null";

		if (this.myDataPointDefinition != null) {
			theID = this.myDataPointDefinition.get_id();

		}
		return theID;
	}

	// Initialisatie methoden

	public void setForDataPointTypeByCode(String inDomainCode, String aDataPointType) {
		/*
		 * Deze methode initialiseert een DataPointValue doormiddel van de
		 * DataPointDefinitie Op basis van domeincode en DataPointType zal uit de
		 * repository de definitie van het DataPoinType worden opgehaald en worden
		 * gekoppeld aan deze datapointValue. Daarna kan de waarde van het DataPointType
		 * worden opgegeven middels een van bovenstaande methodes (ElemValue of
		 * DomainInstance)
		 */
		
		InterfaceToDomainTypeRepository theDomainIFC;
		DataPointType theDataPointTypeFound;

		 theDataPointTypeFound = null;

		// Ophalen interface naar repository
		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();
		this.domainCode = inDomainCode;

		// Ophalen DataPointType definitie uit repository
		theDataPointTypeFound = theDomainIFC.findDataPointTypeByTypename(inDomainCode, aDataPointType);
		if (theDataPointTypeFound != null) {

			// koppelen DataPointType definitie aan deze DataPointValue
			this.myDataPointDefinition = theDataPointTypeFound;
		} else {
			PlanSpaceLogger.getInstance().log(
					"ERROR: cannot find datapoint type definition " + aDataPointType + " in Domain " + inDomainCode);

		}

	}
	
	

	@JsonIgnore()
	public String getValueDescription() {
		String myDescription;
		
		myDescription = "";
		if (this.isElemValue()) {
			myDescription = "["+ this.getDataPointTypeCode() + "=" + this.getElemValueAsString() +  "]";
		}
		if (this.isObjectTypeValue()) {
			myDescription = "["+ this.getDataPointTypeCode() + "=(" + this.getInstanceAsValue().getValueDescriptions() +  ")]";
		}
	
		return myDescription;
	}



}
