package nl.geospatialAI.Assessment.FactBase;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact_PERC_NON_WATER_PERMABLE_WITHIN_NORM extends Fact {

	@Override
	protected void evaluateFactResult(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply,
			int level, boolean exhaustive) {
		theServerGlobals.log("Evaluatie van feit :" + this.getDisplayName());

		this.resetUsedDataPoints();
		this.clearExplanation();
		this.evaluate(theServerGlobals, theCase, theReply, level, exhaustive);

	}

	private void evaluate(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply, int level,
			boolean exhaustive) {
		DataPoint dp_SurfPane;
		DataPoint dp_SurfTotalNonPerm;

		double surfNonPermValue;
		double surfPaneValue;
		double norm;
		double percNonPerm;
		double percNonPerm_afgerond;

		norm = 25.0;

		dp_SurfPane = theCase.getCaseDataPointByType(theServerGlobals, theReply,
				DataPoint.DP_Type.SURFACE_CALCULATED_DESTINATIONPANE);

		if (dp_SurfPane.hasValue()) {
			this.recordUsedDataPoint(dp_SurfPane);
			surfPaneValue = dp_SurfPane.getConvertedValueDouble();

			theServerGlobals.log(dp_SurfPane.getDataPointType() + " == " + surfPaneValue);

			dp_SurfTotalNonPerm = theCase.getCaseDataPointByType(theServerGlobals, theReply,
					DataPoint.DP_Type.TOTAL_SURFACE_WATER_NON_PERMABLE);
			if (dp_SurfTotalNonPerm.hasValue()) {
				this.recordUsedDataPoint(dp_SurfTotalNonPerm);
				surfNonPermValue = dp_SurfTotalNonPerm.getConvertedValueDouble();
				percNonPerm = (surfNonPermValue / surfPaneValue);
				theServerGlobals.log("CALC PERC NON PERM: " + percNonPerm + "% kleiner : " + ((100.0 - norm) / 100.0));
				percNonPerm_afgerond = theServerGlobals.round((percNonPerm * 100), 2);
				if (percNonPerm <= ((100.0 - norm) / 100.0)) {
					this.setFactResult(Fact.tFactClassificationType.TRUE);
					this.addToExplanation("OK: Percentage aandeel niet watertoelatend deel perceel ["
							+ (percNonPerm_afgerond) + "%] ligt binnen de norm van " + (100 - norm) + "%.");
				} else {
					this.setFactResult(Fact.tFactClassificationType.FALSE);
					this.addToExplanation("NIET OK: Percentage aandeel niet watertoelatend deel perceel ["
							+ (percNonPerm_afgerond) + "%] ligt boven de norm van " + (100 - norm) + "%.");
				}

			}
			else {
				this.needInput = true;
			}
		}
		else {
			this.needInput = true;
		}

	}

}
