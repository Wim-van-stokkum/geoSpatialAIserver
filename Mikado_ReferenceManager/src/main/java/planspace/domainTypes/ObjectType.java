
package planspace.domainTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.google.gson.Gson;

import planspace.utils.PlanSpaceLogger;

/* Als DomeinBeheerder wil ik binnen mijn domein een ObjectType kunnen vastleggen zodat ik eigenschappen van dit
   object kan vastleggen

Een ObjectType is de definitie van een concept met eigenschappen. 
Een ObjectType wordt gedefinieerd in een taxonomie en kan 1 parent ObjectType en meerdere child ObjectTypen bevatten. 
Er is altijd 1 perspectief die als overerving wordt gebruikt. 
Het ObjectType zonder ouder heeft als ouder "ROOT"

Van een data point type wordt vastgelegd:
-identificerende eigenschappen
-verwijzing naar eventuele Parent ObjectType
-samenstelling van DataPointType.
-een referentie naar de bron die deze semantisch definieert: NEN, CB-NL, Wet Kamervan Koophandel , BAG etc

Per object kan middels delegatie relaties naar andere objecten hebben middels DataPointTypes van type DomainType.
Zo kan een netwerk structuur worden opgebouwd naast de primaire overervingstructuur
*/

public class ObjectType {

	public enum usedToDescribeAspect {
		LOCATION, MOMENT, SUBJECT, ACTOR, EVENT, REASON, PROPERTY, CLASSIFICATIONVALUE, IDENTIFICATIE, DIMENSION
	}

	/*
	 * ObjectTypen hebben een technische identificatie (_id) kent een domein een
	 * voor de beheerder betekenisvolle naam en omschrijving
	 * 
	 * De code is een leesbare alternatieve unieke Identificatie. De leesbaarheid
	 * van de code maakt debuggen en zoeken door mensen makkelijker dan gebruik van
	 * nietszeggende _id.
	 */

	String _id;
	String name;
	String code;
	String description;
	String domainCode;
	List<String> synonyms = null;
	List<usedToDescribeAspect> describingAspects = null;
	/*
	 * Binnen domein is er eventueel een overeverving (single inheritance) relatie
	 * tussen objecten Deze beschrijven de IS_A relatie
	 * "ik ben een voorkomen van mijn bovenliggend parent object"
	 */

	String myParentObjectID;

	/*
	 * middels de reference kan ik per object een referentie vastleggen naar een
	 * bron/standaard waarin een formele definitie is vastgelegd. Denk aan BIM
	 * standaarden als CB-NL, of NEN of OpenData datadefinities
	 */
	String reference;

	/*
	 * Een DataPointType is de definitie van een eigenschap van een Object. Een
	 * DataPointType kan een elemenair gegeven zijn van type String, Boolean,
	 * ValueSelection, Number, Integer etc. Een DataPointType kan ook een andere
	 * relatie zijn (dan de standaard IS_A overevering relatie) naar een ander
	 * concept (DomainType)
	 * 
	 */

	List<DataPointType> myDataPointTypes;

	/*
	 * Tijdens de evaluatie van regels is het zeer frequent nodig om te weten hoe
	 * een concept (DomainType) past in de taxonomie - Parents: bijvoorbeeld ben ik
	 * als auto een voertuig? - Children : is een raceauto een specialisatie van mij
	 * als auto?
	 *
	 * Om te voorkomen dat iedere keer in de repository de taxonomie moet worden
	 * afgelopen (op en neer) worden de parent en children DomainTypes bij ieder
	 * DomainType als afgeleid gegeven vastgelegd.
	 *
	 * Dit is niet intrinsiek noodzakelijk, maar wordt gedaan uit performance
	 * overwegingen
	 */
	List<String> codesOfMyParents = null;
	List<String> codesOfMyChildren = null;
	List<String> codesOfMyDirectChildren = null;

