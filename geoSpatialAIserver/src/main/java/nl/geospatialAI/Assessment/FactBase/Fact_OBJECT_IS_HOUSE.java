package nl.geospatialAI.Assessment.FactBase;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact_OBJECT_IS_HOUSE extends Fact {

	@Override
	protected void evaluateFactResult(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply,
			int level, boolean exhaustive) {
		theServerGlobals.log("Evaluatie van feit :" + this.getDisplayName());

		this.resetUsedDataPoints();
		this.clearExplanation();

		this.evaluateValue(theServerGlobals, theCase, theReply, level, exhaustive);

	}

	private void evaluateValue(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply, int level,
			boolean exhaustive) {
		DataPoint dp_Purpose_HM_OBJECT;

		String purposeValue;
		boolean isHouse;
		boolean valueKnown;

		// init
		isHouse = false;
		valueKnown = false;
		purposeValue = "";

		dp_Purpose_HM_OBJECT = theCase.getCaseDataPointByType(theServerGlobals, theReply,
				DataPoint.DP_Type.PURPOSE_HM_OBJECT);

		if (dp_Purpose_HM_OBJECT.hasValue()) {
			this.recordUsedDataPoint(dp_Purpose_HM_OBJECT);
			purposeValue = dp_Purpose_HM_OBJECT.getConvertedValueString();

			if (purposeValue.equals("WONEN")) {
				isHouse = true;
				valueKnown = true;
				this.addToExplanation("Er zal inderdaad gewoond gaan worden in het gebouw.");
			} else {
				isHouse = false;
				valueKnown = true;
				this.addToExplanation("Er zal niet gewoond gaan worden in het gebouw.");
			}

		}
		

		if (valueKnown) {
			if (isHouse == true) {
				this.setFactResult(Fact.tFactClassificationType.TRUE);
			} else if (isHouse == false) {
				this.setFactResult(Fact.tFactClassificationType.FALSE);
			}

		}
		else {
			this.needInput = true;
		}

	}

}
