package nl.geospatialAI.beans;

import java.util.List;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPointValue;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class AnswersAdditionalQuestions {
   	int referenceID;
   	List <DataPointValue> additionalDataPoints;
   	
	public int getReferenceID() {
		return referenceID;
	}
	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}
	public List<DataPointValue> getAdditionalDataPoints() {
		return additionalDataPoints;
	}
	public void setAdditionalDataPoints(List<DataPointValue> additionalDataPoints) {
		this.additionalDataPoints = additionalDataPoints;
	}
	public void registerValuesSubmitted(ServerGlobals theServerGlobals, Case theCase, SubmitQuestionsAnswersReply theReply) {
	  int i;
	  DataPointValue anDataPointValue;
	  
	  for (i = 0 ; i < this.additionalDataPoints.size(); i++) {
		  anDataPointValue = this.additionalDataPoints.get(i);
		  anDataPointValue.registerValueSubmitted(theServerGlobals, theCase, theReply);
	  }
		
	}
   	
	
   	
   	
}
