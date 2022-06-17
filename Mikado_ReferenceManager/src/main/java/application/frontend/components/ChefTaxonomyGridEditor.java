package application.frontend.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dnd.GridDropEvent;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.GoalGridService;
import application.frontend.PerspectiveGoalGridService;
import application.frontend.WEB_ReferenceTab;
import application.frontend.WEB_Session;
import planspace.chefModelTypes.ChefRootReference;
import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefTreeContext;
import planspace.chefModelTypes.ChefType;
import planspace.compliance.SourceReference;
import planspace.compliance.SourceReferenceData;
import planspace.export.CSVExportFile;
import planspace.export.JsonExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.jsonExport.JsonPolicyItems;
import planspace.jsonExport.JsonPolicyTreeItems;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_itemTypeDropped;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_sourceType;
import planspace.utils.PlanSpaceLogger;

public class ChefTaxonomyGridEditor extends VerticalLayout {
	private enum t_taxViewMode {
		FULL, FOCUSED
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TreeGrid<ChefTaxonomyItem> taxGrid;
	private List<ChefRootReference> theChefRootReferences;
	private List<ChefTaxonomyItem> theChefTaxonomyRootItems;

	private List<ChefTaxonomyItem> breadCrums;
	private VerticalLayout mainPropertySection;
	private TextField chefTaxNameLine;
	private TextArea chefTaxDescriptionLine;
	private ComboBox<String> chefTaxType;
	private ComboBox<String> chefTaxTheme;
	private TextField chefTaxOtherThemeLine;
	private Button chefTaxOpenTheSourceButton;
	private ChefTaxonomyItem currentEditChefItem;
	private ComboBox<String> chefTaxGoalType;
	private ComboBox<String> chefImpactCB;
	private List<String> themes;
	private t_taxViewMode currentTaxMode;

	private ChefTypeFilterControl filterChefTypeCB;
	private WEB_ReferenceTab myParentReferenceManager;

	private List<Image> levelButtons;
	private int currentLevel;

	public ChefTaxonomyGridEditor(WEB_ReferenceTab myWEB_ReferenceTab) {
		super();
		HorizontalLayout gridAndButtons;
		HorizontalLayout butLine;

		this.myParentReferenceManager = myWEB_ReferenceTab;
		theChefTaxonomyRootItems = new ArrayList<ChefTaxonomyItem>();
		theChefRootReferences = new ArrayList<ChefRootReference>();
		breadCrums = new ArrayList<ChefTaxonomyItem>();

		setDefaultStyle();
		createTaxGrid();
		createMainItemPropertyWindow();
		butLine = taxItemActionButtons();
		// gridAndButtons = new HorizontalLayout();
		// gridAndButtons.add( butLine);
		this.add(this.taxGrid, this.mainPropertySection, butLine);
		loadRootReferences();
		currentTaxMode = t_taxViewMode.FULL;

	}

	private HorizontalLayout taxItemActionButtons() {
		HorizontalLayout butLine;
		Button addButton;
		Button propButton;
		Button filterSourceReferences;
		Button exportButton;
		Button dataContextButton;
		Button backButton;
		Button exportJsonButton;

		butLine = new HorizontalLayout();
		backButton = createBackButton();
		addButton = this.createAddTaxItem();

		propButton = createPropertyWindowBut();
		exportButton = createSaveButton();
		filterSourceReferences = createFilterSourcesItem();
		dataContextButton = createDataContextButton();
		exportJsonButton = createExportJsonButton();
		filterChefTypeCB = new ChefTypeFilterControl(this);
		butLine.add(backButton, filterChefTypeCB, addButton, propButton, filterSourceReferences, exportButton,
				dataContextButton, exportJsonButton);
		this.createLevelButtons(butLine);
		return butLine;

	}

	private void prepare() {
		loadThemesForDomain();

	}

	private Button createExportJsonButton() {
		Image icon;
		String r = "35px";

		icon = new Image("/exportjson.png", "json export");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Exporteer beleidmodel naar Json");

		// Set action
		addBut.addClickListener(e -> {
			this.generateExportToJson();
			// this.restoreByName();
		});

		return addBut;
	}

	private Button createDataContextButton() {
		Image icon;
		String r = "35px";

		icon = new Image("/exportdata.png", "data export");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Exporteer data gebruik in context");

		// Set action
		addBut.addClickListener(e -> {
			this.generateDataContext();
			// this.restoreByName();
		});

		return addBut;
	}

	private Button createFilterSourcesItem() {

		Image icon;
		String r = "35px";

		icon = new Image("/filter.png", "filter");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Verberg alle reeds gebruikt annotaties");

		// Set action
		addBut.addClickListener(e -> {
			this.filterUsedSourceReferences();
			// this.restoreByName();
		});

		return addBut;
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
			this.mainpropertyhandling(this.currentEditChefItem);

		});

