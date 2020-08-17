package nl.geospatialAI.assessObjects;

import java.util.HashMap;
import java.util.List;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class HumanMadeObject {

	public enum tHumanMadeObjectType {
		// detail level 0
		buildingConstruction,

		// detail level 1
		building,

		// detail level 2
		mainBuilding, outBuilding,

		// detail level 3
		commercialBuilding, tradeBuilding, cateringBuilding, officeBuilding, house, residentialBuilding

	}

	private Case myCase;
	tHumanMadeObjectType theHumanMadeObjectType;
	int refId;
	String name;
	public List<DataPoint> dataPoints;
	private HashMap<DataPoint.DP_Type, DataPoint> myDataPointsByType;
	private HashMap<Integer, DataPoint> myDataPointsByID;

	public HumanMadeObject() {

		myDataPointsByType = new HashMap<DataPoint.DP_Type, DataPoint>();
		myDataPointsByID = new HashMap<Integer, DataPoint>();
	}

	public Case getMyCase() {
		return myCase;
	}

	public void setMyCase(Case myCase) {
		this.myCase = myCase;
	}

	public tHumanMadeObjectType getTheHumanMadeObjectType() {
		return theHumanMadeObjectType;
	}

	public void setTheHumanMadeObjectType(tHumanMadeObjectType theHumanMadeObjectType) {
		this.theHumanMadeObjectType = theHumanMadeObjectType;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
			aDP.setStatus(DataPoint.DP_Status.PROVIDED);
			
			if (aDP.getDatapointSource().equals(DataPoint.DP_source.UNKNOWN)) {
				aDP.setDatapointSource(DataPoint.DP_source.USER);
			}
			aDP.initDataPoint(aDP.getDataPointType());
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

	public void addRequestedDataPoint(DataPoint aDP) {
		if (this.GetDataPointByType(aDP.getDataPointType()) == null) {
			this.dataPoints.add(aDP);
			this.myDataPointsByType.put(aDP.getDataPointType(), aDP);
			this.myDataPointsByID.put(aDP.getDP_refId(), aDP);
		}

	}

	public void requestMissingAnswers(ServerGlobals theServerGlobals, AssessRequestReply theSubmitReply) {
		// TODO Auto-generated method stub

		int i;
		DataPoint aDP;

		for (i = 0; i < this.dataPoints.size(); i++) {
			aDP = this.dataPoints.get(i);
			if (aDP.getStatus().equals(DataPoint.DP_Status.REQUESTED)) {
				if (aDP.isAskable()) {
					theServerGlobals
							.log("DataPoint: " + aDP.getDP_refId() + " was not answered, so will request again");
					theSubmitReply.RequestDataPoint(aDP);
				}
			}
		}

	}

}
