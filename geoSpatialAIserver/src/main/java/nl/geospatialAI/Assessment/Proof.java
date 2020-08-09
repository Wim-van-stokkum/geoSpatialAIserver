package nl.geospatialAI.Assessment;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Proof {

	public enum tProofCategoryType {
		UNDER_MAX_HEIGHT, ALLOWED_PROFFESION_AT_HOUSE, WITHIN_BOUNDARY_BUILD_SURFACE, SURFACE_FOUNDATION,
		SURFACE_ADDITIONAL_BUILDINGS

		// to be defined in use case
	}

	public enum tProofClassificationType {
		POSITIVE, NEGATIVE, UNDETERMINED
	}

	public enum tOperandType {
		AND, OR
	}

	static int proof_refID = 500;
	private int refID;

	public int getRefID() {
		return refID;
	}

	public void setRefID(int refID) {
		this.refID = refID;
	}

	private tProofCategoryType proofCategory;

	private tProofClassificationType proofResult = Proof.tProofClassificationType.UNDETERMINED;
	private tProofClassificationType defaultProofResult = Proof.tProofClassificationType.UNDETERMINED;

	private String policyReference;

	private String displayName;

	private List<Proof> mySubProofs;
	private boolean aValueSet = false;
	private Proof.tProofClassificationType subProofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
	private tOperandType myOperand = Proof.tOperandType.AND;

	public tProofCategoryType getProofCategory() {
		return proofCategory;
	}

	public void setProofCategory(tProofCategoryType proofCategory) {
		this.proofCategory = proofCategory;
	}

	public tProofClassificationType getProofResult() {
		return proofResult;
	}

	public void setProofResult(tProofClassificationType proofResult) {
		this.proofResult = proofResult;
	}

	public String getPolicyReference() {
		return policyReference;
	}

	public void setPolicyReference(String policyReference) {
		this.policyReference = policyReference;
	}

	public Proof() {

		proof_refID = proof_refID + 1;
		this.setRefID(proof_refID);

		this.mySubProofs = new ArrayList<Proof>();

		System.out.println("CREATING PROOF [" + this.getRefID() + "]");
		System.out.println("----------------------------------------");
		System.out.println();

	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public tProofClassificationType getDefaultProofResult() {
		return defaultProofResult;
	}

	public void setDefaultProofResult(tProofClassificationType defaultProofResult) {
		this.defaultProofResult = defaultProofResult;
	}

	public void addChildProof(Proof aChild) {
		this.mySubProofs.add(aChild);
	}

	public tOperandType getOperand() {
		return myOperand;
	}

	// ASSESS

	public tProofClassificationType assessProof(ServerGlobals theServerGlobals, int level, boolean exhaustive) {

		theServerGlobals.log("BEOORDEEL BEWIJS  [level: " + level + "]");
		theServerGlobals.log("===========");
		theServerGlobals.log("Proof: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Justification: " + this.getPolicyReference());
		theServerGlobals.log("");

		// detemine sub proof (if any)

		if (this.mySubProofs.size() > 0) {
			// ther are sub proofs

			theServerGlobals.log("BEOORDEEL ONDERLIGGENDE BEWIJZEN  [level: " + level + "]");
			theServerGlobals.log("============================================================");
			theServerGlobals.log("Proof: " + this.getDisplayName() + " [id: " + this.refID + "]");
			theServerGlobals.log("Justification: " + this.getPolicyReference());
			theServerGlobals.log("Operand: " + this.getOperand());
			theServerGlobals.log("");

			if (this.getOperand().equals(Proof.tOperandType.AND)) {
				this.evalProofasAND(theServerGlobals, exhaustive, level + 1);
			} else if (this.getOperand().equals(Proof.tOperandType.OR)) {
				this.evalProofasOR(theServerGlobals, exhaustive, level + 1);
			}
			this.setProofResult(this.subProofOverallResult);
		}

		// detemine sub facts (if any)

		// if applicable set default value

		if ((this.getProofResult().equals(Proof.tProofClassificationType.UNDETERMINED) == false)
				&& (this.getDefaultProofResult().equals(Proof.tProofClassificationType.UNDETERMINED) == false)) {

			theServerGlobals.log("Using default value : " + this.getDefaultProofResult());
			theServerGlobals.log("");

			this.setProofResult(this.getDefaultProofResult());
		}

		theServerGlobals.log("");
		theServerGlobals.log("");
		theServerGlobals.log("=================");
		theServerGlobals.log("BEOORDEELD BEWIJS");
		theServerGlobals.log("=================");
		theServerGlobals.log("Bewijs: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Bron: " + this.getPolicyReference());
		if (this.mySubProofs.size() > 1) {
			theServerGlobals.log("Onderliggende bewijzen: " + this.subProofOverallResult);
		}
		theServerGlobals.log("Bron: " + this.getPolicyReference());
//		/	theServerGlobals.log("Feiten" + factOverallResult);
		theServerGlobals.log("Bewijs resultaat: " + this.getProofResult());
		theServerGlobals.log("=================");

		theServerGlobals.log("");

		return (this.getProofResult());
	}

	// Risk Evaluatie methods

	private void evalProofasAND(ServerGlobals theServerGlobals, boolean exhaustive, int level) {
		int i;
		Proof.tProofClassificationType aProofResult;

		// Proof all underlying proofs
		for (i = 0; i < this.mySubProofs.size(); i++) {
			aProofResult = mySubProofs.get(i).assessProof(theServerGlobals, level, exhaustive);

			if (aProofResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
				this.subProofOverallResult = Proof.tProofClassificationType.NEGATIVE;
				aValueSet = true;

				theServerGlobals.log("Overall impact: evaluatie naar negatief vanwege AND");
				theServerGlobals.log("");

			} else if (aProofResult.equals(Proof.tProofClassificationType.POSITIVE)) {
				// Negative remain Negative : no action
				// Positive remains positive : no action
				// Undetermined, set : no action

				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (this.aValueSet == false) {
						// flip undetermined first time to positive

						theServerGlobals.log("Overall impact: evaluatie va onbekend naar positief");
						theServerGlobals.log("");

						this.subProofOverallResult = Proof.tProofClassificationType.POSITIVE;
						this.aValueSet = true;
					}

				}
			} else if (aProofResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
				// positive becomes undetermind

				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.POSITIVE)) {
					this.subProofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
					this.aValueSet = true;

					theServerGlobals.log("Overall impact : positief wordt onbekend");
					theServerGlobals.log("");

				}
				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (aValueSet == false) {

						theServerGlobals.log("Overall impact: fixeer onbekend");
						theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first negative point ?
			if ((exhaustive == false) && (subProofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE))
					&& (i < (this.mySubProofs.size() - 1))) {

				theServerGlobals.log("Stop voortijdig beoordeling van gecombineerd bewijs " + this.refID
						+ ". Een van onderliggende bewijzen zijn negatief en operand is AND");

				break;
			}

		}
	}

	private void evalProofasOR(ServerGlobals theServerGlobals, boolean exhaustive, int level) {
		int i;
		Proof.tProofClassificationType aProofResult;

		// Proof all underlying proofs
		for (i = 0; i < this.mySubProofs.size(); i++) {
			aProofResult = mySubProofs.get(i).assessProof(theServerGlobals, level, exhaustive);

			if (aProofResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
				// Positive stay positive
				// Negative stays negative
				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)
						&& (aValueSet = true)) {

					this.subProofOverallResult = Proof.tProofClassificationType.NEGATIVE;
					aValueSet = true;
				}

				theServerGlobals.log("Overall impact: evaluatie van onbekend naar negatief");
				theServerGlobals.log("");

			} else if (aProofResult.equals(Proof.tProofClassificationType.POSITIVE)) {
				// Bij OR wordt resultaat POSITIEF: no matter waht the other will be

				theServerGlobals.log("Overall impact : evaluatie naar positief vanwege OR");
				theServerGlobals.log("");

				this.subProofOverallResult = Proof.tProofClassificationType.POSITIVE;
				this.aValueSet = true;

			} else if (aProofResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
				// positive stays positive

				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
					this.subProofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
					this.aValueSet = true;

					theServerGlobals.log("Overall impact : negatief wordt onbekend");
					theServerGlobals.log("");

				}
				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (aValueSet == false) {

						theServerGlobals.log("Overall impact: fixeer onbekend");
						theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first poitieve point ?
			if ((exhaustive == false) && (subProofOverallResult.equals(Proof.tProofClassificationType.POSITIVE))
					&& (i < (this.mySubProofs.size() - 1))) {

				theServerGlobals.log("Stop voortijdig beoordeling van combinatie bewijs " + this.refID
						+ ". Een van onderliggende subbewijzen is POSITIEF en operand is OR");

				break;
			}

		}
	}

}
