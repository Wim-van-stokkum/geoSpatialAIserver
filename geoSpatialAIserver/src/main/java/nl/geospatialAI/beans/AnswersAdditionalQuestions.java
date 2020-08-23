package nl.geospatialAI.beans;

import java.util.List;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPointValue;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class AnswersAdditionalQuestions {
   	int referenceID;
	AssessRequestContext.tUsertype usertype=AssessRequestContext.tUsertype.AANVRAGER;
	
   	List <DataPointValue> additionalDataPoints;
   	
	


	public AssessRequestContext.tUsertype getUsertype() {
		return this.usertype;
	}

	public void setUsertype(AssessRequestContext.tUsertype aUserType) {
		this.usertype = aUserType;
	}
   	
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
	public void registerValuesSubmitted(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply) {
	  int i;
	  DataPointValue anDataPointValue;
	  
	  for (i = 0 ; i < this.additionalDataPoints.size(); i++) {
		  anDataPointValue = this.additionalDataPoints.get(i);
		  anDataPointValue.registerValueSubmitted(theServerGlobals, theCase, theReply);
	  }
		
	}
	public void requestMissingAnswers(ServerGlobals theServerGlobals, Case correspondingCase,
			AssessRequestReply theSubmitReply) {
		// TODO Auto-generated method stub
		correspondingCase.requestMissingAnswers( theServerGlobals,theSubmitReply);
		
	}
   	
	
   	
   	
}
