package nl.geospatialAI.EventTypes.InternalEventTypes;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestReply;

public class Event_NewMainBuildingRiskAssessed extends InternalEvent {

	private AssessRequest theRequest;
	private AssessRequestReply theReply;
	private Case  theCase;

	public Event_NewMainBuildingRiskAssessed() {

		super();

		this.setEventTypeCategory(InternalEvent.t_internalEventTypeCategory.ImpactfullEnvironmentalMutation);
		this.eventType = InternalEvent.t_internalEventType.Build_NewMainBuildingRiskAssessed;

	}


	public void SetCorrespondingRequest(AssessRequest aRequest) {
		theRequest = aRequest;
	}
	
	public void SetCorrespondingReply(AssessRequestReply aReply) {
		theReply = aReply;
	}
	
	public void SetCorrespondingCase(Case aCase) {
		theCase = aCase;
	}
	
	public AssessRequest GetCorrespondingRequest() {
		return theRequest ;
	}
	
	public AssessRequestReply GetCorrespondingReply() {
		return theReply;
	}
	
	public Case GetCorrespondingCase() {
		return theCase;
	}
	
	
}
