package nl.geospatialAI.Assessment.FactBase;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact_OBJECT_IS_COMMERCIAL_BUILDING extends Fact {
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
		DataPoint dp_Purpose_BuildingCategorie;

		String purposeValue;
		boolean isCommercial;
		boolean valueKnown;

		// init
		isCommercial = false;
		valueKnown = false;
		purposeValue = "";

		dp_Purpose_BuildingCategorie = theCase.getCaseDataPointByType(theServerGlobals, theReply,
				DataPoint.DP_Type.BUILDINGCATEGORY);

		if (dp_Purpose_BuildingCategorie.hasValue()) {
			this.recordUsedDataPoint(dp_Purpose_BuildingCategorie);
			purposeValue = dp_Purpose_BuildingCategorie.getConvertedValueString();

			if ((purposeValue.equals("BEDRIJFSGEBOUW")) || (purposeValue.equals("HANDELSGEBOUW"))
					|| (purposeValue.equals("KANTOORACTIVITEITEN")) || (purposeValue.equals("HORECAGEBOUW"))
					|| (purposeValue.equals("KANTOORGEBOUW")) || (purposeValue.equals("KANTOORACTIVITEITEN"))) {
				isCommercial = true;
				valueKnown = true;
				this.addToExplanation("Gebouw is ontworpen voor commerciele doeleinden.");
			} else {
				isCommercial = false;
				valueKnown = true;
				this.addToExplanation("Gebouw is niet expliciet ontworpen voor commerciele doeleinden");
			}

		}

		if (valueKnown) {
			if (isCommercial == true) {
				this.setFactResult(Fact.tFactClassificationType.TRUE);
			} else if (isCommercial == false) {
				this.setFactResult(Fact.tFactClassificationType.FALSE);
			}

		}	else {
			this.needInput = true;
		}

	}

}