	public ObjectType() {
		// constructor: ieder DomainType krijgt een uniek ID
		// parents en children
		this._id = UUID.randomUUID().toString();

		// constructor: ieder DomainType is uit voorzorg een root object zonder
		// eigenschappen
		// parents en children
		this.setMyParentObjectID("_ROOT_");
		this.myDataPointTypes = new ArrayList<DataPointType>();
		this.codesOfMyParents = new ArrayList<String>();
		this.codesOfMyChildren = new ArrayList<String>();
		this.codesOfMyDirectChildren = new ArrayList<String>();
		this.describingAspects = new ArrayList<usedToDescribeAspect>();

	}

	// getters en setters met eventueel afwijkende Json fieldnames

	public List<usedToDescribeAspect> getDescribingAspects() {
		return describingAspects;
	}
	
	

	public void setMyDataPointTypes(List<DataPointType> myDataPointTypes) {
		this.myDataPointTypes = myDataPointTypes;
	}

	public void setDescribingAspects(List<usedToDescribeAspect> describingAspects) {
		this.describingAspects = describingAspects;
	}

	public List<String> getCodesOfMyDirectChildren() {
		return codesOfMyDirectChildren;
	}

	public void setCodesOfMyDirectChildren(List<String> codesOfMyDirectChildren) {
		this.codesOfMyDirectChildren = codesOfMyDirectChildren;
	}

	public List<String> getCodesOfMyChildren() {
		return codesOfMyChildren;
	}

	public void setCodesOfMyChildren(List<String> codesOfMyChildren) {
		this.codesOfMyChildren = codesOfMyChildren;
	}

	public List<String> getCodesOfMyParents() {
		return codesOfMyParents;
	}

	public void setCodesOfMyParents(List<String> codesOfMyParents) {
		this.codesOfMyParents = codesOfMyParents;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String anID) {
		this._id = anID;
	}

	public String getMyParentObjectID() {
		return myParentObjectID;
	}

	public void setMyParentObjectID(String myParentObjectID) {
		this.myParentObjectID = myParentObjectID;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// methodes

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
		if (this.describingAspects.contains(anAspect) == false) {
			this.describingAspects.add(anAspect);
		}
	}

	public boolean check_I_AM_by_type(String aCodeOfObjectType) {
		boolean i_AM;

		/*
		 * Deze methode kan op basis van de cached parents en/of children vast stellen
		 * hij (this) gezien kan worden als een voorkomen van het concept (DomainType)
		 * dat in de parameters is meegegeven.
		 * 
		 * Bijvoorbeeld ik als auto: Ben ik een voertuig?
		 */

		PlanSpaceLogger.getInstance().log_RuleDebug("I AM checking : " + aCodeOfObjectType + " against " + this.code
				+ " \n and parents " + this.codesOfMyParents);

		// Bij voorbaat ben ik het niet
		i_AM = false;

		/*
		 * ik ben het wel als ik exact de opgegeven DomainType ben of dat de opgegeven
		 * domaincode overeenkomt met een van mijn (cached) parents in de taxonomie
		 */
		if (this.code.equals(aCodeOfObjectType)) {
			i_AM = true;

		} else {
			if (this.codesOfMyParents.contains(aCodeOfObjectType)) {
				i_AM = true;
			}
		}
		return i_AM;
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

		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);
		meAsDocument = Document.parse(meAsJson);

		return meAsDocument;
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

	public void addDataPointType(DataPointType newDPtype) {
		// Deze methode voegt een eigenschap DataPointType toe aan het ObjectType.

		this.myDataPointTypes.add(newDPtype);
	}

	public List<DataPointType> getDataPointTypes() {
		// Deze methode geeft alle eigenschappen DataPointType toe aan het ObjectType.
		return this.myDataPointTypes;
	}

	public DataPointType getDataPointTypeByCode(String DPTcode) {
		int i;
		DataPointType aDPT;
		
		aDPT = null;
		
		for (i = 0 ; i < this.myDataPointTypes.size(); i++) {
			if (this.myDataPointTypes.get(i).typeName.equals(DPTcode))	{
				aDPT = this.myDataPointTypes.get(i);
				break;
			}
		}
		return aDPT;
	}

	
}
