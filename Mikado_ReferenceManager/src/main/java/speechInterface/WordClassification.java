package speechInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import planspace.domainTypes.DataPointType;
import planspace.domainTypes.ObjectType;
import planspace.domainTypes.TaxonomyNode;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_ActionProcessRequirement;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_EntityCategory;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_Sentiment;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_WordGrammarType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_adjectivetype;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_elemExpressionOperator;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_provenType;
import planspace.utils.PlanSpaceLogger;

public class WordClassification {

	public String analyzedWord;
	private String mainKeyWord;
	private String possibleSingularForm;
	public t_EntityCategory entityCategory;
	public t_WordGrammarType theGrammarType;
	public t_adjectivetype adjectiveType;
	public t_Sentiment theSentiment;
	public boolean isUnique;
	private t_provenType proven;
	public t_ActionProcessRequirement actionProcessRequirement;

	public String assumedToHaveValue;
	public String metricUnit;
	public String precisionIndicator;
	public boolean operatorIsNot;
	public t_elemExpressionOperator theOperator;
	private MatchingTaxonomyConcept theMatchingTaxonomyConcept;
	private List<WordClassification> theSpecification;

	public WordClassification() {
		analyzedWord = "";
		theSentiment = t_Sentiment.NEUTRAL;
		theSpecification = null;
		this.entityCategory = t_EntityCategory.UNKNOWN;
		theMatchingTaxonomyConcept = null;
		theOperator = null;
		operatorIsNot = false;
		proven = t_provenType.UNKNOWN;
		adjectiveType = t_adjectivetype.NO_ADJECTIVE;
		actionProcessRequirement = t_ActionProcessRequirement.NONE;
	}

	public WordClassification(String aWord) {
		analyzedWord = aWord;
		theSentiment = t_Sentiment.NEUTRAL;
		theSpecification = null;
		this.entityCategory = t_EntityCategory.UNKNOWN;
		theMatchingTaxonomyConcept = null;
		theOperator = null;
		operatorIsNot = false;
		proven = t_provenType.UNKNOWN;
		adjectiveType = t_adjectivetype.NO_ADJECTIVE;
		actionProcessRequirement = t_ActionProcessRequirement.NONE;
	}

	public MatchingTaxonomyConcept getMatchingTaxonomyNode() {
		return theMatchingTaxonomyConcept;
	}

	public String getPossibleSingularForm() {
		return possibleSingularForm;
	}

	public void setPossibleSingularForm(String possibleSingularForm) {
		this.possibleSingularForm = possibleSingularForm;
	}

	public t_provenType getProven() {
		return proven;
	}

	public void setProven(t_provenType proven) {
		this.proven = proven;
	}

	public String getMainKeyWord() {
		return mainKeyWord;
	}

	public void setMainKeyWord(String mainKeyWord) {
		this.mainKeyWord = mainKeyWord;
	}

	public MatchingTaxonomyConcept getTheMatchingTaxonomyConcept() {
		return theMatchingTaxonomyConcept;
	}

	public void setTheMatchingTaxonomyConcept(MatchingTaxonomyConcept theMatchingTaxonomyConcept) {
		this.theMatchingTaxonomyConcept = theMatchingTaxonomyConcept;
	}

	public t_ActionProcessRequirement getActionProcessRequirement() {
		return actionProcessRequirement;
	}

