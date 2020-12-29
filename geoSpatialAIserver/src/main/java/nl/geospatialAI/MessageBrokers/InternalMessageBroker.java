package nl.geospatialAI.MessageBrokers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.geospatialAI.Agents.InternalAgent;
import nl.geospatialAI.EventTypes.AbstractEvent;
import nl.geospatialAI.EventTypes.ExternalEvent;
import nl.geospatialAI.EventTypes.InternalEvent;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class InternalMessageBroker {
    

	private static InternalMessageBroker stdInternalMessageBroker = null;

	private String _id;
	private List <InternalAgent> myListenersToExternalEvents;
	private List <InternalAgent> myListenersToInternalEvents;
	

	public static InternalMessageBroker getInstance() {

		if (stdInternalMessageBroker == null) {

			stdInternalMessageBroker = new InternalMessageBroker();


			return stdInternalMessageBroker;

		}

		else {

			return stdInternalMessageBroker;

		}

	}

	public InternalMessageBroker() {
		_id = UUID.randomUUID().toString();
		myListenersToExternalEvents = new ArrayList<InternalAgent>();
		myListenersToInternalEvents = new ArrayList<InternalAgent>();
	}

	public String getMyID() {
		return _id;
	}

	public void publishExternalEvent(ExternalEvent anExternalEvent) {
		int i; 
		InternalAgent aListener;
		
		
		ServerGlobals.getInstance().log("INTERNAL MESSAGE BROKER: PUBLISHING EXTERNAL EVENT "+anExternalEvent.getMyID() + " ("+ anExternalEvent.getEventType()+ ")" );
		anExternalEvent.setEventStatus(AbstractEvent.t_EventStatus.TO_PUBLISH);
		anExternalEvent.setEventStatus(AbstractEvent.t_EventStatus.PUBLISHING);
		for (i=0; i< myListenersToExternalEvents.size(); i++) {
			aListener = myListenersToExternalEvents.get(i);
			aListener.notifyExternalEvent(anExternalEvent);
			
		}
		
		anExternalEvent.setEventStatus(AbstractEvent.t_EventStatus.PUBLISHED);
	}

	public void registerListenerToExternalEvent(InternalAgent aListeningInternalAgent) {
		this.myListenersToExternalEvents.add(aListeningInternalAgent) ; 
		
	}
	
	public void registerListenerToInternalEvent(InternalAgent aListeningInternalAgent) {
		this.myListenersToInternalEvents.add(aListeningInternalAgent) ; 
		
	}

	public void publishInternalEvent(InternalEvent anInternalEvent) {
		
		int i; 
		InternalAgent aListener;
		
		ServerGlobals.getInstance().log("INTERNAL MESSAGE BROKER: PUBLISHING INTERNAL EVENT "+ anInternalEvent.getMyID() + " (" + anInternalEvent.getEventType()+ ")" );
		anInternalEvent.setEventStatus(AbstractEvent.t_EventStatus.TO_PUBLISH);
		anInternalEvent.setEventStatus(AbstractEvent.t_EventStatus.PUBLISHING);
		
		for (i=0; i< myListenersToInternalEvents.size(); i++) {
			aListener = myListenersToInternalEvents.get(i);
			aListener.notifyInternalEvent(anInternalEvent);
			
		}
		
		anInternalEvent.setEventStatus(AbstractEvent.t_EventStatus.PUBLISHED);
		
	}

}
