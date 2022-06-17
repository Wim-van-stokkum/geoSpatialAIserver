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

import com.fasterxml.jackson.annotation.JsonIgnore;

import planspace.export.CSVExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_annotationType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_sourceType;
import planspace.utils.MikadoSettings;

public class SourceReferenceData {

	private String _id;
	private t_sourceType sourceType;
	private String externalID;
	private String fullPathName;
	private String fullPathNameURL;
	private t_annotationType myAnnotationType;
	private boolean isTextMarkup;
	private String comment;
	private String markedContent;
	private String documentID;
	private int pageNumber;
	private Point2D rightTop, leftTop, rightBottom, leftBottom;

	private t_chefType myChefType;
	private String informalName;
	@JsonIgnore()
	private transient List<Rectangle2D.Double> extractAreas;

	public SourceReferenceData() {
		this._id = UUID.randomUUID().toString();
		this.extractAreas = new ArrayList<Rectangle2D.Double>();
		myChefType = t_chefType.UNKNOWN;
		sourceType = t_sourceType.DOCUMENT;
	}

	public t_chefType getMyChefType() {
		return myChefType;
	}

	public void setMyChefType(t_chefType myChefType) {
		this.myChefType = myChefType;
	}

	public boolean isGoal() {
		boolean isOfType;

		isOfType = false;
		if (this.myChefType != null) {
			if (this.myChefType.equals(t_chefType.GOAL)) {
				isOfType = true;

			}
		}
		return isOfType;
	}

	public boolean isRisk() {
		boolean isOfType;

		isOfType = false;
		if (this.myChefType != null) {
			if (this.myChefType.equals(t_chefType.RISK)) {
				isOfType = true;

			}
		}
		return isOfType;
	}

	public boolean isOpportinity() {
		boolean isOfType;

		isOfType = false;
		if (this.myChefType != null) {
			if (this.myChefType.equals(t_chefType.OPPORTUNITY)) {
				isOfType = true;

			}
		}
		return isOfType;
	}

	public boolean isMeasurement() {
		boolean isOfType;

		isOfType = false;
		if (this.myChefType != null) {
			if (this.myChefType.equals(t_chefType.MEASUREMENT)) {
				isOfType = true;

			}
		}
		return isOfType;
	}

	public boolean isFact() {
		boolean isOfType;

		isOfType = false;
		if (this.myChefType != null) {
			if (this.myChefType.equals(t_chefType.FACT)) {
				isOfType = true;

			}
		}
		return isOfType;
	}

	public boolean isRule() {
		boolean isOfType;

		isOfType = false;
		if (this.myChefType != null) {
			if (this.myChefType.equals(t_chefType.RULE)) {
				isOfType = true;

			}
		}
		return isOfType;
	}

	public boolean isEvent() {
		boolean isOfType;

		isOfType = false;
		if (this.myChefType != null) {
			if (this.myChefType.equals(t_chefType.EVENT)) {
				isOfType = true;

			}
		}
		return isOfType;
	}

	public void setChefType(t_chefType myType) {

		this.myChefType = myType;

	}

	public void addExtractArea(Rectangle2D.Double anArea) {
		if (!this.extractAreas.contains(anArea)) {
			this.extractAreas.add(anArea);
		}
	}

	@JsonIgnore()
	public List<Rectangle2D.Double> getExtractAreas() {
		return extractAreas;
	}

	public void setExtractAreas(List<Rectangle2D.Double> extractAreas) {
		this.extractAreas = extractAreas;
	}

	public String getExternalID() {
		return externalID;
	}

	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getFullPathName() {
		return fullPathName;
	}

	public void setFullPathName(String fullPahName) {
		this.fullPathName = fullPahName;
		sourceType = t_sourceType.DOCUMENT;
	}

	public t_annotationType getMyAnnotationType() {
		return myAnnotationType;
	}

	public void setMyAnnotationType(t_annotationType myAnnotationType) {
		this.myAnnotationType = myAnnotationType;
	}

	public boolean isTextMarkup() {
		return isTextMarkup;
	}

	public void setTextMarkup(boolean isTextMarkup) {
		this.isTextMarkup = isTextMarkup;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMarkedContent() {
		return markedContent;
	}

	public t_sourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(t_sourceType sourceType) {
		this.sourceType = sourceType;
	}

	public void setMarkedContent(String markedContent) {
		this.markedContent = markedContent;
	}

	public String getDocumentID() {
		return documentID;
	}

	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Point2D getRightTop() {
		return rightTop;
	}

	public void setRightTop(Point2D rightTop) {
		this.rightTop = rightTop;
	}

	public Point2D getLeftTop() {
		return leftTop;
	}

	public void setLeftTop(Point2D leftTop) {
		this.leftTop = leftTop;
	}

	public Point2D getRightBottom() {
		return rightBottom;
	}

	public void setRightBottom(Point2D rightBottom) {
		this.rightBottom = rightBottom;
	}

	public Point2D getLeftBottom() {
		return leftBottom;
	}

	public void setLeftBottom(Point2D leftBottom) {
		this.leftBottom = leftBottom;
	}

	public String getInformalName() {
		if (this.informalName != null) {
			return informalName;
		} else {
			return "";
		}
	}

	public void setInformalName(String informalName) {
		this.informalName = informalName;
	}

	public String getFullPathNameURL() {
		return fullPathNameURL;
	}

	public void setFullPathNameURL(String fullPathNameURL) {
		this.fullPathNameURL = fullPathNameURL;
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

	public t_chefType getChefType() {
		// TODO Auto-generated method stub
		return this.myChefType;
	}

	public void storeYourself() {
		InterfaceToDomainTypeRepository theIFC;

		theIFC = InterfaceToDomainTypeRepository.getInstance();
		if (theIFC != null) {
			theIFC.StoreSourceReferenceForDomain(this);
		}
	}

	public void openSourceReferenceDocument() {
		String browerAccessPath;
		
		browerAccessPath = "C://Program Files//Mozilla Firefox//firefox.exe ";
		//browerAccessPath = MikadoSettings.getInstance().getPathToBrowser();
		if (this.getSourceType().equals(t_sourceType.DOCUMENT)) {
			if (this.getUrl() != null) {
				String url = this.getUrl();

				System.out.println("open 2 " + url);
				if (Desktop.isDesktopSupported()) {
					System.out.println("open 3 " + url);
					// Windows
					try {
						Desktop.getDesktop().browse(new URI(url));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {

						e.printStackTrace();
					}
				} else {
					// Ubuntu
					System.out.println("open 4 " + url);
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec(browerAccessPath + url);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

			}

		}
	}

	public void exportReferencesToCSV(CSVExportFile anExport) {
		String t;
		
		if (anExport != null) {
			anExport.writeCSVitem(this.getInformalName());
			anExport.writeCSVitem(this.getFullPathName());
			anExport.writeCSVitem(this.getUrl());
			anExport.writeCSVitem(String.valueOf(this.getPageNumber()));
			t = this.getMarkedContent();
			if (t != null) {
				anExport.writeCSVitem(t.replaceAll("\n", " "));
			}
			else
			{
				anExport.writeEmptyCSVitem();
			}
//			anExport.write(this.getMarkedContent());
		
		}
		
	}

}
