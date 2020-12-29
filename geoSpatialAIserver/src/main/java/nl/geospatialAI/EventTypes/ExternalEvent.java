package nl.geospatialAI.EventTypes;

import java.util.UUID;

public class ExternalEvent extends AbstractEvent {
	

	public enum t_externalEventTypeCategory {
			BusinessServiceEventType
	}
 
	public enum t_externalEventType {
		EnvironmentalMutation_NewMainBuilding,
		EnvironmentalMutation_NewAdditionalBuilding,
		Release_NewPolicyForContext
	}
	
	
	protected t_externalEventType eventType;
	protected t_externalEventTypeCategory eventTypeCategory;

	public ExternalEvent() {
		_id = UUID.randomUUID().toString();
	   this.sourceType = AbstractEvent.t_EventSourceType.EXTERNAL;
	   this.setEventStatus(t_EventStatus.CREATED);

	}

	public t_externalEventType getEventType() {
		return eventType;
	}

	public void setEventType(t_externalEventType eventType) {
		this.eventType = eventType;
	}

	public t_externalEventTypeCategory getEventTypeCategory() {
		return eventTypeCategory;
	}

	public void setEventTypeCategory(t_externalEventTypeCategory eventTypeCategory) {
		this.eventTypeCategory = eventTypeCategory;
	}

	public String getMyID() {
		return _id;
	}



	
	
}
