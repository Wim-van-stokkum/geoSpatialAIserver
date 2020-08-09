package nl.geospatialAI.Assessment;

import java.util.ArrayList;
import java.util.List;


import nl.geospatialAI.serverGlobals.ServerGlobals;

public class Fact {


	public enum tFactType {
		HEIGHT_ABOVE_GREENFIELD, PROFESSION_CATEGORY_B,  OBJECT_TYPE_IS_HOUSE


		// to be defined in use case
	}

	public enum tFactClassificationType {
		TRUE, FALSE, UNKNOWN
	}

	public enum tOperandType {
		AND, OR
	}

	static int fact_refID = 5000;
	private int refID;
	
	private tFactType factType;

	private tFactClassificationType factResult = Fact.tFactClassificationType.UNKNOWN;
	private tFactClassificationType defaultFactResult = Fact.tFactClassificationType.UNKNOWN;

	private String policyReference;

	private String displayName;

	private List<Fact> mySubFacts;
	private boolean aValueSet = false;
	private Fact.tFactClassificationType subFactsOverallResult =  Fact.tFactClassificationType.UNKNOWN;
	private tOperandType myOperand = Fact.tOperandType.AND;

	
	public Fact() {

		fact_refID = fact_refID + 1;
		this.setRefID(fact_refID);

		this.mySubFacts = new ArrayList<Fact>();

		System.out.println("CREATING FACT [" + this.getRefID() + "]");
		System.out.println("----------------------------------------");
		System.out.println();

	}

	public int getRefID() {
		return refID;
	}

	public void setRefID(int refID) {
		this.refID = refID;
	}

	
	public tFactType getFactType() {
		return factType;
	}

	public void setFactType(tFactType factType) {
		this.factType = factType;
	}

	public tFactClassificationType getFactResult() {
		return factResult;
	}

	public void setFactResult(tFactClassificationType factResult) {
		this.factResult = factResult;
	}

