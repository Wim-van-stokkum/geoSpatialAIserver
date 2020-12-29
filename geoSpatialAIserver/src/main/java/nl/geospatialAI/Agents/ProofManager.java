package nl.geospatialAI.Agents;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.EventTypes.InternalEventTypes.Event_NewMainBuildingRiskAssessed;
import nl.geospatialAI.EventTypes.InternalEventTypes.Event_NewMainBuildingRiskHypothesesDetermined;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class ProofManager extends InternalAgent {

	private static ProofManager stdProofManager = null;

	public static ProofManager getInstance() {

		if (stdProofManager == null) {

			stdProofManager = new ProofManager();
			stdProofManager.agentType = InternalAgent.t_AgentType.PROOFMANAGER;
			return stdProofManager;

		}

		else {

			return stdProofManager;

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
					.equals(InternalEvent.t_internalEventType.Build_NewMainBuildingRiskHypothesesDetermined)) {
				this.ProofRiskHypotheses(anInternalEvent);
			}

		} else {
			ServerGlobals.getInstance().log(this.agentType + "  classified internal event NOT relevant");
		}

	}

	private void ProofRiskHypotheses(InternalEvent anInternalEvent) {
		Case theCase;
		AssessRequestReply theReply;
		ServerGlobals theServerGlobals;
		Event_NewMainBuildingRiskHypothesesDetermined eventNewMainBuildingRiskHypothesesDetermined;
		Event_NewMainBuildingRiskAssessed eventNewBuildRiskAssessed;
		boolean BIMfile_statusknown;

		theServerGlobals = ServerGlobals.getInstance();
		eventNewMainBuildingRiskHypothesesDetermined = (Event_NewMainBuildingRiskHypothesesDetermined) anInternalEvent;

		theCase = eventNewMainBuildingRiskHypothesesDetermined.GetCorrespondingCase();
		theReply = eventNewMainBuildingRiskHypothesesDetermined.GetCorrespondingReply();

		// Risk Assessment of Case but first check BIM file availability
		BIMfile_statusknown = theCase.HandleBIMFile(theServerGlobals, theCase, theReply);

		
		if (BIMfile_statusknown) {
			theCase.startAssessment(theServerGlobals, theReply);

			// theCase.evaluateAssessmentCriteria(theServerGlobals, theReply);
			// Publish event case created for relevant event NEW Build
			theServerGlobals.log("Creating internal event for assessed case new build");

			eventNewBuildRiskAssessed = new Event_NewMainBuildingRiskAssessed();
			eventNewBuildRiskAssessed.SetCorrespondingRequest(eventNewMainBuildingRiskHypothesesDetermined.GetCorrespondingRequest());
			eventNewBuildRiskAssessed.SetCorrespondingReply(theReply);
			eventNewBuildRiskAssessed.SetCorrespondingCase(theCase);
			theServerGlobals.getTheInternalMessageBroker().publishInternalEvent(eventNewBuildRiskAssessed);

			// ================================================================================

		}
	


	}

	private boolean classifyInternalEvent(InternalEvent anInternalEvent) {
		boolean relevantEvent;

		relevantEvent = false;
		if (anInternalEvent.getEventType().equals(InternalEvent.t_internalEventType.Build_NewMainBuildingRiskHypothesesDetermined)) {
			relevantEvent = true;
		}

		return relevantEvent;
	}

}
