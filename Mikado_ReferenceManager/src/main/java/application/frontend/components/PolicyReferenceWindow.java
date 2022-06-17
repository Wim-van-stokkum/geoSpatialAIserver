package application.frontend.components;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DragStartEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import application.frontend.AbsoluteLayout;
import application.frontend.WEB_ReferenceTab;
import application.frontend.WEB_Session;
import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefTreeContext;
import planspace.chefModelTypes.ChefType;
import planspace.compliance.ComplianceReferenceProject;
import planspace.compliance.SourceReference;
import planspace.compliance.SourceReferenceData;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_itemTypeDropped;
import planspace.utils.MikadoSettings;
import planspace.utils.PlanSpaceLogger;

public class PolicyReferenceWindow extends VerticalLayout {

	private VerticalLayout sourceDocSection;

	private VerticalLayout annotationSection;
	private AbsoluteLayout policyFrameworkSection;
	private Button importSourceDocumentButton;
	private ComplianceReferenceProject aComplianceProject;
	private MemoryBuffer memoryBuffer;
	private Upload singleFileUpload;
	private String fileName;
	private HashMap<HorizontalLayout, SourceReference> annotationsPerLabel;
	private HashMap<SourceReference, HorizontalLayout> labelPerAnnotation;

	private Tab sourceDocTab;
	private Tab annotationTab;
	private Tab policyFrameWorkTab;
	private Tabs sourceTabs;
	private Image radAlmere;
	private Button radCenter;
	private Button veiligBut;
	private Button showUsedButton;
	private boolean showUsed;
	private HorizontalLayout annotationFilterSection;

	private ComboBox<String> annotationUsedFilterCB;

	private List<String> annotationUsedFilterOptions;
	private t_filterUsedMode filterUsedMode;

	private VerticalLayout annotationOverview, goalOverview;
	private List<SourceReference> theImportedReferences;

	

	private ListBox<String> policyDocsSelected;

	private VerticalLayout theAddFileSection;
	MultiSelectListBox<String> potentialFiles;
	private List<String> chosenFiles;

	private String theThemedGoalFilter;
	private TreeGrid<ChefType> goalGrid;
	private ComboBox<String> chefThemeFilterCB;

	private List<String> themes;

	private ChefType currentSelectedItem;

	private ChefTypeFilterControl aChefTypeFilterControl;

	private WEB_ReferenceTab myParentReferenceManager;

	private enum t_filterUsedMode {
		USED, UNUSED, ALL
	};

	public PolicyReferenceWindow(WEB_ReferenceTab myWEB_ReferenceTab) {
		super();

		this.myParentReferenceManager = myWEB_ReferenceTab;
		annotationUsedFilterOptions = new ArrayList<String>();

		annotationsPerLabel = new HashMap<HorizontalLayout, SourceReference>();
		labelPerAnnotation = new HashMap<SourceReference, HorizontalLayout>();
		theImportedReferences = new ArrayList<SourceReference>();
		chosenFiles = new ArrayList<String>();

		setDefaultStyle();
		initTabWindows();
		setDefaultOpening();

	}

	private void setDefaultOpening() {
		this.selectSourceDoc(); // Default open tab on sourcedoc tab
		showUsed = true;
		this.enableAllFilterOptions();

		filterUsedMode = t_filterUsedMode.UNUSED;

	}

	private void readGoalsForDomain() {
		InterfaceToDomainTypeRepository theIFC;
		List<ChefType> goalsFound;
		WEB_Session theSession;

		PlanSpaceLogger.getInstance().log_ConfigIO("READ 1");

		theIFC = InterfaceToDomainTypeRepository.getInstance();
		theSession = WEB_Session.getInstance();
		if (theSession != null) {
			PlanSpaceLogger.getInstance().log_ConfigIO("READ 2");
			if (theIFC != null) {
				PlanSpaceLogger.getInstance().log_ConfigIO("READ 3");
				goalsFound = theIFC.findChefTypeByType(theSession.getTheDomain().getDomainCode(),
						String.valueOf(t_chefType.GOAL), theThemedGoalFilter);
				if (goalsFound != null) {
					PlanSpaceLogger.getInstance().log_ConfigIO("READ 4" + goalsFound.size());

				} else {
					PlanSpaceLogger.getInstance().log_ConfigIO("No goals for domain");
				}
			} else {
				PlanSpaceLogger.getInstance().log_ConfigIO("No session");
			}
		} else {
			PlanSpaceLogger.getInstance().log_ConfigIO("No domain server running");
		}
	}

