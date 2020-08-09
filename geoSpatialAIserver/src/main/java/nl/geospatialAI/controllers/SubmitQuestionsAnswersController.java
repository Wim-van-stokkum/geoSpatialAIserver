package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.Errorhandling.ErrorReason;
import nl.geospatialAI.beans.AnswersAdditionalQuestions;
import nl.geospatialAI.beans.SubmitQuestionsAnswersReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

@Controller

public class SubmitQuestionsAnswersController {

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/geoSpatialAIserver/submitAnswersAdditionalQuestions")

	@ResponseBody

	public SubmitQuestionsAnswersReply registerRequest(@RequestBody AnswersAdditionalQuestions aRequest) {

		ServerGlobals theServerGlobals;
		Case correspondingCase;
		ErrorReason anError;
		SubmitQuestionsAnswersReply theSubmitReply;

		// get globals
		theServerGlobals = ServerGlobals.getInstance();
		theServerGlobals.log("receiving answers for: " + aRequest.getReferenceID());

		// create the reply
		theSubmitReply = new SubmitQuestionsAnswersReply();
		theSubmitReply.setReferenceID(aRequest.getReferenceID());

		// process the request
		// - Get The corresponding (anomynous) case
		correspondingCase = theServerGlobals.getCaseRegistration().GetCaseByCaseNo(aRequest.getReferenceID());
		if (correspondingCase == null) {
			anError = ErrorReason.createErrorReason_info(ErrorReason.t_ErrorReasonType.NO_CASE_REGISTERED, "with case ID : " +  aRequest.getReferenceID());
			theSubmitReply.registerErrorReason(anError);
			theServerGlobals.log("No case found [" +aRequest.getReferenceID() +   "] for submitted datapoint values");
		} else {

			// - Register the new values
			aRequest.registerValuesSubmitted(theServerGlobals, correspondingCase, theSubmitReply);

			// Configure and sent the reply

		}

		// sent the reply
		return theSubmitReply;

	}

}