	public void setActionProcessRequirement(t_ActionProcessRequirement actionProcessRequirement) {
		this.actionProcessRequirement = actionProcessRequirement;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public void setMatchingTaxonomyNode(MatchingTaxonomyConcept matchingTaxonomyNode) {
		this.theMatchingTaxonomyConcept = matchingTaxonomyNode;
	}

	public t_Sentiment getTheSentiment() {
		return theSentiment;
	}

	public String getAssumedToHaveValue() {
		return assumedToHaveValue;
	}

	public String getPrecisionIndicator() {
		return precisionIndicator;
	}

	public void setPrecisionIndicator(String precisionIndicator) {
		this.precisionIndicator = precisionIndicator;
	}

	public t_adjectivetype getAdjectiveType() {
		return adjectiveType;
	}

	public void setAssumedToHaveValue(String assumedToHaveValue) {
		this.assumedToHaveValue = assumedToHaveValue;
	}

	public void setTheSentiment(t_Sentiment theSentiment) {
		this.theSentiment = theSentiment;
	}

	public t_WordGrammarType getTheGrammarType() {
		return theGrammarType;
	}

	public void setTheGrammarType(t_WordGrammarType theGrammarType) {
		this.theGrammarType = theGrammarType;
	}

	public String getAnalyzedWord() {
		return analyzedWord;
	}

	public String getMetricUnit() {
		return metricUnit;
	}

	public t_elemExpressionOperator getTheOperator() {
		return theOperator;
	}

	public void setTheOperator(t_elemExpressionOperator theOperator) {
		this.theOperator = theOperator;
	}

	public boolean isOperatorIsNot() {
		return operatorIsNot;
	}

	public void setOperatorIsNot(boolean operatorIsNot) {
		this.operatorIsNot = operatorIsNot;
	}

	public void setMetricUnit(String metricUnit) {
		this.metricUnit = metricUnit;
	}

	public void setAnalyzedWord(String analyzedWord) {
		theMatchingTaxonomyConcept = null;
		this.analyzedWord = analyzedWord;
	}

	public t_EntityCategory getEntityCategory() {
		return entityCategory;
	}

	public void setEntityCategory(t_EntityCategory entityCategory) {
		this.entityCategory = entityCategory;
	}

	public List<WordClassification> getTheSpecification() {
		return theSpecification;
	}

	public void setTheSpecification(List<WordClassification> theSpecification) {
		this.theSpecification = theSpecification;
	}

	@JsonIgnore
	public ParsedAspectInfo getParsedAspectInfo() {
		ParsedAspectInfo theAspectInfo;
		int i;
		String aSpecText;
		ParsedAspectInfo parsedSub;
		WordClassification anSpec;

		theAspectInfo = new ParsedAspectInfo();

		// specifications (if any)
		aSpecText = "";
		theAspectInfo.theAspectCore = this.analyzedWord.toUpperCase();
		if (this.theSpecification != null) {

			for (i = 0; i < this.theSpecification.size(); i++) {
				anSpec = this.theSpecification.get(i);
				parsedSub = anSpec.getParsedAspectInfo();
				if (i == 0) {
					aSpecText = "[Kenmerken: " + parsedSub.theAspectCore;
					if (parsedSub.theSpecification.equals("") == false) {
						aSpecText = aSpecText + " " + parsedSub.theSpecification;
						if (anSpec.entityCategory != null) {
							if (anSpec.entityCategory.toString().equals("") == false) {
								aSpecText = aSpecText + "(" + anSpec.entityCategory.toString() + ")";
							}
						}

					}

				} else {
					aSpecText = aSpecText + "," + parsedSub.theAspectCore;
					if (parsedSub.theSpecification.equals("") == false) {
						aSpecText = aSpecText + " " + parsedSub.theSpecification;
						if (anSpec.entityCategory != null) {
							if (anSpec.entityCategory.toString().equals("") == false) {
								aSpecText = aSpecText + "(" + anSpec.entityCategory.toString() + ")";
							}
						}

					}
				}

			}
			if (this.theSpecification.size() > 0) {
				// afsluiten
				aSpecText = aSpecText + "]";
				theAspectInfo.theSpecification = aSpecText;
			}
		}
		return theAspectInfo;
	}

	public void addSpecification(WordClassification aSpec) {
		if (aSpec != null) {
			if (this.theSpecification == null) {
				this.theSpecification = new ArrayList<WordClassification>();
			}
			this.theSpecification.add(aSpec);
		}

	}

	public void addMultipleSpecifications(List<WordClassification> propertiesOfThisAspect) {
		int i;

		for (i = 0; i < propertiesOfThisAspect.size(); i++) {
			this.addSpecification(propertiesOfThisAspect.get(i));
		}

	}

	public void setAdjectiveType(t_adjectivetype anAdjectiveType) {
		this.adjectiveType = anAdjectiveType;
		if (anAdjectiveType.equals(t_adjectivetype.NEG_EMOTION)) {
			this.setTheSentiment(t_Sentiment.NEGATIVE);
		} else if (anAdjectiveType.equals(t_adjectivetype.NEG_EMOTION)) {
			this.setTheSentiment(t_Sentiment.POSITIVE);
		}

	}

	public MatchingTaxonomyConcept matchWithTaxonomy(TaxonomyNode theTaxonomy) {
		// match word with taxonomy provided if match than TaxonomyNode is added
		// if no match then null
		String WordToMatch;
		int i;
		WordClassification mySpecification;
		TaxonomyNode TaxonomyNodeFound;

		// top level WC
		if (this.theMatchingTaxonomyConcept == null) {
			WordToMatch = this.getAnalyzedWord();
			if (theTaxonomy != null) {
				// PlanSpaceLogger.getInstance().log("[MATCHING]" + WordToMatch);
				TaxonomyNodeFound = theTaxonomy.matchTypeName(WordToMatch);
				if (TaxonomyNodeFound != null) {
					// indien gevonden, essentie van Taxonomy Concept registreren
					this.theMatchingTaxonomyConcept = new MatchingTaxonomyConcept();
					theMatchingTaxonomyConcept.setTaxonomyID(TaxonomyNodeFound.get_id());
					theMatchingTaxonomyConcept.setDomainCode(TaxonomyNodeFound.getDomainCode());
					theMatchingTaxonomyConcept.setObjectTypeCode(TaxonomyNodeFound.getCode());
					theMatchingTaxonomyConcept.setObjectTypeDescription(TaxonomyNodeFound.getDescription());
					theMatchingTaxonomyConcept.setObjectTypeID(TaxonomyNodeFound.getObjectType().get_id());

				}
			}
			// recurse for each spcification if present
			if (this.theSpecification != null) {
				for (i = 0; i < this.theSpecification.size(); i++) {
					mySpecification = this.theSpecification.get(i);
					mySpecification.matchSpecificationsWithTaxonomy(theTaxonomy, theMatchingTaxonomyConcept);
				}
			}

		}
		return this.theMatchingTaxonomyConcept;
	}

	public MatchingTaxonomyConcept matchSpecificationsWithTaxonomy(TaxonomyNode theTaxonomy,
			MatchingTaxonomyConcept myObjectMatchingConcept) {
		// match word with taxonomy provided if match than TaxonomyNode is added
		// if no match then null
		String WordToMatch;
		int i;
		WordClassification mySpecification;
		TaxonomyNode TaxonomyNodeFound;
		ObjectType theObjectTypeOfMyParent;
		InterfaceToDomainTypeRepository theDomainIFC;
		boolean matching;
		DataPointType aDataPointTypeOfMyParent;

		// option 1, find matching datatype
		matching = false;
		if (myObjectMatchingConcept != null) {
			// there is a WordClassification for which I am the specification and
			// this WordClassification could be matched. Then first I try to match with it's
			// dataPointTypes
			theDomainIFC = InterfaceToDomainTypeRepository.getInstance();
			if (theDomainIFC != null) {
				theObjectTypeOfMyParent = theDomainIFC.findObjectTypeByTypename(theTaxonomy.getDomainCode(),
						myObjectMatchingConcept.getObjectTypeCode());
				if (theObjectTypeOfMyParent != null) {
					for (i = 0; i < theObjectTypeOfMyParent.getDataPointTypes().size(); i++) {

						aDataPointTypeOfMyParent = theObjectTypeOfMyParent.getDataPointTypes().get(i);
						if (aDataPointTypeOfMyParent != null) {
							if (aDataPointTypeOfMyParent.isMatching(this.getAnalyzedWord())) {
								matching = true;
								this.theMatchingTaxonomyConcept = new MatchingTaxonomyConcept();

								theMatchingTaxonomyConcept.setTaxonomyID(myObjectMatchingConcept.getTaxonomyID());
								theMatchingTaxonomyConcept.setDomainCode(myObjectMatchingConcept.getDomainCode());
								theMatchingTaxonomyConcept
										.setObjectTypeCode(myObjectMatchingConcept.getObjectTypeCode());
								theMatchingTaxonomyConcept
										.setObjectTypeDescription(myObjectMatchingConcept.getObjectTypeDescription());
								theMatchingTaxonomyConcept.setObjectTypeID(myObjectMatchingConcept.getObjectTypeID());
								theMatchingTaxonomyConcept.setDataTypeID(aDataPointTypeOfMyParent.get_id());
								theMatchingTaxonomyConcept.setDataTypeCode(aDataPointTypeOfMyParent.getTypeName());
								theMatchingTaxonomyConcept
										.setDataTypeDescription(aDataPointTypeOfMyParent.getDescription());
							}

						}

					}

				} else {
					PlanSpaceLogger.getInstance()
							.log_ConfigIO("[ERROR DOMAIN API] Expected ObjectType to be configured "
									+ myObjectMatchingConcept.getObjectTypeCode());
				}
			} else {
				PlanSpaceLogger.getInstance()
						.log_ConfigIO("[ERROR DOMAIN API] Not available while matching specifications of concepts ");
			}

		}

		if (matching == false) {
			// option 2, no data point types matching, check for individual concepts
			if (this.theMatchingTaxonomyConcept == null) {
				WordToMatch = this.getAnalyzedWord();
				if (theTaxonomy != null) {
					// PlanSpaceLogger.getInstance().log("[MATCHING]" + WordToMatch);
					TaxonomyNodeFound = theTaxonomy.matchTypeName(WordToMatch);
					if (TaxonomyNodeFound != null) {
						// indien gevonden, essentie van Taxonomy Concept registreren
						this.theMatchingTaxonomyConcept = new MatchingTaxonomyConcept();
						theMatchingTaxonomyConcept.setTaxonomyID(TaxonomyNodeFound.get_id());
						theMatchingTaxonomyConcept.setDomainCode(TaxonomyNodeFound.getDomainCode());
						theMatchingTaxonomyConcept.setObjectTypeCode(TaxonomyNodeFound.getCode());
						theMatchingTaxonomyConcept.setObjectTypeDescription(TaxonomyNodeFound.getDescription());
						theMatchingTaxonomyConcept.setObjectTypeID(TaxonomyNodeFound.getObjectType().get_id());
					}
				}

			}
		}

		// recurse for each spcification if present
		if (this.theSpecification != null) {
			for (i = 0; i < this.theSpecification.size(); i++) {
				mySpecification = this.theSpecification.get(i);
				mySpecification.matchSpecificationsWithTaxonomy(theTaxonomy, theMatchingTaxonomyConcept);
			}
		}

		return this.theMatchingTaxonomyConcept;
	}

	public WordClassification matchDatapointType_by_name(DataPointType aDPT) {
		WordClassification theMatchedSpecification, aSpec;
		int i;
		boolean match;

		// Matching datapointType by name
		theMatchedSpecification = null;

		if (aDPT != null) {

			if (this.theSpecification != null) {

				for (i = 0; i < this.theSpecification.size(); i++) {

					aSpec = this.theSpecification.get(i);
					match = aDPT.isMatching(aSpec.analyzedWord);
					if (match) {

						theMatchedSpecification = aSpec;
						break;
					}
				}
			}
		}

		return theMatchedSpecification;

	}

	public void setUnit(String aMetricUnitAsString) {
		this.metricUnit = aMetricUnitAsString;

	}

	@JsonIgnore
	public List<String> getYourWords() {
		// Get all relevant words being parsed in the analysisResult
		List<String> myWords;
		myWords = new ArrayList<String>();
		String aWord, analysableWord;

		if (this.analyzedWord != null) {
			// convert each tag to a token
			StringTokenizer st = new StringTokenizer(this.analyzedWord, " ");

			while (st.hasMoreTokens()) {
				aWord = (String) st.nextToken();

				analysableWord = aWord.toLowerCase();
				analysableWord = analysableWord.strip();
				PlanSpaceLogger.getInstance().log_LUI("[FOR irrelevant] " + analysableWord);

				myWords.add(analysableWord);
				PlanSpaceLogger.getInstance().log_LUI("[NOT IRRELEVANT] " + analysableWord);

			}

		}
		return myWords;
	}

	public t_EntityCategory i_am_Referening_to_earlier_aspects() {

		// determines wether or not this wordclassification is refering to concepts in
		// the condition
		// if so the condition aspect will be determined and returned

		WordClassification aSpec;
		int i;

		t_EntityCategory theReferenceCategory;

		theReferenceCategory = t_EntityCategory.UNKNOWN;

		if (this.entityCategory.equals(t_EntityCategory.ACTOR_REFERENCE)
				|| this.entityCategory.equals(t_EntityCategory.EVENT_REFERENCE)
				|| this.entityCategory.equals(t_EntityCategory.LOCATION_REFERENCE)
				|| this.entityCategory.equals(t_EntityCategory.MOMENT_REFERENCE)
				|| this.entityCategory.equals(t_EntityCategory.REASON_REFERENCE)) {
			theReferenceCategory = this.entityCategory;
		}

		if (theReferenceCategory.equals(t_EntityCategory.UNKNOWN)) {
			// reference can be in specifications

			if (this.theSpecification != null) {

				for (i = 0; i < this.theSpecification.size(); i++) {

					aSpec = this.theSpecification.get(i);
					theReferenceCategory = aSpec.i_am_Referening_to_earlier_aspects();
					if (!theReferenceCategory.equals(t_EntityCategory.UNKNOWN)) {
						break;
					}
				}
			}
		}

		return theReferenceCategory;
	}

	public List<WordClassification> getRecognisedConcepts() {
		List<WordClassification> recognisedConcepts, recognisedConceptsOfSpec;
		WordClassification aRecognisedConcept;
		WordClassification aSpec;
		int i;
		
		recognisedConcepts = new ArrayList<WordClassification>();
		if (this.getTheMatchingTaxonomyConcept() != null) {
			recognisedConcepts.add(this);
		}
		if (this.theSpecification != null) {

			for (i = 0; i < this.theSpecification.size(); i++) {

				aSpec = this.theSpecification.get(i);
				recognisedConceptsOfSpec = aSpec.getRecognisedConcepts();
				if (recognisedConceptsOfSpec != null)  {
					if (recognisedConceptsOfSpec.size() > 0) {
						recognisedConcepts.addAll(recognisedConceptsOfSpec);
					}
				}
			}
		}
		return recognisedConcepts;
	}

}
