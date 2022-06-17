package application.frontend;

import java.util.List;

import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.components.CaseEditor;
import planspace.caseModel.Case;
import planspace.caseModel.CaseType;

public class WEB_SimulateTab extends WEB_AbstractedTab {

	private VerticalLayout configSpace;
	private Tabs overviewTabs;
	private VerticalLayout caseTypeSection;
	private HorizontalLayout workSection;
	private Tab caseTab;
	private Tab caseTypeTab;
	private List<CaseType> theCaseTypes;
	private TreeGrid<CaseType> theCaseTypeGrids;
	private VerticalLayout caseSection;
	private TreeGrid<Case> theCaseGrids;
	private List<Case> theCases;
	private int tempTeller;
	private CaseEditor myCaseDetailsEditor;

	public WEB_SimulateTab(String tabNaam, WEB_Workspace aWorkSpace) {
		super(tabNaam, aWorkSpace);
		initConfigSpace();

	}

	private void initConfigSpace() {
		this.configSpace = new VerticalLayout();
		this.workSection = new HorizontalLayout();

		configSpace.setWidthFull();
		configSpace.getStyle().set("overflow", "auto");
	//	configSpace.getStyle().set("border", "1px solid gray");
		configSpace.setWidthFull();
		configSpace.setSpacing(true);
		
		configSpace.setHeight("990px");
		configureTabs();
		configureTabContents();
		this.selectCaseTypeTab();

		this.workSection.add(configSpace);
		this.myContent.add(workSection);
		
		tempTeller = 0;

	}

	private void configureTabContents() {
		configureCaseTypePane();
		configureCasePane();
		configureCaseDetails();

	}

	private void configureCaseDetails() {
		this.myCaseDetailsEditor = new CaseEditor(this);
		this.myCaseDetailsEditor.setVisible(false);
		this.configSpace.add(myCaseDetailsEditor);
	}

	private void configureCaseTypePane() {
		this.caseTypeSection = new VerticalLayout();

		caseTypeSection.setWidthFull();
		caseTypeSection.getStyle().set("overflow", "auto");
		// domainTaxonomySection.getStyle().set("border", "1px solid green");
		caseTypeSection.setMinWidth("900px");
		caseTypeSection.setSpacing(true);
		caseTypeSection.setHeight("990px");

		createCaseTypeSelectionGrid();
		caseTypeSection.add(theCaseTypeGrids);
		this.configSpace.add(caseTypeSection);

	}

	private void configureCasePane() {
		this.caseSection = new VerticalLayout();

		caseSection.setWidthFull();
		caseSection.getStyle().set("overflow", "auto");
		// domainTaxonomySection.getStyle().set("border", "1px solid green");
		caseSection.setMinWidth("900px");
		caseSection.setSpacing(true);
		caseSection.setHeight("990px");
		createCaseSelectionGrid();
		caseSection.add(this.theCaseGrids);
		this.configSpace.add(caseSection);

	}

	private void notifyUser(String aNote) {

		Notification notification = new Notification();
		notification.show(aNote, 1500, Notification.Position.BOTTOM_CENTER);

	}

	private void configureTabs() {
		/* tabs */
		caseTypeTab = new Tab("Zaaktypen");
		caseTab = new Tab("Zaken");

		overviewTabs = new Tabs(caseTypeTab, caseTab);

		overviewTabs.addSelectedChangeListener(event -> {
			if (event.getSelectedTab().getLabel().equals("Zaken")) {
				this.selectCaseTab();
			} else if (event.getSelectedTab().getLabel().equals("Zaaktypen")) {
				this.selectCaseTypeTab();
			}

		});
		this.configSpace.add(overviewTabs);
	}

	private void selectCaseTypeTab() {
		loadCaseTypesForDomain();
		this.caseSection.setVisible(false);
		this.caseTypeSection.setVisible(true);
	}

	private void selectCaseTab() {
		loadCasesForDomain();
		this.caseSection.setVisible(true);
		this.caseTypeSection.setVisible(false);
	}

	private void loadCasesForDomain() {
		this.theCases = WEB_Session.getInstance().loadCasesForDomain(null);
		this.showCases();

	}

