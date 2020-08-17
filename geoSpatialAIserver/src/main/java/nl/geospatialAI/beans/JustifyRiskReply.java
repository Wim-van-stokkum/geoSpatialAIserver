package nl.geospatialAI.beans;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.Assessment.Risk;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.Errorhandling.ErrorReason;
import nl.geospatialAI.Justification.JustificationRisk;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class JustifyRiskReply {
	
	public enum t_JustifyReplyStatus {
		ERROR, 
		OK
		
	}
	
 	int referenceID;
 	int riskRefID;
	public String caseID;
	public t_JustifyReplyStatus JustifyReplyStatus;
 	
	public JustificationRisk justificationOfRisk;
    
	public List<ErrorReason> myErrorReasons;

    
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



	public void justifyTheRisk(ServerGlobals theServerGlobals, Case correspondingCase, Risk theRisk) {
		
		this.justificationOfRisk = new JustificationRisk(theRisk, theServerGlobals, correspondingCase, this);
		
		
	}
}
