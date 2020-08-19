package nl.geospatialAI.DataPoints;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.Justification.JustificationDatapoint;
import nl.geospatialAI.Justification.JustificationFact;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class DataPoint {

	public enum DP_Type {

		MEASUREDHEIGHT, MEASUREDDISTANCE,

		CHAMBREOFCOMMERCEDOSSIERNUMBER, COMMERCIALUSE, BUSINESSACTIVITIES,

		BIMFILEURL,

		PURPOSE_HM_OBJECT, BUILDINGCATEGORY, DESIGN_HAS_GARDEN,

		MAX_LENGTH_DESTINATIONPANE, MAX_WIDTH_DESTINATIONPANE, SURFACE_CALCULATED_DESTINATIONPANE,

		MAX_WIDTH_OBJECT, MAX_LENGTH_OBJECT, SURFACE_CALCULATED_OBJECT,

		MAX_WIDTH_GARDEN, MAX_LENGTH_GARDEN, SURFACE_CALCULATED_GARDEN,

		SURFACE_TILES_GARDEN, PERC_WATER_PERM_GARDEN, TOTAL_SURFACE_WATER_NON_PERMABLE,
		PROFESSION_AT_HOME

		// to de defined or extented in pilot

	}

	public enum DP_category {
		DIMENSION, PURPOSE, DESIGN, ACTIVITY, MATERIAL, CONSTRUCTION, DIRECT_ENVIRONMENT, OTHER

		// to de defined or extented in pilot
	}

	public enum DP_Status {
		REQUESTED, PROVIDED, DERIVED

		// to de defined or extented in pilot
	}

	public enum DP_dataType {
		TEXT, INTEGERVALUE, NUMBER, CURRENCY_EURO, FILE_URL, FILE_PATH, TRUTHVALUE, VALUESELECTION, MEMO

		// to de defined or extented in pilot
	}

	public enum DP_maskType {
		IBAN, MOBILEPHONE_NL, CHAMBREOFCOMMERCE_DOSSIERNUMBER, ZIPCODENL, EMAILADDRESS, dateDDMMJJJ, NONE

		// to de defined or extented in pilot
	}

	public enum DP_contentStyle {
		RADIOBUTTON, COMBO, LISTBOX, STANDARD

		// to de defined or extented in pilot
	}

	public enum DP_presentionStyle {
		STANDARD, LARGE, EMPHASIS

		// to de defined or extented in pilot
	}

	public enum DP_source {
		FORMAL_REGISTRY, ADMINISTRATION, USER, DIGITAL_TWIN, DESIGN_FILE, EXTERNAL_INFORMAL, EXTERNAL_FORMAL, UNKNOWN,
		RULE_ENGINE, OTHER
	}

	private static int dpRefID = 99;

	DP_category dataPointCategory = DP_category.OTHER;
	DP_Type dataPointType;
	DP_maskType maskType;
	DP_contentStyle contentStyle = DP_contentStyle.STANDARD;
	DP_presentionStyle presentationStyle = DP_presentionStyle.STANDARD;
	DP_source datapointSource = DP_source.UNKNOWN;
	DP_dataType dataType;

	// @JsonIgnore
	DP_Status status;

	// @JsonIgnore
	boolean isAskable = true;

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
	List<String> values;
	List<AllowedValue> allowedValueList;
	protected List<DataPoint> usedDataPoints;

	public DataPoint() {

		dpRefID = dpRefID + 1;
		this.setDP_refId(dpRefID);
		System.out.println("CREATING DATAPOINT: " + this.getDP_refId());
		isAskable = true;
		usedDataPoints = new ArrayList<DataPoint>();
	}

	public void resetUsedDataPoints() {
		this.usedDataPoints.clear();
	}

	public void recordUsedDataPoint(DataPoint aUsedDP) {
		this.usedDataPoints.add(aUsedDP);
	}

	public DataPoint(DataPoint.DP_Type aSpecificType) {
		dpRefID = dpRefID + 1;
		this.setDP_refId(dpRefID);
		System.out.println("CREATING DATAPOINT: " + this.getDP_refId());
		isAskable = true;
		usedDataPoints = new ArrayList<DataPoint>();
		this.initDataPoint(aSpecificType);

	}

	public boolean isAskable() {
		return isAskable;
	}

	public void setAskable(boolean isAskable) {
		this.isAskable = isAskable;

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
		if ((newValue != "") && (newValue != "null") && (newValue != "?")) {
			theServerGlobals.log("Changing the value for : " + this.getDataPointType() + "[" + this.getDP_refId()
					+ "] from " + this.getValue() + " into " + newValue);
			this.setStatus(DataPoint.DP_Status.PROVIDED);
			this.setValue(newValue);
		}
		return ok;
	}

	public void addAllowedValue(AllowedValue newAllowedValue) {
		if (this.allowedValueList == null) {
			this.allowedValueList = new ArrayList<AllowedValue>();
		}
		this.allowedValueList.add(newAllowedValue);

	}

	public void setStatus(DP_Status theStatus) {
		this.status = theStatus;

	}

	public DP_Status getStatus() {
		return this.status;

	}

	@JsonIgnore
	public boolean getConvertedValueBoolean() {
		boolean val;

		val = false;
		if (this.getDataType().equals(DataPoint.DP_dataType.TRUTHVALUE)) {
			if (this.getValue().equals("true") || this.getValue().equals("TRUE")) {
				val = true;
			}
		} else
			ServerGlobals.getInstance()
					.log("ERROR: Boolean value asked for non boolean type DataPoint: " + this.getQuestionText());

		return val;
	}

	public boolean hasValue() {
		boolean hasVal;

		hasVal = false;
		// Ignore requested datapoints but include the derived (non askable ones)
		if (this.getStatus().equals(DataPoint.DP_Status.PROVIDED) || (this.isAskable == false)) {
			if ((this.getValue().equals("") == false) && (this.getValue().equals("UNKNOWN") == false)
					&& (this.getValue().equals("null ") == false) && (this.getValue().equals("NULL ") == false)
					&& (this.getValue().equals("?") == false)) {
				hasVal = true;
			}

		}
		return hasVal;
	}

	@JsonIgnore
	public double getConvertedValueDouble() {

		double val;

		val = 0;
		if (this.getDataType().equals(DataPoint.DP_dataType.NUMBER)) {
			if (this.hasValue()) {
				val = Double.parseDouble(this.getValue());
			} else if (this.hasDefaultValue()) {
				val = Double.parseDouble(this.getDefaultValue());
			} else {
				ServerGlobals.getInstance().log("ERROR: Missing value while expecting it: " + this.getQuestionText());
			}

		} else
			ServerGlobals.getInstance()
					.log("ERROR: Number value asked for non Number type DataPoint: " + this.getQuestionText());

		return val;
	}

	@JsonIgnore
	public Integer getConvertedValueInteger() {

		Integer val;

		val = 0;
		if (this.getDataType().equals(DataPoint.DP_dataType.INTEGERVALUE)) {
			if (this.hasValue()) {
				val = Integer.valueOf(this.getValue());
			} else if (this.hasDefaultValue()) {
				val = Integer.valueOf(this.getDefaultValue());
			}
			ServerGlobals.getInstance().log("ERROR: Missing value while expecting it: " + this.getQuestionText());

		} else
			ServerGlobals.getInstance()
					.log("ERROR: Integer value asked for non integer type DataPoint: " + this.getQuestionText());

		return val;
	}

	@JsonIgnore
	public String getConvertedValueString() {

		String val;

		val = "UNKNOWN";
		ServerGlobals.getInstance().log(this.dataPointType.toString());
		ServerGlobals.getInstance().log(this.dataType.toString());
		if (this.getDataType().equals(DataPoint.DP_dataType.TEXT)
				|| this.getDataType().equals(DataPoint.DP_dataType.FILE_URL)
				||  this.getDataType().equals(DataPoint.DP_dataType.VALUESELECTION)) {
	

			if (this.hasValue()) {
				val = this.getValue();
			} else if (this.hasDefaultValue()) {
				val = this.getDefaultValue();
			}
			ServerGlobals.getInstance().log("ERROR: Missing value while expecting it: " + this.getQuestionText());

		} else
			ServerGlobals.getInstance()
					.log("ERROR: String value asked for non String type DataPoint: " + this.getQuestionText());

		return val;
	}

	private boolean hasDefaultValue() {
		boolean hasVal;

		hasVal = false;
		if (this.getStatus().equals(DataPoint.DP_Status.PROVIDED)) {
			if ((this.getDefaultValue().equals("") == false) && (this.getDefaultValue().equals("UNKNOWN") == false)
					&& (this.getDefaultValue().equals("null ") == false)
					&& (this.getDefaultValue().equals("NULL ") == false)) {
				hasVal = true;
			}

		}
		return hasVal;

	}

	public void deriveValue(ServerGlobals theServiceGlobals, AssessRequestReply theReply, Case theCase) {
		// Standard no derivation

	}

	// ====================================================================================
	// ====================================================================================
	// ================================== INITS ===================================
	// ====================================================================================
	// ====================================================================================

	private void initDataPoint_BIMFILEURL() {
		System.out.println("INIT BIMFILEURL");

		DataPoint newDP;
		AllowedValue newValue;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.OTHER);
		newDP.setDataType(DataPoint.DP_dataType.FILE_URL);
		newDP.setDataPointType(DataPoint.DP_Type.BIMFILEURL);

		newDP.setExplanationText("De locatie of URL waar evntueel ontwerp bestand te vinden is");
		newDP.setQuestionText("Selecteer potentiele BIM file");

		newValue = new AllowedValue();
		newValue.setCode("NO_BIM");
		newValue.setDisplayText("Gebruik geen BIM file, BIM niet ontvangen");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("WONING.BIM");
		newValue.setDisplayText("WONING.BIM");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("KANTOOR.BIM");
		newValue.setDisplayText("WONING.BIM");
		newDP.addAllowedValue(newValue);
	}

	private void initDataPoint_PURPOSE_HM_OBJECT() {
		DataPoint newDP;
		AllowedValue newValue;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.PURPOSE);
		newDP.setDataType(DataPoint.DP_dataType.VALUESELECTION);
		newDP.setDataPointType(DataPoint.DP_Type.PURPOSE_HM_OBJECT);

		newDP.setExplanationText("Nodig om type woning af te leiden");
		newDP.setQuestionText("Welke type activiteit wordt gepland for dit object?");

		newValue = new AllowedValue();
		newValue.setCode("WONEN");
		newValue.setDisplayText("Gebruikt voor wonen");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("BRANDSTOF");
		newValue.setDisplayText("Gebruikt voor opslag of verkoop brandstoffen");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("DRIJVEN");
		newValue.setDisplayText("Gebruikt voor drijven op water");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("KANTOORACTIVITEITEN");
		newValue.setDisplayText("Gebruikt voor kantoor activiteiten");
		newDP.addAllowedValue(newValue);
	}

	private void initDataPoint_BUILDINGCATEGORY() {
		DataPoint newDP;
		AllowedValue newValue;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataPointType(DataPoint.DP_Type.BUILDINGCATEGORY);
		newDP.setDataType(DataPoint.DP_dataType.VALUESELECTION);
		newDP.setDefaultValue("WOONGEBOUW");
		newDP.setExplanationText("Object bestemd te dienen als hoofdverblijf voor wonen");
		newDP.setQuestionText("Welk type heeft het the plaatsen hoofdgebouw?");

		newValue = new AllowedValue();
		newValue.setCode("BEDRIJFSGEBOUW");
		newValue.setDisplayText("Bedrijfsgebouw");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("HANDELSGEBOUW");
		newValue.setDisplayText("Handelsgebouw");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("HORECAGEBOUW");
		newValue.setDisplayText("Horeca gebouw");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("KANTOORGEBOUW");
		newValue.setDisplayText("Kantoorgebouw");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("WOONGEBOUW");
		newValue.setDisplayText("Woongebouw");
		newDP.addAllowedValue(newValue);
	}

	private void initDataPoint_MEASUREDHEIGHT() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MEASUREDHEIGHT);
		newDP.setDefaultValue("0.0");
		newDP.setExplanationText("Meet hoogte vanaf maaiveld");
		newDP.setQuestionText("Wat de hoogte van het object?");
	}

	private void initDataPoint_MAX_WIDTH_OBJECT() {
		DataPoint newDP;

		newDP = this;

		newDP.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_OBJECT);

		newDP.setQuestionText("Maximale breedte van object");
		newDP.setExplanationText("Nodig voor berekenen standaard oppervlakte");
	}

	private void initDataPoint_MAX_LENGTH_OBJECT() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_OBJECT);

		newDP.setQuestionText("Maximale lengte van object");
		newDP.setExplanationText("Nodig voor berekenen standaard oppervlakte");

	}

	private void initDataPoint_SURFACE_CALCULATED_OBJECT() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.SURFACE_CALCULATED_OBJECT);

		newDP.setQuestionText("Wat is de oppervlakte van de footpint van het object?");
		newDP.setExplanationText("Wij vragen dit de verhouding tussen object en perceel te beoordelen");
	}

	private void initDataPoint_MAX_WIDTH_DESTINATIONPANE() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_DESTINATIONPANE);

		newDP.setExplanationText("Gebruik centimers. Nodig voor berekenen standaard oppervlakte perceel");
		newDP.setQuestionText("Maximale breedte bouwperceel");

	}

	private void initDataPoint_MAX_LENGTH_DESTINATIONPANE() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_DESTINATIONPANE);

		newDP.setExplanationText("Gebruik centimers. Nodig voor berekenen standaard oppervlakte perceel");
		newDP.setQuestionText("Maximale lengte bouwperceel");

	}

	private void initDataPoint_SURFACE_CALCULATED_DESTINATIONPANE() {
		DataPoint newDP;
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.SURFACE_CALCULATED_DESTINATIONPANE);

		newDP.setQuestionText("Wat is de oppervlakte van het bouwperceel?");
		newDP.setExplanationText("Wij vragen dit de verhouding tussen object en perceel te beoordelen");
	}

	private void initDataPoint_COMMERCIALUSE() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
		newDP.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.COMMERCIALUSE);
		newDP.setDefaultValue("false");

		newDP.setQuestionText("Gaat u werkzaamheden aan huis verrichten?");
		newDP.setExplanationText("Wij vragen dit om risico's m.b.t bestemming vast te kunnen stellen");

	}

	private void initDataPoint_BUSINESSACTIVITIES() {
		DataPoint newDP;
		AllowedValue newValue;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
		newDP.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.BUSINESSACTIVITIES);
		newDP.setDefaultValue("false");

		newDP.setQuestionText("Wat is de aard van uw bedrijfsactiviteiten?");
		newDP.setExplanationText("Wij vragen dit om het effect/risico op de wijk vast te kunnen stellen");

		newValue = new AllowedValue();
		newValue.setCode("WEBSHOP");
		newValue.setDisplayText("Webshop");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("ADMIN");
		newValue.setDisplayText("Administratie");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("EDUCATION");
		newValue.setDisplayText("Eductatie");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("MANUFACT");
		newValue.setDisplayText("Constructie/productie");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("FINANCE");
		newValue.setDisplayText("Financiele diensten");
		newDP.addAllowedValue(newValue);
	}

	private void initDataPoint_MAX_WIDTH_GARDEN() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_GARDEN);

		newDP.setQuestionText("Wat is de breedte van de tuin?");
		newDP.setExplanationText("Wij vragen dit de oppervlakte van de tuin vast te stellen");

	}

	private void initDataPoint_MAX_LENGTH_GARDEN() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_GARDEN);

		newDP.setQuestionText("Wat is de lengte van de tuin?");
		newDP.setExplanationText("Wij vragen dit de oppervlakte van de tuin vast te stellen");
	}

	private void initDataPoint_SURFACE_CALCULATED_GARDEN() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.SURFACE_CALCULATED_GARDEN);

		newDP.setQuestionText("Wat is de oppervlakte van de tuin?");
		newDP.setExplanationText("Wij vragen dit de watertoelatendheid vast te stellen");
	}

	private void initDataPoint_CHAMBREOFCOMMERCEDOSSIERNUMBER() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.OTHER);
		newDP.setDataType(DataPoint.DP_dataType.TEXT);
		newDP.setDataPointType(DataPoint.DP_Type.CHAMBREOFCOMMERCEDOSSIERNUMBER);

		newDP.setQuestionText("Wat is de oppervlakte van de tuin?");
		newDP.setExplanationText("Wij vragen dit de watertoelatendheid vast te stellen");
	}

	private void initDataPoint_PERC_WATER_PERM_GARDEN() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.OTHER);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.PERC_WATER_PERM_GARDEN);

		newDP.setQuestionText("Percentage waterdoorlatendheid tuin?");
		newDP.setExplanationText("Wij vragen dit de totale watertoelatendheid vast te stellen");
	}

	private void initDataPoint_SURFACE_TILES_GARDEN() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.SURFACE_TILES_GARDEN);

		newDP.setQuestionText("Oppervlakte verhanding tuin");
		newDP.setExplanationText("Wij vragen dit de totale watertoelatendheid vast te stellen van de tuin");

	}

	private void initDataPoint_DESIGN_HAS_GARDEN() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.DESIGN_HAS_GARDEN);

		newDP.setQuestionText("Is er het voornemen een tuin aan te leggen?");
		newDP.setExplanationText("Wij vragen dit de totale watertoelatendheid vast te stellen van de tuin");

	}

	private void initDataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE() {
		DataPoint newDP;

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.TOTAL_SURFACE_WATER_NON_PERMABLE);
		newDP.setAskable(false);

		newDP.setQuestionText("Totale Oppervlakte niet water toelatend");
		newDP.setExplanationText("Nodig om te beoordelen of deze binnen normen valt");

	}
	

	private void initDataPoint_PROFESSION_AT_HOME() {
		DataPoint newDP;
		AllowedValue newValue;
		
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
		newDP.setDataType(DataPoint.DP_dataType.VALUESELECTION);
		newDP.setDataPointType(DataPoint.DP_Type.PROFESSION_AT_HOME);
		newDP.setDefaultValue("OTHER");
		newDP.setAskable(true);

		newDP.setQuestionText("Beroep uitgeoefend aan huis");
		newDP.setExplanationText("Nodig om te beoordelen of dit beroep risico's met zich mee brengt voor de omgeving");

		
		newValue = new AllowedValue();
		newValue.setCode("CONSULTANCY");
		newValue.setDisplayText("Consultancy");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("COACHING");
		newValue.setDisplayText("Coaching");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("WEBSHOP");
		newValue.setDisplayText("Webshop");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("ACCOUNTANT");
		newValue.setDisplayText("Accountancy");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("DENTIST");
		newValue.setDisplayText("Tandarts");
		newDP.addAllowedValue(newValue);
		
		newValue = new AllowedValue();
		newValue.setCode("BARBER");
		newValue.setDisplayText("Kapper");
		newDP.addAllowedValue(newValue);
		
		newValue = new AllowedValue();
		newValue.setCode("OTHER");
		newValue.setDisplayText("Ander beroep");
		newDP.addAllowedValue(newValue);
	}


	public void initDataPoint(DataPoint.DP_Type aDPtype) {
		if (aDPtype.equals(DataPoint.DP_Type.BIMFILEURL)) {
			this.initDataPoint_BIMFILEURL();
		} else if (aDPtype.equals(DataPoint.DP_Type.PURPOSE_HM_OBJECT)) {
			this.initDataPoint_PURPOSE_HM_OBJECT();
		} else if (aDPtype.equals(DataPoint.DP_Type.BUILDINGCATEGORY)) {
			this.initDataPoint_BUILDINGCATEGORY();
		} else if (aDPtype.equals(DataPoint.DP_Type.MEASUREDHEIGHT)) {
			this.initDataPoint_MEASUREDHEIGHT();
		} else if (aDPtype.equals(DataPoint.DP_Type.MAX_WIDTH_OBJECT)) {
			this.initDataPoint_MAX_WIDTH_OBJECT();
		} else if (aDPtype.equals(DataPoint.DP_Type.MAX_LENGTH_OBJECT)) {
			this.initDataPoint_MAX_LENGTH_OBJECT();
		} else if (aDPtype.equals(DataPoint.DP_Type.SURFACE_CALCULATED_OBJECT)) {
			this.initDataPoint_SURFACE_CALCULATED_OBJECT();
		} else if (aDPtype.equals(DataPoint.DP_Type.MAX_WIDTH_DESTINATIONPANE)) {
			this.initDataPoint_MAX_WIDTH_DESTINATIONPANE();
		} else if (aDPtype.equals(DataPoint.DP_Type.MAX_LENGTH_DESTINATIONPANE)) {
			this.initDataPoint_MAX_LENGTH_DESTINATIONPANE();
		} else if (aDPtype.equals(DataPoint.DP_Type.SURFACE_CALCULATED_DESTINATIONPANE)) {
			this.initDataPoint_SURFACE_CALCULATED_DESTINATIONPANE();
		} else if (aDPtype.equals(DataPoint.DP_Type.COMMERCIALUSE)) {
			this.initDataPoint_COMMERCIALUSE();
		} else if (aDPtype.equals(DataPoint.DP_Type.BUSINESSACTIVITIES)) {
			this.initDataPoint_BUSINESSACTIVITIES();
		} else if (aDPtype.equals(DataPoint.DP_Type.MAX_WIDTH_GARDEN)) {
			this.initDataPoint_MAX_WIDTH_GARDEN();
		} else if (aDPtype.equals(DataPoint.DP_Type.MAX_LENGTH_GARDEN)) {
			this.initDataPoint_MAX_LENGTH_GARDEN();
		} else if (aDPtype.equals(DataPoint.DP_Type.SURFACE_CALCULATED_GARDEN)) {
			this.initDataPoint_SURFACE_CALCULATED_GARDEN();
		} else if (aDPtype.equals(DataPoint.DP_Type.CHAMBREOFCOMMERCEDOSSIERNUMBER)) {
			this.initDataPoint_CHAMBREOFCOMMERCEDOSSIERNUMBER();
		} else if (aDPtype.equals(DataPoint.DP_Type.PERC_WATER_PERM_GARDEN)) {
			this.initDataPoint_PERC_WATER_PERM_GARDEN();
		} else if (aDPtype.equals(DataPoint.DP_Type.SURFACE_TILES_GARDEN)) {
			this.initDataPoint_SURFACE_TILES_GARDEN();
		} else if (aDPtype.equals(DataPoint.DP_Type.DESIGN_HAS_GARDEN)) {
			this.initDataPoint_DESIGN_HAS_GARDEN();
		} else if (aDPtype.equals(DataPoint.DP_Type.TOTAL_SURFACE_WATER_NON_PERMABLE)) {
			this.initDataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE();
		} else if (aDPtype.equals(DataPoint.DP_Type.PROFESSION_AT_HOME)) {
			this.initDataPoint_PROFESSION_AT_HOME();
		}
			ServerGlobals.getInstance().log("ERROR: No init entry for " + aDPtype);

	}

	public void justifyDataPoint(ServerGlobals theServerGlobals, Case correspondingCase,
			JustificationFact myFactJustification) {

		int i;
		DataPoint aUsedDataPoint;
		
		JustificationDatapoint myDataPointJustification;

		myDataPointJustification = new JustificationDatapoint(this);
		myFactJustification.addJustificationDataPoint(myDataPointJustification);

		// To do Sub DataPoints
		for (i = 0; i < this.usedDataPoints.size(); i++) {
			aUsedDataPoint = this.usedDataPoints.get(i);
			aUsedDataPoint.justifyChildDataPoint(theServerGlobals,correspondingCase, myDataPointJustification); 
		}
	}

	private void justifyChildDataPoint(ServerGlobals theServerGlobals, Case correspondingCase,
			JustificationDatapoint myDataPointJustification) {
		JustificationDatapoint ChildDataPointJustification;
		DataPoint aUsedDataPoint;
        int i;
        
        
		ChildDataPointJustification = new JustificationDatapoint(this);
		myDataPointJustification.addChildDataPointJustification(ChildDataPointJustification);
		// To do Sub DataPoints
				for (i = 0; i < this.usedDataPoints.size(); i++) {
					aUsedDataPoint = this.usedDataPoints.get(i);
					aUsedDataPoint.justifyChildDataPoint(theServerGlobals,correspondingCase, myDataPointJustification); 
				}
		
	}

}
