package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import nl.geospatialAI.EventTypes.ExternalEventTypes.EventEnvironmentalMutationNewMainObject;
import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestContext;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

@Controller

public class AssessRequestController {

	private int requestRefID = 0;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/geoSpatialAIserver/assessRequest")

	@ResponseBody

	public AssessRequestReply registerRequest(@RequestBody AssessRequest aRequest) {
	
		ServerGlobals theServerGlobals;

		AssessRequestReply theReply;

		EventEnvironmentalMutationNewMainObject theAssessEvent; 
		
		
        // get globals
		theServerGlobals = ServerGlobals.getInstance();

	
		// Create a response for the request
		theReply = new AssessRequestReply();
		theReply.setUserType( AssessRequestContext.tUsertype.AANVRAGER);
		if (aRequest.getTheContext().getUserType() != null) {
			if (aRequest.getTheContext().getUserType().equals( AssessRequestContext.tUsertype.BEOORDELAAR)) {
				theReply.setUserType( AssessRequestContext.tUsertype.BEOORDELAAR);
	
			}
		}
		requestRefID = requestRefID + 1;
		
		// CreateExternalEvent for explicit request from digital twin
		theAssessEvent = new EventEnvironmentalMutationNewMainObject();
		theAssessEvent.SetCorrespondingRequest(aRequest);
		theAssessEvent.SetCorrespondingReply(theReply);
		theServerGlobals.getTheInternalMessageBroker().publishExternalEvent(theAssessEvent);
		

		
	
		return theReply;

	}

}