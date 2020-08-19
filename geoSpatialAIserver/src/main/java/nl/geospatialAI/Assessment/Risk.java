package nl.geospatialAI.Assessment;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.geospatialAI.Assessment.AssessmentCriterium.tAssessmentCriteriumCategoryType;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.Justification.JustificationRisk;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Risk {

	public enum tRiskCategoryType {
		CONSTRUCTION, HEALTH, ENVIRONMENTAL, WATER_PERMABILITY, PURPOSE

		// to be defined in use case
	}

	public enum tRiskClassificationType {
		NEUTRAL, OBSERVATION, INCREASED, UNACCEPTABLE, UNDETERMINED
	}

	public enum tOperandType {
		AND, OR
	}

	static int risk_refID = 199;

	private String displayName;
	private int refID;
	private tRiskCategoryType riskCategory;
	private tRiskClassificationType riskValue;
	private String policyReference;
	private List<Proof> myProofs;
	private boolean aValueSet = false;
	private Proof.tProofClassificationType proofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
    public String explanation;
    private tAssessmentCriteriumCategoryType myAssessmentCriterium;
    
    
    

	public tAssessmentCriteriumCategoryType getMyAssessmentCriterium() {
		return myAssessmentCriterium;
	}

	public void setMyAssessmentCriterium(tAssessmentCriteriumCategoryType myAssessmentCriterium) {
		this.myAssessmentCriterium = myAssessmentCriterium;
	}

	private tOperandType myOperand = Risk.tOperandType.AND;

	public Risk() {

		risk_refID = risk_refID + 1;
		this.setRefID(risk_refID);
	 this.clearExplanation();

		// initialize proofs
		this.myProofs = new ArrayList<Proof>();

	}
	
	private void clearExplanation() {
		this.explanation = "";
	}

	protected void addToExplanation(String aText) {
		if (this.explanation.length() == 0) {
			this.explanation = aText;
		}
		else {
			this.explanation = this.explanation + " " +  aText;
		}
		
	}


	@JsonIgnore
	public String explainYourSelf() {
		return this.explanation;
	}
	
	public String getPolicyReference() {
		return policyReference;
	}

	

	
	
   @JsonIgnore
    public tOperandType getOperand() {
		return myOperand;
	}

	public void evaluateAsOr() {
		this.myOperand = Risk.tOperandType.OR;
	}

	public void evaluateAsAND() {
		this.myOperand = Risk.tOperandType.AND;
	}

	public void setPolicyReference(String policyReference) {
		this.policyReference = policyReference;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getRefID() {
		return refID;
	}

	public void setRefID(int refID) {
		this.refID = refID;
	}

	public tRiskCategoryType getRiskCategory() {
		return riskCategory;
	}

	public void setRiskCategory(tRiskCategoryType riskCategory) {
		this.riskCategory = riskCategory;
	}

	public tRiskClassificationType getRiskValue() {
		return riskValue;
	}

	public void setRiskValue(tRiskClassificationType riskValue) {
		this.riskValue = riskValue;
	}

	public void addProof(Proof aProof) {
		this.myProofs.add(aProof);
	}

	// Risk Evaluatie methods

	private void evalProofasAND(ServerGlobals theServerGlobals,  Case theCase, AssessRequestReply theReply , boolean exhaustive) {
		int i;
		Proof.tProofClassificationType aProofResult;

		// Proof all underlying proofs
		for (i = 0; i < this.myProofs.size(); i++) {
			aProofResult = myProofs.get(i).assessProof(theServerGlobals, theCase, theReply, 1, exhaustive);
			this.addToExplanation(myProofs.get(i).explainYourSelf());
			
			if (aProofResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
				this.proofOverallResult = Proof.tProofClassificationType.NEGATIVE;
				aValueSet = true;
				theServerGlobals.log("Overall impact: evaluatie naar negatief vanwege AND");
				theServerGlobals.log("");

			} else if (aProofResult.equals(Proof.tProofClassificationType.POSITIVE)) {
				// Negative remain Negative : no action
				// Positive remains positive : no action
				// Undetermined, set : no action

				if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (this.aValueSet == false) {
						// flip undetermined first time to positive

						theServerGlobals.log("Overall impact: evaluatie va onbekend naar positief");
						theServerGlobals.log("");

						this.proofOverallResult = Proof.tProofClassificationType.POSITIVE;
						this.aValueSet = true;
					}

				}
			} else if (aProofResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
				// positive becomes undetermind

				if (this.proofOverallResult.equals(Proof.tProofClassificationType.POSITIVE)) {
					this.proofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
					this.aValueSet = true;

					theServerGlobals.log("Overall impact : positief wordt onbekend");
					theServerGlobals.log("");

				}
				if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (aValueSet == false) {

						theServerGlobals.log("Overall impact: fixeer onbekend");
						theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first negative point ?
			if ((exhaustive == false) && (proofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE))
					&& (i < (this.myProofs.size() - 1))) {

				theServerGlobals.log("Stop voortijdig beoordeling van risico " + this.refID
						+ ". Een van onderliggende bewijzen zijn negatief en operand is AND");
				this.clearExplanation();

				break;
			}

		}
	}

	private void evalProofasOR(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply , boolean exhaustive) {
		int i;
		Proof.tProofClassificationType aProofResult;

		// Proof all underlying proofs
		for (i = 0; i < this.myProofs.size(); i++) {
			aProofResult = myProofs.get(i).assessProof(theServerGlobals,theCase, theReply, 1, exhaustive);
			this.addToExplanation(myProofs.get(i).explainYourSelf());
			if (aProofResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
				// Positive stay positive
				// Negative stays negative
				if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED) && (aValueSet = true)) {

					this.proofOverallResult = Proof.tProofClassificationType.NEGATIVE;
					aValueSet = true;
				}

				theServerGlobals.log("Overall impact: evaluatie van onbekend naar negatief");
				theServerGlobals.log("");

			} else if (aProofResult.equals(Proof.tProofClassificationType.POSITIVE)) {
				// Bij OR wordt resultaat POSITIEF: no matter waht the other will be

				theServerGlobals.log("Overall impact : evaluatie naar positief vanwege OR");
				theServerGlobals.log("");

				this.proofOverallResult = Proof.tProofClassificationType.POSITIVE;
				this.aValueSet = true;

			} else if (aProofResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
				// positive stays positive

				if (this.proofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
					this.proofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
					this.aValueSet = true;

					theServerGlobals.log("Overall impact : negatief wordt onbekend");
					theServerGlobals.log("");

				}
				if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (aValueSet == false) {

						theServerGlobals.log("Overall impact: fixeer onbekend");
						theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first poitieve point ?
			if ((exhaustive == false) && (proofOverallResult.equals(Proof.tProofClassificationType.POSITIVE))
					&& (i < (this.myProofs.size() - 1))) {

				theServerGlobals.log("Stop voortijdig beoordeling van risico " + this.refID
						+ ". Een van onderliggende bewijzen is POSITIEF en operand is OR");
				this.clearExplanation();
				break;
			}

		}
	}

	public void assessRisk(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply , boolean exhaustive) {

		// initialize
		this.proofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
		this.setRiskValue(Risk.tRiskClassificationType.UNDETERMINED);
		this.aValueSet = false;

		// record start

		theServerGlobals.log("");
		theServerGlobals.log("");
		theServerGlobals.log("=================");
		theServerGlobals.log("BEOORDEEL RISICO");
		theServerGlobals.log("=================");
		theServerGlobals.log("Risico: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Bron: " + this.getPolicyReference());
		theServerGlobals.log("");

		
		this.clearExplanation();
		// Evalueer strategie
		if (this.getOperand().equals(Risk.tOperandType.AND)) {
			this.evalProofasAND(theServerGlobals, theCase, theReply,  exhaustive);
		} else if (this.getOperand().equals(Risk.tOperandType.OR)) {
			this.evalProofasOR(theServerGlobals, theCase, theReply, exhaustive);
		}

		// Evaluate risk value based of overall proofresult

		if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
			this.setRiskValue(Risk.tRiskClassificationType.UNDETERMINED);
		} else if (this.proofOverallResult.equals(Proof.tProofClassificationType.POSITIVE)) {
			this.setRiskValue(Risk.tRiskClassificationType.NEUTRAL);
		} else if (this.proofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
			// Later determine severity
			this.setRiskValue(Risk.tRiskClassificationType.INCREASED);
		}

		theServerGlobals.log("");
		theServerGlobals.log("");
		theServerGlobals.log("=================");
		theServerGlobals.log("BEOORDEELD RISICO");
		theServerGlobals.log("=================");
		theServerGlobals.log("Risico: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Bron: " + this.getPolicyReference());
		theServerGlobals.log("Bewijzen: " + proofOverallResult);
		theServerGlobals.log("Operand: " + this.myOperand);
		theServerGlobals.log("Risico vaststelling: " + this.getRiskValue());
		theServerGlobals.log("=================");

		theServerGlobals.log("");

	}



	public void justifyTheProofs(ServerGlobals theServerGlobals, Case correspondingCase, Risk aRisk,
			JustificationRisk justificationRisk) {
	    int i;
	    Proof aProof;
	    
	    for (i = 0 ; i< this.myProofs.size();  i ++) {
	    	aProof = this.myProofs.get(i);
	    	aProof.justifyProof(theServerGlobals,correspondingCase, aRisk,justificationRisk  );
	    }
		
	}

}
