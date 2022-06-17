package planspace.chefModelTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import application.frontend.WEB_Session;
import planspace.InterfaceToRuleRepository.InterfaceToRuleRepository;
import planspace.compliance.SourceReferenceData;
import planspace.domainRules.RuleTemplates.ChefTypeCondition;
import planspace.export.CSVExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_ChefTypeConditiontype;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_goalArcheType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_monitoringState;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_stateRegime;

public class ChefType {
	private String _id;
	private String name;
	private String description;
	private String domainCode;
	private String questionText;
	private t_chefType type;
	private String themeAsString;
	private t_goalArcheType goalArcheType;
	private List<SourceReferenceData> sourceReferences;
	private t_monitoringState lastState;
	@JsonIgnore
	private transient ChefTypeCondition theRootCondition;

	t_stateRegime stateRegime;

	public ChefType() {

		this._id = UUID.randomUUID().toString();
		goalArcheType = t_goalArcheType.UNKNOWN;
		type = t_chefType.UNKNOWN;
		this.name = "<nieuw beleidsobject>";
		this.description = "";
		this.questionText = "";
		this.themeAsString = "";
		this.sourceReferences = new ArrayList<SourceReferenceData>();
		theRootCondition = new ChefTypeCondition();
		theRootCondition.setConditionType(t_ChefTypeConditiontype.ONE_UNDERLYING);
		theRootCondition.setTheChefTypeObject(this);

	}

	public ChefTypeCondition getTheRootCondition() {
		return theRootCondition;
	}

	public void setTheRootCondition(ChefTypeCondition theRootCondition) {
		this.theRootCondition = theRootCondition;
	}

	static public List<String> getInformalStatesForStateRegime(t_language lang, t_stateRegime aStateRegime) {
		List<String> informalStates;

		informalStates = new ArrayList<String>();
		if (lang.equals(t_language.NL)) {
			if (aStateRegime.equals(t_stateRegime.LEVELED)) {
				informalStates.add("ONBEKEND");
				informalStates.add("NEUTRAAL");
				informalStates.add("LAAG");
				informalStates.add("MEDIUM");
				informalStates.add("HOOG");
				informalStates.add("UNACCEPTABEL");
			} else if (aStateRegime.equals(t_stateRegime.BINARY)) {
				informalStates.add("ONBEKEND");
				informalStates.add("WAAR");
				informalStates.add("ONWAAR");
			} else if (aStateRegime.equals(t_stateRegime.AVAILABLE)) {
				informalStates.add("ONBEKEND");
				informalStates.add("BESCHIKBAAR");
				informalStates.add("NIET BESCHIKBAAR");
			} else if (aStateRegime.equals(t_stateRegime.TASKSTATE)) {
				informalStates.add("ONBEKEND");
				informalStates.add("AANGESTUURD");
				informalStates.add("UITGEVOERD");
				informalStates.add("TE LAAT");
			}
		}
		return informalStates;
	}

	static public t_monitoringState getFormalStateForInformalState(t_language lang, String informalState) {
		t_monitoringState theFormalState;

		theFormalState = t_monitoringState.UNKNOWN;
		if (lang.equals(t_language.NL)) {
			if (informalState.equals("ONBEKEND")) {
				theFormalState = t_monitoringState.UNKNOWN;
			} else if (informalState.equals("NEUTRAAL")) {
				theFormalState = t_monitoringState.NEUTRAL;
			} else if (informalState.equals("LAAG")) {
				theFormalState = t_monitoringState.LOW;
			} else if (informalState.equals("MEDIUM")) {
				theFormalState = t_monitoringState.MEDIUM;
			} else if (informalState.equals("HOOG")) {
				theFormalState = t_monitoringState.HIGH;
			} else if (informalState.equals("UNACCEPTABEL")) {
				theFormalState = t_monitoringState.UNACCEPTABLE;
			} else if (informalState.equals("WAAR")) {
				theFormalState = t_monitoringState.TRUE;
			} else if (informalState.equals("ONWAAR")) {
				theFormalState = t_monitoringState.FALSE;
			} else if (informalState.equals("BESCHIKBAAR")) {
				theFormalState = t_monitoringState.AVAILABLE;
			} else if (informalState.equals("NIET BESCHIKBAAR")) {
				theFormalState = t_monitoringState.UNAVAILABLE;
			} else if (informalState.equals("AANGESTUURD")) {
				theFormalState = t_monitoringState.ASSIGNED;
			} else if (informalState.equals("UITGEVOERD")) {
				theFormalState = t_monitoringState.EXECUTED;
			} else if (informalState.equals("TE LAAT")) {
				theFormalState = t_monitoringState.DUE;
			}
		}
		return theFormalState;
	}

