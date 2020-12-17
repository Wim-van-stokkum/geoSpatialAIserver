package nl.geospatialAI.Justification;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.geospatialAI.Assessment.AssessmentCriterium.tAssessmentCriteriumCategoryType;
import nl.geospatialAI.Assessment.Risk;
import nl.geospatialAI.Assessment.Risk.tRiskCategoryType;
import nl.geospatialAI.Assessment.Risk.tRiskClassificationType;
import nl.geospatialAI.Case.Case;
import nl.geospatialAI.beans.JustifyRiskReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class JustificationRisk {
	public int refID;
	public tRiskCategoryType riskCategory;
	public tAssessmentCriteriumCategoryType myAssessmentCriterium;
	public String policyReference;
	public String displayName;
	public tRiskClassificationType riskValue;

	public String explanation;
	

	
    public List<JustificationProof> underlyingProofResults;

	
	public JustificationRisk(Risk aRisk, ServerGlobals theServerGlobals, Case correspondingCase,
			JustifyRiskReply justifyRiskReply) {
        String wijk;
        String policyName;
        
		
		this.refID = aRisk.getRefID();
		this.riskCategory = aRisk.getRiskCategory();
		this.myAssessmentCriterium = aRisk.getMyAssessmentCriterium();
		this.policyReference = aRisk.getPolicyReference();
		this.displayName = aRisk.getDisplayName();
		this.riskValue = aRisk.getRiskValue();
		if (aRisk.isEvaluated()) { 
		wijk = this.explanation = "Op basis van postcode is wijk: " + correspondingCase.getContextLocation().GetWijk() + " vastgesteld.";
		policyName = "Beleid van toepassing is: " + correspondingCase.thePolicy.getApplicablePolicyName();
		this.explanation = wijk + " " + policyName + " "+   aRisk.explainYourSelf();
		
		} else {
			this.explanation = "Dit risico is niet betrokken in de beoordeling.";
		}
		this.underlyingProofResults = new ArrayList<JustificationProof>();
		
		aRisk.justifyTheProofs(theServerGlobals,correspondingCase,aRisk , this );
		
		
	}


	public void addProofJustification(JustificationProof aProofJustification) {
	    this.underlyingProofResults.add(aProofJustification);
		
	}


	
}
