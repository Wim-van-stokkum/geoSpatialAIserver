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
		boolean recording = true;
		ServerGlobals theServerGlobals;
		PolicyLibrary thePolicyLibrary;
		
        // get globals
		theServerGlobals = ServerGlobals.getInstance();
		thePolicyLibrary = theServerGlobals.getPolicyLibrary();
		
		// Register the new request
		Case newCase;


		requestRefID = requestRefID + 1;
		// log incoming requests
		AssessRequestDAO.getInstance().add(aRequest);

		// Create case for handling this request
		newCase = new Case();
		theServerGlobals.getCaseRegistration().add(newCase);
		
		if (recording) {
			System.out.println("Creating case [" + newCase.getCaseID() + "] for request : " + requestRefID);
		}
		newCase.initCaseByRequest(aRequest, recording);
		
		// First Assessment of case
		newCase.determinePolicyForContext(thePolicyLibrary);
		newCase.startFirstAssessment(recording);

		// Create a response for the request
		AssessRequestReply stdregreply = new AssessRequestReply();

		stdregreply.setReferenceID(requestRefID);

		// stdregreply.CreateStubWithQuestions();



		return stdregreply;

	}

}