package application.frontend.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.IFC_DetailEditListener;
import application.frontend.WEB_Session;
import planspace.caseModel.CaseType;
import planspace.context.ContextDefinition;
import planspace.export.JsonExportFile;
import planspace.jsonExport.JsonPolicyItems;
import planspace.utils.PlanSpaceLogger;

public class CaseTypeEditor extends HorizontalLayout implements IFC_DetailEditListener {

	private static final long serialVersionUID = 1L;

	private List<CaseType> theCaseTypes;
	private TreeGrid<CaseType> theCaseTypeGrids;
	private VerticalLayout editCaseTypeDialog;
	private VerticalLayout editCaseTypeContextDialog;
	private CaseType currentCaseTypeBeingEdited;
	private TextField textCaseTypeDescription;
	private TextField textCaseTypeCode;

	private MonitorConcernsEditor caseTypeMonitoringEditor;
	private CaseTypeContextEditor myCaseTypeContextEditor;
	
	private IFC_DetailEditListener parentEditor;

	public CaseTypeEditor(IFC_DetailEditListener theParentEditor) {
		super();
		configureCaseTypePane();
		parentEditor = theParentEditor;
	}

	private void configureCaseTypePane() {

		this.createCaseTypeSection();
		this.createCaseTypeSelectionGrid();
		this.createCaseTypeEditDialog();
		this.createCaseTypeContextDialog();

		this.add(theCaseTypeGrids, editCaseTypeDialog, editCaseTypeContextDialog);

	}

	private void createCaseTypeContextDialog() {
		
		
		this.editCaseTypeContextDialog = new VerticalLayout();
		this.myCaseTypeContextEditor = new	CaseTypeContextEditor(this);
		Button closeButton = createCloseContextWindowBut();
	
		
		editCaseTypeContextDialog.add(myCaseTypeContextEditor,closeButton);
		this.editCaseTypeContextDialog.setVisible(false);
	}

	public void prepareOpening() {
		if (WEB_Session.getInstance().getTheDomain() != null) {
			this.loadCaseTypesForDomain();
		
		}
	}
	
	private Button createExportJsonButton() {
		Image icon;
		String r = "35px";

		icon = new Image("/exportjson.png", "json export");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Exporteer projectopgaven naar Json");

		// Set action
		addBut.addClickListener(e -> {
			this.generateExportToJson();
			// this.restoreByName();
		});

		return addBut;
	}
	

	private void generateExportToJson() {
		JsonExportFile anExport;
		int level;


		boolean recursive;
		String fileName;	
		JsonPolicyItems thePolicyItems;
	

		level = 0;
		recursive = true;
		fileName = createFileNameForJson();
		anExport = new JsonExportFile(fileName);
		

		thePolicyItems = new JsonPolicyItems();
		this.caseTypeMonitoringEditor.exportMonitoredConcernsToJson(anExport, level, recursive, thePolicyItems );
	

		anExport.writeln(thePolicyItems.toJson());

		this.notifyUser("JSON export aangemaakt: " + fileName);
		anExport.closeTextFile();
	}
	
	private String createFileNameForJson() {
		String s;

		s = "Project_JSON_export_project_" + this.currentCaseTypeBeingEdited.getCaseTypeCode();

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


	private void loadCaseTypesForDomain() {
		this.theCaseTypes = WEB_Session.getInstance().loadCaseTypesForDomain();
		this.showCaseTypes();
	}

	private void createCaseTypeSection() {
		this.setMinWidth("1200px");
		this.setSpacing(true);
		this.setHeight("990px");
	}

	private void createCaseTypeSelectionGrid() {
		HorizontalLayout option;
		Image img;
		Label aText;
		String r = "30px";

		theCaseTypeGrids = new TreeGrid<CaseType>();
		theCaseTypeGrids.addComponentHierarchyColumn(CaseType::getGuiLabel).setHeader("Project");
		theCaseTypeGrids.addColumn(CaseType::getCaseTypeCode).setHeader("Dossier code");
		theCaseTypeGrids.setRowsDraggable(false);
		theCaseTypeGrids.setMaxHeight("400px");
		theCaseTypeGrids.setMinWidth("500px");
		theCaseTypeGrids.setVerticalScrollingEnabled(true);

		GridContextMenu myContextMenu = theCaseTypeGrids.addContextMenu();

		option = new HorizontalLayout();
		img = new Image("/add.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Toevoegen");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			this.addCaseType();
		});

