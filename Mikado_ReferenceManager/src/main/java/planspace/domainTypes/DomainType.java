package planspace.domainTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import planspace.agentConfig.AgentConfiguration;
import planspace.caseModel.CaseType;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;

public class DomainType {
	
	/* =========================================================================================================================
	Dit project bevat een applicatie (geen agent). 
	Beoogd gebruiker rol is een Domeinbeheerder
	
	Doel: 
	Als DomeinBeheerder wil ik een applicatie hebben waarmee ik de concepten kan
	modelleren die in mijn domein een belangrijke rol spelen.
	
	Als DomeinBeheerder wil ik voor mijn toepassingsdomeinen een Domein kunnen aanmaken, 
	zodat ik alle concepten definities en regels kan afbakenen binnen de context van dit domein
    Objecten met hun eigenschappen hebben betekenis binnen een bepaald domein. 
    Ook kunnen per domein andere UserRoles betrokken zijn als directe gebruiker van de toepassingen.

	 
	Toelichting: 
	PlanSpace moet generiek van aard zijn. 
	Het moet kunnen werken in ongeacht welk domein. 
	Per Domein spelen andere concepten (en eigenschappen) een rol. 
	Daarom is er een beheer applicatie nodig waarmee deze domeinconcepten kunnen worden gemodelleerd 
	door een domeinbeheerder zonder tussenkomst van een programmeur. 
	
	Deze class:
	Deze class representeerd een domein.
	Met een domein maak ik een afgebakende (namespace) waarbinnen definities gelden.
	  */
	
	
	/* Domeinen zijn unieke namespaces.
	 * Naast een technische identificatie (_id) kent een domein een voor de beheerder betekenisvolle naam en 
	 * omschrijving
	 * 
	 * De domeincode is een leesbare alternatieve unieke Identificatie. 
	 * De leesbaarheid maakt debuggen en zoeken door mensen makkelijker dan gebruik van nietszeggende _id.
	 */

	String _id;
	String name;
	String domainCode;
	String description;
	List <AgentConfiguration> myAgentConfigurations;
	List <String> themes;
	List <CaseType> caseTypes;


	public DomainType() {

		themes =  new ArrayList<String>();
		caseTypes = new ArrayList <CaseType>();
		

	}
	
		
	
	public List<CaseType> getCaseTypes() {
		return caseTypes;
	}





	public void setCaseTypes(List<CaseType> caseTypes) {
		this.caseTypes = caseTypes;
	}

	public void addCaseType(CaseType  aCaseType) {
		if (!this.caseTypes.contains(aCaseType)) {
			this.caseTypes.add(aCaseType);
		}
	}
	
	public void removeCaseType(CaseType  aCaseType) {
		if (this.caseTypes.contains(aCaseType)) {
			this.caseTypes.remove(aCaseType);
		}
	}
		

	public void updateYourself() {
		InterfaceToDomainTypeRepository theIFC;
		theIFC = InterfaceToDomainTypeRepository.getInstance();
		theIFC.updateDomainType(this);
		//to do update domain
	}


	public List<String> getThemes() {
		return themes;
	}




	public void setThemes(List<String> themes) {
		this.themes = themes;
	}




	public DomainType(String aCode, String aName, String aDescription) {
		//Constructor hier wordt een naam aangemaakt met zijn kenmerken.
		
		this._id = UUID.randomUUID().toString();
		this.name = aName;
		this.domainCode = aCode;
		this.description = aDescription;
		this.myAgentConfigurations = new ArrayList<AgentConfiguration>();

	}
	
	// De getters en setters. 

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


	public String getDomainCode() {
		return domainCode;
		
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getDisplayName(Object o) {
		DomainType convertedObjectToThis;
		
		convertedObjectToThis = (DomainType) o;
		
		return convertedObjectToThis.getName();
	}

	


	public void addAgentConfiguration(AgentConfiguration anAgentConfig) {
		
		this.myAgentConfigurations.add(anAgentConfig);
		
	}

	public AgentConfiguration getAgentConfigurationByTypeCode(String agentConfigTypeCode) {
		AgentConfiguration foundAgentConfiguration, aAgentConfiguration;
		int i;
		
		foundAgentConfiguration = null;
		for (i = 0; i < this.myAgentConfigurations.size(); i++) {
			aAgentConfiguration = this.myAgentConfigurations.get(i);
			if (aAgentConfiguration.getAgentConfigurationTypeCode().equals(agentConfigTypeCode)) {
				foundAgentConfiguration = aAgentConfiguration;
				break;
			}
		}
				
		return foundAgentConfiguration;
	}

}
