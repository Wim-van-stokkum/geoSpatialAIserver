package nl.geospatialAI.Agents;

import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.ExternalEventTypes.EventEnvironmentalMutationNewMainObject;
import nl.geospatialAI.EventTypes.InternalEventTypes.EventBuild_NewMainBuilding;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class EventObserver extends InternalAgent {

	private static EventObserver stdObserver = null;

	public static EventObserver getInstance() {

		if (stdObserver == null) {

			stdObserver = new EventObserver();
			stdObserver.agentType = InternalAgent.t_AgentType.OBSERVER;
			return stdObserver;

		}

		else {

			return stdObserver;

		}

	}

	@Override
	public void notifyExternalEvent(ExternalEvent anExternalEvent) {
	   boolean relevantEvent ;
	   EventBuild_NewMainBuilding internalEventNewMainBuilding;
	   EventEnvironmentalMutationNewMainObject externalEventNewMainBuilding;
	   
		super.notifyExternalEvent(anExternalEvent);
      
	   relevantEvent = this.classifyExternalEvent(anExternalEvent);
		if (relevantEvent) {
			ServerGlobals.getInstance().log(this.agentType + " with ID " + this.getMyID() 
		    + " classified external event " + anExternalEvent.getMyID() + " as being relevant" );
			// ServerGlobals.getInstance().log("TYPE " + anExternalEvent.getEventType());
			if (anExternalEvent.getEventType().equals(ExternalEvent.t_externalEventType.EnvironmentalMutation_NewMainBuilding)) {
			
				ServerGlobals.getInstance().log("Creating internal event for new build");
						externalEventNewMainBuilding = (EventEnvironmentalMutationNewMainObject) anExternalEvent;
						internalEventNewMainBuilding = new EventBuild_NewMainBuilding();
						internalEventNewMainBuilding.SetCorrespondingRequest(externalEventNewMainBuilding.GetCorrespondingRequest());
						internalEventNewMainBuilding.SetCorrespondingReply(externalEventNewMainBuilding.GetCorrespondingReply());
						ServerGlobals.getInstance().getTheInternalMessageBroker().publishInternalEvent(internalEventNewMainBuilding);
			}
	
						
			
		} else
		{
			ServerGlobals.getInstance().log(this.agentType + " with ID " + this.getMyID() 
			    + " classified external event " + anExternalEvent.getMyID() + " as being irrelevant" );
		}
	}

	private boolean classifyExternalEvent(ExternalEvent anExternalEvent) {
		boolean relevantEvent;
		
		relevantEvent = false;
		if (anExternalEvent.getEventType().equals(ExternalEvent.t_externalEventType.EnvironmentalMutation_NewMainBuilding)) {
			relevantEvent = true;
		}
		  
		return relevantEvent;
	}

}
