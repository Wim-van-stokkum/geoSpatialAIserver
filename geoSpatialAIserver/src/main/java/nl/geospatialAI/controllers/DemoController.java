package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.Demo.Demo;
import nl.geospatialAI.EventTypes.ExternalEventTypes.EventNewDemoDataPointTypeConfig;
import nl.geospatialAI.beans.ConfigUpdateReply;
import nl.geospatialAI.beans.ConfigUpdateRequest;
import nl.geospatialAI.serverGlobals.ServerGlobals;

@Controller

public class DemoController {
	private int requestRefID = 0;
	
	@CrossOrigin

	@RequestMapping(method = RequestMethod.POST, value = "/geoSpatialAIserver/configmanagement/createDemoContent")


	@ResponseBody

	public ConfigUpdateReply registerRequest(@RequestBody ConfigUpdateRequest aRequest) {
	
	
		ServerGlobals theServerGlobals;
		
		ConfigUpdateReply theReply;
		Demo newDemo;
		
		EventNewDemoDataPointTypeConfig anEventNewDemoDataPointTypeConfig;
	
	
		
        // get globals
		theServerGlobals = ServerGlobals.getInstance();

		
		// Register the new request
		theServerGlobals.log("Processing request to create demo context for " +  aRequest.getContextType());

		requestRefID = requestRefID + 1;
		
		//===========================CORE============================//
		anEventNewDemoDataPointTypeConfig = new EventNewDemoDataPointTypeConfig();
		anEventNewDemoDataPointTypeConfig.setContext(aRequest.getContextType());
		anEventNewDemoDataPointTypeConfig.SetCorrespondingRequest(aRequest);

		theReply = new ConfigUpdateReply();
		anEventNewDemoDataPointTypeConfig.SetCorrespondingReply(theReply);
		theServerGlobals.getTheInternalMessageBroker().publishExternalEvent(anEventNewDemoDataPointTypeConfig);

		//===========================CORE============================//



		anEventNewDemoDataPointTypeConfig.GetCorrespondingReply().setStatus("DemoContentCreated");
		anEventNewDemoDataPointTypeConfig.GetCorrespondingReply().setReferenceID(requestRefID);

		return anEventNewDemoDataPointTypeConfig.GetCorrespondingReply();

	}

}
