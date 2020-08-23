package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.beans.ScenarioRequest;
import nl.geospatialAI.serverGlobals.ServerGlobals;

@Controller
public class ScenarioController {


	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/geoSpatialAIserver/scenario")

	@ResponseBody

	public void registerRequest(@RequestBody ScenarioRequest aRequest) {

		ServerGlobals theServerGlobals;
		
		
		

		// get globals
		theServerGlobals = ServerGlobals.getInstance();
		theServerGlobals.setScenario(aRequest.getSCENARIO());
		theServerGlobals.log("ACTIVATION OF SCENARIO: " + aRequest.getSCENARIO() );

	}
}