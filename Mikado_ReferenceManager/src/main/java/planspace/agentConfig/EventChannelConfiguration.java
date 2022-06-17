package planspace.agentConfig;

import java.util.ArrayList;
import java.util.List;

import planspace.agentinteraction.AbstractEvent.t_eventType;

public class EventChannelConfiguration {
	public enum t_EventChannelType {
		CONSUMER_CHANNEL,
		PRODUCER_CHANNEL
	}
	
	String topicName;
	String groupIDname;
	t_EventChannelType channelType;

	List<t_eventType> eventTypesOfInterest= null;


	public EventChannelConfiguration() {
		// constructor
		eventTypesOfInterest = new ArrayList<t_eventType>();

	}

	/****************************************************
	 * Getters and Setters
	 */

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getGroupIDname() {
		return groupIDname;
	}

	public void setGroupIDname(String groupIDname) {
		this.groupIDname = groupIDname;
	}

	
	

	public t_EventChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(t_EventChannelType channelType) {
		this.channelType = channelType;
	}

	public List<t_eventType> getEventTypesOfInterest() {
		return eventTypesOfInterest;
	}

	public void setEventTypesOfInterest(List<t_eventType> eventTypesOfInterest) {
		this.eventTypesOfInterest = eventTypesOfInterest;
	}

	public void addEventTypeOfInterest(t_eventType anEventTypeOfInterest) {

		if (this.eventTypesOfInterest.contains(anEventTypeOfInterest) == false) {
			this.eventTypesOfInterest.add(anEventTypeOfInterest);
		}
	}

	public boolean isConsumingEventType(t_eventType theEventTypeDetected) {
		return this.eventTypesOfInterest.contains(theEventTypeDetected);
	}
}
