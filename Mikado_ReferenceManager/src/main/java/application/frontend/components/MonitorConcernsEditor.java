package application.frontend.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.WEB_Session;
import planspace.caseModel.CaseType;
import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefTreeContext;
import planspace.chefModelTypes.ChefType;
import planspace.chefModelTypes.MonitoredConcern;
import planspace.chefModelTypes.MonitoredConcernItem;
import planspace.chefModelTypes.MonitoringRegime;
import planspace.chefModelTypes.MonitoringRegime.t_monitoringFrequency;
import planspace.export.JsonExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.jsonExport.JsonPolicyItems;

public class MonitorConcernsEditor extends VerticalLayout {

	private TreeGrid<MonitoredConcernItem> caseTypeMonitoringGrid;
	private List<MonitoredConcernItem> monitoredAspectsOfCurrentCaseTypeBeingEdited;
	private CaseType caseTypeBeingEdited;

	public MonitorConcernsEditor() {
		super();
		createCaseTypeMonitoringAspectGrid();
	}

	public void editForCaseType(CaseType aCaseType) {
		this.caseTypeBeingEdited = aCaseType;
		monitoredAspectsOfCurrentCaseTypeBeingEdited = new ArrayList<MonitoredConcernItem>();
		this.loadMonitoredAspectsOfCaseType();
		this.showConcerns();
	}

	private void showConcerns() {
		if (this.monitoredAspectsOfCurrentCaseTypeBeingEdited != null) {
			this.caseTypeMonitoringGrid.setItems(this.monitoredAspectsOfCurrentCaseTypeBeingEdited);
		}
	}

	private void loadMonitoredAspectsOfCaseType() {
		MonitoredConcernItem aMonitoredConcernItem;
		MonitoredConcern aConcern;
		int i;

		monitoredAspectsOfCurrentCaseTypeBeingEdited.clear();
		for (i = 0; i < caseTypeBeingEdited.getMonitoredAspects().size(); i++) {
			aConcern = caseTypeBeingEdited.getMonitoredAspects().get(i);

			aMonitoredConcernItem = this.createMonitoredConcernItemForConcern(aConcern);
			aMonitoredConcernItem.setMonitoringCaseType(this.caseTypeBeingEdited);
			if (aMonitoredConcernItem != null) {
				this.monitoredAspectsOfCurrentCaseTypeBeingEdited.add(aMonitoredConcernItem);
			}
		}

	}

	private MonitoredConcernItem createMonitoredConcernItemForConcern(MonitoredConcern aConcern) {
		MonitoredConcernItem aMonitoredConcernItem;
		MonitoredConcern aMonitoredConcern;
		ChefType theChefType;
		String idOfChefType;
		InterfaceToDomainTypeRepository theIFC;

		aMonitoredConcernItem = null;
		idOfChefType = aConcern.getChefTypeID();
		theIFC = InterfaceToDomainTypeRepository.getInstance();
		if (theIFC != null) {
			theChefType = theIFC.findChefTypeByID(WEB_Session.getInstance().getTheDomain().getDomainCode(),
					idOfChefType);
			if (theChefType != null) {
				aMonitoredConcernItem = new MonitoredConcernItem();
				aMonitoredConcernItem.setTheConcernForChefType(aConcern, theChefType);

			}
		}
		return aMonitoredConcernItem;
	}