	private void showCases() {
		if (this.theCases != null) {
			theCaseGrids.setItems(theCases);
		}
	}

	private void loadCaseTypesForDomain() {
		this.theCaseTypes = WEB_Session.getInstance().loadCaseTypesForDomain();
		this.showCaseTypes();
	}

	private void showCaseTypes() {
		if (this.theCaseTypes != null) {
			theCaseTypeGrids.setItems(theCaseTypes);
		}
	}

	private void createCaseTypeSelectionGrid() {
		HorizontalLayout option;
		Image img;
		Label aText;
		String r = "30px";

		theCaseTypeGrids = new TreeGrid<CaseType>();
		theCaseTypeGrids.addComponentHierarchyColumn(CaseType::getGuiLabel).setHeader("Zaaktype");
		// theCaseTypeGrids.addColumn(CaseType::getCaseTypeCode).setHeader("Technische
		// naam");
		theCaseTypeGrids.setRowsDraggable(false);
		theCaseTypeGrids.setMaxHeight("400px");
		theCaseTypeGrids.setMinWidth("500px");
		theCaseTypeGrids.setVerticalScrollingEnabled(true);

		GridContextMenu myContextMenu = theCaseTypeGrids.addContextMenu();

		option = new HorizontalLayout();
		img = new Image("/newcase.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Aanmaken zaak");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			this.createCaseForSelected();
		});

	}

	private void createCaseSelectionGrid() {
		HorizontalLayout option;
		Image img;
		Label aText;
		String r = "30px";

		theCaseGrids = new TreeGrid<Case>();
		theCaseGrids.addComponentHierarchyColumn(Case::getGuiLabel).setHeader("Zaken");
		theCaseGrids.addColumn(Case::getTheCaseTypeName).setHeader("Zaaktype");
	
		theCaseGrids.setRowsDraggable(false);
		theCaseGrids.setMaxHeight("400px");
		theCaseGrids.setMinWidth("500px");
		theCaseGrids.setVerticalScrollingEnabled(true);

		GridContextMenu myContextMenu = theCaseGrids.addContextMenu();

		option = new HorizontalLayout();
		img = new Image("/newcase.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Details zaak");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			if (theCaseGrids.getSelectedItems().size() == 1) {
				theCaseGrids.getSelectedItems().forEach(aSelItem -> {
					this.openCaseDetails(aSelItem);
				});
			} else {
				this.notifyUser("Selecteer eerst een zaak a.u.b");
			}
		});
		
		

	}

	private void openCaseDetails(Case theCase) {
		this.overviewTabs.setVisible(false);
		this.caseSection.setVisible(false);
		this.caseTypeSection.setVisible(false);
		
		this.myCaseDetailsEditor.prepareForOpening(theCase);
		this.myCaseDetailsEditor.setVisible(true);
		
	}

	private void createCaseForSelected() {
		if (theCaseTypeGrids.getSelectedItems().size() > 0) {
			theCaseTypeGrids.getSelectedItems().forEach(aSelItem -> {
				this.createCaseForCaseType(aSelItem);
			});
		} else {
			this.notifyUser("Selecteer eerst een zaaktype a.u.b");
		}
	}

	private void createCaseForCaseType(CaseType theCaseType) {
		Case newCase;
		int i;
		
		newCase = new Case();
		newCase.setCaseTypeID(theCaseType.get_id());
		tempTeller = tempTeller + 1;
		newCase.setExternalCaseID("Zaak "  + tempTeller );
		newCase.setDescription("Case " + tempTeller + " of case type "  +  theCaseType.getDescription());
		newCase.setDomainCode(theCaseType.getDomainCode());

		newCase.initializeConcernsForCaseType(theCaseType);
		newCase.createYourself();
	}
	
	@Override
	public void prepareOpening() {
		this.selectCaseTypeTab();
		
	}

	public void notifyCloseCaseDetail() {
		this.myCaseDetailsEditor.setVisible(false);
		this.overviewTabs.setVisible(true);
		this.selectCaseTab();
		
	}
	
	

}
