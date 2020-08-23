package nl.geospatialAI.Assessment.FactBase;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact_OBJECT_IS_OFFICE extends Fact {
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
		boolean isOffice;
		boolean valueKnown;

		// init
		isOffice = false;
		valueKnown = false;
		purposeValue = "";

		dp_Purpose_HM_OBJECT = theCase.getCaseDataPointByType(theServerGlobals, theReply,
				DataPoint.DP_Type.PURPOSE_HM_OBJECT);

		if (dp_Purpose_HM_OBJECT.hasValue()) {
			this.recordUsedDataPoint(dp_Purpose_HM_OBJECT);
			purposeValue = dp_Purpose_HM_OBJECT.getConvertedValueString();

			if (purposeValue.equals("KANTOORACTIVITEITEN")) {
				isOffice = true;
				valueKnown = true;
				this.addToExplanation("Activiteit KANTOORACTIVITEITEN  is onderdeel van doel object.");
			} else {
				isOffice = false;
				valueKnown = true;
				this.addToExplanation("Activiteit KANTOORACTIVITEITEN is geen onderdeel van doel object");
			}

		}

		if (valueKnown) {
			if (isOffice == true) {
				this.setFactResult(Fact.tFactClassificationType.TRUE);
			} else if (isOffice == false) {
				this.setFactResult(Fact.tFactClassificationType.FALSE);
			}

		} 	else {
			this.needInput = true;
		}

	}

}
