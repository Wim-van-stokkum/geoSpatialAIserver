package application.frontend;

import java.util.Iterator;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.components.CaseTypeEditor;
import application.frontend.components.ChefTaxonomyBrowser;
import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.domainTypes.DomainType;
import planspace.domainTypes.TaxonomyNode;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.utils.PlanSpaceLogger;

public class WEB_DomainTab extends WEB_AbstractedTab implements IFC_DetailEditListener {
	private ComboBox domainSelector;
	private VerticalLayout configSpace;
	private Tab caseTypeTab;
	private Tab capabilityTab;
	private Tab themeTab;
	private Tab taxonomyTab;
	private Tabs domainConfigTabs;
	private CaseTypeEditor caseTypeSection;
	private HorizontalLayout capabilityModelSection;
	private HorizontalLayout themeSection;
	private HorizontalLayout domainTaxonomySection;

	private TreeGrid<TaxonomyNode> taxTree;
	private TaxonomyNode theTaxonomy;
	private TaxonomyNode selectedConcept;


	private HorizontalLayout option;

	private ChefTaxonomyBrowser myChefTaxonomyBrowser;
	private Button toggleTaxBrowserWindow;
	private HorizontalLayout workSection;

	private List<ChefTaxonomyItem> monitoredAspectsOfCurrentCaseTypeBeingEdited;

	public WEB_DomainTab(String tabNaam, WEB_Workspace aWorkSpace) {
		super(tabNaam, aWorkSpace);
		initDomainSelector();
		initConfigSpace();

	}

	public void ConsumeDetailEditorOpened() {
		domainConfigTabs.setVisible(false);
	}

	public void ConsumeDetailEditorClosed() {
		domainConfigTabs.setVisible(true);
	}
	
	public void ConsumeEditorEvent(String editorEvent) {
		
	}


	private void initConfigSpace() {
		this.configSpace = new VerticalLayout();
		this.workSection = new HorizontalLayout();
		
		configSpace.setWidthFull();
		configSpace.getStyle().set("overflow", "auto");
		configSpace.getStyle().set("border", "1px solid green");
		configSpace.setWidthFull();
		configSpace.setSpacing(true);
		configSpace.setHeight("990px");
		configureTabs();
		configureTabContents();
		this.selectCaseTypeTab();

		myChefTaxonomyBrowser = new ChefTaxonomyBrowser();
		myChefTaxonomyBrowser.setVisible(false);
		this.workSection.add(myChefTaxonomyBrowser, configSpace);
		this.myContent.add(workSection);
		this.configSpace.setVisible(false);

	}

	private void configureTabContents() {
		configureCaseTypePane();
		configureCapabilityPane();
		configureThemePane();
		configureTaxonomyPane();
	}

	private void configureTaxonomyPane() {
		TreeGrid<TaxonomyNode> aTree;

		WEB_Session theSession;

		this.domainTaxonomySection = new HorizontalLayout();
		domainTaxonomySection.setWidthFull();
		domainTaxonomySection.getStyle().set("overflow", "auto");
		// domainTaxonomySection.getStyle().set("border", "1px solid green");
		domainTaxonomySection.setWidthFull();
		domainTaxonomySection.setSpacing(true);
		domainTaxonomySection.setHeight("990px");
		this.configSpace.add(domainTaxonomySection);

		this.taxTree = new TreeGrid<TaxonomyNode>();
		taxTree.addHierarchyColumn(TaxonomyNode::getTreeName).setHeader("Concept");
		taxTree.addColumn(TaxonomyNode::getTreeDescription).setHeader("Omschrijving");
		domainTaxonomySection.add(taxTree);

	}

	private void showTaxonomy() {
		WEB_Session theSession;

		theSession = WEB_Session.getInstance();
		theTaxonomy = theSession.getDomainTaxonomy();
		taxTree = this.createInMemoryTaxonomyTree();
		taxTree.setSizeFull();
	}

	private void configureThemePane() {
		this.themeSection = new HorizontalLayout();
		themeSection.setWidthFull();
		themeSection.getStyle().set("overflow", "auto");
		// domainTaxonomySection.getStyle().set("border", "1px solid green");
		themeSection.setWidthFull();
		themeSection.setSpacing(true);
		themeSection.setHeight("990px");
		this.configSpace.add(themeSection);
	}

	private void configureCapabilityPane() {
		this.capabilityModelSection = new HorizontalLayout();
		capabilityModelSection.getStyle().set("overflow", "auto");
		// domainTaxonomySection.getStyle().set("border", "1px solid green");
		capabilityModelSection.setWidthFull();
		capabilityModelSection.setSpacing(true);
		capabilityModelSection.setHeight("990px");
		this.configSpace.add(capabilityModelSection);

	}

	private void configureCaseTypePane() {

		this.createCaseTypeSection();
		this.configSpace.add(caseTypeSection);

	}