	public void enableAllFilterOptions() {

		this.annotationUsedFilterOptions.clear();
		this.annotationUsedFilterOptions.add("Alle");
		this.annotationUsedFilterOptions.add("Ongebruikt");
		this.annotationUsedFilterOptions.add("Gebruikt");
	}



	public void setDefaultStyle() {
		this.setWidthFull();
		this.getStyle().set("border", "1px solid blue");
		this.setWidth("55%");
		this.setWidth("810px");
		this.setHeight("1000px");
		this.setSpacing(true);
	}

	private void initTabWindows() {
		createPolicySourceTab();
		createAnnotationSelectionTab();
		createFrameWorkSection();

		this.configureTabs();

		this.add(sourceTabs, sourceDocSection, annotationSection, policyFrameworkSection);

	}

	public void setRadAlmereAsPolicyFrameWork() {
		createPolicyRadAlmere();
	}

	private void configureTabs() {
		/* tabs */
		sourceDocTab = new Tab("beleidsbronnen");
		annotationTab = new Tab("annotaties");
		policyFrameWorkTab = new Tab("beleid raamwerk");
		sourceTabs = new Tabs(sourceDocTab, annotationTab, policyFrameWorkTab);

		sourceTabs.addSelectedChangeListener(event -> {
			if (event.getSelectedTab().getLabel().equals("annotaties")) {
				this.selectAnnotationTab();
			} else if (event.getSelectedTab().getLabel().equals("beleid raamwerk")) {
				this.selectFrameWorkTab();
			} else if (event.getSelectedTab().getLabel().equals("beleidsbronnen")) {
				this.selectSourceDoc();
			}

		});

	}

	// Tab logistics

	private void selectAnnotationTab() {
		this.policyFrameworkSection.setVisible(false);
		this.annotationSection.setVisible(true);
		this.sourceDocSection.setVisible(false);

	}

	private void selectFrameWorkTab() {
		this.policyFrameworkSection.setVisible(true);
		this.annotationSection.setVisible(false);
		this.sourceDocSection.setVisible(false);

	}

	private void selectSourceDoc() {
		this.policyFrameworkSection.setVisible(false);
		this.annotationSection.setVisible(false);
		this.sourceDocSection.setVisible(true);

	}

	// Tab contents
	private void createFrameWorkSection() {
		/* input = framework */
		this.policyFrameworkSection = new AbsoluteLayout();
		this.policyFrameworkSection.setWidthFull();
		this.policyFrameworkSection.getStyle().set("overflow", "auto");
		// this.policyFrameworkSection.getStyle().set("border", "1px solid blue");
		// this.policyFrameworkSection.setWidth("790");
		this.policyFrameworkSection.setHeight("990px");
		// this.itemsOpenSection.setHeight("300px%");

	}

	private void ChangeThemeFilter(String value) {
		// TODO Auto-generated method stub
		theThemedGoalFilter = null;
		if (chefThemeFilterCB.getValue() != null) {
			this.theThemedGoalFilter = chefThemeFilterCB.getValue();
			if (theThemedGoalFilter.equals("Ander")) {
				theThemedGoalFilter = null;
			} else if (theThemedGoalFilter.equals("Nader te bepalen")) {
				theThemedGoalFilter = null;
			}

		}
		// this.readGoalsForDomain();
	}

	



	private void notifyUser(String aNote) {

		// Notification notification = new Notification();
		Notification.show(aNote, 1500, Notification.Position.BOTTOM_CENTER);

	}

