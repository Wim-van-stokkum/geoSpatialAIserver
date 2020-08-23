package nl.geospatialAI.beans;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.Assessment.AssessmentCriterium;
import nl.geospatialAI.Assessment.Risk;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.Errorhandling.ErrorReason;
import nl.geospatialAI.beans.AssessRequestContext.tUsertype;

public class AssessRequestReply {
	
	public enum assessmentStatusType {
		ERROR, 
		PENDING,
		 ASSESSING,
		 REQUESTING_DATAPOINTS,
		 COMPLETED,
		 ARCHIVED
		
	}
 

	

	public int referenceID;
	public AssessRequestContext.tUsertype theUsertype;
	public String caseID;
	public assessmentStatusType assessmentStatus;
	public List <DataPoint> additionalDataPointsRequest;
	public List <Risk> riskAssessmentResults;
	public List <AssessmentCriterium> assessmentCriteria;
	public List<ErrorReason> myErrorReasons;

	
	
	
    public AssessRequestReply() {
    
         riskAssessmentResults = new ArrayList<Risk>();
     	 myErrorReasons = new ArrayList<ErrorReason>();
     	additionalDataPointsRequest = new ArrayList<DataPoint>();
        this.assessmentCriteria = new ArrayList<AssessmentCriterium>();
    	this.assessmentStatus= AssessRequestReply.assessmentStatusType.ASSESSING;
    	this.theUsertype = AssessRequestContext.tUsertype.AANVRAGER;
    }
    
    public void addAssessmentCriterium(AssessmentCriterium anCriterium) {
    	this.assessmentCriteria.add(anCriterium);
    }
    
    
   
    
    
	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public int getReferenceID() {
		return referenceID;
	}
	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}
	public assessmentStatusType getAssessmentStatus() {
		return assessmentStatus;
	}
	public void setAssessmentStatus(assessmentStatusType assessmentStatus) {
		this.assessmentStatus = assessmentStatus;
	}

	public void AddRiskResult(Risk aRisk) {
		this.riskAssessmentResults.add(aRisk);
		
	}

	public void RequestDataPoint(DataPoint DP_toRequest) {
		DP_toRequest.setStatus(DataPoint.DP_Status.REQUESTED);
		
		 if (this.additionalDataPointsRequest == null) {
		    	this.additionalDataPointsRequest = new ArrayList<DataPoint>();
		    }
		this.additionalDataPointsRequest.add(DP_toRequest);
		
	}

	

	public void registerErrorReason(ErrorReason anError) {
		this.myErrorReasons.add(anError);
		if (anError.getSeverity().equals(ErrorReason.t_ErrorReasonSeverity.ERROR)){
		    this.assessmentStatus = AssessRequestReply.assessmentStatusType.ERROR;
		}
	
	}

	public void EvalStatus() {
		// TODO Auto-generated method stub
		if ( this.assessmentStatus.equals(AssessRequestReply.assessmentStatusType.ERROR) == false) {
			if (this.additionalDataPointsRequest.size() > 0 ) {
				 this.assessmentStatus = AssessRequestReply.assessmentStatusType.REQUESTING_DATAPOINTS;
			}
			else {
				 this.assessmentStatus = AssessRequestReply.assessmentStatusType.COMPLETED;
			}
			
		}
	}

	public void setUserType(tUsertype aUserType) {
		// TODO Auto-generated method stub
		this.theUsertype = aUserType;
	}

	public tUsertype getUserType() {
		// TODO Auto-generated method stub
		return this.theUsertype;
	}
}
