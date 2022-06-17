package planspace.domainTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;

import planspace.domainTypes.ObjectType.usedToDescribeAspect;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.utils.PlanSpaceLogger;

public class TaxonomyNode {

	String _id;
	String name;
	String code;
	List<String> synonyms = null;
	String description;
	String myParentObjectID;
	List<TaxonomyNode> myChildren;
	String domainCode;
	List<usedToDescribeAspect> describingAspects = null;

	public TaxonomyNode() {
		// Constructor
		this.myChildren = new ArrayList<TaxonomyNode>();

	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public List<usedToDescribeAspect> getDescribingAspects() {
		return describingAspects;
	}

	public void setDescribingAspects(List<usedToDescribeAspect> describingAspects) {
		this.describingAspects = describingAspects;
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

	public void set_id(String _id) {
		this._id = _id;
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

	public String getMyParentObjectID() {
		return myParentObjectID;
	}

	public void setMyParentObjectID(String myParentObjectID) {
		this.myParentObjectID = myParentObjectID;
	}

	public List<TaxonomyNode> getMyChildren() {
		return myChildren;
	}

	public void setMyChildren(List<TaxonomyNode> myChildren) {
		this.myChildren = myChildren;
	}

	public void addChildNode(TaxonomyNode taxonomyNodeOfMyChild) {
		this.myChildren.add(taxonomyNodeOfMyChild);

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

		// Gson is de externe utility class (zie POM.XML) die Java Classes kan omzetten
		// naar Json.
		gsonParser = new Gson();

		// Ik zet mijzelf (instantie van deze class) om naar Json, en deze wordt binair
		// gemaakt om
		// in MongoDB opgeslagen te kunnen worden.

		meAsJson = gsonParser.toJson(this);
		meAsDocument = Document.parse(meAsJson);

		return meAsDocument;
	}

	public TaxonomyNode getForCodeType(String startAt) {
		TaxonomyNode subTree, aChild;
		int i;

		subTree = null;
		if (this.getCode().equals(startAt)) {
			subTree = this;
		} else {
			for (i = 0; i < this.myChildren.size(); i++) {
				aChild = this.myChildren.get(i);
				subTree = aChild.getForCodeType(startAt);
				if (subTree != null) {
					// found

					break;
				}
			}
		}

		return subTree;
	}

	public static String getTreeName(TaxonomyNode theNode) {
		return theNode.getName();
	}

	public static String getTreeDescription(TaxonomyNode theNode) {
		return theNode.getDescription();
	}

	public TaxonomyNode matchTypeName(String typeNameToMatch) {
		TaxonomyNode foundConcept;
		String toMatch;

		toMatch = typeNameToMatch;
		foundConcept = matchTypeNameAssumingForm(toMatch);
		if (foundConcept == null) {
			toMatch = getSignularForm(typeNameToMatch);
			if (toMatch.equals(typeNameToMatch) == false) {
				// PlanSpaceLogger.getInstance().log_LUI("Singular processing: " +
				// typeNameToMatch + " -> " + toMatch);
				foundConcept = matchTypeNameAssumingForm(toMatch);
			}
		}
		return foundConcept;
	}

	private String getSignularForm(String typeNameToMatch) {
		String result;

		result = typeNameToMatch;
		if (typeNameToMatch != null) {
			if (typeNameToMatch.endsWith("en")) {
				result = getSignularFormEndsOn_EN(typeNameToMatch);
			} else if (typeNameToMatch.endsWith("tjes")) {
				result = typeNameToMatch.substring(0, typeNameToMatch.length() - 4);
			} else if (typeNameToMatch.endsWith("tje")) {
				result = typeNameToMatch.substring(0, typeNameToMatch.length() - 3);
			} else if (typeNameToMatch.endsWith("pjes")) {
				result = typeNameToMatch.substring(0, typeNameToMatch.length() - 4);
			}
		}

		return result;
	}

	private String getSignularFormEndsOn_EN(String typeNameToMatch) {
		String result;
		String toSingle;
		int wlen;
		char laatsteKarakter, eenNaLaatsteKarakter;

		result = typeNameToMatch;
		if (typeNameToMatch != null) {
			// sloop en aan eind er af
			if (typeNameToMatch.endsWith("en")) {

				wlen = typeNameToMatch.length();
				result = typeNameToMatch.substring(0, wlen - 2);
				toSingle = typeNameToMatch.substring(0, wlen - 2);

				wlen = toSingle.length();
				if (wlen > 2) {
					laatsteKarakter = toSingle.charAt(wlen - 1);
					eenNaLaatsteKarakter = toSingle.charAt(wlen - 2);

					if (laatsteKarakter == 'i') {
						// koeien -> koe
						result = toSingle.substring(0, wlen - 1);

					} else {

						if (laatsteKarakter == eenNaLaatsteKarakter) {
							// kippen, rennen, stappen
							result = toSingle.substring(0, wlen - 1); // kip, ren, stap

						} else {
							// bomen, ramen

							// check of klinker
							if ((eenNaLaatsteKarakter == 'a') || (eenNaLaatsteKarakter == 'e')
									|| (eenNaLaatsteKarakter == 'i') || (eenNaLaatsteKarakter == 'o')
									|| (eenNaLaatsteKarakter == 'u')) {
								result = toSingle.substring(0, wlen - 1);
								result = result + eenNaLaatsteKarakter; // bomen -> bom -> boo
								result = result + laatsteKarakter; // boo -> boom

							}

						}
					}
				}

			}

		}

		return result;
	}

	private TaxonomyNode matchTypeNameAssumingForm(String typeNameToMatch) {
		TaxonomyNode foundConcept, aChild;
		int i;

		foundConcept = null;
		if (isDomainTypeCodeMatching(typeNameToMatch)) {
			foundConcept = this;
		} else {
			for (i = 0; i < this.myChildren.size(); i++) {
				aChild = this.myChildren.get(i);
				foundConcept = aChild.matchTypeName(typeNameToMatch);
				if (foundConcept != null) {
					// found

					break;
				}
			}
		}

		return foundConcept;
	}

	private boolean isDomainTypeCodeMatching(String typeNameToMatch) {
		boolean isMatch;
		String aSynonym;
		String convertedTypeNameToMatch;
		String convertedTypeNameTax;

		int i;

		isMatch = false;

		// prepare typename to match
		convertedTypeNameToMatch = typeNameToMatch.strip();
		convertedTypeNameToMatch = convertedTypeNameToMatch.toLowerCase();
		convertedTypeNameToMatch = prepareWordForMatching(convertedTypeNameToMatch);

		if (this.getCode() != null) {
			convertedTypeNameTax = this.getCode();
			convertedTypeNameTax = convertedTypeNameTax.strip();
			convertedTypeNameTax = convertedTypeNameTax.toLowerCase();
			if (convertedTypeNameTax.equals(convertedTypeNameToMatch)) {
				isMatch = true;
				// PlanSpaceLogger.getInstance()
				// .log("[INFO] succes direct match on synonym : " + convertedTypeNameToMatch);
			} else {
				if (this.getSynonyms() != null) {
					for (i = 0; i < this.getSynonyms().size(); i++) {
						convertedTypeNameTax = this.getSynonyms().get(i).toLowerCase().strip();
						if (convertedTypeNameTax.equals(convertedTypeNameToMatch)) {
							// PlanSpaceLogger.getInstance()
							// .log("[INFO] succes matching on synonym : " + convertedTypeNameToMatch);
							isMatch = true;
							break;
						}
					}
				}
			}

		} else {
			if (this.get_id() == null) {
				PlanSpaceLogger.getInstance()
						.log("[ERROR] ObjectTypeName should not be null: " + convertedTypeNameToMatch + " id unknown");
			} else {
				PlanSpaceLogger.getInstance().log("[ERROR] ObjectTypeName should not be null: "
						+ convertedTypeNameToMatch + " id " + this.get_id());
			}
		}
		return isMatch;
	}

	private String prepareWordForMatching(String aWordToMatch) {
		String aWordForMatching;
		int lastIndex;

		aWordForMatching = aWordToMatch.strip();
		aWordForMatching.toLowerCase();
		lastIndex = aWordForMatching.length(); // start with 0

		// Skip voltooi tijd
		if (aWordToMatch.startsWith("is ")) {
			aWordForMatching = aWordForMatching.substring(3, lastIndex);
		} else if (aWordToMatch.startsWith("wordt ")) {
			aWordForMatching = aWordForMatching.substring(6, lastIndex);
		} else if (aWordToMatch.startsWith("heeft ")) {
			aWordForMatching = aWordForMatching.substring(6, lastIndex);
		} else if (aWordToMatch.startsWith("werd ")) {
			aWordForMatching = aWordForMatching.substring(5, lastIndex);
		} else if (aWordToMatch.startsWith("had ")) {
			aWordForMatching = aWordForMatching.substring(4, lastIndex);
		} else if (aWordToMatch.startsWith("was ")) {
			aWordForMatching = aWordForMatching.substring(4, lastIndex);
		} else if (aWordToMatch.startsWith("kan ")) {
			aWordForMatching = aWordForMatching.substring(4, lastIndex);
		} else if (aWordToMatch.startsWith("kon ")) {
			aWordForMatching = aWordForMatching.substring(4, lastIndex);
		} else if (aWordToMatch.startsWith("kan worden ")) {
			aWordForMatching = aWordForMatching.substring(11, lastIndex);
		} else if (aWordToMatch.startsWith("kon worden ")) {
			aWordForMatching = aWordForMatching.substring(11, lastIndex);
		}
		return aWordForMatching;
	}

	public ObjectType getObjectType() {
		ObjectType theFullObjectType;
		InterfaceToDomainTypeRepository theIFC;

		theFullObjectType = null;
		theIFC = InterfaceToDomainTypeRepository.getInstance();
		if (theIFC != null) {
			theFullObjectType = theIFC.findObjectTypeByTypename(this.getDomainCode(), this.getCode());
		}
		return theFullObjectType;
	}

	public boolean isHavingChildren() {
		boolean havingChildren;
		
		havingChildren = false;
		if ( this.myChildren != null) {
			if (this.getMyChildren().size() > 0 ) {
				havingChildren = true;
			}
		}
		return havingChildren;
		
	}

}
