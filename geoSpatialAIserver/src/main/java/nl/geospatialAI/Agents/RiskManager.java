package nl.geospatialAI.Agents;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.EventTypes.InternalEventTypes.EventBuild_NewMainBuildingCase;
import nl.geospatialAI.EventTypes.InternalEventTypes.Event_NewMainBuildingRiskHypothesesDetermined;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class RiskManager extends InternalAgent {

	private static RiskManager stdRiskManager = null;

	public static RiskManager getInstance() {

		if (stdRiskManager == null) {

			stdRiskManager = new RiskManager();
			stdRiskManager.agentType = InternalAgent.t_AgentType.RISKMANAGER;
			return stdRiskManager;

		}

		else {

			return stdRiskManager;

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
		ServerGlobals.getInstance().log("");
		relevantEvent = this.classifyInternalEvent(anInternalEvent);
		if (relevantEvent) {
			ServerGlobals.getInstance().log(this.agentType + " internal event is relevant");

			if (anInternalEvent.getEventType()
					.equals(InternalEvent.t_internalEventType.Build_NewMainBuildingCaseCreated)) {
				this.DetermineRiskHypotheses(anInternalEvent);
			}

		} else {
			ServerGlobals.getInstance().log(this.agentType + "  classified internal event NOT relevant");
		}

	}

	private void DetermineRiskHypotheses(InternalEvent anInternalEvent) {
		Case theCase;
		AssessRequestReply theReply;
		ServerGlobals theServerGlobals;
		EventBuild_NewMainBuildingCase eventNewBuildCaseCreated;
		Event_NewMainBuildingRiskHypothesesDetermined eventNewBuildRiskHypothesesDetermined;
	

		theServerGlobals = ServerGlobals.getInstance();
		eventNewBuildCaseCreated = (EventBuild_NewMainBuildingCase) anInternalEvent;

		theCase = eventNewBuildCaseCreated.GetCorrespondingCase();
		theReply = eventNewBuildCaseCreated.GetCorrespondingReply();

		// Risk Assessment of Case but first check BIM file availability
	

		theCase.determinePolicyForContext(theServerGlobals.getPolicyLibrary(), theReply);
		theCase.addRisksToReply(theServerGlobals, theReply);

			
			theServerGlobals.log("Creating internal event after risk Hypotheses");

			eventNewBuildRiskHypothesesDetermined = new Event_NewMainBuildingRiskHypothesesDetermined();
			eventNewBuildRiskHypothesesDetermined.SetCorrespondingRequest(eventNewBuildCaseCreated.GetCorrespondingRequest());
			eventNewBuildRiskHypothesesDetermined.SetCorrespondingReply(theReply);
			eventNewBuildRiskHypothesesDetermined.SetCorrespondingCase(theCase);
			theServerGlobals.getTheInternalMessageBroker().publishInternalEvent(eventNewBuildRiskHypothesesDetermined);

			// ================================================================================

		
	


	}

	private boolean classifyInternalEvent(InternalEvent anInternalEvent) {
		boolean relevantEvent;

		relevantEvent = false;
		if (anInternalEvent.getEventType().equals(InternalEvent.t_internalEventType.Build_NewMainBuildingCaseCreated)) {
			relevantEvent = true;
		}

		return relevantEvent;
	}

}