	public String getPolicyReference() {
		return policyReference;
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

	public tFactClassificationType getDefaultFactResult() {
		return defaultFactResult;
	}

	public void setDefaultFactResult(tFactClassificationType defaultFactResult) {
		this.defaultFactResult = defaultFactResult;
	}

	public void addChildFact(Fact aChild) {
		this.mySubFacts.add(aChild);
	}

	public tOperandType getOperand() {
		return myOperand;
	}

	// ASSESS

	public tFactClassificationType assessFact(ServerGlobals theServerGlobals, int level, boolean exhaustive) {

		theServerGlobals.log("BEOORDEEL FEIT  [level: " + level + "]");
		theServerGlobals.log("===================================");
		theServerGlobals.log("Feit: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Justification: " + this.getPolicyReference());
		theServerGlobals.log("");

		// detemine sub proof (if any)

		if (this.mySubFacts.size() > 0) {
			// there are sub facts

			theServerGlobals.log("BEOORDEEL ONDERLIGGENDE FEITEN  [level: " + level + "]");
			theServerGlobals.log("============================================================");
			theServerGlobals.log("Feit: " + this.getDisplayName() + " [id: " + this.refID + "]");
			theServerGlobals.log("Justification: " + this.getPolicyReference());
			theServerGlobals.log("Operand: " + this.getOperand());
			theServerGlobals.log("");

			if (this.getOperand().equals(Proof.tOperandType.AND)) {
				this.evalFactasAND(theServerGlobals, exhaustive, level + 1);
			} else if (this.getOperand().equals(Proof.tOperandType.OR)) {
				this.evalFactasOR(theServerGlobals, exhaustive, level + 1);
			}
			this.setFactResult(this.subFactsOverallResult);
		}



		// if applicable set default value

		if ((this.getFactResult().equals(Fact.tFactClassificationType.UNKNOWN) == false)
				&& (this.getDefaultFactResult().equals(Fact.tFactClassificationType.UNKNOWN) == false)) {

			theServerGlobals.log("Using default value for fact: " + this.getDefaultFactResult());
			theServerGlobals.log("");

			this.setFactResult(this.getDefaultFactResult());
		}

		theServerGlobals.log("");
		theServerGlobals.log("");
		theServerGlobals.log("=================");
		theServerGlobals.log("BEOORDEELD FEIT");
		theServerGlobals.log("=================");
		theServerGlobals.log("Feit: " + this.getDisplayName() + " [id: " + this.refID + "]");
		theServerGlobals.log("Bron: " + this.getPolicyReference());
		if (this.mySubFacts.size() > 1) {
			theServerGlobals.log("Onderliggende feiten: " + this.subFactsOverallResult);
		}
		theServerGlobals.log("Bron: " + this.getPolicyReference());

		theServerGlobals.log("Feit resultaat: " + this.getFactResult());
		theServerGlobals.log("=================");

		theServerGlobals.log("");

		return (this.getFactResult());
	}

	// Risk Evaluatie methods

	private void evalFactasAND(ServerGlobals theServerGlobals, boolean exhaustive, int level) {
		int i;
		Fact.tFactClassificationType aFactResult;

		// Review all underlying facts
		for (i = 0; i < this.mySubFacts.size(); i++) {
			aFactResult = mySubFacts.get(i).assessFact(theServerGlobals, level, exhaustive);

			if (aFactResult.equals(Fact.tFactClassificationType.FALSE)) {
				this.subFactsOverallResult = Fact.tFactClassificationType.FALSE;
				aValueSet = true;

				theServerGlobals.log("Overall impact fact: evaluatie naar FALSE vanwege AND");
				theServerGlobals.log("");

			} else if (aFactResult.equals(Fact.tFactClassificationType.TRUE)) {
				// Negative remain Negative : no action
				// Positive remains positive : no action
				// Undetermined, set : no action

				if (this.subFactsOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
					if (this.aValueSet == false) {
						// flip undetermined first time to positive

						theServerGlobals.log("Overall impact feit: evaluatie van onbekend naar true");
						theServerGlobals.log("");

						this.subFactsOverallResult = Fact.tFactClassificationType.TRUE;
						this.aValueSet = true;
					}

				}
			} else if (aFactResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
				// positive becomes undetermind

				if (this.subFactsOverallResult.equals(Fact.tFactClassificationType.TRUE)) {
					this.subFactsOverallResult = Fact.tFactClassificationType.UNKNOWN;
					this.aValueSet = true;

					theServerGlobals.log("Overall impact feit : TRUE wordt onbekend");
					theServerGlobals.log("");

				}
				if (this.subFactsOverallResult.equals( Fact.tFactClassificationType.UNKNOWN)) {
					if (aValueSet == false) {

						theServerGlobals.log("Overall impact: fixeer onbekend");
						theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first negative point ?
			if ((exhaustive == false) && (subFactsOverallResult.equals(Fact.tFactClassificationType.FALSE))
					&& (i < (this.mySubFacts.size() - 1))) {

				theServerGlobals.log("Stop voortijdig beoordeling van gecombineerd feit " + this.refID
						+ ". Een van onderliggende feiten zijn FALSE en operand is AND");

				break;
			}

		}
	}

	private void evalFactasOR(ServerGlobals theServerGlobals, boolean exhaustive, int level) {
		int i;
		Fact.tFactClassificationType aFactResult;

		// Review all underlying facts
		for (i = 0; i < this.mySubFacts.size(); i++) {
			aFactResult = mySubFacts.get(i).assessFact(theServerGlobals, level, exhaustive);

			if (aFactResult.equals(Fact.tFactClassificationType.FALSE)) {
				// Positive stay positive
				// Negative stays negative
				if (this.subFactsOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)
						&& (aValueSet = true)) {

					this.subFactsOverallResult = Fact.tFactClassificationType.FALSE;
					aValueSet = true;
				}

				theServerGlobals.log("Overall impact: evaluatie van onbekend naar negatief");
				theServerGlobals.log("");

			} else if (aFactResult.equals(Fact.tFactClassificationType.TRUE)) {
				// Bij OR wordt resultaat POSITIEF: no matter waht the other will be

				theServerGlobals.log("Overall impact : evaluatie naar positief vanwege OR");
				theServerGlobals.log("");

				this.subFactsOverallResult = Fact.tFactClassificationType.TRUE;
				this.aValueSet = true;

			} else if (aFactResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
				// positive stays positive

				if (this.subFactsOverallResult.equals(Fact.tFactClassificationType.FALSE)) {
					this.subFactsOverallResult = Fact.tFactClassificationType.UNKNOWN;
					this.aValueSet = true;

					theServerGlobals.log("Overall impact : negatief wordt onbekend");
					theServerGlobals.log("");

				}
				if (this.subFactsOverallResult.equals(Fact.tFactClassificationType.UNKNOWN)) {
					if (aValueSet == false) {

						theServerGlobals.log("Overall impact: fixeer onbekend");
						theServerGlobals.log("");

						this.aValueSet = true;
					}

				}
			}

			// Break by first poitieve point ?
			if ((exhaustive == false) && (subFactsOverallResult.equals(Fact.tFactClassificationType.TRUE))
					&& (i < (this.mySubFacts.size() - 1))) {

				theServerGlobals.log("Stop voortijdig beoordeling van combinatie feit " + this.refID
						+ ". Een van onderliggende subbewijzen is TRUE en operand is OR");

				break;
			}

		}
	}

}
