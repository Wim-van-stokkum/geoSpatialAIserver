package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestContext;
import nl.geospatialAI.beans.AssessRequestReply;




@Controller

public class assessRequestController {
	
  private int requestRefID = 0 ;

  @RequestMapping(method = RequestMethod.POST, value="/geoSpatialAIserver/assessRequest")

  @ResponseBody

  public AssessRequestReply registerRequest(@RequestBody AssessRequest aRequest) {

	  requestRefID = requestRefID + 1;
	  
  System.out.println("receiving AssessRequest"  + requestRefID + 
                     " for city: " + aRequest.getContextLocation().getCity() );

    AssessRequestReply stdregreply = new AssessRequestReply();           
    stdregreply.setReferenceID(requestRefID);
    


   if (  AssessRequestContext.tScenarioType.formalRequest == aRequest.getTheContext().getScenario() ) {
	   stdregreply.setCaseID("ALMERE_CASE:2020-345B1223");
   }
    		
    
      
        //We are setting the below value just to reply a message back to the caller

 


        return stdregreply;

}

}