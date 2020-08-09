package nl.geospatialAI.DataPoints;

import nl.geospatialAI.DataPoints.DataPoint.DP_source;

public class DataPointValue {

	
	
	int DP_refId;	
	String value = "";
	DP_source valueSource = DP_source.UNKNOWN;
	
	public DP_source getValueSource() {
		return valueSource;
	}
	public void setValueSource(DP_source valueSource) {
		this.valueSource = valueSource;
	}
	public int getDP_refId() {
		return DP_refId;
	}
	public void setDP_refId(int dP_refId) {
		DP_refId = dP_refId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
