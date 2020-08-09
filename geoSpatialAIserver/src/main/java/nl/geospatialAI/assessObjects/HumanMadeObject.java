package nl.geospatialAI.assessObjects;

import java.util.HashMap;
import java.util.List;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;

enum humanMadeObjectType {
	// detail level 0
	buildingConstruction,

	// detail level 1
	building,

	// detail level 2
	mainBuilding, outBuilding,

	// detail level 3
	commercialBuilding, tradeBuilding, cateringBuilding, officeBuilding, house, residentialBuilding

}

public class HumanMadeObject {
	private Case myCase;
	humanMadeObjectType theHumanMadeObjectType;
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

	public humanMadeObjectType getTheHumanMadeObjectType() {
		return theHumanMadeObjectType;
	}

	public void setTheHumanMadeObjectType(humanMadeObjectType theHumanMadeObjectType) {
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

	public void indexDataPoints(Case theCase, boolean recording) {
		int i;
		DataPoint aDP;

		if (recording) {
			System.out.println("Datapoints in HM object (" + this.name + ") : " + this.dataPoints.size());
		}
		for (i = 0; i < this.dataPoints.size(); i++) {
			aDP = this.dataPoints.get(i);
			this.myDataPointsByType.put(aDP.getDataPointType(), aDP);
			this.myDataPointsByID.put(aDP.getDP_refId(), aDP);
			theCase.index_a_Datapoint(aDP);
			if (recording) {
				System.out.println("Registreren HM  datapoint " + aDP.getDataPointType() + " [" + aDP.getDP_refId()
						+ "] value = " + aDP.getValue());
			}
		}

	}

	public DataPoint GetDataPointByID(int aDP_refID) {

		return this.myDataPointsByID.get(aDP_refID);

	}

	public DataPoint GetDataPointByType(DataPoint.DP_Type aDP_Type) {

		return this.myDataPointsByType.get(aDP_Type);

	}

}
