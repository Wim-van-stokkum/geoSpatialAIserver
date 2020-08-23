package nl.geospatialAI.beans;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.Errorhandling.ErrorReason;
import nl.geospatialAI.beans.AssessRequestContext.tUsertype;

public class DerivedDataPointsReply {

	public enum t_DerivedReplyStatus {
		ERROR, OK
	}

	int referenceID;
	private tUsertype usertype;
	public String caseID;
	public t_DerivedReplyStatus replyStatus;
	public List<DataPoint> derivedDataPoints;
	public List<ErrorReason> myErrorReasons;

	
	public DerivedDataPointsReply() {
	   this.caseID = "";
	   this.usertype = AssessRequestContext.tUsertype.AANVRAGER;
	   this.replyStatus = t_DerivedReplyStatus.OK;
	   this.derivedDataPoints = new ArrayList <DataPoint>();
	   this.myErrorReasons = new ArrayList <ErrorReason>();
	}


	public int getReferenceID() {
		return referenceID;
	}


	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}


	public String getCaseID() {
		return caseID;
	}


	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}


	public tUsertype getUsertype() {
		return usertype;
	}

	
	

	public void setUsertype(tUsertype usertype) {
		this.usertype = usertype;
	}


	public List<DataPoint> getDerivedDataPoints() {
		return derivedDataPoints;
	}


	public void setDerivedDataPoints(List<DataPoint> derivedDataPoints) {
		this.derivedDataPoints = derivedDataPoints;
	}



	
	public void registerErrorReason(ErrorReason anError) {
		this.myErrorReasons.add(anError);
		if (anError.getSeverity().equals(ErrorReason.t_ErrorReasonSeverity.ERROR)){
			this.replyStatus = DerivedDataPointsReply.t_DerivedReplyStatus.ERROR;
		}
	
	}


	public void evalStatus() {
	
		this.replyStatus = DerivedDataPointsReply.t_DerivedReplyStatus.OK;
		if (this.myErrorReasons.size() > 0 ) {
			this.replyStatus = DerivedDataPointsReply.t_DerivedReplyStatus.ERROR;
		}
		
	}


	public void gatherDerivedDataPoints(Case correspondingCase) {
		this.derivedDataPoints.clear();
		
		correspondingCase.gatherDerivedDataPoints(this);
		
		
	}


	public void addDerivedPataPoint(DataPoint aDP) {
	   this.derivedDataPoints.add(aDP);
		
	}
}
