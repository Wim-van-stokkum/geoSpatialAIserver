package planspace.chefModelTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.google.gson.Gson;

import application.frontend.WEB_Session;
import planspace.export.JsonExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.jsonExport.JsonPolicyItems;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_impact;

public class ChefTreeContext {
	private String _id;
	private String parentID;
	private String themeAsString;
	private String relatedChefObjectID;
	private String sourceReferenceID;
	private List<String> directChildTreeContexts;
	private String domainCode;
	private t_impact impact;

	public ChefTreeContext() {
		this._id = UUID.randomUUID().toString();
		directChildTreeContexts = new ArrayList<String>();

	}

	public String get_id() {
		return _id;
	}

	public t_impact getImpact() {
		return impact;
	}

	public void setImpact(t_impact impact) {
		this.impact = impact;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getSourceReferenceID() {
		return sourceReferenceID;
	}

	public void setSourceReferenceID(String sourceReferenceID) {
		this.sourceReferenceID = sourceReferenceID;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getThemeAsString() {
		return themeAsString;
	}

	public void setThemeAsString(String themeAsString) {
		this.themeAsString = themeAsString;
	}

	public String getRelatedChefObjectID() {
		return relatedChefObjectID;
	}

	public void setRelatedChefObjectID(String relatedChefObjectID) {
		this.relatedChefObjectID = relatedChefObjectID;
	}

	public List<String> getDirectChildTreeContexts() {
		return directChildTreeContexts;
	}

	public void setDirectChildTreeContexts(List<String> directChildTreeContexts) {
		this.directChildTreeContexts = directChildTreeContexts;
	}

	public void addDirectChildTreeContext(String directChildTreeContext) {

		if (!this.directChildTreeContexts.contains(directChildTreeContext)) {
			this.directChildTreeContexts.add(directChildTreeContext);

		}
	}

	public void removeDirectChildTreeContext(String directChildTreeContext) {
		if (this.directChildTreeContexts.contains(directChildTreeContext)) {
			this.directChildTreeContexts.remove(directChildTreeContext);
		}
	}

	public void clearDirectChildTreeContext() {
		this.directChildTreeContexts.clear();
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

	public void storeYourself() {
		InterfaceToDomainTypeRepository theIFC;

		if (WEB_Session.getInstance().isPersistanceOn()) {

			theIFC = InterfaceToDomainTypeRepository.getInstance();
			if (theIFC != null) {
				theIFC.StoreChefTreeContextForDomain(WEB_Session.getInstance().getTheDomain().getDomainCode(), this);
			}
		}
	}

	public void setParentID(String get_id) {
		this.parentID = get_id;

	}

	public String getParentID() {
		return this.parentID;

	}

	public ChefType getChefObjectOfParent() {
		InterfaceToDomainTypeRepository theIFC;
		ChefType theParentObject;
		ChefTreeContext theParentTreeContext;

		theParentObject = null;
		if (this.parentID != null) {
			if (!this.parentID.isBlank() && !this.parentID.isEmpty() && !this.parentID.equals("REMOVED")) {
				theIFC = InterfaceToDomainTypeRepository.getInstance();
				theParentTreeContext = theIFC.findChefTreeContextByID(
						WEB_Session.getInstance().getTheDomain().getDomainCode(), this.parentID);
				if (theParentTreeContext != null) {
					if (theParentTreeContext.getRelatedChefObjectID() != null) {
						if (!theParentTreeContext.getRelatedChefObjectID().isBlank()
								&& !theParentTreeContext.getRelatedChefObjectID().isEmpty()
								&& !theParentTreeContext.getRelatedChefObjectID().equals("REMOVED")) {
							theParentObject = theIFC.findChefTypeByID(
									WEB_Session.getInstance().getTheDomain().getDomainCode(),
									theParentTreeContext.getRelatedChefObjectID());
						}
					}
				}
			}
		}
		return theParentObject;
	}

	public List<ChefType> getChefObjectOfDirectChildren() {
		InterfaceToDomainTypeRepository theIFC;
		ChefType aChildObject;
		List<ChefType> theDirectChildren;
		String aChildTreeContextID;
		ChefTreeContext aChildTreeContext;
		int i;

		theDirectChildren = new ArrayList<ChefType>();
		if (this.directChildTreeContexts != null) {
			for (i = 0; i < this.directChildTreeContexts.size(); i++) {
				aChildTreeContextID = directChildTreeContexts.get(i);
				if (aChildTreeContextID != null) {
					if (!aChildTreeContextID.isBlank() && !aChildTreeContextID.isEmpty() && !aChildTreeContextID.equals("REMOVED")) {
						theIFC = InterfaceToDomainTypeRepository.getInstance();
						aChildTreeContext = theIFC.findChefTreeContextByID(
								WEB_Session.getInstance().getTheDomain().getDomainCode(), aChildTreeContextID);
						if (aChildTreeContext != null) {
							if (aChildTreeContext.getRelatedChefObjectID() != null) {
								if (!aChildTreeContext.getRelatedChefObjectID().isBlank()
										&& !aChildTreeContext.getRelatedChefObjectID().isEmpty()
										&& !aChildTreeContext.getRelatedChefObjectID().equals("REMOVED")) {
									aChildObject = theIFC.findChefTypeByID(
											WEB_Session.getInstance().getTheDomain().getDomainCode(),
											aChildTreeContext.getRelatedChefObjectID());
									if (!theDirectChildren.contains(aChildObject)) {
										theDirectChildren.add(aChildObject);
									}
								}
							}
						}
					}
				}
			}
		}
		return theDirectChildren;
	}

	public void removeYourself() {
		InterfaceToDomainTypeRepository theIFC;
		String domainCode;

		domainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
		if (WEB_Session.getInstance().isPersistanceOn()) {

			theIFC = InterfaceToDomainTypeRepository.getInstance();
			if (theIFC != null) {
				theIFC.deleteTreeContextByID(domainCode, this.get_id());
			}
		}
	}

	public void exportChefItemsToJson(JsonExportFile anExport, int level, boolean recursive,
			JsonPolicyItems thePolicyItems) {
		    this.getChefObjectOfDirectChildren().forEach(aDirectChild-> {
		    	thePolicyItems.addPolicyItemByChefType(aDirectChild);
		    });
		
	}
}
