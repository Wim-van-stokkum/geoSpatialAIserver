package nl.geospatialAI.DataPoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.Justification.JustificationDatapoint;
import nl.geospatialAI.Justification.JustificationFact;
import nl.geospatialAI.beans.AssessRequestContext;
import nl.geospatialAI.beans.AssessRequestReply;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class DataPoint {

	public enum DP_Type {

		MEASUREDHEIGHT, MEASUREDDISTANCE,

		CHAMBREOFCOMMERCEDOSSIERNUMBER, REGISTEREDDUTCHKVK, COMMERCIALUSE, BUSINESSACTIVITIES,

		BIMFILEURL,WORK_FROM_HOME,

		PURPOSE_HM_OBJECT, BUILDINGCATEGORY, DESIGN_HAS_GARDEN, SBI_ORGANISATION,

		MAX_LENGTH_DESTINATIONPANE, MAX_WIDTH_DESTINATIONPANE, SURFACE_CALCULATED_DESTINATIONPANE,

		MAX_WIDTH_OBJECT, MAX_LENGTH_OBJECT, SURFACE_CALCULATED_OBJECT,

		MAX_WIDTH_GARDEN, MAX_LENGTH_GARDEN, SURFACE_CALCULATED_GARDEN,

		SURFACE_TILES_GARDEN, PERC_WATER_PERM_GARDEN, TOTAL_SURFACE_WATER_NON_PERMABLE, PROFESSION_AT_HOME

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

	protected HashMap<String, String> questionTextPerRole;
	protected HashMap<String, String> explainTextPerRole ;

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
		questionTextPerRole = new HashMap<String, String>();
		explainTextPerRole = new HashMap<String, String>();
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
		questionTextPerRole = new HashMap<String, String>();
		explainTextPerRole = new HashMap<String, String>();
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

		if (this.getDataType().equals(DataPoint.DP_dataType.TEXT)
				|| this.getDataType().equals(DataPoint.DP_dataType.FILE_URL)
				|| this.getDataType().equals(DataPoint.DP_dataType.VALUESELECTION)) {

			if (this.hasValue()) {
				val = this.getValue();
			} else if (this.hasDefaultValue()) {
				val = this.getDefaultValue();
			} else
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

		DataPoint newDP;
		AllowedValue newValue;

		newDP = this;
		
		questionTextPerRole.put("AANVRAGER", "Hoe heet uw bestand met het ontwerp?");
		questionTextPerRole.put("BEOORDELAAR", "Hoe heet de BIM file?");

		explainTextPerRole.put("AANVRAGER",
				"Als u geen ontwerp bestand heeft, kies dan voor GEEN.");
		explainTextPerRole.put("BEOORDELAAR",
				"Raadpleeg de repository met ingediende ontwerp bestanden.");
		
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
		newValue.setCode("WONING_TUIN.BIM");
		newValue.setDisplayText("WONING MET TUIN.BIM");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("KANTOOR.BIM");
		newValue.setDisplayText("WONING.BIM");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("SCHOOL.BIM");
		newValue.setDisplayText("SCHOOL.BIM");
		newDP.addAllowedValue(newValue);
	}

	private void initDataPoint_PURPOSE_HM_OBJECT() {
		DataPoint newDP;
		AllowedValue newValue;
		
		
		questionTextPerRole.put("AANVRAGER", "Voor welk doel wilt u het object gebruiken?");
		questionTextPerRole.put("BEOORDELAAR", "Welke type activiteit wordt gepland for dit object?");

		explainTextPerRole.put("AANVRAGER",
				"Dit gegeven hebben we nodig om te controleren of deze bestemming is toegestaan.");
		explainTextPerRole.put("BEOORDELAAR",
				"Dit gegeven is nodig om de bestemming te valideren tegen beleid bestemmingsplan.");

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
		newValue.setCode("EDUCATIE");
		newValue.setDisplayText("Educatie of scholing");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("PRODUCTIE");
		newValue.setDisplayText("Productie van goederen");
		newDP.addAllowedValue(newValue);

		newValue = new AllowedValue();
		newValue.setCode("KANTOORACTIVITEITEN");
		newValue.setDisplayText("Gebruikt voor kantoor activiteiten");
		newDP.addAllowedValue(newValue);
	}

	public void setForUser(AssessRequestContext.tUsertype usertype) {
		if (usertype.equals(AssessRequestContext.tUsertype.BEOORDELAAR)) {

			this.setDefaultValue(this.questionTextPerRole.get("BEOORDELAAR"));
			this.setExplanationText(this.explainTextPerRole.get("BEOORDELAAR"));

		} else {
			if ((this.questionTextPerRole.get("AANVRAGER") != null)) {
				this.setExplanationText(this.explainTextPerRole.get("AANVRAGER"));
				this.setQuestionText(this.questionTextPerRole.get("AANVRAGER"));
			}
			else {
				//doe ff niets
			}
		}

	}

	private void initDataPoint_BUILDINGCATEGORY() {
		DataPoint newDP;
		AllowedValue newValue;

		newDP = this;
		questionTextPerRole.put("AANVRAGER", "Wat voor type gebouw wilt u plaatsen?");
		questionTextPerRole.put("BEOORDELAAR", "Wat voor type object is het hoofdgebouw?");

		explainTextPerRole.put("AANVRAGER",
				"Wij vragen dit om te bepalen aan welke normen het gebouw en de bestemming dient te voldoen.");
		explainTextPerRole.put("BEOORDELAAR",
				"Gebruik de classificatie zoals beschreven in definities beleidsdocument.");

		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataPointType(DataPoint.DP_Type.BUILDINGCATEGORY);
		newDP.setDataType(DataPoint.DP_dataType.VALUESELECTION);
		newDP.setDefaultValue("WOONGEBOUW");
		newDP.setExplanationText(this.explainTextPerRole.get("AANVRAGER"));
		newDP.setQuestionText(this.questionTextPerRole.get("AANVRAGER"));

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
		
		
		questionTextPerRole.put("AANVRAGER", "Hoe hoog wilt u het gebouw gaan bouwen?");
		questionTextPerRole.put("BEOORDELAAR", "De hoogte van het object");
		explainTextPerRole.put("AANVRAGER",
				"Gaat om hoogte vanaf maaiveld tot aan hoogste punt in het ontwerp in meters");
		explainTextPerRole.put("BEOORDELAAR",
				"De hoogte zoals gedefinieerd in de definities van het beleid");

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

		questionTextPerRole.put("AANVRAGER", "Wat wordt de maximale breedte van het gebouw?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de maximale breedte van het object?");
		explainTextPerRole.put("AANVRAGER",
				"De breedte het gebouw in meters");
		explainTextPerRole.put("BEOORDELAAR",
				"De breedte zoals gedefinieerd in de definities van het beleid");
		
		newDP = this;

		newDP.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_OBJECT);

		newDP.setQuestionText("Maximale breedte van object");
		newDP.setExplanationText("Nodig voor berekenen standaard oppervlakte");
	}

	private void initDataPoint_MAX_LENGTH_OBJECT() {
		DataPoint newDP;

		questionTextPerRole.put("AANVRAGER", "Wat wordt de maximale lengte van het gebouw?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de maximale lengte van het object?");
		explainTextPerRole.put("AANVRAGER",
				"De lengte het gebouw in meters");
		explainTextPerRole.put("BEOORDELAAR",
				"De lengte zoals gedefinieerd in de definities van het beleid");
		
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_OBJECT);

		newDP.setQuestionText("Maximale lengte van object");
		newDP.setExplanationText("Nodig voor berekenen standaard oppervlakte");

	}

	private void initDataPoint_SBI_ORGANISATION() {
		DataPoint newDP;

		questionTextPerRole.put("AANVRAGER", "Wat is de primaire SBI die de KVK heeft geregistreerd?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de primaire SBI code?");
		explainTextPerRole.put("AANVRAGER",
				"De SBI code zoals geregistreerd in de Kamer van Koophandel");
		explainTextPerRole.put("BEOORDELAAR",
				"De SBI code geregistreerd in Kamer van Koophandel");
		
		
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.OTHER);
		newDP.setDataType(DataPoint.DP_dataType.INTEGERVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.SBI_ORGANISATION);

		newDP.setQuestionText("Onder welke SBI code staat uw organisatie geregistreerd?");
		newDP.setExplanationText("Wij vragen dit om de type activiteiten in het object af te leiden");
	}

	private void initDataPoint_SURFACE_CALCULATED_OBJECT() {
		DataPoint newDP;

		questionTextPerRole.put("AANVRAGER", "Wat wordt de oppervlakte van de gebouw(en)?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de oppervlakte van hoofd- en bijgebouw?");
		explainTextPerRole.put("AANVRAGER",
				"Het gaat om oppervlakte fundering van hoofd- en bijgebouwen (schuren etc).");
		explainTextPerRole.put("BEOORDELAAR",
				"Het gaat om oppervlakte fundering van hoofd- en bijgebouwen (schuren etc).");
		
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.SURFACE_CALCULATED_OBJECT);

		newDP.setQuestionText("Wat is de oppervlakte van de footpint van het object?");
		newDP.setExplanationText("Wij vragen dit de verhouding tussen object en perceel te beoordelen");
	}

	private void initDataPoint_MAX_WIDTH_DESTINATIONPANE() {
		DataPoint newDP;
		
		questionTextPerRole.put("AANVRAGER", "Wat is de oppervlakte van het perceel waarop het gebouw wordt geplaatst.");
		questionTextPerRole.put("BEOORDELAAR", "Wat is oppervlakte van bouwperceel");
		explainTextPerRole.put("AANVRAGER",
				"Nodig om de verhouding vast te stellen tussen bebouwd en onbebouwd deel.");
		explainTextPerRole.put("BEOORDELAAR",
				"Nodig om de verhouding vast te stellen tussen bebouwd en onbebouwd deel. Raadpleeg kadaster");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_DESTINATIONPANE);

		newDP.setExplanationText("Gebruik meters. Nodig voor berekenen standaard oppervlakte perceel");
		newDP.setQuestionText("Maximale breedte bouwperceel in meters.");

	}

	private void initDataPoint_MAX_LENGTH_DESTINATIONPANE() {
		DataPoint newDP;
		

		questionTextPerRole.put("AANVRAGER", "Wat is de maximale lengte van het perceel waarop het gebouw wordt geplaatst.");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de maximale lengte van het bouwperceel");
		explainTextPerRole.put("AANVRAGER",
				"Nodig om de oppervlakte vast te stellen van het bouwperceel");
		explainTextPerRole.put("BEOORDELAAR",
				"Nodig om de oppervlakte vast te stellen van het bouwperceel");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.CONSTRUCTION);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_DESTINATIONPANE);

		newDP.setExplanationText("Gebruik meters. Nodig voor berekenen standaard oppervlakte perceel");
		newDP.setQuestionText("Maximale lengte bouwperceel in meters.");

	}

	private void initDataPoint_SURFACE_CALCULATED_DESTINATIONPANE() {
		questionTextPerRole.put("AANVRAGER", "Wat is de oppervlakte van het bouwperceel?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de oppervlakte van het bouwperceel?");
		explainTextPerRole.put("AANVRAGER",
				"Nodig om de verhouding tussen object en bouwperceel vast te stellen");
		explainTextPerRole.put("BEOORDELAAR",
				"Oppervlakte bouwperceel is nodig om verhouding bouwing/niet bebouwing vast te stellen");
		
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
		
		questionTextPerRole.put("AANVRAGER", "Gaat u werkzaamheden aan huis verrichten?");
		questionTextPerRole.put("BEOORDELAAR", "Voor welk doel wordt het gebouw gebruikt?");
		explainTextPerRole.put("AANVRAGER",
				"Nodig om te beoordelen of deze werkzaamheden passen bij gebouw en omgeving");
		explainTextPerRole.put("BEOORDELAAR",
				"Nodig om risico bestemming vast te stellen");
		
		
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
		newDP.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.COMMERCIALUSE);
		newDP.setDefaultValue("false");

		newDP.setQuestionText("Gaat u werkzaamheden aan huis verrichten?");
		newDP.setExplanationText("Wij vragen dit om risico's m.b.t bestemming vast te kunnen stellen");

	}

	
	

	private void initDataPoint_WORK_FROM_HOME() {
		DataPoint newDP;
		
		questionTextPerRole.put("AANVRAGER", "Gaat u vanuit huis werken?");
		questionTextPerRole.put("BEOORDELAAR", "Gaat de aanvrager vanuit huis werken?");
		explainTextPerRole.put("AANVRAGER",
				"Er worden eisen gesteld aan het soort beroepen die vanuit huis mogen worden uitgeoefend");
		explainTextPerRole.put("BEOORDELAAR",
				"Indien ja, dan wordt getoetst of beroep in categorie B van beroepen valt.");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
		newDP.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.WORK_FROM_HOME);

		newDP.setQuestionText("Gaat u vanuit huis werken?");
		newDP.setExplanationText(
				"Er worden eisen gesteld aan het soort beroepen die vanuit huis mogen worden uitgeoefend");
	}
	
	private void initDataPoint_REGISTEREDDUTCHKVK() {
		DataPoint newDP;
		
		questionTextPerRole.put("AANVRAGER", "Heeft u een inschrijving in de Kamer van Koophandel?");
		questionTextPerRole.put("BEOORDELAAR", "Is de aanvrager ingeschreven bij de Kamer van Koophandel?");
		explainTextPerRole.put("AANVRAGER",
				"Hiermee kunnen we uw openbare gegevens opvragen en zonder extra vragen gegevens verzamelen over uw commerciele activiteiten");
		explainTextPerRole.put("BEOORDELAAR",
				"Indien het geval, dan kan deze basisadministratie worden gebruikt.");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
		newDP.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.REGISTEREDDUTCHKVK);

		newDP.setQuestionText("Heeft u een inschrijving in de Kamer van Koophandel?");
		newDP.setExplanationText(
				"Hiermee kunnen we uw openbare gegevens opvragen en zonder extra vragen gegevens verzamelen over uw commerciele activiteiten");

	}

	private void initDataPoint_BUSINESSACTIVITIES() {
		DataPoint newDP;
		AllowedValue newValue;
		
		questionTextPerRole.put("AANVRAGER", "Wat is de aard van de activiteiten in uw bedrijf?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de aard van de bedrijfsactiviteiten?");
		explainTextPerRole.put("AANVRAGER",
				"Wij vragen dit om het effect of risico op de wijk vast te kunnen stellen");
		explainTextPerRole.put("BEOORDELAAR",
				"Nodig om vast te stellen of deze passen bij beroepen aan huis in categorie B");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
		newDP.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.BUSINESSACTIVITIES);
		newDP.setDefaultValue("false");

		newDP.setQuestionText("Wat is de aard van uw bedrijfsactiviteiten?");
		newDP.setExplanationText("Wij vragen dit om het effect of risico op de wijk vast te kunnen stellen");

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
		newValue.setDisplayText("Educatie");
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
		
		
		questionTextPerRole.put("AANVRAGER", "Wat is de breedte op het breedste punt in de tuin?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de maximale breedte van de tuin?");
		explainTextPerRole.put("AANVRAGER",
				"Dit vragen wij om de oppervlakte van de tuin te berekenen");
		explainTextPerRole.put("BEOORDELAAR",
				"Zie definities, nodig om oppervlakte tuin te berekenen");


		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_WIDTH_GARDEN);

		newDP.setQuestionText("Wat is de breedte van de tuin?");
		newDP.setExplanationText("Wij vragen dit de oppervlakte van de tuin vast te stellen");

	}

	private void initDataPoint_MAX_LENGTH_GARDEN() {
		DataPoint newDP;
		
		questionTextPerRole.put("AANVRAGER", "Wat is de lengte op het breedste punt in de tuin?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is de maximale lengte van de tuin?");
		explainTextPerRole.put("AANVRAGER",
				"Dit vragen wij om de oppervlakte van de tuin te berekenen");
		explainTextPerRole.put("BEOORDELAAR",
				"Zie definities, nodig om oppervlakte tuin te berekenen");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.MAX_LENGTH_GARDEN);

		newDP.setQuestionText("Wat is de lengte van de tuin?");
		newDP.setExplanationText("Wij vragen dit de oppervlakte van de tuin vast te stellen");
	}

	private void initDataPoint_SURFACE_CALCULATED_GARDEN() {
		DataPoint newDP;

		
		questionTextPerRole.put("AANVRAGER", "Oppervlakte tuin");
		questionTextPerRole.put("BEOORDELAAR", "Oppervlakte tuin");
		explainTextPerRole.put("AANVRAGER",
				"Het aandeel dat de tuin inneemt t.o.v oppervlakte van het bouwperceel.");
		explainTextPerRole.put("BEOORDELAAR",
				"Het aandeel dat de tuin inneemt t.o.v oppervlakte van het bouwperceel.");

		
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.SURFACE_CALCULATED_GARDEN);

		newDP.setQuestionText("Wat is de oppervlakte van de tuin?");
		newDP.setExplanationText("Wij vragen dit de watertoelatendheid vast te stellen");
	}

	private void initDataPoint_CHAMBREOFCOMMERCEDOSSIERNUMBER() {
		DataPoint newDP;
		questionTextPerRole.put("AANVRAGER", "Onder welke KVK nummer staat u ingeschreven?");
		questionTextPerRole.put("BEOORDELAAR", "Het KVK nummer van de aanvrager");
		explainTextPerRole.put("AANVRAGER",
				"Dit wordt gebruikt om gegevens uit de basis administratie op te vragen, zodat u deze niet zelf hoeft te verstrekken.");
		explainTextPerRole.put("BEOORDELAAR",
				"De basisadministratie KVK zal worden geraadpleegd om feiten te verzamelen");
		
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.OTHER);
		newDP.setDataType(DataPoint.DP_dataType.TEXT);
		newDP.setDataPointType(DataPoint.DP_Type.CHAMBREOFCOMMERCEDOSSIERNUMBER);

		newDP.setQuestionText("Onder welke KVK nummer staat u ingeschreven?");
		newDP.setExplanationText("Hiermee kunnen we gegevens uit de basisadministratie benaderen");
	}

	private void initDataPoint_PERC_WATER_PERM_GARDEN() {
		DataPoint newDP;
		questionTextPerRole.put("AANVRAGER", "Vastgesteld percentage watertoelatend gebied");
		questionTextPerRole.put("BEOORDELAAR", "Het percentage oppervlakte dat water toelatend is.");
		explainTextPerRole.put("AANVRAGER",
				"Belangrijk om risico op wateroverlast vast te stellen.");
		explainTextPerRole.put("BEOORDELAAR",
				"Wordt gebruikt om risico op wateroverlast vast te stellen.");
		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.OTHER);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.PERC_WATER_PERM_GARDEN);

		newDP.setQuestionText("Percentage waterdoorlatendheid tuin?");
		newDP.setExplanationText("Wij vragen dit de totale watertoelatendheid vast te stellen");
	}

	private void initDataPoint_SURFACE_TILES_GARDEN() {
		DataPoint newDP;
		
		questionTextPerRole.put("AANVRAGER", "Welk deel van de oppervlakte van de tuin zal worden verhard?");
		questionTextPerRole.put("BEOORDELAAR", "Hoe groot is het verharde deel van de tuin?");
		explainTextPerRole.put("AANVRAGER",
				"Verharding geeft invloed op de watertoelatendheid");
		explainTextPerRole.put("BEOORDELAAR",
				"Het verharde deel heeft invloed op de watertoelatendheid.");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.NUMBER);
		newDP.setDataPointType(DataPoint.DP_Type.SURFACE_TILES_GARDEN);

		newDP.setQuestionText("Oppervlakte verharding tuin");
		newDP.setExplanationText("Wij vragen dit de totale watertoelatendheid vast te stellen van de tuin");

	}

	private void initDataPoint_DESIGN_HAS_GARDEN() {
		DataPoint newDP;
		
		questionTextPerRole.put("AANVRAGER", "Is er een voornemen om een tuin aan te leggen?");
		questionTextPerRole.put("BEOORDELAAR", "Bevat het ontwerp een tuin?");
		explainTextPerRole.put("AANVRAGER",
				"Een tuin heeft invloed op de watertoelatendheid van het perceel.");
		explainTextPerRole.put("BEOORDELAAR",
				"Een tuin heeft invloed op de watertoelatendheid van het perceel.");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.DESIGN);
		newDP.setDataType(DataPoint.DP_dataType.TRUTHVALUE);
		newDP.setDataPointType(DataPoint.DP_Type.DESIGN_HAS_GARDEN);

		newDP.setQuestionText("Is er het voornemen een tuin aan te leggen?");
		newDP.setExplanationText("Wij vragen dit de totale watertoelatendheid vast te stellen van de tuin");

	}

	private void initDataPoint_TOTAL_SURFACE_WATER_NON_PERMABLE() {
		DataPoint newDP;

		questionTextPerRole.put("AANVRAGER", "Totale oppervlakte van het niet waterdoorlatend deel");
		questionTextPerRole.put("BEOORDELAAR", "Totaal oppervlakte niet waterdoorlatend deel");
		explainTextPerRole.put("AANVRAGER",
				"Het niet water doorlatend deel van het perceel wordt beoordeeld in het risico op wateroverlast.");
		explainTextPerRole.put("BEOORDELAAR",
				"Dit feit bepaald de kans op potentiele wateroverlast.");

		
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
		
		questionTextPerRole.put("AANVRAGER", "Wat is uw beroep?");
		questionTextPerRole.put("BEOORDELAAR", "Wat is het beroep van de aanvrager?");
		explainTextPerRole.put("AANVRAGER",
				"Beroepen aan huis zijn toegestaan, mits zij geen potentieel risico vormen voor de directe leefomgeving.");
		explainTextPerRole.put("BEOORDELAAR",
				"Aanvrager dient aan te tonen dat beroep in categorie B van beroepen valt.");

		newDP = this;
		newDP.setDataPointCategory(DataPoint.DP_category.ACTIVITY);
		newDP.setDataType(DataPoint.DP_dataType.VALUESELECTION);
		newDP.setDataPointType(DataPoint.DP_Type.PROFESSION_AT_HOME);
		newDP.setDefaultValue("OTHER");
		newDP.setAskable(true);

	

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
		newValue.setDisplayText("Accountant");
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
		newValue.setDisplayText("Een ander beroep dan hierboven vermeld.");
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
		} else if (aDPtype.equals(DataPoint.DP_Type.REGISTEREDDUTCHKVK)) {
			this.initDataPoint_REGISTEREDDUTCHKVK();
		} else if (aDPtype.equals(DataPoint.DP_Type.SBI_ORGANISATION)) {
			this.initDataPoint_SBI_ORGANISATION();
		} else if (aDPtype.equals(DataPoint.DP_Type.WORK_FROM_HOME)) {
			this.initDataPoint_WORK_FROM_HOME();
		} else {
			ServerGlobals.getInstance().log("ERROR: No init entry for " + aDPtype);
		} 
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
			aUsedDataPoint.justifyChildDataPoint(theServerGlobals, correspondingCase, myDataPointJustification);
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
			aUsedDataPoint.justifyChildDataPoint(theServerGlobals, correspondingCase, myDataPointJustification);
		}

	}

	public String getDisplayValueFor(String professionInfo) {
		int i;
		String display;
		display = "";
		for (i = 0; i < this.allowedValueList.size(); i++) {
			if (this.allowedValueList.get(i).getCode().equals(professionInfo)) {
				display = this.allowedValueList.get(i).getDisplayText();
			}
		}

		return display;
	}

}
