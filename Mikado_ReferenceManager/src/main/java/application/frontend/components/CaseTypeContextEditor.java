package application.frontend.components;

import java.util.List;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import application.frontend.IFC_DetailEditListener;
import planspace.caseModel.CaseType;
import planspace.context.ContextAspect;
import planspace.context.ContextDefinition;
import planspace.domainTypes.ObjectType;
import planspace.instanceModel.DomainInstance;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_aspectInSentence;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_contextAspect;

public class CaseTypeContextEditor extends VerticalLayout implements IFC_DetailEditListener {
	private static final long serialVersionUID = 8705826235165849956L;

	private CaseType theCaseTypeBeingEdited;

	private ContextAspectEditor the_WHO_editor;
	private ContextAspectEditor the_HOW_editor;
	private ContextAspectEditor the_WHAT_editor;
	private ContextAspectEditor the_WHEN_editor;
	private ContextAspectEditor the_WHERE_editor;
	private ContextAspectEditor the_WHY_editor;

	private IFC_DetailEditListener theParent;
	private boolean notifyChangeEnabled;

	public CaseTypeContextEditor(IFC_DetailEditListener aParent) {
		super();
		this.theParent = aParent;
		notifyChangeEnabled = false;
		configureEditor();
	}

	private void configureEditor() {
		setDefaultForEditor();
		createContextAspectEditors();

	}

	private void createContextAspectEditors() {
		HorizontalLayout hz1, hz2;

		this.the_HOW_editor = new ContextAspectEditor(t_aspectInSentence.HOW, this);
		this.the_WHAT_editor = new ContextAspectEditor(t_aspectInSentence.WHAT, this);
		this.the_WHO_editor = new ContextAspectEditor(t_aspectInSentence.WHO, this);
		this.the_WHEN_editor = new ContextAspectEditor(t_aspectInSentence.WHEN, this);
		this.the_WHERE_editor = new ContextAspectEditor(t_aspectInSentence.WHERE, this);
		this.the_WHY_editor = new ContextAspectEditor(t_aspectInSentence.WHY, this);
		hz1 = new HorizontalLayout();
		hz1.add(the_HOW_editor, the_WHAT_editor, the_WHO_editor);
		hz2 = new HorizontalLayout();
		hz2.add(the_WHEN_editor, the_WHERE_editor, the_WHY_editor);
		this.add(hz1, hz2);

	}

	private void setDefaultForEditor() {

		this.setMinWidth("1150px");
		this.setSpacing(true);
		this.setHeight("850px");

	}

	public void prepareForEditingCaseType(CaseType aCT) {
		this.theCaseTypeBeingEdited = aCT;
		notifyChangeEnabled = false;
		if (aCT.getMyContext() != null) {
			this.setupExistingContextForCaseType();
		}
		notifyChangeEnabled = true;
	}

	private void setupExistingContextForCaseType() {
		ContextDefinition currentContext;

		currentContext = theCaseTypeBeingEdited.getMyContext();
		this.setupExistingContextForAspectType(currentContext, t_contextAspect.HOW);
		this.setupExistingContextForAspectType(currentContext, t_contextAspect.WHAT);
		this.setupExistingContextForAspectType(currentContext, t_contextAspect.WHO);
		this.setupExistingContextForAspectType(currentContext, t_contextAspect.WHERE);
		this.setupExistingContextForAspectType(currentContext, t_contextAspect.WHEN);
		this.setupExistingContextForAspectType(currentContext, t_contextAspect.WHY);

	}

	private void setupExistingContextForAspectType(ContextDefinition currentContext, t_contextAspect theAspect) {
		List<ContextAspect> currentContextAspects;

		currentContextAspects = currentContext.getContextAspectsOfAspectType(theAspect);
		if (currentContextAspects != null) {
			currentContextAspects.forEach(anAspect -> {
				if (theAspect.equals(t_contextAspect.HOW)) {
					the_HOW_editor.setUpForExistingContextAspect(anAspect);
				} else if (theAspect.equals(t_contextAspect.WHAT)) {
					the_WHAT_editor.setUpForExistingContextAspect(anAspect);
				} else if (theAspect.equals(t_contextAspect.WHO)) {
					the_WHO_editor.setUpForExistingContextAspect(anAspect);
				} else if (theAspect.equals(t_contextAspect.WHEN)) {
					the_WHEN_editor.setUpForExistingContextAspect(anAspect);
				} else if (theAspect.equals(t_contextAspect.WHERE)) {
					the_WHERE_editor.setUpForExistingContextAspect(anAspect);
				} else if (theAspect.equals(t_contextAspect.WHY)) {
					the_WHY_editor.setUpForExistingContextAspect(anAspect);
				}
			});
		}

	}

	public void ConsumeDetailEditorOpened() {

	}

	public void ConsumeDetailEditorClosed() {

	}

	public void ConsumeEditorEvent(String editorEvent) {
		if (editorEvent.equals("Context_Changed")) {

			notifyParent();
		}
	}

	private void notifyParent() {
		if (notifyChangeEnabled) {
			if (this.theParent != null) {
				this.theParent.ConsumeEditorEvent("Context_Changed");
			}
		}
	}

	public ContextDefinition assembleContextDefinition() {
		ContextDefinition theContextDefinition;
		ObjectType theAspectObjectType;
		DomainInstance aDomainInstance;
		ContextAspect theContextAspect;

		theContextDefinition = new ContextDefinition();

		theAspectObjectType = the_HOW_editor.getSelectedObjectType();
		if (theAspectObjectType != null) {
			theContextAspect = theContextDefinition.addContextAspect(t_contextAspect.HOW,
					theAspectObjectType.getCode());
			the_HOW_editor.addContextAspectPropertiesToContextAspect(theContextAspect);
		}

		theAspectObjectType = the_WHAT_editor.getSelectedObjectType();
		if (theAspectObjectType != null) {
			theContextAspect = theContextDefinition.addContextAspect(t_contextAspect.WHAT,
					theAspectObjectType.getCode());
			the_WHAT_editor.addContextAspectPropertiesToContextAspect(theContextAspect);
		}

		theAspectObjectType = the_WHO_editor.getSelectedObjectType();
		if (theAspectObjectType != null) {
			theContextAspect = theContextDefinition.addContextAspect(t_contextAspect.WHO,
					theAspectObjectType.getCode());
			the_WHO_editor.addContextAspectPropertiesToContextAspect(theContextAspect);
		}

		theAspectObjectType = the_WHEN_editor.getSelectedObjectType();
		if (theAspectObjectType != null) {
			theContextAspect = theContextDefinition.addContextAspect(t_contextAspect.WHEN,
					theAspectObjectType.getCode());
			the_WHEN_editor.addContextAspectPropertiesToContextAspect(theContextAspect);
		}

		theAspectObjectType = the_WHERE_editor.getSelectedObjectType();
		if (theAspectObjectType != null) {
			theContextAspect = theContextDefinition.addContextAspect(t_contextAspect.WHERE,
					theAspectObjectType.getCode());
			the_WHERE_editor.addContextAspectPropertiesToContextAspect(theContextAspect);
		}

		theAspectObjectType = the_WHY_editor.getSelectedObjectType();
		if (theAspectObjectType != null) {
			theContextAspect = theContextDefinition.addContextAspect(t_contextAspect.WHY,
					theAspectObjectType.getCode());
			the_WHY_editor.addContextAspectPropertiesToContextAspect(theContextAspect);
		}

		return theContextDefinition;
	}

}
