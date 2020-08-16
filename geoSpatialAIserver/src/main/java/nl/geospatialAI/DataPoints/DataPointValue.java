package nl.geospatialAI.DataPoints;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint.DP_source;
import nl.geospatialAI.Errorhandling.ErrorReason;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class DataPointValue {

	
	
	int DP_refId;	
	String value = "";
	DP_source valueSource = DP_source.UNKNOWN;
	
	public DP_source getValueSource() {
		return valueSource;
	}
	public void setValueSource(DP_source valueSource) {
		this.valueSource = valueSource;
	}
	public int getDP_refId() {
		return DP_refId;
	}
	public void setDP_refId(int dP_refId) {
		DP_refId = dP_refId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public DataPointValue() {
		System.out.println("CREATING ANSWER FOR ADDITIONAL QUESTION");
	}
	public void registerValueSubmitted(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply) {
		DataPoint theDataPointSubmitted;
		ErrorReason anError;
		String errorString;
		
		theDataPointSubmitted = theCase.GetDataPointByID(this.DP_refId);
	    if (theDataPointSubmitted != null) {
	    	theDataPointSubmitted.changeValue(this.value,theServerGlobals );
	    	theDataPointSubmitted.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
	    }
	    else
	    {
	    
	    	errorString = "No datapoint with id " + this.getDP_refId() + " exists for case " + theCase.getCaseNo();
	    	anError = ErrorReason.createErrorReason_info(ErrorReason.t_ErrorReasonType.NO_DATAPOINT_EXISTS, errorString);
	    	theReply.registerErrorReason(anError);
	    	theServerGlobals.log(errorString);
	    }
		
		
	}

}
