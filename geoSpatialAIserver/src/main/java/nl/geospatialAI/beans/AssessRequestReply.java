package nl.geospatialAI.beans;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.Assessment.AssessmentCriterium;
import nl.geospatialAI.Assessment.Risk;
import nl.geospatialAI.DataPoints.AllowedValue;
import nl.geospatialAI.DataPoints.DataPoint;

public class AssessRequestReply {
	
	public enum assessmentStatusType {
		ERROR, 
		PENDING,
		 ASSESSING,
		 REQUESTING_DATAPOINTS,
		 COMPLETED,
		 ARCHIVED
		
	}
 
	private static int replyRefID = 0 ;
	

	public int referenceID;
	public String caseID;
	public assessmentStatusType assessmentStatus;
	public List <DataPoint> additionalDataPointsRequest;
	public List <Risk> riskAssessmentResults;
	public List <AssessmentCriterium> assessmentCriteria;
	
	
    public AssessRequestReply() {
   	 System.out.println("CREATING STUB FOR ");
    
    	CreateStubWithQuestions();
    }
    
    private void CreateStubWithQuestions() {
    	DataPoint dp1;
    	DataPoint dp2;
    	DataPoint dp3;
    	
    	Risk risk1;
    	Risk risk2;
    
    	AssessmentCriterium criteria1;
    	AssessmentCriterium criteria2;
    	AssessmentCriterium criteria3;
    	AssessmentCriterium criteria4;

    	
    	AllowedValue allowedValue1;
    	AllowedValue allowedValue2;
    	AllowedValue allowedValue3;
    	List <AllowedValue>  allowedValuesForDP3;
    	
    	//  Additional Questions
    	this.setAssessmentStatus(assessmentStatusType.REQUESTING_DATAPOINTS);
        this.additionalDataPointsRequest = new ArrayList<>();
        
        dp1 = new DataPoint();
        dp1.setQuestionText("Gaat u werkzaamheden aan huis verrichten?");
        dp1.setExplanationText("Wij vragen dit om risico's m.b.t bestemming vast te kunnen stellen");
        dp1.setDataPointType(DataPoint.DP_Type.COMMERCIALUSE);
        dp1.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
        dp1.setRequired(true);
        dp1.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
        dp1.setDefaultValue("false");
        this.additionalDataPointsRequest.add(dp1);
        
        dp2 = new DataPoint();
        dp2.setQuestionText("Hoeveel parkeerplaatsen worden voorzien?");
        dp2.setExplanationText("Wij vragen dit om het effect op verkeer in de straat vast te kunnen stellen");
        dp2.setDataPointType(DataPoint.DP_Type.NUMBEROFPARKINGLOTS);
        dp2.setDataType(DataPoint.DP_dataType.INTEGERVALUE);
        dp2.setRequired(true);
        dp2.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
        dp2.setDefaultValue("1");
        dp2.setMinValueNumber(0);
        dp2.setMinValueInteger(5);
        this.additionalDataPointsRequest.add(dp2);
        
        dp3 = new DataPoint();
        dp3.setQuestionText("Wat is de aard van uw bedrijfsactiviteiten?");
        dp3.setExplanationText("Wij vragen dit om het effect op de wijk vast te kunnen stellen");
        dp3.setDataPointType(DataPoint.DP_Type.COMMERCIALUSE);
        dp3.setDataType(DataPoint.DP_dataType.VALUESELECTION);
        dp3.setRequired(true);
        dp3.setDataPointCategory(DataPoint.DP_category.PURPOSE);
        
        
        allowedValuesForDP3 = new ArrayList<>();
        allowedValue1= new AllowedValue();
        allowedValue1.setCode("INDUS");
        allowedValue1.setDisplayText("Industrial activities");
        allowedValuesForDP3.add(allowedValue1);
        
        allowedValue2= new AllowedValue();
        allowedValue2.setCode("FINAN");
        allowedValue2.setDisplayText("Financial activities");
        allowedValuesForDP3.add(allowedValue2);
        
        
        allowedValue3= new AllowedValue();
        allowedValue3.setCode("GAMB");
        allowedValue3.setDisplayText("Gambling related activities");
        allowedValuesForDP3.add(allowedValue3);
        
        
        dp3.setAllowedValueList(allowedValuesForDP3);

        this.additionalDataPointsRequest.add(dp3);
        
        // RISKS
        
        this.riskAssessmentResults = new ArrayList<>();
        
        risk1 = new Risk();
        risk1.setDisplayName("Risico op verkeersoverlast");
        risk1.setRiskCategory(Risk.tRiskCategoryType.LIVING_ENVIRONMENT);
        risk1.setRiskValue(Risk.tRiskClassificationType.NEUTRAL);
        risk1.setPolicyReference("Art 4.5a van Bestemmingsplan");
        this.riskAssessmentResults.add(risk1);
        
        
        risk2 = new Risk();
        risk2.setRiskCategory(Risk.tRiskCategoryType.PURPOSE);
        risk2.setRiskValue(Risk.tRiskClassificationType.INCREASED);
        risk2.setDisplayName("Aantasting straatbeeld");
        risk2.setPolicyReference("Art 12.6a van Beleidskader Gemeente 2020");
        this.riskAssessmentResults.add(risk2);
     
        
        // ASSESSMENT CRITERIA
        
        this.assessmentCriteria = new ArrayList<>();
        
        criteria1 = new AssessmentCriterium();
        criteria1.setExemptionRequestAllowed(true);
        criteria1.setCriteriumCategory(AssessmentCriterium.tAssessmentCriteriumCategoryType.PURPOSE);
        criteria1.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.UNKNOWN);
        criteria1.setDisplayName("Criterium bestemming");
        this.assessmentCriteria.add(criteria1);
        
        criteria2 = new AssessmentCriterium();
        criteria2.setExemptionRequestAllowed(false);
        criteria2.setCriteriumCategory(AssessmentCriterium.tAssessmentCriteriumCategoryType.CONSTRUCTION);
        criteria2.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.UNAPPROVED);
        criteria2.setDisplayName("Criterium bouw");
        this.assessmentCriteria.add(criteria2);
        
      
        criteria3 = new AssessmentCriterium();
        criteria3.setExemptionRequestAllowed(false);
        criteria3.setCriteriumCategory(AssessmentCriterium.tAssessmentCriteriumCategoryType.ENVIRONMENTAL);
        criteria3.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.APPROVED);
        criteria3.setDisplayName("Criterium milieu");
        this.assessmentCriteria.add(criteria3);
        
        criteria4 = new AssessmentCriterium();
        criteria4.setExemptionRequestAllowed(false);
        criteria4.setCriteriumCategory(AssessmentCriterium.tAssessmentCriteriumCategoryType.LIVING_ENVIRONMENT);
        criteria4.setAssessmentResult(AssessmentCriterium.tAssessmentCriteriumClassificationType.APPROVED );
        criteria4.setDisplayName("Criterium directe leefomgeving");
        this.assessmentCriteria.add(criteria4);
        
        
        
    }
	
    
    
	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public int getReferenceID() {
		return referenceID;
	}
	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}
	public assessmentStatusType getAssessmentStatus() {
		return assessmentStatus;
	}
	public void setAssessmentStatus(assessmentStatusType assessmentStatus) {
		this.assessmentStatus = assessmentStatus;
	}

}
