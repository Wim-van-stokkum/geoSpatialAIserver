package nl.geospatialAI.Assessment.FactBase;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact_WORK_FROM_HOME extends Fact {
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
		DataPoint dp_Activity_Work_From_Home;


		boolean workFromHome;
		boolean valueKnown;

		// init
	
		valueKnown = false;
		workFromHome = false;

		dp_Activity_Work_From_Home = theCase.getCaseDataPointByType(theServerGlobals, theReply,
				DataPoint.DP_Type.WORK_FROM_HOME);

		if (dp_Activity_Work_From_Home.hasValue()) {
			this.recordUsedDataPoint(dp_Activity_Work_From_Home);
			workFromHome = dp_Activity_Work_From_Home.getConvertedValueBoolean();

			if (workFromHome) {
				valueKnown = true;
				this.addToExplanation("De aanvrager heeft aan vanuit huis te willen (gaan) werken.");
			} else {
			
				valueKnown = true;
				this.addToExplanation("Er zal niet vanuit huis worden gewerkt.");
			}

		}
		

		if (valueKnown) {
			if (workFromHome == true) {
				this.setFactResult(Fact.tFactClassificationType.TRUE);
			} else if (workFromHome == false) {
				this.setFactResult(Fact.tFactClassificationType.FALSE);
			}
			this.needInput = false;
		}
		else {
			this.needInput = true;
		}

	}

}