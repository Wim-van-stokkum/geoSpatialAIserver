package nl.geospatialAI.Agents;

import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.ExternalEventTypes.EventEnvironmentalMutationNewMainObject;
import nl.geospatialAI.EventTypes.InternalEventTypes.EventBuild_NewMainBuilding;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class PolicyManager extends InternalAgent {


	private static PolicyManager stdPolicyManager = null;

	public static PolicyManager getInstance() {

		if (stdPolicyManager == null) {

			stdPolicyManager = new PolicyManager();
			stdPolicyManager.agentType = InternalAgent.t_AgentType.POLICYMANAGER;
			return stdPolicyManager;

		}

		else {

			return stdPolicyManager;

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
			ServerGlobals.getInstance().log(this.agentType 
		    + " classified external event as being relevant" );
			// ServerGlobals.getInstance().log("TYPE " + anExternalEvent.getEventType());
			if (anExternalEvent.getEventType().equals(ExternalEvent.t_externalEventType.Release_NewPolicyForContext)) {
			
				ServerGlobals.getInstance().log("Creating internal event for new policy for context");
					//	externalEventNewMainBuilding = (EventEnvironmentalMutationNewMainObject) anExternalEvent;
						//internalEventNewMainBuilding = new EventBuild_NewMainBuilding();
						//internalEventNewMainBuilding.SetCorrespondingRequest(externalEventNewMainBuilding.GetCorrespondingRequest());
						//internalEventNewMainBuilding.SetCorrespondingReply(externalEventNewMainBuilding.GetCorrespondingReply());
					//	ServerGlobals.getInstance().getTheInternalMessageBroker().publishInternalEvent(internalEventNewMainBuilding);
			}
	
						
			
		} else
		{
			ServerGlobals.getInstance().log(this.agentType 
			    + " classified external event as being irrelevant" );
		}
	}

	private boolean classifyExternalEvent(ExternalEvent anExternalEvent) {
		boolean relevantEvent;
		
		relevantEvent = false;
		if (anExternalEvent.getEventType().equals(ExternalEvent.t_externalEventType.Release_NewPolicyForContext)) {
			relevantEvent = true;
		}
		  
		return relevantEvent;
	}

}