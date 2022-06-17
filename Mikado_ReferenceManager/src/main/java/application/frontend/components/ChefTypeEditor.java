package application.frontend.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.WEB_ReferenceTab;
import application.frontend.WEB_Session;
import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefType;
import planspace.chefModelTypes.ChefTypeContext;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;

public class ChefTypeEditor extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private ChefType theChefObjectBeingEdited;
	private ChefTypeContext theContextOfTheObjectBeingEdited;

	private Tab propertyTab;
	private Tab dependencyTab;
	private Tab monitoringTab;
	private Tab effectTab;
	private Tab effectivityTab;
	private Tabs aspectTabs;
	private Tab TaxTab;

	private VerticalLayout propertySection;
	private VerticalLayout dependencySection;
	private VerticalLayout monitorSection;
	private VerticalLayout effectSection;
	private VerticalLayout effectivitySection;

	private TextField chefTaxNameLine;

	private ComboBox<String> chefTaxType;
	private ComboBox<String> chefTaxTheme;
	private ComboBox<String> chefTaxGoalType;

	private TextField chefTaxOtherThemeLine;
	private TextArea chefTaxDescriptionLine;
	private Button chefTaxOpenTheSourceButton;
	private List<String> themes;

	private TreeGrid<ChefType> dependencyRisksGrid;
	private TreeGrid<ChefType> dependencyParentGrid;
	private TreeGrid<ChefType> dependencyOpportunityGrid;
	private TreeGrid<ChefType> dependencyFactsGrid;
	private TreeGrid<ChefType> dependencyDataGrid;
	private TreeGrid<ChefType> dependencyDataSourceGrid;
	private TreeGrid<ChefType> dependencyNormGrid;
	private TreeGrid<ChefType> dependencySubGoalGrid;
	private TreeGrid<ChefType> dependencyMeasurementGrid;
	private TreeGrid<ChefType> dependencyRuleGrid;
	private TreeGrid<ChefType> dependencyUnkownGrid;
	private TreeGrid<ChefType> dependencyEventGrid;


	private String contextGridSize;



	private ChefMonitoringRulesEditor theMonitoringRulesEditor;
	private WEB_ReferenceTab myParentReferenceManager;

	public ChefTypeEditor(WEB_ReferenceTab myWEB_ReferenceTab) {
		// constructor
		this.myParentReferenceManager = myWEB_ReferenceTab;
		this.createTabs();
		this.createGoalSection();
		this.createDependencySection();

		this.createMonitoringSection();
		this.createEffectSection();
		this.createEffectivitySection();
		this.selectPropertyTab();

		this.add(aspectTabs, propertySection,  monitorSection, effectSection, dependencySection,
				effectivitySection);

		theContextOfTheObjectBeingEdited = new ChefTypeContext();
	}

	private void createEffectivitySection() {
		this.effectivitySection = new VerticalLayout();
		this.effectivitySection.setWidthFull();
		this.effectivitySection.getStyle().set("overflow", "auto");
		this.effectivitySection.setHeight("990px");
		this.effectivitySection.setSpacing(true);

	}

	private void createEffectSection() {
		this.effectSection = new VerticalLayout();
		this.effectSection.setWidthFull();
		this.effectSection.getStyle().set("overflow", "auto");
		this.effectSection.setHeight("990px");
		this.effectSection.setSpacing(true);

	}



	private void createDependencySection() {
		this.dependencySection = new VerticalLayout();
		this.dependencySection.setWidthFull();
		this.dependencySection.getStyle().set("overflow", "auto");
		this.dependencySection.setHeight("990px");
		this.dependencySection.setSpacing(true);
		contextGridSize = "150px";
		createContextParentGrid();

		createDependencySubGoalGrid();
		createDependencyRiskGrid();
		createDependencyOpportunityGrid();
		createDependencyFactsGrid();
		createDependencyDataGrid();
		createDependencyNormGrid();
		createDependencyDataSourceGrid();
		createDependencyRuleGrid();
		createDependencyMeasurementGrid();
		createDependencyUnknownGrid();
		createDependencyEventGrid();

		dependencySection.add(dependencyParentGrid, this.dependencyEventGrid, dependencySubGoalGrid,
				dependencyRisksGrid, this.dependencyOpportunityGrid, this.dependencyFactsGrid, this.dependencyNormGrid,
				this.dependencyDataGrid, this.dependencyDataSourceGrid, this.dependencyRuleGrid,
				this.dependencyMeasurementGrid, this.dependencyUnkownGrid);

	}

	private void createContextParentGrid() {
		this.dependencyParentGrid = new TreeGrid<ChefType>();

		dependencyParentGrid.addHierarchyColumn(ChefType::getName).setHeader("Ondersteund:");

		dependencyParentGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyParentGrid.setRowsDraggable(true);
		dependencyParentGrid.setMaxHeight(contextGridSize);
		dependencyParentGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyParentGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencySubGoalGrid() {
		this.dependencySubGoalGrid = new TreeGrid<ChefType>();
		dependencySubGoalGrid.addHierarchyColumn(ChefType::getName).setHeader("Gerealiseerd door subdoelen");
		dependencySubGoalGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencySubGoalGrid.setRowsDraggable(true);
		dependencySubGoalGrid.setMaxHeight(contextGridSize);
		dependencySubGoalGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencySubGoalGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyRuleGrid() {
		this.dependencyRuleGrid = new TreeGrid<ChefType>();
		dependencyRuleGrid.addHierarchyColumn(ChefType::getName).setHeader("Heeft beleidsregels");
		dependencyRuleGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyRuleGrid.setRowsDraggable(true);
		dependencyRuleGrid.setMaxHeight(contextGridSize);
		dependencyRuleGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyRuleGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyMeasurementGrid() {
		this.dependencyMeasurementGrid = new TreeGrid<ChefType>();
		dependencyMeasurementGrid.addHierarchyColumn(ChefType::getName).setHeader("Heeft toe te passen maatregelen");
		dependencyMeasurementGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyMeasurementGrid.setRowsDraggable(true);
		dependencyMeasurementGrid.setMaxHeight(contextGridSize);
		dependencyMeasurementGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyMeasurementGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyUnknownGrid() {
		this.dependencyUnkownGrid = new TreeGrid<ChefType>();
		dependencyUnkownGrid.addHierarchyColumn(ChefType::getName).setHeader("Gebaseerd op bewakingsobjecten");
		dependencyUnkownGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyUnkownGrid.setRowsDraggable(true);
		dependencyUnkownGrid.setMaxHeight(contextGridSize);
		dependencyUnkownGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyUnkownGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyEventGrid() {
		this.dependencyEventGrid = new TreeGrid<ChefType>();
		dependencyEventGrid.addHierarchyColumn(ChefType::getName).setHeader("Reageert op gebeurtenissen");
		dependencyEventGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyEventGrid.setRowsDraggable(true);
		dependencyEventGrid.setMaxHeight(contextGridSize);
		dependencyEventGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyEventGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyRiskGrid() {
		this.dependencyRisksGrid = new TreeGrid<ChefType>();
		dependencyRisksGrid.addHierarchyColumn(ChefType::getName).setHeader("Bedreigd door risico's");
		dependencyRisksGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyRisksGrid.setRowsDraggable(true);
		dependencyRisksGrid.setMaxHeight(contextGridSize);
		dependencyRisksGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyRisksGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyOpportunityGrid() {
		this.dependencyOpportunityGrid = new TreeGrid<ChefType>();
		dependencyOpportunityGrid.addHierarchyColumn(ChefType::getName).setHeader("Gestimuleerd door kansen");
		dependencyOpportunityGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyOpportunityGrid.setRowsDraggable(true);
		dependencyOpportunityGrid.setMaxHeight(this.contextGridSize);
		dependencyOpportunityGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyOpportunityGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyFactsGrid() {
		this.dependencyFactsGrid = new TreeGrid<ChefType>();
		dependencyFactsGrid.addHierarchyColumn(ChefType::getName).setHeader("Baseert zich op feiten");
		dependencyFactsGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyFactsGrid.setRowsDraggable(true);
		dependencyFactsGrid.setMaxHeight(this.contextGridSize);
		dependencyFactsGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyFactsGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyDataGrid() {
		this.dependencyDataGrid = new TreeGrid<ChefType>();
		dependencyDataGrid.addHierarchyColumn(ChefType::getName).setHeader("Feiten gebaseerd op gegevens");
		dependencyDataGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyDataGrid.setRowsDraggable(true);
		dependencyDataGrid.setMaxHeight(contextGridSize);
		dependencyDataGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyDataGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyNormGrid() {
		this.dependencyNormGrid = new TreeGrid<ChefType>();
		dependencyNormGrid.addHierarchyColumn(ChefType::getName).setHeader("Gebaseerd op normen");
		dependencyNormGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyNormGrid.setRowsDraggable(true);
		dependencyNormGrid.setMaxHeight(contextGridSize);
		dependencyNormGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyNormGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createDependencyDataSourceGrid() {
		this.dependencyDataSourceGrid = new TreeGrid<ChefType>();
		dependencyDataSourceGrid.addHierarchyColumn(ChefType::getName).setHeader("Gebruikt data bronnen");
		dependencyDataSourceGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		dependencyDataSourceGrid.setRowsDraggable(true);
		dependencyDataSourceGrid.setMaxHeight(contextGridSize);
		dependencyDataSourceGrid.setVerticalScrollingEnabled(true);
		GridContextMenu myContextMenu = dependencyDataSourceGrid.addContextMenu();
		myContextMenu.addItem("Info", event -> {
			// to do
		});
	}

	private void createGoalSection() {
		this.propertySection = new VerticalLayout();
		this.propertySection.setWidthFull();
		this.propertySection.getStyle().set("overflow", "auto");
		this.propertySection.setHeight("990px");
		this.propertySection.setSpacing(true);
		fillPropertyWindow();

	}

	private void fillPropertyWindow() {
		List<String> types;
		List<String> themes;
		HorizontalLayout horClassGroup;

		// content
		this.chefTaxNameLine = new TextField();
		this.chefTaxNameLine.setLabel("Korte omschrijving");
		this.chefTaxNameLine.setWidthFull();
		this.chefTaxNameLine.addValueChangeListener(event -> {
			this.changeTaxNameOfCurrentItem(event.getValue());
		});

		this.chefTaxType = new ComboBox<String>();
		this.chefTaxType.setLabel("Type");
		types = new ArrayList<String>();
		types.addAll(ChefType.getTypesAsStringList(false));
		this.chefTaxType.setItems(types);
		this.chefTaxType.setWidth("50%");
		this.chefTaxType.addValueChangeListener(event -> {
			this.changeTypeOfCurrentItem(event.getValue());
		});

		this.chefTaxTheme = new ComboBox<String>();
		this.chefTaxTheme.setLabel("Thema");

		// this.chefTaxTheme.setWidth("50%");
		this.chefTaxTheme.addValueChangeListener(event -> {
			this.changeThemeOfCurrentItem(event.getValue());
		});

		this.chefTaxGoalType = new ComboBox<String>();
		this.chefTaxGoalType.setLabel("Doel type");
		ArrayList<String> goalTypes = new ArrayList<String>();
		goalTypes.add("Stimuleren / vergroten");
		goalTypes.add("Ontmoedigen/ reduceren");
		goalTypes.add("Streven naar / instandhouden");
		goalTypes.add("Verbieden / straffen");
		goalTypes.add("Reguleren / toestemmen");
		goalTypes.add("Onbekend");

		chefTaxGoalType.setVisible(false);
		this.chefTaxGoalType.setItems(goalTypes);
		this.chefTaxGoalType.setWidth("50%");
		this.chefTaxGoalType.addValueChangeListener(event -> {
			this.changeGoalTypeOfCurrentItem(event.getValue());
		});

		horClassGroup = new HorizontalLayout();
		horClassGroup.add(chefTaxType, chefTaxGoalType, chefTaxTheme);

		this.chefTaxOtherThemeLine = new TextField();
		this.chefTaxOtherThemeLine.setLabel("Afwijkend thema");
		this.chefTaxOtherThemeLine.setWidthFull();
		this.chefTaxOtherThemeLine.setVisible(false);

		this.chefTaxDescriptionLine = new TextArea();
		this.chefTaxDescriptionLine.setLabel("Omschrijving");
		this.chefTaxDescriptionLine.setWidthFull();
		this.chefTaxDescriptionLine.setHeight("150px");
		this.chefTaxDescriptionLine.addValueChangeListener(event -> {
			this.changeDescriptionOfCurrentItem(event.getValue());
		});

		HorizontalLayout butlineUnder = new HorizontalLayout();
		this.chefTaxOpenTheSourceButton = new Button("Bron....");
		this.chefTaxOpenTheSourceButton.setVisible(false);
		this.chefTaxOpenTheSourceButton.addClickListener(event -> {
			if (this.theChefObjectBeingEdited != null) {
				this.theChefObjectBeingEdited.openReferenceDocuments();
			}
		});

		butlineUnder.add(chefTaxOpenTheSourceButton);
		this.propertySection.add(chefTaxNameLine, horClassGroup, chefTaxOtherThemeLine, chefTaxDescriptionLine,
				butlineUnder);

	}

	private void changeDescriptionOfCurrentItem(String value) {
		if (this.theChefObjectBeingEdited != null) {
			this.theChefObjectBeingEdited.setDescription(value);
			theChefObjectBeingEdited.storeYourself();
			syncOtherEditors();
		}

	}

	private void changeTaxNameOfCurrentItem(String value) {
		if (this.theChefObjectBeingEdited != null) {
			this.theChefObjectBeingEdited.setName(value);
			theChefObjectBeingEdited.storeYourself();
			syncOtherEditors();
		}

	}

	private void changeGoalTypeOfCurrentItem(String value) {
		if (this.theChefObjectBeingEdited != null) {
			this.theChefObjectBeingEdited.setGoalTypeAsString(t_language.NL, value);
			theChefObjectBeingEdited.storeYourself();
			syncOtherEditors();
		}

	}

	private void changeThemeOfCurrentItem(String value) {
		if (this.theChefObjectBeingEdited != null) {
			this.theChefObjectBeingEdited.setThemeAsString(value);
			theChefObjectBeingEdited.storeYourself();
			syncOtherEditors();
		}

	}

	private void changeTypeOfCurrentItem(String value) {
		if (this.theChefObjectBeingEdited != null) {
			this.theChefObjectBeingEdited.setTypeByString(t_language.NL, value);
			theChefObjectBeingEdited.storeYourself();
			syncOtherEditors();
		}

	}

	private void syncOtherEditors() {
		if (this.myParentReferenceManager != null) {
			this.myParentReferenceManager.requestSyncEditors();
		}
		
	}

	private void createTabs() {
		/* tabs */
		propertyTab = new Tab("Doel");
		dependencyTab = new Tab("Afhankelijkheden");
		TaxTab = new Tab("Taxonomie");

		monitoringTab = new Tab("Monitoring");
		effectTab = new Tab("Beoogde effecten");
		effectivityTab = new Tab("Effectiviteit");

		aspectTabs = new Tabs(propertyTab, TaxTab, monitoringTab, effectTab, effectivityTab, dependencyTab);

		aspectTabs.addSelectedChangeListener(event -> {
			if (event.getSelectedTab().getLabel().equals("Doel")) {
				this.selectPropertyTab();
			} else if (event.getSelectedTab().getLabel().equals("Afhankelijkheden")) {
				this.selectDependencyTab();
			} else if (event.getSelectedTab().getLabel().equals("Taxonomie")) {
				this.selectTaxTab();
			} else if (event.getSelectedTab().getLabel().equals("Monitoring")) {
				this.selectMonitoringTab();
			} else if (event.getSelectedTab().getLabel().equals("Beoogde effecten")) {
				this.selectEffectTab();
			} else if (event.getSelectedTab().getLabel().equals("Effectiviteit")) {
				this.selectEffectivityTab();
			}

		});

	}

	private void selectEffectivityTab() {

		this.updateBeforeSectionSwith();
		this.propertySection.setVisible(false);
		this.dependencySection.setVisible(false);
		this.monitorSection.setVisible(false);
		this.effectSection.setVisible(false);
		this.effectivitySection.setVisible(true);


	}

	private void selectEffectTab() {
		this.updateBeforeSectionSwith();
		this.propertySection.setVisible(false);
		this.dependencySection.setVisible(false);
		this.monitorSection.setVisible(false);
		this.effectSection.setVisible(true);
		this.effectivitySection.setVisible(false);
	

	}

	private void selectMonitoringTab() {
		this.updateBeforeSectionSwith();
		this.prepareMonitoringSection();

		this.propertySection.setVisible(false);
		this.dependencySection.setVisible(false);
		this.monitorSection.setVisible(true);
		this.effectSection.setVisible(false);
		this.effectivitySection.setVisible(false);
	

	}

	private void selectTaxTab() {
		this.updateBeforeSectionSwith();
		this.propertySection.setVisible(false);
		this.dependencySection.setVisible(false);
		this.monitorSection.setVisible(false);
		this.effectSection.setVisible(false);
		this.effectivitySection.setVisible(false);
	

	}

	private void selectDependencyTab() {
		this.updateBeforeSectionSwith();
		this.propertySection.setVisible(false);
		this.dependencySection.setVisible(true);
		this.monitorSection.setVisible(false);
		this.effectSection.setVisible(false);
		this.effectivitySection.setVisible(false);

	}

	private void selectPropertyTab() {
		this.updateBeforeSectionSwith();
		this.propertySection.setVisible(true);
		this.dependencySection.setVisible(false);
		this.monitorSection.setVisible(false);
		this.effectSection.setVisible(false);
		this.effectivitySection.setVisible(false);
	

	}

	private void updateBeforeSectionSwith() {
		if (this.monitorSection.isVisible()) {
			if (this.theChefObjectBeingEdited != null) {
				this.theChefObjectBeingEdited.saveRootChefCondition();
			}
		}

	}

	public ChefType getChefObjectBeingEdited() {
		return theChefObjectBeingEdited;
	}

	public void setTheChefType(ChefType anObject) {
		this.theChefObjectBeingEdited = anObject;
		if (anObject != null) {
			prepareForChefObjectBeingEdited();
		}
	}
	
	public void setTheTaxonomyItemBeingEdited(ChefTaxonomyItem anItem) {
		setTheChefType(anItem.getTheChefType());
	}
	

	private void prepareMonitoringSection() {
		this.theMonitoringRulesEditor.setTheChefObjectBeingEdited(this.theChefObjectBeingEdited,
				this.theContextOfTheObjectBeingEdited);

	}

	private void prepareForChefObjectBeingEdited() {

		if (this.theChefObjectBeingEdited != null) {
			// Set context helper to get tp childs and parents
			this.theContextOfTheObjectBeingEdited.setTheChefType(theChefObjectBeingEdited);

			this.chefTaxNameLine.setValue(theChefObjectBeingEdited.getName());
			this.chefTaxDescriptionLine.setValue(theChefObjectBeingEdited.getDescription());

			this.chefTaxType.setValue(theChefObjectBeingEdited.getTypeName(t_language.NL));
			// this.chefImpactCB.setValue(theGoal.get(t_language.NL));
			if (theChefObjectBeingEdited.getType().equals(t_chefType.GOAL)) {
				chefTaxGoalType.setVisible(true);
				chefTaxGoalType.setValue(theChefObjectBeingEdited.getGoalTypeAsString(t_language.NL));
			} else {
				chefTaxGoalType.setVisible(false);
			}
			if (theChefObjectBeingEdited.hasSourceReference()) {
				this.chefTaxOpenTheSourceButton.setVisible(true);
			} else {
				this.chefTaxOpenTheSourceButton.setVisible(false);
			}

			this.chefTaxTheme.setValue(theChefObjectBeingEdited.getThemeAsString());

			chefTaxNameLine.setAutofocus(true);
			chefTaxNameLine.focus();
			this.prepareHeaders();
			this.setContextGrids();
			this.prepareStateEditor();
		}

	}

	private void setContextGrids() {
		this.setHigherContextGrid();
		this.setSubGoalsGrid();
		this.setRisksGrid();
		this.setOpportunityGrid();
		this.setFactsGrid();
		this.setDataGrid();
		this.setDataSourceGrid();
		this.setNormGrid();

		this.setRulesGrid();
		this.setUnknownGrid();
		this.setMeasurementGrid();
		this.setEventGrid();
	}

	private void setNormGrid() {
		this.dependencyNormGrid.setItems(theContextOfTheObjectBeingEdited.getChildNormObjects());
		if (theContextOfTheObjectBeingEdited.getChildNormObjects().size() > 0) {
			this.dependencyNormGrid.setVisible(true);
		} else {
			this.dependencyNormGrid.setVisible(false);
		}

	}

	private void setDataGrid() {
		this.dependencyDataGrid.setItems(theContextOfTheObjectBeingEdited.getChildDataObjects());
		if (theContextOfTheObjectBeingEdited.getChildDataObjects().size() > 0) {
			this.dependencyDataGrid.setVisible(true);
		} else {
			this.dependencyDataGrid.setVisible(false);
		}

	}

	private void setDataSourceGrid() {
		this.dependencyDataSourceGrid.setItems(theContextOfTheObjectBeingEdited.getChildDataSourceObjects());
		if (theContextOfTheObjectBeingEdited.getChildDataSourceObjects().size() > 0) {
			this.dependencyDataSourceGrid.setVisible(true);
		} else {
			this.dependencyDataSourceGrid.setVisible(false);
		}

	}

	private void setFactsGrid() {
		this.dependencyFactsGrid.setItems(theContextOfTheObjectBeingEdited.getChildFactObjects());
		if (theContextOfTheObjectBeingEdited.getChildFactObjects().size() > 0) {
			this.dependencyFactsGrid.setVisible(true);
		} else {
			this.dependencyFactsGrid.setVisible(false);
		}

	}

	private void setRulesGrid() {
		this.dependencyRuleGrid.setItems(theContextOfTheObjectBeingEdited.getChildRulesObjects());
		if (theContextOfTheObjectBeingEdited.getChildRulesObjects().size() > 0) {
			this.dependencyRuleGrid.setVisible(true);
		} else {
			this.dependencyRuleGrid.setVisible(false);
		}

	}

	private void setUnknownGrid() {
		this.dependencyUnkownGrid.setItems(theContextOfTheObjectBeingEdited.getChildUnknownObjects());
		if (theContextOfTheObjectBeingEdited.getChildUnknownObjects().size() > 0) {
			this.dependencyUnkownGrid.setVisible(true);
		} else {
			this.dependencyUnkownGrid.setVisible(false);
		}

	}

	private void setEventGrid() {
		this.dependencyEventGrid.setItems(theContextOfTheObjectBeingEdited.getChildEventObjects());
		if (theContextOfTheObjectBeingEdited.getChildEventObjects().size() > 0) {
			this.dependencyEventGrid.setVisible(true);
		} else {
			this.dependencyEventGrid.setVisible(false);
		}

	}

	private void setMeasurementGrid() {

		this.dependencyMeasurementGrid.setItems(theContextOfTheObjectBeingEdited.getChildMeasurementObjects());
		if (theContextOfTheObjectBeingEdited.getChildMeasurementObjects().size() > 0) {
			this.dependencyMeasurementGrid.setVisible(true);
		} else {
			this.dependencyMeasurementGrid.setVisible(false);
		}

	}

	private void setOpportunityGrid() {

		this.dependencyOpportunityGrid.setItems(theContextOfTheObjectBeingEdited.getChildOpportunityObjects());
		if (theContextOfTheObjectBeingEdited.getChildOpportunityObjects().size() > 0) {
			this.dependencyOpportunityGrid.setVisible(true);
		} else {
			this.dependencyOpportunityGrid.setVisible(false);
		}

	}

	private void setRisksGrid() {

		this.dependencyRisksGrid.setItems(theContextOfTheObjectBeingEdited.getChildRiskObjects());
		if (theContextOfTheObjectBeingEdited.getChildRiskObjects().size() > 0) {
			this.dependencyRisksGrid.setVisible(true);
		} else {
			this.dependencyRisksGrid.setVisible(false);
		}

	}

	private void setSubGoalsGrid() {

		this.dependencySubGoalGrid.setItems(theContextOfTheObjectBeingEdited.getChildSubGoalObjects());
		if (theContextOfTheObjectBeingEdited.getChildSubGoalObjects().size() > 0) {
			this.dependencySubGoalGrid.setVisible(true);
		} else {
			this.dependencySubGoalGrid.setVisible(false);
		}

	}

	private void setHigherContextGrid() {

		if (this.theContextOfTheObjectBeingEdited.getTheDirectParentChefObjects() != null) {
			this.dependencyParentGrid.setItems(theContextOfTheObjectBeingEdited.getTheDirectParentChefObjects());
			if (theContextOfTheObjectBeingEdited.getTheDirectParentChefObjects().size() > 0) {
				this.dependencyParentGrid.setVisible(true);
			} else {
				this.dependencyParentGrid.setVisible(false);
			}

		} else {
			this.dependencyParentGrid.setVisible(false);
		}

	}

	private void prepareHeaders() {
		if (this.theChefObjectBeingEdited.getType().equals(t_chefType.GOAL)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is onderliggend doel voor:");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.RISK)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is risico voor:");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.OPPORTUNITY)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is kans voor:");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.FACT)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is onderliggend feit voor:");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.DATA)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is gegeven gebruikt voor:");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.NORM)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is norm toegepast voor:");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.EVENT)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is gebeurtenis die optreed bij:");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.DATASOURCE)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is gegevensbron voor");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.RULE)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is beleidsregel voor");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.MEASUREMENT)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is (potentiele)  maatregel voor");
		} else if (this.theChefObjectBeingEdited.getType().equals(t_chefType.UNKNOWN)) {
			dependencyParentGrid.getColumns().get(0).setHeader("Is beheersingobject voor");
		}
	}

	public void prepare() {
		loadThemesForDomain();

	}

	private void loadThemesForDomain() {
		this.themes = WEB_Session.getInstance().getTheDomain().getThemes();
		this.chefTaxTheme.setItems(themes);
		if (this.theChefObjectBeingEdited != null) {
			this.chefTaxTheme.setValue(theChefObjectBeingEdited.getThemeAsString());
		}
	}


	private void createMonitoringSection() {
		this.monitorSection = new VerticalLayout();
		this.monitorSection.setWidthFull();
		this.monitorSection.getStyle().set("overflow", "auto");
		this.monitorSection.setHeight("990px");
		this.monitorSection.setSpacing(true);

		theMonitoringRulesEditor = new ChefMonitoringRulesEditor();
		monitorSection.add(theMonitoringRulesEditor);

	}

	private void prepareStateEditor() {
		theMonitoringRulesEditor.setTheChefObjectBeingEdited(this.theChefObjectBeingEdited,
				this.theContextOfTheObjectBeingEdited);

	}
}
