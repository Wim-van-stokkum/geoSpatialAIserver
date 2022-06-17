package application.frontend.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.GoalGridService;
import application.frontend.PerspectiveGoalGridService;
import application.frontend.WEB_DomainTab;
import application.frontend.WEB_Session;
import planspace.chefModelTypes.ChefRootReference;
import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefTreeContext;
import planspace.chefModelTypes.ChefType;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.utils.PlanSpaceLogger;

public class ChefTaxonomyBrowser extends VerticalLayout {
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



	private ChefTaxonomyItem currentEditChefItem;


	private t_taxViewMode currentTaxMode;

	private ChefTypeFilterControl filterChefTypeCB;


	private List<Image> levelButtons;
	private int currentLevel;

	public ChefTaxonomyBrowser() {
		super();

		HorizontalLayout butLine;

		
		theChefTaxonomyRootItems = new ArrayList<ChefTaxonomyItem>();
		theChefRootReferences = new ArrayList<ChefRootReference>();
		breadCrums = new ArrayList<ChefTaxonomyItem>();

		setDefaultStyle();
		createTaxGrid();

		butLine = taxItemActionButtons();
		// gridAndButtons = new HorizontalLayout();
		// gridAndButtons.add( butLine);
		this.add(this.taxGrid, butLine);
		loadRootReferences();
		currentTaxMode = t_taxViewMode.FULL;

	}

	private HorizontalLayout taxItemActionButtons() {
		HorizontalLayout butLine;

		Button backButton;

		butLine = new HorizontalLayout();
		backButton = createBackButton();

		filterChefTypeCB = new ChefTypeFilterControl(this);
		butLine.add(backButton, filterChefTypeCB);
		this.createLevelButtons(butLine);
		return butLine;

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

	private void openChefTaxonomyProperies() {
		// System.out.println("open properties now used for loading");
		this.createCompleteChefTaxonomyForDomain();
	}

	/***
	 * Contents
	 */

	public void clearChefTaxonomy() {
		this.theChefTaxonomyRootItems.clear();
	}

	public List<ChefTaxonomyItem> getChefTaxonomyRootItems() {
		return this.theChefTaxonomyRootItems;
	}

	public void ReloadTaxGrid() {
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
		this.getStyle().set("border", "1px solid blue");
		this.setMinWidth("900px");
		this.setSpacing(true);
		this.setHeight("990px");

	}

	private void createTaxGrid() {
		String r = "30px";
		HorizontalLayout option;
		Label aText;
		Image img;

		this.taxGrid = new TreeGrid<ChefTaxonomyItem>();

		taxGrid.addComponentHierarchyColumn(ChefTaxonomyItem::getAvatar).setHeader("Beleid taxonomie");
		taxGrid.setDropMode(GridDropMode.ON_TOP);
		taxGrid.setRowsDraggable(true);
	//	taxGrid.drag
		// taxGrid.setSizeFull();
		taxGrid.setMinWidth("550px");
		taxGrid.setHeight("880px");
		taxGrid.setVerticalScrollingEnabled(true);

		// Event start dragging
		taxGrid.addDragStartListener(event -> {
			List<ChefTaxonomyItem> taxItemsBeingDragged;

			taxItemsBeingDragged = new ArrayList<ChefTaxonomyItem>();
			event.getDraggedItems().forEach(anItem -> {
				taxItemsBeingDragged.add((ChefTaxonomyItem) anItem);
			});
			WEB_Session.getInstance().notifyTaxonomyItemsDragged(taxItemsBeingDragged);
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

	private void notifyUser(String aNote) {

		Notification notification = new Notification();
		notification.show(aNote, 1500, Notification.Position.BOTTOM_CENTER);

	}

	private void openPolicy() {

		this.taxGrid.getSelectedItems().forEach(anItem -> {
			anItem.openReferencedDocument();
		});

	}


	/******************************************************************************************
	 * 
	 * SOURCE REFERENCE DROPPING
	 * 
	 */

	public TreeGrid<ChefTaxonomyItem> getTaxonomyGrid() {

		return this.taxGrid;
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
				PlanSpaceLogger.getInstance().log_Error("From Cache: "  + this.theChefTaxonomyRootItems.size());
				this.RefreshTaxGrid();
			}

		} else {
			PlanSpaceLogger.getInstance().log_Error("[ERROR] No domain selected");

		}

	}

	public void prepareOpening() {
	
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
