package nl.geospatialAI.Assessment;


public class Risk {
	

public enum tRiskCategoryType {
	CONSTRUCTION,
	HEALTH,
	ENVIRONMENTAL,
	LIVING_ENVIRONMENT,
	PURPOSE
	
	// to be defined in use case
}

public enum tRiskClassificationType {
	NEUTRAL,
	OBSERVATION,
	INCREASED,
	UNACCEPTABLE
}


static int risk_refID = 199;

   private String displayName;
   private int refID;
   private tRiskCategoryType riskCategory;
   private tRiskClassificationType  riskValue;
   private String policyReference;
   
   public Risk() {
	   	 System.out.println("CREATING RISK");
	   	risk_refID = risk_refID + 1;
		this.setRefID(risk_refID);
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

}