		option = new HorizontalLayout();
		img = new Image("/edit.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Eigenschappen");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			this.editCaseType();
		});

		option = new HorizontalLayout();
		img = new Image("/icon_CONTEXT.png", "");
		img.setHeight(r);
		img.setWidth(r);
		
		aText = new Label("Context");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			this.editContextForCaseType();
		});

		option = new HorizontalLayout();
		img = new Image("/delete.png", "");
		img.setHeight(r);
		img.setWidth(r);
		aText = new Label("Verwijder");
		option.add(img, aText);
		myContextMenu.addItem(option, event -> {
			this.deleteCaseType();
		});

	}

	private void editContextForCaseType() {
		
		if (this.theCaseTypeGrids.getSelectedItems().size() == 1) {
			this.theCaseTypeGrids.getSelectedItems().forEach(anItem -> {
				this.currentCaseTypeBeingEdited = anItem;
				
				this.openContextEditor();
			});

		} else {
			notifyUser("Geen zaaktype geselecteerd");
			this.currentCaseTypeBeingEdited = null;
			this.editCaseTypeDialog.setVisible(false);

		}


	}



	private void createCaseTypeEditDialog() {

		this.editCaseTypeDialog = new VerticalLayout();
		editCaseTypeDialog.setMinWidth("1200px");
		editCaseTypeDialog.setSpacing(true);
		editCaseTypeDialog.setHeight("850px");

		this.textCaseTypeDescription = new TextField("Omschrijving");
		this.textCaseTypeDescription.addValueChangeListener(listener -> {
			editCaseTypeDescription();
		});
		textCaseTypeDescription.setMinWidth("600px");
		this.textCaseTypeCode = new TextField("Technische naam");
		textCaseTypeCode.setMinWidth("600px");
		this.textCaseTypeCode.addValueChangeListener(listener -> {
			editCaseTypeCode();
		});

		this.caseTypeMonitoringEditor = new MonitorConcernsEditor();

		Button closeButton = createClosePropertyWindowBut();
		Button exportJsonButton = createExportJsonButton();
		HorizontalLayout butLine = new HorizontalLayout();
		butLine.add(closeButton, exportJsonButton);
		
		
		editCaseTypeDialog.add(textCaseTypeDescription, textCaseTypeCode, caseTypeMonitoringEditor, butLine);
		editCaseTypeDialog.setVisible(false);

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
			this.currentCaseTypeBeingEdited = null;
			this.editCaseTypeDialog.setVisible(false);
			if (editCaseTypeContextDialog.isVisible()) {
				this.editCaseTypeContextDialog.setVisible(false);
			}
			this.parentEditor.ConsumeDetailEditorClosed();
			this.theCaseTypeGrids.setVisible(true);
			this.showCaseTypes();

		});

		return addBut;
	}
	

	private Button createCloseContextWindowBut() {
		Image icon;
		String r = "35px";

		icon = new Image("/closeeditor.png", "close");
		icon.setHeight(r);
		icon.setWidth(r);

		Button addBut;

		addBut = new Button("", icon);
		addBut.getElement().setAttribute("title", "Sluit context");

		// Set action
		addBut.addClickListener(e -> {
			this.editCaseTypeContextDialog.setVisible(false);
			
			if (!editCaseTypeDialog.isVisible()) {
				notifyParent();
				parentEditor.ConsumeDetailEditorOpened();
				this.theCaseTypeGrids.setVisible(true);
				this.showCaseTypes();
				
			}
			this.parentEditor.ConsumeDetailEditorClosed();
		
		});

		return addBut;
	}

	private void notifyParent() {
		if (this.parentEditor != null) {
			this.parentEditor.ConsumeEditorEvent("Context_Changed");
		}
	}
	

	private void deleteCaseType() {
		if (this.theCaseTypeGrids.getSelectedItems().size() > 0) {
			this.theCaseTypeGrids.getSelectedItems().forEach(anItem -> {

				this.currentCaseTypeBeingEdited = null;
				this.editCaseTypeDialog.setVisible(false);
				this.theCaseTypeGrids.setVisible(true);
				this.theCaseTypes.remove(anItem);
				anItem.deleteYourself();
				this.showCaseTypes();
				notifyUser("Zaaktype: " + anItem.getDescription() + " is verwijderd");
			});

		} else {
			notifyUser("Geen zaaktype geselecteerd");

		}

	}

	private void showCaseTypes() {
		theCaseTypeGrids.setItems(theCaseTypes);
	}

	private void editCaseTypeCode() {
		if (this.currentCaseTypeBeingEdited != null) {
			this.currentCaseTypeBeingEdited.setCaseTypeCode(textCaseTypeCode.getValue());
			this.showCaseTypes();
			currentCaseTypeBeingEdited.updateYourself();

		}

	}

	private void editCaseTypeDescription() {
		if (this.currentCaseTypeBeingEdited != null) {
			this.currentCaseTypeBeingEdited.setDescription(textCaseTypeDescription.getValue());
			this.showCaseTypes();
			currentCaseTypeBeingEdited.updateYourself();

		}

	}

	private void addCaseType() {
		CaseType newCaseType;
		String domCode;
		newCaseType = new CaseType();
		int seqNR;

		
		parentEditor.ConsumeDetailEditorOpened();
		domCode = WEB_Session.getInstance().getTheDomain().getDomainCode();
		seqNR = this.theCaseTypes.size() + 1;
		newCaseType.setDomainCode(domCode);
		newCaseType.setDescription("Nieuw zaaktype (" + String.valueOf(seqNR) + ")");
		notifyUser("Zaaktype toegevoegd");
		newCaseType.setCaseTypeCode("ZaakType_" + domCode + String.valueOf(seqNR));
		newCaseType.addYourself();
		this.theCaseTypes.add(newCaseType);
		this.currentCaseTypeBeingEdited = newCaseType;
		this.showCaseTypes();
		this.openCaseTypeEditor();

	}

	private void openCaseTypeEditor() {
		
		if (this.currentCaseTypeBeingEdited != null) {
			
			parentEditor.ConsumeDetailEditorOpened();
			this.caseTypeMonitoringEditor.editForCaseType(this.currentCaseTypeBeingEdited);
			this.textCaseTypeDescription.setValue(currentCaseTypeBeingEdited.getDescription());
			this.textCaseTypeCode.setValue(currentCaseTypeBeingEdited.getCaseTypeCode());
			this.textCaseTypeDescription.setAutoselect(true);
			this.textCaseTypeDescription.focus();
			this.theCaseTypeGrids.setVisible(false);
			this.editCaseTypeDialog.setVisible(true);
		}

	}
	
	
	private void openContextEditor() {
		if (this.currentCaseTypeBeingEdited != null) {
			parentEditor.ConsumeDetailEditorOpened();
			this.myCaseTypeContextEditor.prepareForEditingCaseType(this.currentCaseTypeBeingEdited);

			this.theCaseTypeGrids.setVisible(false);
			this.editCaseTypeContextDialog.setVisible(true);
		}
		
	}

	private void notifyUser(String aNote) {

		Notification notification = new Notification();
		notification.show(aNote, 1500, Notification.Position.BOTTOM_CENTER);

	}

	private void editCaseType() {
		if (this.theCaseTypeGrids.getSelectedItems().size() ==1) {
			this.theCaseTypeGrids.getSelectedItems().forEach(anItem -> {
				this.currentCaseTypeBeingEdited = anItem;
				this.openCaseTypeEditor();
			});

		} else {
			notifyUser("Geen zaaktype geselecteerd");
			this.currentCaseTypeBeingEdited = null;
			this.editCaseTypeDialog.setVisible(false);

		}
	}
	
	public void ConsumeDetailEditorOpened() {
		
	}

	public void ConsumeDetailEditorClosed() {
		
	}
	
	public void ConsumeEditorEvent(String editorEvent) {
		ContextDefinition anECC;
		
		if (editorEvent.equals("Context_Changed")) {
			anECC = this.myCaseTypeContextEditor.assembleContextDefinition();
			currentCaseTypeBeingEdited.setContextDefinition(anECC);
			currentCaseTypeBeingEdited.updateYourself();
			PlanSpaceLogger.getInstance().log("CONTEXT DEFINITION Created : " + anECC.toJson() );
		}	
	}

}
