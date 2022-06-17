package application.frontend.components;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.SubMenuBase;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.ConditionGridService;
import planspace.chefModelTypes.ChefType;
import planspace.chefModelTypes.ChefTypeContext;
import planspace.domainRules.RuleTemplates.ChefTypeCondition;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_ChefTypeConditiontype;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_stateRegime;

public class ChefMonitoringRulesEditor extends VerticalLayout {
	private Tabs monitoringLevedStateTabs;
	private Tab valueHighTab;
	private Tab valueLowTab;
	private Tab valueMediumTab;
	private Tab valueNeutralTab;
	private Tab valueUnacceptableTab;
	private Tab valueTrueTab;
	private Tabs monitoringBinaireStateTabs;
	private Tab valueFalseTab;
	private Tabs availableStateTabs;
	private Tab valueAvailableTab;
	private Tab valueUnavailableTab;
	private Tabs executionStateTabs;
	private Tab valueAssignedTab;
	private Tab valueCompletedTab;

	private ChefType theChefObjectBeingEdited;
	private ChefTypeContext theContextOfObjectBeingEdited;
	private VerticalLayout editorSection;

	private TreeGrid<ChefTypeCondition> conditionsGrid;
	private VerticalLayout newConditionDetails;
	private Button addCondition;
	private ComboBox<String> theChefTypeSelector;
	private t_ChefTypeConditiontype currentConditionType;
	private ComboBox<ChefType> theChefChildObjectSelector;
	private ChefType selectedObjectLeftForCondition;
	private ComboBox<String> theConditionTypeSelector;
	private HorizontalLayout conditionDetailsButtonLine;
	private VerticalLayout conditionGridPanel;
	private GridContextMenu<ChefTypeCondition> myContextMenu;

	public void setTheChefObjectBeingEdited(ChefType aTypeBeingEdited, ChefTypeContext theContext) {
		this.theChefObjectBeingEdited = aTypeBeingEdited;
		theContextOfObjectBeingEdited = theContext;
		theChefObjectBeingEdited.loadRootConditionForObject();

		this.prepareStateEditor();
		this.refreshConditionGrid();
	}

	public ChefMonitoringRulesEditor() {
		// constructor

		this.createTabsLevedMonitoringState();
		this.createTabsLevedBinairState();
		this.createTabsAvailabilityState();
		this.createTabsExecutedState();
		createEditorSection();

		HorizontalLayout monLayout = new HorizontalLayout();
		monLayout.setWidthFull();
		monLayout.add(monitoringLevedStateTabs, monitoringBinaireStateTabs, availableStateTabs, executionStateTabs,
				editorSection);
		this.add(monLayout);

	}

	private void createEditorSection() {

		this.editorSection = new VerticalLayout();
		this.editorSection.setWidthFull();
		this.editorSection.getStyle().set("overflow", "auto");
		this.editorSection.getStyle().set("border", "1px solid blue");
		this.editorSection.setHeight("800px");

		this.editorSection.setSpacing(true);

		createConditionsGrid();
		createNewConditionDetails();

		addCondition = createAddConditionButton();
		HorizontalLayout aHorSection = new HorizontalLayout();
		aHorSection.add(this.conditionGridPanel, addCondition, newConditionDetails);
		this.editorSection.add(aHorSection);
		this.selectConditionGrid();
	}

	private void selectConditionGrid() {
		this.conditionGridPanel.setVisible(true);
		addCondition.setVisible(true);
		this.newConditionDetails.setVisible(false);

	}

	private void selectNewConditionDetails() {
		this.conditionGridPanel.setVisible(false);

		addCondition.setVisible(false);
		this.setUpConditionDetailsForObjectBeingEdited();
		this.newConditionDetails.setVisible(true);

	}

	private void setUpConditionDetailsForObjectBeingEdited() {
		List<t_chefType> typesOfDirectChildren;
		List<String> filteredTypeList;
		List<t_ChefTypeConditiontype> suitableConditionTypes;
		List<String> conditionTypeOptions;

		theChefChildObjectSelector.setVisible(false);
		theChefTypeSelector.setVisible(false);

		suitableConditionTypes = ChefTypeCondition
				.getChefConditionTypesForChefType(this.theChefObjectBeingEdited.getType());
		conditionTypeOptions = ChefTypeCondition.convertConditionTypeListToInformalOptionList(t_language.NL,
				suitableConditionTypes);
		theConditionTypeSelector.setItems(conditionTypeOptions);
		theConditionTypeSelector.setVisible(true);

	}

