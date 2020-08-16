package nl.geospatialAI.Assessment.FactBase;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact_OBJECT_IS_HOUSE extends Fact {
    
	@Override
	protected void evaluateFactResult(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply, int level, boolean exhaustive) {
        theServerGlobals.log("Evaluatie van feit :"  + this.getDisplayName());
        
      
		DataPoint purpose;
		purpose = theCase.getCaseDataPointByType(theServerGlobals, theReply, DataPoint.DP_Type.BIM_PURPOSE_HM_OBJECT);
		if ( purpose.getStatus() != DataPoint.DP_Status.REQUESTED) {
			// There is a value to evaluate
			
		}
		
	}
	
}
