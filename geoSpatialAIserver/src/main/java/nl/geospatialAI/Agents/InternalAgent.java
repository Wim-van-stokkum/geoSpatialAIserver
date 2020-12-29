package nl.geospatialAI.Agents;

import java.util.UUID;

import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.MessageBrokers.InternalMessageBroker;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class InternalAgent {
	public enum t_AgentType {
		OBSERVER,
	    CASEMANAGER,
		RISKMANAGER,
		RISKSTATUSMANAGER,
		PROOFMANAGER,
		POLICYMANAGER
}
	protected String _id;
	protected InternalMessageBroker ObservingAt ;
	protected t_AgentType agentType;

	public InternalAgent() {
		_id = UUID.randomUUID().toString();
	}

	public String getMyID() {
		return _id;
	}

	public void listenToExternalEventsOn(InternalMessageBroker anInternalMessageBroker) {
		ObservingAt = anInternalMessageBroker;
		anInternalMessageBroker.registerListenerToExternalEvent(this);
	}
	
	public void listenToInternalEventsOn(InternalMessageBroker anInternalMessageBroker) {
		ObservingAt = anInternalMessageBroker;
		anInternalMessageBroker.registerListenerToInternalEvent(this);
	}

	public void notifyExternalEvent(ExternalEvent anExternalEvent) {
	   ServerGlobals.getInstance().log(this.agentType + " learns about external event " + anExternalEvent.getMyID());
	   ServerGlobals.getInstance().log("Event category: " + anExternalEvent.getEventTypeCategory());
	   ServerGlobals.getInstance().log("Event source: " + anExternalEvent.getEventSourceType());
	   ServerGlobals.getInstance().log("Event eventtype: " + anExternalEvent.getEventType());
	}

	public void notifyInternalEvent(InternalEvent anInternalEvent) {
		   ServerGlobals.getInstance().log("");
		   ServerGlobals.getInstance().log("----------------------------------------------------------------------------------");
		   
		   ServerGlobals.getInstance().log(this.agentType + "(" + " learns about internal event " + anInternalEvent.getMyID());
		   ServerGlobals.getInstance().log("Event category: " + anInternalEvent.getEventTypeCategory());
		   ServerGlobals.getInstance().log("Event source: " + anInternalEvent.getEventSourceType());
		   ServerGlobals.getInstance().log("Event eventtype: " + anInternalEvent.getEventType());
		   ServerGlobals.getInstance().log("----------------------------------------------------------------------------------");
		   ServerGlobals.getInstance().log("");
		
	}
}
