package application.frontend.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import application.frontend.IFC_DetailEditListener;
import application.frontend.IFC_ObjectTypeRequester;
import application.frontend.WEB_DataPointTypesOfObjectTypeEditor;
import application.frontend.WEB_SelectObjectTypeFromTaxonomy;
import application.frontend.WEB_Session;
import planspace.context.ContextAspect;
import planspace.domainTypes.ObjectType;
import planspace.domainTypes.TaxonomyNode;
import planspace.instanceModel.DomainInstance;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_aspectInSentence;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_contextAspect;
import planspace.utils.PlanSpaceLogger;
import speechInterface.MatchingTaxonomyConcept;
import speechInterface.WordClassification;

public class ContextAspectEditor extends VerticalLayout implements IFC_ObjectTypeRequester, IFC_DetailEditListener  {
	private t_aspectInSentence theContextAspect;
	private TextField theObjectTypeNameEditLine;
	private MatchingTaxonomyConcept selectedConcept;
	private WordClassification selectedWordClassification;
	private ObjectType selectedObjectType;
	private Button theDPTbutton;
	private HorizontalLayout actionLine;
	private Button theSelectButton;
	private IFC_ObjectTypeRequester answerTo;
	private WEB_DataPointTypesOfObjectTypeEditor theDPTeditor;
	private VerticalLayout theDPTeditorBody = null;
	private IFC_DetailEditListener theParent;
	
	public ContextAspectEditor(t_aspectInSentence aContextAspect, IFC_DetailEditListener aParent) {
		super();
		theParent = aParent;
		this.theContextAspect = aContextAspect;
		configureEditor();
		configureAspectTitle();
		configureEditorBody();
		
	}

	private void configureEditorBody() {
		initManualInputLine();

		// get or create buttons
		actionLine = new HorizontalLayout();

		this.theSelectButton = this.initSelectButton();
		this.theDPTbutton = this.initDataPointButton();
		theDPTbutton.setVisible(false);
		actionLine.add(theSelectButton, theDPTbutton);

		this.add(actionLine);
		this.reset();

		
	}

	protected Button initSelectButton() {

		if (this.theSelectButton == null) {
		
			
			Image icon;
			String r = "35px";

			icon = new Image("/taxonomy.png", "");
			icon.setHeight(r);
			icon.setWidth(r);
			this.theSelectButton = new Button("",  icon);
			

			// Set action
			this.theSelectButton.addClickListener(e -> {
				this.selectObjectTypeFromTaxonomy();
			});

		}
		return this.theSelectButton;
	}
	
	protected void selectObjectTypeFromTaxonomy() {
		WEB_SelectObjectTypeFromTaxonomy aTaxonomySelectionDialog;
		aTaxonomySelectionDialog = new WEB_SelectObjectTypeFromTaxonomy();

		if (aTaxonomySelectionDialog != null) {
			aTaxonomySelectionDialog.selectForMe(this);
		}

	}
	
	public void ConsumeDetailEditorOpened() {
		
	}

	public void ConsumeDetailEditorClosed() {
		
	}
	
	public void ConsumeEditorEvent(String editorEvent) {
		notifyParent();
	}

	private void initManualInputLine() {
		// init the input line for manual entru

		theObjectTypeNameEditLine = new TextField("Concept");
		theObjectTypeNameEditLine.setSizeFull();
		theObjectTypeNameEditLine.setClearButtonVisible(true);

		// event
		theObjectTypeNameEditLine.addKeyPressListener(Key.ENTER, e -> {
			processManualInput();
		});

		// add to body
		this.add(theObjectTypeNameEditLine);

	}
	
	private void processManualInput() {
		String manualInput;

		manualInput = this.theObjectTypeNameEditLine.getValue().strip();

		if (manualInput.isBlank() == false) {
			if (manualInput.isEmpty() == false) {
				manualInput.toUpperCase();
				matchExternalInput(manualInput);
				
			} else {
				this.reset();

			}
		} else {
			this.reset();
		}

	}
	
	
	protected ObjectType matchExternalInput(String extObjectTypeName) {
		WEB_Session theSession;
		TaxonomyNode theTaxonomy, matchingNode;
		ObjectType matchingConcept;

		matchingConcept = null;
		if (this != null) {
			theSession = WEB_Session.getInstance();
			theTaxonomy = theSession.getDomainTaxonomy();
			matchingNode = theTaxonomy.matchTypeName(extObjectTypeName);
			
			if (matchingNode != null) {
				this.registerSelectedConcept(matchingNode);
			}
			
			// PlanSpaceLogger.getInstance().log("[INFO] succes: typename FOUND in Onthology
			// : " + extObjectTypeName);
			
		} else {

			// PlanSpaceLogger.getInstance().log("[INFO] typename not found in Onthology : "
			// + extObjectTypeName);

			this.reset();

		}
		return matchingConcept;

	}
	
