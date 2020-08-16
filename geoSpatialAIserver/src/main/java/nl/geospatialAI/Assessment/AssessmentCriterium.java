package nl.geospatialAI.Assessment;




public class AssessmentCriterium {


public enum tAssessmentCriteriumCategoryType {
	PURPOSE,
	CONSTRUCTION,
	ENVIRONMENTAL,
	LIVING_ENVIRONMENT,
	WATER
	
	// to be defined in use case
}

public enum tAssessmentCriteriumClassificationType {
	APPROVED,
	UNAPPROVED,
	UNKNOWN
}




static int assessCrit_refID = 499;
	
	   private String displayName;
	   private int refID;
	   private tAssessmentCriteriumCategoryType criteriumCategory;
	   private tAssessmentCriteriumClassificationType assessmentResult;
	   private boolean exemptionRequestAllowed;
	   
	   
	  public  AssessmentCriterium() {
		  // CREATING ASSESSMENTCRITIUM
		  assessCrit_refID = assessCrit_refID + 1;
		  this.setRefID(assessCrit_refID);
	  }
	   
	  
	 
	public boolean isExemptionRequestAllowed() {
		return exemptionRequestAllowed;
	}



	public void setExemptionRequestAllowed(boolean exemptionRequestAllowed) {
		this.exemptionRequestAllowed = exemptionRequestAllowed;
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
	public tAssessmentCriteriumCategoryType getCriteriumCategory() {
		return criteriumCategory;
	}
	public void setCriteriumCategory(tAssessmentCriteriumCategoryType criteriumCategory) {
		this.criteriumCategory = criteriumCategory;
	}
	public tAssessmentCriteriumClassificationType getAssessmentResult() {
		return assessmentResult;
	}
	public void setAssessmentResult(tAssessmentCriteriumClassificationType assessmentResult) {
		this.assessmentResult = assessmentResult;
	}
	   
	   
}
