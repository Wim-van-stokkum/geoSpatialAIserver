package nl.geospatialAI.beans;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.Errorhandling.ErrorReason;

public class JustifyRiskReply {
	
	public enum t_JustifyReplyStatus {
		ERROR, 
		OK
		
	}
	
 	int referenceID;
 	int riskRefID;
	public String caseID;
 	t_JustifyReplyStatus JustifyReplyStatus;
 	
 	String answer = "Daarom !";
    List<ErrorReason> myErrorReasons;

    
 	public int getReferenceID() {
		return referenceID;
	}



	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}



	public int getRiskRefID() {
		return riskRefID;
	}



	public void setRiskRefID(int riskRefID) {
		this.riskRefID = riskRefID;
	}



	public String getCaseID() {
		return caseID;
	}



	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}



	public t_JustifyReplyStatus getJustifyReplyStatus() {
		return JustifyReplyStatus;
	}



	public void setJustifyReplyStatus(t_JustifyReplyStatus justifyReplyStatus) {
		JustifyReplyStatus = justifyReplyStatus;
	}



	public String getAnswer() {
		return answer;
	}



	public void setAnswer(String answer) {
		this.answer = answer;
	}



	public List<ErrorReason> getMyErrorReasons() {
		return myErrorReasons;
	}



	public void setMyErrorReasons(List<ErrorReason> myErrorReasons) {
		this.myErrorReasons = myErrorReasons;
	}



	public JustifyRiskReply() {
 	 	 myErrorReasons = new ArrayList<ErrorReason>();
 	 	JustifyReplyStatus = JustifyRiskReply.t_JustifyReplyStatus.OK  ;   // tot tegendeel
 	 	this.caseID = "";
 	}

 	

	public void registerErrorReason(ErrorReason anError) {
		this.myErrorReasons.add(anError);
		if (anError.getSeverity().equals(ErrorReason.t_ErrorReasonSeverity.ERROR)){
		    this.JustifyReplyStatus =  JustifyRiskReply.t_JustifyReplyStatus.ERROR;
		}
	
	}
	

	public void EvalStatus() {
		// TODO Auto-generated method stub
		if ( this.JustifyReplyStatus.equals(AssessRequestReply.assessmentStatusType.ERROR) == false) {
			  this.JustifyReplyStatus =  JustifyRiskReply.t_JustifyReplyStatus.OK;
			
		}
	}
}