		return addBut;
	}

	private Button createBackButton() {
		Image icon;
		String r = "35px";

		icon = new Image("/backdetail.png", "terug");
		icon.setHeight(r);
		icon.setWidth(r);

		Button backbut;

		backbut = new Button("", icon);
		backbut.getElement().setAttribute("title", "Terug naar bredere context");

		// Set action
		backbut.addClickListener(e -> {
			this.detailBack();

		});

		return backbut;
	}

	private void detailBack() {
		if (this.breadCrums.size() > 0) {
			this.breadCrums.remove(this.breadCrums.size() - 1);
			if (this.breadCrums.size() > 0) {
				this.currentEditChefItem = this.breadCrums.get(this.breadCrums.size() - 1);
				this.RefreshAndOpenTaxGrid(currentEditChefItem);
			} else {
				this.setTaxViewModeFull();
			}
		}
		if (this.currentLevel > 0) {
			this.currentLevel--;
			this.hideLevelButtonsAbove(currentLevel);
		}

	}

	private Button createAddTaxItem() {

		Image icon;
		String r = "35px";

		icon = new Image("/add.png", "Toevoegen");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Toevoegen onderliggend aspect");

		// Set action
		addBut.addClickListener(e -> {
			this.addChefTaxonomyItemManually();

		});

		return addBut;
	}

	private Button createSaveButton() {

		Image icon;
		String r = "35px";

		icon = new Image("/export.png", "export");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Export taxonomie");

		// Set action
		addBut.addClickListener(e -> {
			this.exportToCSV();

		});

		return addBut;
	}

	private Button createPropertyWindowBut() {
		Image icon;
		String r = "35px";

		icon = new Image("/load.png", "herladen definities");
		icon.setHeight(r);
		icon.setWidth(r);

		Button propBut;

		propBut = new Button("", icon);
		propBut.getElement().setAttribute("title", "Herladen definities");

		// Set action
		propBut.addClickListener(e -> {
			this.openChefTaxonomyProperies();

		});

		return propBut;
	}

	private void openChefTaxonomyProperies() {
		// System.out.println("open properties now used for loading");
		this.createCompleteChefTaxonomyForDomain();
	}

	private void addChefTaxonomyItemManually() {
		ChefTaxonomyItem theNewItem;
		System.out.println("add manual");
		if (this.taxGrid.getSelectedItems() != null) {
			if (this.taxGrid.getSelectedItems().size() > 0) {
				taxGrid.getSelectedItems().forEach(aParent -> {
					ChefTaxonomyItem theNewChildItem;
					theNewChildItem = this.addChefTaxonomyItemManuallyAtRoot(aParent);
					mainpropertyhandling(theNewChildItem);
				});

			} else {
				theNewItem = this.addChefTaxonomyItemManuallyAtRoot();
				mainpropertyhandling(theNewItem);
			}

		} else {
			theNewItem = this.addChefTaxonomyItemManuallyAtRoot();
			mainpropertyhandling(theNewItem);
		}
	}

	private ChefTaxonomyItem addChefTaxonomyItemManuallyAtRoot(ChefTaxonomyItem aParent) {
		ChefTaxonomyItem aNewManualTaxItem;
		ChefType theChefObject;

		SourceReferenceData theManualSoureReferenceData;

		aNewManualTaxItem = new ChefTaxonomyItem();

		theChefObject = new ChefType();
		theChefObject.setDomainCode(WEB_Session.getInstance().getTheDomain().getDomainCode());

		theManualSoureReferenceData = new SourceReferenceData();
		theManualSoureReferenceData.setSourceType(t_sourceType.MANUAL);

		theChefObject.addSourceReference(theManualSoureReferenceData);
		aNewManualTaxItem.setTheChefType(theChefObject);

		aParent.addAChild(aNewManualTaxItem);
		aParent.storeYourself();
		aNewManualTaxItem.storeYourself();
		this.RefreshAndOpenTaxGrid(aNewManualTaxItem);
		return aNewManualTaxItem;
	}

	private ChefTaxonomyItem addChefTaxonomyItemManuallyAtRoot() {
		ChefTaxonomyItem aNewManualTaxItem;
		ChefType theChefObject;

		SourceReferenceData theManualSoureReferenceData;

		aNewManualTaxItem = new ChefTaxonomyItem();
		theChefObject = new ChefType();
		theChefObject.setDomainCode(WEB_Session.getInstance().getTheDomain().getDomainCode());
		theManualSoureReferenceData = new SourceReferenceData();
		theManualSoureReferenceData.setSourceType(t_sourceType.MANUAL);

		aNewManualTaxItem.setTheChefType(theChefObject);

		theChefObject.addSourceReference(theManualSoureReferenceData);
		addChefTaxonomyRoot(aNewManualTaxItem);

		aNewManualTaxItem.storeYourself();

		this.RefreshAndOpenTaxGrid(aNewManualTaxItem);
		return aNewManualTaxItem;

	}

	private void createMainItemPropertyWindow() {
		List<String> types;
		List<String> themes;
		HorizontalLayout horClassGroup;

		this.mainPropertySection = new VerticalLayout();
		this.mainPropertySection.setWidthFull();
		this.mainPropertySection.getStyle().set("overflow", "auto");
		this.mainPropertySection.getStyle().set("border", "1px solid green");
		this.mainPropertySection.setWidthFull();
		this.mainPropertySection.setSpacing(true);
		// this.setHeight("600px");
		this.mainPropertySection.setHeight("540px");
		this.mainPropertySection.setVisible(false);

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

		this.chefTaxTheme.setWidth("50%");
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

		this.chefImpactCB = new ComboBox<String>();
		this.chefImpactCB.setLabel("Impact");
		ArrayList<String> impacts = new ArrayList<String>();
		impacts.add("Neutraal");
		impacts.add("Laag");
		impacts.add("Midden");
		impacts.add("Hoog");
		impacts.add("Onacceptabel");
		impacts.add("Onbekend");

		chefImpactCB.setVisible(true);
		this.chefImpactCB.setItems(impacts);
		this.chefImpactCB.setWidth("50%");
		this.chefImpactCB.addValueChangeListener(event -> {
			this.changeImpactCurrentItem(event.getValue());
		});

		horClassGroup = new HorizontalLayout();
		horClassGroup.add(chefTaxType, chefTaxGoalType, chefTaxTheme, chefImpactCB);

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
			if (this.currentEditChefItem != null) {
				this.currentEditChefItem.openReferencedDocument();
			}
		});

		Button closeBut = this.createClosePropertyWindowBut();
		butlineUnder.add(chefTaxOpenTheSourceButton, closeBut);
		this.mainPropertySection.add(chefTaxNameLine, horClassGroup, chefTaxOtherThemeLine, chefTaxDescriptionLine,
				butlineUnder);

	}

	private void changeDescriptionOfCurrentItem(String value) {
		if (this.currentEditChefItem != null) {
			this.currentEditChefItem.setDescription(value);
			currentEditChefItem.storeYourself();
		}

	}

	private void changeTaxNameOfCurrentItem(String value) {
		if (this.currentEditChefItem != null) {
			this.currentEditChefItem.setInformalName(value);
			currentEditChefItem.storeYourself();
		}

	}

	private void changeGoalTypeOfCurrentItem(String value) {
		if (this.currentEditChefItem != null) {
			this.currentEditChefItem.setGoalTypeAsString(t_language.NL, value);
			currentEditChefItem.storeYourself();
		}

	}

	private void changeImpactCurrentItem(String value) {
		if (this.currentEditChefItem != null) {
			this.currentEditChefItem.setImpactAsString(t_language.NL, value);
			currentEditChefItem.storeYourself();
		}

	}

	private void changeThemeOfCurrentItem(String value) {
		if (this.currentEditChefItem != null) {
			this.currentEditChefItem.setThemeAsString(value);
			currentEditChefItem.storeYourself();
		}

	}

	private void changeTypeOfCurrentItem(String value) {
		if (this.currentEditChefItem != null) {
			this.currentEditChefItem.setTypeByString(t_language.NL, value);
			currentEditChefItem.storeYourself();
		}

	}

	private void renameItem() {
		ChefTaxonomyItem theItem;

		if (this.taxGrid.getSelectedItems() != null) {
			if (this.taxGrid.getSelectedItems().size() > 0) {
				taxGrid.getSelectedItems().forEach(anItem -> {
					anItem.editYourName();
				});

			}
		}
	}

	/***
	 * Contents
	 */

	public void clearChefTaxonomy() {
		this.theChefTaxonomyRootItems.clear();
	}

	private void addChefTaxonomyRoot(ChefTaxonomyItem aNewRoot) {
		if (!this.theChefTaxonomyRootItems.contains(aNewRoot)) {
			this.theChefTaxonomyRootItems.add(aNewRoot);
		}
	}

	private void deleteChefTaxonomyRoot(ChefTaxonomyItem aDelRoot) {
		if (this.theChefTaxonomyRootItems.contains(aDelRoot)) {
			this.theChefTaxonomyRootItems.remove(aDelRoot);
		}

	}

	public List<ChefTaxonomyItem> getChefTaxonomyRootItems() {
		return this.theChefTaxonomyRootItems;
	}

	public void ReloadTaxGrid() {
		WEB_Session.getInstance().clearChefTaxonomyCache();
		this.createCompleteChefTaxonomyForDomain();
		GoalGridService chefService = new GoalGridService(this.theChefTaxonomyRootItems);
		this.taxGrid.setItems(chefService.getRootNodes(), chefService::getChildNodes);

	}

	/*
	 * public void RefreshTaxGrid() { GoalGridService chefService = new
	 * GoalGridService(this.theChefTaxonomyRootItems);
	 * this.taxGrid.setItems(chefService.getRootNodes(),
	 * chefService::getChildNodes);
	 * 
	 * }
	 */

	public void RefreshTaxGrid() {
		List<t_chefType> perspectives;
		List<ChefTaxonomyItem> theRootOfFocus;
		if (this.currentTaxMode.equals(t_taxViewMode.FULL)) {
			theRootOfFocus = this.theChefTaxonomyRootItems;
		} else {
			if (currentEditChefItem != null) {
				theRootOfFocus = new ArrayList<ChefTaxonomyItem>();
				theRootOfFocus.add(this.currentEditChefItem);
			} else {
				theRootOfFocus = this.theChefTaxonomyRootItems;
			}
		}

		perspectives = this.filterChefTypeCB.getTypesAllowedByFilter();
		PerspectiveGoalGridService chefService = new PerspectiveGoalGridService(theRootOfFocus, perspectives);
		this.taxGrid.setItems(chefService.getRootNodes(), chefService::getChildNodes);

	}

	public void RefreshAndOpenTaxGrid(ChefTaxonomyItem theItemToFocus) {
		List<ChefTaxonomyItem> myPath;
		this.RefreshTaxGrid();

		if (theItemToFocus != null) {

			myPath = this.restoreCollapsedView();
			taxGrid.expand(myPath);
			myPath = getPathToItem(theItemToFocus);
			taxGrid.expand(myPath);
		}

	}

	private List<ChefTaxonomyItem> getItemsAsOrderedList() {
		List<ChefTaxonomyItem> treeAsList;
		int i;

		treeAsList = new ArrayList<ChefTaxonomyItem>();
		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			this.theChefTaxonomyRootItems.get(i).addItemsToOrderedList(treeAsList);
		}
		return treeAsList;
	}

	private List<ChefTaxonomyItem> getPathToItem(ChefTaxonomyItem theItemToFocus) {
		List<ChefTaxonomyItem> path;

		int i;
		boolean rootOrUnder;

		path = new ArrayList<ChefTaxonomyItem>();
		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			rootOrUnder = this.theChefTaxonomyRootItems.get(i).isItemPartOfTree(theItemToFocus, path);
			if (rootOrUnder) {
				break;
			}
		}
		return path;
	}

	private List<ChefTaxonomyItem> restoreCollapsedView() {
		List<ChefTaxonomyItem> path;

		int i;

		path = new ArrayList<ChefTaxonomyItem>();
		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			this.theChefTaxonomyRootItems.get(i).restoreCollapsedView(path);

		}
		return path;
	}

	private void setDefaultStyle() {
		this.setWidthFull();
		this.getStyle().set("overflow", "auto");
		// this.getStyle().set("border", "1px solid green");
		this.setWidthFull();
		this.setSpacing(true);
		this.setHeight("990px");

	}

	private t_itemTypeDropped checkTypeOfItemDropped() {
		t_itemTypeDropped recognised;

		recognised = t_itemTypeDropped.UNKNOWN;
		if (DragSource.configure(taxGrid).getDragData() != null) {
			if (DragSource.configure(taxGrid).getDragData().getClass().getName()
					.equals(SourceReference.class.getName())) {
				recognised = t_itemTypeDropped.SOURCE_REFERENCE;
			}

			if (DragSource.configure(taxGrid).getDragData().getClass().getName()
					.equals("java.util.Collections$UnmodifiableRandomAccessList")) {
				recognised = t_itemTypeDropped.TAXONOMY_ITEM;
			}
		}
		return recognised;
	}

	private void createTaxGrid() {
		String r = "30px";
		HorizontalLayout option;
		Label aText;
		Image img;

		this.taxGrid = new TreeGrid<ChefTaxonomyItem>();

		taxGrid.addComponentHierarchyColumn(ChefTaxonomyItem::getAvatar).setHeader("Beleid taxonomie");
		taxGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
		taxGrid.setRowsDraggable(true);
		// taxGrid.setSizeFull();
		taxGrid.setMinWidth("800px");
		taxGrid.setHeight("880px");
		taxGrid.setVerticalScrollingEnabled(true);

		// Event start dragging
		taxGrid.addDragStartListener(event -> {
			List<ChefTaxonomyItem> taxItemsBeingDragged;

			taxItemsBeingDragged = new ArrayList<ChefTaxonomyItem>();
			event.getDraggedItems().forEach(anItem -> {
				taxItemsBeingDragged.add((ChefTaxonomyItem) anItem);
			});
			this.myParentReferenceManager.notifyTaxonomyItemsDragged(taxItemsBeingDragged);
			// DragSource.configure(taxGrid).setDragData(event.getDraggedItems());
			// event.getSource().getUI().get().getInternals().setActiveDragSourceComponent(event.getSource());

		});

		GridContextMenu myContextMenu = taxGrid.addContextMenu();

		option = new HorizontalLayout();
		img = new Image("/focus.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Focus");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			this.editDetailsTreeItem();
		});

		option = new HorizontalLayout();
		img = new Image("/add.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Toevoegen");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			addChefTaxonomyItemManually();

		});

		option = new HorizontalLayout();
		img = new Image("/rename.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Hernoemen");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			renameItem();

		});

		option = new HorizontalLayout();
		img = new Image("/edit.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Eigenschappen");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			this.editTreeItem();
		});

		option = new HorizontalLayout();
		img = new Image("/backdetail.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Verplaats naar hoger niveau");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			this.treeItemLevelUp();
		});

		option = new HorizontalLayout();
		img = new Image("/deletenodeonly.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Verwijder met behoud deelaspecten");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			delSelectedItem(false);
		});

		option = new HorizontalLayout();
		img = new Image("/deletenoderecursive.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Verwijder inclusief deelaspecten");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			delSelectedItem(true);
		});

		option = new HorizontalLayout();
		img = new Image("/opensourcedoc.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Bron...");
		option.add(img, aText);

		myContextMenu.addItem(option, event -> {
			this.openPolicy();
		});

		// Event start dragging

		taxGrid.setDetailsVisibleOnClick(false);

		/*
		 * taxGrid.addItemClickListener(event -> { // Right hand click
		 * System.out.println("button " + event.getButton() + event.getClickCount()); if
		 * (event.getClickCount() == 1) { this.mainpropertyhandling(event.getItem()); }
		 * 
		 * });
		 */

		// Event end dragg
		taxGrid.addDropListener(event -> {
			Optional<ChefTaxonomyItem> myOptionalTargetTreeItem;
			ChefTaxonomyItem myTargetTreeItem;
			t_itemTypeDropped itemTypeDropped;

			// Wat wordt er gedropped
			// itemTypeDropped = checkTypeOfItemDropped();
			itemTypeDropped = this.myParentReferenceManager.getCurrentItemTypeDropped();
			System.out.println("Dropped " + itemTypeDropped);
			// Is er een target
			myTargetTreeItem = null;

			myOptionalTargetTreeItem = event.getDropTargetItem();
			if (myOptionalTargetTreeItem.isPresent()) {
				myTargetTreeItem = myOptionalTargetTreeItem.get();
			}

			// Handle dropping
			if (itemTypeDropped.equals(t_itemTypeDropped.TAXONOMY_ITEM)) {
				handleDroppingTreeItems(event, myTargetTreeItem);

			}

			if (itemTypeDropped.equals(t_itemTypeDropped.SOURCE_REFERENCE)) {
				handleDroppingSourceReference(event, myTargetTreeItem);
			}

			// update Tree as List
			// this.updateCompleteTreeAsList();
		});

		taxGrid.addCollapseListener(listener -> {
			listener.getItems().forEach(item -> item.notifyCollapse(true));
		});

		taxGrid.addExpandListener(listener -> {
			listener.getItems().forEach(item -> item.notifyCollapse(false));
		});

	}

	private void editDetailsTreeItem() {

		if (this.currentLevel < 9) {
			if (this.taxGrid.getSelectedItems().size() == 1) {

				this.taxGrid.getSelectedItems().forEach(anItem -> {
					// WEB_Session.getInstance().notifyComplianceEditor("Edit_Details",
					// anItem.getTheChefObject());
					this.currentTaxMode = t_taxViewMode.FOCUSED;
					this.currentEditChefItem = anItem;
					this.breadCrums.add(anItem);

					this.RefreshTaxGrid();
				});
				this.currentLevel++;
				this.levelButtons.get(currentLevel - 1).getElement().setAttribute("title",
						currentEditChefItem.getInformalName());

				this.hideLevelButtonsAbove(currentLevel);
			}
		} else {
			this.notifyUser("Maximaal aantal levels van detail bereikt");
		}

	}

	private void delSelectedItem(boolean recursive) {
		// TODO Auto-generated method stub

		this.taxGrid.getSelectedItems().forEach(anItem -> {
			notifyUser("Verwijderd " + anItem.getTreeName());
			this.deleteTaxNode(anItem, recursive);

		});

	}

	private void notifyUser(String aNote) {

		Notification notification = new Notification();
		notification.show(aNote, 1500, Notification.Position.BOTTOM_CENTER);

	}

	private void openPolicy() {

		this.taxGrid.getSelectedItems().forEach(anItem -> {
			anItem.openReferencedDocument();
		});

	}

	private void editTreeItem() {

		if (this.taxGrid.getSelectedItems().size() == 1) {

			this.taxGrid.getSelectedItems().forEach(anItem -> {
				// this.mainpropertyhandling(anItem);
				if (this.myParentReferenceManager != null) {
					myParentReferenceManager.requestToEditChefType(anItem);
				}
			});
		}
	}

	private void treeItemLevelUp() {

		this.taxGrid.getSelectedItems().forEach(anItem -> {
			this.moveParentUp(anItem);
		});

	}

	private void mainpropertyhandling(ChefTaxonomyItem theItem) {

		if (!this.mainPropertySection.isVisible()) {
			// Open if closed

			this.taxGrid.setHeight("450px");
			this.setupMainPropertiesForChefTaxonomyItem(theItem);

			this.mainPropertySection.setVisible(true);

		} else {
			// Close is opened
			this.mainPropertySection.setVisible(false);
			this.saveMainPropertiesForChefTaxonomyItem();
			this.taxGrid.setHeight("990px");

		}

	}

	private void loadThemesForDomain() {
		this.themes = WEB_Session.getInstance().getTheDomain().getThemes();
		this.chefTaxTheme.setItems(themes);
	}

	private void mainPropertyHandlingAfterDrop(ChefTaxonomyItem theItem) {

		if (!this.mainPropertySection.isVisible()) {
			this.taxGrid.setHeight("450px");
			this.mainPropertySection.setVisible(true);

		}
		// Open if closed

		this.setupMainPropertiesForChefTaxonomyItem(theItem);

	}

	private void saveMainPropertiesForChefTaxonomyItem() {
		if (this.currentEditChefItem != null) {
			RefreshAndOpenTaxGrid(currentEditChefItem);
		}

	}

	private void setupMainPropertiesForChefTaxonomyItem(ChefTaxonomyItem theItem) {

		this.currentEditChefItem = theItem;
		if (this.currentEditChefItem != null) {
			this.chefTaxNameLine.setValue(currentEditChefItem.getInformalName());
			this.chefTaxDescriptionLine.setValue(currentEditChefItem.getTreeDescription());
			this.chefTaxTheme.setValue(currentEditChefItem.getThemeAsString());
			this.chefTaxType.setValue(currentEditChefItem.getTypeNameAsString(t_language.NL));
			this.chefImpactCB.setValue(currentEditChefItem.getImpactAsString(t_language.NL));
			if (currentEditChefItem.getMyChefType().equals(t_chefType.GOAL)) {
				chefTaxGoalType.setVisible(true);
				chefTaxGoalType.setValue(currentEditChefItem.getGoalTypeAsString(t_language.NL));
			} else {
				chefTaxGoalType.setVisible(false);
			}
			if (currentEditChefItem.hasSourceReference()) {
				this.chefTaxOpenTheSourceButton.setVisible(true);
			} else {
				this.chefTaxOpenTheSourceButton.setVisible(false);
			}
			chefTaxNameLine.setAutofocus(true);
			chefTaxNameLine.focus();
		}

	}

	private void handleDroppingTreeItems(GridDropEvent<ChefTaxonomyItem> event, ChefTaxonomyItem myTargetTreeItem) {

		List<ChefTaxonomyItem> droppedTreeItems;
		ChefTaxonomyItem aDroppedItem;
		int i;

		droppedTreeItems = this.myParentReferenceManager.getCurrentTaxItemsDropped();

		if (droppedTreeItems != null) {

			System.out.println("\n\n===========================");
			System.out.println("DROPPING TREE ITEMS");
			System.out.println("===========================");
			System.out.println("DROP Location " + event.getDropLocation());

			// Get the SourceReference dropped
			for (i = 0; i < droppedTreeItems.size(); i++) {
				aDroppedItem = droppedTreeItems.get(i);
				// waar
				if (event.getDropLocation().equals(GridDropLocation.ON_TOP)) {
					dropTreeItemOnTop(aDroppedItem, myTargetTreeItem);

				} else if (event.getDropLocation().equals(GridDropLocation.ABOVE)) {
					dropTreeItemAbove(aDroppedItem, myTargetTreeItem, event);
				} else if (event.getDropLocation().equals(GridDropLocation.BELOW)) {
					dropTreeItemBelow(aDroppedItem, myTargetTreeItem, event);

				} else {
					dropTreeItemAsNewRootItem(aDroppedItem, myTargetTreeItem, event);
				}
				RefreshAndOpenTaxGrid(aDroppedItem);
				// mainPropertyHandlingAfterDrop(aDroppedItem);
				System.out.println("===========================\n\n");
			}
		}
		this.myParentReferenceManager.notifyDropHandled();
	}

	private void handleDroppingSourceReference(GridDropEvent<ChefTaxonomyItem> event,
			ChefTaxonomyItem myTargetTreeItem) {

		ChefTaxonomyItem newItem;
		SourceReference theSourceReferenceDropped;

		System.out.println("\n\n===========================");
		System.out.println("DROPPING SOURCEREFERENCE");
		System.out.println("===========================");
		System.out.println("DROP Location " + event.getDropLocation());

		// Get the SourceReference dropped
		// theSourceReferenceDropped = (SourceReference)
		// DragSource.configure(taxGrid).getDragData();
		theSourceReferenceDropped = this.myParentReferenceManager.getCurrentSourceReferenceDropped();
		System.out.println("Dropping sourcereference" + theSourceReferenceDropped.getInformalName());
		theSourceReferenceDropped.setUsed(true);
		// waar
		if (event.getDropLocation().equals(GridDropLocation.ON_TOP)) {
			newItem = dropSourceReferenceOnTop(theSourceReferenceDropped, myTargetTreeItem);

		} else if (event.getDropLocation().equals(GridDropLocation.ABOVE)) {
			newItem = dropSourceReferencAbove(theSourceReferenceDropped, myTargetTreeItem, event);
		} else if (event.getDropLocation().equals(GridDropLocation.BELOW)) {
			newItem = dropSourceReferencBelow(theSourceReferenceDropped, myTargetTreeItem, event);

		} else {
			newItem = dropSourceReferencAsNewRootItem(theSourceReferenceDropped, myTargetTreeItem, event);
		}

		RefreshAndOpenTaxGrid(newItem);
		WEB_Session.getInstance().notifyPolicyReferenceEditor("SourceReferenceUsed");

		// mainPropertyHandlingAfterDrop(newItem);
		this.myParentReferenceManager.notifyDropHandled();
		System.out.println("===========================\n\n");
	}

	/****
	 * TREE ITEM DROPPING
	 */
	private void dropTreeItemOnTop(ChefTaxonomyItem theDraggedItem, ChefTaxonomyItem myTargetTreeItem) {
		ChefTaxonomyItem oldParent;

		if (myTargetTreeItem != null) {
			// no dropping on yourself allowed
			if (!myTargetTreeItem.get_id().equals(theDraggedItem.get_id())) {
				// no dropping on child of yourself
				if (!theDraggedItem.hasItemPartOfTree(myTargetTreeItem)) {

					System.out.println("Transfering as child on " + myTargetTreeItem.getTreeName());
					oldParent = theDraggedItem.getParent();

					if (oldParent != null) {
						oldParent.removeAChild(theDraggedItem);
						oldParent.storeYourself();
						theDraggedItem.storeYourself();
					} else {
						this.deleteChefTaxonomyRoot(theDraggedItem);
						theDraggedItem.storeYourself();
					}
					myTargetTreeItem.addAChild(theDraggedItem);
					myTargetTreeItem.storeYourself();
					theDraggedItem.storeYourself();
				}
			}

		}
	}

	// Above another
	private void dropTreeItemAbove(ChefTaxonomyItem theDraggedItem, ChefTaxonomyItem myTargetTreeItem,
			GridDropEvent<ChefTaxonomyItem> event) {
		ChefTaxonomyItem newItem, oldParent, directPredecessor;

		if (myTargetTreeItem != null) {
			// No dropping above yourself
			if (!theDraggedItem.get_id().equals(myTargetTreeItem.get_id())) {
				// No dropping above a child of yourself
				if (!theDraggedItem.hasItemPartOfTree(myTargetTreeItem)) {

					oldParent = theDraggedItem.getParent();
					if (oldParent != null) {
						oldParent.removeAChild(theDraggedItem);
						oldParent.storeYourself();
					} else {
						this.deleteChefTaxonomyRoot(theDraggedItem);
					}

					// get the direct predecesoort
					directPredecessor = this.getDirectPredecessor(myTargetTreeItem);
					if (directPredecessor != null) {
						if (directPredecessor.itemIsExpanded() && directPredecessor.hasChildren()) {
							// add to predecerssor as child
							System.out.println(
									"Drop reference to predecessor as last item " + myTargetTreeItem.getTreeName());

							directPredecessor.addAChild(theDraggedItem);
							directPredecessor.storeYourself();
							theDraggedItem.storeYourself();
						} else {
							// add direct above
							System.out.println("Drop reference direct ABOVE " + myTargetTreeItem.getTreeName());

							this.addChefTaxonomyAbove(theDraggedItem, myTargetTreeItem);
							theDraggedItem.storeYourself();

						}
					} else {
						System.out.println("Drop reference direct ABOVE " + myTargetTreeItem.getTreeName());

						this.addChefTaxonomyAbove(theDraggedItem, myTargetTreeItem);
						theDraggedItem.storeYourself();
					}
				}

			}
		}

	}

	// Below another
	private void dropTreeItemBelow(ChefTaxonomyItem theDraggedItem, ChefTaxonomyItem myTargetTreeItem,
			GridDropEvent<ChefTaxonomyItem> event) {
		ChefTaxonomyItem oldParent;

		// No dropping below myself
		if (myTargetTreeItem != null) {
			if (!theDraggedItem.get_id().equals(myTargetTreeItem.get_id())) {
				// No dropping above a child of yourself
				if (!theDraggedItem.hasItemPartOfTree(myTargetTreeItem)) {
					// no dropping on same place
					if (!(myTargetTreeItem.itemIsExpanded() && myTargetTreeItem.hasItemAsDirectChild(theDraggedItem))) {

						oldParent = theDraggedItem.getParent();
						if (oldParent != null) {
							oldParent.removeAChild(theDraggedItem);
							oldParent.storeYourself();
						} else {
							this.deleteChefTaxonomyRoot(theDraggedItem);
						}

						System.out.println("Dropping below " + myTargetTreeItem.getTreeName());

						if (myTargetTreeItem.itemIsCollapsed()
								|| (myTargetTreeItem.itemIsExpanded() && !myTargetTreeItem.hasChildren())) {

							this.addChefTaxonomyBelow(theDraggedItem, myTargetTreeItem);
							theDraggedItem.storeYourself();
						} else {

							myTargetTreeItem.addAChildAsFirst(theDraggedItem);
							myTargetTreeItem.storeYourself();
							theDraggedItem.storeYourself();
						}
					}
				}
			}
		}
	}

	// In Root
	private void dropTreeItemAsNewRootItem(ChefTaxonomyItem theDraggedItem, ChefTaxonomyItem myTargetTreeItem,
			GridDropEvent<ChefTaxonomyItem> event) {
		ChefTaxonomyItem oldParent;

		oldParent = theDraggedItem.getParent();
		if (oldParent != null) {
			oldParent.removeAChild(theDraggedItem);
			oldParent.storeYourself();
			theDraggedItem.storeYourself();
		} else {
			this.deleteChefTaxonomyRoot(theDraggedItem);
			theDraggedItem.storeYourself();
		}

		System.out.println("Adding treeItems as root ");
		this.addChefTaxonomyRoot(theDraggedItem);
	}

	/******************************************************************************************
	 * 
	 * SOURCE REFERENCE DROPPING
	 * 
	 */

	/* Als kind onder een TaxItem */
	private ChefTaxonomyItem dropSourceReferenceOnTop(SourceReference theSourceReferenceDropped,
			ChefTaxonomyItem myTargetTreeItem) {
		ChefTaxonomyItem newItem;

		newItem = null;
		if (myTargetTreeItem != null) {
			System.out.println("Dropping on top of " + myTargetTreeItem.getTreeName());
			newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
			myTargetTreeItem.addAChild(newItem);
			myTargetTreeItem.storeYourself();
			newItem.storeYourself();
		} else {
			System.out.println("Adding source reference as root ");
			newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
			this.addChefTaxonomyRoot(newItem);
			newItem.storeYourself();

		}
		return newItem;
	}

	// Above another
	private ChefTaxonomyItem dropSourceReferencAbove(SourceReference theSourceReferenceDropped,
			ChefTaxonomyItem myTargetTreeItem, GridDropEvent<ChefTaxonomyItem> event) {
		ChefTaxonomyItem newItem, directPredecessor;
		int rowOfParent;

		newItem = null;
		if (myTargetTreeItem != null) {
			// get the direct predecesoort
			directPredecessor = this.getDirectPredecessor(myTargetTreeItem);
			if (directPredecessor != null) {
				if (directPredecessor.itemIsExpanded() && directPredecessor.hasChildren()) {
					// add to predecerssor as child
					System.out.println("Drop reference to predecessor as last item " + myTargetTreeItem.getTreeName());
					newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
					directPredecessor.addAChild(newItem);
					directPredecessor.storeYourself();
					newItem.storeYourself();
				} else {
					// add direct above
					System.out.println("Drop reference direct ABOVE " + myTargetTreeItem.getTreeName());
					newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
					this.addChefTaxonomyAbove(newItem, myTargetTreeItem);
					myTargetTreeItem.storeYourself();
					newItem.storeYourself();

				}
			} else {
				System.out.println("Drop reference direct ABOVE " + myTargetTreeItem.getTreeName());
				newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
				this.addChefTaxonomyAbove(newItem, myTargetTreeItem);
				myTargetTreeItem.storeYourself();
				newItem.storeYourself();
			}
		} else {

			System.out.println("Adding sourcereference ABOVE as root");
			newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
			this.addChefTaxonomyRoot(newItem);
			newItem.storeYourself();
		}
		return newItem;

	}

	private ChefTaxonomyItem getDirectPredecessor(ChefTaxonomyItem myTargetTreeItem) {
		ChefTaxonomyItem thePredecessor;
		int locOfTarget;

		thePredecessor = null;
		if (myTargetTreeItem.getParent() != null) {
			if (myTargetTreeItem.getParent().getMyChildren() != null) {
				locOfTarget = myTargetTreeItem.getParent().getMyChildren().indexOf(myTargetTreeItem);
				if (locOfTarget > 0) {
					thePredecessor = myTargetTreeItem.getParent().getMyChildren().get(locOfTarget - 1);
				}
			}
		}
		return thePredecessor;
	}

	// Below another
	private ChefTaxonomyItem dropSourceReferencBelow(SourceReference theSourceReferenceDropped,
			ChefTaxonomyItem myTargetTreeItem, GridDropEvent<ChefTaxonomyItem> event) {
		ChefTaxonomyItem newItem;
		int rowOfParent = 0;

		newItem = null;

		if (myTargetTreeItem != null) {

			System.out.println("Dropping below" + myTargetTreeItem.getTreeName() + ">>" + rowOfParent);

			if (myTargetTreeItem.itemIsCollapsed()
					|| (myTargetTreeItem.itemIsExpanded() && !myTargetTreeItem.hasChildren())) {

				newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
				this.addChefTaxonomyBelow(newItem, myTargetTreeItem);
				myTargetTreeItem.storeYourself();
				newItem.storeYourself();
			} else {
				newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
				myTargetTreeItem.addAChildAsFirst(newItem);
				myTargetTreeItem.storeYourself();
				newItem.storeYourself();
			}

		} else {
			System.out.println("Adding sourcereference BELOW as root >> " + rowOfParent);

			newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
			this.addChefTaxonomyRoot(newItem);
			newItem.storeYourself();
		}
		return newItem;
	}

	// In Root
	private ChefTaxonomyItem dropSourceReferencAsNewRootItem(SourceReference theSourceReferenceDropped,
			ChefTaxonomyItem myTargetTreeItem, GridDropEvent<ChefTaxonomyItem> event) {
		ChefTaxonomyItem newItem;

		System.out.println("Adding sourcereference as root ");
		newItem = createChefTaxonomytemForDraggedSourceReference(theSourceReferenceDropped);
		this.addChefTaxonomyRoot(newItem);
		newItem.storeYourself();
		return newItem;
	}

	private ChefTaxonomyItem createChefTaxonomytemForDraggedSourceReference(SourceReference theSourceRef) {
		ChefTaxonomyItem aNewRootItem;
		ChefType newType;
		String domainCode;

		// temp
		aNewRootItem = null;
		domainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
		if (theSourceRef != null) {
			aNewRootItem = new ChefTaxonomyItem();

			newType = new ChefType();
			newType.setName(theSourceRef.getInformalName());
			aNewRootItem.setInformalName(theSourceRef.getInformalName());
			newType.setDescription(theSourceRef.getMarkedContent());
			newType.setDomainCode(domainCode);
			newType.setType(theSourceRef.getChefType());
			newType.addSourceReference(theSourceRef.getMySourceReferenceData());
			aNewRootItem.setTheChefType(newType);

			// specifiek doel
			if (theSourceRef.isGoal()) {
				newType.setQuestionText("In hoeverre is het doel [" + theSourceRef.getInformalName() + "] bereikt?");

			}
			// specifiek risk
			if (theSourceRef.isRisk()) {
				newType.setQuestionText("Is er een risico m.b.t.  [" + theSourceRef.getInformalName() + "]?");
			}

			// specifiek kans
			if (theSourceRef.isOpportinity()) {
				newType.setQuestionText("Is er een risico m.b.t.  [" + theSourceRef.getInformalName() + "]?");

			}

		}
		return aNewRootItem;
	}

	public TreeGrid<ChefTaxonomyItem> getTaxonomyGrid() {

		return this.taxGrid;
	}

	private void addChefTaxonomyBelow(ChefTaxonomyItem newItem, ChefTaxonomyItem myTargetTreeItem) {
		int loc;

		if (!this.theChefTaxonomyRootItems.contains(newItem)) {
			loc = theChefTaxonomyRootItems.indexOf(myTargetTreeItem);
			if (loc != -1) {
				this.theChefTaxonomyRootItems.add(loc + 1, newItem);

			} else {
				addToTargetChildBelowAnyRoot(newItem, myTargetTreeItem);
			}
		}

	}

	private void addChefTaxonomyAbove(ChefTaxonomyItem newItem, ChefTaxonomyItem myTargetTreeItem) {
		int loc;

		loc = theChefTaxonomyRootItems.indexOf(myTargetTreeItem);
		if (loc != -1) {
			if (!this.theChefTaxonomyRootItems.contains(newItem)) {
				this.theChefTaxonomyRootItems.add(loc, newItem);
			}
		} else {
			addToTargetChildAboveAnyRoot(newItem, myTargetTreeItem);

		}

	}

	private void addToTargetChildAboveAnyRoot(ChefTaxonomyItem newRootItem, ChefTaxonomyItem myTargetTreeItem) {
		int i;
		boolean found;

		found = false;
		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			found = this.theChefTaxonomyRootItems.get(i).addToTargetChildAbove(newRootItem, myTargetTreeItem);
			if (found) {
				break;
			}
		}

	}

	private void addToTargetChildBelowAnyRoot(ChefTaxonomyItem newRootItem, ChefTaxonomyItem myTargetTreeItem) {
		int i;
		boolean found;

		found = false;
		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			found = this.theChefTaxonomyRootItems.get(i).addToTargetChildBelow(newRootItem, myTargetTreeItem);
			if (found) {
				break;
			}
		}

	}

	// loading

	private void loadRootReferences() {

		theChefRootReferences.clear();
		String domainCode, objectID;
		ChefType theObject;
		InterfaceToDomainTypeRepository theIFC;
		List<ChefTreeContext> theChefRoots;
		int i;
		ChefTreeContext aRoot;
		ChefRootReference aReferenceForRoot;

		if (WEB_Session.getInstance().getTheDomain() != null) {
			this.theChefRootReferences.clear();
			domainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
			theIFC = InterfaceToDomainTypeRepository.getInstance();
			if (theIFC != null) {
				theChefRoots = theIFC.getAllChefRootsForDomain(domainCode);
				if (theChefRoots != null) {

					for (i = 0; i < theChefRoots.size(); i++) {
						aRoot = theChefRoots.get(i);
						aReferenceForRoot = new ChefRootReference();
						aReferenceForRoot.setRootID(aRoot.get_id());
						aReferenceForRoot.setTheme(aRoot.getThemeAsString());

						// Load the related object
						objectID = aRoot.getRelatedChefObjectID();
						theObject = theIFC.findChefTypeByID(domainCode, objectID);
						if (theObject != null) {
							aReferenceForRoot.setDisplayName(theObject.getName());
						} else {
							PlanSpaceLogger.getInstance().log_Error("ChefType lost: " + objectID);
						}
						this.theChefRootReferences.add(aReferenceForRoot);

					}

				}
			} else {
				PlanSpaceLogger.getInstance().log_Error("[ERROR] Domain repository not available");

			}

		} else {
			PlanSpaceLogger.getInstance().log_Error("[ERROR] No domain selected");

		}

	}

	/*
	 * private void createCompleteChefTaxonomyForDomain() { String domainCode;
	 * InterfaceToDomainTypeRepository theIFC; List<ChefTreeContext> theChefRoots;
	 * int i; ChefTreeContext aRoot; ChefTaxonomyItem aTaxItemForRoot;
	 * 
	 * this.theChefTaxonomyRootItems.clear(); if
	 * (WEB_Session.getInstance().getTheDomain() != null) { domainCode =
	 * WEB_Session.getInstance().getTheDomain().getDomainCode(); theIFC =
	 * InterfaceToDomainTypeRepository.getInstance(); if (theIFC != null) {
	 * theChefRoots = theIFC.getAllChefRootsForDomain(domainCode); if (theChefRoots
	 * != null) { // System.out.println("roots received " + theChefRoots.size());
	 * for (i = 0; i < theChefRoots.size(); i++) { aRoot = theChefRoots.get(i);
	 * aTaxItemForRoot = new ChefTaxonomyItem();
	 * aTaxItemForRoot.setUpByChefTreeContext(aRoot, true);
	 * this.theChefTaxonomyRootItems.add(aTaxItemForRoot);
	 * 
	 * } // System.out.println("root count" + this.theChefTaxonomyRootItems.size());
	 * this.RefreshTaxGrid(); } } else { PlanSpaceLogger.getInstance().
	 * log_Error("[ERROR] Domain repository not available");
	 * 
	 * }
	 * 
	 * } else {
	 * PlanSpaceLogger.getInstance().log_Error("[ERROR] No domain selected");
	 * 
	 * }
	 * 
	 * }
	 */

	private void createCompleteChefTaxonomyForDomain() {
		String domainCode;
		InterfaceToDomainTypeRepository theIFC;
		List<ChefTreeContext> theChefRoots;
		int i;
		ChefTreeContext aRoot;
		ChefTaxonomyItem aTaxItemForRoot;

		if (WEB_Session.getInstance().getTheDomain() != null) {
			// first try cache
			this.theChefTaxonomyRootItems = WEB_Session.getInstance().getDomainChefTaxonomy();
			if (theChefTaxonomyRootItems == null) {
				// if no cache then load
				this.theChefTaxonomyRootItems = new ArrayList<ChefTaxonomyItem>();
				domainCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
				theIFC = InterfaceToDomainTypeRepository.getInstance();
				if (theIFC != null) {
					theChefRoots = theIFC.getAllChefRootsForDomain(domainCode);
					if (theChefRoots != null) {
						// System.out.println("roots received " + theChefRoots.size());
						for (i = 0; i < theChefRoots.size(); i++) {
							aRoot = theChefRoots.get(i);
							aTaxItemForRoot = new ChefTaxonomyItem();
							aTaxItemForRoot.setUpByChefTreeContext(aRoot, true);
							this.theChefTaxonomyRootItems.add(aTaxItemForRoot);

						}
						// System.out.println("root count" + this.theChefTaxonomyRootItems.size());
						this.RefreshTaxGrid();
					}
					WEB_Session.getInstance().setDomainChefTaxonomy(theChefTaxonomyRootItems);
				} else {
					PlanSpaceLogger.getInstance().log_Error("[ERROR] Domain repository not available");

				}
			} else {
				this.RefreshTaxGrid();
			}

		} else {
			PlanSpaceLogger.getInstance().log_Error("[ERROR] No domain selected");

		}

	}

	public void handleEvent(String anNotification, ChefTaxonomyItem chefTaxonomyItem) {

		if (anNotification.equals("moveUp")) {
			if (chefTaxonomyItem != null) {
				moveParentUp(chefTaxonomyItem);
			}
		} else if (anNotification.equals("edit")) {
			if (chefTaxonomyItem != null) {
				this.mainpropertyhandling(chefTaxonomyItem);
			}
		}

		else if (anNotification.equals("delete")) {
			if (chefTaxonomyItem != null) {
				this.deleteTaxNode(chefTaxonomyItem, false);
			}
		} else if (anNotification.equals("toRoot")) {
			if (chefTaxonomyItem != null) {
				this.addChefTaxonomyRoot(chefTaxonomyItem);
				System.out.println("TOT ROORT");
			}
		}

	}

	private void deleteTaxNode(ChefTaxonomyItem theItem, boolean recursive) {
		ChefTaxonomyItem newParent;
		List<ChefTaxonomyItem> childsToMove;
		int i;
		ChefTaxonomyItem aChild;

		newParent = theItem.getParent();

		childsToMove = theItem.getMyChildren();

		if (!recursive) {
			while (childsToMove.size() > 0) {
				aChild = childsToMove.get(0);
				this.moveParentUp(aChild);
				childsToMove = theItem.getMyChildren();
			}
			theItem.getMyChildren().clear();
		} else {
			while (childsToMove.size() > 0) {
				aChild = childsToMove.get(0);
				aChild.removeYourself();
				theItem.removeAChild(aChild);
				childsToMove = theItem.getMyChildren();
			}
		}
		if (newParent != null) {
			newParent.removeAChild(theItem);
			newParent.storeYourself();
		} else {
			this.deleteChefTaxonomyRoot(theItem);
		}
		// newParent.storeYourself();
		theItem.setParent(null);
		theItem.setRemoved(true);
		theItem.removeYourself();
		this.RefreshAndOpenTaxGrid(newParent);
	}

	private void moveParentUp(ChefTaxonomyItem theItem) {
		ChefTaxonomyItem oldParent;
		ChefTaxonomyItem parentFromParent;

		oldParent = theItem.getParent();
		if (oldParent != null) {
			oldParent.removeAChild(theItem);
			oldParent.storeYourself();
			parentFromParent = oldParent.getParent();
			if (parentFromParent != null) {
				parentFromParent.addAChild(theItem);
				parentFromParent.storeYourself();
				theItem.storeYourself();
			} else {
				this.addChefTaxonomyRoot(theItem);
				theItem.storeYourself();
			}
			this.RefreshAndOpenTaxGrid(theItem);
		} else {
			// do nothing, I am already in root
		}

	}

	private void exportToCSV() {
		CSVExportFile anExport;
		int i, level;
		ChefTaxonomyItem aRoot;
		boolean recursive;
		String fileName;

		level = 0;
		recursive = true;
		fileName = createFileName();
		anExport = new CSVExportFile(fileName);
		this.createCSVheader(anExport);
		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			aRoot = this.theChefTaxonomyRootItems.get(i);
			aRoot.exportYourSelfToCSV(anExport, level, recursive);
		}

		anExport.closeTextFile();
	}

	private void generateDataContext() {
		CSVExportFile anExport;
		int i, level;
		ChefTaxonomyItem aRoot;
		boolean recursive;
		String fileName;

		level = 0;
		recursive = true;
		fileName = createFileNameForData();
		anExport = new CSVExportFile(fileName);
		// this.createCSVheader(anExport);
		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			aRoot = this.theChefTaxonomyRootItems.get(i);
			aRoot.exportDataContext(anExport, level, recursive);
		}

		anExport.closeTextFile();

	}

	private void restoreByName() {

		int i, level;
		ChefTaxonomyItem aRoot;

		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			aRoot = this.theChefTaxonomyRootItems.get(i);
			aRoot.restoreByName();
		}
		WEB_Session.getInstance().notifyPolicyReferenceEditor("RestoreByName");

	}

	private void filterUsedSourceReferences() {

		int i, level;
		ChefTaxonomyItem aRoot;

		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			aRoot = this.theChefTaxonomyRootItems.get(i);
			aRoot.filterUsedSourceReferences();
		}
		WEB_Session.getInstance().notifyPolicyReferenceEditor("RefreshView");

	}

	private void createCSVheader(CSVExportFile anExport) {
		anExport.writeCSVitem("level");
		anExport.writeCSVitem("type");
		anExport.writeCSVitem("doel_type");
		anExport.writeCSVitem("informele naam");
		anExport.writeCSVitem("thema");
		anExport.writeCSVitem("impact");
		// ids
		anExport.writeCSVitem("node_id");
		anExport.writeCSVitem("object_id");
		anExport.writeCSVitem("bron_id");

		// scheider
		anExport.writeCSVitem("annotatie");
		// annotatie
		anExport.writeCSVitem("annotatie_naam");
		anExport.writeCSVitem("bron_locatie");
		anExport.writeCSVitem("URL");
		anExport.writeCSVitem("pagina_nr");
		anExport.write("tekst_annotatie");
		anExport.write("not_used");

		// afsluiten
		anExport.write("\n");
	}

	private void generateExportToJson() {
		JsonExportFile anExport;
		int i, level;
		ChefTaxonomyItem aRoot;
		boolean recursive;
		String fileName;
		String jsonItems;

		JsonPolicyItems thePolicyItems;
	

		level = 0;
		recursive = true;
		fileName = createFileNameForJson();
		anExport = new JsonExportFile(fileName);
		

		thePolicyItems = new JsonPolicyItems();
		for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
			aRoot = this.theChefTaxonomyRootItems.get(i);
			aRoot.exportItemsToJson(anExport, level, recursive, thePolicyItems);
		}

		if (!thePolicyItems.isEmpty()) {
	
		
			for (i = 0; i < this.theChefTaxonomyRootItems.size(); i++) {
				aRoot = this.theChefTaxonomyRootItems.get(i);
				aRoot.exportTreeToJson(anExport, level, recursive, thePolicyItems);
			}
	
			anExport.writeln(thePolicyItems.toJson());
		}

		this.notifyUser("JSON export aangemaakt: " + fileName);
		anExport.closeTextFile();
	}

	private String createFileName() {
		String s;

		s = "export_" + WEB_Session.getInstance().getTheDomain().getDomainCode();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss");
		LocalDateTime localDate = LocalDateTime.now();
		/*
		 * s = s + String.valueOf(localDate.getYear()); s = s +
		 * String.valueOf(localDate.getMonth()); s = s +
		 * String.valueOf(localDate.getDayOfMonth()); s = s + "_"; s = s +
		 * String.valueOf(localDate);
		 */
		s = s + "_" + String.valueOf(dtf.format(localDate));
		s = s + ".csv";
		System.out.println("Name : " + s);
		return s;
	}

	private String createFileNameForData() {
		String s;

		s = "DATA_export_" + WEB_Session.getInstance().getTheDomain().getDomainCode();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss");
		LocalDateTime localDate = LocalDateTime.now();
		/*
		 * s = s + String.valueOf(localDate.getYear()); s = s +
		 * String.valueOf(localDate.getMonth()); s = s +
		 * String.valueOf(localDate.getDayOfMonth()); s = s + "_"; s = s +
		 * String.valueOf(localDate);
		 */
		s = s + "_" + String.valueOf(dtf.format(localDate));
		s = s + ".txt";
		System.out.println("Name : " + s);
		return s;
	}

	private String createFileNameForJson() {
		String s;

		s = "JSON_export_" + WEB_Session.getInstance().getTheDomain().getDomainCode();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss");
		LocalDateTime localDate = LocalDateTime.now();
		/*
		 * s = s + String.valueOf(localDate.getYear()); s = s +
		 * String.valueOf(localDate.getMonth()); s = s +
		 * String.valueOf(localDate.getDayOfMonth()); s = s + "_"; s = s +
		 * String.valueOf(localDate);
		 */
		s = s + "_" + String.valueOf(dtf.format(localDate));
		s = s + ".JSON";
		System.out.println("Name : " + s);
		return s;
	}

	public void prepareOpening() {
		this.loadThemesForDomain();
		this.currentTaxMode = t_taxViewMode.FULL;
		this.breadCrums.clear();
		createCompleteChefTaxonomyForDomain();
	}

	public void notifyFilterSet() {
		if (this.currentEditChefItem != null) {
			this.RefreshAndOpenTaxGrid(currentEditChefItem);
		} else {
			this.RefreshTaxGrid();
		}

	}

	public void setTaxViewModeFull() {
		this.currentTaxMode = t_taxViewMode.FULL;
		if (this.currentEditChefItem != null) {
			this.RefreshAndOpenTaxGrid(currentEditChefItem);
		} else {
			this.RefreshTaxGrid();
		}
		hideLevelButtonsAbove(0);
		this.breadCrums.clear();

	}

	private void hideLevelButtonsAbove(int newLevel) {
		int i;

		currentLevel = newLevel;

		for (i = 0; i < newLevel; i++) {
			this.levelButtons.get(i).setVisible(true);
		}

		for (i = newLevel; i < this.levelButtons.size(); i++) {
			this.levelButtons.get(i).setVisible(false);
		}

	}

	private void createLevelButtons(HorizontalLayout topButLine) {
		this.levelButtons = new ArrayList<Image>();

		Image number;
		String r = "35px";

		number = new Image("/one.png", "1");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 1");
		number.addClickListener(listener -> {
			gotoDetailLevel(1);
		});
		levelButtons.add(number);
		topButLine.add(number);

		number = new Image("/two.png", "2");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 2");
		number.addClickListener(listener -> {
			gotoDetailLevel(2);
		});
		levelButtons.add(number);
		topButLine.add(number);

		number = new Image("/three.png", "3");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 3");
		number.addClickListener(listener -> {
			gotoDetailLevel(3);
		});
		levelButtons.add(number);
		topButLine.add(number);

		number = new Image("/four.png", "4");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 4");
		number.addClickListener(listener -> {
			gotoDetailLevel(4);
		});
		levelButtons.add(number);
		topButLine.add(number);

		number = new Image("/five.png", "5");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 5");
		number.addClickListener(listener -> {
			gotoDetailLevel(5);
		});
		levelButtons.add(number);
		topButLine.add(number);

		number = new Image("/six.png", "6");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 6");
		number.addClickListener(listener -> {
			gotoDetailLevel(6);
		});
		levelButtons.add(number);
		topButLine.add(number);

		number = new Image("/seven.png", "7");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 7");
		number.addClickListener(listener -> {
			gotoDetailLevel(7);
		});
		levelButtons.add(number);
		topButLine.add(number);

		number = new Image("/eight.png", "8");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 8");
		number.addClickListener(listener -> {
			gotoDetailLevel(8);
		});
		levelButtons.add(number);
		topButLine.add(number);

		number = new Image("/eight.png", "9");
		number.setHeight(r);
		number.setWidth(r);
		number.setVisible(false);
		number.getElement().setAttribute("title", "Detail level 9");
		number.addClickListener(listener -> {
			gotoDetailLevel(9);
		});
		levelButtons.add(number);
		topButLine.add(number);

	}

	private void gotoDetailLevel(int backToLevel) {
		int i;

		ChefTaxonomyItem theGotoLevel;

		theGotoLevel = this.breadCrums.get(backToLevel - 1);
		i = backToLevel;
		while (i < breadCrums.size()) {
			breadCrums.remove(i);
			i++;
		}
		this.currentEditChefItem = theGotoLevel;
		this.RefreshTaxGrid();
		this.hideLevelButtonsAbove(backToLevel);
		this.currentLevel = backToLevel;

	}

}
