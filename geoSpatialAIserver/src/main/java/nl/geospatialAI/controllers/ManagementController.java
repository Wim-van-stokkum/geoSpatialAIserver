package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.beans.AssessRequestContext;
import nl.geospatialAI.beans.ConfigUpdateReply;
import nl.geospatialAI.beans.ConfigUpdateRequest;
import nl.geospatialAI.serverGlobals.ServerGlobals;

@Controller

public class ManagementController {
	private int requestRefID = 0;
	
	@CrossOrigin

	@RequestMapping(method = RequestMethod.POST, value = "/geoSpatialAIserver/configmanagement/updateContextConfig")


	@ResponseBody

	public ConfigUpdateReply registerRequest(@RequestBody ConfigUpdateRequest aRequest) {
	
	
		ServerGlobals theServerGlobals;
		
		ConfigUpdateReply theReply;
	
	
		
        // get globals
		theServerGlobals = ServerGlobals.getInstance();

		
		// Register the new request
		theServerGlobals.log("Processing request to update Config for context:" +  aRequest.getContextType());

		requestRefID = requestRefID + 1;
		// log incoming requests
		// FOR PERFORMANCE CANCELLED : AssessRequestDAO.getInstance().add(aRequest);
		
		// Create a response for the request
		theReply = new ConfigUpdateReply();
	
		
		// Create case for handling this request
		

		// prepare for user
	
         // sent reply
		theReply.setStatus("ConfigUpdated");
		theReply.setReferenceID(requestRefID);

		return theReply;

	}

}
