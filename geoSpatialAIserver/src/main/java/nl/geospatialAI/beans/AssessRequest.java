package nl.geospatialAI.beans;

import nl.geospatialAI.assessObjects.DestinationPane;
import nl.geospatialAI.assessObjects.HumanMadeObject;
import nl.geospatialAI.caseContext.Location;

public class AssessRequest {
    private AssessRequestContext theContext;
	private Location contextLocation;
	private HumanMadeObject theHumanMadeObject;
	private DestinationPane theDestinationPane;
	
	
	public DestinationPane getTheDestinationPane() {
		return theDestinationPane;
	}

	
	public void setTheDestinationPane(DestinationPane theDestinationPane) {
		this.theDestinationPane = theDestinationPane;
	}

    
    public HumanMadeObject getTheHumanMadeObject() {
		return theHumanMadeObject;
	}

	public void setTheHumanMadeObject(HumanMadeObject theHumanMadeObject) {
		this.theHumanMadeObject = theHumanMadeObject;
	}

	public Location getContextLocation() {
		return contextLocation;
	}

	public void setContextLocation(Location contextLocation) {
		this.contextLocation = contextLocation;
	}



	public AssessRequestContext getTheContext() {
		return theContext;
	}

	public void setTheContext(AssessRequestContext theContext) {
		this.theContext = theContext;
	}
    
    
}
