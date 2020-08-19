package nl.geospatialAI.Justification;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.Assessment.Proof;
import nl.geospatialAI.Assessment.Proof.tOperandType;
import nl.geospatialAI.Assessment.Proof.tProofCategoryType;
import nl.geospatialAI.Assessment.Proof.tProofClassificationType;

public class JustificationProof {

	public int refID;
	public tProofCategoryType proofCategory;

	public String policyReference;
	public String displayName;
	public tProofClassificationType proofResult;
	public String explanation;
	public tOperandType operandApplied;
	public List<JustificationFact> underlyingFacts;
	
	
	public JustificationProof(Proof aProof){
		this.refID = aProof.getRefID();
		this.proofCategory = aProof.getProofCategory();
		this.displayName = aProof.getDisplayName();
		this.policyReference = aProof.getPolicyReference();
		this.proofResult = aProof.getProofResult();
		this.explanation = aProof.explainYourSelf();
		this.operandApplied = aProof.getOperand();
		

		underlyingFacts = new ArrayList<JustificationFact>();
	}


	public void addJustificationFact(JustificationFact aFactJustification) {
	   this.underlyingFacts.add(aFactJustification);
		
	}

}
