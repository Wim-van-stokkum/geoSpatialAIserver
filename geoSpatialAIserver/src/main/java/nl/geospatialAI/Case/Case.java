package nl.geospatialAI.Case;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.geospatialAI.Assessment.ApplicablePolicy;
import nl.geospatialAI.Assessment.AssessmentCriterium;
import nl.geospatialAI.Assessment.PolicyLibrary;
import nl.geospatialAI.Assessment.Risk;
import nl.geospatialAI.BIM.BIMfile;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.KVK.KVKdetails;
import nl.geospatialAI.assessObjects.DestinationPane;
import nl.geospatialAI.assessObjects.HumanMadeObject;
import nl.geospatialAI.beans.AssessRequest;
import nl.geospatialAI.beans.AssessRequestContext;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.beans.DerivedDataPointsReply;
import nl.geospatialAI.caseContext.Location;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Case {
	static int c_CaseNo = 10000;
	static int assessCrit_refID = 499;
	private int refID_crit_PURPOSE;
	private int refID_crit_LIVING_ENVIRONMENT;
	private int refID_crit_WATER;

	private int caseNo;
	private String caseID = "anomynous";
	private Location contextLocation;
	private HumanMadeObject theHumanMadeObject;
	private DestinationPane theDestinationPane;

	private HashMap<Integer, DataPoint> allDataPointsByID;
	private HashMap<DataPoint.DP_Type, DataPoint> allDataPointsByType;
	public BIMfile theBIMfile;
	private KVKdetails theKVKdetails;
	public ApplicablePolicy thePolicy;
	public boolean formalCase;

	public enum tCaseType {
		OBJECTPLANNING_SCENARIO, OBJECTMUTATION_SCENARIO, OBJECTPLANNING_REQUEST, OBJECTMUTATION_REQUEST
	}

	private List<Risk> myRisks;

	public Case() {
		// define unique identifier
		c_CaseNo = c_CaseNo + 1;
		this.caseID = "Case: " + c_CaseNo;
		this.caseNo = c_CaseNo;

		assessCrit_refID = assessCrit_refID + 1;
		refID_crit_PURPOSE = assessCrit_refID;

		assessCrit_refID = assessCrit_refID + 1;
		refID_crit_LIVING_ENVIRONMENT = assessCrit_refID;

		assessCrit_refID = assessCrit_refID + 1;
		refID_crit_WATER = assessCrit_refID;

		// index to datapoints
		allDataPointsByType = new HashMap<DataPoint.DP_Type, DataPoint>();
		allDataPointsByID = new HashMap<Integer, DataPoint>();

		theKVKdetails = new KVKdetails();

		// initialize risks
		myRisks = new ArrayList<Risk>();
		this.theBIMfile = new BIMfile();
		this.formalCase = false;
		this.thePolicy = new ApplicablePolicy();

	}
	
	public double GetNormMaxHouse() {
		return this.thePolicy.getNorm_max_height_house();
	}
	
	public double GetNormMaxOffice() {
		return this.thePolicy.getNorm_max_height_office();
	}
	
	public double GetNormPercWaterPermable() {
		return this.thePolicy.getPerc_water_permable();
	}
	
	
	public boolean GetNormWorkHomeAllowed() {
		return this.thePolicy.isWork_home_allowed();
	}
	

	
	public void setPolicyForZipcode(String aZipCode) {
		this.thePolicy.setPolicyCentrum();
		
	}

	public void initCaseByRequest(AssessRequest aRequest, ServerGlobals theServerGlobals) {

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
		Risk riskWater;
		Risk horizonPolution;

		Risk commercialUse;

		this.setPolicyForZipcode("todo");
		if (ServerGlobals.getInstance().scenario.equals("ALL")) {
			riskWater = thePolicyLibrary.createRisk_WATER_PERMABILITY();
			this.myRisks.add(riskWater);
			horizonPolution = thePolicyLibrary.createRisk_HORIZON_POLUTION();
			this.myRisks.add(horizonPolution);
			commercialUse = thePolicyLibrary.createRisk_COMMERCIAL_USE();
			this.myRisks.add(commercialUse);
		} else if (ServerGlobals.getInstance().scenario.equals("WATER")) {
			riskWater = thePolicyLibrary.createRisk_WATER_PERMABILITY();
			this.myRisks.add(riskWater);

		} else

		if (ServerGlobals.getInstance().scenario.equals("HORIZON_POLUTION")) {
			horizonPolution = thePolicyLibrary.createRisk_HORIZON_POLUTION();
			this.myRisks.add(horizonPolution);

		} else if (ServerGlobals.getInstance().scenario.equals("COMMERCIAL_USE")) {
			commercialUse = thePolicyLibrary.createRisk_COMMERCIAL_USE();
			this.myRisks.add(commercialUse);

		} else {
			riskWater = thePolicyLibrary.createRisk_WATER_PERMABILITY();
			this.myRisks.add(riskWater);
			horizonPolution = thePolicyLibrary.createRisk_HORIZON_POLUTION();
			this.myRisks.add(horizonPolution);
			commercialUse = thePolicyLibrary.createRisk_COMMERCIAL_USE();
			this.myRisks.add(commercialUse);
		}
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

		} else

		if (theType.equals(DataPoint.DP_Type.SURFACE_CALCULATED_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_CALCULATED_GARDEN(this,
					theServiceGlobals, theReply);

		}

		else

		if (theType.equals(DataPoint.DP_Type.SURFACE_TILES_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_TILES_GARDEN(this, theServiceGlobals,
					theReply);

		}

		else

		if (theType.equals(DataPoint.DP_Type.SURFACE_CALCULATED_OBJECT)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_CALCULATED_OBJECT(this,
					theServiceGlobals, theReply);

		}

		else

		if (theType.equals(DataPoint.DP_Type.SURFACE_TILES_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SURFACE_TILES_GARDEN(this, theServiceGlobals,
					theReply);
		} else

		if (theType.equals(DataPoint.DP_Type.TOTAL_SURFACE_WATER_NON_PERMABLE)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE(this,
					theServiceGlobals, theReply);

		} else

		if (theType.equals(DataPoint.DP_Type.BIMFILEURL)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_BIMFILEURL(this, theServiceGlobals, theReply);

		} else

		if (theType.equals(DataPoint.DP_Type.DESIGN_HAS_GARDEN)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_DESIGN_HAS_GARDEN(this, theServiceGlobals,
					theReply);

		} else if (theType.equals(DataPoint.DP_Type.PURPOSE_HM_OBJECT)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_PURPOSE_HM_OBJECT(this, theServiceGlobals,
					theReply);

		} else

		if (theType.equals(DataPoint.DP_Type.MEASUREDHEIGHT)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_MEASUREDHEIGHT(this, theServiceGlobals,
					theReply);

		}

		else

		if (theType.equals(DataPoint.DP_Type.PROFESSION_AT_HOME)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_PROFESSION_AT_HOME(this, theServiceGlobals,
					theReply);

		}

		else

		if (theType.equals(DataPoint.DP_Type.SBI_ORGANISATION)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_SBI_ORGANISATION(this, theServiceGlobals,
					theReply);

		}

		else if (theType.equals(DataPoint.DP_Type.BUILDINGCATEGORY)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_BUILDINGCATEGORY(this, theServiceGlobals,
					theReply);

		} else

		if (theType.equals(DataPoint.DP_Type.REGISTEREDDUTCHKVK)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_REGISTEREDDUTCHKVK(this, theServiceGlobals,
					theReply);

		}

		else

		if (theType.equals(DataPoint.DP_Type.CHAMBREOFCOMMERCEDOSSIERNUMBER)) {
			newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_CHAMBREOFCOMMERCEDOSSIERNUMBER(this,
					theServiceGlobals, theReply);

		}

		else

			if (theType.equals(DataPoint.DP_Type.WORK_FROM_HOME)) {
				newDP = theServiceGlobals.getPolicyLibrary().createDataPoint_WORK_AT_HOME(this, theServiceGlobals, theReply);

			}

		else
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

		this.theDestinationPane.requestMissingAnswers(theServerGlobals, theSubmitReply);
		this.theHumanMadeObject.requestMissingAnswers(theServerGlobals, theSubmitReply);

	}

	@JsonIgnore
	public boolean HandleBIMFile(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply) {

		DataPoint dp_BIM_filename;

		boolean BIMfile_statusknown;

		String BIM_fileName;

		BIMfile_statusknown = false;
		if (this.theBIMfile.getActive() == false) {
			theServerGlobals.log("DEBUG:BIM NIET actief");
			// Check filename
			dp_BIM_filename = this.CheckExistsDataPointByType(theServerGlobals, theReply, DataPoint.DP_Type.BIMFILEURL);
			if (dp_BIM_filename != null) {
				theServerGlobals.log("DEBUG:dp_BIM_filename not null");

				if (dp_BIM_filename.hasValue()) {

					this.theBIMfile.setActive(true);
					theServerGlobals.log("DEBUG:dp_BIM_filename HAS VALUE");
					BIM_fileName = dp_BIM_filename.getValue();
					// theServerGlobals.log("FILENAME: " + BIM_fileName);

					BIMfile_statusknown = true;
					this.theBIMfile.setFileName(BIM_fileName);
					this.theBIMfile.processBIMfile(theServerGlobals, theCase);

				}

			} else {
				// request
				if (this.theBIMfile.getFileName().equals("NO_BIM") == false) {

					BIMfile_statusknown = false;
					dp_BIM_filename = this.getCaseDataPointByType(theServerGlobals, theReply,
							DataPoint.DP_Type.BIMFILEURL);
				}
			}

		} else {
			// is al active
			theServerGlobals.log("DEBUG:BIM actief");
			BIMfile_statusknown = true;
			this.theBIMfile.processBIMfile(theServerGlobals, theCase);
		}
		theServerGlobals.log("Sending: " + BIMfile_statusknown);
		return BIMfile_statusknown;
	}

	public boolean hasBIM() {
		boolean bimExist;

		bimExist = false;
		if (this.theBIMfile != null) {
			bimExist = this.theBIMfile.getActive();
		}

		return bimExist;
	}

	public void evaluateAssessmentCriteria(ServerGlobals theServerGlobals, AssessRequestReply theReply) {
		// AssessmentCriteria Water

		this.evaluateAssessmentCriteriaWater(theServerGlobals, theReply);
		this.evaluateAssessmentLivingEnvironment(theServerGlobals, theReply);
		this.evaluateAssessmentPurpose(theServerGlobals, theReply);
	}

	private void evaluateAssessmentCriteriaWater(ServerGlobals theServerGlobals, AssessRequestReply theReply) {
		// AssessmentCriteria Water
		AssessmentCriterium waterCriterium;
		Risk aRisk;
		int i;
		boolean ok;
		int aantalRisk;

		ok = true; // tot tegendeel
		aantalRisk = 0;
		waterCriterium = new AssessmentCriterium();
		waterCriterium.setRefID(this.refID_crit_WATER);
		waterCriterium.setDisplayName("Beoordeling op water aspecten");
		waterCriterium.setExemptionRequestAllowed(false);
		waterCriterium.setCriteriumCategory(AssessmentCriterium.tAssessmentCriteriumCategoryType.WATER);

		for (i = 0; i < this.myRisks.size(); i++) {
			aRisk = this.myRisks.get(i);
			if (aRisk.getMyAssessmentCriterium().equals(waterCriterium.getCriteriumCategory())) {
				if (aRisk.getRiskValue().equals(Risk.tRiskClassificationType.UNDETERMINED) == false) {
					aantalRisk = aantalRisk + 1;
				}
				if ((aRisk.getRiskValue().equals(Risk.tRiskClassificationType.NEUTRAL) == false)
						&& (aRisk.getRiskValue().equals(Risk.tRiskClassificationType.UNDETERMINED) == false)) {
					ok = false;

				}
			}
		}

		if (ok && (aantalRisk > 0)) {
			waterCriterium.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.APPROVED);
		} else if (ok && (aantalRisk == 0)) {
			waterCriterium.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.UNKNOWN);
		} else if (ok == false) {
			waterCriterium.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.UNAPPROVED);
		}
		theReply.addAssessmentCriterium(waterCriterium);
	}

	private void evaluateAssessmentPurpose(ServerGlobals theServerGlobals, AssessRequestReply theReply) {
		// AssessmentCriteria Water
		AssessmentCriterium purposeCriterium;
		Risk aRisk;
		int i;
		boolean ok;
		int aantalRisk;

		ok = true; // tot tegendeel
		aantalRisk = 0;
		purposeCriterium = new AssessmentCriterium();
		purposeCriterium.setRefID(this.refID_crit_PURPOSE);
		purposeCriterium.setDisplayName("Beoordeling op bestemming");
		purposeCriterium.setExemptionRequestAllowed(false);
		purposeCriterium.setCriteriumCategory(AssessmentCriterium.tAssessmentCriteriumCategoryType.PURPOSE);

		for (i = 0; i < this.myRisks.size(); i++) {
			aRisk = this.myRisks.get(i);
			if (aRisk.getMyAssessmentCriterium().equals(purposeCriterium.getCriteriumCategory())) {
				if (aRisk.getRiskValue().equals(Risk.tRiskClassificationType.UNDETERMINED) == false) {
				}
				if ((aRisk.getRiskValue().equals(Risk.tRiskClassificationType.NEUTRAL) == false)
						&& (aRisk.getRiskValue().equals(Risk.tRiskClassificationType.UNDETERMINED) == false)) {
					ok = false;

				}
			}
		}

		if (ok && (aantalRisk > 0)) {
			purposeCriterium.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.APPROVED);
		} else if (ok && (aantalRisk == 0)) {
			purposeCriterium.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.UNKNOWN);
		} else if (ok == false) {
			purposeCriterium.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.UNAPPROVED);
		}
		theReply.addAssessmentCriterium(purposeCriterium);
	}

	private void evaluateAssessmentLivingEnvironment(ServerGlobals theServerGlobals, AssessRequestReply theReply) {
		// AssessmentCriteria Water
		AssessmentCriterium livingEnvironmentCriterium;
		Risk aRisk;
		int i;
		boolean ok;
		int aantalRisk;

		ok = true; // tot tegendeel
		aantalRisk = 0;
		livingEnvironmentCriterium = new AssessmentCriterium();
		livingEnvironmentCriterium.setRefID(this.refID_crit_LIVING_ENVIRONMENT);
		livingEnvironmentCriterium.setDisplayName("Beoordeling op leef omgeving aspecten");
		livingEnvironmentCriterium.setExemptionRequestAllowed(false);
		livingEnvironmentCriterium
				.setCriteriumCategory(AssessmentCriterium.tAssessmentCriteriumCategoryType.LIVING_ENVIRONMENT);

		for (i = 0; i < this.myRisks.size(); i++) {
			aRisk = this.myRisks.get(i);
			if (aRisk.getMyAssessmentCriterium().equals(livingEnvironmentCriterium.getCriteriumCategory())) {
				if (aRisk.getRiskValue().equals(Risk.tRiskClassificationType.UNDETERMINED) == false) {
					aantalRisk = aantalRisk + 1;
				}
				if ((aRisk.getRiskValue().equals(Risk.tRiskClassificationType.NEUTRAL) == false)
						&& (aRisk.getRiskValue().equals(Risk.tRiskClassificationType.UNDETERMINED) == false)) {
					ok = false;

				}
			}
		}

		if (ok && (aantalRisk > 0)) {
			livingEnvironmentCriterium
					.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.APPROVED);
		} else if (ok && (aantalRisk == 0)) {
			livingEnvironmentCriterium
					.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.UNKNOWN);
		} else if (ok == false) {
			livingEnvironmentCriterium
					.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.UNAPPROVED);
		}
		theReply.addAssessmentCriterium(livingEnvironmentCriterium);
	}

	public Risk getRiskByID(int riskRefID) {
		Risk theRisk;
		Risk aRisk;
		int i;

		theRisk = null;
		for (i = 0; i < this.myRisks.size(); i++) {
			aRisk = this.myRisks.get(i);
			if (aRisk.getRefID() == riskRefID) {
				theRisk = aRisk;
			}
		}

		return theRisk;
	}

	public void checkKVKRegister(String aKVKnummer) {
		if (this.theKVKdetails != null) {
			this.theKVKdetails.readKVK(aKVKnummer);

		} else
			ServerGlobals.getInstance().log("ERROR: KVK DETAILS NOT FOUND");

	}

	public KVKdetails getKVKdetails() {
		KVKdetails theDetails;

		theDetails = null;
		if (this.theKVKdetails != null) {
			theDetails = this.theKVKdetails;
		}

		return theDetails;
	}

	public void gatherDerivedDataPoints(DerivedDataPointsReply derivedDataPointsReply) {

		DataPoint aDP;
		boolean isDerived;

		for (Entry<Integer, DataPoint> anEntry : allDataPointsByID.entrySet()) {
			aDP = anEntry.getValue();
			isDerived = false;

			if (       (aDP.getDatapointSource().equals(DataPoint.DP_source.RULE_ENGINE))
					|| (aDP.getDatapointSource().equals(DataPoint.DP_source.DESIGN_FILE))
					|| (aDP.getDatapointSource().equals(DataPoint.DP_source.ADMINISTRATION))
					|| (aDP.getDatapointSource().equals(DataPoint.DP_source.EXTERNAL_FORMAL))
					|| (aDP.getDatapointSource().equals(DataPoint.DP_source.EXTERNAL_INFORMAL)
					|| (aDP.getDatapointSource().equals(DataPoint.DP_source.FORMAL_REGISTRY))
					|| (aDP.getDatapointSource().equals(DataPoint.DP_source.OTHER)))) {
				isDerived = true;
		
			}

			if (isDerived) {
				derivedDataPointsReply.addDerivedPataPoint(aDP);
			}

			System.out.println("refID = " + anEntry.getKey() + ", DataPoint = " + anEntry.getValue().getQuestionText()
					+ ", Source :   " + anEntry.getValue().getDatapointSource() + ", Derived :   " + isDerived);
		}
	}

	public void setForUser(AssessRequestReply theReply) {
		DataPoint aDP;
		AssessRequestContext.tUsertype theUserType;
		
		theUserType = theReply.getUserType();
	
		for (Entry<Integer, DataPoint> anEntry : allDataPointsByID.entrySet()) {
			aDP = anEntry.getValue();
			theUserType= theReply.getUserType();
			aDP.setForUser(theUserType);
		}
		
	}

}