	private void createAnnotationSelectionTab() {

		// Complete tab
		this.annotationSection = new VerticalLayout();
		this.annotationSection.setWidthFull();
		// this.annotationSection.getStyle().set("overflow", "auto");
		// this.annotationSection.getStyle().set("border", "1px solid blue");
		// this.annotationImportSection.setWidth("790x");
		this.annotationSection.setHeight("990px");
		// this.itemsOpenSection.setHeight("300px%");
		this.annotationSection.setSpacing(true);

		// filter multi select not supported in flow
		this.annotationFilterSection = new HorizontalLayout();
//
		this.aChefTypeFilterControl = new ChefTypeFilterControl(this);

		this.annotationUsedFilterCB = new ComboBox<String>();
		this.annotationUsedFilterCB.setItems(this.annotationUsedFilterOptions);
		this.annotationUsedFilterCB.addValueChangeListener(event -> {
			String seloption = (String) event.getValue();
			this.handleUsedFilterChanged(seloption);
		});

		annotationFilterSection.add(aChefTypeFilterControl, annotationUsedFilterCB);
		/* input = annotaties */
		this.annotationOverview = new VerticalLayout();
		this.annotationOverview.setWidthFull();
		this.annotationOverview.getStyle().set("overflow", "auto");
		// this.annotationOverview.getStyle().set("border", "1px solid blue");
		this.annotationOverview.setHeight("800px");
		this.annotationOverview.setSpacing(true);

		annotationSection.add(annotationFilterSection, annotationOverview);

	}

	private void handleUsedFilterChanged(String selectedOption) {
		System.out.println("option selected : " + selectedOption);
		if (selectedOption.equals("Alle")) {
			this.filterUsedMode = t_filterUsedMode.ALL;
		} else if (selectedOption.equals("Gebruikt")) {
			this.filterUsedMode = t_filterUsedMode.USED;
		} else if (selectedOption.equals("Ongebruikt")) {
			this.filterUsedMode = t_filterUsedMode.UNUSED;
		}
		this.showAnnotationsUsingFilter();
	}

	private void createPolicySourceTab() {
		/* input = sourceDoc */

		VerticalLayout fileOverviewSection, theAddFileSection;

		this.sourceDocSection = new VerticalLayout();
		this.sourceDocSection.setWidthFull();
		this.sourceDocSection.getStyle().set("overflow", "auto");
		// this.sourceDocSection.getStyle().set("border", "1px solid blue");
		// this.annotationImportSection.setWidth("790x");
		this.sourceDocSection.setHeight("990px");
		// this.itemsOpenSection.setHeight("300px%");
		this.sourceDocSection.setSpacing(true);

		fileOverviewSection = this.createSourceDocOverview();

		create_manualFileNameLine();
		// this.sourceDocSection.add(this.singleFileUpload);
		this.sourceDocSection.add(fileOverviewSection);
		this.sourceDocSection.add(this.importSourceDocumentButton);
		theAddFileSection = this.addSelectFileSection();
		this.sourceDocSection.add(theAddFileSection);

	}

	// Behaviour

	private VerticalLayout createSourceDocOverview() {
		VerticalLayout theFileOverviewSection;
		HorizontalLayout fileOverviewWithButtons;
		VerticalLayout buttonComplFiles;
		Button addComplFileBut, startProcessingBut;
		Label theLabel;

		theLabel = new Label("Compliancy bronnen");

		theFileOverviewSection = new VerticalLayout();
		/* Overview files */
		fileOverviewWithButtons = new HorizontalLayout();
		fileOverviewWithButtons.setWidthFull();

		/* buttons doc manipulation */

		this.policyDocsSelected = new ListBox<String>();
		this.policyDocsSelected.setWidth("80%");

		buttonComplFiles = new VerticalLayout();
		addComplFileBut = addAddComplianceFileButton();
		buttonComplFiles.add(addComplFileBut);

		fileOverviewWithButtons.add(policyDocsSelected, buttonComplFiles);

		startProcessingBut = initimportSourceDocumentButton();
		theFileOverviewSection.add(theLabel, fileOverviewWithButtons, startProcessingBut);
		// Import button

		return theFileOverviewSection;
	}

