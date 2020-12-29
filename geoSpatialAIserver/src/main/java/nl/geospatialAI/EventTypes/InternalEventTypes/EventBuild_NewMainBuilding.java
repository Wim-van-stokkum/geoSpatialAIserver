package nl.geospatialAI.EventTypes.InternalEventTypes;

import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestReply;

public class EventBuild_NewMainBuilding extends InternalEvent {
	private AssessRequest theRequest;
	private AssessRequestReply theReply;

	public EventBuild_NewMainBuilding() {

		super();

		this.setEventTypeCategory(InternalEvent.t_internalEventTypeCategory.ImpactfullEnvironmentalMutation);
		this.eventType = InternalEvent.t_internalEventType.Build_NewMainBuilding;

	}


	public void SetCorrespondingRequest(AssessRequest aRequest) {
		theRequest = aRequest;
	}
	
	public void SetCorrespondingReply(AssessRequestReply aReply) {
		theReply = aReply;
	}
	
	public AssessRequest GetCorrespondingRequest() {
		return theRequest ;
	}
	
	public AssessRequestReply GetCorrespondingReply() {
		return theReply;
	}
	
	
}
