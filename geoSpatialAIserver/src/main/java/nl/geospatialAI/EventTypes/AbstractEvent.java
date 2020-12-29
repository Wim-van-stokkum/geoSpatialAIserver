package nl.geospatialAI.EventTypes;

import nl.geospatialAI.EventTypes.AbstractEvent.t_EventStatus;

public class AbstractEvent {
	
	public enum t_EventSourceType{
		EXTERNAL,
		INTERNAL
}
	public enum t_EventStatus {
		CREATED,
		TO_PUBLISH,
		PUBLISHING,
		PUBLISHED
}
	
	
	protected String _id;
	protected t_EventSourceType sourceType;
	protected t_EventStatus eventStatus;
	
	
	
	public t_EventSourceType getEventSourceType() {
		return sourceType;
	}
	
	public t_EventStatus getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(t_EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}
}