	private Button createToggleTaxBrowserBut() {
		Image icon;
		String r = "35px";

		icon = new Image("/taxonomy.png", "taxonomy browser");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Sluit eigenschappen");

		// Set action
		addBut.addClickListener(e -> {
			if (WEB_Session.getInstance().getTheDomain() != null) {
				if (!this.myChefTaxonomyBrowser.isVisible()) {
					this.myChefTaxonomyBrowser.prepareOpening();
					this.myChefTaxonomyBrowser.setVisible(true);
				} else {
					this.myChefTaxonomyBrowser.setVisible(false);
				}
			}
			else
			{
				this.notifyUser("Selecteer eerst een domein a.u.b");
			}

		});

		return addBut;
	}

	


	private void notifyUser(String aNote) {

		Notification notification = new Notification();
		notification.show(aNote, 1500, Notification.Position.BOTTOM_CENTER);

	}



	


	private void createCaseTypeSection() {
		this.caseTypeSection = new CaseTypeEditor(this);

	}

	private void configureTabs() {
		/* tabs */
		caseTypeTab = new Tab("Projecten");
		capabilityTab = new Tab("Capabilities");
		themeTab = new Tab("Thema's");
		taxonomyTab = new Tab("Taxonomie");
		domainConfigTabs = new Tabs(caseTypeTab, capabilityTab, taxonomyTab, themeTab);

		domainConfigTabs.addSelectedChangeListener(event -> {
			if (event.getSelectedTab().getLabel().equals("Projecten")) {
				this.selectCaseTypeTab();
			} else if (event.getSelectedTab().getLabel().equals("Capabilities")) {
				this.selectCapabilityTab();
			} else if (event.getSelectedTab().getLabel().equals("Thema's")) {
				this.selectThemeTab();
			} else if (event.getSelectedTab().getLabel().equals("Taxonomie")) {
				this.selectTaxonomyTab();
			}

		});
		this.configSpace.add(domainConfigTabs);
	}

	private void selectCaseTypeTab() {

		this.caseTypeSection.prepareOpening();
		this.capabilityModelSection.setVisible(false);
		this.caseTypeSection.setVisible(true);
		this.themeSection.setVisible(false);
		this.domainTaxonomySection.setVisible(false);


	}

	

	private void selectCapabilityTab() {
		this.capabilityModelSection.setVisible(true);
		this.caseTypeSection.setVisible(false);
		this.themeSection.setVisible(false);
		this.domainTaxonomySection.setVisible(false);

	}

	private void selectThemeTab() {
		this.capabilityModelSection.setVisible(false);
		this.caseTypeSection.setVisible(false);
		this.themeSection.setVisible(true);
		this.domainTaxonomySection.setVisible(false);

	}

	private void selectTaxonomyTab() {

		this.capabilityModelSection.setVisible(false);
		this.caseTypeSection.setVisible(false);
		this.themeSection.setVisible(false);
		this.domainTaxonomySection.setVisible(true);
		if (WEB_Session.getInstance().getTheDomain() != null) {

			showTaxonomy();
		}
	}

	private TreeGrid<TaxonomyNode> createInMemoryTaxonomyTree() {
		TaxoGridService taxoService = new TaxoGridService();

		taxTree.setItems(taxoService.getRootNodes(), taxoService::getChildNodes);

		taxTree.addItemClickListener(event -> {
			// PlanSpaceLogger.getInstance().log("Now selected: " +
			// event.getItem().getCode());
			this.selectedConcept = event.getItem();

		});

		taxTree.addExpandListener(event -> {

			TaxonomyNode aNode;

			Iterator<TaxonomyNode> itr = event.getItems().iterator();
			while (itr.hasNext()) {
				aNode = itr.next();
				// PlanSpaceLogger.getInstance().log("Now selected: " + aNode.getCode());
				this.selectedConcept = aNode;
			}

		});

		return taxTree;
	}

	private void initDomainSelector() {
		HorizontalLayout aLine;

		aLine = new HorizontalLayout();
		this.domainSelector = new ComboBox<DomainType>("");
		this.domainSelector.setLabel("Domein");
		this.domainSelector.setMinWidth("300px");
		this.refreshDomainSelector();

		this.domainSelector.addValueChangeListener(event -> {
			WEB_Session theSession;

			if (event.getValue() != null) {
				theSession = WEB_Session.getInstance();
				theSession.setTheDomain((DomainType) event.getValue());
				this.configSpace.setVisible(true);
				this.selectCaseTypeTab();
			}

		});

		this.toggleTaxBrowserWindow = this.createToggleTaxBrowserBut();

		aLine.add(domainSelector, toggleTaxBrowserWindow);
		this.myContent.add(aLine);
	}

	private void initChatDialog() {

	}

	private void refreshDomainSelector() {
		InterfaceToDomainTypeRepository theRepository;
		List<DomainType> allDomains;

		this.domainSelector.clear();
		theRepository = InterfaceToDomainTypeRepository.getInstance();
		allDomains = theRepository.GetAllDomains();
		if (allDomains != null) {
			this.domainSelector.setItems(allDomains);
			this.domainSelector.setItemLabelGenerator(DomainType::getDisplayName);

		} else {
			PlanSpaceLogger.getInstance().log("Geen domeinen gevonde ");
		}

	}

}