	private Button createAddConditionButton() {

		Icon icon;
		Button addBut;

		icon = VaadinIcon.ADD_DOCK.create();

		addBut = new Button("", icon);

		// Set action
		addBut.addClickListener(e -> {
			this.currentConditionType = t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE;
			selectedObjectLeftForCondition = null;

			this.selectNewConditionDetails();
			// this.restoreByName();
		});

		return addBut;
	}

	private Button saveNewConditionDetailsButton() {

		Icon icon;
		Button addBut;

		icon = VaadinIcon.CHECK_CIRCLE_O.create();

		addBut = new Button("", icon);

		// Set action
		addBut.addClickListener(e -> {
			this.addNewCondition();
			this.selectConditionGrid();
			// this.restoreByName();
		});

		return addBut;
	}

	private Button cancelNewConditionDetailsButton() {

		Icon icon;
		Button addBut;

		icon = VaadinIcon.CLOSE_SMALL.create();

		addBut = new Button("", icon);

		// Set action
		addBut.addClickListener(e -> {
			this.selectConditionGrid();
			// this.restoreByName();
		});

		return addBut;
	}

	private void createTabsLevedMonitoringState() {
		this.monitoringLevedStateTabs = new Tabs();
		monitoringLevedStateTabs.setOrientation(Orientation.VERTICAL);
		this.valueHighTab = new Tab("Hoog");
		this.valueLowTab = new Tab("Laag");
		this.valueMediumTab = new Tab("Medium");
		this.valueNeutralTab = new Tab("Neutraal");
		this.valueUnacceptableTab = new Tab("Onacceptabel");
		monitoringLevedStateTabs.add(valueHighTab, valueLowTab, valueMediumTab, valueNeutralTab, valueUnacceptableTab);
	}

	private void createTabsLevedBinairState() {
		this.monitoringBinaireStateTabs = new Tabs();
		monitoringBinaireStateTabs.setOrientation(Orientation.VERTICAL);
		this.valueTrueTab = new Tab("Waar");
		this.valueFalseTab = new Tab("Onwaar");

		monitoringBinaireStateTabs.add(valueTrueTab, valueFalseTab);
	}

	private void createTabsAvailabilityState() {
		this.availableStateTabs = new Tabs();
		monitoringBinaireStateTabs.setOrientation(Orientation.VERTICAL);
		this.valueAvailableTab = new Tab("Beschikbaar");
		this.valueUnavailableTab = new Tab("Onbeschikbaar");

		availableStateTabs.add(valueAvailableTab, valueUnavailableTab);
	}

	private void createTabsExecutedState() {
		this.executionStateTabs = new Tabs();
		executionStateTabs.setOrientation(Orientation.VERTICAL);
		this.valueAssignedTab = new Tab("Aangestuurd");
		this.valueCompletedTab = new Tab("Uitgevoerd");
		executionStateTabs.add(valueAvailableTab, valueUnavailableTab);
	}

	private void prepareStateEditor() {

		monitoringLevedStateTabs.setVisible(false);
		monitoringBinaireStateTabs.setVisible(false);
		availableStateTabs.setVisible(false);
		executionStateTabs.setVisible(false);

		if (this.theChefObjectBeingEdited.getStateRegime().equals(t_stateRegime.LEVELED)) {
			monitoringLevedStateTabs.setVisible(true);
		} else if (this.theChefObjectBeingEdited.getStateRegime().equals(t_stateRegime.BINARY)) {
			monitoringBinaireStateTabs.setVisible(true);
		} else if (this.theChefObjectBeingEdited.getStateRegime().equals(t_stateRegime.AVAILABLE)) {
			availableStateTabs.setVisible(true);
		} else if (this.theChefObjectBeingEdited.getStateRegime().equals(t_stateRegime.TASKSTATE)) {
			executionStateTabs.setVisible(true);
		}

		this.prepareContextMenu();

	}

	private void prepareContextMenu() {

		SubMenuBase aSub;

		myContextMenu = this.conditionsGrid.addContextMenu();
		myContextMenu.removeAll();
		myContextMenu.addItem("Toevoegen", event -> {
			selectNewConditionDetails();
		});
		myContextMenu.addItem("Verwijderen met behoud subcondities", event -> {
			deleteConditionNonRecursive();
		});
		myContextMenu.addItem("Verwijderen inclusief subcondities", event -> {
			deleteConditionRecursive();
		});

	}

