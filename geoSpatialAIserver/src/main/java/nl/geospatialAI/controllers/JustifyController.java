package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.Assessment.Risk;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.Errorhandling.ErrorReason;
import nl.geospatialAI.beans.JustifyRiskReply;
import nl.geospatialAI.beans.JustifyRiskRequest;
import nl.geospatialAI.serverGlobals.ServerGlobals;


@Controller

public class JustifyController {


	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/geoSpatialAIserver/justifyRisk")

	@ResponseBody

	public JustifyRiskReply registerRequest(@RequestBody JustifyRiskRequest aRequest) {

		ServerGlobals theServerGlobals;
		Case correspondingCase;
		ErrorReason anError;
		JustifyRiskReply theJustifyReply;
		Risk theRisk;
		
		

		// get globals
		theServerGlobals = ServerGlobals.getInstance();
		theServerGlobals.log("Justification for risk: " + aRequest.getRiskRefID() + " of case " + aRequest.getReferenceID());
		theRisk = null;
		correspondingCase = null;

		// create the reply
		theJustifyReply = new JustifyRiskReply();


		// process the request
		// - Get The corresponding (anomynous) case
		correspondingCase = theServerGlobals.getCaseRegistration().GetCaseByCaseNo(aRequest.getReferenceID());

		if (correspondingCase == null) {
			anError = ErrorReason.createErrorReason_info(ErrorReason.t_ErrorReasonType.NO_CASE_REGISTERED, "with case ID : " +  aRequest.getReferenceID());
			theJustifyReply.registerErrorReason(anError);
			theServerGlobals.log("No case found [" +aRequest.getReferenceID() +   "] for submitted datapoint values");
		} else {
			theJustifyReply.setCaseID(correspondingCase.getCaseID());
		}
		
		//get the risk
		
		if (correspondingCase != null) {
			theRisk = correspondingCase.getRiskByID(aRequest.getRiskRefID());
			if (theRisk == null) {
				anError = ErrorReason.createErrorReason_info(ErrorReason.t_ErrorReasonType.NO_RISK_FOUND, "with case ID : " +  aRequest.getRiskRefID());
				theJustifyReply.registerErrorReason(anError);
				theServerGlobals.log("No risk found [" +aRequest.getRiskRefID() +   "] to justify");
			} else {
				theServerGlobals.log("Case found");
			}
			
		}
		
		// justify
		
		if (correspondingCase != null) {
			if (theRisk != null) {

				theJustifyReply.justifyTheRisk(theServerGlobals,correspondingCase,theRisk  );
				
			}
		}
		
		

		// sent the reply
		if (correspondingCase != null) {
			theJustifyReply.setReferenceID(correspondingCase.getCaseNo());
			theJustifyReply.EvalStatus();
		}
	
		return theJustifyReply;

	}
}