	private VerticalLayout addSelectFileSection() {

		Label theLabel;

		Button addSelectedFiles, cancelAddingBut;

		// Section boundary

		this.theAddFileSection = new VerticalLayout();
		this.theAddFileSection.setWidthFull();
		this.theAddFileSection.getStyle().set("border", "1px solid blue");
		// title

		theLabel = new Label("Beschikbare compliancy bronnen");

		// LB for files
		potentialFiles = new MultiSelectListBox();
		potentialFiles.setWidthFull();

		// select button

		HorizontalLayout buttonLine = new HorizontalLayout();
		addSelectedFiles = new Button("Voeg toe aan domein");
		addSelectedFiles.addClickListener(event -> {
			this.addSelectedComplianceFiles(event);
		});

		cancelAddingBut = new Button("Annuleer toevoegen");
		cancelAddingBut.addClickListener(event -> {
			this.theAddFileSection.setVisible(false);
		});

		buttonLine.add(addSelectedFiles, cancelAddingBut);
		theAddFileSection.add(theLabel, potentialFiles, buttonLine);
		theAddFileSection.setVisible(false);

		return theAddFileSection;
	}

	private void addSelectedComplianceFiles(ClickEvent<Button> event) {

		potentialFiles.getSelectedItems().forEach((String aSelectedItem) -> {
			this.chosenFiles.add(aSelectedItem);
		});
		this.theAddFileSection.setVisible(false);
		this.policyDocsSelected.setItems(chosenFiles);

	}

	private Button addAddComplianceFileButton() {

		Icon icon;
		Button addComplFileBut;

		icon = VaadinIcon.FILE_ADD.create();

		addComplFileBut = new Button("", icon);

		// Set action
		addComplFileBut.addClickListener(e -> {
			if (this.theAddFileSection.isVisible()) {
				this.theAddFileSection.setVisible(false);
			} else {
				this.getAvailableComplianceFiles();
				this.theAddFileSection.setVisible(true);
			}

		});

		return addComplFileBut;
	}

