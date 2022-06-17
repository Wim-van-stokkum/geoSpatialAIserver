package planspace.agentConfig;

import java.util.ArrayList;
import java.util.List;

import planspace.agentinteraction.InternalEvent;

/* Planspace heeft een multi-agent architectuur
 * Een of meerdere agents luisteren naar gestandaardiseerde berichten waarin gebeurtenissen worden gecommuniceerd
 * 
 * Afhankelijk van inhoud bericht besluiten ze of een gebeurtenis al dan niet interessant is voor hun doel
 * Mocht het interessant zijn dan kunnen ze een bericht interpreteren en een inkomend event classificeren als een 
 * geinterpreteerd event.  Regenen in Almere  wordt dan bijvoorbeeld geinterpreteerd als Wateroverlast in flevoland
 * 
 * geinterpreteerde relevante berichten worden vervolgens intern verwerkt (in het brein). 
 * Het brein kan geimplementeerd zijn als:
 * - Geen inhoudelijke kennis. De interpretatie (classificatie) wordt dan 1-1 doorgegeven
 * - Inhoudelijke kennis: een externe rule engine/rekenmodule/ webservice / applicatie etc wordt aangeroepen met als 
 *    data de data die in de gebeurtenis is ontstaan. Hier kan uitkomen een resultaat, geen resultaat. tevens kan 
 *    hier al dan niet de behoefte ontstaan om meer aanvullende informatie. 
 *    
 *  Afhankelijk van inregeling kan /moet een menselijke gebruiker ingeschakeld worden. Deze gebruiker is de
 *  "eigenaar van de agent" een agent is een geautomatiseerde assistent van een mens. De agent staat in dienst van deze persoon
 *  en de persoon machtigt de agent om al dan niet namens hem op te treden.
 *  
 *  De uitkomst van het brein/ al dan niet aangevuld met de interpretate/opvatting van de "eigenaar" / mens vormen
 *  de gebeurtenissen die de agent vervolgens weer verder communiceert naar andere agenten.
 *  
 *  Er zijn diverse agenten waarvan gedrag afhankelijk is van specialisme, 
 *  echter in de essentie is bovenstaand gedrag identifiek ongeacht welke agentrol er wordt ingevuld.
 *  
 *  Deze InternalAgent class beschrijft het generieke gedrag van een agent
 */

public class InternalAgent {

	/* Er zijn diverse agent rollen binnen planspace */

	public enum t_AgentType {
		IMPACTANALYSISOBSERVER, IMPACTHYPOTHESEPROVER, CASEMANAGER, RISKMANAGER, RISKSTATUSMANAGER, PROOFMANAGER,
		POLICYMANAGER, DEMO_MANAGER
	}

	protected t_AgentType agentType;

	/* Een agent is van een AgentType (rol) en heeft een unieke identificatie */

	String _id;
	String myName;
	String description;

	/* Configuration of internal agent */
	protected AgentCharacteristics myCharacteristics;
	protected AgentConfiguration myAgentConfiguration;
	protected List<AgentObjective> myObjectives;


	
 
	

	public InternalAgent() {
		/*
		 * constructor: een Agent krijgt een unieke ID, wordt gebruikt in de
		 * verantwoording
		 */
		// _id = UUID.randomUUID().toString();
		myObjectives = new ArrayList<AgentObjective>();

	}

	public String getMyID() {
		return _id;
	}

	protected void setMyAgentID(String configuredAgentId) {
		this._id = configuredAgentId;

	}

	/*
	 * Agenten worden genotificeerd als er interne gebeurtenissen ontstaan (door
	 * andere agenten)
	 */
	public void notifyInternalEvent(InternalEvent anInternalEvent) {
		// TODO Auto-generated method stub

	}

	// getter and setters
	public t_AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(t_AgentType agentType) {
		this.agentType = agentType;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String aName) {
		this.myName = aName;
	}

	public AgentCharacteristics getMyCharacteristics() {
		return myCharacteristics;
	}

	public void setMyCharacteristics(AgentCharacteristics myCharacteristics) {
		this.myCharacteristics = myCharacteristics;
	}

	public AgentConfiguration getMyAgentConfiguration() {
		return myAgentConfiguration;
	}

	public void setMyAgentConfiguration(AgentConfiguration myAgentConfiguration) {
		this.myAgentConfiguration = myAgentConfiguration;
	}

	public List<AgentObjective> getMyObjectives() {
		return myObjectives;
	}

	public void setMyObjectives(List<AgentObjective> myObjectives) {
		this.myObjectives = myObjectives;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	
	
	

}