	private void createCaseTypeMonitoringAspectGrid() {
		String r = "30px";
		HorizontalLayout option;
		Label aText;
		Image img;

		this.caseTypeMonitoringGrid = new TreeGrid<MonitoredConcernItem>();

		caseTypeMonitoringGrid.addComponentHierarchyColumn(MonitoredConcernItem::getMyTreeGUIpresence)
				.setHeader("Bewaakt");
		caseTypeMonitoringGrid.setDropMode(GridDropMode.ON_GRID);

		// taxGrid.drag
		// taxGrid.setSizeFull();
		caseTypeMonitoringGrid.setMinWidth("550px");
		caseTypeMonitoringGrid.setHeight("500px");
		caseTypeMonitoringGrid.setVerticalScrollingEnabled(true);

		// Event start dragging

		GridContextMenu myContextMenu = caseTypeMonitoringGrid.addContextMenu();

		/*
		 * option = new HorizontalLayout(); img = new Image("/focus.png", "");
		 * img.setHeight(r); img.setWidth(r); aText = new Label("Focus");
		 * option.add(img, aText); myContextMenu.addItem(option, event -> {
		 * this.editDetailsTreeItem(); });
		 */
		option = new HorizontalLayout();
		img = new Image("/opensourcedoc.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Bron...");
		option.add(img, aText);

		myContextMenu.addItem(option, event -> {

			this.caseTypeMonitoringGrid.getSelectedItems().forEach(anItem -> {
				anItem.openReferenceDocuments();

			});

		});

		// Event drop dragging

		caseTypeMonitoringGrid.addDropListener(event -> {

			// Wat wordt er gedropped
			// itemTypeDropped = checkTypeOfItemDropped();
			WEB_Session.getInstance().getCurrentItemsDragged().forEach(anItem -> {
				MonitoredConcernItem newConcernItem;
				MonitoredConcern newConcern;
				MonitoringRegime regimeForNew;

				ChefTaxonomyItem aChefTaxItem;
				System.out.println("case TypeDropped " + anItem.getInformalName());
				// this.currentCaseTypeBeingEdited.getMonitoredAspects();

				aChefTaxItem = (ChefTaxonomyItem) anItem;

				newConcern = new MonitoredConcern();
				newConcern.setChefTypeID(aChefTaxItem.getTheChefType().get_id());
				newConcern.setChefTreeContextId(aChefTaxItem.getTreeContextData().get_id());
				regimeForNew = new MonitoringRegime();
				regimeForNew.setMonitoringFrequency(t_monitoringFrequency.ON_REQUEST);
				newConcern.setTheRegime(regimeForNew);
				newConcernItem = new MonitoredConcernItem();
				newConcernItem.setMonitoringCaseType(this.caseTypeBeingEdited);
				newConcernItem.setTheConcernForChefType(newConcern, aChefTaxItem.getTheChefType());

				this.monitoredAspectsOfCurrentCaseTypeBeingEdited.add(newConcernItem);
				this.caseTypeBeingEdited.addMonitoringConcern(newConcern);
			});
			this.caseTypeBeingEdited.updateYourself();
			this.showConcerns();

		});
		caseTypeMonitoringGrid.setDetailsVisibleOnClick(false);
		this.add(caseTypeMonitoringGrid);
	}

	public void exportMonitoredConcernsToJson(JsonExportFile anExport, int level, boolean recursive,
			JsonPolicyItems thePolicyItems) {
		int i;
		ChefTreeContext aChefTypeContext;
		MonitoredConcernItem aConcernItem;
		MonitoredConcern aConcern;
		ChefTreeContext aRoot;
		String fileName;
		String jsonItems;
		String contextDataId;

		for (i = 0; i < this.monitoredAspectsOfCurrentCaseTypeBeingEdited.size(); i++) {
			aConcernItem = this.monitoredAspectsOfCurrentCaseTypeBeingEdited.get(i);
			aConcern = aConcernItem.getTheConcern();
			aConcern.exportMonitoredConcernsToJson(anExport, level, recursive, thePolicyItems);

		}
		if (!thePolicyItems.isEmpty()) {

			for (i = 0; i < this.monitoredAspectsOfCurrentCaseTypeBeingEdited.size(); i++) {
				aConcernItem = this.monitoredAspectsOfCurrentCaseTypeBeingEdited.get(i);
				aConcern = aConcernItem.getTheConcern();
				contextDataId = aConcern.getChefTreeContextId();
				aChefTypeContext = InterfaceToDomainTypeRepository.getInstance().findChefTreeContextByID(
						WEB_Session.getInstance().getTheDomain().getDomainCode(), contextDataId);
				thePolicyItems.addPolicyTreeItemByChefTreeContext(aChefTypeContext);
			}

		}

	}

}
