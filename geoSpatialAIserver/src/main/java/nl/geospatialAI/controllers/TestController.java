package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.beans.TestReply;
import nl.geospatialAI.beans.TestRequest;
import nl.geospatialAI.serverGlobals.ServerGlobals;

@Controller
public class TestController {
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/geoSpatialAIserver/test")

	@ResponseBody

	public TestReply RegisterTest(@RequestBody TestRequest aRequest) {

		ServerGlobals theServerGlobals;
		
		TestReply theReply;
		

		// get globals
		theServerGlobals = ServerGlobals.getInstance();

		theServerGlobals.log("BERICHT VAN AGENT: " + aRequest.getVanAgent() );
		theReply = new TestReply();
		
		return theReply;
	}

}
