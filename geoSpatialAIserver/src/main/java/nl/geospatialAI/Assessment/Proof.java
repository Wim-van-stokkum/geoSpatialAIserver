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
	private List<Fact> myFacts;

	private boolean aValueSet = false;
	private Proof.tProofClassificationType subProofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
	private Fact.tFactClassificationType subFactOverallResult = Fact.tFactClassificationType.UNKNOWN;
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
		this.myFacts = new ArrayList<Fact>();

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

	public void addFacts(Fact aFact) {
		this.myFacts.add(aFact);
	}

	public tOperandType getOperand() {
		return myOperand;
	}

	// ASSESS

	public tProofClassificationType assessProof(ServerGlobals theServerGlobals, int level, boolean exhaustive) {
		tProofClassificationType resultAssessmentSubProof;
		tProofClassificationType resultFacts;

		theServerGlobals.log("BEOORDEEL BEWIJS  [level: " + level + "]");
		theServerGlobals.log("===========");
		theServerGlobals.log("Proof: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Justification: " + this.getPolicyReference());
		theServerGlobals.log("");

		// Assess sub proof (if Any)
		if (this.mySubProofs.size() > 0) {
			resultAssessmentSubProof = this.AssessSubProofs(theServerGlobals, level, exhaustive);
		}
		// detemine sub facts (if any)
		if (this.myFacts.size() > 0) {
			resultFacts = this.AssessFacts(theServerGlobals, level, exhaustive);
		}
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

	private tProofClassificationType AssessSubProofs(ServerGlobals theServerGlobals, int level, boolean exhaustive) {
		// determine sub proof (if any)

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

		return this.subProofOverallResult;
	}
	
	private tProofClassificationType AssessFacts(ServerGlobals theServerGlobals, int level, boolean exhaustive) {
		// determine sub proof (if any)
		
		tProofClassificationType conclusion;

		theServerGlobals.log("BEOORDEEL FEITEN  [level: " + level + "]");
		theServerGlobals.log("============================================================");
		theServerGlobals.log("Proof: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Justification: " + this.getPolicyReference());
		theServerGlobals.log("Operand: " + this.getOperand());
		theServerGlobals.log("");

		if (this.getOperand().equals(Proof.tOperandType.AND)) {
			this.evalFactsasAND(theServerGlobals, exhaustive, level + 1);
		} else if (this.getOperand().equals(Proof.tOperandType.OR)) {
			this.evalFactsasOR(theServerGlobals, exhaustive, level + 1);
		}
		
		//Conclusion
	//	this.setProofResult(this.subFactOverallResult);

		return this.subProofOverallResult;
	}

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
	
	
	// FACTS
	

	private void evalFactsasAND(ServerGlobals theServerGlobals, boolean exhaustive, int level) {
		int i;
		Fact.tFactClassificationType aFactResult;

		// Proof all underlying proofs
		for (i = 0; i < this.myFacts.size(); i++) {
			aFactResult = myFacts.get(i).assessFact(theServerGlobals, level, exhaustive);

			if (aFactResult.equals(Fact.tFactClassificationType.FALSE)) {
				this.subFactOverallResult = Fact.tFactClassificationType.FALSE;
				aValueSet = true;

				theServerGlobals.log("Overall impact feit: evaluatie naar FALSE vanwege AND");
				theServerGlobals.log("");

			} else if (aFactResult.equals(Fact.tFactClassificationType.TRUE)) {
				// Negative remain Negative : no action
				// Positive remains positive : no action
				// Undetermined, set : no action

				if (this.subProofOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
					if (this.aValueSet == false) {
						// flip undetermined first time to positive

						theServerGlobals.log("Overall impact: evaluatie va onbekend naar positief");
						theServerGlobals.log("");

						this.subFactOverallResult = Fact.tFactClassificationType.TRUE;
						this.aValueSet = true;
					}

				}
			} else if (aFactResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
				// positive becomes undetermind

				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.TRUE)) {
					this.subFactOverallResult = Fact.tFactClassificationType.UNKNOWN;
					this.aValueSet = true;

					theServerGlobals.log("Overall impact feit : true wordt onbekend");
					theServerGlobals.log("");

				}
				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
					if (aValueSet == false) {

						theServerGlobals.log("Overall impact feit: fixeer onbekend");
						theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first negative point ?
			if ((exhaustive == false) && (subFactOverallResult.equals(Fact.tFactClassificationType.FALSE))
					&& (i < (this.myFacts.size() - 1))) {

				theServerGlobals.log("Stop voortijdig beoordeling van feiten " + this.refID
						+ ". Een van onderliggende feiten zijn negatief en operand is AND");

				break;
			}

		}
	}

	private void evalFactsasOR(ServerGlobals theServerGlobals, boolean exhaustive, int level) {
		int i;
		Fact.tFactClassificationType aFactResult;

		// Proof all underlying proofs
		for (i = 0; i < this.mySubProofs.size(); i++) {
			aFactResult = this.myFacts.get(i).assessFact(theServerGlobals, level, exhaustive);

			if (aFactResult.equals(Fact.tFactClassificationType.FALSE)) {
				// Positive stay positive
				// Negative stays negative
				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)
						&& (aValueSet = true)) {

					this.subFactOverallResult = Fact.tFactClassificationType.FALSE;
					aValueSet = true;
				}

				theServerGlobals.log("Overall impact: evaluatie van onbekend naar FALSE");
				theServerGlobals.log("");

			} else if (aFactResult.equals(Fact.tFactClassificationType.TRUE)) {
				// Bij OR wordt resultaat POSITIEF: no matter waht the other will be

				theServerGlobals.log("Overall impact feiten : evaluatie naar TRUE vanwege OR");
				theServerGlobals.log("");

				this.subFactOverallResult = Fact.tFactClassificationType.TRUE;
				this.aValueSet = true;

			} else if (aFactResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
				// positive stays positive

				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.FALSE)) {
					this.subFactOverallResult = Fact.tFactClassificationType.UNKNOWN;
					this.aValueSet = true;

					theServerGlobals.log("Overall impact feiten : FALSE wordt onbekend");
					theServerGlobals.log("");

				}
				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
					if (aValueSet == false) {

						theServerGlobals.log("Overall impact feiten: fixeer onbekend");
						theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first poitieve point ?
			if ((exhaustive == false) && (subFactOverallResult.equals(Fact.tFactClassificationType.TRUE))
					&& (i < (this.mySubProofs.size() - 1))) {

				theServerGlobals.log("Stop voortijdig beoordeling van feiten " + this.refID
						+ ". Een van onderliggende feiten is TRUE en operand is OR");

				break;
			}

		}
	}

}
