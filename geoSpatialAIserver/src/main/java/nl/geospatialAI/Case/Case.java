package nl.geospatialAI.Case;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.geospatialAI.Assessment.PolicyLibrary;
import nl.geospatialAI.Assessment.Risk;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.assessObjects.DestinationPane;
import nl.geospatialAI.assessObjects.HumanMadeObject;
import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestContext;
import nl.geospatialAI.caseContext.Location;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Case {
	static int c_CaseNo = 10000;
	private int caseNo;
	private String caseID = "anomynous";
	private Location contextLocation;
	private HumanMadeObject theHumanMadeObject;
	private DestinationPane theDestinationPane;

	private HashMap<Integer, DataPoint> allDataPointsByID;
	private HashMap<DataPoint.DP_Type, DataPoint> allDataPointsByType;

	public enum tCaseType {
		OBJECTPLANNING_SCENARIO, OBJECTMUTATION_SCENARIO, OBJECTPLANNING_REQUEST, OBJECTMUTATION_REQUEST
	}

	private boolean formalCase = false;

	private List<DataPoint> requestedDataPoints;
	private List<Risk> myRisks;

	public Case() {
		// define unique identifier
		c_CaseNo = c_CaseNo + 1;
		this.caseID = "Case: " + c_CaseNo;
		this.caseNo= c_CaseNo;

		// index to datapoints
		allDataPointsByType = new HashMap<DataPoint.DP_Type, DataPoint>();
		allDataPointsByID = new HashMap<Integer, DataPoint>();
		requestedDataPoints = new ArrayList<DataPoint>();

		// initialize risks
		myRisks = new ArrayList<Risk>();

	}

	public void initCaseByRequest(AssessRequest aRequest, ServerGlobals theServerGlobals) {
		AssessRequestContext theContext;

		// copy the request body objects
		this.setContextLocation(aRequest.getContextLocation());
		this.setTheDestinationPane(aRequest.getTheDestinationPane());
		this.setTheHumanMadeObject(aRequest.getTheHumanMadeObject());

		// handle the context
		if (aRequest.getTheContext().getScenario() == AssessRequestContext.tScenarioType.formalRequest) {
			if (this.getContextLocation().getCity() != null) {
				this.setCaseID( this.getContextLocation().getCity() + "_case_000" + this.getCaseNo());
			} else {
				this.setCaseID( "case_900" + this.getCaseNo());
			}

			this.formalCase = true;
		} else {
			this.formalCase = false;
			this.setCaseID( "exploration_session_000" + this.getCaseNo());
		}

		this.getTheHumanMadeObject().indexDataPoints(this,theServerGlobals );
		this.getTheDestinationPane().indexDataPoints(this, theServerGlobals);

	}

	public Location getContextLocation() {
		return contextLocation;
	}

	public void setContextLocation(Location contextLocation) {
		this.contextLocation = contextLocation;
	}

	public HumanMadeObject getTheHumanMadeObject() {
		return theHumanMadeObject;
	}

	public void setTheHumanMadeObject(HumanMadeObject theHumanMadeObject) {
		this.theHumanMadeObject = theHumanMadeObject;
	}

	public DestinationPane getTheDestinationPane() {
		return theDestinationPane;
	}

	public void setTheDestinationPane(DestinationPane theDestinationPane) {
		this.theDestinationPane = theDestinationPane;
	}

	public int getCaseNo() {
		return caseNo;
	}

	public  void setCaseNo(int aCaseNo) {
		caseNo = aCaseNo;
	}

	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public void index_a_Datapoint(DataPoint aDP) {
		if (aDP != null) {
			this.allDataPointsByType.put(aDP.getDataPointType(), aDP);
			this.allDataPointsByID.put(aDP.getDP_refId(), aDP);
		}

	}

	public DataPoint GetDataPointByID(int aDP_refID) {

		return this.allDataPointsByID.get(aDP_refID);

	}

	public DataPoint GetDataPointByType(DataPoint.DP_Type aDP_Type) {

		return this.allDataPointsByType.get(aDP_Type);

	}

	public void determinePolicyForContext(PolicyLibrary thePolicyLibrary) {
		   Risk risk1;
		 
		   risk1 = new Risk();
		   this.myRisks.add(risk1);
	        risk1.setDisplayName("Risico op verkeersoverlast");
	        risk1.setRiskCategory(Risk.tRiskCategoryType.LIVING_ENVIRONMENT);
	        risk1.setPolicyReference("Art 4.5a van Bestemmingsplan");
	        risk1.createTestStub1(thePolicyLibrary);
	   
		
	}

	public void startFirstAssessment(ServerGlobals theServerGlobals) {
        int i;
        Risk aRisk;
        
        for (i = 0 ; i < this.myRisks.size(); i++) {
        	aRisk = this.myRisks.get(i);
        	aRisk.assessRisk(theServerGlobals, false);// exhaustive uit
        }
	
	}
	
	

}
