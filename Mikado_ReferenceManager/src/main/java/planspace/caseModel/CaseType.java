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

import planspace.chefModelTypes.ChefType;
import planspace.chefModelTypes.MonitoredConcern;
import planspace.chefModelTypes.MonitoringRegime;
import planspace.chefModelTypes.MonitoringRegime.t_monitoringFrequency;
import planspace.context.ContextDefinition;
import planspace.domainRules.RuleTemplates.EventContextCondition;
import planspace.interfaceToCaseManagerRepository.InterfaceToCaseManagerRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_caseArcheType;

public class CaseType {
	private String _id;
	private String description;
	private String domainCode;
	private String caseTypeCode;
	private t_caseArcheType caseArcheType;
	private List<MonitoredConcern> monitoredConcerns;
	private List<ManagedObjectForCase> managedObjects;
	private ContextDefinition myContext;

	public CaseType() {
		this._id = UUID.randomUUID().toString();
		monitoredConcerns = new ArrayList<MonitoredConcern>();
		managedObjects = new ArrayList<ManagedObjectForCase>();
		caseArcheType = t_caseArcheType.REQUEST;
	}

	
	@JsonIgnore
	public HorizontalLayout getGuiLabel() {
		Image caseTypeAvatar;
		HorizontalLayout hz;
		hz = new HorizontalLayout();
		caseTypeAvatar = new Image("/icon_CASETYPE.png", "CASETYPE");
		caseTypeAvatar.setHeight("38px");
		caseTypeAvatar.setWidth("38px");
		Label lb = new Label(this.description);
		hz.add(caseTypeAvatar, lb);
	
		return hz;
	}
	
	public t_caseArcheType getCaseArcheType() {
		return caseArcheType;
	}



	




	public ContextDefinition getMyContext() {
		return myContext;
	}


	public void setMyContext(ContextDefinition myContext) {
		this.myContext = myContext;
	}


	public void setCaseArcheType(t_caseArcheType caseArcheType) {
		this.caseArcheType = caseArcheType;
	}



	public String get_id() {
		return _id;
	}

	public List<MonitoredConcern> getMonitoredConcerns() {
		return monitoredConcerns;
	}

	public void setMonitoredConcerns(List<MonitoredConcern> monitoredConcerns) {
		this.monitoredConcerns = monitoredConcerns;
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

	public String getCaseTypeCode() {
		return caseTypeCode;
	}

	public void setCaseTypeCode(String caseTypeCode) {
		this.caseTypeCode = caseTypeCode;
	}

	public List<MonitoredConcern> getMonitoredAspects() {
		return monitoredConcerns;
	}

	public void setMonitoredAspects(List<MonitoredConcern> monitoredAspects) {
		this.monitoredConcerns = monitoredAspects;
	}

	public List<ManagedObjectForCase> getManagedObjects() {
		return managedObjects;
	}

	public void setManagedObjects(List<ManagedObjectForCase> managedObjects) {
		this.managedObjects = managedObjects;
	}

	@JsonIgnore
	public boolean updateYourself() {
		boolean succes;
		InterfaceToCaseManagerRepository theIFC;
		
		theIFC = InterfaceToCaseManagerRepository.getInstance();
		return theIFC.updateCaseType(this);
	
	}
	
	@JsonIgnore
	public boolean addYourself() {
		boolean succes;
		InterfaceToCaseManagerRepository theIFC;
		
		theIFC = InterfaceToCaseManagerRepository.getInstance();
		return theIFC.getInstance().addCaseType(this);
	
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

	@JsonIgnore
	public void deleteYourself() {
		boolean succes;
		InterfaceToCaseManagerRepository theIFC;
		
		theIFC = InterfaceToCaseManagerRepository.getInstance();
		 theIFC.deleteCaseType(this);
	
		
	}

	public void AddChefTypeForMonitoring(ChefType theChefType) {
		MonitoredConcern newConcern;
		MonitoringRegime regimeForNew;
		
		
		if (theChefType != null) {
			newConcern = new MonitoredConcern();
			regimeForNew = new MonitoringRegime();
			regimeForNew.setMonitoringFrequency(t_monitoringFrequency.ON_REQUEST);
			
			newConcern.setChefTypeID(theChefType.get_id());
			newConcern.setTheRegime(regimeForNew);
			this.monitoredConcerns.add(newConcern);
		}
		
	}

	public void addMonitoringConcern(MonitoredConcern newConcern) {
		if(!this.monitoredConcerns.contains(newConcern)) {
			this.monitoredConcerns.add(newConcern);
		}
		
	}


	public void setContextDefinition(ContextDefinition anECC) {
		this.myContext = anECC;
		
	}




	

}
