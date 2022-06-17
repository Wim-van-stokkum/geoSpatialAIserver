package planspace.agentConfig;

import java.util.ArrayList;
import java.util.List;

import planspace.domainRules.RuleTemplates.EventContextCondition;

public class AgentObjectiveSpecialism {
	/*
	 * Een agent kan gespecialiseerd of gefocussed zijn. Bijvoorbeeld niet alle
	 * mutaties van huizen in de stad behoren tot zijn interesse of specialisme,
	 * maar slechts de Kastelen of Kantoren in die stad.
	 * 
	 * Per Objective kunnen daarom deze specialismen AgentObjectiveSpecialim worden
	 * ingesteld. Na instellen zullen bij het evalueren van gebeurtenissen de
	 * gebeurtenissen buiten dit specialisme niet worden opgepakt door de agent.
	 * 
	 * Dit wordt bewerkstelligd door de EventRelevantObjctivesRules niet alleen te
	 * filteren op objective, maar tevens te laten filteren op het specialisme
	 * 
	 * Een specialisme wordt gevormd door 0 tot meerdere EventContextConditions Deze
	 * filteren op basis van aspecten de Events
	 */

	List<EventContextCondition> myFocus = null;

	public AgentObjectiveSpecialism() {
		// constructor

		myFocus = new ArrayList<EventContextCondition>();

	}
	
	// Getters and setters

	public List<EventContextCondition> getMyObjectiveSpecialisms() {
		return myFocus;
	}

	public void setMyObjectiveSpecialisms(List<EventContextCondition> allMyFocus) {
		this.myFocus = allMyFocus;
	}
	
	public void addFocus(EventContextCondition anFocus) {
		this.myFocus.add(anFocus);
	}
	
	public boolean isMyFocus(String inDomain, EventContextCondition anOtherEventContextCondition) {
		/* This method wil check if the anOtherEventContextCondition in arguments
		 * matches semanticly with one of the Focusses set for this specialisme
		 */
		
		boolean inFocus, allInFocus;
		int i;
		EventContextCondition aFocus;
		
		allInFocus = true;
		
		for (i = 0 ; i < myFocus.size(); i++) {
			aFocus = myFocus.get(i);
			inFocus = aFocus.isMatchingWithOtherEventCondition(inDomain, anOtherEventContextCondition);
			if (inFocus == false) {
				allInFocus = false;
			}
		}
		return allInFocus;
	}
	
	
	
	
	

}
