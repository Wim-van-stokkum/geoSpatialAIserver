package nl.geospatialAI.EventTypes.ExternalEventTypes;

import nl.geospatialAI.EventTypes.ExternalEvent;

import nl.geospatialAI.beans.ConfigUpdateReply;
import nl.geospatialAI.beans.ConfigUpdateRequest;

public class EventNewDemoDataPointTypeConfig extends ExternalEvent {
	private ConfigUpdateRequest theRequest;
	private  ConfigUpdateReply theReply;
	private String context;

	public EventNewDemoDataPointTypeConfig() {
		super();

		this.setEventTypeCategory(ExternalEvent.t_externalEventTypeCategory.ConfigurationEventType);
		this.eventType = ExternalEvent.t_externalEventType.New_Demo_DataPointTypes;

	}

	public void SetCorrespondingRequest(ConfigUpdateRequest aRequest) {
		theRequest = aRequest;
	}
	
	public void SetCorrespondingReply(ConfigUpdateReply aReply) {
		theReply = aReply;
	}
	
	public ConfigUpdateRequest GetCorrespondingRequest() {
		return theRequest ;
	}
	
	public ConfigUpdateReply GetCorrespondingReply() {
		return theReply;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	
}
