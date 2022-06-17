package application.frontend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import planspace.context.ContextAspect;
import planspace.context.ContextAspectProperty;
import planspace.domainTypes.AllowedValue;
import planspace.domainTypes.DataPointType;
import planspace.domainTypes.ElemValue;
import planspace.domainTypes.ObjectType;
import planspace.instanceModel.DataPointValue;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_elemDataType;
import planspace.utils.PlanSpaceLogger;
import speechInterface.WordClassification;

public class WEB_DataPointTypesOfObjectTypeEditor {

	private VerticalLayout editorBody;
	private HashMap<Component, DataPointType> DPT_by_TextField;
	private HashMap<DataPointType, Component> TextField_by_DPT;
	private ObjectType myObjectType = null;
	private IFC_DetailEditListener theParent;

	public WEB_DataPointTypesOfObjectTypeEditor(IFC_DetailEditListener aParent) {
		// constructor
		this.theParent = aParent;
		DPT_by_TextField = new HashMap<Component, DataPointType>();
		TextField_by_DPT = new HashMap<DataPointType, Component>();
	}

	public static WEB_DataPointTypesOfObjectTypeEditor createForObjectType(ObjectType theObjectType,
			IFC_DetailEditListener aParent) {
	
		WEB_DataPointTypesOfObjectTypeEditor theEditorCreated;

		// PlanSpaceLogger.getInstance().log("[GUI] Have to create DP editor for : " +
		//theObjectType.getCode());

		theEditorCreated = new WEB_DataPointTypesOfObjectTypeEditor(aParent);
		theEditorCreated.initEditorBody(theObjectType);
		theEditorCreated.setMyObjectType(theObjectType);
		return theEditorCreated;

	}

	public ObjectType getMyObjectType() {
		return myObjectType;
	}

	public void setMyObjectType(ObjectType myObjectType) {
		this.myObjectType = myObjectType;
	}

	public VerticalLayout getEditorBody() {
		return editorBody;
	}

	private void notifyParent() {
		if (this.theParent != null) {
			this.theParent.ConsumeEditorEvent("Context_Changed");
		}
	}

	private void initEditorBody(ObjectType theObjectType) {
		int noOfDataPoints;
		int i;
		HorizontalLayout aRow;
		DataPointType aDPT;
		VerticalLayout aDPTcontent;
		Label anEmptyLabel;

		Accordion theDPTaccordion;
		AccordionPanel aAccordionPanel;
		Component theComponent;

		editorBody = new VerticalLayout();

		theDPTaccordion = new Accordion();

		noOfDataPoints = theObjectType.getDataPointTypes().size();
		if (theObjectType != null) {

			if (this.editorBody != null) {
				for (i = 0; i < noOfDataPoints; i++) {
					aAccordionPanel = new AccordionPanel();
					aDPTcontent = new VerticalLayout();

					aDPT = theObjectType.getDataPointTypes().get(i);

					aAccordionPanel.setSummaryText(aDPT.getDisplayName());
					theComponent = this.createEditFieldForDataPointType(aDPT, aDPTcontent);

					aAccordionPanel.addContent(aDPTcontent);
					// add to hasmap for quick access and tracability

					if (theComponent != null) {
						DPT_by_TextField.put(theComponent, aDPT);
						TextField_by_DPT.put(aDPT, theComponent);
					}
					// spacing

					theDPTaccordion.add(aAccordionPanel);

				}

			}
		}

		aRow = new HorizontalLayout();
		anEmptyLabel = new Label("               "); // inspringen
		aRow.add(anEmptyLabel, theDPTaccordion);
		editorBody.add(aRow);
	}

	private Component createEditFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		String dataTypeOfDPT;
		Component theComponent;

