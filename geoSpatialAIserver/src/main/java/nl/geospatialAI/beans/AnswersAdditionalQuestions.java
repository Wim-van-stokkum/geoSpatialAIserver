package nl.geospatialAI.beans;

import java.util.List;

import nl.geospatialAI.DataPoints.DataPointValue;

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
   	
	public AnswersAdditionalQuestions() {
		System.out.println("CREATING ANSWER FOR ADDITIONAL QUESTION");
	}
   	
   	
}
