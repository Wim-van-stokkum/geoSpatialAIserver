package nl.geospatialAI.beans;

import java.lang.String;



public class AssessRequestContext {

	public enum tScenarioType {
		  scenarioExploration,
		  formalRequest
		}



   private tScenarioType scenario;
   private String referenceID;
   
   public tScenarioType getScenario() {
	return scenario;
}
public void setScenario(tScenarioType scenario) {
	this.scenario = scenario;
}
public String getReferenceID() {
	return referenceID;
}
public void setReferenceID(String referenceID) {
	this.referenceID = referenceID;
}

   
   
   
}
