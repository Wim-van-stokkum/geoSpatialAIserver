package nl.geospatialAI.Agents;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.EventTypes.InternalEventTypes.EventBuild_NewMainBuilding;
import nl.geospatialAI.EventTypes.InternalEventTypes.EventBuild_NewMainBuildingCase;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class CaseManager extends InternalAgent {
	private static CaseManager stdCaseManager = null;

	public static CaseManager getInstance() {

		if (stdCaseManager == null) {

			stdCaseManager = new CaseManager();
			stdCaseManager.agentType = InternalAgent.t_AgentType.CASEMANAGER;
			return stdCaseManager;

		}

		else {

			return stdCaseManager;

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
		Case theCase;

		super.notifyInternalEvent(anInternalEvent);

		relevantEvent = this.classifyInternalEvent(anInternalEvent);
		if (relevantEvent) {
			ServerGlobals.getInstance().log(this.agentType 
					+ "  classified internal event relevant");

			ServerGlobals.getInstance().log("eventtype "+ anInternalEvent.getEventType());
			if (anInternalEvent.getEventType().equals(InternalEvent.t_internalEventType.Build_NewMainBuilding)) {
				theCase = this.CreateCaseForNewBuild(anInternalEvent);
			}

		} else {
			ServerGlobals.getInstance().log(this.agentType 
					+ "  classified internal event NOT relevant");
		}
	}

	private boolean classifyInternalEvent(InternalEvent anInternalEvent) {
		boolean relevantEvent;
        
		
		relevantEvent = false;
		if (anInternalEvent.getEventType().equals(InternalEvent.t_internalEventType.Build_NewMainBuilding)) {
			relevantEvent = true;
		}

		return relevantEvent;
	}

	private Case CreateCaseForNewBuild(InternalEvent anInternalEvent) {
		Case newCase;
		AssessRequestReply theReply;
		boolean BIMfile_statusknown;

		EventBuild_NewMainBuilding eventNewBuild;
		EventBuild_NewMainBuildingCase eventNewBuildCaseCreated;
		
		ServerGlobals theServerGlobals;

		theServerGlobals = ServerGlobals.getInstance();
		eventNewBuild = (EventBuild_NewMainBuilding) anInternalEvent;

		newCase = new Case();
		theReply = eventNewBuild.GetCorrespondingReply();

		theServerGlobals.getCaseRegistration().add(newCase);
		theServerGlobals.log("Creating case [" + newCase.getCaseID() + "]");

		newCase.initCaseByRequest(eventNewBuild.GetCorrespondingRequest(), theServerGlobals);
		eventNewBuild.GetCorrespondingReply().setCaseID(newCase.getCaseID());


		

		// Publish event case created for relevant event NEW Build
		theServerGlobals.log("Creating internal event for new build after case creation");
		
		eventNewBuildCaseCreated = new EventBuild_NewMainBuildingCase();
		eventNewBuildCaseCreated.SetCorrespondingRequest(eventNewBuild.GetCorrespondingRequest());
		eventNewBuildCaseCreated.SetCorrespondingReply(eventNewBuild.GetCorrespondingReply());
		eventNewBuildCaseCreated.SetCorrespondingCase(newCase);
		ServerGlobals.getInstance().getTheInternalMessageBroker().publishInternalEvent(eventNewBuildCaseCreated);
		
		// ================================================================================
		
		
		
		
		
		newCase.setForUser(theReply);

		theReply.setReferenceID(newCase.getCaseNo());
		theReply.EvalStatus();

		return newCase;
	}
}
