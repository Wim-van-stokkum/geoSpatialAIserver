package nl.geospatialAI.DataPoints;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.google.gson.Gson;



import nl.geospatialAI.DataAccessHandlers.Planspace_ConfigDB;
import nl.geospatialAI.Justification.RoleBasedJustificationText;
import nl.geospatialAI.Justification.RoleBasedQuestionText;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class DataPointType {
	
	private Planspace_ConfigDB myConfigDataBase = null;
	
	 String _id;
	 String typeName;
	 String description;
	String dataPointCategory = "OTHER";
	String maskType = "NO_MASK";
	String contentStyle = "STANDARD";
	String presentationStyle = "STANDARD";
	String datapointSource = "UNKNOWN";
	String dataType;
	boolean isAskable = true;
	boolean multiValued = false;
	boolean required = false;
	String defaultValue = "";
	int maxValueInteger;
	int minValueInteger;
	float minValueNumber;
	float maxValueNumber;
	List<AllowedValue> allowedValueList;
	List<RoleBasedQuestionText> questionTextPerRole;
	List<RoleBasedJustificationText> justificationTextPerRole;
	
	

	protected void generateId() {
		_id = UUID.randomUUID().toString();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeName() {
		return typeName;
	}

	public DataPointType() {
		this.questionTextPerRole = new ArrayList<RoleBasedQuestionText>();
		this.justificationTextPerRole = new ArrayList<RoleBasedJustificationText>();
		this.allowedValueList = new ArrayList<AllowedValue>();
		this.generateId();
		this.setDataPointType("undefined");

	}

	public void setQuestionTextForRole(String roleType, String theQuestionText) {
		RoleBasedQuestionText newTextforRole;

		newTextforRole = new RoleBasedQuestionText(roleType, theQuestionText);
		this.questionTextPerRole.add(newTextforRole);

	}

	public void setDataPointCategory(String aCategory) {
		this.dataPointCategory = aCategory;

	}

	public void setDataType(String aDataType) {
		this.dataType = aDataType;

	}

	public void setDataPointType(String aDataPointType) {
		this.typeName = aDataPointType;

	}

	public void addAllowedValue(AllowedValue newAllowedValue) {
           this.allowedValueList.add(newAllowedValue);

	}

	public void setExplainTextForRole(String roleType, String theJustificationtext) {

		RoleBasedJustificationText newJustificationtextforRole;

		newJustificationtextforRole = new RoleBasedJustificationText(roleType, theJustificationtext);
		this.justificationTextPerRole.add(newJustificationtextforRole);

	}

	public Document toMongoDocument() {
		Gson gsonParser;
		String meAsJson;
		Document meAsDocument;

		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);
		

		//ServerGlobals.getInstance().log("JSON for DataPointType " + this.typeName + "-> " + meAsJson);
	
		meAsDocument = Document.parse(meAsJson);
		//ServerGlobals.getInstance().log("DOC for DataPointType " + this.typeName + "-> " + meAsDocument);
		return meAsDocument;
	}


	
	public void storeYourSelf() {
		Planspace_ConfigDB theConfigDB = null; 
		
		theConfigDB = ServerGlobals.getInstance().getPlanSpaceConfigDB();
		 theConfigDB.storeDataPointType(this);
	}

	public void setDefaultValue(String aDefaultValue) {
	  
		this.defaultValue = aDefaultValue;
	}

	public void setAskable(boolean b) {

		this.isAskable = b;
	}

	public void initializeDataPoint(DataPoint aDataPointOfThisType) {
		int i;
		AllowedValue anAllowedValue;
		RoleBasedQuestionText aRoleBasedQuestionText;	
		RoleBasedJustificationText aRoleBasedJustificationText;	
		
		aDataPointOfThisType.setContentStyle(DataPoint.DP_contentStyle.valueOf(this.contentStyle));
		aDataPointOfThisType.setPresentationStyle(DataPoint.DP_presentionStyle.valueOf(this.presentationStyle));
		aDataPointOfThisType.setDataPointCategory(DataPoint.DP_category.valueOf(this.dataPointCategory));
		aDataPointOfThisType.setDataPointType(DataPoint.DP_Type.valueOf(this.typeName));
		aDataPointOfThisType.setMaskType(DataPoint.DP_maskType.valueOf(this.maskType));
		aDataPointOfThisType.setDataType(DataPoint.DP_dataType.valueOf(this.dataType));
		aDataPointOfThisType.setMultiValued(this.multiValued);
		aDataPointOfThisType.setRequired(this.required);
		if (this.defaultValue == null) {
			aDataPointOfThisType.setDefaultValue("");
		}
		else {
			aDataPointOfThisType.setDefaultValue(this.defaultValue);
		}
		
		aDataPointOfThisType.setMaxValueInteger(this.maxValueInteger);
		aDataPointOfThisType.setMinValueInteger(this.minValueInteger);
		aDataPointOfThisType.setMinValueNumber(this.minValueNumber);
		aDataPointOfThisType.setMaxValueNumber(this.maxValueNumber);
		aDataPointOfThisType.setAskable(this.isAskable);
		aDataPointOfThisType.setDescription(this.description);

		
		// List of allowed Values
		if (aDataPointOfThisType.allowedValueList == null) {
			aDataPointOfThisType.allowedValueList = new ArrayList<AllowedValue>();
		} else {
			aDataPointOfThisType.allowedValueList.clear();
		}
	
		for( i = 0; i < this.allowedValueList.size(); i++) {
			anAllowedValue = this.allowedValueList.get(i);
			aDataPointOfThisType.addAllowedValue(anAllowedValue);
		}
		
		// questiontext as hasmap
		aDataPointOfThisType.questionTextPerRole.clear();
		for( i = 0; i < this.questionTextPerRole.size(); i++) {
			aRoleBasedQuestionText = this.questionTextPerRole.get(i);
			aDataPointOfThisType.questionTextPerRole.put(aRoleBasedQuestionText.roleType, aRoleBasedQuestionText.questionText);
		}
		
		
		// justificationtext as hasmap
		aDataPointOfThisType.explainTextPerRole.clear();
		for( i = 0; i < this.justificationTextPerRole.size(); i++) {
			aRoleBasedJustificationText = this.justificationTextPerRole.get(i);
			aDataPointOfThisType.explainTextPerRole.put(aRoleBasedJustificationText.roleType, aRoleBasedJustificationText.justificationText);
		}
		
	}

}
