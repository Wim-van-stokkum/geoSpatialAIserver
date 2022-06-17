package planspace.chefModelTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import application.frontend.WEB_Session;
import planspace.compliance.SourceReference;
import planspace.compliance.SourceReferenceData;
import planspace.export.CSVExportFile;
import planspace.export.JsonExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.jsonExport.JsonPolicyItems;
import planspace.jsonExport.JsonPolicyTreeItems;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_impact;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;
import planspace.utils.PlanSpaceLogger;

public class ChefTaxonomyItem {
	private String _id;
	private ChefType theChefObject;

	private List<ChefTaxonomyItem> myChildren;

	private HorizontalLayout myTreeGuiPresence;
	private ChefTaxonomyItem myParent;
	private boolean isCollapsed;
	private Image myTreeAvatarIMG;
	private String myColor;
	private HorizontalLayout myTreeGUIpresence;

	private String informalName;

	private t_impact impact;

	private ChefTreeContext myTreeContextData;
	private boolean removed;
	private TextField editName;
	private Label guiLabel;
	private VerticalLayout messageBody;

	public ChefTaxonomyItem() {
		this._id = UUID.randomUUID().toString();
		myChildren = new ArrayList<ChefTaxonomyItem>();
		isCollapsed = true;
		this.myTreeAvatarIMG = new Image("/icon_tree_OTHER.png", "?");
		this.myTreeAvatarIMG.setWidth("38px");
		this.myTreeAvatarIMG.setHeight("38px");
		this.theChefObject = null;
		this.myTreeContextData = new ChefTreeContext();
		this.setDescription("");
		this.impact = t_impact.NEUTRAL;
		this.removed = false;
	}

	public ChefTreeContext getTreeContextData() {
		return myTreeContextData;
	}

	public void setMyTreeContextData(ChefTreeContext myTreeContextData) {
		this.myTreeContextData = myTreeContextData;
	}

	public void updateTreeContextData() {
		int i;
		ChefTaxonomyItem aChild;
		if (this.theChefObject != null) {
			this.myTreeContextData.setDomainCode(this.theChefObject.getDomainCode());
			this.myTreeContextData.setRelatedChefObjectID(this.theChefObject.get_id());
			this.myTreeContextData.setThemeAsString(this.theChefObject.getThemeAsString());
			this.myTreeContextData.setImpact(this.impact);
			if (this.getParent() != null) {
				this.myTreeContextData.setParentID(this.getParent().getTreeContextDataID());
			} else {
				if (this.removed == false) {
					this.myTreeContextData.setParentID("");
				} else {
					this.myTreeContextData.setParentID("REMOVED");

				}
			}

			this.myTreeContextData.clearDirectChildTreeContext();
			for (i = 0; i < this.myChildren.size(); i++)

			{
				aChild = this.myChildren.get(i);
				this.myTreeContextData.addDirectChildTreeContext(aChild.getTreeContextDataID());
			}
			this.myTreeContextData.storeYourself();
		}
	}

