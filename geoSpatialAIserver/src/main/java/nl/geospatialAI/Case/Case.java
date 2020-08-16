package nl.geospatialAI.Case;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.geospatialAI.Assessment.PolicyLibrary;
import nl.geospatialAI.Assessment.Risk;
import nl.geospatialAI.BIM.BIMfile;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.assessObjects.DestinationPane;
import nl.geospatialAI.assessObjects.HumanMadeObject;
import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestContext;
import nl.geospatialAI.beans.AssessRequestReply;
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
	public BIMfile theBIMfile;

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
		this.caseNo = c_CaseNo;

		// index to datapoints
		allDataPointsByType = new HashMap<DataPoint.DP_Type, DataPoint>();
		allDataPointsByID = new HashMap<Integer, DataPoint>();
		requestedDataPoints = new ArrayList<DataPoint>();

		// initialize risks
		myRisks = new ArrayList<Risk>();
		this.theBIMfile = new BIMfile();

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
				this.setCaseID(this.getContextLocation().getCity() + "_case_000" + this.getCaseNo());
			} else {
				this.setCaseID("case_900" + this.getCaseNo());
			}

			this.formalCase = true;
		} else {
			this.formalCase = false;
			this.setCaseID("exploration_session_000" + this.getCaseNo());
		}

		this.getTheHumanMadeObject().indexDataPoints(this, theServerGlobals);
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

	public void setCaseNo(int aCaseNo) {
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

	public void determinePolicyForContext(PolicyLibrary thePolicyLibrary, AssessRequestReply theReply) {
		Risk risk1;

		risk1 = thePolicyLibrary.createRisk_WATER_PERMABILITY();
		this.myRisks.add(risk1);
	}

	public void addRisksToReply(ServerGlobals theServerGlobals, AssessRequestReply theReply) {
		int i;
		Risk aRisk;

		for (i = 0; i < this.myRisks.size(); i++) {
			aRisk = this.myRisks.get(i);
			theReply.AddRiskResult(aRisk);
		}

	}

	public void startAssessment(ServerGlobals theServerGlobals, AssessRequestReply theReply) {
		int i;
		Risk aRisk;

		for (i = 0; i < this.myRisks.size(); i++) {
			aRisk = this.myRisks.get(i);
			aRisk.assessRisk(theServerGlobals, this, theReply, false);// exhaustive uit
		}

	}

	public DataPoint CheckExistsDataPointByType(ServerGlobals theServiceGlobals, AssessRequestReply theReply,
			DataPoint.DP_Type theType) {

		DataPoint aDP;

		aDP = this.GetDataPointByType(theType);
		if (aDP != null) {
			aDP.deriveValue(theServiceGlobals, theReply, this);
		}

		return aDP;
	}

	public DataPoint getCaseDataPointByType(ServerGlobals theServiceGlobals, AssessRequestReply theReply,
			DataPoint.DP_Type theType) {

		DataPoint aDP;

		aDP = this.GetDataPointByType(theType);
		if (aDP == null) {
			aDP = this.createDataPointByType(theServiceGlobals, theReply, theType);

		}
		aDP.deriveValue(theServiceGlobals, theReply, this);
		return aDP;
	}

	private DataPoint createDataPointByType(ServerGlobals theServiceGlobals, AssessRequestReply theReply,
			DataPoint.DP_Type theType) {

		DataPoint newDP;
		theServiceGlobals.log("Case [" + this.getCaseNo() + "]: Datapoint " + theType + " not available:");

		newDP = null;

		if (theType.equals(DataPoint.DP_Type.SURFACE_CALCULATED_DESTINATIONPANE)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_CALCULATED_DESTINATIONPANE(this,
					theServiceGlobals, theReply);
			this.getTheDestinationPane().addRequestedDataPoint(newDP);
		} else

		if (theType.equals(DataPoint.DP_Type.SURFACE_CALCULATED_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_CALCULATED_GARDEN(this,
					theServiceGlobals, theReply);

			this.getTheDestinationPane().addRequestedDataPoint(newDP);
		}

		else

		if (theType.equals(DataPoint.DP_Type.SURFACE_TILES_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_TILES_GARDEN(this, theServiceGlobals,
					theReply);
			this.getTheDestinationPane().addRequestedDataPoint(newDP);
		}

		else

		if (theType.equals(DataPoint.DP_Type.SURFACE_CALCULATED_OBJECT)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_CALCULATED_OBJECT(this,
					theServiceGlobals, theReply);
			this.getTheHumanMadeObject().addRequestedDataPoint(newDP);
			this.index_a_Datapoint(newDP); // CHECK
		}

		else

		if (theType.equals(DataPoint.DP_Type.SURFACE_TILES_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_TILES_GARDEN(this, theServiceGlobals,
					theReply);
		} else

		if (theType.equals(DataPoint.DP_Type.SURFACE_TILES_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_TILES_GARDEN(this, theServiceGlobals,
					theReply);
			this.getTheDestinationPane().addRequestedDataPoint(newDP);
		}

		else

		if (theType.equals(DataPoint.DP_Type.TOTAL_SURFACE_WATER_NON_PERMABLE)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE(this,
					theServiceGlobals, theReply);
			this.getTheDestinationPane().addRequestedDataPoint(newDP);
		} else

		if (theType.equals(DataPoint.DP_Type.BIMFILEURL)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_BIMFILEURL(this, theServiceGlobals, theReply);
			this.getTheDestinationPane().addRequestedDataPoint(newDP);
		} else

		if (theType.equals(DataPoint.DP_Type.DESIGN_HAS_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_DESIGN_HAS_GARDEN(this, theServiceGlobals,
					theReply);
			this.getTheDestinationPane().addRequestedDataPoint(newDP);
		} else
			theServiceGlobals.log("ERROR CREATING NEW DP: no creation point for type: " + theType);

		// Register stuff
		if (newDP != null) {
			newDP.setStatus(DataPoint.DP_Status.REQUESTED);
			if (newDP.isAskable()) {
				theReply.RequestDataPoint(newDP);
			}
			this.index_a_Datapoint(newDP);

		}
		return newDP;
	}

	public void requestMissingAnswers(ServerGlobals theServerGlobals, AssessRequestReply theSubmitReply) {
		// TODO Auto-generated method stub
		int i;

		this.theDestinationPane.requestMissingAnswers(theServerGlobals, theSubmitReply);
		this.theHumanMadeObject.requestMissingAnswers(theServerGlobals, theSubmitReply);

	}

	@JsonIgnore
	public boolean HandleBIMFile(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply) {

		DataPoint dp_BIM_Available;
		DataPoint dp_BIM_filename;

		boolean chk_BIM_available;

		String BIM_fileName;

		chk_BIM_available = false;
		if (this.theBIMfile.getActive() == false) {
			// Check filename
			dp_BIM_filename = this.CheckExistsDataPointByType(theServerGlobals, theReply, DataPoint.DP_Type.BIMFILEURL);
			if (dp_BIM_filename != null) {

				this.theBIMfile.setActive(true);
				chk_BIM_available = true;
				if (dp_BIM_filename.hasValue()) {

					BIM_fileName = dp_BIM_filename.getConvertedValueString();
					theServerGlobals.log("FILENAME: " + BIM_fileName);
					this.theBIMfile.setFileName(BIM_fileName);
					this.theBIMfile.processBIMfile(theServerGlobals, theCase);
				} else {
					dp_BIM_filename.changeValue("WONING.BIM", theServerGlobals);
					this.theBIMfile.setFileName("WONING.BIM");
					this.theBIMfile.processBIMfile(theServerGlobals, theCase);
				}
			} else {
				// request
				chk_BIM_available = false;
				dp_BIM_filename = this.getCaseDataPointByType(theServerGlobals, theReply, DataPoint.DP_Type.BIMFILEURL);
			}

		} else {
			// is al active
			chk_BIM_available = true;
			this.theBIMfile.processBIMfile(theServerGlobals, theCase);
		}
		return chk_BIM_available;
	}

	public boolean hasBIM() {
		boolean bimExist;
		
		bimExist = false;
		if ( this.theBIMfile != null) {
			bimExist =  this.theBIMfile.getActive();
		}

		return bimExist;
	}



}
