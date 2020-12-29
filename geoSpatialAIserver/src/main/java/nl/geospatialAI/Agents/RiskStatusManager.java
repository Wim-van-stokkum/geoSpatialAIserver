package nl.geospatialAI.Agents;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.EventTypes.InternalEventTypes.Event_NewMainBuildingRiskAssessed;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class RiskStatusManager extends InternalAgent {

	private static RiskStatusManager stdRiskStatusManager = null;

	public static RiskStatusManager getInstance() {

		if (stdRiskStatusManager == null) {

			stdRiskStatusManager = new RiskStatusManager();
			stdRiskStatusManager.agentType = InternalAgent.t_AgentType.RISKSTATUSMANAGER;
			return stdRiskStatusManager;

		}

		else {

			return stdRiskStatusManager;

		}

	}

	@Override
	public void notifyExternalEvent(ExternalEvent anExternalEvent) {
		boolean relevantEvent;

		super.notifyExternalEvent(anExternalEvent);

		relevantEvent = this.classifyExternalEvent(anExternalEvent);
		if (relevantEvent) {
			ServerGlobals.getInstance().log(this.agentType + " with ID " + this.getMyID()
					+ " classified external event " + anExternalEvent.getMyID() + " as being relevant");

		} else {
			ServerGlobals.getInstance().log(this.agentType + " with ID " + this.getMyID()
					+ " classified external event " + anExternalEvent.getMyID() + " as being irrelevant");
		}
	}

	private boolean classifyExternalEvent(ExternalEvent anExternalEvent) {
		boolean relevantEvent;

		relevantEvent = false;

		return relevantEvent;
	}

	@Override
	public void notifyInternalEvent(InternalEvent anInternalEvent) {
		boolean relevantEvent;
	
	

		super.notifyInternalEvent(anInternalEvent);
		
		relevantEvent = this.classifyInternalEvent(anInternalEvent);
		if (relevantEvent) {
			ServerGlobals.getInstance().log(this.agentType + 
					" classified internal event relevant");

	
			if (anInternalEvent.getEventType().equals(InternalEvent.t_internalEventType.Build_NewMainBuildingRiskAssessed)) {
					this.DetermineRiskStatus(anInternalEvent);
			}

		} else {
			ServerGlobals.getInstance().log(this.agentType +  " classified internal event NOT relevant");
		}
	}

	private void DetermineRiskStatus(InternalEvent anInternalEvent) {
		Case theCase;
		AssessRequestReply theReply;
		ServerGlobals theServerGlobals;
	
		Event_NewMainBuildingRiskAssessed eventNewBuildRiskAssessed;

		
		
		theServerGlobals = ServerGlobals.getInstance();
		eventNewBuildRiskAssessed = (Event_NewMainBuildingRiskAssessed)anInternalEvent;
		
		theCase =eventNewBuildRiskAssessed.GetCorrespondingCase();
		theReply = eventNewBuildRiskAssessed.GetCorrespondingReply();
		


		theCase.evaluateAssessmentCriteria(theServerGlobals, theReply);
	
		// Store case to mongoDB
	//	theServerGlobals.getTheCaseDB().storeCase(theCase);
		
		
	}

	private boolean classifyInternalEvent(InternalEvent anInternalEvent) {
		boolean relevantEvent;
        
		
		relevantEvent = false;
		if (anInternalEvent.getEventType().equals(InternalEvent.t_internalEventType.Build_NewMainBuildingRiskAssessed)) {
			relevantEvent = true;
		}

		return relevantEvent;
	}

	

}