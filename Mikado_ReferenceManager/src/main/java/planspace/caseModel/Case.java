package planspace.caseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import planspace.beans.external.CommunicationTypes.ExternalDomainInstance;
import planspace.chefModelTypes.MonitoredConcern;
import planspace.interfaceToCaseManagerRepository.InterfaceToCaseManagerRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;

public class Case {
	protected String _id;
	protected String description;
	protected String domainCode;
	protected String caseTypeID;
	protected String externalCaseID;
	
	transient CaseType myCaseType;

	protected 	List<ManagedObjectForCase> myManagedObjects;
	private List<MonitoredConcern> monitoredConcerns;

	transient protected List<ExternalDomainInstance> myDataObjects;

	public Case() {
		this._id = UUID.randomUUID().toString();
		myDataObjects = new ArrayList<ExternalDomainInstance>();
		myManagedObjects = new ArrayList<ManagedObjectForCase>();
		monitoredConcerns = new ArrayList<MonitoredConcern>();
	}



	@JsonIgnore
	public HorizontalLayout getGuiLabel() {
		Image caseAvatar;
		HorizontalLayout hz;
		hz = new HorizontalLayout();
		caseAvatar = new Image("/icon_CASE.png", "CASE");
		caseAvatar.setHeight("38px");
		caseAvatar.setWidth("38px");
		Label lb = new Label(this.description);
		hz.add(caseAvatar, lb);
		return hz;
	}
	
	
	@JsonIgnore
	public String getTheCaseTypeName() {
		String nm = "?";
		

		if (this.getTheCaseType() != null) {
			nm = this.getTheCaseType().getDescription();
		} 
		return nm;
	}
	
	
	
	@JsonIgnore
	public CaseType getTheCaseType() {
		CaseType ct;
		
		if (this.myCaseType != null) {
		  ct = this.myCaseType;	
		} else
		{
			ct = InterfaceToCaseManagerRepository.getInstance().findCaseTypeByID(this.caseTypeID);
			this.myCaseType = ct;
		}
		return ct;
	}

	public String getCaseTypeID() {
		return caseTypeID;
	}




	public void setCaseTypeID(String caseTypeID) {
		this.caseTypeID = caseTypeID;
	}




	public List<MonitoredConcern> getMonitoredConcerns() {
		return monitoredConcerns;
	}




	public void setMonitoredConcerns(List<MonitoredConcern> monitoredConcerns) {
		this.monitoredConcerns = monitoredConcerns;
	}




	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}


	public String getDescription() {
		return description;
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



	public String getExternalCaseID() {
		return externalCaseID;
	}


	public void setExternalCaseID(String externalCaseID) {
		this.externalCaseID = externalCaseID;
	}


	public List<ManagedObjectForCase> getMyManagedObjects() {
		return myManagedObjects;
	}


	public void setMyManagedObjects(List<ManagedObjectForCase> myManagedObjects) {
		this.myManagedObjects = myManagedObjects;
	}


	public List<ExternalDomainInstance> getMyDataObjects() {
		return myDataObjects;
	}


	public void setMyDataObjects(List<ExternalDomainInstance> myDataObjects) {
		this.myDataObjects = myDataObjects;
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

	public ManagedObjectForCase getMOReferenceForObjectType(String theObjectType) {
		ManagedObjectForCase theMO4case, aMO4case;
		int i;

		theMO4case = null;
		if (myManagedObjects != null) {
			for (i = 0; i < this.myManagedObjects.size(); i++) {
				aMO4case = this.myManagedObjects.get(i);
				if (aMO4case.getObjectTypeName().equals(theObjectType)) {
					theMO4case = aMO4case;
					break;
				}
			}
		}
		return theMO4case;
	}
	
	public void RegisterManagedObjectForCase(String theObjectType, String anMOid) {
		ManagedObjectForCase aMO4case;
		boolean alreadyRegistered;
		
		alreadyRegistered = false;
		aMO4case= this.getMOReferenceForObjectType(theObjectType);
		
		if (aMO4case != null) {
			if (aMO4case.getManagedObjectID().equals(anMOid)) {
				alreadyRegistered = true;
			}
		}
		
		if (!alreadyRegistered) {
			aMO4case = new ManagedObjectForCase();
			aMO4case.setManagedObjectID(anMOid);
			aMO4case.setObjectTypeName(theObjectType);
			this.myManagedObjects.add(aMO4case);
		}
	}




	public void createYourself() {
		InterfaceToCaseManagerRepository theIFC;
		
		theIFC = InterfaceToCaseManagerRepository.getInstance();
		theIFC.createCase(this);
		
	}




	public void updatePropertiesOfYourself() {
		InterfaceToCaseManagerRepository theIFC;
		
		theIFC = InterfaceToCaseManagerRepository.getInstance();
		theIFC.updateCase(this);
		
	}



	public void initializeConcernsForCaseType(CaseType theCaseType) {
		int i;
		
		for( i = 0; i < theCaseType.getMonitoredConcerns().size(); i++) {
			this.monitoredConcerns.add(theCaseType.getMonitoredConcerns().get(i));
		}
		
	}





}
