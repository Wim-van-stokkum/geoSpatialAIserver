package planspace.compliance;

import java.awt.Desktop;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import application.frontend.WEB_Session;
import planspace.export.CSVExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_annotationType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_sourceType;

public class SourceReference {

	private SourceReferenceData mySourceReferenceData;

	private HorizontalLayout myGuiPresence;

	protected Image myAvatarIMG;
	protected String myColor;
	protected DragSource<HorizontalLayout> myDragSource;
	private boolean isUsed;

	private Button openRefButton;

	public SourceReference(SourceReferenceData myData) {
		mySourceReferenceData = myData;
	
		this.myAvatarIMG = new Image("/icon_OTHER.png", "?");
		this.myAvatarIMG.setHeight("65px");
		this.myAvatarIMG.setWidth("65px");
		myColor = "black";
		this.isUsed = false;

	}

	public boolean isUsed() {
		return isUsed;
	}

	public SourceReferenceData getMySourceReferenceData() {
		return mySourceReferenceData;
	}

	public void setMySourceReferenceData(SourceReferenceData mySourceReferenceData) {
		this.mySourceReferenceData = mySourceReferenceData;
	}

	public HorizontalLayout getMeAsDragableReference() {

		this.updateMyGuiPresence();

		return this.myGuiPresence;
	}

	public boolean isGoal() {
		boolean isOfType;
		isOfType = false;
		if (this.mySourceReferenceData != null) {
			isOfType = mySourceReferenceData.isGoal();
		}
		return isOfType;
	}

	public boolean isRisk() {
		boolean isOfType;
		isOfType = false;
		if (this.mySourceReferenceData != null) {
			isOfType = mySourceReferenceData.isRisk();
		}
		return isOfType;
	}

	public boolean isOpportinity() {
		boolean isOfType;
		isOfType = false;
		if (this.mySourceReferenceData != null) {
			isOfType = mySourceReferenceData.isOpportinity();
		}
		return isOfType;
	}

	public boolean isMeasurement() {
		boolean isOfType;
		isOfType = false;
		if (this.mySourceReferenceData != null) {
			isOfType = mySourceReferenceData.isMeasurement();
		}
		return isOfType;
	}

	public boolean isFact() {
		boolean isOfType;
		isOfType = false;
		if (this.mySourceReferenceData != null) {
			isOfType = mySourceReferenceData.isFact();
		}
		return isOfType;
	}

	public boolean isRule() {
		boolean isOfType;
		isOfType = false;
		if (this.mySourceReferenceData != null) {
			isOfType = mySourceReferenceData.isRule();
		}
		return isOfType;
	}

	public boolean isEvent() {
		boolean isOfType;
		isOfType = false;
		if (this.mySourceReferenceData != null) {
			isOfType = mySourceReferenceData.isEvent();
		}
		return isOfType;
	}