	static public String getInformalStateForFormalState(t_language lang, t_monitoringState formalState) {
		String theInFormalState;

		theInFormalState = "ONBEKEND";
		if (lang.equals(t_language.NL)) {
			if (formalState.equals(t_monitoringState.UNKNOWN)) {
				theInFormalState = "ONBEKEND";
			} else if (formalState.equals(t_monitoringState.NEUTRAL)) {
				theInFormalState = "NEUTRAAL";
			} else if (formalState.equals(t_monitoringState.LOW)) {
				theInFormalState = "LAAG";
			} else if (formalState.equals(t_monitoringState.MEDIUM)) {
				theInFormalState = "MEDIUM";
			} else if (formalState.equals(t_monitoringState.HIGH)) {
				theInFormalState = "HOOG";
			} else if (formalState.equals(t_monitoringState.UNACCEPTABLE)) {
				theInFormalState = "UNACCEPTABEL";
			} else if (formalState.equals(t_monitoringState.TRUE)) {
				theInFormalState = "WAAR";
			} else if (formalState.equals(t_monitoringState.FALSE)) {
				theInFormalState = "ONWAAR";
			} else if (formalState.equals(t_monitoringState.AVAILABLE)) {
				theInFormalState = "BESCHIKBAAR";
			} else if (formalState.equals(t_monitoringState.UNAVAILABLE)) {
				theInFormalState = "NIET BESCHIKBAAR";
			} else if (formalState.equals(t_monitoringState.ASSIGNED)) {
				theInFormalState = "AANGESTUURD";
			} else if (formalState.equals(t_monitoringState.EXECUTED)) {
				theInFormalState = "UITGEVOERD";
			} else if (formalState.equals(t_monitoringState.DUE)) {
				theInFormalState = "TE LAAT";
			}
		}
		return theInFormalState;
	}

	static public List<String> getTypesAsStringList(boolean includeUnknown) {
		List<String> theStringTypeList;
		theStringTypeList = new ArrayList<String>();

		theStringTypeList.add("Doelstelling");
		theStringTypeList.add("Risico");
		theStringTypeList.add("Kans");
		theStringTypeList.add("Feit");

		theStringTypeList.add("Beleidsregel");
		theStringTypeList.add("Norm");
		theStringTypeList.add("Gegeven");
		theStringTypeList.add("Gegevensbron");

		theStringTypeList.add("Gebeurtenis/aanleiding");
		theStringTypeList.add("Maatregel");
		if (includeUnknown) {
			theStringTypeList.add("Te bepalen");
		}
		return theStringTypeList;
	}

	static public List<String> ConvertToStringList(t_language lang, List<t_chefType> aFormalList) {
		List<String> theConvertedList;

		theConvertedList = new ArrayList<String>();
		aFormalList.forEach(aFormal -> {
			theConvertedList.add(ChefType.convertFormalTypeToString(lang, aFormal));

		});

		return theConvertedList;
	}

	static public t_chefType getFormalTypeByString(t_language lang, String aTypeAsString) {
		t_chefType formalType;

		formalType = t_chefType.UNKNOWN;
		if (lang.equals(t_language.NL)) {
			if (aTypeAsString.equals("Doelstelling")) {
				formalType = t_chefType.GOAL;
			} else if (aTypeAsString.equals("Risico")) {
				formalType = t_chefType.RISK;
			} else if (aTypeAsString.equals("Kans")) {
				formalType = t_chefType.OPPORTUNITY;
			} else if (aTypeAsString.equals("Feit")) {
				formalType = t_chefType.FACT;
			} else if (aTypeAsString.equals("Beleidsregel")) {
				formalType = t_chefType.RULE;
			} else if (aTypeAsString.equals("Norm")) {
				formalType = t_chefType.NORM;
			} else if (aTypeAsString.equals("Gegeven")) {
				formalType = t_chefType.DATA;
			} else if (aTypeAsString.equals("Gegevensbron")) {
				formalType = t_chefType.DATASOURCE;
			} else if (aTypeAsString.equals("Gebeurtenis/aanleiding")) {
				formalType = t_chefType.EVENT;
			} else if (aTypeAsString.equals("Maatregel")) {
				formalType = t_chefType.MEASUREMENT;
			} else if (aTypeAsString.equals("Te bepalen")) {
				formalType = t_chefType.UNKNOWN;
			}
		}

		return formalType;
	}

