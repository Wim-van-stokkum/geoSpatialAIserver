package nl.geospatialAI.Justification;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.Assessment.Fact;
import nl.geospatialAI.Assessment.Fact.tFactClassificationType;
import nl.geospatialAI.Assessment.Fact.tFactType;

public class JustificationFact {

	
	public int refID;
	public tFactType factType;
	public tFactClassificationType factResult;
	public String policyReference;
	public String displayName;
	
	public String explanation;
	public List<JustificationDatapoint>  underlyingDataPoints;
	
	public boolean needAdditionalInputForNonManualAssessment;

	public JustificationFact(Fact aFact) {
		this.refID = aFact.getRefID();
		this.factType = aFact.getFactType();
		this.displayName = aFact.getDisplayName();
		this.explanation = aFact.explainYourSelf();
		this.factResult = aFact.getFactResult();
		this.needAdditionalInputForNonManualAssessment = aFact.getNeedInput();  //TO DO REMOVE?
		underlyingDataPoints = new ArrayList<JustificationDatapoint>();
	
	}

	public void addJustificationDataPoint(JustificationDatapoint aDataPointJustification) {
		
		this.underlyingDataPoints.add(aDataPointJustification);
		
	}
	
}
