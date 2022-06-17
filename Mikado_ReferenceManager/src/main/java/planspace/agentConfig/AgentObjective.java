package planspace.agentConfig;

import java.util.ArrayList;
import java.util.List;

import planspace.agentConfig.EventChannelConfiguration.t_EventChannelType;
import planspace.agentinteraction.AbstractEvent.t_eventType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_agentObjectiveType;

public class AgentObjective {
	/*public enum t_agentObjectiveType {
		CLASSIFY_EXTERNAL_EVENTS, DETERMINE_HYPOTHETICAL_IMPACT, CLASSIFICATION_POTENTIAL_IMPACT,

		// * TO DO
		PROVE_HYPOTHETICAL_IMPACT, CHECK_FACTS
	}*/

	// Type of Objective

	t_agentObjectiveType myObjective;
	
	// Specialisms for objective
	AgentObjectiveSpecialism mySpecialism = null;

	// Channels
	EventChannelConfiguration myChannelToListenTo;
	EventChannelConfiguration myChannelToProduceTo;


	public AgentObjective() {
		// constructor

		mySpecialism = null;

	}


	public void setMyObjective(t_agentObjectiveType myObjective ) {
		this.myObjective = myObjective;

	}

	/* General getter and setters */
	
	
	
	/**************************************************
	 * settings of optional specialisms This will filter the type of events the
	 * agent will listen to
	 ***********************************************/
	public AgentObjectiveSpecialism getMySpecialism() {
		return mySpecialism;
	}



	public void setMySpecialism(AgentObjectiveSpecialism mySpecialism) {
		this.mySpecialism = mySpecialism;
	}
	
	public t_agentObjectiveType getMyObjective() {
		return myObjective;
	}
	


	/**************************************************
	 * Default settings of Kafka based on Objective Type
	 ***********************************************/

	public EventChannelConfiguration getMyChannelToListenTo() {
		return myChannelToListenTo;
	}

	public void setMyChannelToListenTo(EventChannelConfiguration myChannelToListenTo) {
		this.myChannelToListenTo = myChannelToListenTo;
	}

	public EventChannelConfiguration getMyChannelToProduceTo() {
		return myChannelToProduceTo;
	}

	public void setMyChannelToProduceTo(EventChannelConfiguration myChannelToProduceTo) {
		this.myChannelToProduceTo = myChannelToProduceTo;
	}

	public void addEventChannelConfiguration(EventChannelConfiguration anEventChannelConfiguration) {

		if (anEventChannelConfiguration.getChannelType().equals(t_EventChannelType.CONSUMER_CHANNEL)) {
			myChannelToListenTo = anEventChannelConfiguration;
		} else if (anEventChannelConfiguration.getChannelType().equals(t_EventChannelType.PRODUCER_CHANNEL)) {
			myChannelToProduceTo = anEventChannelConfiguration;
		}

	}

	public boolean isConsumingEventType(t_eventType theEventTypeDetected) {
		int i;
		boolean iAm_ConsumingTheEventTypeDetected;

		/*
		 * Future list for (i= 0; i < this.myChannelToListenTo)
		 * 
		 */

		iAm_ConsumingTheEventTypeDetected = false;

		if (this.myChannelToListenTo != null) {
			if (this.myChannelToListenTo.isConsumingEventType(theEventTypeDetected)) {
				iAm_ConsumingTheEventTypeDetected = true;
			}
		}
		return iAm_ConsumingTheEventTypeDetected;
	}

	public List<t_eventType> getEventTypesConsumed() {

		List<t_eventType> theEventypesConsumed;

		theEventypesConsumed = new ArrayList<t_eventType>();

		/*
		 * Future list for (i= 0; i < this.myChannelToListenTo)
		 * 
		 */

		if (this.myChannelToListenTo != null) {
			theEventypesConsumed = this.myChannelToListenTo.getEventTypesOfInterest();
		}
		return theEventypesConsumed;
	}

	public List<t_eventType> getEventTypesProduced() {

		List<t_eventType> theEventypesProduced;

		theEventypesProduced = new ArrayList<t_eventType>();

		/*
		 * Future list for (i= 0; i < this.myChannelToListenTo)
		 * 
		 */

		if (this.myChannelToProduceTo != null) {
			theEventypesProduced = this.myChannelToProduceTo.getEventTypesOfInterest();
		}
		return theEventypesProduced;
	}

}