		theComponent = null;
		if (aDPT.getMyValueType().equals(DataPointType.t_valueType.ELEMVALUE)) {
			// Elementair field needing textual input
			dataTypeOfDPT = aDPT.getDataType();
			dataTypeOfDPT = dataTypeOfDPT.toUpperCase();
			if (dataTypeOfDPT.equals(t_elemDataType.TEXT.toString())) {
				theComponent = createTextFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.FILE_PATH.toString())) {
				theComponent = createFilePathForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.FILE_URL.toString())) {
				theComponent = createFilePathForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.UNKNOWN.toString())) {
				theComponent = createTextFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.TRUTHVALUE.toString())) {
				theComponent = createBooleanFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.VALUESELECTION.toString())) {
				theComponent = createEnumFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.MEMO.toString())) {
				theComponent = createMemoFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.DATE_EURO.toString())) {
				theComponent = createDateFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.INTEGERVALUE.toString())) {
				theComponent = createIntegerFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals("INTEGER")) {
				theComponent = createIntegerFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.NUMBER.toString())) {
				theComponent = createNumberFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.CURRENCY_EURO.toString())) {
				theComponent = createEuroFieldForDataPointType(aDPT, aDPTcontent);

			} else if (dataTypeOfDPT.equals(t_elemDataType.FILE_PATH.toString())) {
				theComponent = createFilePathForDataPointType(aDPT, aDPTcontent);

			}

		} else if (aDPT.getMyValueType().equals(DataPointType.t_valueType.OBJECTTYPE)) {

			// TODO selecteren object
		}
		return theComponent;
	}

	private Component createFilePathForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		// TODO Upload
		TextField aTextField;

		aTextField = new TextField();
		aTextField.setClearButtonVisible(true);
		aTextField.setValue(aDPT.getDefaultValueAsString());
		aDPTcontent.add(aTextField);
		return aTextField;
	}

	private Component createIntegerFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		IntegerField numberField;
		ElemValue defaultValue;
		int theDefaultvalueAsInteger;

		numberField = new IntegerField();
		numberField.setHasControls(true);
		defaultValue = aDPT.getDefaultValueAsElemValue();
		if (defaultValue != null) {
			theDefaultvalueAsInteger = defaultValue.getIntValue();
			numberField.setValue(theDefaultvalueAsInteger);
		}
		aDPTcontent.add(numberField);
		return numberField;
	}

	private Component createNumberFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		NumberField numberField;
		ElemValue defaultValue;

		numberField = new NumberField();
		numberField.setStep(0.1d);
		// TO DO set min max

		defaultValue = aDPT.getDefaultValueAsElemValue();
		if (defaultValue != null) {
			numberField.setValue(Double.valueOf(defaultValue.getNumberValue()));
		}
		aDPTcontent.add(numberField);
		return numberField;
	}

	private Component createEuroFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		NumberField numberField;
		ElemValue defaultValue;

		numberField = new NumberField();
		numberField.setLabel("Euro");
		numberField.setStep(0.10d);
		numberField.setMin(0);
		defaultValue = aDPT.getDefaultValueAsElemValue();
		if (defaultValue != null) {
			numberField.setValue(Double.valueOf(defaultValue.getNumberValue()));
		}
		aDPTcontent.add(numberField);
		return numberField;
	}

	private Component createDateFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		DatePicker labelDatePicker = new DatePicker();
		LocalDate valueOfDate;

		Locale locale = new Locale("nl", "NL");
		DatePicker valueDatePicker = new DatePicker();
		LocalDate now = LocalDate.now();

		valueDatePicker.setValue(now);

		aDPTcontent.add(valueDatePicker);
		return valueDatePicker;

	}

	private Component createMemoFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		// TODO Auto-generated method stub
		TextArea atextArea = new TextArea();
		atextArea.getStyle().set("maxHeight", "200px");
		atextArea.setPlaceholder("Schijf hier ...");

		aDPTcontent.add(atextArea);
		return atextArea;
	}

	private Component createEnumFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
		AllowedValue anAllowedValue;
		int i;
		List<String> theItemsToSet;

		radioGroup.setLabel("Kies");
		theItemsToSet = new ArrayList<String>();

		String asJson = aDPT.toJson();

		for (i = 0; i < aDPT.getAllowedValueList().size(); i++) {
			anAllowedValue = aDPT.getAllowedValueList().get(i);
			theItemsToSet.add(anAllowedValue.getDisplayText());
		}

		radioGroup.setItems(theItemsToSet);
		radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

		aDPTcontent.add(radioGroup);
		return radioGroup;

	}

	private Component createBooleanFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {

		RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
		radioGroup.setLabel("Kies");
		radioGroup.setItems("Waar", "Niet waar", "Onbekend");
		radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

		radioGroup.setValue("Onbekend");
		if ((aDPT.getDefaultValueAsString().toLowerCase().equals("true"))
				|| (aDPT.getDefaultValueAsString().toLowerCase().equals("waar"))) {

			radioGroup.setValue("Waar");
		} else if ((aDPT.getDefaultValueAsString().toLowerCase().equals("false"))
				|| (aDPT.getDefaultValueAsString().toLowerCase().equals("onwaar"))) {

			radioGroup.setValue("Niet waar");
		}

		aDPTcontent.add(radioGroup);
		return radioGroup;
	}

	private Component createTextFieldForDataPointType(DataPointType aDPT, VerticalLayout aDPTcontent) {
		TextField aTextField;

		aTextField = new TextField();
		aTextField.setClearButtonVisible(true);
		aTextField.setValue(aDPT.getDefaultValueAsString());
		aDPTcontent.add(aTextField);
		aTextField.addValueChangeListener(listener -> {
			this.notifyParent();
		});
		return aTextField;
	}

	public void matchDPTWithWordClassificationSpecification(WordClassification selectedWordClassification) {
		int i;
		DataPointType aDPT;
		WordClassification aMatchingSpecification;
		Component theEditorForDPT;

		PlanSpaceLogger.getInstance().log_LUI("[LUI] matchDPTWithWordClassificationSpecification");
		// PlanSpaceLogger.getInstance().log("k7");
		if (selectedWordClassification != null) {
			// PlanSpaceLogger.getInstance().log("k8");
			if (this.myObjectType != null) {
				// PlanSpaceLogger.getInstance().log("k9");
				for (i = 0; i < this.myObjectType.getDataPointTypes().size(); i++) {
					aDPT = this.myObjectType.getDataPointTypes().get(i);
					// checking for dataPointType of object recognized
					// PlanSpaceLogger.getInstance().log("k10 " + aDPT.getDataType() );
					aMatchingSpecification = selectedWordClassification.matchDatapointType_by_name(aDPT);
					if (aMatchingSpecification != null) {
						// a match is found
						theEditorForDPT = this.TextField_by_DPT.get(aDPT);
						setValueForEditorByString(theEditorForDPT, aMatchingSpecification.getAssumedToHaveValue());
					}
				}
			}
		}

	}

	private void setValueForEditorByString(Component theEditorForDPT, String theValueToSet) {
		double aDouble;
		int anInteger;
		
		if (theEditorForDPT != null) {
		
			if (theValueToSet != null) {
				// editor found
				
			
				if (theEditorForDPT.getClass() == NumberField.class) {
					NumberField aNumberField = (NumberField) theEditorForDPT;
					try {
					
						aDouble = Double.parseDouble(theValueToSet);
						aNumberField.setValue(aDouble);
					} catch (Exception e) {
						// do nothing
					}

				} else if (theEditorForDPT.getClass() == TextField.class) {
					TextField aTextField = (TextField) theEditorForDPT;
					try {
					
						aTextField.setValue(theValueToSet);
					} catch (Exception e) {
						// do nothing
					}

				} else if (theEditorForDPT.getClass() == IntegerField.class) {
					IntegerField anIntegerField = (IntegerField) theEditorForDPT;
					try {
					
						anInteger = Integer.parseInt(theValueToSet);
						anIntegerField.setValue(anInteger);
					} catch (Exception e) {
						// do nothing
					}

				}
			}

		}
	}

	public void addContextAspectPropertiesToContextAspect(ContextAspect aContextAspect) {
		DataPointType aDPT;
		Component theDPTeditor;
		String valueAsString;
		int i;
		ElemValue theElemValue;
		ContextAspectProperty anAspectProperty;

		if (this.myObjectType != null) {

			if (this.myObjectType.getDataPointTypes() != null) {

				// iterate datapoints ... they may have values
				for (i = 0; i < this.myObjectType.getDataPointTypes().size(); i++) {
					aDPT = this.myObjectType.getDataPointTypes().get(i);
					theDPTeditor = TextField_by_DPT.get(aDPT);

					if (theDPTeditor != null) {

						// check if the editor for DPT has a value
						// will be specific per editor type

						valueAsString = getValueFromEditorAsString(theDPTeditor);

						if (valueAsString != null) {
							if (valueAsString.isEmpty() == false) {
								// there is a value, so a DPV can be made for it
								// to do check valid value
								anAspectProperty = new ContextAspectProperty();
								anAspectProperty.setAspectPropertyCode(aDPT.getTypeName());
								theElemValue = new ElemValue(aDPT.getDataType(), valueAsString);
								anAspectProperty.setElemValueToCompareWith(theElemValue);
								aContextAspect.getConceptProperties().add(anAspectProperty);
							} else {
								PlanSpaceLogger.getInstance().log_LUI("[DPT EDIT] value  empty: ");
							}
						}
					}
				}
			}
		}

	}

	private String getValueFromEditorAsString(Component theEditorForDPT) {
		String valueAsString;

		valueAsString = null;
		if (theEditorForDPT != null) {

			// editor found
			if (theEditorForDPT.getClass() == NumberField.class) {
				NumberField aNumberField = (NumberField) theEditorForDPT;
				try {
					if (aNumberField.isEmpty() != true) {
						valueAsString = String.valueOf(aNumberField.getValue());
					}

				} catch (Exception e) {
					// do nothing
				}

			} else if (theEditorForDPT.getClass() == TextField.class) {
				TextField aTextField = (TextField) theEditorForDPT;
				try {
					if (aTextField.isEmpty() != true) {
						valueAsString = aTextField.getValue();
					}
				} catch (Exception e) {
					// do nothing
				}

			} else if (theEditorForDPT.getClass() == IntegerField.class) {
				IntegerField anIntegerField = (IntegerField) theEditorForDPT;
				try {
					if (anIntegerField.isEmpty() != true) {
						valueAsString = String.valueOf(anIntegerField.getValue());
					}
				} catch (Exception e) {
					// do nothing
				}

			}

		}
		return valueAsString;
	}

	public void setupForExistingContextAspect(ContextAspect anAspect) {

		anAspect.getConceptProperties().forEach(anProperty -> {
			Component theEditorForDPT;
			DataPointType aDPT;

			aDPT = this.myObjectType.getDataPointTypeByCode(anProperty.getAspectPropertyCode());

			if (aDPT != null) {
				theEditorForDPT = this.TextField_by_DPT.get(aDPT);
				
			//	System.out.println("TRYING " + anProperty.getAspectPropertyCode() + " value "
				//		+ anProperty.getElemValueToCompareWith().getValueAsString());
			
				setValueForEditorByString(theEditorForDPT, anProperty.getElemValueToCompareWith().getValueAsString());
			}
		});

	}
}