	static public String convertFormalTypeToString(t_language lang, t_chefType formalType) {
		String theInformalType;

		theInformalType = "Te bepalen";
		if (lang.equals(t_language.NL)) {

			if (formalType.equals(t_chefType.EVENT)) {
				theInformalType = "Gebeurtenis/aanleiding";
			} else if (formalType.equals(t_chefType.GOAL)) {
				theInformalType = "Doelstelling";
			} else if (formalType.equals(t_chefType.RISK)) {
				theInformalType = "Risico";
			} else if (formalType.equals(t_chefType.OPPORTUNITY)) {
				theInformalType = "Kans";
			} else if (formalType.equals(t_chefType.MEASUREMENT)) {
				theInformalType = "Maatregel";
			} else if (formalType.equals(t_chefType.FACT)) {
				theInformalType = "Feit";
			} else if (formalType.equals(t_chefType.RULE)) {
				theInformalType = "Beleidsregel";
			} else if (formalType.equals(t_chefType.NORM)) {
				theInformalType = "Norm";
			} else if (formalType.equals(t_chefType.DATA)) {
				theInformalType = "Gegeven";
			} else if (formalType.equals(t_chefType.DATASOURCE)) {
				theInformalType = "Gegevensbron";
			} else if (formalType.equals(t_chefType.UNKNOWN)) {
				theInformalType = "Te bepalen";
			}
		}
		return theInformalType;
	}

	public List<SourceReferenceData> getSourceReferences() {
		return sourceReferences;
	}

	public void setSourceReferences(List<SourceReferenceData> sourceReferences) {
		this.sourceReferences = sourceReferences;
	}

	public void addSourceReference(SourceReferenceData aReference) {
		if (this.notInSourceReference(aReference)) {
			this.sourceReferences.add(aReference);
		}
	}

	private boolean notInSourceReference(SourceReferenceData aReference) {
		boolean notIn;
		int i;

		notIn = true;
		for (i = 0; i < this.sourceReferences.size(); i++) {
			if (sourceReferences.get(i).get_id().equals(aReference.get_id())) {
				notIn = false;
				break;
			}
		}

		return notIn;
	}

	public String get_id() {
		return _id;
	}

