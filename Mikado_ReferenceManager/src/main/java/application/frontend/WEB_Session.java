package application.frontend;

import java.util.ArrayList;
import java.util.List;

import application.frontend.components.ChefTaxonomyGridEditor;
import application.frontend.components.ChefTypeEditor;
import application.frontend.components.PolicyReferenceWindow;
import planspace.caseModel.Case;
import planspace.caseModel.CaseType;
import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.domainTypes.DomainType;
import planspace.domainTypes.TaxonomyNode;
import planspace.interfaceToCaseManagerRepository.InterfaceToCaseManagerRepository;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.utils.PlanSpaceLogger;

public class WEB_Session {

	private static WEB_Session stdSession;

	private DomainType theDomain;
	
	TaxonomyNode domainTaxonomy = null;
	ChefTaxonomyGridEditor currentTaxGridEditor;

	private List<ChefTaxonomyItem> currentItemsDragged;

	public ChefTaxonomyGridEditor getCurrentTaxGridEditor() {
		return currentTaxGridEditor;
	}

	

	private boolean withKafka;

	

	private boolean persistanceOn;

	private PolicyReferenceWindow currentPolicyReferenceWindow;
	private WEB_ReferenceTab currentComplianceEditor;

	private ChefTypeEditor currentGoalEditor;

	private List<ChefTaxonomyItem> ChefTaxonomyCache;
	

	public static WEB_Session getInstance() {
		/*
		 * Er is maar 1 sessie voorlopig
		 */
		if (stdSession == null) {

			stdSession = new WEB_Session();

		}
		return stdSession;
	}

	public WEB_Session() {
		// constructor
		this.clearSession();
		withKafka = true;
		currentTaxGridEditor = null;
		ChefTaxonomyCache = null;
	}

	public void clearSession() {

		theDomain = null;

	}



	public boolean isWithKafka() {
		return withKafka;
	}

	public void setWithKafka(boolean withKafka) {
		this.withKafka = withKafka;
	}

	

	public TaxonomyNode getDomainTaxonomy() {
		InterfaceToDomainTypeRepository theDomainModelAPI;

		if (domainTaxonomy == null) {

			// load taxonomy for current domain as cache
			theDomainModelAPI = InterfaceToDomainTypeRepository.getInstance();
			domainTaxonomy = theDomainModelAPI.GetTaxonomyForObjectTypeCode(this.theDomain.getDomainCode(), "");

		}

		return domainTaxonomy;
	}

	public void setDomainTaxonomy(TaxonomyNode domainTaxonomy) {
		this.domainTaxonomy = domainTaxonomy;
	}

	public DomainType getTheDomain() {
		return theDomain;
	}

	

	public void setTheDomain(DomainType theDomain) {
		this.theDomain = theDomain;
		PlanSpaceLogger.getInstance().log_Debug("[SELECT DOMAIN]" + theDomain.getDomainCode());
		// reset current cached taxonomy
		this.domainTaxonomy = null;
		this.ChefTaxonomyCache = null;
	}

	public void reloadTaxonomy() {
		this.domainTaxonomy = null;
		this.getDomainTaxonomy();

	}

	public String getLanguage() {

		return "nl";
	}

	

	public boolean isPersistanceOn() {
		return persistanceOn;
	}

	public void setPersistanceOn(boolean persistanceOn) {
		this.persistanceOn = persistanceOn;
	}

	

	public void notifyTaxGridEditor(String anNotification, ChefTaxonomyItem chefTaxonomyItem) {
		if(this.currentTaxGridEditor != null) {
			this.currentTaxGridEditor.handleEvent(anNotification,chefTaxonomyItem );
		}
		
	}
	
	public void setCurrentTaxGridEditor( ChefTaxonomyGridEditor currentTaxGridEditor) {
		this.currentTaxGridEditor = currentTaxGridEditor;
	}


	public void notifyTaxonomyItemsDragged(List<ChefTaxonomyItem> taxItemsBeingDragged) {
		this.currentItemsDragged = taxItemsBeingDragged;

	}

	public List<ChefTaxonomyItem> getCurrentItemsDragged() {
		return currentItemsDragged;
	}


	public void setCurrentItemsDragged(List<ChefTaxonomyItem> currentItemsDragged) {
		this.currentItemsDragged = currentItemsDragged;
	}

	
	public void notifyPolicyReferenceEditor(String anNotification) {
		if(this.currentPolicyReferenceWindow != null) {
			this.currentPolicyReferenceWindow.handleEvent(anNotification );
		}
		
	}

	public void setPolicyReferenceEditor(PolicyReferenceWindow myPolicyReferenceEditor) {
		this.currentPolicyReferenceWindow = myPolicyReferenceEditor;
		
	}

	public void notifyPolicyReferenceEditor(String event, ChefTaxonomyItem chefTaxonomyItem) {
		if(this.currentPolicyReferenceWindow != null) {
			this.currentPolicyReferenceWindow.handleEvent(event, chefTaxonomyItem );
			
		}
		
	}

	

	public WEB_ReferenceTab getCurrentComplianceEditor() {
		return currentComplianceEditor;
	}

	public void setCurrentComplianceEditor(WEB_ReferenceTab currentComplianceEditor) {
		this.currentComplianceEditor = currentComplianceEditor;
	}

	public void setCurrentGoalEditor(ChefTypeEditor aGoalEditor) {
		this.currentGoalEditor = aGoalEditor;
		
	}

	public ChefTypeEditor getCurrentGoalEditor() {
		return currentGoalEditor;
	}

	public void setDomainChefTaxonomy(List<ChefTaxonomyItem> theChefTaxonomyRootItems) {
	   this.ChefTaxonomyCache = theChefTaxonomyRootItems;
		
	}
	
	public List<ChefTaxonomyItem>  getDomainChefTaxonomy() {
		
		 if (this.ChefTaxonomyCache != null) {
			 return  this.ChefTaxonomyCache;
		 } else {
			 return null;
		 }
		
			
	}

	public void clearChefTaxonomyCache() {
		this.ChefTaxonomyCache = null;
	}
	
	
	public  List<CaseType>  loadCaseTypesForDomain() {
		List<CaseType> theCaseTypes;
		InterfaceToCaseManagerRepository theIFC;

		theCaseTypes = new ArrayList<CaseType>();
		if (this.getTheDomain() != null) {

			theIFC = InterfaceToCaseManagerRepository.getInstance();
			theCaseTypes = theIFC.findCaseTypes();
			if (theCaseTypes == null) {
				theCaseTypes = new ArrayList<CaseType>();
			}

		}
		return theCaseTypes;
	}
	
	public  List<Case>  loadCasesForDomain(String caseTypeID) {
		List<Case> theCases;
		InterfaceToCaseManagerRepository theIFC;

		theCases = new ArrayList<Case>();
		if (this.getTheDomain() != null) {

			theIFC = InterfaceToCaseManagerRepository.getInstance();
			theCases = theIFC.findCases(caseTypeID);
			if (theCases == null) {
				theCases = new ArrayList<Case>();
			}

		}
		return theCases;
	}

}