	protected void reset() {

		selectedConcept = null;
		selectedWordClassification = null;
		this.selectedObjectType = null;
		this.theObjectTypeNameEditLine.setValue("");
		theDPTbutton.setVisible(false);
		removeCurrentDPTeditor();
//		if (this.consumingEditor != null) {
	//		this.consumingEditor.ConsumeAspectReset(this);

//		}
		this.removeCurrentDPTeditor();

	}
	
	
	private void notifyParent() {
		if (this.theParent != null) {
			this.theParent.ConsumeEditorEvent("Context_Changed");
		}
	}
	
	
	protected void registerSelectedConcept(TaxonomyNode theConcept) {
		ObjectType theMatchingObjectType;
		MatchingTaxonomyConcept theMatchingTaxonomyConcept;
		removeCurrentDPTeditor();

		/*
		 * this method will register an ObjectType after it is matched by the ontology
		 *
		 */
	
		if (theConcept != null) {
			// convert to matching concept

			theMatchingTaxonomyConcept = new MatchingTaxonomyConcept();
			theMatchingTaxonomyConcept.setTaxonomyID(theConcept.get_id());
			theMatchingTaxonomyConcept.setDomainCode(theConcept.getDomainCode());
			theMatchingTaxonomyConcept.setObjectTypeCode(theConcept.getCode());
			theMatchingTaxonomyConcept.setObjectTypeDescription(theConcept.getDescription());
			theMatchingTaxonomyConcept.setObjectTypeID(theConcept.getObjectType().get_id());
			
		
			
			
			// The TaxonomyItem is set
			this.selectedConcept = theMatchingTaxonomyConcept;
			this.selectedObjectType = null;
			this.theObjectTypeNameEditLine.setValue(theConcept.getCode());

			// get definition of object type of concept
			theMatchingObjectType = this.getSelectedObjectType();
			if (theMatchingObjectType != null) {
				// Check for datapoint types

				if (theMatchingObjectType.getDataPointTypes().size() > 0) {

					prepareDataPointTypeEditor();
					this.theDPTbutton.setVisible(true);
				} else {

					this.removeCurrentDPTeditor();
					this.theDPTbutton.setVisible(false);
				}
				notifyParent();

			} else {
				this.theDPTbutton.setVisible(false);
			}
		}
	}
	
	private void prepareDataPointTypeEditor() {
		// if there are datapointtypes an editor will be created and the body of the
		// editor will be integrated in this editor

	
		if (this.selectedConcept != null) {
		//	PlanSpaceLogger.getInstance().log("k1");
			if (this.getSelectedObjectType() != null) {
			//	PlanSpaceLogger.getInstance().log("k2");
				this.removeCurrentDPTeditor();
			//	PlanSpaceLogger.getInstance().log("k3");
				this.theDPTeditor = WEB_DataPointTypesOfObjectTypeEditor
						.createForObjectType(this.getSelectedObjectType(), this);
				if (theDPTeditor != null) {
				//	PlanSpaceLogger.getInstance().log("k4");
					// close the previous DPT editor
				
					this.theDPTeditorBody = this.theDPTeditor.getEditorBody();
					this.theDPTeditorBody.setVisible(false);
					if (theDPTeditorBody != null) {
				//		PlanSpaceLogger.getInstance().log("k5");
						this.add(theDPTeditorBody);
						if (this.selectedWordClassification != null) {
				//			PlanSpaceLogger.getInstance().log("k6");
							theDPTeditor.matchDPTWithWordClassificationSpecification(this.selectedWordClassification);
						}
					} else {
						PlanSpaceLogger.getInstance().log_Debug("[ERROR] No aspect editor body created");
					}
				}
			}
		}


	}

	
	protected ObjectType getSelectedObjectType() {
		ObjectType theObjectType;
		InterfaceToDomainTypeRepository theInterfaceToDomainTypeRepository;

		// Gets the Object Type Definition from ontolgy wen selected Object is known

		theObjectType = this.selectedObjectType;

		if (theObjectType == null) {

			if (this.selectedConcept != null) {
				theInterfaceToDomainTypeRepository = InterfaceToDomainTypeRepository.getInstance();
				if (theInterfaceToDomainTypeRepository != null) {
					theObjectType = theInterfaceToDomainTypeRepository.findObjectTypeByTypename(
							this.selectedConcept.getDomainCode(), this.selectedConcept.getObjectTypeCode());
					this.selectedObjectType = theObjectType;
				}

			}
		}
		return theObjectType;
	}

