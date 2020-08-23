package nl.geospatialAI.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.Errorhandling.ErrorReason;
import nl.geospatialAI.beans.DerivedDataPointsReply;
import nl.geospatialAI.beans.DerivedDataPointsRequest;
import nl.geospatialAI.serverGlobals.ServerGlobals;


@Controller
public class GetDerivedDataPoints {
	@CrossOrigin

	@RequestMapping(method = RequestMethod.GET, value = "/geoSpatialAIserver/getDerivedDataPoints")

	@ResponseBody

	public DerivedDataPointsReply getDerivedDataPoints(@RequestBody DerivedDataPointsRequest aRequest) {

		ServerGlobals theServerGlobals;
		Case correspondingCase;
		ErrorReason anError;
		DerivedDataPointsReply theDerivedDataPointsReply;


	
		// get globals
		
		theServerGlobals = ServerGlobals.getInstance();
		theServerGlobals.log("getting derived datapoints for: " + aRequest.getReferenceID());

		// create the reply
		theDerivedDataPointsReply = new DerivedDataPointsReply();
		theDerivedDataPointsReply.setReferenceID(aRequest.getReferenceID());
		theDerivedDataPointsReply.setUsertype(aRequest.getUsertype());
		
		
		// process the request
		// - Get The corresponding (anomynous) case
		correspondingCase = theServerGlobals.getCaseRegistration().GetCaseByCaseNo(aRequest.getReferenceID());

		if (correspondingCase == null) {
			anError = ErrorReason.createErrorReason_info(ErrorReason.t_ErrorReasonType.NO_CASE_REGISTERED,
					"with case ID : " + aRequest.getReferenceID());
			theDerivedDataPointsReply.registerErrorReason(anError);
			theServerGlobals.log("No case found [" + aRequest.getReferenceID() + "] for getting derived datapoint values");
		} else {
			theDerivedDataPointsReply.setCaseID(correspondingCase.getCaseID());

			// Do my stuff
			theDerivedDataPointsReply.gatherDerivedDataPoints(correspondingCase);

		}

		// sent the reply
		if (correspondingCase != null) {
			theDerivedDataPointsReply.setReferenceID(correspondingCase.getCaseNo());
			theDerivedDataPointsReply.evalStatus();
		}
		return theDerivedDataPointsReply;
		
	}
}