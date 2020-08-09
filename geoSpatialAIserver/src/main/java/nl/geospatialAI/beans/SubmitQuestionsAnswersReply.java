package nl.geospatialAI.beans;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.Errorhandling.ErrorReason;

public class SubmitQuestionsAnswersReply {
	public String status;
	public int referenceID;
 	public List<ErrorReason> myErrorReasons;
 	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getReferenceID() {
		return referenceID;
	}
	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}
	
	public SubmitQuestionsAnswersReply() {
		this.myErrorReasons = new ArrayList<ErrorReason>();
		this.setStatus("Ok");
	}
	public void registerErrorReason(ErrorReason anError) {
		this.myErrorReasons.add(anError);
		if (anError.getSeverity().equals(ErrorReason.t_ErrorReasonSeverity.ERROR)){
			this.setStatus("Processing errors");
		}
	
	}
   	

 	
 	
}
