package nl.geospatialAI.DataPoints.derivedDataPoints;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class DataPoint_BIM_FILE_AVAILABLE  extends DataPoint {

	public DataPoint_BIM_FILE_AVAILABLE() {
		super( );
	}
	
	public DataPoint_BIM_FILE_AVAILABLE(DataPoint.DP_Type aSpecificType){
		super( aSpecificType);
	}
	
	@Override
	public void deriveValue(ServerGlobals theServiceGlobals, AssessRequestReply theReply, Case theCase) {
		DataPoint dp_BIM_FileName;
		
		dp_BIM_FileName = 
				theCase.getCaseDataPointByType(theServiceGlobals, theReply,
						DataPoint.DP_Type.BIMFILEURL);
		if (dp_BIM_FileName.hasValue()) {
			
		}
		
	}
}
