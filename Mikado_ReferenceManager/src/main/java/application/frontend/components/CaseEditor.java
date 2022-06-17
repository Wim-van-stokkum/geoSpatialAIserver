package application.frontend.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.WEB_Session;
import application.frontend.WEB_SimulateTab;
import planspace.caseModel.Case;
import planspace.chefModelTypes.ChefTreeContext;
import planspace.chefModelTypes.ChefType;
import planspace.chefModelTypes.MonitoredConcern;
import planspace.export.JsonExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.jsonExport.JsonPolicyItems;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;

public class CaseEditor extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tab caseEvents;
	private Tab caseObjectiveTab;
	private Tab caseRiskTab;
	private Tab caseOpportunityTab;
	private Tab caseMeasurementTab;
	private Tab caseFactTab;
	private Tab caseMOTab;
	private Tabs aspectTabs;
	private Case theCaseBeingEdited;
	private WEB_SimulateTab myParentTab;

	private TextField textCaseDescription;
	private TextField textExtCaseID;

	private List<MonitoredConcern> myGridConcerns;

	private String concernSizeGrid;
	private TreeGrid<MonitoredConcern> concernGrid;
	private VerticalLayout editCaseConcerns;

	public CaseEditor(WEB_SimulateTab web_SimulateTab) {
		super();
		myGridConcerns = new ArrayList<MonitoredConcern>();
		this.myParentTab = web_SimulateTab;
		configureCasePane();
	}

	private void configureCasePane() {
		Button closeButton;
	
		
		// general settings
		this.setSpacing(true);
		concernSizeGrid = "450px";

		// general settings
		createTaskID();
		createTabs();
		this.add(aspectTabs);
		configureTabContents();
		closeButton = createClosePropertyWindowBut();
		
		this.add(closeButton);
	}

	
	
	
	private void notifyUser(String aNote) {

		Notification notification = new Notification();
		notification.show(aNote, 1500, Notification.Position.BOTTOM_CENTER);

	}
	

	
	private void createTaskID() {

		HorizontalLayout idPane;
		idPane = new HorizontalLayout();
		idPane.setMinWidth("900px");
		idPane.setSpacing(true);
		idPane.getStyle().set("border", "1px solid gray");
		this.textCaseDescription = new TextField("Omschrijving");
		this.textCaseDescription.setMinWidth("400px");
		this.textCaseDescription.addValueChangeListener(listener -> {
			editCaseDescription();
		});

		this.textExtCaseID = new TextField("Externe case identificatie");
		this.textExtCaseID.setMinWidth("350px");
		this.textExtCaseID.addValueChangeListener(listener -> {
			editExternalCaseID();
		});
		idPane.add(textCaseDescription, textExtCaseID);
		this.add(idPane);

	}

	private void configureTabContents() {
		configureConcernTab();
	}

	private Button createClosePropertyWindowBut() {
		Image icon;
		String r = "35px";

		icon = new Image("/closeeditor.png", "close");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Sluit eigenschappen");

		// Set action
		addBut.addClickListener(e -> {

			if (this.myParentTab != null) {
				this.myParentTab.notifyCloseCaseDetail();
			}
		});

		return addBut;
	}

	private void configureConcernTab() {
		this.editCaseConcerns = new VerticalLayout();
		editCaseConcerns.setMinWidth("1000px");
		editCaseConcerns.setSpacing(true);
		editCaseConcerns.setHeight("650px");

		this.createConcernGrid();

		this.editCaseConcerns.setMinWidth("500px");
		editCaseConcerns.setVisible(false);
		editCaseConcerns.add(this.concernGrid);
		this.add(editCaseConcerns);
	}

	private void createTabs() {
		/* tabs */
		caseEvents = new Tab("Gebeurtenissen");
		caseObjectiveTab = new Tab("Doelen");
		caseRiskTab = new Tab("Risico's");
		caseOpportunityTab = new Tab("Kansen");
		caseMeasurementTab = new Tab("Maatregelen");
		caseFactTab = new Tab("Feiten");
		caseMOTab = new Tab("Beheerde objecten");

		aspectTabs = new Tabs(caseEvents, caseObjectiveTab, caseRiskTab, caseOpportunityTab, caseMeasurementTab,
				caseFactTab, caseMOTab);

		aspectTabs.addSelectedChangeListener(event -> {
			if (event.getSelectedTab().getLabel().equals("Gebeurtenissen")) {
				this.selectEvents();
			} else if (event.getSelectedTab().getLabel().equals("Doelen")) {
				this.selectCaseObjectiveTab();
			} else if (event.getSelectedTab().getLabel().equals("Risico's")) {
				this.selectCaseRiskTab();
			} else if (event.getSelectedTab().getLabel().equals("Kansen")) {
				this.selectCaseOpportunityTab();
			} else if (event.getSelectedTab().getLabel().equals("Maatregelen")) {
				this.selectCaseMeasurementTab();
			} else if (event.getSelectedTab().getLabel().equals("Feiten")) {
				this.selectcaseFactTab();
			} else if (event.getSelectedTab().getLabel().equals("Beheerde objecten")) {
				this.selectCaseMoTab();
			}

		});

	}

	private void editCaseDescription() {
		if (this.theCaseBeingEdited != null) {
			if (!this.theCaseBeingEdited.getDescription().equals(textCaseDescription.getValue())) {
				this.theCaseBeingEdited.setDescription(textCaseDescription.getValue());
				theCaseBeingEdited.updatePropertiesOfYourself();
			}
		}

	}

	private void editExternalCaseID() {
		if (this.theCaseBeingEdited != null) {
			if (!this.theCaseBeingEdited.getExternalCaseID().equals(textExtCaseID.getValue())) {
				this.theCaseBeingEdited.setExternalCaseID(textExtCaseID.getValue());

				theCaseBeingEdited.updatePropertiesOfYourself();
			}
		}
	}

	private void selectCaseMoTab() {
		this.editCaseConcerns.setVisible(false);
	}

	private void selectcaseFactTab() {
		this.editCaseConcerns.setVisible(true);
		this.gatherConcernsOfChefType(t_chefType.FACT);
		showConcerns();
	}

	private void selectCaseMeasurementTab() {
		this.editCaseConcerns.setVisible(true);
		this.gatherConcernsOfChefType(t_chefType.MEASUREMENT);
		showConcerns();
	}

	private void selectCaseOpportunityTab() {
		this.editCaseConcerns.setVisible(true);
		this.gatherConcernsOfChefType(t_chefType.OPPORTUNITY);
		showConcerns();
	}

	private void selectCaseRiskTab() {
		this.editCaseConcerns.setVisible(true);
		this.gatherConcernsOfChefType(t_chefType.RISK);
		showConcerns();
	}

	private void selectCaseObjectiveTab() {
		this.editCaseConcerns.setVisible(true);
		this.gatherConcernsOfChefType(t_chefType.GOAL);
		showConcerns();
	}

	private void selectEvents() {
		this.editCaseConcerns.setVisible(true);
		this.gatherConcernsOfChefType(t_chefType.EVENT);
		showConcerns();
	}



	private void showConcerns() {

		concernGrid.setItems(this.myGridConcerns);
		if (!this.concernGrid.isVisible()) {
			this.concernGrid.setVisible(true);
		}
	}

	private void gatherConcernsOfChefType(t_chefType aCT) {
		MonitoredConcern aConcern;

		this.myGridConcerns.clear();
		int i;

		System.out.println("Type " + String.valueOf(aCT) + " -- aantal " + this.theCaseBeingEdited.getMonitoredConcerns().size() );
		for (i = 0; i < this.theCaseBeingEdited.getMonitoredConcerns().size(); i++) {
			aConcern = this.theCaseBeingEdited.getMonitoredConcerns().get(i);
			if (aConcern.getTheChefType().getType().equals(aCT)) {
				this.myGridConcerns.add(aConcern);
			}
		}

	}

	public void prepareForOpening(Case theCase) {
		if (theCase != null) {
			this.theCaseBeingEdited = theCase;
			this.textCaseDescription.setValue(theCase.getDescription());
			this.textExtCaseID.setValue(theCase.getExternalCaseID());
		}

		this.selectEvents();

	}

	private void createConcernGrid() {
		this.concernGrid = new TreeGrid<MonitoredConcern>();
		concernGrid.addComponentHierarchyColumn(MonitoredConcern::getNameLabel).setHeader("Vaststellingen");
		concernGrid.addComponentColumn(MonitoredConcern::getLastValueLabel).setHeader("Laatste vaststelling");
		concernGrid.setRowsDraggable(true);
		concernGrid.setMaxHeight(concernSizeGrid);
		concernGrid.setMinWidth("800px");
		concernGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = concernGrid.addContextMenu();
		myContextMenu.addItem("Grondslag", event -> {
			// to do
		});
	}

}
