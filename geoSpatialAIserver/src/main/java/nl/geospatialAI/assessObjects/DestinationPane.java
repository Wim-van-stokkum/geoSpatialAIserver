package nl.geospatialAI.assessObjects;

import java.util.HashMap;
import java.util.List;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.serverGlobals.ServerGlobals;

enum destinationPaneType {
	plot, buildingSite, lot
}

public class DestinationPane {
	private destinationPaneType theDestinationPaneType;
	private String name;
	private int refId;
	public List<DataPoint> dataPoints;
	private HashMap<DataPoint.DP_Type, DataPoint> myDataPointsByType;
	private HashMap<Integer, DataPoint> myDataPointsByID;

	public DestinationPane() {
		myDataPointsByType = new HashMap<DataPoint.DP_Type, DataPoint>();
		myDataPointsByID = new HashMap<Integer, DataPoint>();
	}

	public List<DataPoint> getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(List<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}

	public destinationPaneType getTheDestinationPaneType() {
		return theDestinationPaneType;
	}

	public void setTheDestinationPaneType(destinationPaneType theDestinationPaneType) {
		this.theDestinationPaneType = theDestinationPaneType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	
	public void indexDataPoints(Case theCase, ServerGlobals theServerGlobals) {
		int i;
		DataPoint aDP;

		theServerGlobals.log("Datapoints in HM object (" + this.name + ") : " + this.dataPoints.size());

		for (i = 0; i < this.dataPoints.size(); i++) {
			aDP = this.dataPoints.get(i);
			this.myDataPointsByType.put(aDP.getDataPointType(), aDP);
			this.myDataPointsByID.put(aDP.getDP_refId(), aDP);
			theCase.index_a_Datapoint(aDP);

			theServerGlobals.log("Registreren HM  datapoint " + aDP.getDataPointType() + " [" + aDP.getDP_refId()
					+ "] value = " + aDP.getValue());

		}

	}

	public DataPoint GetDataPointByID(int aDP_refID) {

		return this.myDataPointsByID.get(aDP_refID);

	}

	public DataPoint GetDataPointByType(DataPoint.DP_Type aDP_Type) {

		return this.myDataPointsByType.get(aDP_Type);

	}

}
