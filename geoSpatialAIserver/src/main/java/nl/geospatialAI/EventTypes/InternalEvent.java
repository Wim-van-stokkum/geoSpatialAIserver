package nl.geospatialAI.EventTypes;

import java.util.UUID;

import nl.geospatialAI.EventTypes.AbstractEvent.t_EventStatus;

public class InternalEvent extends AbstractEvent {

	public enum t_internalEventTypeCategory {
		ImpactfullEnvironmentalMutation
	}

	public enum t_internalEventType {
		Build_NewMainBuilding, 
		Build_NewAdditionalBuilding, 
		Build_NewMainBuildingCaseCreated, 
		Build_NewMainBuildingRiskAssessed,
		Build_NewMainBuildingRiskHypothesesDetermined
	}

	protected t_internalEventType eventType;
	protected t_internalEventTypeCategory eventTypeCategory;

	public InternalEvent() {
		_id = UUID.randomUUID().toString();
		this.sourceType = AbstractEvent.t_EventSourceType.INTERNAL;
		this.setEventStatus(t_EventStatus.CREATED);

	}

	public t_internalEventType getEventType() {
		return eventType;
	}

	public void setEventType(t_internalEventType eventType) {
		this.eventType = eventType;
	}

	public t_internalEventTypeCategory getEventTypeCategory() {
		return eventTypeCategory;
	}

	public void setEventTypeCategory(t_internalEventTypeCategory eventTypeCategory) {
		this.eventTypeCategory = eventTypeCategory;
	}

	public String getMyID() {
		return _id;
	}

}