	public void setChefType(t_chefType myType) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setChefType(myType);
		}
	}

	/*
	 * public void addExtractArea(Rectangle2D.Double anArea) { if
	 * (this.mySourceReferenceData != null) {
	 * mySourceReferenceData.addExtractArea(anArea); } }
	 */

	/*
	 * public List<Rectangle2D.Double> getExtractAreas() { List<Rectangle2D.Double>
	 * areas;
	 * 
	 * areas = null; if (this.mySourceReferenceData != null) { areas =
	 * mySourceReferenceData.getExtractAreas(); } return areas; }
	 */
	/*
	 * public void setExtractAreas(List<Rectangle2D.Double> extractAreas) {
	 * 
	 * if (this.mySourceReferenceData != null) {
	 * mySourceReferenceData.setExtractAreas(extractAreas); } }
	 */

	protected void refreshAvatar() {
		if (this.mySourceReferenceData != null) {
			// avatare picture
			if (this.mySourceReferenceData.getMyChefType() != null) {
				if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.GOAL)) {

					this.myAvatarIMG = new Image("/icon_GOAL.png", "DOEL");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.OPPORTUNITY)) {

					this.myAvatarIMG = new Image("/icon_KANS.png", "KANS");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.RISK)) {

					this.myAvatarIMG = new Image("/icon_RISK.png", "RISK");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.MEASUREMENT)) {

					this.myAvatarIMG = new Image("/icon_ACT.png", "ACTIE");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.EVENT)) {

					this.myAvatarIMG = new Image("/icon_EVENT.png", "EVENT");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.RULE)) {

					this.myAvatarIMG = new Image("/icon_RULE.png", "RULE");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.FACT)) {

					this.myAvatarIMG = new Image("/icon_FACT.png", "FACT");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.NORM)) {

					this.myAvatarIMG = new Image("/icon_NORM.png", "NORM");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.DATA)) {

					this.myAvatarIMG = new Image("/icon_DATA.png", "DATA");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.DATASOURCE)) {

					this.myAvatarIMG = new Image("/icon_DATASOURCE.png", "DATASOURCE");
					myColor = "black";
				} else if (this.mySourceReferenceData.getMyChefType().equals(t_chefType.UNKNOWN)) {

					this.myAvatarIMG = new Image("/icon_OTHER.png", "UNKNOWN");
					myColor = "black";
				}

			} else if (this.mySourceReferenceData.getMyChefType() == null) {
				// no TAGS

				this.myAvatarIMG = new Image("/icon_OTHER_.png", "ONBEKEND");
				myColor = "gray";
			}
			this.myAvatarIMG.setWidth("50px");
			this.myAvatarIMG.setHeight("50px");
		}
	}

	public String getExternalID() {
		String externalID;
		externalID = null;
		if (this.mySourceReferenceData != null) {
			externalID = mySourceReferenceData.getExternalID();
		}
		return externalID;
	}

	public void setExternalID(String externalID) {

		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setExternalID(externalID);
		}

	}

	public String get_id() {
		String id;
		id = null;
		if (this.mySourceReferenceData != null) {
			id = mySourceReferenceData.get_id();
		}
		return id;
	}

	public void set_id(String _id) {

		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.set_id(_id);
		}
	}

	public String getFullPathName() {
		String fullPathName;
		fullPathName = null;
		if (this.mySourceReferenceData != null) {
			fullPathName = mySourceReferenceData.getFullPathName();
		}
		return fullPathName;
	}

	public void setFullPathName(String fullPathName) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setFullPathName(fullPathName);
		}

	}

	public t_annotationType getMyAnnotationType() {

		t_annotationType myAnnotationType;
		myAnnotationType = t_annotationType.UNKNOWN_TYPE;
		if (this.mySourceReferenceData != null) {
			myAnnotationType = mySourceReferenceData.getMyAnnotationType();
		}
		return myAnnotationType;
	}

	public void setMyAnnotationType(t_annotationType myAnnotationType) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setMyAnnotationType(myAnnotationType);
		}

	}

	public boolean isTextMarkup() {
		boolean isTextMarkup;
		isTextMarkup = false;
		if (this.mySourceReferenceData != null) {
			isTextMarkup = mySourceReferenceData.isTextMarkup();
		}
		return isTextMarkup;
	}

	public void setTextMarkup(boolean isTextMarkup) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setTextMarkup(isTextMarkup);
		}
	}

	public String getComment() {

		String comment;
		comment = null;
		if (this.mySourceReferenceData != null) {
			comment = mySourceReferenceData.getComment();
		}
		return comment;
	}

	public void setComment(String comment) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setComment(comment);
		}

	}

	public String getMarkedContent() {

		String markedContent;
		markedContent = null;
		if (this.mySourceReferenceData != null) {
			markedContent = mySourceReferenceData.getMarkedContent();
		}
		return markedContent;
	}

	public void setMarkedContent(String markedContent) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setMarkedContent(markedContent);
		}
	}

	public String getDocumentID() {
		String documentID;
		documentID = null;
		if (this.mySourceReferenceData != null) {
			documentID = mySourceReferenceData.getDocumentID();
		}
		return documentID;
	}

	public void setDocumentID(String documentID) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setDocumentID(documentID);
		}
	}

	public int getPageNumber() {

		int pageNumber;
		pageNumber = -1;
		if (this.mySourceReferenceData != null) {
			pageNumber = mySourceReferenceData.getPageNumber();
		}
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setPageNumber(pageNumber);
		}
	}

	public Point2D getRightTop() {

		Point2D rightTop;
		rightTop = null;
		if (this.mySourceReferenceData != null) {
			rightTop = mySourceReferenceData.getRightTop();
		}
		return rightTop;
	}

	public void setRightTop(Point2D rightTop) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setRightTop(rightTop);
		}
	}

	public Point2D getLeftTop() {

		Point2D leftTop;
		leftTop = null;
		if (this.mySourceReferenceData != null) {
			leftTop = mySourceReferenceData.getLeftTop();
		}
		return leftTop;
	}

	public void setLeftTop(Point2D leftTop) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setLeftTop(leftTop);
		}
	}

	public Point2D getRightBottom() {
		Point2D rightBottom;
		rightBottom = null;
		if (this.mySourceReferenceData != null) {
			rightBottom = mySourceReferenceData.getRightBottom();
		}
		return rightBottom;
	}

	public void setRightBottom(Point2D rightBottom) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setRightBottom(rightBottom);
		}
	}

	public Point2D getLeftBottom() {

		Point2D leftBottom;
		leftBottom = null;
		if (this.mySourceReferenceData != null) {
			leftBottom = mySourceReferenceData.getLeftBottom();
		}
		return leftBottom;
	}

	public void setLeftBottom(Point2D leftBottom) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setLeftBottom(leftBottom);
		}
	}

	public String getInformalName() {

		String name;
		name = null;
		if (this.mySourceReferenceData != null) {
			name = mySourceReferenceData.getInformalName();
		}
		return name;
	}

	public void setInformalName(String informalName) {
		if (this.mySourceReferenceData != null) {
			mySourceReferenceData.setInformalName(informalName);
		}
	}

	public HorizontalLayout updateMyGuiPresence() {

		HorizontalLayout messageComponent;
		VerticalLayout messageBody;
		Div theAvatar;

		messageComponent = MessageComponentFrame();
		if (messageComponent != null) {

			theAvatar = prepareAvatar();
			if (theAvatar != null) {
				messageComponent.add(theAvatar);
			}
			messageBody = referenceInfoBody();
			if (messageBody != null) {
				messageComponent.add(messageBody);
			}
			Icon icon = new Icon(VaadinIcon.LINK);
			openRefButton = new Button("", icon);
			openRefButton.addClickListener(event -> {
				if (this.mySourceReferenceData.getSourceType().equals(t_sourceType.DOCUMENT)) {
					this.openReferencedDocument();
				}

			});
			messageComponent.add(openRefButton);
		}
		this.myGuiPresence = messageComponent;

		/*
		 * this.myDragSource = DragSource.create(myGuiPresence);
		 * this.myDragSource.addDragStartListener((DragStartEvent<HorizontalLayout>
		 * event) -> { System.out.println("Start dragging me"); });
		 * this.myDragSource.addDragEndListener((DragEndEvent<HorizontalLayout> event)
		 * -> { System.out.println("Stop dragging me"); });
		 * this.myDragSource.setDragData(this);
		 */
		return messageComponent;
	}

	protected VerticalLayout referenceInfoBody() {
		// will create a text message with name of initator
		Div name = new Div();
		Text textMessage;
		VerticalLayout messageBody;

		messageBody = new VerticalLayout();
		if (this.getInformalName() != null) {
			// tagname
			name.add(new Text(getInformalName()));
			name.getStyle().set("color", this.myColor);
			messageBody.add(name);
		}
		if (this.mySourceReferenceData != null) {
			if (this.mySourceReferenceData.getMarkedContent() != null) {
				// referenced text
				textMessage = new Text(this.mySourceReferenceData.getMarkedContent());
				messageBody.add(textMessage);
			}
		}
		return messageBody;
	}

	protected Div prepareAvatar() {
		Div avatar = new Div();

		refreshAvatar();
		if (avatar != null) {
			avatar.setWidth("80px");
			avatar.setHeight("52px");
			avatar.getStyle().set("margin", "16px");
			avatar.add(this.myAvatarIMG);

		}
		return avatar;
	}

	protected HorizontalLayout MessageComponentFrame() {
		String bordercolor;
		HorizontalLayout theFrame;

		theFrame = new HorizontalLayout();
		if (theFrame != null) {

		//	bordercolor = "1px solid " + this.myColor;
			theFrame.setSpacing(false);
			//theFrame.getStyle().set("border", bordercolor);
			theFrame.setWidth("700px");
			/*
			 * theFrame.addClickListener( e -> System.out.println(this.getUrl()) //maak div
			 * voor mij );
			 */
		}
		return theFrame;
	}

	public String getFullPathNameURL() {
		String fullPathNameURL;
		fullPathNameURL = null;
		if (this.mySourceReferenceData != null) {
			fullPathNameURL = this.mySourceReferenceData.getFullPathNameURL();
		}
		return fullPathNameURL;
	}

	public void setFullPathNameURL(String fullPathNameURL) {

		if (this.mySourceReferenceData != null) {
			this.mySourceReferenceData.setFullPathNameURL(fullPathNameURL);
		}
	}

	public String getUrl() {
		String myURL;

		// file:///C:/compliance/w1.pdf#page=3&comment=1476e256-ac1f-401e-9a0a-90ecabf05e5c&highlight=120,489,320,,498
		// file:///C:/compliance/w1.pdf#page=2&comment=1476e256-ac1f-401e-9a0a-90ecabf05e5c

		myURL = null;

		if (this.getExternalID() != null) {
			myURL = "file:///";
			if (!this.getExternalID().isEmpty()) {
				myURL = myURL + this.getFullPathNameURL() + "#page=" + this.getPageNumber() + "&comment="
						+ this.getExternalID();
			} else {
				myURL = myURL + this.getFullPathName() + "#page=" + this.getPageNumber();
			}
		}
		return myURL;
	}

	public void setUsed(boolean b) {
		this.isUsed = b;


	}

	public void openReferencedDocument() {

		if (this.mySourceReferenceData != null) {
			this.mySourceReferenceData.openSourceReferenceDocument();
		}
	}

	public t_chefType getChefType() {
		// TODO Auto-generated method stub
		t_chefType myChefType;

		myChefType = t_chefType.UNKNOWN;

		if (this.mySourceReferenceData != null) {
			myChefType = this.mySourceReferenceData.getChefType();
		}
		return myChefType;
	}

	public void storeYourself() {
		if (WEB_Session.getInstance().isPersistanceOn()) {

			if (this.mySourceReferenceData != null) {
				this.mySourceReferenceData.storeYourself();
			}
		}

	}



}
