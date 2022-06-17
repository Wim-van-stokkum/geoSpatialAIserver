package planspace.domainRules.RuleTemplates;


import java.util.ArrayList;
import java.util.List;

import planspace.InterfaceToRuleRepository.InterfaceToRuleRepository;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_agentObjectiveType;

/* Planspace heeft een multi-agent architectuur
 * Een of meerdere agents luisteren naar gestandaardiseerde berichten waarin gebeurtenissen worden gecommuniceerd
 * 
 * Afhankelijk van inhoud bericht besluiten ze of een gebeurtenis al dan niet interessant is voor hun doel
 * Mocht het interessant zijn dan kunnen ze een bericht interpreteren en een inkomend event classificeren als een 
 * geinterpreteerd event.  Regenen in Almere  wordt dan bijvoorbeeld geinterpreteerd als Wateroverlast in flevoland
 * 
 * 
 * Voor het interpreteren van events worden regels gebruikt. 
 * Deze class definieert zo'n regel. Een regel heeft een 
 * - Type (waarvoor wordt hij gebruikt): 
 * 		voor interpreteren externe events van type X
 * 		voor interpreteren interne events van type Y
 * 		voor interpreteren resultaat na aanroep Brein
 * 		
 *  Deze class Configureert Event Interpretatie regels voor het bepalen of een event relevant is voor een 
 *  agent objective type
 *  
 *  Het is een specifieke invulling van een generieke Event Interpretatie rule
 */
public class EventRelevantObjectiveRule extends AbstractEventInterpretationRule {
	
	List<t_agentObjectiveType> usableForObjectives = null;
	


	public EventRelevantObjectiveRule() {
		super();
		this.ruleType = "EventRelevantForObjectiveRule";
		usableForObjectives = new ArrayList<t_agentObjectiveType>();
	}
	
	public void addUsableForObjective(t_agentObjectiveType anObjectiveCodeType) {
		this.usableForObjectives.add(anObjectiveCodeType);
	}

	
	@Override
	public void storeYourSelf() {
		
		//* Dit type regel wordt in de configuratie database opgeslagen in zijn eigen collection

		InterfaceToRuleRepository theRuleIFC;
		theRuleIFC = InterfaceToRuleRepository.getInstance();

		theRuleIFC.storeRuleInRepository(this);
	}
}
