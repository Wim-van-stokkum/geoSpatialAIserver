package nl.geospatialAI.DataPoints;

import java.util.List;

import nl.geospatialAI.serverGlobals.ServerGlobals;


public class DataPoint {
	

public enum DP_Type {
	
	MEASUREDHEIGHT,
	MEASUREDDISTANCE,
	OBJECTHEIGHT,
	OBJECTWIDTH,
	COMMERCIALUSE,
	BUSINESSACTIVITIES,
	CHAMBREOFCOMMERCEDOSSIERNUMBER,
	SURFACECALCULATED,
	BIMFILEURL,
	BUILDINGCATEGORY,
	SHADOWSUCCEEDSDESTINATIONPANE,
	NUMBEROFPARKINGLOTS,
	CONTENTCUBICMETER,
	MATERIALTYPE,
	NUMBEROFFLOORS,
	DISTANCETOROAD

	
	// to de defined or extented in pilot
		
}

public enum DP_category {
	DIMENSION,
	PURPOSE,
	ACTIVITY,
	MATERIAL,
	CONSTRUCTION,
	OTHER
	
	// to de defined or  extented in pilot
}

public enum DP_dataType {
	TEXT,
	INTEGERVALUE,
	NUMBER,
	CURRENCY_EURO,
	FILE_URL,
	FILE_PATH,
	TRUTHVALUE,
	VALUESELECTION,
	MEMO
	
	// to de defined or  extented in pilot
}

public enum DP_maskType {
	IBAN,
	MOBILEPHONE_NL,
	CHAMBREOFCOMMERCE_DOSSIERNUMBER,
	ZIPCODENL,
	EMAILADDRESS,
	dateDDMMJJJ,
	NONE
	
	// to de defined or  extented in pilot
}

public enum DP_contentStyle {
	RADIOBUTTON,
	COMBO,
	LISTBOX,
	STANDARD
	
	// to de defined or  extented in pilot
}

public enum DP_presentionStyle {
	STANDARD,
	LARGE,
	EMPHASIS
	
	// to de defined or  extented in pilot
}


public enum DP_source {
	FORMAL_REGISTRY,
	ADMINISTRATION,
	USER,
	DIGITAL_TWIN,
	DESIGN_FILE,
	EXTERNAL_INFORMAL,
	EXTERNAL_FORMAL,
	UNKNOWN,
	OTHER
}


private static int dpRefID = 99 ;
	
	
	DP_category dataPointCategory = DP_category.OTHER;
	DP_Type dataPointType;
	DP_maskType maskType;
	DP_contentStyle contentStyle = DP_contentStyle.STANDARD;
	DP_presentionStyle presentationStyle = DP_presentionStyle.STANDARD;
	DP_source datapointSource = DP_source.UNKNOWN;
	DP_dataType dataType;
	boolean multiValued = false;
	boolean required = false;
	int DP_refId;	
	String questionText;
	String explanationText;
	String value = "";
	String defaultValue = "";
	int maxValueInteger;
	int minValueInteger;
	float minValueNumber;
	float maxValueNumber;
	List <String> values;
	List <AllowedValue> allowedValueList;
	
	
	public DataPoint() {
	
		dpRefID = dpRefID + 1;
		this.setDP_refId(dpRefID);
	   	 System.out.println("CREATING DATAPOINT: " + this.getDP_refId());
	}
	
	
	
	
	public String getDefaultValue() {
		return defaultValue;
	}




	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}




	public DP_dataType getDataType() {
		return dataType;
	}
	public void setDataType(DP_dataType dataType) {
		this.dataType = dataType;
	}
	public int getMaxValueInteger() {
		return maxValueInteger;
	}
	public void setMaxValueInteger(int maxValueInteger) {
		this.maxValueInteger = maxValueInteger;
	}
	public int getMinValueInteger() {
		return minValueInteger;
	}
	public void setMinValueInteger(int minValueInteger) {
		this.minValueInteger = minValueInteger;
	}
	public float getMinValueNumber() {
		return minValueNumber;
	}
	public void setMinValueNumber(float minValueNumber) {
		this.minValueNumber = minValueNumber;
	}
	public float getMaxValueNumber() {
		return maxValueNumber;
	}
	public void setMaxValueNumber(float maxValueNumber) {
		this.maxValueNumber = maxValueNumber;
	}

	
	public List<AllowedValue> getAllowedValueList() {
		return allowedValueList;
	}
	public void setAllowedValueList(List<AllowedValue> allowedValueList) {
		this.allowedValueList = allowedValueList;
	}
	public DP_category getDataPointCategory() {
		return dataPointCategory;
	}
	public void setDataPointCategory(DP_category dataPointCategory) {
		this.dataPointCategory = dataPointCategory;
	}
	public DP_Type getDataPointType() {
		return dataPointType;
	}
	public void setDataPointType(DP_Type dataPointType) {
		this.dataPointType = dataPointType;
	}
	public DP_maskType getMaskType() {
		return maskType;
	}
	public void setMaskType(DP_maskType maskType) {
		this.maskType = maskType;
	}
	public DP_contentStyle getContentStyle() {
		return contentStyle;
	}
	public void setContentStyle(DP_contentStyle contentStyle) {
		this.contentStyle = contentStyle;
	}
	public DP_presentionStyle getPresentationStyle() {
		return presentationStyle;
	}
	public void setPresentationStyle(DP_presentionStyle presentationStyle) {
		this.presentationStyle = presentationStyle;
	}
	public DP_source getDatapointSource() {
		return datapointSource;
	}
	public void setDatapointSource(DP_source datapointSource) {
		this.datapointSource = datapointSource;
	}
	public boolean isMultiValued() {
		return multiValued;
	}
	public void setMultiValued(boolean multiValued) {
		this.multiValued = multiValued;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public int getDP_refId() {
		return DP_refId;
	}
	public void setDP_refId(int dP_refId) {
		DP_refId = dP_refId;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getExplanationText() {
		return explanationText;
	}
	public void setExplanationText(String explanationText) {
		this.explanationText = explanationText;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}




	public boolean changeValue(String newValue, ServerGlobals theServerGlobals) {
        boolean ok = true;
        
        // TO DO all kind of validation checks
        
		theServerGlobals.log("Changing the value for : " + this.getDataPointType()
		                      + "[" + this.getDP_refId() + "] from " + this.getValue() + " into " +  newValue );
		this.setValue(newValue);
		
		return ok;
	}

}
