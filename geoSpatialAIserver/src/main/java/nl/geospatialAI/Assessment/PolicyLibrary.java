package nl.geospatialAI.Assessment;

import nl.geospatialAI.Assessment.FactBase.Fact_HOUSE_HEIGHT_WITHIN_NORM;
import nl.geospatialAI.Assessment.FactBase.Fact_OBJECT_IS_COMMERCIAL_BUILDING;
import nl.geospatialAI.Assessment.FactBase.Fact_OBJECT_IS_HOUSE;
import nl.geospatialAI.Assessment.FactBase.Fact_OBJECT_IS_OFFICE;
import nl.geospatialAI.Assessment.FactBase.Fact_OFFICE_HEIGHT_WITHIN_NORM;
import nl.geospatialAI.Assessment.FactBase.Fact_PERC_NON_WATER_PERMABLE_WITHIN_NORM;
import nl.geospatialAI.Assessment.FactBase.Fact_PROFESSION_CATEGORY_B;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.DataPoints.derivedDataPoints.DataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE;
import nl.geospatialAI.assessObjects.HumanMadeObject;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class PolicyLibrary {
	private int refID = 1000;

	public int getRefID() {
		return refID;
	}

	public void setRefID(int refID) {
		this.refID = refID;
	}

	public PolicyLibrary() {
		this.setRefID(refID);
		refID = refID + 1;
		System.out.println("CREATING POLICY LIBRARY [" + this.getRefID() + "]");
		System.out.println("----------------------------------------");
		System.out.println();
	}

	// RISK LIBRARY

	public Risk createRisk_WATER_PERMABILITY() {
		Risk newRisk;
		Proof myProof;

		newRisk = new Risk();
		newRisk.setRiskCategory(Risk.tRiskCategoryType.WATER_PERMABILITY);
		newRisk.setDisplayName("Risico: Water doorlatendheid");

		newRisk.setPolicyReference("Artikel 12.1, bestemmingsplan oost");
		newRisk.setMyAssessmentCriterium(AssessmentCriterium.tAssessmentCriteriumCategoryType.WATER);
		myProof = this.createProof_SURFACE_WATER_NON_PERM_ACCEPTABLE(Proof.tProofClassificationType.UNDETERMINED);
		newRisk.addProof(myProof);

		return newRisk;
	}

	public Risk createRisk_HORIZON_POLUTION() {
		Risk newRisk;
		Proof myProof;
		Proof subProof;

		newRisk = new Risk();
		newRisk.setRiskCategory(Risk.tRiskCategoryType.ENVIRONMENTAL);
		newRisk.setDisplayName("Risico: horizon vervuiling");

		newRisk.setPolicyReference("Artikel 14.2, bestemmingsplan oost");
		newRisk.setMyAssessmentCriterium(AssessmentCriterium.tAssessmentCriteriumCategoryType.LIVING_ENVIRONMENT);
		// myProof =
		// this.createProof_UNDER_MAX_HEIGHT(Proof.tProofClassificationType.UNDETERMINED);
		// newRisk.addProof(myProof);

		// TO DO : bring under proof MAX HEIGTH WITH SUBPROOF: justifiy-> subproofs code

		// temp
		subProof = this.createProof_UNDER_MAX_HEIGHT_HOUSE(Proof.tProofClassificationType.UNDETERMINED);
		newRisk.addProof(subProof);
		subProof = this.createProof_UNDER_MAX_HEIGHT_OFFICE(Proof.tProofClassificationType.UNDETERMINED);
		newRisk.addProof(subProof);
		newRisk.evaluateAsOr();

		return newRisk;
	}

	public Risk createRisk_COMMERCIAL_USE() {
		Risk newRisk;
		Proof myProof;
		Proof subProof;

		newRisk = new Risk();
		newRisk.setRiskCategory(Risk.tRiskCategoryType.PURPOSE);
		newRisk.setDisplayName("Risico: onverantwoord commercieel gebruik");

		newRisk.setPolicyReference("Artikel 12.2, bestemmingsplan oost");
		newRisk.setMyAssessmentCriterium(AssessmentCriterium.tAssessmentCriteriumCategoryType.PURPOSE);

		// temp
		subProof = this.createProof_OBJECT_MEANT_FOR_COMMERCIAL_USE(Proof.tProofClassificationType.UNDETERMINED);
		newRisk.addProof(subProof);
		subProof = this.createProof_ALLOWED_PROFFESION_AT_HOUSE(Proof.tProofClassificationType.UNDETERMINED);
		newRisk.addProof(subProof);
		newRisk.evaluateAsOr();

		return newRisk;
	}

	// PROOF LIBRARY
	public Proof createProof_OBJECT_MEANT_FOR_COMMERCIAL_USE(Proof.tProofClassificationType initValue) {
		Proof newProof;
		Fact newFact;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.OBJECT_MEANT_FOR_COMMERCIAL_USE);
		newProof.setDisplayName("Bewijs: object is bestemd voor commerciele activiteiten");

		newProof.setPolicyReference("Artikel 12.25.b, bestemmingsplan oost");

		newFact = createFact_OBJECT_IS_COMMERCIAL_BUILDING(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);
		return newProof;
	}

	public Proof createProof_ALLOWED_PROFFESION_AT_HOUSE(Proof.tProofClassificationType initValue) {
		Proof newProof;
		Fact newFact;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.ALLOWED_PROFFESION_AT_HOUSE);
		newProof.setDisplayName("Bewijs: beroep aan huis is toelaatbaar");

		newProof.setPolicyReference("Artikel 12.5.d, bestemmingsplan oost");

		newFact = createFact_OBJECT_TYPE_IS_HOUSE(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);
		newFact = createFact_PROFESSION_CATEGORY_B(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);
		return newProof;
	}

	public Proof createProof_SURFACE_WATER_NON_PERM_ACCEPTABLE(Proof.tProofClassificationType initValue) {
		Proof newProof;
		Fact newFact;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.SURFACE_WATER_NON_PERM_ACCEPTABLE);
		newProof.setDisplayName("Bewijs: gebied niet waterdoorlatend is acceptable voor perceel");

		newProof.setPolicyReference("Artikel 13.5.a, bestemmingsplan oost");

		newFact = createFact_PERC_NON_WATER_PERMABLE_WITHIN_NORM(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);
		return newProof;
	}

	public Proof createProof_UNDER_MAX_HEIGHT_HOUSE(Proof.tProofClassificationType initValue) {
		Proof newProof;
		Fact newFact;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.UNDER_MAX_HEIGHT);
		newProof.setDisplayName("Bewijs: De maximale hoogte vanaf maaiveld valt binnen norm voor type Woning");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
		newProof.setPolicyReference("Artikel 12.1, bestemmingsplan oost");

		newFact = this.createFact_OBJECT_TYPE_IS_HOUSE(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);

		newFact = this.createFact_HOUSE_HEIGHT_WITHIN_NORM(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);
		newProof.evaluateAsAND();

		return newProof;
	}

	public Proof createProof_UNDER_MAX_HEIGHT_OFFICE(Proof.tProofClassificationType initValue) {
		Proof newProof;
		Fact newFact;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.UNDER_MAX_HEIGHT);
		newProof.setDisplayName("Bewijs: De maximale hoogte vanaf maaiveld valt binnen norm voor type Kantoor");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
		newProof.setPolicyReference("Artikel 12.4, bestemmingsplan oost");

		newFact = this.createFact_OBJECT_TYPE_IS_OFFICE(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);

		newFact = this.createFact_OFFICE_HEIGHT_WITHIN_NORM(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);
		newProof.evaluateAsAND();

		return newProof;
	}

	public Proof createProof_WITHIN_BOUNDARY_BUILD_SURFACE(Proof.tProofClassificationType initValue) {
		Proof newProof;
		Proof subProof;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.WITHIN_BOUNDARY_BUILD_SURFACE);
		newProof.setDisplayName(
				"Bewijs: Het gebied op het perceel dat bebouwd is dient binnen beleidsgrenzen te bevinden");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
		newProof.setPolicyReference("Artikel 12.4, bestemmingsplan oost");

		subProof = this.createProof_SURFACE_FOUNDATION(Proof.tProofClassificationType.POSITIVE);
		newProof.addChildProof(subProof);

		subProof = createProof_SURFACE_ADDITIONAL_BUILDINGS(Proof.tProofClassificationType.NEGATIVE);
		newProof.addChildProof(subProof);

		return newProof;
	}

	//

	public Proof createProof_SURFACE_FOUNDATION(Proof.tProofClassificationType initValue) {
		Proof newProof;
		Fact newFact;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.SURFACE_FOUNDATION);
		newProof.setDisplayName("Bewijs: Footprint oppervlakte van de fundering");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}

		newProof.setPolicyReference("Artikel 12.5.1, bestemmingsplan oost");

		newFact = this.createFact_OBJECT_TYPE_IS_HOUSE(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);
		newFact = this.createFact_PROFESSION_CATEGORY_B(Fact.tFactClassificationType.UNKNOWN);
		newProof.addFacts(newFact);

		return newProof;

	}

	public Proof createProof_SURFACE_ADDITIONAL_BUILDINGS(Proof.tProofClassificationType initValue) {
		Proof newProof;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.SURFACE_ADDITIONAL_BUILDINGS);
		newProof.setDisplayName("Bewijs: Footprint oppervlakte van de bijgebouwen");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
		newProof.setProofResult(Proof.tProofClassificationType.NEGATIVE);
		newProof.setPolicyReference("Artikel 12.6/1, bestemmingsplan oost");
		return newProof;

	}

	// FACT LIBRARY
	public Fact createFact_PERC_NON_WATER_PERMABLE_WITHIN_NORM(Fact.tFactClassificationType initValue) {
		Fact newFact;

		newFact = new Fact_PERC_NON_WATER_PERMABLE_WITHIN_NORM();
		newFact.setFactType(Fact.tFactType.PERC_NON_WATER_PERMABLE_UNDER_NORM);
		newFact.setDisplayName("Feit: Percentage niet water doorlatend gebied van perceel binnen norm");
		newFact.setPolicyReference("Bestemmingplan 15.3 lid c");

		return newFact;

	}

	public Fact createFact_HEIGHT_ABOVE_GREENFIELD(Fact.tFactClassificationType initValue) {
		Fact newFact;

		newFact = new Fact();
		newFact.setFactType(Fact.tFactType.HEIGHT_ABOVE_GREENFIELD);
		newFact.setDisplayName("Feit: hoogte boven maaiveld");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}
		newFact.setFactResult(Fact.tFactClassificationType.TRUE);
		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	public Fact_OBJECT_IS_HOUSE createFact_OBJECT_TYPE_IS_HOUSE(Fact.tFactClassificationType initValue) {
		Fact_OBJECT_IS_HOUSE newFact;

		newFact = new Fact_OBJECT_IS_HOUSE();
		newFact.setFactType(Fact.tFactType.OBJECT_TYPE_IS_HOUSE);
		newFact.setDisplayName("Feit: gebouw gebruikt als woning");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}

		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	public Fact_HOUSE_HEIGHT_WITHIN_NORM createFact_HOUSE_HEIGHT_WITHIN_NORM(Fact.tFactClassificationType initValue) {
		Fact_HOUSE_HEIGHT_WITHIN_NORM newFact;

		newFact = new Fact_HOUSE_HEIGHT_WITHIN_NORM();
		newFact.setFactType(Fact.tFactType.HOUSE_HEIGHT_WITHIN_NORM);
		newFact.setDisplayName("Feit: woning hoogte valt binnen normen beleid.");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}

		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	public Fact createFact_OBJECT_TYPE_IS_OFFICE(Fact.tFactClassificationType initValue) {
		Fact_OBJECT_IS_OFFICE newFact;

		newFact = new Fact_OBJECT_IS_OFFICE();
		newFact.setFactType(Fact.tFactType.OBJECT_TYPE_IS_OFFICE);
		newFact.setDisplayName("Feit: gebouw gebruikt voor kantooractiviteiten");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}

		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	public Fact_OFFICE_HEIGHT_WITHIN_NORM createFact_OFFICE_HEIGHT_WITHIN_NORM(Fact.tFactClassificationType initValue) {
		Fact_OFFICE_HEIGHT_WITHIN_NORM newFact;

		newFact = new Fact_OFFICE_HEIGHT_WITHIN_NORM();
		newFact.setFactType(Fact.tFactType.OFFICE_HEIGHT_WITHIN_NORM);
		newFact.setDisplayName("Feit: kantoorgebouw hoogte valt binnen normen beleid.");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}

		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	public Fact_OBJECT_IS_COMMERCIAL_BUILDING createFact_OBJECT_IS_COMMERCIAL_BUILDING(
			Fact.tFactClassificationType initValue) {
		Fact_OBJECT_IS_COMMERCIAL_BUILDING newFact;

		newFact = new Fact_OBJECT_IS_COMMERCIAL_BUILDING();
		newFact.setFactType(Fact.tFactType.OBJECT_IS_COMMERCIAL_BUILDING);
		newFact.setDisplayName("Feit: object is van nature een commercieel gebouw.");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}

		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	public Fact createFact_PROFESSION_CATEGORY_B(Fact.tFactClassificationType initValue) {
		Fact_PROFESSION_CATEGORY_B newFact;

		newFact = new Fact_PROFESSION_CATEGORY_B();
		newFact.setFactType(Fact.tFactType.PROFESSION_CATEGORY_B);
		newFact.setDisplayName("Feit: beroep valt in category B");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}

		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	// DATAPOINTS
	// =================================================================================================================================

	public DataPoint createDataPoint_PROFESSION_AT_HOME(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {

		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.PROFESSION_AT_HOME);

		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);
		return newDP;

	}

	public DataPoint createDataPoint_PURPOSE_HM_OBJECT(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {

		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.PURPOSE_HM_OBJECT);

		// BIM
		if (theCase.hasBIM()) {

			newDP.setValue(theCase.theBIMfile.getActiviteitType().toString());
			newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
			newDP.setAskable(false);

		}

		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);
		return newDP;

	}

	public DataPoint createDataPoint_BUILDINGCATEGORY(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {

		HumanMadeObject theHM;

		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.BUILDINGCATEGORY);

		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);

		theHM = theCase.getTheHumanMadeObject();
		if (theHM != null) {

			if (theHM.getTheHumanMadeObjectType() != null) {
				if (theHM.getTheHumanMadeObjectType().equals(HumanMadeObject.tHumanMadeObjectType.house)) {
					newDP.setValue("WOONGEBOUW");
					newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
				} else if (theHM.getTheHumanMadeObjectType()
						.equals(HumanMadeObject.tHumanMadeObjectType.officeBuilding)) {
					newDP.setValue("KANTOORGEBOUW");
					newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
				} else if (theHM.getTheHumanMadeObjectType()
						.equals(HumanMadeObject.tHumanMadeObjectType.cateringBuilding)) {
					newDP.setValue("HORECAGEBOUW");
					newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
				} else if (theHM.getTheHumanMadeObjectType()
						.equals(HumanMadeObject.tHumanMadeObjectType.tradeBuilding)) {
					newDP.setValue("HANDELSGEBOUW");
					newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
				}
				if (theHM.getTheHumanMadeObjectType().equals(HumanMadeObject.tHumanMadeObjectType.commercialBuilding)) {
					newDP.setValue("HANDELSGEBOUW");
					newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
				}
			}
			else {
				// BIM
				if (theCase.hasBIM()) {

					newDP.setValue(theCase.theBIMfile.getGebouwType().toString());
					newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
					newDP.setAskable(false);

				}
			}

		}

	
		return newDP;
	}

	public DataPoint createDataPoint_MEASUREDHEIGHT(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;
		double bimHeight;

		newDP = new DataPoint(DataPoint.DP_Type.MEASUREDHEIGHT);
		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);

		// BIM
		if (theCase.hasBIM()) {
			bimHeight = theCase.theBIMfile.getObjectHeight();
			newDP.setValue(String.valueOf(bimHeight));
			newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
			newDP.setAskable(false);

		}
		return newDP;
	}

	public DataPoint createDataPoint_BIMFILEURL(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.BIMFILEURL);

		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);
		return newDP;
	}

	public DataPoint createDataPoint_MAX_WIDTH_OBJECT(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.MAX_WIDTH_OBJECT);

		// BIM
		if (theCase.hasBIM()) {

			newDP.setValue(String.valueOf(theCase.theBIMfile.getObjectWidth()));
			newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
			newDP.setAskable(false);

		}
		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);
		return newDP;
	}

	public DataPoint createDataPoint_MAX_LENGTH_OBJECT(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.MAX_LENGTH_OBJECT);

		// BIM
		if (theCase.hasBIM()) {

			newDP.setValue(String.valueOf(theCase.theBIMfile.getObjectLength()));
			newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
			newDP.setAskable(false);

		}
		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);
		return newDP;
	}

	public DataPoint createDataPoint_SURFACE_CALCULATED_OBJECT(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.SURFACE_CALCULATED_OBJECT);

		// BIM
		if (theCase.hasBIM()) {

			newDP.setValue(String.valueOf(theCase.theBIMfile.getFootPrint()));
			newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
			newDP.setAskable(false);

		}
		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);
		return newDP;
	}

	public DataPoint createDataPoint_MAX_WIDTH_DESTINATIONPANE(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.MAX_WIDTH_DESTINATIONPANE);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);

		return newDP;
	}

	public DataPoint createDataPoint_MAX_LENGTH_DESTINATIONPANE(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.MAX_LENGTH_DESTINATIONPANE);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);

		return newDP;
	}

	public DataPoint createDataPoint_SURFACE_CALCULATED_DESTINATIONPANE(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.SURFACE_CALCULATED_DESTINATIONPANE);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);

		theServiceGlobals.log("Aangemaakt: " + newDP.getDataPointType());

		return newDP;
	}

	public DataPoint createDataPoint_COMMERCIALUSE(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.COMMERCIALUSE);
		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);

		return newDP;
	}

	public DataPoint createDataPoint_BUSINESSACTIVITIES(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.BUSINESSACTIVITIES);

		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);

		return newDP;
	}

	public DataPoint createDataPoint_MAX_WIDTH_GARDEN(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.MAX_WIDTH_GARDEN);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);
		return newDP;
	}

	public DataPoint createDataPoint_MAX_LENGTH_GARDEN(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.MAX_LENGTH_GARDEN);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);

		return newDP;
	}

	public DataPoint createDataPoint_SURFACE_CALCULATED_GARDEN(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.SURFACE_CALCULATED_GARDEN);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);

		// BIM
		if (theCase.hasBIM()) {

			newDP.setValue(String.valueOf(theCase.theBIMfile.getFootPrintGarden()));
			newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
			newDP.setAskable(false);

		}

		return newDP;
	}

	public DataPoint createDataPoint_CHAMBREOFCOMMERCEDOSSIERNUMBER(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.CHAMBREOFCOMMERCEDOSSIERNUMBER);
		theCase.getTheHumanMadeObject().addRequestedDataPoint(newDP);

		return newDP;
	}

	public DataPoint createDataPoint_PERC_WATER_PERM_GARDEN(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.PERC_WATER_PERM_GARDEN);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);
		return newDP;
	}

	public DataPoint createDataPoint_SURFACE_TILES_GARDEN(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.SURFACE_TILES_GARDEN);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);
		return newDP;
	}

	public DataPoint createDataPoint_DESIGN_HAS_GARDEN(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.DESIGN_HAS_GARDEN);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);
		// BIM
		if (theCase.hasBIM()) {

			newDP.setValue(String.valueOf(theCase.theBIMfile.isHasGarden()));
			newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
			newDP.setAskable(false);
		}
		return newDP;
	}

	public DataPoint createDataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {
		DataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE newDP;

		newDP = new DataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE(DataPoint.DP_Type.TOTAL_SURFACE_WATER_NON_PERMABLE);
		theCase.getTheDestinationPane().addRequestedDataPoint(newDP);
		return newDP;
	}

}
