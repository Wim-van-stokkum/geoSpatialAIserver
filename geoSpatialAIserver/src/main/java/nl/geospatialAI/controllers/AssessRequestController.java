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
		PolicyLibrary thePolicyLibrary;
		AssessRequestReply theReply;
		boolean BIMfile_statusknown;
		
		
        // get globals
		theServerGlobals = ServerGlobals.getInstance();
		thePolicyLibrary = theServerGlobals.getPolicyLibrary();
		
		// Register the new request
		Case newCase;


		requestRefID = requestRefID + 1;
		// log incoming requests
		// FOR PERFORMANCE CANCELLED : AssessRequestDAO.getInstance().add(aRequest);
		
		// Create a response for the request
		theReply = new AssessRequestReply();
		theReply.setUserType( AssessRequestContext.tUsertype.AANVRAGER);
		if (aRequest.getTheContext().getUserType() != null) {
			if (aRequest.getTheContext().getUserType().equals( AssessRequestContext.tUsertype.BEOORDELAAR)) {
				theReply.setUserType( AssessRequestContext.tUsertype.BEOORDELAAR);
	
			}
		}
	


		// Create case for handling this request
		newCase = new Case();
		theServerGlobals.getCaseRegistration().add(newCase);
		theServerGlobals.log("Creating case [" + newCase.getCaseID() + "] for request : " + requestRefID);
		
		newCase.initCaseByRequest(aRequest,theServerGlobals );
		theReply.setCaseID(newCase.getCaseID());

		// First Assessment of case, but first check BIM file availability
		BIMfile_statusknown = newCase.HandleBIMFile(theServerGlobals, newCase, theReply);
		newCase.determinePolicyForContext(thePolicyLibrary,  theReply);
		newCase.addRisksToReply(theServerGlobals, theReply);
		if (BIMfile_statusknown) {
			newCase.startAssessment(theServerGlobals, theReply);
			newCase.evaluateAssessmentCriteria(theServerGlobals, theReply);
		}
	


		// prepare for user
		  newCase.setForUser(theReply);
         // sent reply
		theReply.setReferenceID(newCase.getCaseNo());
		theReply.EvalStatus();
		return theReply;

	}

}