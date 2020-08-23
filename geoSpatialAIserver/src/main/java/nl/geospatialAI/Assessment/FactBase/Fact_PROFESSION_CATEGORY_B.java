package nl.geospatialAI.Assessment.FactBase;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.KVK.KVKdetails;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact_PROFESSION_CATEGORY_B extends Fact {

	@Override
	protected void evaluateFactResult(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply,
			int level, boolean exhaustive) {
		theServerGlobals.log("Evaluatie van feit :" + this.getDisplayName());

		this.resetUsedDataPoints();
		this.clearExplanation();
		this.evaluateValue(theServerGlobals, theCase, theReply, level, exhaustive);

	}

	private void evaluateValue(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply, int level,
			boolean exhaustive) {
		DataPoint dp_registeredKVK;
		DataPoint dp_KVKnumber;
		DataPoint dp_Profession;
		DataPoint dp_SBI;

	

		boolean professionCatB;
		String professionInfo;
		boolean registeredKVK;
	
		String KVKnummer;
		boolean valueKnown;
		String professionValueDisplay;
		KVKdetails theKVKdetails;
		boolean registeredKVKknown;
		// init

		professionCatB = false;
		registeredKVK = false;
		KVKnummer = "";

		valueKnown = false;
		professionInfo = "";
		dp_KVKnumber = null;
		professionValueDisplay = "";
		registeredKVKknown = false;
		// CHECK KVK

		theServerGlobals.log("DEBUG:  ga nu naar KVK vragen");
		dp_registeredKVK = theCase.getCaseDataPointByType(theServerGlobals, theReply,
				DataPoint.DP_Type.REGISTEREDDUTCHKVK);
		if (dp_registeredKVK.hasValue()) {
			registeredKVKknown = true;
			this.recordUsedDataPoint(dp_registeredKVK);
			registeredKVK = dp_registeredKVK.getConvertedValueBoolean();
	
			if (registeredKVK) {
				dp_KVKnumber = theCase.getCaseDataPointByType(theServerGlobals, theReply,
						DataPoint.DP_Type.CHAMBREOFCOMMERCEDOSSIERNUMBER);
				this.addToExplanation(
						"Gegevens uit basisregistratie KVK worden leidend voor feiten over commerciele activiteiten");
				
				if (dp_KVKnumber.hasValue()) {
		
					this.recordUsedDataPoint(dp_KVKnumber);
					KVKnummer = dp_KVKnumber.getConvertedValueString();
					theCase.checkKVKRegister(KVKnummer); // Read KVK
			

				}

			} else {

				this.addToExplanation(
						"Gegevens uit basisregistratie KVK zijn niet voorhanden voor verzamelen feiten over commerciele activiteiten");
			}

		}

		// CHECK PROFESSION DETAILS if no KVK

		if (registeredKVK) {

			theKVKdetails = theCase.getKVKdetails();
			if (theKVKdetails.isReadKVK()) {
				dp_SBI = theCase.getCaseDataPointByType(theServerGlobals, theReply, DataPoint.DP_Type.SBI_ORGANISATION); 
				this.recordUsedDataPoint(dp_SBI);
				if (theKVKdetails.getCodeSBI() == 70221) {
					professionCatB = true;
					valueKnown = true;
					this.addToExplanation("SBI code " + theKVKdetails.getCodeSBI() + " (" + theKVKdetails.getSBIname()
							+ ") van organisatie " + theKVKdetails.getCompanyName() + " [KVK:"
							+ theKVKdetails.getDossierNumber() + "] wordt gerekend tot beroepen in categorie B");
				} else {
					professionCatB = false;
					valueKnown = true;
					
					this.addToExplanation("SBI code " + theKVKdetails.getCodeSBI() + " (" + theKVKdetails.getSBIname()
							+ ") van organisatie " + theKVKdetails.getCompanyName() + " [KVK:"
							+ theKVKdetails.getDossierNumber() + "] wordt NIET gerekend tot beroepen in categorie B");
				}
			}
		}

		if (registeredKVK == false) {
			if (registeredKVKknown) {
				dp_Profession = theCase.getCaseDataPointByType(theServerGlobals, theReply,
						DataPoint.DP_Type.PROFESSION_AT_HOME);
				if (dp_Profession.hasValue()) {
					this.recordUsedDataPoint(dp_Profession);
					professionInfo = dp_Profession.getConvertedValueString();
					if (professionInfo.equals("OTHER")) {
						professionCatB = false;
						valueKnown = true;
						this.addToExplanation("Beroep valt niet in categorie B");
					} else {
						professionCatB = true;
						valueKnown = true;
						professionValueDisplay = dp_Profession.getDisplayValueFor(professionInfo);
						this.addToExplanation(
								"Beroep " + professionValueDisplay + " wordt gerekend tot beroepen in categorie B");
					}
				}
			}
		}

		if (valueKnown) {
			if (professionCatB == true) {

				this.setFactResult(Fact.tFactClassificationType.TRUE);
			} else if (professionCatB == false) {
				this.setFactResult(Fact.tFactClassificationType.FALSE);
			}

		} else {
			this.needInput = true;
		}

	}

}
