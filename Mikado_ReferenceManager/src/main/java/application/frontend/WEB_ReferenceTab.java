package application.frontend;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import application.frontend.components.ChefTaxonomyGridEditor;
import application.frontend.components.ChefTypeEditor;
import application.frontend.components.PolicyReferenceWindow;
import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefType;
import planspace.compliance.SourceReference;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_itemTypeDropped;

public class WEB_ReferenceTab extends WEB_AbstractedTab {

	private VerticalLayout referenceMainWindow;
	protected HorizontalLayout theWorkSection;
	protected HorizontalLayout myGuiLayout;

	PolicyReferenceWindow myPolicyReferenceEditor;
	ChefTaxonomyGridEditor myChefTaxonomyGridEditor;
	private ChefTypeEditor myChefTypeDetailEditor;
	private ChefType currentItem;
	private ChefType currentChefObject;
	private HorizontalLayout topLineControls;
	private Button annotateModeBut;
	private Button showTaxFull;
	private t_itemTypeDropped currentItemTypeDropped;
	private SourceReference currentSourceReferenceDropped;
	private List<ChefTaxonomyItem> currentTaxItemsDropped;


	public WEB_ReferenceTab(String tabNaam, WEB_Workspace aWorkSpace) {

		super(tabNaam, aWorkSpace);
		

		createGuiPresence(this.myContent);
		this.myPolicyReferenceEditor.setVisible(false);


	}

	@Override
	public void prepareOpening() {
		this.myPolicyReferenceEditor.prepareOpening();
		this.myChefTaxonomyGridEditor.prepareOpening();
		this.myPolicyReferenceEditor.setVisible(false);
	}

	private void createGuiPresence(VerticalLayout theContentToUse) {

		referenceMainWindow = new VerticalLayout();
		referenceMainWindow.getStyle().set("display", "block");
		referenceMainWindow.setSizeUndefined();
		referenceMainWindow.setWidthFull();

		this.topLineControls = createTopLineControls();

		myPolicyReferenceEditor = new PolicyReferenceWindow(this);
		WEB_Session.getInstance().setPolicyReferenceEditor(myPolicyReferenceEditor);
		myPolicyReferenceEditor.setRadAlmereAsPolicyFrameWork();
		WEB_Session.getInstance().setCurrentComplianceEditor(this);

		// Create Taxonomy editor and pdf viewer
		this.createWorkSection();

		myGuiLayout = new HorizontalLayout();
		this.myGuiLayout.setWidthFull();

		myGuiLayout.add(myPolicyReferenceEditor, theWorkSection);

		referenceMainWindow.add(myGuiLayout);

		if (theContentToUse != null) {
			theContentToUse.add(topLineControls, referenceMainWindow);
		}
	}

	private HorizontalLayout createTopLineControls() {
		HorizontalLayout topButLine;

		topButLine = new HorizontalLayout();
		this.annotateModeBut = createTogglePolicyReferenceButton();
		this.showTaxFull = createShowTaxButton();
		topButLine.add(annotateModeBut, showTaxFull);
		
		
		return topButLine;
	}

	

	private Button createTogglePolicyReferenceButton() {
		Image icon = new Image("/annotate.png", "Annotaties");
		icon.setHeight("35px");
		icon.setWidth("35px");
		Button toggle;

		toggle = new Button("", icon);
		toggle.getElement().setAttribute("title", "Annotatie overzicht");
		toggle.addClickListener(listener -> {
			if (this.myPolicyReferenceEditor.isVisible()) {
				this.myPolicyReferenceEditor.setVisible(false);
			} else {
				myPolicyReferenceEditor.setVisible(true);
			}

		});
		return toggle;
	}

	private Button createShowTaxButton() {

		Button taxBut;
		Image icon = new Image("/taxonomy.png", "Taxonomie");
		icon.setHeight("35px");
		icon.setWidth("35px");

		taxBut = new Button("", icon);

		// Set action
		taxBut.addClickListener(e -> {
		 if (this.myChefTypeDetailEditor.isVisible()) {
			 this.myChefTypeDetailEditor.setVisible(false);
		 }
		 
		 this.myChefTaxonomyGridEditor.setTaxViewModeFull();
		   this.myChefTaxonomyGridEditor.setVisible(true);
			
		});
		
	
	
		return taxBut;
	}



	private void showGoalTaxonomy() {
		if (this.myChefTaxonomyGridEditor != null && this.myChefTypeDetailEditor != null) {

			this.myChefTypeDetailEditor.setVisible(false);

			this.myChefTaxonomyGridEditor.setVisible(true);
			this.myChefTaxonomyGridEditor.ReloadTaxGrid();
		}
	}

	private void createWorkSection() {
		this.theWorkSection = new HorizontalLayout();
		this.theWorkSection.setWidthFull();
		this.theWorkSection.getStyle().set("overflow", "auto");
		this.theWorkSection.getStyle().set("border", "1px solid green");
		this.theWorkSection.setWidthFull();
		this.theWorkSection.setSpacing(true);
		this.theWorkSection.setHeight("1000px");

		myChefTaxonomyGridEditor = new ChefTaxonomyGridEditor(this);
		WEB_Session.getInstance().setCurrentTaxGridEditor(myChefTaxonomyGridEditor);
		myChefTypeDetailEditor = new ChefTypeEditor(this);
		myChefTypeDetailEditor.setVisible(false);
		WEB_Session.getInstance().setCurrentGoalEditor(myChefTypeDetailEditor);
		this.theWorkSection.add(myChefTaxonomyGridEditor, myChefTypeDetailEditor);

	}


	
	public void requestToEditChefType(ChefTaxonomyItem anItem) {
		if ( this.myChefTypeDetailEditor != null) {
			if (anItem != null) {
			//	this.myChefTaxonomyGridEditor.setVisible(false);

				this.myChefTypeDetailEditor.prepare();
				this.myChefTypeDetailEditor.setTheTaxonomyItemBeingEdited(anItem);

				this.myChefTypeDetailEditor.setVisible(true);
				// myChefTaxonomyGridEditor.showChefTypeRelatedNodesInTaxonomy(currentSelectedItem);
			}

		}
		
	}

	public void notifySourceReferenceDragged(SourceReference aDraggedSourceReference) {
		this.currentItemTypeDropped = t_itemTypeDropped.SOURCE_REFERENCE;
		this.currentSourceReferenceDropped = aDraggedSourceReference;

	}

	public t_itemTypeDropped getCurrentItemTypeDropped() {
		return currentItemTypeDropped;
	}

	public SourceReference getCurrentSourceReferenceDropped() {
		return currentSourceReferenceDropped;
	}
	

	public List<ChefTaxonomyItem> getCurrentTaxItemsDropped() {
		return currentTaxItemsDropped;
	}

	public void notifyTaxonomyItemsDragged(List<ChefTaxonomyItem> taxItemsBeingDragged) {
		this.currentItemTypeDropped = t_itemTypeDropped.TAXONOMY_ITEM;
		this.currentTaxItemsDropped = taxItemsBeingDragged;
	}

	public void notifyDropHandled() {
		this.currentItemTypeDropped = t_itemTypeDropped.UNKNOWN;
		this.currentTaxItemsDropped = null;
		this.currentSourceReferenceDropped =null;
	}

	public void requestSyncEditors() {
		if(this.myChefTaxonomyGridEditor != null) {
			this.myChefTaxonomyGridEditor.RefreshTaxGrid();
		}
		
	}

}
