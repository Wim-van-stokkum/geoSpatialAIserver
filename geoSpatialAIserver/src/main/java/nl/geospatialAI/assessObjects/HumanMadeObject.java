package nl.geospatialAI.assessObjects;

import java.util.List;

import nl.geospatialAI.DataPoints.DataPoint;

enum humanMadeObjectType {
	//detail level 0
	buildingConstruction,
	
	//detail level 1
	building,
	
	//detail level 2
	mainBuilding,
	outBuilding,
	
	//detail level 3
	commercialBuilding,
	tradeBuilding,
	cateringBuilding,
	officeBuilding,
	house,
	residentialBuilding
	
}

public class HumanMadeObject {
	humanMadeObjectType  theHumanMadeObjectType;
	int refId;
	String name;
	private List <DataPoint> dataPoints;
	
	
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
	
}
