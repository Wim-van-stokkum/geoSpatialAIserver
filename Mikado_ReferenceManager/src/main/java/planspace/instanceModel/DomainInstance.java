package planspace.instanceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import planspace.domainTypes.ElemValue;
import planspace.domainTypes.ObjectType;
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

Als PlanSpaceAgent moet ik voorkomens van ObjectTypen kunnen lezen en delen, zodat ik binnen
een domein gegevens kan uitwisselen
De DomainInstance is een instantie van een ObjectType en houdt de gegevens DataPointValues 
voor eventuele eigenschappen.


 */
public class DomainInstance {
	// Elke instance heeft een eigen uniek ID en leeft in een domein
	String _id;
	String domainCode = "null";

	// De DomainInstance is een instantie van een ObjectType
	ObjectType myObjectType;

	// De instanties kan waarden hebben voor 0 of meerdere DataPointTypes vam het
	// ObjectType
	public List<DataPointValue> dataPointValues;

// Constructor

	public DomainInstance() {
		// * Create an DomainInstance, set a unique ID and initialise both values as
		// definition to unknown
		this._id = String.valueOf(UUID.randomUUID());
		this.dataPointValues = new ArrayList<DataPointValue>();
	
	}

	// Getters en setters

	public String get_id() {
		// gets the unique technical ID of this Instance
		return _id;
	}

	

	// instances leven in een domain.

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}




	public ObjectType getMyObjectType() {
		return myObjectType;
	}

	public void setMyObjectType(ObjectType myObjectType) {
		this.myObjectType = myObjectType;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	// methods
	public void addDataPointValue(DataPointValue aDataPointValue) {
		// Deze methode voegt een waarde to voor een DataPointType van het ObjectType

		this.dataPointValues.add(aDataPointValue);

	}


	public DataPointValue getDataPointValueByTypeName(String DP_DataPointTypeCode) {
		/*
		 * This method will return a DataPointValue of this instance having the
		 * DataPointType name corresponding specified argument
		 */
		int i;
		DataPointValue dataPointValueFound, aDataPointValue;

		dataPointValueFound = null;
		for (i = 0; i < this.dataPointValues.size(); i++) {
			aDataPointValue = this.dataPointValues.get(i);
			if (aDataPointValue.getDataPointTypeCode().equals(DP_DataPointTypeCode)) {
				// match
				dataPointValueFound = aDataPointValue;
				break;
			}
		}

		return dataPointValueFound;
	}

	public ElemValue getElemValue_of_DataPointValueByTypeName(String DP_DataPointTypeCode) {
		/*
		 * This method will return the elementary value of a DataPointValue of this
		 * instance having the DataPointType name corresponding specified argument
		 */

		ElemValue theResultValue;
		DataPointValue dataPointValue_of_theDomainInstance;

		// By default no ElemValue
		theResultValue = null;
		dataPointValue_of_theDomainInstance = this.getDataPointValueByTypeName(DP_DataPointTypeCode);

		if (dataPointValue_of_theDomainInstance != null) {
			/*
			 * If found we will compare. comparing elementairy values will be different from
			 * comparing complete instances
			 */
			if (dataPointValue_of_theDomainInstance.isElemValue()) {
				// In case of elementairy value we get the value of the other property to
				// compare with
				theResultValue = dataPointValue_of_theDomainInstance.getElemValueAsValue();
			}

		}
		return theResultValue;
	}
	
	

	public List<DataPointValue> getDataPointValues() {
		return dataPointValues;
	}

	public void setDataPointValues(List<DataPointValue> dataPointValues) {
		this.dataPointValues = dataPointValues;
	}

	@JsonIgnore()
	public String getValueDescriptions() {
		int i;
		DataPointValue aDataPointValue;
		String valueDescription, valueDescriptions;

		valueDescriptions = "";
		for (i = 0; i < this.dataPointValues.size(); i++) {
			aDataPointValue = this.dataPointValues.get(i);
			if (valueDescriptions.equals("") == false) {
				valueDescriptions = valueDescriptions + ", ";
			}

			valueDescription = aDataPointValue.getValueDescription();
			valueDescriptions = valueDescriptions + valueDescription;

		}
		return valueDescriptions;
	}
	
	@JsonIgnore()
	public void setForObjectByCode(String inDomainCode, String anObjectTypeCode) {
		/*
		 * Deze methode initialiseert de instantie door het ObjectType bekend te maken
		 * De methode haalt de definitie op het het ObjectType uit de repository op
		 * basis van de ObjectType code en de Domein code en koppelt de opgehaalde
		 * definitie vervolgens aan de DomainInstance
		 */

		InterfaceToDomainTypeRepository theDomainIFC;
		theDomainIFC = InterfaceToDomainTypeRepository.getInstance();
		

		ObjectType theObjectTypeFound = null;

		// Haal interface naar Repository op
		this.setDomainCode(inDomainCode);

		// Zoek definitie in repository van ObjectType in het Domein
		theObjectTypeFound = theDomainIFC.findObjectTypeByTypename(inDomainCode, anObjectTypeCode);

		// Als gevonden koppel aan de instantie
		if (theObjectTypeFound != null) {
			this.myObjectType = theObjectTypeFound;
		} else {
			PlanSpaceLogger.getInstance()
					.log("ERROR: cannot instantiate for " + anObjectTypeCode + " in Domain " + this.getDomainCode());

		}

	}

}
