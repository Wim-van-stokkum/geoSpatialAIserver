package nl.geospatialAI.Justification;

import java.util.ArrayList;
import java.util.List;

import nl.geospatialAI.DataPoints.DataPoint;
import nl.geospatialAI.DataPoints.DataPoint.DP_Type;
import nl.geospatialAI.DataPoints.DataPoint.DP_category;
import nl.geospatialAI.DataPoints.DataPoint.DP_source;

public class JustificationDatapoint {
	public int DP_refId;
	public DP_category dataPointCategory ;
	public DP_Type dataPointType;
	public DP_source datapointSource;
	

	public String questionText;
	public String explanationText;
	public String value ;
	public List <JustificationDatapoint> usedDataPoints;

	
	public JustificationDatapoint(DataPoint aDP) {
		this.dataPointCategory = aDP.getDataPointCategory();
		this.dataPointType = aDP.getDataPointType();
		this.DP_refId = aDP.getDP_refId();
		this.dataPointCategory = aDP.getDataPointCategory();
		this.datapointSource = aDP.getDatapointSource();
		this.questionText = aDP.getQuestionText();  //TODO change to description
		this.explanationText = aDP.getExplanationText();
		this.value = aDP.getValue();	
		this.usedDataPoints = new ArrayList<JustificationDatapoint>();
	}


	public void addChildDataPointJustification(JustificationDatapoint aDataPointJustification) {
		// TODO Auto-generated method stub
		this.usedDataPoints.add(aDataPointJustification);
	}


}