	private String getTreeContextDataID() {
		String treeContextID;
		treeContextID = null;

		if (this.myTreeContextData != null) {
			treeContextID = this.myTreeContextData.get_id();
		}
		return treeContextID;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	protected void refreshAvatar() {

		// avatare picture
		if (this.getMyChefType() != null) {
			if (this.getMyChefType().equals(t_chefType.GOAL)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_GOAL.png", "DOEL");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.OPPORTUNITY)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_KANS.png", "KANS");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.RISK)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_RISK.png", "RISK");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.MEASUREMENT)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_ACT.png", "ACTIE");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.EVENT)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_EVENT.png", "EVENT");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.RULE)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_RULE.png", "RULE");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.FACT)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_FACT.png", "FACT");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.NORM)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_NORM.png", "NORM");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.DATA)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_DATA.png", "DATA");
				myColor = "black";
			} else if (this.getMyChefType().equals(t_chefType.DATASOURCE)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_DATASOURCE.png", "BRON");
				myColor = "black";
			}

			else if (this.getMyChefType().equals(t_chefType.UNKNOWN)) {

				this.myTreeAvatarIMG = new Image("/icon_tree_OTHER.png", "UNKNOWN");
				myColor = "gray";
			}

		} else {
			// no TAGS

			this.myTreeAvatarIMG = new Image("/icon_tree_OTHER.png", "ONBEKEND");
			myColor = "gray";
		}
		this.myTreeAvatarIMG.setWidth("38px");
		this.myTreeAvatarIMG.setHeight("38px");
	}

	public t_chefType getMyChefType() {
		t_chefType theType;

		theType = t_chefType.UNKNOWN;
		if (this.theChefObject != null) {
			theType = this.theChefObject.getType();
		}
		return theType;
	}

	public boolean isGoal() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.GOAL)) {
			isOfType = true;

		}

		return isOfType;
	}

	public boolean isRisk() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.RISK)) {
			isOfType = true;

		}

		return isOfType;
	}

	public boolean isOpportinity() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.OPPORTUNITY)) {
			isOfType = true;

		}

		return isOfType;
	}

	public boolean isMeasurement() {
		boolean isOfType;

		isOfType = false;
		if (this.getMyChefType().equals(t_chefType.MEASUREMENT)) {
			isOfType = true;

		}
		return isOfType;

	}

	public boolean isFact() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.FACT)) {
			isOfType = true;

		}

		return isOfType;
	}

	public boolean isRule() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.RULE)) {
			isOfType = true;

		}

		return isOfType;
	}

	public boolean isNorm() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.NORM)) {
			isOfType = true;

		}

		return isOfType;
	}

	public boolean isDATA() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.DATA)) {
			isOfType = true;

		}

		return isOfType;
	}

	public boolean isDATASOURCE() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.DATASOURCE)) {
			isOfType = true;

		}

		return isOfType;
	}

	public boolean isEvent() {
		boolean isOfType;

		isOfType = false;

		if (this.getMyChefType().equals(t_chefType.EVENT)) {
			isOfType = true;

		}
		return isOfType;
	}

	public HorizontalLayout updateMyTreeGuiPresence() {

		HorizontalLayout messageComponent;
		VerticalLayout butLine;
		Div theAvatar;
		Button openRefButton;
		Button moveUpButton;
		Button editButton;
		Button delButton;

		Icon icon;

		messageComponent = TreeMessageComponentFrame();
		if (messageComponent != null) {

			theAvatar = prepareTreeAvatar();
			if (theAvatar != null) {

				messageComponent.add(theAvatar);

			}
			messageBody = TreeReferenceInfoBody();
			if (messageBody != null) {
				messageComponent.add(messageBody);
			}

		}

		this.myTreeGUIpresence = messageComponent;

		/*
		 * this.myTreeDragSource = DragSource.create(myTreeGUIpresence);
		 * this.myTreeDragSource.addDragStartListener((DragStartEvent<HorizontalLayout>
		 * event) -> { System.out.println("Start dragging my tree gui"); });
		 * this.myTreeDragSource.addDragEndListener((DragEndEvent<HorizontalLayout>
		 * event) -> { System.out.println("Stop dragging my tree gui"); });
		 * this.myTreeDragSource.setDragData(this);
		 */

		return messageComponent;
	}

	public HorizontalLayout getMyTreeGUIpresence() {

		this.updateMyTreeGuiPresence();

		return this.myTreeGUIpresence;
	}

	protected Div prepareTreeAvatar() {
		Div avatar = new Div();

		this.refreshAvatar();
		if (avatar != null) {
			avatar.setWidth("40px");
			avatar.setHeight("26px");
			avatar.getStyle().set("margin", "8px");
			avatar.add(this.myTreeAvatarIMG);

		}
		return avatar;
	}

	protected HorizontalLayout TreeMessageComponentFrame() {
		String bordercolor;
		HorizontalLayout theFrame;

		theFrame = new HorizontalLayout();
		if (theFrame != null) {

			// bordercolor = "1px solid " + this.myColor;
			theFrame.setSpacing(false);
			// theFrame.getStyle().set("border", bordercolor);
			theFrame.setWidth("700px");
			/*
			 * theFrame.addClickListener( e -> System.out.println(this.getUrl()) //maak div
			 * voor mij );
			 */
		}
		return theFrame;
	}

	protected VerticalLayout TreeReferenceInfoBody() {
		// will create a text message with name of initator
		Div name = new Div();

		VerticalLayout messageBody;

		messageBody = new VerticalLayout();
		if (this.getInformalName() != null) {
			// tagname
			this.editName = new TextField();
			this.guiLabel = new Label(this.getInformalName());
			name.add(guiLabel);
			name.add(editName);
			editName.setVisible(false);
			this.guiLabel.setVisible(true);
			editName.addValueChangeListener(listener -> {
				String newInformalName;
				newInformalName = this.editName.getValue();
				System.out.println("Edit ->  " + newInformalName);
				this.setInformalName(newInformalName);
				guiLabel.setText(newInformalName);
				this.guiLabel.setVisible(true);
				editName.setVisible(false);
				this.storeYourself();
			});
			name.getStyle().set("color", this.myColor);
			messageBody.add(name);
		}

		return messageBody;
	}

	public String getInformalName() {
		informalName = this.getTreeName();
		if (informalName == null) {
			informalName = "<onbekend>";
		}
		return informalName;
	}

	public void setInformalName(String informalName) {
		if (this.theChefObject != null) {
			this.theChefObject.setName(informalName);
		}
		this.informalName = informalName;
	}

	public ChefType getTheChefType() {
		return theChefObject;
	}

	public void setTheChefType(ChefType theChefType) {
		this.theChefObject = theChefType;

	}

	public List<ChefTaxonomyItem> getMyChildren() {
		return myChildren;
	}

	public void setMyChildren(List<ChefTaxonomyItem> myChildren) {
		this.myChildren = myChildren;

	}

	public String getTreeName() {
		String s;

		s = "<onbekend>";
		if (this.theChefObject != null) {
			s = this.theChefObject.getName();
			if (s == null) {
				if (this.theChefObject.getSourceReferences() != null) {
					if (this.theChefObject.getSourceReferences().size() > 0) {
						s = this.theChefObject.getSourceReferences().get(0).getInformalName();
						this.theChefObject.setName(s);
						this.theChefObject.storeYourself();
					}
				}
			}
		}

		return s;
	}

	public String getTreeDescription() {
		String s;

		s = "";
		if (this.theChefObject != null) {
			s = this.theChefObject.getDescription();
		}

		return s;
	}

	public String getTreeType() {
		String s;

		s = "beleidsnotitie";
		if (this.theChefObject != null) {
			s = this.theChefObject.getTypeName(t_language.NL);
		}

		return s;
	}

	public void removeAChild(ChefTaxonomyItem anChildItemToDelete) {

		if (this.myChildren.contains(anChildItemToDelete)) {
			this.myChildren.remove(anChildItemToDelete);
			anChildItemToDelete.setParent(null);

		}
	}

	public void addAChild(ChefTaxonomyItem anewChildItem) {
		if (!this.myChildren.contains(anewChildItem)) {
			this.myChildren.add(anewChildItem);
			anewChildItem.setParent(this);

		}
	}

	public void addAChildAsFirst(ChefTaxonomyItem anewChildItem) {
		if (!this.myChildren.contains(anewChildItem)) {
			this.myChildren.add(0, anewChildItem);
			anewChildItem.setParent(this);

		}
	}

	public void setParent(ChefTaxonomyItem myNewParent) {
		this.myParent = myNewParent;

	}

	public ChefTaxonomyItem getParent() {
		return this.myParent;
	}

	public HorizontalLayout getAvatar() {

		myTreeGuiPresence = this.getMyTreeGUIpresence();
		/*
		 * this.myTreeDragSource = DragSource.create(myTreeGuiPresence);
		 * 
		 * this.myTreeDragSource.addDragStartListener((DragStartEvent<HorizontalLayout>
		 * event) -> { System.out.println("Start dragging my tree thing"); });
		 * this.myTreeDragSource.addDragEndListener((DragEndEvent<HorizontalLayout>
		 * event) -> { System.out.println("Stop dragging my tree thing"); });
		 * this.myTreeDragSource.setDragData(this);
		 * 
		 */

		return myTreeGuiPresence;
	}

	public boolean addToTargetChildAbove(ChefTaxonomyItem newItem, ChefTaxonomyItem myTargetTreeItem) {
		boolean found = false;

		int loc;
		int i;

		loc = getMyChildren().indexOf(myTargetTreeItem);
		if (loc != -1) {
			found = true;
			this.getMyChildren().add(loc, newItem);
			newItem.setParent(this);
			this.storeYourself();

		} else {
			for (i = 0; i < getMyChildren().size(); i++) {
				found = getMyChildren().get(i).addToTargetChildAbove(newItem, myTargetTreeItem);
				if (found) {
					break;
				}

			}
		}
		return found;
	}

	public void notifyCollapse(boolean iBecomeCollapsed) {
		this.isCollapsed = iBecomeCollapsed;
		// System.out.println("item " + this.getTreeDescription() + " collapsed " +
		// this.isCollapsed);
	}

	public boolean addToTargetChildBelow(ChefTaxonomyItem newItem, ChefTaxonomyItem myTargetTreeItem) {
		boolean found = false;

		int loc;
		int i;

		loc = getMyChildren().indexOf(myTargetTreeItem);
		if (loc != -1) {
			found = true;
			this.getMyChildren().add(loc + 1, newItem);
			newItem.setParent(this);
			this.storeYourself();
		} else {
			for (i = 0; i < getMyChildren().size(); i++) {
				found = getMyChildren().get(i).addToTargetChildBelow(newItem, myTargetTreeItem);
				if (found) {
					break;
				}

			}
		}
		return found;
	}

	public boolean itemIsCollapsed() {
		return this.isCollapsed;
	}

	public boolean itemIsExpanded() {
		return !this.isCollapsed;
	}

	public boolean hasChildren() {
		boolean childs = false;
		if (this.getMyChildren() != null) {
			if (this.getMyChildren().size() > 0) {
				childs = true;
			}
		}
		return childs;
	}

	public void addItemsToOrderedList(List<ChefTaxonomyItem> treeAsList) {
		int i;

		treeAsList.add(this);
		for (i = 0; i < this.getMyChildren().size(); i++) {
			this.getMyChildren().get(i).addItemsToOrderedList(treeAsList);
		}

	}

	public boolean isItemPartOfTree(ChefTaxonomyItem theItemToFocus, List<ChefTaxonomyItem> path) {
		boolean isOrUnder = false;
		int i;

		if (theItemToFocus == this) {
			isOrUnder = true;
			path.add(0, this);
		} else {
			for (i = 0; i < this.getMyChildren().size(); i++) {
				isOrUnder = this.getMyChildren().get(i).isItemPartOfTree(theItemToFocus, path);
				if (isOrUnder) {
					path.add(0, this);
					break;
				}
			}
		}
		return isOrUnder;
	}

	public boolean hasItemAsDirectChild(ChefTaxonomyItem theItem) {

		return this.myChildren.contains(theItem);
	}

	public boolean hasItemPartOfTree(ChefTaxonomyItem theItemToFocus) {
		boolean isOrUnder = false;
		int i;

		if (theItemToFocus == this) {
			isOrUnder = true;

		} else {
			for (i = 0; i < this.getMyChildren().size(); i++) {
				isOrUnder = this.getMyChildren().get(i).hasItemPartOfTree(theItemToFocus);
				if (isOrUnder) {
					break;
				}
			}
		}
		return isOrUnder;
	}

	public void restoreCollapsedView(List<ChefTaxonomyItem> path) {

		int i;

		if (this.itemIsExpanded()) {
			path.add(this);
		}

		for (i = 0; i < this.getMyChildren().size(); i++) {
			this.getMyChildren().get(i).restoreCollapsedView(path);
		}

	}

	public String getThemeAsString() {
		String s;
		s = "Nader te bepalen";
		if (this.theChefObject != null) {
			s = this.theChefObject.getThemeAsString();
		}
		return s;
	}

	public void setThemeAsString(String s) {
		if (this.theChefObject != null) {
			this.theChefObject.setThemeAsString(s);
		}

	}

	public boolean hasSourceReference() {
		boolean hasRef;
		hasRef = false;
		if (this.getTheChefType() != null) {
			hasRef = this.getTheChefType().hasSourceReference();
		} else {
			hasRef = false;
		}
		return hasRef;
	}

	public String getTypeNameAsString(t_language lang) {
		String typeNameAsString;
		typeNameAsString = "";
		if (this.theChefObject != null) {
			typeNameAsString = this.theChefObject.getTypeName(lang);
		}
		return typeNameAsString;
	}

	public void setTypeByString(t_language lang, String theTypeName) {
		if (this.theChefObject != null) {
			this.theChefObject.setTypeByString(lang, theTypeName);
		}

	}

	public String getGoalTypeAsString(t_language lang) {

		String t;

		t = "Onbekend";

		if (this.theChefObject != null) {
			t = this.theChefObject.getGoalTypeAsString(lang);
		}

		return t;
	}

	public String getImpactAsString(t_language lang) {

		String t;

		t = "Onbekend";

		if (this.impact != null) {
			if (lang.equals(t_language.NL)) {
				if (this.impact.equals(t_impact.NEUTRAL)) {
					t = "Neutraal";
				} else if (this.impact.equals(t_impact.LOW)) {
					t = "Laag";
				} else if (this.impact.equals(t_impact.MEDIUM)) {
					t = "Midden";
				} else if (this.impact.equals(t_impact.HIGH)) {
					t = "Hoog";
				} else if (this.impact.equals(t_impact.UNACCEPTABLE)) {
					t = "Onacceptabel";
				}

			}
		}
		return t;
	}

	public void setGoalTypeAsString(t_language lang, String aTypeName) {

		if (this.theChefObject != null) {
			this.theChefObject.setGoalTypeAsString(lang, aTypeName);
		}

	}

	public void setImpactAsString(t_language lang, String aTypeName) {

		t_impact t;

		System.out.println("SET IMPACT " + aTypeName);

		t = t_impact.UNKNOWN;
		if (lang.equals(t_language.NL)) {
			if (aTypeName.equals("Neutraal")) {
				t = t_impact.NEUTRAL;
			} else if (aTypeName.equals("Laag")) {
				t = t_impact.LOW;
			} else if (aTypeName.equals("Midden")) {
				t = t_impact.MEDIUM;
			} else if (aTypeName.equals("Hoog")) {

				t = t_impact.HIGH;
			} else if (aTypeName.equals("Onacceptabel")) {
				t = t_impact.UNACCEPTABLE;
			}

		}
		this.impact = t;

	}

	public void setDescription(String value) {
		if (this.theChefObject != null) {
			this.theChefObject.setDescription(value);
		}

	}

	public void openReferencedDocument() {

		if (this.getTheChefType() != null) {
			this.theChefObject.openReferenceDocuments();

		}

	}

	public void storeYourself() {
		if (this.getTheChefType() != null) {
			this.getTheChefType().storeYourself();
		}

		if (this.getTreeContextData() != null) {
			this.updateTreeContextData();
		}

	}

	public void setUpByChefTreeContext(ChefTreeContext aTreeContext, boolean recursive) {
		String domainCode;
		InterfaceToDomainTypeRepository theIFC;
		ChefType theObject;
		SourceReferenceData theRefData;
		SourceReference theRef;
		int i;
		ChefTreeContext aChild;
		ChefTaxonomyItem aTaxItemForChild;

		String objectID, sourceID, childID;

		domainCode = aTreeContext.getDomainCode();

		theIFC = InterfaceToDomainTypeRepository.getInstance();
		this.setMyTreeContextData(aTreeContext);

		if (recursive) {
			// Load children recursively

			for (i = 0; i < aTreeContext.getDirectChildTreeContexts().size(); i++) {
				childID = aTreeContext.getDirectChildTreeContexts().get(i);
				aChild = theIFC.findChefTreeContextByID(domainCode, childID);
				if (aChild != null) {
					aTaxItemForChild = new ChefTaxonomyItem();
					aTaxItemForChild.setUpByChefTreeContext(aChild, recursive);
					this.addAChild(aTaxItemForChild);

				} else {
					PlanSpaceLogger.getInstance().log_Error("Child lost: " + childID);
				}
			}

		}
		this.setThemeAsString(aTreeContext.getThemeAsString());

		// Load the related object
		objectID = aTreeContext.getRelatedChefObjectID();
		impact = aTreeContext.getImpact();
		theObject = theIFC.findChefTypeByID(domainCode, objectID);
		if (theObject != null) {
			this.setTheChefType(theObject);
		} else {
			PlanSpaceLogger.getInstance().log_Error("ChefType lost: " + objectID);
		}

		// Load the source reference
		sourceID = aTreeContext.getSourceReferenceID();
		theRefData = theIFC.findSourceReferenceByID(sourceID);

	}

	public void exportYourSelfToCSV(CSVExportFile anExport, int level, boolean recursive) {
		int i, j;

		ChefTaxonomyItem aChild;

		level++;

		// Filter header
		anExport.writeCSVitem(String.valueOf(level));
		anExport.writeCSVitem(this.getTreeType());
		anExport.writeCSVitem(this.getGoalTypeAsString(t_language.NL));
		anExport.writeCSVitem(this.getInformalName());
		anExport.writeCSVitem(this.getThemeAsString());
		anExport.writeCSVitem(this.getImpactAsString(t_language.NL));

		// ids
		anExport.writeCSVitem(this.get_id());

		if (this.theChefObject != null) {
			anExport.writeCSVitem(this.theChefObject.get_id());

		} else {
			anExport.writeEmptyCSVitem();
		}

		// level
		/*
		 * for (i = 0; i < (level - 1); i++) { anExport.writeEmptyCSVitem(); }
		 * 
		 */

		// Source info
		anExport.writeEmptyCSVitem();
		this.theChefObject.exportReferencesToCSV(anExport);

		// Afsluiten
		anExport.write("\n");

		// Write children
		if (recursive) {
			if (this.theChefObject != null) {
				for (j = 0; j < this.myChildren.size(); j++) {
					aChild = this.myChildren.get(j);
					aChild.exportYourSelfToCSV(anExport, level, recursive);
				}

			}
		}

	}

	public void moveChildrenTo(ChefTaxonomyItem newParent) {
		int j;
		ChefTaxonomyItem aChild;

		for (j = 0; j < this.myChildren.size(); j++) {
			aChild = this.myChildren.get(j);
			if (newParent != null) {
				newParent.addAChild(aChild);
				newParent.storeYourself();
			} else {
				WEB_Session.getInstance().notifyTaxGridEditor("toRoot", aChild);
			}
			aChild.storeYourself();

			this.myChildren.clear();
		}

	}

	public void setRemoved(boolean b) {
		this.removed = b;

	}

	public void filterUsedSourceReferences() {
		int j;
		ChefTaxonomyItem aChild;

		if (this.hasSourceReference()) {
			WEB_Session.getInstance().notifyPolicyReferenceEditor("SourceReferenceInUse", this);

		}

		for (j = 0; j < this.myChildren.size(); j++) {
			aChild = this.myChildren.get(j);
			aChild.filterUsedSourceReferences();

		}

	}

	public void restoreByName() {
		int j;
		ChefTaxonomyItem aChild;

		WEB_Session.getInstance().notifyPolicyReferenceEditor("RestoreByName", this);

		for (j = 0; j < this.myChildren.size(); j++) {
			aChild = this.myChildren.get(j);
			aChild.restoreByName();

		}

	}

	public boolean exportDataContext(CSVExportFile anExport, int level, boolean recursive) {
		int i, j;

		ChefTaxonomyItem aChild;
		boolean dataFoundAtLeaf;

		// if I am the leaf ... else childres.
		System.out.println("DATA EXPORT: " + this.getInformalName() + "  " + this.myChildren.size());
		dataFoundAtLeaf = false;
		if (this.myChildren.size() == 0) {
			if (this.getMyChefType().equals(t_chefType.DATASOURCE) || this.getMyChefType().equals(t_chefType.DATA)) {

				dataFoundAtLeaf = true;

			}
			if (!dataFoundAtLeaf) {
				writeDataContext(anExport, level, recursive);
			}
			System.out.println("LEAF: " + this.getInformalName() + "   " + this.getTypeNameAsString(t_language.NL));

		} else {

			// search leaves

			if (this.theChefObject != null) {
				for (j = 0; j < this.myChildren.size(); j++) {
					aChild = this.myChildren.get(j);
					dataFoundAtLeaf = aChild.exportDataContext(anExport, level, recursive);
					if (!dataFoundAtLeaf) {
						writeDataContext(anExport, level, recursive);
					}
				}

			}

		}
		return dataFoundAtLeaf;
	}

	public void exportItemsToJson(JsonExportFile anExport, int level, boolean recursive,
			JsonPolicyItems thePolicyItems) {
		int j;

		ChefTaxonomyItem aChild;

		if (this.theChefObject != null) {
			thePolicyItems.addPolicyItemByChefType(this.theChefObject);
			for (j = 0; j < this.myChildren.size(); j++) {
				aChild = this.myChildren.get(j);
				aChild.exportItemsToJson(anExport, level, recursive, thePolicyItems);
			}

		}

	}

	public void exportTreeToJson(JsonExportFile anExport, int level, boolean recursive,
			JsonPolicyItems thePolicyItems) {

		int j;

		ChefTaxonomyItem aChild;

		thePolicyItems.addPolicyTreeItemByChefTaxItem(this);
		for (j = 0; j < this.myChildren.size(); j++) {
			aChild = this.myChildren.get(j);
			aChild.exportTreeToJson(anExport, level, recursive, thePolicyItems);
		}

	}

	private void writeDataContext(CSVExportFile anExport, int level, boolean recursive) {

		if (this.getMyChefType().equals(t_chefType.DATASOURCE)) {
			// Data is above me

			anExport.writeln("\nDATA SOURCE: " + this.getInformalName() + "\n");
		} else

		if (this.getMyChefType().equals(t_chefType.DATA)) {
			// Data is above me

			anExport.writeln("");
			anExport.writeln("***************  DATA " + this.getInformalName() + " ******************");
			anExport.writeln(this.informalName);
			anExport.writeln("Gegeven: " + this.getInformalName());
			anExport.writeln("");
			anExport.writeln("Gebruikt voor doel context");
			anExport.writeln("");

		} else {

			// Data is under me

			anExport.writeln("");
			anExport.writeln("");
			anExport.writeln("--  " + this.getTypeNameAsString(t_language.NL) + "  " + this.getInformalName() + " --");
			anExport.writeln(this.informalName);
			anExport.writeln(this.getTypeNameAsString(t_language.NL) + " : " + this.getInformalName());
			anExport.writeln("");
			anExport.writeln(this.theChefObject.getDescription());
			anExport.writeln("");

		}

	}

	public void removeYourself() {
		if (this.myChildren != null) {
			this.myChildren.forEach(aChild -> {
				aChild.removeYourself();
			});
		}

		if (this.theChefObject != null) {
			theChefObject.removeYourself();
		}
		if (this.myTreeContextData != null) {
			this.myTreeContextData.removeYourself();
		}

	}

	public ChefTaxonomyItem findFirstItemOfTypes(List<t_chefType> Perspectives) {
		ChefTaxonomyItem foundTaxNode;
		int i;
		ChefTaxonomyItem aChild;

		foundTaxNode = null;
		if (this.getTheChefType() != null) {
			if (Perspectives.contains(this.getTheChefType().getType())) {
				foundTaxNode = this;
			} else {
				for (i = 0; i < this.getMyChildren().size(); i++) {
					aChild = this.getMyChildren().get(i);
					foundTaxNode = aChild.findFirstItemOfTypes(Perspectives);
					if (foundTaxNode != null) {
						break;
					}
				}
			}
		}
		return foundTaxNode;
	}

	public List<ChefTaxonomyItem> findAllDirectChildItemOfTypes(List<t_chefType> Perspectives) {
		List<ChefTaxonomyItem> foundTaxNodes;
		int i;
		ChefTaxonomyItem aChild;
		List<ChefTaxonomyItem> FoundDirectChildren;

		foundTaxNodes = new ArrayList<ChefTaxonomyItem>();
		for (i = 0; i < this.getMyChildren().size(); i++) {
			aChild = this.getMyChildren().get(i);
			if (Perspectives.contains(aChild.getMyChefType())) {
				foundTaxNodes.add(aChild);
			} else {
				FoundDirectChildren = aChild.findAllDirectChildItemOfTypes(Perspectives);
				if (FoundDirectChildren != null) {
					FoundDirectChildren.forEach(achildOfChild -> {
						foundTaxNodes.add(achildOfChild);
					});
				}
			}
		}

		return foundTaxNodes;
	}

	public void editYourName() {

		if (this.getInformalName().equals("<nieuw beleidsobject>")) {
			editName.setValue("");
		} else {

			editName.setValue(this.getInformalName());
		}
		editName.setVisible(true);
		guiLabel.setVisible(false);
		editName.focus();
	}

}