	private void deleteConditionNonRecursive() {

		this.conditionsGrid.getSelectedItems().forEach(aSelectedItem -> {
			if (this.theChefObjectBeingEdited != null) {
				if (this.theChefObjectBeingEdited.getTheRootCondition() != null) {
					this.theChefObjectBeingEdited.getTheRootCondition().deleteSubCondition(aSelectedItem, false);
					this.theChefObjectBeingEdited.saveRootChefCondition();
				}
				
				this.refreshConditionGrid();
			}
		});


	}

	private void deleteConditionRecursive() {

		this.conditionsGrid.getSelectedItems().forEach(aSelectedItem -> {
			if (this.theChefObjectBeingEdited != null) {
				if (this.theChefObjectBeingEdited.getTheRootCondition() != null) {
					this.theChefObjectBeingEdited.getTheRootCondition().deleteSubCondition(aSelectedItem, true);
					this.theChefObjectBeingEdited.saveRootChefCondition();
				}
				
				this.refreshConditionGrid();
			}
		});
	
	}

	private void createConditionsGrid() {
		this.conditionGridPanel = new VerticalLayout();

		this.conditionsGrid = new TreeGrid<ChefTypeCondition>();

		conditionsGrid.addComponentHierarchyColumn(ChefTypeCondition::getMyGUIpresence).setHeader("Voorwaarden");

		conditionsGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		conditionsGrid.setRowsDraggable(true);
		conditionsGrid.setHeight("750px");
		conditionsGrid.setWidth("830px");
		conditionsGrid.setVerticalScrollingEnabled(true);

		ComboBox<String> mainOperator = new ComboBox<String>();
		mainOperator.setItems("Alle onderstaande voorwaarden moeten gelden",
				"Tenminste een van onderstaande voorwaarden moet gelden");
		mainOperator.setWidth("500px");
		mainOperator.setValue("Tenminste een van onderstaande voorwaarden moet gelden");
		mainOperator.addValueChangeListener(listen -> {
			if (listen.getValue().equals("Alle onderstaande voorwaarden moeten gelden")) {
				this.theChefObjectBeingEdited.getTheRootCondition()
						.setConditionType(t_ChefTypeConditiontype.ALL_UNDERLYING);
				this.theChefObjectBeingEdited.saveRootChefCondition();
			} else {
				this.theChefObjectBeingEdited.getTheRootCondition()
						.setConditionType(t_ChefTypeConditiontype.ONE_UNDERLYING);
				this.theChefObjectBeingEdited.saveRootChefCondition();
			}
		});

		conditionGridPanel.add(mainOperator, conditionsGrid);
	}

	private void createNewConditionDetails() {

		this.createSelectorForConditionTypeSelection();
		this.createSelectorForChefType();
		this.createChildObjectSelector();
		this.createNewConditionButtonLine();
		this.createNewConditionPanel();
		newConditionDetails.add(this.theConditionTypeSelector, theChefTypeSelector, theChefChildObjectSelector,
				conditionDetailsButtonLine);
	}

	private ComboBox<ChefType> createChildObjectSelector() {
		theChefChildObjectSelector = new ComboBox<ChefType>("Monitoring aspect");
		theChefChildObjectSelector.setItemLabelGenerator(child -> child.getName());
		theChefChildObjectSelector.setWidth("600px");
		theChefChildObjectSelector.addValueChangeListener(listener -> {

			this.selectedObjectLeftForCondition = listener.getValue();
		});
		return theChefChildObjectSelector;
	}

	private ComboBox<String> createSelectorForConditionTypeSelection() {

		theConditionTypeSelector = new ComboBox<String>("Type voorwaarde");
		theConditionTypeSelector.setWidth("600px");
		theConditionTypeSelector.addValueChangeListener(listener -> {
			t_ChefTypeConditiontype selected;
			if (listener.getValue() != null) {
				hideConditionTypeDependendControls();
				selected = ChefTypeCondition.getFormalForInformalChefTypeConditionType(t_language.NL,
						listener.getValue());
				if (selected != null) {
					this.currentConditionType = selected;
					this.prepareForConditionType(selected);
				}
			}
		});
		return theConditionTypeSelector;
	}

