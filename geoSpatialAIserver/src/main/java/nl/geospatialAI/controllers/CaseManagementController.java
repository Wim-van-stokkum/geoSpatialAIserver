package nl.geospatialAI.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.serverGlobals.ServerGlobals;

@Controller

public class CaseManagementController {

	@CrossOrigin

	@RequestMapping(method = RequestMethod.GET, value = "/geoSpatialAIserver/management/getAllCases")

	@ResponseBody
	public List<Case> getAllCases() {

		ServerGlobals theServerGlobals;

		// get globals
		theServerGlobals = ServerGlobals.getInstance();

	
	
		return theServerGlobals.getCaseRegistration().getCases();
		
	}
	
	
	
	/*TEMP */
	/*
	public ResponseAllCases getAllCases() {

		ServerGlobals theServerGlobals;

		// get globals
		theServerGlobals = ServerGlobals.getInstance();

		//temp
		
		ResponseAllCases all = new ResponseAllCases();
		return all;
		//return theServerGlobals.getCaseRegistration().getCases();
		
	}
	*/
	
}