package nl.geospatialAI.Assessment.FactBase;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact_OFFICE_HEIGHT_WITHIN_NORM extends Fact {

	@Override
	protected void evaluateFactResult(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply,
			int level, boolean exhaustive) {
		theServerGlobals.log("Evaluatie van feit :" + this.getDisplayName());

		this.resetUsedDataPoints();
         	this.evaluateValue(theServerGlobals, theCase, theReply, level, exhaustive);
        
	}
	
	
	private void evaluateValue(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply, int level,
			boolean exhaustive) {
		DataPoint dp_Purpose_HM_OBJECT_HEIGHT;
		

		Double objHeight, roundedHeight;
		Double norm, roundedNorm;
        boolean withinLimit;
        boolean valueKnown;
        
        //init

        valueKnown = false;
        objHeight = 0.0;
        
        norm = 13.5;
        roundedNorm = theServerGlobals.round(norm, 1);
        roundedHeight= 0.0;
        withinLimit = false;
        		

        dp_Purpose_HM_OBJECT_HEIGHT = theCase.getCaseDataPointByType(theServerGlobals, theReply,
				DataPoint.DP_Type.MEASUREDHEIGHT);

		if (dp_Purpose_HM_OBJECT_HEIGHT.hasValue()) {
			this.recordUsedDataPoint(dp_Purpose_HM_OBJECT_HEIGHT);
			objHeight = dp_Purpose_HM_OBJECT_HEIGHT.getConvertedValueDouble();
			roundedHeight = theServerGlobals.round(objHeight, 1);
			if (objHeight <= norm ){
				withinLimit = true;
				valueKnown = true;
				this.addToExplanation("Hoogte kantoor ["+ roundedHeight+ "] valt binnen norm van: " + roundedNorm + " meter");
			} else {
				withinLimit = false;
				valueKnown = true;
				this.addToExplanation("Hoogte kantoor ["+ roundedHeight+ "] valt BUITEN de norm van: " + roundedNorm + " meter");
			} 
				
		}
		
		if (valueKnown) {
			if (withinLimit == true) {
				 this.setFactResult(Fact.tFactClassificationType.TRUE);
			}
			else
			if (withinLimit == false) {
				 this.setFactResult(Fact.tFactClassificationType.FALSE);
			}
			
		}
		
	}
}
