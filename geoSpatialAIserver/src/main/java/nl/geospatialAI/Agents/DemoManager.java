package nl.geospatialAI.Agents;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.Demo.Demo;
import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.EventTypes.ExternalEventTypes.EventNewDemoDataPointTypeConfig;
import nl.geospatialAI.EventTypes.InternalEventTypes.EventBuild_NewMainBuilding;
import nl.geospatialAI.EventTypes.InternalEventTypes.EventBuild_NewMainBuildingCase;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class DemoManager extends InternalAgent {
	private static DemoManager stdDemoManager = null;

	public static DemoManager getInstance() {

		if (stdDemoManager == null) {

			stdDemoManager = new DemoManager();
			stdDemoManager.agentType = InternalAgent.t_AgentType.DEMO_MANAGER;
			return stdDemoManager;

		}

		else {

			return stdDemoManager;

		}

	}

	@Override
	public void notifyExternalEvent(ExternalEvent anExternalEvent) {
		boolean relevantEvent;
		Demo theDemo;


		super.notifyExternalEvent(anExternalEvent);

		relevantEvent = this.classifyExternalEvent(anExternalEvent);
		if (relevantEvent) {
			ServerGlobals.getInstance().log(this.agentType + " with ID " + this.getMyID()
					+ " classified external event " + anExternalEvent.getMyID() + " as being relevant");

			ServerGlobals.getInstance().log("eventtype " + anExternalEvent.getEventType());
			if (anExternalEvent.getEventType().equals(ExternalEvent.t_externalEventType.New_Demo_DataPointTypes)) {
				

				theDemo = new Demo();
				theDemo.initDataPointTypes();

			}

		} else {
			ServerGlobals.getInstance().log(this.agentType + " with ID " + this.getMyID()
					+ " classified external event " + anExternalEvent.getMyID() + " as being irrelevant");
		}
	}

	private boolean classifyExternalEvent(ExternalEvent anExternalEvent) {
		boolean relevantEvent;
		EventNewDemoDataPointTypeConfig theNewDataPointTypesEvent;

		relevantEvent = false;
		if (anExternalEvent.getEventType().equals(ExternalEvent.t_externalEventType.New_Demo_DataPointTypes)) {
			theNewDataPointTypesEvent = (EventNewDemoDataPointTypeConfig) anExternalEvent;
			if (theNewDataPointTypesEvent.getContext().equals("Almere Demo")) {
				relevantEvent = true;
			}
		}

		return relevantEvent;
	}

	@Override
	public void notifyInternalEvent(InternalEvent anInternalEvent) {
		boolean relevantEvent;
		Case theCase;

		super.notifyInternalEvent(anInternalEvent);

		relevantEvent = this.classifyInternalEvent(anInternalEvent);
		if (relevantEvent) {
			ServerGlobals.getInstance().log(this.agentType + "  classified internal event relevant");

		} else {
			ServerGlobals.getInstance().log(this.agentType + "  classified internal event NOT relevant");
		}
	}

	private boolean classifyInternalEvent(InternalEvent anInternalEvent) {
		boolean relevantEvent;

		relevantEvent = false;

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
