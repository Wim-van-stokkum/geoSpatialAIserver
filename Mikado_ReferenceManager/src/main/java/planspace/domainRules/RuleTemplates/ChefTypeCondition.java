package planspace.domainRules.RuleTemplates;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import application.frontend.WEB_Session;
import planspace.InterfaceToRuleRepository.InterfaceToRuleRepository;
import planspace.chefModelTypes.ChefType;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_BayBoolean;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_ChefTypeConditiontype;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_monitoringState;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_stateRegime;
import planspace.utils.PlanSpaceLogger;

public class ChefTypeCondition {
	private String _id;
	private String chefTypeObjectID;
	private String domainCode;
	private boolean isCompound = false;
	private t_ChefTypeConditiontype conditionType;
	private t_BayBoolean evaluationResult;
	private boolean isNot;
	private List<t_monitoringState> monitoringStatesToCompareWith;
	private List<ChefTypeCondition> mySubconditions;

	@JsonIgnore
	private transient Image myAvatarIcon;
	@JsonIgnore
	private transient ChefType theChefTypeObject;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public boolean isNot() {
		return isNot;
	}

	public void setNot(boolean isNot) {
		this.isNot = isNot;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getChefTypeObjectID() {
		return chefTypeObjectID;
	}

	public void setChefTypeObjectID(String anID) {
		this.chefTypeObjectID = anID;
	}

	public void StoreYourself() {
		InterfaceToRuleRepository theIFC;

		if (this.theChefTypeObject != null) {
			this.chefTypeObjectID = theChefTypeObject.get_id();
			this.domainCode = theChefTypeObject.getDomainCode();
			theIFC = InterfaceToRuleRepository.getInstance();
			if (theIFC != null) {
				System.out.println("ToSTORE 3");
				theIFC.storeChefTypeConditionInRepository(this);
			} else {
				PlanSpaceLogger.getInstance().log_Error("[ERROR] No ChefTypeCondition available");
			}
			// TO DO
		} else {
			PlanSpaceLogger.getInstance().log_Error("[ERROR]No ChefObject in Condition");
		}
	}

	static public List<t_ChefTypeConditiontype> getChefConditionTypesForChefType(t_chefType aChefType) {
		List<t_ChefTypeConditiontype> conditionTypeList;

		conditionTypeList = new ArrayList<t_ChefTypeConditiontype>();

		if (aChefType.equals(t_chefType.GOAL)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.FACT)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.RISK)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.OPPORTUNITY)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.RULE)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.EVENT)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.DATA)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.DATASOURCE)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.MEASUREMENT)) {
			conditionTypeList.add(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE);
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		} else if (aChefType.equals(t_chefType.UNKNOWN)) {
			conditionTypeList.add(t_ChefTypeConditiontype.ALL_UNDERLYING);
			conditionTypeList.add(t_ChefTypeConditiontype.ONE_UNDERLYING);
		}

		return conditionTypeList;
	}

	static public String getInformalForFormalChefTypeConditionType(t_language lang,
			t_ChefTypeConditiontype aChefTypeConditiontype) {
		String informal;

		informal = "";
		if (lang.equals(t_language.NL)) {
			if (aChefTypeConditiontype.equals(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE)) {
				informal = "Status van monitoring aspect";
			} else if (aChefTypeConditiontype.equals(t_ChefTypeConditiontype.ALL_UNDERLYING)) {
				informal = "Alle onderliggende voorwaarden moeten gelden";
			} else if (aChefTypeConditiontype.equals(t_ChefTypeConditiontype.ONE_UNDERLYING)) {
				informal = "Tenminste 1 van onderliggende voorwaarden moet gelden";
			}
		}
		return informal;
	}

	static public t_ChefTypeConditiontype getFormalForInformalChefTypeConditionType(t_language lang,
			String informalConditionType) {
		t_ChefTypeConditiontype formal;

		formal = null;
		if (lang.equals(t_language.NL)) {
			if (informalConditionType.equals("Status van monitoring aspect")) {
				formal = t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE;

			} else if (informalConditionType.equals("Alle onderliggende voorwaarden moeten gelden")) {
				formal = t_ChefTypeConditiontype.ALL_UNDERLYING;

			} else if (informalConditionType.equals("Tenminste 1 van onderliggende voorwaarden moet gelden")) {
				formal = t_ChefTypeConditiontype.ONE_UNDERLYING;

			}

		}
		return formal;
	}

	static public List<String> convertConditionTypeListToInformalOptionList(t_language lang,
			List<t_ChefTypeConditiontype> aInformalCondTypeList) {
		List<String> conditionTypeList;

		conditionTypeList = new ArrayList<String>();
		aInformalCondTypeList.forEach(aFormalCondType -> {
			String informalType;

			informalType = ChefTypeCondition.getInformalForFormalChefTypeConditionType(lang, aFormalCondType);
			if (informalType != null) {
				conditionTypeList.add(informalType);
			}

		});

		return conditionTypeList;
	}

	@JsonIgnore
	public CheckboxGroup<String> getStateSelectorForRegime(t_stateRegime aStateRegime) {
		CheckboxGroup<String> selectionBox;
		selectionBox = new CheckboxGroup<String>();
		// selectionBox.addThemeVariants(CheckboxGroupVariant.lu);
		selectionBox.setItems(ChefType.getInformalStatesForStateRegime(t_language.NL, aStateRegime));
		selectionBox.addValueChangeListener(listener -> {
			this.setSelectedMonitoringStatesToCompareWith(listener.getValue());

		});
		return selectionBox;
	}

	public void setMonitoringStatesToCompareWith(List<t_monitoringState> monitoringStatesToCompareWith) {
		this.monitoringStatesToCompareWith = monitoringStatesToCompareWith;
	}

	@JsonIgnore
	private void setSelectedMonitoringStatesToCompareWith(Set<String> statesSelected) {

		this.monitoringStatesToCompareWith.clear();
		statesSelected.forEach(aStateSelected -> {
			t_monitoringState aMonitoringState;

			aMonitoringState = ChefType.getFormalStateForInformalState(t_language.NL, aStateSelected);
			this.monitoringStatesToCompareWith.add(aMonitoringState);
		});

	}

	public List<t_monitoringState> getMonitoringStatesToCompareWith() {
		return monitoringStatesToCompareWith;
	}

	public ChefTypeCondition() {

		init();

	}

	public ChefTypeCondition(ChefType anObject, t_ChefTypeConditiontype aType) {
		init();
		this.theChefTypeObject = anObject;
		this.conditionType = aType;

	}

	private void init() {
		this._id = UUID.randomUUID().toString();
		evaluationResult = t_BayBoolean.UNKNOWN;
		isNot = false;
		monitoringStatesToCompareWith = new ArrayList<t_monitoringState>();
		theChefTypeObject = null;
		conditionType = t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE;
		mySubconditions = new ArrayList<ChefTypeCondition>();
	}

	public boolean acceptsAsSubConditions(ChefTypeCondition aSubType) {
		boolean accept;

		accept = false;
		if (this.conditionType.equals(t_ChefTypeConditiontype.ALL_UNDERLYING)) {
			accept = true;
		} else if (this.conditionType.equals(t_ChefTypeConditiontype.ONE_UNDERLYING)) {
			accept = true;
		}
		return accept;
	}

	public void addSubCondition(ChefTypeCondition aSubCond) {
		if (!this.mySubconditions.contains(aSubCond)) {
			this.mySubconditions.add(aSubCond);
		}
	}

	public void removeSubCondition(ChefTypeCondition aSubCond) {
		if (this.mySubconditions.contains(aSubCond)) {
			this.mySubconditions.remove(aSubCond);
		}
	}

	public List<ChefTypeCondition> getMySubconditions() {
		return mySubconditions;
	}

	public void setMySubconditions(List<ChefTypeCondition> mySubconditions) {
		this.mySubconditions = mySubconditions;
	}

	public boolean isCompound() {
		return isCompound;
	}

	public void setCompound(boolean isCompound) {
		this.isCompound = isCompound;
	}

	public t_ChefTypeConditiontype getConditionType() {
		return conditionType;
	}

	public void setConditionType(t_ChefTypeConditiontype conditionType) {
		this.conditionType = conditionType;
	}

	public ChefType getTheChefTypeObject() {
		return theChefTypeObject;
	}

	public void setTheChefTypeObject(ChefType theChefTypeObject) {
		this.theChefTypeObject = theChefTypeObject;
		this.chefTypeObjectID = theChefTypeObject.get_id();
		this.domainCode = theChefTypeObject.getDomainCode();
	}

	public t_BayBoolean getEvaluationResult() {
		return evaluationResult;
	}

	public void setEvaluationResult(t_BayBoolean evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	@JsonIgnore
	public HorizontalLayout getMyGUIpresence() {
		HorizontalLayout theLayout;

		theLayout = null;
		if (this.conditionType.equals(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE)) {
			theLayout = getMyGUIpresenceStateValue();
		} else {
			theLayout = getMyGUIpresenceNonChefType();
		}
		return theLayout;
	}

	@JsonIgnore
	private HorizontalLayout getMyGUIpresenceNonChefType() {
		HorizontalLayout myGUIpresence;
		VerticalLayout myLabel;
		String bordercolor;
		CheckboxGroup<String> stateSelector;

		myGUIpresence = new HorizontalLayout();
		if (myGUIpresence != null) {
			String myColor = "gray";
			bordercolor = "1px solid " + myColor;
			myGUIpresence.setSpacing(false);
			myGUIpresence.getStyle().set("border", bordercolor);
			myGUIpresence.setWidth("780px");
			myGUIpresence.setHeight("100px");

		}

		refreshAvatarForNonChefTypeBasedCondition();
		myLabel = this.refreshNonChefTypeLabel();
		;

		myGUIpresence.add(myAvatarIcon, myLabel);
		return myGUIpresence;
	}

	@JsonIgnore
	private HorizontalLayout getMyGUIpresenceStateValue() {
		HorizontalLayout myGUIpresence;
		VerticalLayout myLabel;
		String bordercolor;

		myGUIpresence = new HorizontalLayout();
		if (myGUIpresence != null) {
			String myColor = "gray";
			bordercolor = "1px solid " + myColor;
			myGUIpresence.setSpacing(false);
			myGUIpresence.getStyle().set("border", bordercolor);
			myGUIpresence.setWidth("780px");
			myGUIpresence.setHeight("100px");

		}

		refreshAvatar();
		myLabel = refreshChefTypeLabel();

		myGUIpresence.add(myAvatarIcon, myLabel);
		return myGUIpresence;
	}

	@JsonIgnore
	private VerticalLayout refreshNonChefTypeLabel() {
		Div name = new Div();
		VerticalLayout messageBody;

		messageBody = new VerticalLayout();

		// tagname
		name.add(new Text(
				ChefTypeCondition.getInformalForFormalChefTypeConditionType(t_language.NL, this.getConditionType())));
		name.getStyle().set("color", "gray");
		messageBody.add(name);

		return messageBody;

	}

	@JsonIgnore
	private VerticalLayout refreshChefTypeLabel() {

		VerticalLayout chefTypeSelectStateLabel;
		CheckboxGroup<String> stateSelector;

		chefTypeSelectStateLabel = createChefTypeSelectStateLabel();
		stateSelector = getStateSelectorForRegime(this.getTheChefTypeObject().getStateRegime());
		chefTypeSelectStateLabel.add(stateSelector);
		setMonitoringStatesToStateSelector(stateSelector);
		return chefTypeSelectStateLabel;

	}

	@JsonIgnore
	private void setMonitoringStatesToStateSelector(CheckboxGroup<String> stateSelector) {
		List<String> activeValues;

		activeValues = new ArrayList<String>();
		this.monitoringStatesToCompareWith.forEach(aMonitoringState -> {
			activeValues.add(ChefType.getInformalStateForFormalState(t_language.NL, aMonitoringState));

		});
		stateSelector.select(activeValues);

	}

	@JsonIgnore
	private VerticalLayout createChefTypeSelectStateLabel() {
		// will create a text message with name of initator
		Div name = new Div();
		VerticalLayout messageBody;

		messageBody = new VerticalLayout();
		if (this.theChefTypeObject != null) {
			if (theChefTypeObject.getName() != null) {
				// tagname
				name.add(new Text(theChefTypeObject.getName()));
				name.getStyle().set("color", "gray");
				messageBody.add(name);
			}
		}
		return messageBody;
	}

	@JsonIgnore
	private void refreshAvatar() {
		if (this.conditionType.equals(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE)) {
			refreshAvatarForChefTypeBasedCondition();
		}

	}

	private void refreshAvatarForNonChefTypeBasedCondition() {
		if (this.conditionType.equals(t_ChefTypeConditiontype.ALL_UNDERLYING)) {
			this.myAvatarIcon = new Image("/icon_ALL.png", "ALL");
		} else if (this.conditionType.equals(t_ChefTypeConditiontype.ONE_UNDERLYING)) {
			this.myAvatarIcon = new Image("/icon_ONE.png", "ONE");
		}
		this.myAvatarIcon.setWidth("60px");
		this.myAvatarIcon.setHeight("60px");

	}

	@JsonIgnore
	private void refreshAvatarForChefTypeBasedCondition() {

		// avatare picture
		if (this.getTheChefTypeObject() != null) {
			if (this.getMyChefType().equals(t_chefType.GOAL)) {
				this.myAvatarIcon = new Image("/icon_GOAL.png", "DOEL");
			} else if (this.getMyChefType().equals(t_chefType.OPPORTUNITY)) {
				this.myAvatarIcon = new Image("/icon_KANS.png", "KANS");
			} else if (this.getMyChefType().equals(t_chefType.RISK)) {

				this.myAvatarIcon = new Image("/icon_RISK.png", "RISK");

			} else if (this.getMyChefType().equals(t_chefType.MEASUREMENT)) {

				this.myAvatarIcon = new Image("/icon_ACT.png", "ACTIE");

			} else if (this.getMyChefType().equals(t_chefType.EVENT)) {

				this.myAvatarIcon = new Image("/icon_EVENT.png", "EVENT");

			} else if (this.getMyChefType().equals(t_chefType.RULE)) {

				this.myAvatarIcon = new Image("/icon_RULE.png", "RULE");

			} else if (this.getMyChefType().equals(t_chefType.FACT)) {

				this.myAvatarIcon = new Image("/icon_FACT.png", "FACT");

			} else if (this.getMyChefType().equals(t_chefType.NORM)) {

				this.myAvatarIcon = new Image("/icon_NORM.png", "NORM");

			} else if (this.getMyChefType().equals(t_chefType.DATA)) {

				this.myAvatarIcon = new Image("/icon_DATA.png", "DATA");

			} else if (this.getMyChefType().equals(t_chefType.DATASOURCE)) {

				this.myAvatarIcon = new Image("/icon_DATASOURCE.png", "BRON");

			}

			else if (this.getMyChefType().equals(t_chefType.UNKNOWN)) {

				this.myAvatarIcon = new Image("/icon_OTHER.png", "UNKNOWN");

			}

		} else {
			// no TAGS

			this.myAvatarIcon = new Image("/icon_OTHER.png", "ONBEKEND");

		}
		this.myAvatarIcon.setWidth("60px");
		this.myAvatarIcon.setHeight("60px");

	}

	private t_chefType getMyChefType() {
		if (this.theChefTypeObject != null) {
			return this.theChefTypeObject.getType();
		} else
			return null;
	}

	public void loadRelatedObjects() {
		InterfaceToDomainTypeRepository theIFC;

		theIFC = InterfaceToDomainTypeRepository.getInstance();
		if (theIFC != null) {
			if (this.chefTypeObjectID != null) {
				this.theChefTypeObject = theIFC.findChefTypeByID(
						WEB_Session.getInstance().getTheDomain().getDomainCode(), this.chefTypeObjectID);
			}
			if (this.mySubconditions != null) {
				this.mySubconditions.forEach(aSubCondition -> {
					aSubCondition.loadRelatedObjects();
				});
			}
		}

	}

	public void deleteSubCondition(ChefTypeCondition aSelectedItem, boolean recursive) {
		if (this.mySubconditions.contains(aSelectedItem)) {
			if (!recursive) {
				aSelectedItem.moveYourSubConditionsTo(this);
			}
			this.mySubconditions.remove(aSelectedItem);
		} else {
			this.mySubconditions.forEach(aChildCondition -> {
				aChildCondition.deleteSubCondition(aSelectedItem, recursive);
			});
		}

	}

	private void moveYourSubConditionsTo(ChefTypeCondition newParent) {
		this.mySubconditions.forEach(aChildCondition -> {
			newParent.addSubCondition(aChildCondition);
		});

	}

}
