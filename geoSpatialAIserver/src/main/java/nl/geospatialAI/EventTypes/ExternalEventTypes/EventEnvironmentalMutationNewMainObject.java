package nl.geospatialAI.EventTypes.ExternalEventTypes;

import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestReply;

public class EventEnvironmentalMutationNewMainObject extends ExternalEvent {

	private AssessRequest theRequest;
	private  AssessRequestReply theReply;

	public EventEnvironmentalMutationNewMainObject() {
		super();

		this.setEventTypeCategory(ExternalEvent.t_externalEventTypeCategory.BusinessServiceEventType);
		this.eventType = ExternalEvent.t_externalEventType.EnvironmentalMutation_NewMainBuilding;

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