	private void getAvailableComplianceFiles() {

		File folder = new File(MikadoSettings.getInstance().getPathToAnnotatedDocuments());
		File[] listOfFiles = folder.listFiles();
		List<String> newItems;

		newItems = new ArrayList<String>();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				if (!this.chosenFiles.contains(file.getName())) {
					newItems.add(file.getName());
				}
			}
		}
		this.potentialFiles.setItems(newItems);
	}

	private Button initShowUsedButton() {

		Icon icon;

		icon = VaadinIcon.REFRESH.create();
		if (this.showUsedButton == null) {
			this.showUsedButton = new Button("Toon gebruikte annotaties", icon);

			// Set action
			this.showUsedButton.addClickListener(e -> {
				showUsedAnnotations();

			});

		}
		return this.showUsedButton;
	}

	private void showUsedAnnotations() {
		this.showUsed = true;
		int i;

		for (i = 0; i < this.annotationOverview.getComponentCount(); i++) {
			if (!this.annotationOverview.getComponentAt(i).isVisible()) {
				this.annotationOverview.getComponentAt(i).setVisible(true);
			}
		}

	}

	private void createPolicyRadAlmere() {

		/* Rad */
		radAlmere = new Image("/RadLeefomgevingAlmere.png", "Rad van de Leefomgeving");
		radAlmere.setMaxWidth("750px");
		radAlmere.setMaxHeight("750px");
		radAlmere.addClickListener(event -> {
			System.out.println("X=" + event.getClientX() + ", Y=" + event.getClientY());
		});

		policyFrameworkSection.add(radAlmere);
		// center button
		radCenter = new Button("  ");
		radCenter.setWidth("90px");
		radCenter.setHeight("90px");
		radCenter.addClickListener(event -> {
			System.out.println("groene gezonde stad");
		});
		policyFrameworkSection.add(radCenter, 330, 330);
		veiligBut = new Button("  ");
		veiligBut.setWidth("60px");
		veiligBut.setHeight("25px");
		veiligBut.addClickListener(event -> {
			System.out.println("veilig gezonde leefomgeving");
		});
		policyFrameworkSection.add(veiligBut, 203, 300);

	}

	private Button initimportSourceDocumentButton() {

		Icon icon;

		icon = VaadinIcon.REFRESH.create();
		if (this.importSourceDocumentButton == null) {
			this.importSourceDocumentButton = new Button("Verwerk beleidsdocument", icon);

			// Set action
			this.importSourceDocumentButton.addClickListener(e -> {
				if (this.policyDocsSelected.getValue() != null) {
					startImportReferences(this.policyDocsSelected.getValue());
				}
			});

		}
		return this.importSourceDocumentButton;
	}

	private void startImportReferences(String aFileName) {
		List<SourceReferenceData> newImportedReferences;

		aComplianceProject = new ComplianceReferenceProject(aFileName);

		aComplianceProject.ReadComplianceProject();

		newImportedReferences = aComplianceProject.getReferences();
		if (newImportedReferences != null) {
			this.processReferencesFound(newImportedReferences);
			this.showAnnotationsUsingFilter();
			this.selectAnnotationTab();

		}

	}

	private void create_manualFileNameLine() {

		memoryBuffer = new MemoryBuffer();
		singleFileUpload = new Upload(memoryBuffer);
		singleFileUpload.setAutoUpload(false);

		singleFileUpload.addSucceededListener(event -> { // Get information about

			String selFileName = event.getFileName();
			this.fileName = selFileName;
			PlanSpaceLogger.getInstance().log_Debug("[SELECTED]" + this.fileName);

		});
	}

	private void processReferencesFound(List<SourceReferenceData> newImportedReferences) {
		int i;
		SourceReference aRef;
		HorizontalLayout theReferenceLabel;

		annotationsPerLabel.clear();

		for (i = 0; i < newImportedReferences.size(); i++) {
			aRef = new SourceReference(newImportedReferences.get(i));

			// Add to existing list

			if (this.theImportedReferences == null) {
				this.theImportedReferences = new ArrayList<SourceReference>();
			}
			if (!alreadyImported(aRef)) {
				this.theImportedReferences.add(aRef);
				theReferenceLabel = aRef.getMeAsDragableReference();

				// setting drag behaviour
				DragSource<HorizontalLayout> myDragSource = DragSource.create(theReferenceLabel);
				myDragSource.addDragStartListener((DragStartEvent<HorizontalLayout> event) -> {

					if (myDragSource.getDragData().getClass().getName().equals(SourceReference.class.getName())) {
						SourceReference theSourceReferenceDragged = (SourceReference) myDragSource.getDragData();
						this.myParentReferenceManager.notifySourceReferenceDragged(theSourceReferenceDragged);
					}

					event.getSource().getUI().get().getInternals().setActiveDragSourceComponent(event.getSource());
				});
				myDragSource.setDragData(aRef);

				if (theReferenceLabel != null) {

					annotationsPerLabel.put(theReferenceLabel, aRef);
					labelPerAnnotation.put(aRef, theReferenceLabel);

				}

			} else {
				PlanSpaceLogger.getInstance().log("[INFO]: Reimported source reference " + aRef.getInformalName());
			}
		}

	}

	private boolean alreadyImported(SourceReference aRef) {
		boolean existingItem;
		SourceReference existingRef;
		int i;

		existingItem = false;
		for (i = 0; i < this.theImportedReferences.size(); i++) {
			existingRef = this.theImportedReferences.get(i);
			if (aRef.getExternalID() != null) {
				if (!aRef.getExternalID().isBlank() && !aRef.getExternalID().isEmpty()) {
					if (existingRef.getExternalID() != null) {
						if (!existingRef.getExternalID().isBlank() && !existingRef.getExternalID().isEmpty()) {
							if (aRef.getExternalID().equals(existingRef.getExternalID())) {
								existingItem = true;
							}
						}

					}
				}

			}
		}
		return existingItem;
	}

	private void showAnnotationsUsingFilter() {
		int i;
		SourceReference aRef;
		HorizontalLayout theReferenceLabel;
		this.annotationOverview.removeAll();

		boolean show;

		List<t_chefType> annoTypesAllowedByFilter;
		annoTypesAllowedByFilter = this.aChefTypeFilterControl.getTypesAllowedByFilter();

		if (this.theImportedReferences != null) {

			for (i = 0; i < this.theImportedReferences.size(); i++) {

				show = false;
				aRef = this.theImportedReferences.get(i);
				if (annoTypesAllowedByFilter.contains(aRef.getChefType())) {
					if (this.filterUsedMode.equals(t_filterUsedMode.ALL)) {
						show = true;
					} else if (this.filterUsedMode.equals(t_filterUsedMode.USED)) {
						if (aRef.isUsed()) {
							show = true;
						}

					} else if (this.filterUsedMode.equals(t_filterUsedMode.UNUSED)) {
						if (!aRef.isUsed()) {
							show = true;
						}
					}
					if (show) {
						theReferenceLabel = this.labelPerAnnotation.get(aRef);
						if (theReferenceLabel != null) {

							this.annotationOverview.add(theReferenceLabel);
						}
					}
				}
			}

		}

	}

	public void handleEvent(String anNotification) {
		if (anNotification.equals("SourceReferenceUsed")) {
			this.showAnnotationsUsingFilter();
		}
		if (anNotification.equals("RefreshView")) {
			this.showAnnotationsUsingFilter();
		}

	}

	public void handleEvent(String event, ChefTaxonomyItem chefTaxonomyItem) {

		if (event.equals("SourceReferenceInUse")) {

			setUsedForItem(chefTaxonomyItem);

		} else

		if (event.equals("RestoreByName")) {
			System.out.println("Restore");
			restoreSourceReferenceByName(chefTaxonomyItem);

		}

	}

	public void setUsedForItem(ChefTaxonomyItem chefTaxonomyItem) {
		int i, j;
		SourceReference aRef;
		SourceReferenceData existingRef;
		ChefType theChefObject;

		theChefObject = chefTaxonomyItem.getTheChefType();
		if (theChefObject.getSourceReferences() != null) {
			for (j = 0; j < theChefObject.getSourceReferences().size(); j++) {
				existingRef = theChefObject.getSourceReferences().get(j);
				// System.out.println("theImportedReferences " + theImportedReferences.size());
				for (i = 0; i < this.theImportedReferences.size(); i++) {
					// System.out.println("Send3 " + i);
					aRef = theImportedReferences.get(i);

					if (aRef.getExternalID() != null) {
						if (!aRef.getExternalID().isBlank() && !aRef.getExternalID().isEmpty()) {
							if (existingRef.getExternalID() != null) {
								if (!existingRef.getExternalID().isBlank() && !existingRef.getExternalID().isEmpty()) {
									if (aRef.getExternalID().equals(existingRef.getExternalID())) {
										aRef.setUsed(true);
										System.out.println("MARKED REF AS USED" + aRef.getInformalName());
										break;
									}
								}

							}
						}

					}
				}
			}
		}
	}

	public void restoreSourceReferenceByName(ChefTaxonomyItem chefTaxonomyItem) {
		int i;

		SourceReference aRef;

		ChefTreeContext existingRef = chefTaxonomyItem.getTreeContextData();
		// System.out.println("theImportedReferences " + theImportedReferences.size());
		for (i = 0; i < this.theImportedReferences.size(); i++) {
			// System.out.println("Send3 " + i);
			aRef = theImportedReferences.get(i);

			if (aRef.getInformalName() != null) {
				if (!aRef.getInformalName().isBlank() && !aRef.getInformalName().isEmpty()) {
					if (chefTaxonomyItem.getInformalName().contentEquals(aRef.getInformalName())) {
						aRef.getMySourceReferenceData().set_id(existingRef.getSourceReferenceID());
						aRef.storeYourself();

					}
				}

			}

		}
	}

	public void prepareOpening() {

	}

	public void notifyFilterSet() {
		this.showAnnotationsUsingFilter();

	}

}
