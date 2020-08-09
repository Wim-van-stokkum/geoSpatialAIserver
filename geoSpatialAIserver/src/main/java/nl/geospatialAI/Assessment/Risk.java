package nl.geospatialAI.Assessment;

import java.util.ArrayList;
import java.util.List;

public class Risk {

	public enum tRiskCategoryType {
		CONSTRUCTION, HEALTH, ENVIRONMENTAL, LIVING_ENVIRONMENT, PURPOSE

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
	private tOperandType myOperand = Risk.tOperandType.AND;

	public Risk() {

		risk_refID = risk_refID + 1;
		this.setRefID(risk_refID);

		// initialize proofs
		this.myProofs = new ArrayList<Proof>();

		System.out.println("CREATING RISK [" + this.getRefID() + "]");
		System.out.println("----------------------------------------");
		System.out.println();

	}

	public String getPolicyReference() {
		return policyReference;
	}

	public void createTestStub1(PolicyLibrary policyLib) {
		Proof proofHoogte;
		Proof proofBouwopvlak;

		// hoogte
		proofHoogte = policyLib.createProof_UNDER_MAX_HEIGHT(Proof.tProofClassificationType.UNDETERMINED);

		// bouwopvlak

		proofBouwopvlak = policyLib.createProof_WITHIN_BOUNDARY_BUILD_SURFACE(Proof.tProofClassificationType.UNDETERMINED);
		this.addProof(proofHoogte);
		this.addProof(proofBouwopvlak);
	}

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

	private void evalProofasAND(boolean tRecording, boolean exhaustive) {
		int i;
		Proof.tProofClassificationType aProofResult;

		// Proof all underlying proofs
		for (i = 0; i < this.myProofs.size(); i++) {
			aProofResult = myProofs.get(i).assessProof(tRecording, 1, exhaustive);

			if (aProofResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
				this.proofOverallResult = Proof.tProofClassificationType.NEGATIVE;
				aValueSet = true;
				if (tRecording) {
					System.out.println("Overall impact: evaluatie naar negatief vanwege AND");
					System.out.println();
				}
			} else if (aProofResult.equals(Proof.tProofClassificationType.POSITIVE)) {
				// Negative remain Negative : no action
				// Positive remains positive : no action
				// Undetermined, set : no action

				if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (this.aValueSet == false) {
						// flip undetermined first time to positive
						if (tRecording) {
							System.out.println("Overall impact: evaluatie va onbekend naar positief");
							System.out.println();
						}
						this.proofOverallResult = Proof.tProofClassificationType.POSITIVE;
						this.aValueSet = true;
					}

				}
			} else if (aProofResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
				// positive becomes undetermind

				if (this.proofOverallResult.equals(Proof.tProofClassificationType.POSITIVE)) {
					this.proofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
					this.aValueSet = true;
					if (tRecording) {
						System.out.println("Overall impact : positief wordt onbekend");
						System.out.println();
					}

				}
				if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (aValueSet == false) {
						if (tRecording) {
							System.out.println("Overall impact: fixeer onbekend");
							System.out.println();
						}
						this.aValueSet = true;
					}

				}
			}

			// Break by first negative point ?
			if ((exhaustive == false) && (proofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE))
					&& (i < (this.myProofs.size() - 1))) {

				if (tRecording) {
					System.out.println("Stop voortijdig beoordeling van risico " + this.refID
							+ ". Een van onderliggende bewijzen zijn negatief en operand is AND");
				}
				break;
			}

		}
	}

	private void evalProofasOR(boolean tRecording, boolean exhaustive) {
		int i;
		Proof.tProofClassificationType aProofResult;

		// Proof all underlying proofs
		for (i = 0; i < this.myProofs.size(); i++) {
			aProofResult = myProofs.get(i).assessProof(tRecording, 1, exhaustive);

			if (aProofResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
				// Positive stay positive
				// Negative stays negative
				if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED) && (aValueSet = true)) {

					this.proofOverallResult = Proof.tProofClassificationType.NEGATIVE;
					aValueSet = true;
				}

				if (tRecording) {
					System.out.println("Overall impact: evaluatie van onbekend naar negatief");
					System.out.println();
				}
			} else if (aProofResult.equals(Proof.tProofClassificationType.POSITIVE)) {
				// Bij OR wordt resultaat POSITIEF: no matter waht the other will be
				if (tRecording) {
					System.out.println("Overall impact : evaluatie naar positief vanwege OR");
					System.out.println();
				}
				this.proofOverallResult = Proof.tProofClassificationType.POSITIVE;
				this.aValueSet = true;

			} else if (aProofResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
				// positive stays positive

				if (this.proofOverallResult.equals(Proof.tProofClassificationType.NEGATIVE)) {
					this.proofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
					this.aValueSet = true;
					if (tRecording) {
						System.out.println("Overall impact : negatief wordt onbekend");
						System.out.println();
					}

				}
				if (this.proofOverallResult.equals(Proof.tProofClassificationType.UNDETERMINED)) {
					if (aValueSet == false) {
						if (tRecording) {
							System.out.println("Overall impact: fixeer onbekend");
							System.out.println();
						}
						this.aValueSet = true;
					}

				}
			}

			// Break by first poitieve point ?
			if ((exhaustive == false) && (proofOverallResult.equals(Proof.tProofClassificationType.POSITIVE))
					&& (i < (this.myProofs.size() - 1))) {

				if (tRecording) {
					System.out.println("Stop voortijdig beoordeling van risico " + this.refID
							+ ". Een van onderliggende bewijzen is POSITIEF en operand is OR");
				}
				break;
			}

		}
	}

	public void assessRisk(boolean tRecording, boolean exhaustive) {

		// initialize
		this.proofOverallResult = Proof.tProofClassificationType.UNDETERMINED;
		this.setRiskValue(Risk.tRiskClassificationType.UNDETERMINED);
		this.aValueSet = false;

		// record start
		if (tRecording) {
			System.out.println("");
			System.out.println("");
			System.out.println("=================");
			System.out.println("BEOORDEEL RISICO");
			System.out.println("=================");
			System.out.println("Risico: " + this.getDisplayName() + " [id: " + this.refID + "]");
			System.out.println("Bron: " + this.getPolicyReference());
			System.out.println("");

		}

		// Evalueer strategie
		if (this.getOperand().equals(Risk.tOperandType.AND)) {
			this.evalProofasAND(tRecording, exhaustive);
		} else if (this.getOperand().equals(Risk.tOperandType.OR)) {
			this.evalProofasOR(tRecording, exhaustive);
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

		if (tRecording) {
			System.out.println("");
			System.out.println("");
			System.out.println("=================");
			System.out.println("BEOORDEELD RISICO");
			System.out.println("=================");
			System.out.println("Risico: " + this.getDisplayName() + " [id: " + this.refID + "]");
			System.out.println("Bron: " + this.getPolicyReference());
			System.out.println("Bewijzen: " + proofOverallResult);
			System.out.println("Operand: " + this.myOperand);
			System.out.println("Risico vaststelling: " + this.getRiskValue());
			System.out.println("=================");

			System.out.println("");

		}

	}

}