	public String getThemeAsString() {
		return themeAsString;
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

	public String getTypeName(t_language lang) {
		String typeName;
		typeName = ChefType.convertFormalTypeToString(lang, this.type);
		return typeName;
	}

	public void setTypeByTypeName(t_language lang, String typeName) {

		if (lang.equals(t_language.NL)) {
			this.type = ChefType.getFormalTypeByString(lang, typeName);
		}

	}

	public String getDescription() {
		if (this.description != null) {
			return description;
		} else {
			return "";
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
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

	public t_chefType getType() {
		return type;
	}

	public void setType(t_chefType type) {
		this.type = type;
	}

	public void storeYourself() {
		InterfaceToDomainTypeRepository theIFC;
		String domainCode;

		domainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
		if (WEB_Session.getInstance().isPersistanceOn()) {

			theIFC = InterfaceToDomainTypeRepository.getInstance();
			if (theIFC != null) {
				theIFC.StoreChefTypeForDomain(domainCode, this);
			}
		}
	}

	public void removeYourself() {
		InterfaceToDomainTypeRepository theIFC;
		String domainCode;

		domainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
		if (WEB_Session.getInstance().isPersistanceOn()) {

			theIFC = InterfaceToDomainTypeRepository.getInstance();
			if (theIFC != null) {
				theIFC.deleteChefTypeByID(domainCode, this.get_id());
			}
		}
	}

	public void setThemeAsString(String s) {
		this.themeAsString = s;

	}

	public t_goalArcheType getGoalArcheType() {
		return goalArcheType;
	}

	public void setGoalArcheType(t_goalArcheType goalArcheType) {
		this.goalArcheType = goalArcheType;
	}

	public void openReferenceDocuments() {
		int i;
		if (this.sourceReferences != null) {

			for (i = 0; i < this.sourceReferences.size(); i++) {
				this.sourceReferences.get(i).openSourceReferenceDocument();
			}
		}

	}

	public void exportReferencesToCSV(CSVExportFile anExport) {
		int i;
		if (this.sourceReferences != null) {

			for (i = 0; i < this.sourceReferences.size(); i++) {
				this.sourceReferences.get(i).exportReferencesToCSV(anExport);
			}
		}

	}

	public String getGoalTypeAsString(t_language lang) {
		String t;

		t = "Onbekend";

		if (lang.equals(t_language.NL)) {
			if (this.getGoalArcheType().equals(t_goalArcheType.STIMULATE)) {
				t = "Stimuleren / vergroten";
			} else if (this.getGoalArcheType().equals(t_goalArcheType.DISCOURAGE)) {
				t = "Ontmoedigen/ reduceren";
			} else if (this.getGoalArcheType().equals(t_goalArcheType.MAINTAIN)) {
				t = "Streven naar / instandhouden";
			} else if (this.getGoalArcheType().equals(t_goalArcheType.FORBID)) {
				t = "Verbieden / straffen";
			} else if (this.getGoalArcheType().equals(t_goalArcheType.REGULATE)) {
				t = "Reguleren / toestemmen";
			}

		}
		return t;
	}

	public t_goalArcheType setGoalTypeAsString(t_language lang, String aTypeName) {
		t_goalArcheType t;

		t = t_goalArcheType.UNKNOWN;
		if (lang.equals(t_language.NL)) {
			if (aTypeName.equals("Stimuleren / vergroten")) {
				t = t_goalArcheType.STIMULATE;
			} else if (aTypeName.equals("Ontmoedigen/ reduceren")) {
				t = t_goalArcheType.DISCOURAGE;
			} else if (aTypeName.equals("Streven naar / instandhouden")) {
				t = t_goalArcheType.MAINTAIN;
			} else if (aTypeName.equals("Verbieden / straffen")) {

				t = t_goalArcheType.FORBID;
			} else if (aTypeName.equals("Reguleren / toestemmen")) {
				t = t_goalArcheType.REGULATE;
			}

		}
		this.setGoalArcheType(t);
		return t;
	}

	public boolean hasSourceReference() {
		boolean hasRef;
		hasRef = false;
		if (this.getSourceReferences() != null) {
			if (this.getSourceReferences().size() > 0) {
				hasRef = true;
			}
		}
		return hasRef;
	}

	public void setTypeByString(t_language lang, String theInformalTypeName) {

		t_chefType theTypeRecognised;

		theTypeRecognised = ChefType.getFormalTypeByString(lang, theInformalTypeName);
		if (theTypeRecognised != null) {
			this.setType(theTypeRecognised);
		}

	}

	public t_stateRegime getStateRegime() {

		if (this.getType().equals(t_chefType.GOAL)) {
			return t_stateRegime.LEVELED;
		} else if (this.getType().equals(t_chefType.RISK)) {
			return t_stateRegime.LEVELED;
		} else if (this.getType().equals(t_chefType.OPPORTUNITY)) {
			return t_stateRegime.LEVELED;
		} else if (this.getType().equals(t_chefType.FACT)) {
			return t_stateRegime.BINARY;
		} else if (this.getType().equals(t_chefType.DATA)) {
			return t_stateRegime.AVAILABLE;
		} else if (this.getType().equals(t_chefType.NORM)) {
			return t_stateRegime.AVAILABLE;
		} else if (this.getType().equals(t_chefType.EVENT)) {
			return t_stateRegime.AVAILABLE;
		} else if (this.getType().equals(t_chefType.DATASOURCE)) {
			return t_stateRegime.AVAILABLE;
		} else if (this.getType().equals(t_chefType.RULE)) {
			return t_stateRegime.BINARY;
		} else if (this.getType().equals(t_chefType.MEASUREMENT)) {
			return t_stateRegime.TASKSTATE;
		} else if (this.getType().equals(t_chefType.UNKNOWN)) {
			return t_stateRegime.BINARY;

		}
		return null;

	}

	public void setStateRegime(t_stateRegime stateRegime) {
		this.stateRegime = stateRegime;
	}

	@JsonIgnore
	public void loadRootConditionForObject() {
		InterfaceToRuleRepository theIFC;

		List<ChefTypeCondition> theConditionsFound;
		theIFC = InterfaceToRuleRepository.getInstance();
		if (theIFC != null) {
			theConditionsFound = theIFC.findChefTypConditionsForChefObjectID(this.getDomainCode(), this.get_id());
			if (theConditionsFound != null) {
				if (theConditionsFound.size() > 0) {
					this.theRootCondition = theConditionsFound.get(0);
				
					this.theRootCondition.loadRelatedObjects();
				}
			}
		}

	}

	public void saveRootChefCondition() {
		if (this.theRootCondition != null) {
			this.theRootCondition.StoreYourself();
		}
	}

}
