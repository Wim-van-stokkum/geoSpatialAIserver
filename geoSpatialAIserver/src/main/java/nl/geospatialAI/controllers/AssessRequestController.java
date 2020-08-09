package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.Assessment.PolicyLibrary;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestDAO;
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
		PolicyLibrary thePolicyLibrary;
		AssessRequestReply theReply;
		
        // get globals
		theServerGlobals = ServerGlobals.getInstance();
		thePolicyLibrary = theServerGlobals.getPolicyLibrary();
		
		// Register the new request
		Case newCase;


		requestRefID = requestRefID + 1;
		// log incoming requests
		AssessRequestDAO.getInstance().add(aRequest);
		
		// Create a response for the request
		theReply = new AssessRequestReply(requestRefID);
	


		// Create case for handling this request
		newCase = new Case();
		theServerGlobals.getCaseRegistration().add(newCase);
		theServerGlobals.log("Creating case [" + newCase.getCaseID() + "] for request : " + requestRefID);
		
		newCase.initCaseByRequest(aRequest,theServerGlobals );
		
		// First Assessment of case
		newCase.determinePolicyForContext(thePolicyLibrary);
		newCase.startFirstAssessment(theServerGlobals);


		// stdregreply.CreateStubWithQuestions();

         // sent reply

		return theReply;

	}

}