	protected Button initDataPointButton() {

		if (this.theDPTbutton == null) {
			
			
			Image icon;
			String r = "35px";

			icon = new Image("/details.png", "");
			icon.setHeight(r);
			icon.setWidth(r);
			this.theDPTbutton = new Button("",  icon);
			
			

			// Set action
			this.theDPTbutton.addClickListener(e -> {

				if (this.theDPTeditorBody != null) {
					if (this.theDPTeditorBody.isVisible()) {
						this.theDPTeditorBody.setVisible(false);
						this.notifyParent();
					} else {
						this.theDPTeditorBody.setVisible(true);
					}
				}
			});

		}
		return this.theDPTbutton;
	}
	
	
	private void removeCurrentDPTeditor() {

		if (this.theDPTeditorBody != null) {
			this.remove(theDPTeditorBody);
			theDPTeditorBody = null;

		}
		if (this.theDPTeditor != null) {
			this.theDPTeditor = null;

		}

	}
	
	
	
	private void configureAspectTitle() {
		Label aLB;
		aLB = new Label(this.getTitleForAspect());
		this.add(aLB);
		
	}

	private String getTitleForAspect() {
		String aTL;
		aTL = "Onbekend";
		if (theContextAspect.equals(t_aspectInSentence.HOW)) {
			aTL = "Gebeurtenis/actie";
		} else if (theContextAspect.equals(t_aspectInSentence.WHAT)) {
			aTL = "Betrokken object";
		}else if (theContextAspect.equals(t_aspectInSentence.WHO)) {
			aTL = "Persoon/organisatie";
		}else if (theContextAspect.equals(t_aspectInSentence.WHERE)) {
			aTL = "Locatie";
		} else if (theContextAspect.equals(t_aspectInSentence.WHEN)) {
			aTL = "Moment";
		}else if (theContextAspect.equals(t_aspectInSentence.WHY)) {
			aTL = "Reden";
		}
			
		return aTL;
	}

	private void configureEditor( ) {
		this.setMinWidth("350px");
		this.setSpacing(true);
		this.setHeight("400px");
		this.getStyle().set("border", "1px solid gray");
		this.getStyle().set("overflow", "auto");
	}

	
	public void acceptSelectedObjectType(TaxonomyNode selectedConcept) {
		processSelectedInput(selectedConcept);
	}
	
	protected void processSelectedInput(TaxonomyNode theSelectedConcept) {
		String selectedInputTypeName;



		selectedInputTypeName = theSelectedConcept.getCode();

	
		if (selectedInputTypeName.isBlank() == false) {
			if (selectedInputTypeName.isEmpty() == false) {
				selectedInputTypeName.toUpperCase();
				this.registerSelectedConcept(theSelectedConcept);

			} else {
				this.reset();
			}
		} else {
			this.reset();
		}

		notifyParent();

	}
	

	
	public void addContextAspectPropertiesToContextAspect(ContextAspect aContextAspect) {

		if (this.theDPTeditor != null) {
			// There are potential DataPoints values
			this.theDPTeditor.addContextAspectPropertiesToContextAspect(aContextAspect);
		}

	}

	public void setUpForExistingContextAspect(ContextAspect anAspect) {
		this.matchExternalInput(anAspect.getConceptTypeCode());
		if (anAspect.getConceptProperties() != null) {
			if (anAspect.getConceptProperties().size() > 0 ) {
				this.theDPTeditor.setupForExistingContextAspect(anAspect);
			}
		}
		
		
	}




}
