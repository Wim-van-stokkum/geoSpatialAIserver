package nl.geospatialAI.DataPoints.derivedDataPoints;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class DataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE extends DataPoint {

	public DataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE() {
		super( );
	}
	
	public DataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE(DataPoint.DP_Type aSpecificType){
		super( aSpecificType);
	}
	
	@Override
	public void deriveValue(ServerGlobals theServiceGlobals, AssessRequestReply theReply, Case theCase) {
	
		
		DataPoint DP_surfaceObject;
		DataPoint DP_surfaceGarden;
		DataPoint DP_surfaceGardenTiled;
		DataPoint DP_hasGarden;
		
		double surfObject;
	
		double surfTiled;
		double total;
		boolean succesA, succesB;
		
		
		theServiceGlobals.log ("Determine DERIVED value for: " + this.getDataPointType() + 
				" ["+ this.getDP_refId() + "]");
		
		succesA = false;
		succesB = false;
		total = 0.0;
        this.resetUsedDataPoints();
        
		// Get my Datapoints
        this.setDatapointSource(DataPoint.DP_source.RULE_ENGINE);
		DP_surfaceObject = theCase.getCaseDataPointByType(theServiceGlobals, theReply,
				DataPoint.DP_Type.SURFACE_CALCULATED_OBJECT);
		if (DP_surfaceObject.hasValue()) {
			this.recordUsedDataPoint(DP_surfaceObject);
			surfObject = DP_surfaceObject.getConvertedValueDouble();
			total = surfObject;
			succesA = true;
		}
		DP_hasGarden = theCase.getCaseDataPointByType(theServiceGlobals, theReply, DataPoint.DP_Type.DESIGN_HAS_GARDEN);
		if (DP_hasGarden.hasValue()) {
			this.recordUsedDataPoint(DP_hasGarden);
			theServiceGlobals.log("HEEFT TUIN : "  + DP_hasGarden.getConvertedValueBoolean());
			if (DP_hasGarden.getConvertedValueBoolean()) {
			
				DP_surfaceGarden = theCase.getCaseDataPointByType(theServiceGlobals, theReply,
						DataPoint.DP_Type.SURFACE_CALCULATED_GARDEN);
				this.recordUsedDataPoint(DP_surfaceGarden);
				DP_surfaceGardenTiled = theCase.getCaseDataPointByType(theServiceGlobals, theReply,
						DataPoint.DP_Type.SURFACE_TILES_GARDEN);
				if (DP_surfaceGardenTiled.hasValue()) {
					this.recordUsedDataPoint(DP_surfaceGardenTiled);
					surfTiled = DP_surfaceGardenTiled.getConvertedValueDouble();
					total = total + surfTiled;
					succesB = true;
				} else {
					succesB = true;
				}
			}
		}

		if (succesA && succesB) {
			this.setValue(String.valueOf(total));
		}
	}

}
