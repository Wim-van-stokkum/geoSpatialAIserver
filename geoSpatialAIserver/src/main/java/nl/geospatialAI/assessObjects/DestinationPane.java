package nl.geospatialAI.assessObjects;

import java.util.List;

import nl.geospatialAI.DataPoints.DataPoint;

enum destinationPaneType {
	plot,
	buildingSite,
	lot
}

public class DestinationPane {
  private destinationPaneType theDestinationPaneType;
  private String name;
  private int refId;
  private List <DataPoint> dataPoints;
  
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
  
  
}
