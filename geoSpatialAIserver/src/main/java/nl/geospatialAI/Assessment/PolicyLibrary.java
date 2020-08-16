package nl.geospatialAI.Assessment;

import nl.geospatialAI.Assessment.FactBase.Fact_OBJECT_IS_HOUSE;
import nl.geospatialAI.Assessment.FactBase.Fact_PERC_NON_WATER_PERMABLE_WITHIN_NORM;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.AllowedValue;
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

		myProof = this.createProof_SURFACE_WATER_NON_PERM_ACCEPTABLE(Proof.tProofClassificationType.UNDETERMINED);
		newRisk.addProof(myProof);

		return newRisk;
	}

	// PROOF LIBRARY
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

	public Proof createProof_UNDER_MAX_HEIGHT(Proof.tProofClassificationType initValue) {
		Proof newProof;

		newProof = new Proof();
		newProof.setProofCategory(Proof.tProofCategoryType.UNDER_MAX_HEIGHT);
		newProof.setDisplayName("Bewijs: De hoogte vanaf maaiveld tot nok dient binnen de grenzen te vallen");
		if (initValue.equals(Proof.tProofClassificationType.UNDETERMINED) == false) {
			newProof.setProofResult(initValue);
		}
		newProof.setPolicyReference("Artikel 12.1, bestemmingsplan oost");
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
		newFact.setDisplayName("Feit: gebouw is een huis");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}
		newFact.setFactResult(Fact.tFactClassificationType.TRUE);
		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	public Fact createFact_PROFESSION_CATEGORY_B(Fact.tFactClassificationType initValue) {
		Fact newFact;

		newFact = new Fact();
		newFact.setFactType(Fact.tFactType.PROFESSION_CATEGORY_B);
		newFact.setDisplayName("Feit: beroep valt in category B");
		if (initValue.equals(Fact.tFactClassificationType.UNKNOWN) == false) {
			newFact.setFactResult(initValue);
		}
		newFact.setFactResult(Fact.tFactClassificationType.TRUE);
		newFact.setPolicyReference("Definities, bestemmingsplan oost");
		return newFact;

	}

	// DATAPOINTS
	// =================================================================================================================================

	public DataPoint createDataPoint_BIM_PURPOSE_HM_OBJECT(Case theCase, ServerGlobals theServiceGlobals,
			AssessRequestReply theReply) {

		DataPoint newDP;

		newDP = new DataPoint(DataPoint.DP_Type.BIM_PURPOSE_HM_OBJECT);

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
			if (theHM.getTheHumanMadeObjectType().equals(HumanMadeObject.tHumanMadeObjectType.house)) {
				newDP.setValue("WOONGEBOUW");
				newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
			} else if (theHM.getTheHumanMadeObjectType().equals(HumanMadeObject.tHumanMadeObjectType.officeBuilding)) {
				newDP.setValue("KANTOORGEBOUW");
				newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
			} else if (theHM.getTheHumanMadeObjectType()
					.equals(HumanMadeObject.tHumanMadeObjectType.cateringBuilding)) {
				newDP.setValue("HORECAGEBOUW");
				newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
			} else if (theHM.getTheHumanMadeObjectType().equals(HumanMadeObject.tHumanMadeObjectType.tradeBuilding)) {
				newDP.setValue("HANDELSGEBOUW");
				newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
			}
			if (theHM.getTheHumanMadeObjectType().equals(HumanMadeObject.tHumanMadeObjectType.commercialBuilding)) {
				newDP.setValue("HANDELSGEBOUW");
				newDP.setDatapointSource(DataPoint.DP_source.DIGITAL_TWIN);
			}

		}

		// BIM
		if (theCase.hasBIM()) {

			newDP.setValue(theCase.theBIMfile.getGebouwType().toString());
			newDP.setDatapointSource(DataPoint.DP_source.DESIGN_FILE);
			newDP.setAskable(false);

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
		AllowedValue newValue;

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
