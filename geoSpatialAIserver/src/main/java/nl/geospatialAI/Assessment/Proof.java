package nl.geospatialAI.Assessment;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.Justification.JustificationProof;
import nl.geospatialAI.Justification.JustificationRisk;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Proof {

	public enum tProofCategoryType {
		UNDER_MAX_HEIGHT, ALLOWED_PROFFESION_AT_HOUSE, WITHIN_BOUNDARY_BUILD_SURFACE, SURFACE_FOUNDATION,
		SURFACE_ADDITIONAL_BUILDINGS, SURFACE_WATER_NON_PERM_ACCEPTABLE, OBJECT_MEANT_FOR_COMMERCIAL_USE

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

	private tProofCategoryType proofCategory;

	private tProofClassificationType proofResult = Proof.tProofClassificationType.UNDETERMINED;
	private tProofClassificationType defaultProofResult = Proof.tProofClassificationType.UNDETERMINED;

	private String policyReference;

	private String displayName;

	private List<Proof> mySubProofs;
	private List<Fact> myFacts;
	protected String explanation;

	private boolean aValueSet = false;
	protected boolean needInput;

	private Proof.tProofClassificationType subProofOverallResult = Proof.tProofClassificationType.UNDETERMINED;

	private Fact.tFactClassificationType subFactOverallResult = Fact.tFactClassificationType.UNKNOWN;
	private tOperandType myOperand = Proof.tOperandType.AND;

	public tProofCategoryType getProofCategory() {
		return proofCategory;
	}

	public boolean getNeedInput() {
		return this.needInput;
	}

	protected void addToExplanation(String aText) {
	
		if (this.explanation.length() == 0) {
			this.explanation = aText;
		} else {
			this.explanation = this.explanation + " " + aText;
		}

	}

	@JsonIgnore
	public String explainYourSelf() {
		return this.explanation;
	}

	public int getRefID() {
		return refID;
	}

	public void setRefID(int refID) {
		this.refID = refID;
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

	public tProofClassificationType assessProof(ServerGlobals theServerGlobals, Case theCase,
			AssessRequestReply theReply, int level, boolean exhaustive) {
		tProofClassificationType resultAssessmentSubProof;
		tProofClassificationType resultFacts;

		theServerGlobals.log("BEOORDEEL BEWIJS  [level: " + level + "]");
		theServerGlobals.log("===========");
		theServerGlobals.log("Proof: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Justification: " + this.getPolicyReference());
		theServerGlobals.log("");

		this.needInput = false; // until proofed otherWise
		this.clearMyExplanation();

		// Assess sub proof (if Any)

		resultFacts = Proof.tProofClassificationType.UNDETERMINED;
		resultAssessmentSubProof = Proof.tProofClassificationType.UNDETERMINED;

		if (this.mySubProofs.size() > 0) {
			resultAssessmentSubProof = this.AssessSubProofs(theServerGlobals, theCase, theReply, level, exhaustive);
		}

		// determine sub facts (if any) and no input needed to solve subproofs first
		if (this.myFacts.size() > 0) {
			if (this.getOperand() == Proof.tOperandType.AND) {
				if (this.getNeedInput() == false) {
					resultFacts = this.AssessFacts(theServerGlobals, theCase, theReply, level, exhaustive);
				}
			} else {
				resultFacts = this.AssessFacts(theServerGlobals, theCase, theReply, level, exhaustive);
			}
		}

		// to do overall conclusions over sub proofs and facts
		if ((this.myFacts.size() > 0) && (this.mySubProofs.size() > 0)) {
			if (this.myOperand.equals(Proof.tOperandType.AND)) {
				if (resultAssessmentSubProof.equals(Proof.tProofClassificationType.NEGATIVE)
						|| resultFacts.equals(Proof.tProofClassificationType.NEGATIVE)) {
					this.setProofResult(Proof.tProofClassificationType.NEGATIVE);
				} else if (resultAssessmentSubProof.equals(Proof.tProofClassificationType.POSITIVE)
						&& resultFacts.equals(Proof.tProofClassificationType.POSITIVE)) {
					this.setProofResult(Proof.tProofClassificationType.POSITIVE);
				} else if (resultAssessmentSubProof.equals(Proof.tProofClassificationType.UNDETERMINED)
						|| resultFacts.equals(Proof.tProofClassificationType.UNDETERMINED)) {

					this.setProofResult(Proof.tProofClassificationType.UNDETERMINED);
				}

			}

			if (this.myOperand.equals(Proof.tOperandType.OR)) {
				if (resultAssessmentSubProof.equals(Proof.tProofClassificationType.POSITIVE)
						|| resultFacts.equals(Proof.tProofClassificationType.POSITIVE)) {
					this.setProofResult(Proof.tProofClassificationType.POSITIVE);
				} else if (resultAssessmentSubProof.equals(Proof.tProofClassificationType.NEGATIVE)
						&& resultFacts.equals(Proof.tProofClassificationType.NEGATIVE)) {
					this.setProofResult(Proof.tProofClassificationType.NEGATIVE);
				} else if (resultAssessmentSubProof.equals(Proof.tProofClassificationType.UNDETERMINED)
						|| resultFacts.equals(Proof.tProofClassificationType.UNDETERMINED)) {

					this.setProofResult(Proof.tProofClassificationType.UNDETERMINED);
				}

			}

		}

		if ((this.myFacts.size() > 0) && (this.mySubProofs.size() == 0)) {
			this.setProofResult(resultFacts);
		//	theServerGlobals.log("SET RESULT FOR FACTS: " + resultFacts);
		}

		if ((this.myFacts.size() == 0) && (this.mySubProofs.size() > 0)) {
			this.setProofResult(resultAssessmentSubProof);
		//	theServerGlobals.log("SET RESULT FOR PROOFS: " + resultAssessmentSubProof);
		}

		// if applicable set default value

		if ((this.getProofResult().equals(Proof.tProofClassificationType.UNDETERMINED) == true)
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
		theServerGlobals.log(this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Bron: " + this.getPolicyReference());
		if (this.mySubProofs.size() > 1) {
			theServerGlobals.log("Onderliggende bewijzen: " + resultAssessmentSubProof);
		}
		if (this.myFacts.size() > 1) {
			theServerGlobals.log("Onderliggende feiten: " + resultFacts);
		}
		theServerGlobals.log("Bron: " + this.getPolicyReference());

		theServerGlobals.log("Bewijs resultaat: " + this.getProofResult());
		theServerGlobals.log("=================");

		theServerGlobals.log("");

		return (this.getProofResult());
	}

	private void clearMyExplanation() {
		this.explanation = "";

	}

	private tProofClassificationType AssessSubProofs(ServerGlobals theServerGlobals, Case theCase,
			AssessRequestReply theReply, int level, boolean exhaustive) {
		// determine sub proof (if any)

		tProofClassificationType conclusion;

		theServerGlobals.log("BEOORDEEL ONDERLIGGENDE BEWIJZEN  [level: " + level + "]");
		theServerGlobals.log("============================================================");
		theServerGlobals.log( this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Justification: " + this.getPolicyReference());
		theServerGlobals.log("Operand: " + this.getOperand());
		theServerGlobals.log("");

		if (this.getOperand().equals(Proof.tOperandType.AND)) {
			this.evalProofasAND(theServerGlobals, theCase, theReply, exhaustive, level + 1);
		} else if (this.getOperand().equals(Proof.tOperandType.OR)) {
			this.evalProofasOR(theServerGlobals, theCase, theReply, exhaustive, level + 1);
		}
		conclusion = subProofOverallResult;

		return conclusion;
	}

	private tProofClassificationType AssessFacts(ServerGlobals theServerGlobals, Case theCase,
			AssessRequestReply theReply, int level, boolean exhaustive) {
		// determine facts

		tProofClassificationType conclusion;

		theServerGlobals.log("BEOORDEEL FEITEN  [level: " + level + "]");
		theServerGlobals.log("============================================================");
		theServerGlobals.log(this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Justification: " + this.getPolicyReference());
		theServerGlobals.log("Operand: " + this.getOperand());
		theServerGlobals.log("");

		if (this.getOperand().equals(Proof.tOperandType.AND)) {
			this.evalFactsasAND(theServerGlobals, theCase, theReply, exhaustive, level + 1);
		} else if (this.getOperand().equals(Proof.tOperandType.OR)) {
			this.evalFactsasOR(theServerGlobals, theCase, theReply, exhaustive, level + 1);
		}
		;
		// Conclusion
		conclusion = Proof.tProofClassificationType.UNDETERMINED;

		if (this.subFactOverallResult.equals(Fact.tFactClassificationType.TRUE)) {
			conclusion = Proof.tProofClassificationType.POSITIVE;
		} else if (this.subFactOverallResult.equals(Fact.tFactClassificationType.FALSE)) {
			conclusion = Proof.tProofClassificationType.NEGATIVE;
		} else if (this.subFactOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
			conclusion = Proof.tProofClassificationType.UNDETERMINED;
		}

		return conclusion;
	}

	private void evalProofasAND(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply,
			boolean exhaustive, int level) {
		int i;
		boolean breakBecauseNeedInput;

		Proof.tProofClassificationType aProofResult;

		// Proof all underlying proofs
		breakBecauseNeedInput = false;
		for (i = 0; ((i < this.mySubProofs.size()) && (breakBecauseNeedInput == false)); i++) {
			aProofResult = mySubProofs.get(i).assessProof(theServerGlobals, theCase, theReply, level, exhaustive);
			this.addToExplanation(mySubProofs.get(i).explainYourSelf());
			breakBecauseNeedInput = mySubProofs.get(i).getNeedInput();
			if (breakBecauseNeedInput) {
				this.needInput = true;
			}

			if (aProofResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
				this.subProofOverallResult = Proof.tProofClassificationType.NEGATIVE;
				aValueSet = true;

		//		theServerGlobals.log("Overall impact: evaluatie naar negatief vanwege AND");
		//		theServerGlobals.log("");

			} else if (aProofResult.equals(Proof.tProofClassificationType.POSITIVE)) {
				// Negative remain Negative : no action
				// Positive remains positive : no action
				// Undetermined, set : no action

				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (this.aValueSet == false) {
						// flip undetermined first time to positive

			//			theServerGlobals.log("Overall impact: evaluatie va onbekend naar positief");
			//			theServerGlobals.log("");

						this.subProofOverallResult = Proof.tProofClassificationType.POSITIVE;
						this.aValueSet = true;
					}

				}
			} else if (aProofResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
				// positive becomes undetermind

				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.POSITIVE)) {
					this.subProofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
					this.aValueSet = true;

			//		theServerGlobals.log("Overall impact : positief wordt onbekend");
				//	theServerGlobals.log("");

				}
				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (aValueSet == false) {

			//			theServerGlobals.log("Overall impact: fixeer onbekend");
			//			theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first negative point ?
			if ((exhaustive == false) && (subProofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE))
					&& (i < (this.mySubProofs.size() - 1))) {

			//	theServerGlobals.log("Stop voortijdig beoordeling van gecombineerd bewijs " + this.refID
			//			+ ". Een van onderliggende bewijzen zijn negatief en operand is AND");
				this.clearExplanation();
				break;
			}

		}
	}

	private void evalProofasOR(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply,
			boolean exhaustive, int level) {
		int i;
		Proof.tProofClassificationType aProofResult;
        boolean breakBecauseNeedInput;
        
		// Proof all underlying proofs
		breakBecauseNeedInput = false;
		for (i = 0; ((i < this.mySubProofs.size()) && (breakBecauseNeedInput == false)); i++) {
			aProofResult = mySubProofs.get(i).assessProof(theServerGlobals, theCase, theReply, level, exhaustive);
			this.addToExplanation(mySubProofs.get(i).explainYourSelf());
			breakBecauseNeedInput = mySubProofs.get(i).getNeedInput();
			if (breakBecauseNeedInput) {
				this.needInput = true;
			}

			if (aProofResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
				// Positive stay positive
				// Negative stays negative
				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)
						&& (aValueSet = true)) {

					this.subProofOverallResult = Proof.tProofClassificationType.NEGATIVE;
					aValueSet = true;
				}

		//		theServerGlobals.log("Overall impact: evaluatie van onbekend naar negatief");
	//			theServerGlobals.log("");

			} else if (aProofResult.equals(Proof.tProofClassificationType.POSITIVE)) {
				// Bij OR wordt resultaat POSITIEF: no matter waht the other will be

		//		theServerGlobals.log("Overall impact : evaluatie naar positief vanwege OR");
		//		theServerGlobals.log("");

				this.subProofOverallResult = Proof.tProofClassificationType.POSITIVE;
				this.aValueSet = true;

			} else if (aProofResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
				// positive stays positive

				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
					this.subProofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
					this.aValueSet = true;

		//			theServerGlobals.log("Overall impact : negatief wordt onbekend");
		//			theServerGlobals.log("");

				}
				if (this.subProofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (aValueSet == false) {

			//			theServerGlobals.log("Overall impact: fixeer onbekend");
			//			theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first poitieve point ?
			if ((exhaustive == false) && (subProofOverallResult.equals(Proof.tProofClassificationType.POSITIVE))
					&& (i < (this.mySubProofs.size() - 1))) {

	//			theServerGlobals.log("Stop voortijdig beoordeling van combinatie bewijs " + this.refID
		//				+ ". Een van onderliggende subbewijzen is POSITIEF en operand is OR");
				this.clearExplanation();
				break;
			}

		}
	}

	// FACTS

	private void evalFactsasAND(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply,
			boolean exhaustive, int level) {
		int i;
		Fact.tFactClassificationType aFactResult;
		boolean breakBecauseNeedInput;

		// Proof all underlying facts
		this.subFactOverallResult = Fact.tFactClassificationType.UNKNOWN;
		breakBecauseNeedInput = false;
		this.aValueSet = false;
		for (i = 0; ((i < this.myFacts.size()) && (breakBecauseNeedInput == false)); i++) {
			aFactResult = myFacts.get(i).assessFact(theServerGlobals, theCase, theReply, level, exhaustive);
			breakBecauseNeedInput = myFacts.get(i).getNeedInput();
			if (breakBecauseNeedInput) {
				this.needInput = true;
			}
			this.addToExplanation(myFacts.get(i).explainYourSelf());

			if (aFactResult.equals(Fact.tFactClassificationType.FALSE)) {
				this.subFactOverallResult = Fact.tFactClassificationType.FALSE;
				aValueSet = true;

	//			theServerGlobals.log("Overall impact feit: evaluatie naar FALSE vanwege AND");
	//			theServerGlobals.log("");

			} else if (aFactResult.equals(Fact.tFactClassificationType.TRUE)) {
				// Negative remain Negative : no action
				// Positive remains positive : no action
				// Undetermined, set : no action

				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
					if (this.aValueSet == false) {
						// flip undetermined first time to positive

		//				theServerGlobals.log("Overall impact: evaluatie va onbekend naar positief");
	//					theServerGlobals.log("");

						this.subFactOverallResult = Fact.tFactClassificationType.TRUE;
						this.aValueSet = true;
					}

				}
			} else if (aFactResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
				// positive becomes undetermind

				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.TRUE)) {
					this.subFactOverallResult = Fact.tFactClassificationType.UNKNOWN;
					this.aValueSet = true;

		//			theServerGlobals.log("Overall impact feit : true wordt onbekend");
		//			theServerGlobals.log("");

				}
				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
					if (aValueSet == false) {

		//				theServerGlobals.log("Overall impact feit: fixeer onbekend");
			//			theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first negative point ?
			if ((exhaustive == false) && (subFactOverallResult.equals(Fact.tFactClassificationType.FALSE))
					&& (i < (this.myFacts.size() - 1))) {

	//			theServerGlobals.log("Stop voortijdig beoordeling van feiten " + this.refID
	//					+ ". Een van onderliggende feiten zijn negatief en operand is AND");
				this.clearExplanation();
				break;
			}

		}
	}

	private void evalFactsasOR(ServerGlobals theServerGlobals, Case theCase, AssessRequestReply theReply,
			boolean exhaustive, int level) {
		int i;
		Fact.tFactClassificationType aFactResult;
		boolean breakBecauseNeedInput;

		// Proof all underlying facts
		this.subFactOverallResult = Fact.tFactClassificationType.UNKNOWN;
		breakBecauseNeedInput = false;
		this.aValueSet = false;
		for (i = 0; ((i < this.myFacts.size()) && (breakBecauseNeedInput == false)); i++) {
			aFactResult = myFacts.get(i).assessFact(theServerGlobals, theCase, theReply, level, exhaustive);
			breakBecauseNeedInput = myFacts.get(i).getNeedInput();
			if (breakBecauseNeedInput) {
				this.needInput = true;
			}

			if (aFactResult.equals(Fact.tFactClassificationType.FALSE)) {
				// Positive stay positive
				// Negative stays negative
				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.UNKNOWN) && (aValueSet = true)) {

					this.subFactOverallResult = Fact.tFactClassificationType.FALSE;
					aValueSet = true;
				}

//				theServerGlobals.log("Overall impact: evaluatie van onbekend naar FALSE");
	//			theServerGlobals.log("");

			} else if (aFactResult.equals(Fact.tFactClassificationType.TRUE)) {
				// Bij OR wordt resultaat POSITIEF: no matter waht the other will be

	//			theServerGlobals.log("Overall impact feiten : evaluatie naar TRUE vanwege OR");
	//			theServerGlobals.log("");

				this.subFactOverallResult = Fact.tFactClassificationType.TRUE;
				this.aValueSet = true;

			} else if (aFactResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
				// positive stays positive

				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.FALSE)) {
					this.subFactOverallResult = Fact.tFactClassificationType.UNKNOWN;
					this.aValueSet = true;

	//				theServerGlobals.log("Overall impact feiten : FALSE wordt onbekend");
	//				theServerGlobals.log("");

				}
				if (this.subFactOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
					if (aValueSet == false) {

		//				theServerGlobals.log("Overall impact feiten: fixeer onbekend");
			//			theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first poitieve point ?
			if ((exhaustive == false) && (subFactOverallResult.equals(Fact.tFactClassificationType.TRUE))
					&& (i < (this.mySubProofs.size() - 1))) {

	//			theServerGlobals.log("Stop voortijdig beoordeling van feiten " + this.refID
		//				+ ". Een van onderliggende feiten is TRUE en operand is OR");
				this.clearExplanation();
				break;
			}

		}
	}

	private void clearExplanation() {
		// TODO Auto-generated method stub
		this.explanation = "";
	}

	public void justifyProof(ServerGlobals theServerGlobals, Case correspondingCase, Risk aRisk,
			JustificationRisk justificationRisk) {

		int i;
		Fact aFact;

		JustificationProof myProofJustification;
		myProofJustification = new JustificationProof(this);
		justificationRisk.addProofJustification(myProofJustification);

		for (i = 0; i < this.myFacts.size(); i++) {
			aFact = this.myFacts.get(i);
			aFact.justifyFact(theServerGlobals, correspondingCase, myProofJustification);
		}

	}

	public void evaluateAsAND() {
		// TODO Auto-generated method stub
		this.myOperand = Proof.tOperandType.AND;

	}

	public void evaluateAsOR() {
		// TODO Auto-generated method stub
		this.myOperand = Proof.tOperandType.OR;

	}

}