	private void hideConditionTypeDependendControls() {
		if (theChefChildObjectSelector != null) {
			theChefChildObjectSelector.setVisible(false);
		}
		if (theChefTypeSelector != null) {
			theChefTypeSelector.setVisible(false);
		}
	}

	private void prepareForConditionType(t_ChefTypeConditiontype condType) {

		if (condType.equals(t_ChefTypeConditiontype.CHEFTYPE_HAS_STATE)) {
			this.setupChildSelector();
		}

	}

	private void setupChildSelector() {
		List<t_chefType> typesOfDirectChildren;
		List<String> filteredTypeList;

		typesOfDirectChildren = this.theContextOfObjectBeingEdited.getCurrentChildTypes();
		filteredTypeList = ChefType.ConvertToStringList(t_language.NL, typesOfDirectChildren);
		this.theChefTypeSelector.setItems(filteredTypeList);
		this.theChefTypeSelector.setVisible(true);
		this.theChefChildObjectSelector.clear();
		theChefChildObjectSelector.setVisible(false);
	}

	private ComboBox<String> createSelectorForChefType() {
		theChefTypeSelector = new ComboBox<String>("Type monitoring aspect");
		theChefTypeSelector.setWidth("250px");
		theChefTypeSelector.addValueChangeListener(listener -> {
			t_chefType selectedFormal;
			String selectInformal;

			selectInformal = listener.getValue();
			if (selectInformal != null) {
				selectedFormal = ChefType.getFormalTypeByString(t_language.NL, selectInformal);
				if (selectedFormal != null) {
					theChefChildObjectSelector
							.setItems(this.theContextOfObjectBeingEdited.getChildsForType(selectedFormal));

					theChefChildObjectSelector.setVisible(true);

				}
			}
		});
		return theChefTypeSelector;
	}

	private void createNewConditionPanel() {
		this.newConditionDetails = new VerticalLayout();
		newConditionDetails.setHeight("800px");
		newConditionDetails.setWidth("850px");
		Label aLabel = new Label("nieuw conditie");
		newConditionDetails.add(aLabel);

	}

	private void createNewConditionButtonLine() {
		Button cancel = cancelNewConditionDetailsButton();
		Button save = this.saveNewConditionDetailsButton();
		conditionDetailsButtonLine = new HorizontalLayout();
		conditionDetailsButtonLine.add(cancel, save);

	}

	private void addNewCondition() {
		ChefTypeCondition theNewCondition;
		ChefTypeCondition potentialParent;

		theNewCondition = new ChefTypeCondition();
		if (this.selectedObjectLeftForCondition == null) {
			// for all / if any -> object edited
			this.selectedObjectLeftForCondition = this.theChefObjectBeingEdited;
		}
		theNewCondition.setTheChefTypeObject(this.selectedObjectLeftForCondition);
		theNewCondition.setConditionType(this.currentConditionType);

		if (this.conditionsGrid.getSelectedItems() != null) {
			if (this.conditionsGrid.getSelectedItems().size() > 0) {
				this.conditionsGrid.getSelectedItems().forEach(anItem -> {
					if (anItem.acceptsAsSubConditions(theNewCondition)) {
						anItem.addSubCondition(theNewCondition);
					} else {
						this.addTopCondition(theNewCondition);

					}
				});
			} else {
				this.addTopCondition(theNewCondition);
			}
		} else {
			this.addTopCondition(theNewCondition);
		}
		this.theChefObjectBeingEdited.saveRootChefCondition();
		this.refreshConditionGrid();
	}

	private void addTopCondition(ChefTypeCondition theNewCondition) {

		this.theChefObjectBeingEdited.getTheRootCondition().addSubCondition(theNewCondition);
	}

	private void refreshConditionGrid() {
		ConditionGridService condGridService = new ConditionGridService(
				this.theChefObjectBeingEdited.getTheRootCondition().getMySubconditions());
		this.conditionsGrid.setItems(condGridService.getRootNodes(), condGridService::getChildNodes);

		// this.conditionsGrid.setItems(theConditions);

	}

	private void notifyUser(String aNote) {

		// Notification notification = new Notification();
		Notification.show(aNote, 1500, Notification.Position.BOTTOM_CENTER);

	}
}
