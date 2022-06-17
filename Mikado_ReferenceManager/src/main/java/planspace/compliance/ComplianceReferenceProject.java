package planspace.compliance;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pdfclown.documents.Document;
import org.pdfclown.documents.Page;
import org.pdfclown.documents.contents.ITextString;
import org.pdfclown.documents.interaction.annotations.Annotation;
import org.pdfclown.documents.interaction.annotations.AnnotationActions;
import org.pdfclown.documents.interaction.annotations.TextMarkup;
import org.pdfclown.files.File;
import org.pdfclown.objects.PdfArray;
import org.pdfclown.objects.PdfDataObject;
import org.pdfclown.objects.PdfDictionary;
import org.pdfclown.objects.PdfDirectObject;
import org.pdfclown.objects.PdfIndirectObject;
import org.pdfclown.objects.PdfName;
import org.pdfclown.objects.PdfTextString;
import org.pdfclown.tools.TextExtractor;
import org.pdfclown.util.math.geom.Quad;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_annotationType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.utils.MikadoSettings;
import planspace.utils.PlanSpaceLogger;

public class ComplianceReferenceProject {

	private String filePath ;
	private String filePathURL;
	private String fullFileName, fullFileNameURL;
	private File complianceFile;
	private Document complianceDocument;
	private List<SourceReferenceData> references;

	public ComplianceReferenceProject(String theFileName) {
		// TODO Auto-generated constructor stub
		
		filePath = MikadoSettings.getInstance().getPathToAnnotatedDocuments();
		filePathURL = MikadoSettings.getInstance().getURLToAnnotatedDocuments();
		this.fullFileName = this.filePath +"//" + theFileName;
		this.fullFileNameURL = this.filePathURL + "//" + theFileName;
		references = new ArrayList<SourceReferenceData>();
	}

	public void ReadComplianceProject() {
		this.openCompliancePDF(fullFileName);

	}

	public List<SourceReferenceData> getReferences() {
		return references;
	}

	public void setReferences(List<SourceReferenceData> references) {
		this.references = references;
	}

	private void openCompliancePDF(String fullFileName) {
		try {

			complianceFile = new File(fullFileName);
			if (complianceFile != null) {
				complianceDocument = complianceFile.getDocument();
				if (complianceDocument != null) {
					PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] Succesfully opened PDFFile:" + fullFileName);
					extractPages(complianceDocument);
					// walkThroughObjects();

				} else {
					PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] PDFFile has no document:" + fullFileName);
				}

				complianceFile.close();
			} else {
				PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] PDFFile not accessible:" + fullFileName);
			}

		} catch (IOException e) {

			PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] PDFFile not found:" + fullFileName);
			e.printStackTrace();
		}

	}

	private void openCompliancePDFPartialRead(String fullFileName, double x, double y, double w, double h, double m,
			double f) {
		try {
			complianceFile = new File(fullFileName);
			if (complianceFile != null) {
				complianceDocument = complianceFile.getDocument();
				if (complianceDocument != null) {
					PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] Succesfully opened PDFFile:" + fullFileName);
					extractTextOnPage(complianceDocument, x, y, w, h, m, f);

				} else {
					PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] PDFFile has no document:" + fullFileName);
				}

				complianceFile.close();
			} else {
				PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] PDFFile not accessible:" + fullFileName);
			}

		} catch (IOException e) {

			PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] PDFFile not found:" + fullFileName);
			e.printStackTrace();
		}

	}

	private void extractTextOnPage(Document complianceDocument2, double x, double y, double w, double h, double m,
			double f) {

		TextExtractor aTextExtractor, aTextExtractor2;
		Map<Rectangle2D, List<ITextString>> allWords;
		List<ITextString> theWords, allWordsAsList;

		Rectangle2D.Double anArea1;
		int i;
		Page aPDFpage;

		double pageW, pageH;

		// anAreaList.add(anArea1);
		aPDFpage = complianceDocument2.getPages().get(0);
		aTextExtractor = new TextExtractor();
		allWords = aTextExtractor.extract(aPDFpage);

		pageW = aPDFpage.getSize().getWidth();
		pageH = aPDFpage.getSize().getHeight();

		// System.out.println("Page width" + aPDFpage.getSize().getWidth());
		// System.out.println("Page height" + aPDFpage.getSize().getHeight());

		if (allWords != null) {
			allWordsAsList = allWords.get(null);
			/*
			 * for (i = 0; i < allWordsAsList.size(); i++) { System.out.println("All Words["
			 * + i + "]" + allWordsAsList.get(i).getText() + " @ " + "[X=" +
			 * allWordsAsList.get(i).getBox().getX() + ", Y=" +
			 * allWordsAsList.get(i).getBox().getY() + ", W=" +
			 * allWordsAsList.get(i).getBox().getWidth() + ", H=" +
			 * allWordsAsList.get(i).getBox().getHeight()); }
			 */
			anArea1 = new Rectangle2D.Double();
			anArea1.x = x - m;
			anArea1.y = y - m;
			anArea1.width = w + m + m;
			anArea1.height = h * f;

			// System.out.println("\n-------\nTop (X=" + anArea1.getX() + ", Y=" +
			// anArea1.getY() + ")");
			// System.out.println("Width:=" + anArea1.getWidth() + ", Height=" +
			// anArea1.getHeight());
			aTextExtractor2 = new TextExtractor();
			theWords = aTextExtractor2.filter(allWords, anArea1);
			if (theWords != null) {
				if (theWords.size() > 0) {
					for (i = 0; i < theWords.size(); i++) {
						// System.out.println("WORD PARTIAL READ[" + i + "]" +
						// theWords.get(i).getText());
					}
				} else {
					// System.out.println("No words found in area");
				}
			} else {
				// System.out.println("No words found area");
			}

		}
	}

	private void ParseTheAnnotations(Document theComplianceDocument, Page aPDFpage) {
		List<Annotation> annotations;
		int i;
		int anno;
		Annotation anAnnotation;
		PdfDictionary theDict;
		PdfDirectObject theBaseObject;
		TextExtractor aTextExtractor;
		AnnotationActions theActions;

		Map<Rectangle2D, List<ITextString>> allWords;

		if (aPDFpage != null) {
			annotations = aPDFpage.getAnnotations();
			if (annotations != null) {
				if (annotations.size() > 0) {

					// all word on page
					// System.out.println("--------Text contents on page----------");

					// optional for debug purposes
					// this.printTextStrings(allWords.get(null), true, "All Words on Page");

					// extractor.setAreaTolerance(2);
					annotations = aPDFpage.getAnnotations();
					
					if (annotations.size() > 0) {
					
						aTextExtractor = new TextExtractor();
						try {
						    allWords = aTextExtractor.extract(aPDFpage);
						   
								for (i = 0; i < annotations.size(); i++) {
								PlanSpaceLogger.getInstance().log("annotation :  " + i + " / " + annotations.size());
								anAnnotation = annotations.get(i);
								// System.out.println("\n----------------------------");
								// PlanSpaceLogger.getInstance()
								// .log_Debug("[ANNOTATION] : " + (i + 1) + " of " + annotations.size());
								theDict = anAnnotation.getBaseDataObject();

								// this.inspectDirectObject(theBaseObject, "Baseobject of annotation");
								this.checkByDictionaryForPage(theComplianceDocument, aPDFpage, anAnnotation, theDict,
										allWords);

								// theActions = anAnnotation.getActions();
								// this.checkAnnotationActions(aPDFpage, anAnnotation, theActions, "Actions of
								// annotation");

							}
							
			
						}
						finally {
							PlanSpaceLogger.getInstance().log_Debug("Cannot parse PDF content");
							
						}

						// System.out.println("--------Annotations----------");
						// System.out.println("-----" + fullFileName + "------");

						
					
						// System.out.println("----------------------------");
					}
				} else {
					// System.out.println("No annotations on page");
				}
			} else {
				// System.out.println("No annotations on page");
			}

		} else {
			// System.out.println("No Page found");
		}
	}

	private void checkByDictionaryForPage(Document theComplianceDocument, Page aPDFpage, Annotation anAnnotation,
			PdfDictionary aDict, Map<Rectangle2D, List<ITextString>> allWords) {
		SourceReferenceData aSourceReference;

		List<Quad> myQuads;
		Quad aQuad;
		Rectangle2D.Double anExtractArea;
		double pageH, pageW;
		String markedText;
		List<Rectangle2D.Double> myAreasFromQuadInDict;
		int i;
		boolean isHeading;

		/*****************************************/

		if (this.checkIsAnnotation(aDict)) {
			if (this.isRelevantSubType(aDict)) {
				aSourceReference = new SourceReferenceData();
				isHeading = false;
				/* to do external ID */

				aSourceReference.setTextMarkup(checkTypeIsTextMarkup(anAnnotation));

				/* References */
				aSourceReference.setPageNumber(aPDFpage.getIndex() + 1);
				aSourceReference.setFullPathName(fullFileName);
				aSourceReference.setFullPathNameURL(fullFileNameURL);
				aSourceReference.setExternalID(this.getExternalIdIfPresent(aDict));

				// optional for debug purposes
				this.printDictionaryItems(aDict, "Dictionary of annotation");

				// annotation type
				aSourceReference.setMyAnnotationType(this.determineAnnotationType(aDict));
				// tags
				isHeading = processPotentialHeading(aDict, aSourceReference);
				parseContentsForTags(aDict, aSourceReference);
				
				// Dimensions of page, just for information
				pageW = aPDFpage.getSize().getWidth();
				pageH = aPDFpage.getSize().getHeight();
				// System.out.println("Page width" + aPDFpage.getSize().getWidth());
				// System.out.println("Page height" + aPDFpage.getSize().getHeight());

				// Quads
				markedText = ""; 
				myAreasFromQuadInDict = this.getTextExtractionAreasFromDictionary(aDict, anAnnotation, aSourceReference,
						pageH);
				if (myAreasFromQuadInDict != null) {
					markedText = this.getMarkedText(aPDFpage, allWords, aSourceReference);
					
					 PlanSpaceLogger.getInstance().log_Debug("[TEXTEXTRACTION] textFound = " +  markedText);
				} else {
					myQuads = this.getQuadsForAnnotation(anAnnotation, aSourceReference);
					if (myQuads != null) {
						for (i = 0; i < myQuads.size(); i++) {
							aQuad = myQuads.get(i);
							anExtractArea = GetAreaForTextExtraction(aQuad, aSourceReference);
						}
						markedText = this.getMarkedText(aPDFpage, allWords, aSourceReference);

						 PlanSpaceLogger.getInstance().log_Debug("[TEXTEXTRACTION] textFound = " +	 markedText);
					} else {
						// to do get te rectangle2D
					}
				}
				if (!isHeading) {
					this.setInformalName(aDict,aSourceReference,  markedText);
					this.references.add(aSourceReference);
				}
			}
		} else {
			PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] Error annotation not of type ANNOT ");
		}
	}

	private String getExternalIdIfPresent(PdfDictionary aDict) {
		String extID;
		PdfDirectObject anID;

		extID = "";
		anID = aDict.get(PdfName.NM);
		if (anID != null) {
			extID = anID.toString();
			if (extID.startsWith("(")) {
				extID = extID.substring(1);
			}
			if (extID.endsWith(")")) {
				extID = extID.substring(0, extID.length() - 1);
			}
		}
		return extID;
	}

	private boolean isRelevantSubType(PdfDictionary aDict) {
		boolean isRelevant;
		PdfDirectObject theSubType;

		isRelevant = false;
		theSubType = aDict.get(PdfName.Subtype);
		if (theSubType != null) {
			if (theSubType.toString().equals("Highlight")) {
				isRelevant = true;

			} else if (theSubType.toString().equals("Text")) {
				isRelevant = true;
			} else if (theSubType.toString().equals("Link")) {
				isRelevant = true;

			}
		}
		return isRelevant;
	}

	private List<Rectangle2D.Double> getTextExtractionAreasFromDictionary(PdfDictionary aDict, Annotation anAnnotation,
			SourceReferenceData aSourceReference, double pageHeight) {
		List<Rectangle2D.Double> myAreas;
		PdfArray dictQuads;

		double rightBottomX, rightBottomY;
		double width, height;
		double margin, heightCorrectionFactor;
		double setCount = 0;
		double x1, x2, x3, x4, y1, y2, y3, y4;
		double yMin, xMin, xMax, yMax;

		int i;
		Rectangle2D.Double aRect;

		myAreas = null;
		dictQuads = (PdfArray) aDict.get(PdfName.QuadPoints);
		if (dictQuads != null) {
			if (dictQuads.size() > 0) {
				setCount = dictQuads.size() / 8;
				myAreas = new ArrayList<Rectangle2D.Double>();
				for (i = 0; i < setCount; i++) {

					aRect = new Rectangle2D.Double();
					x1 = Double.parseDouble(dictQuads.get(0 + (i * 8)).toString());
					x2 = Double.parseDouble(dictQuads.get(2 + (i * 8)).toString());
					x3 = Double.parseDouble(dictQuads.get(4 + (i * 8)).toString());
					x4 = Double.parseDouble(dictQuads.get(6 + (i * 8)).toString());

					y1 = Double.parseDouble(dictQuads.get(1 + (i * 8)).toString());
					y1 = pageHeight - y1;
					y2 = Double.parseDouble(dictQuads.get(3 + (i * 8)).toString());
					y2 = pageHeight - y2;
					y3 = Double.parseDouble(dictQuads.get(5 + (i * 8)).toString());
					y3 = pageHeight - y3;
					y4 = Double.parseDouble(dictQuads.get(7 + (i * 8)).toString());
					y4 = pageHeight - y4;

					xMin = Double.min(x1, x2);
					xMin = Double.min(xMin, x3);
					xMin = Double.min(xMin, x4);

					xMax = Double.max(x1, x2);
					xMax = Double.max(xMax, x3);
					xMax = Double.max(xMax, x4);

					yMin = Double.min(y1, y2);
					yMin = Double.min(yMin, y3);
					yMin = Double.min(yMin, y4);

					yMax = Double.max(y1, y2);
					yMax = Double.max(yMax, y3);
					yMax = Double.max(yMax, y4);

					aRect.x = xMin;
					aRect.y = yMin;
					width = xMax - xMin;
					height = yMax - yMin;
					aRect.width = width;
					aRect.height = height;
					myAreas.add(aRect);

					// set standard margin to let TextExtraction be succesfull
					margin = 2;
					heightCorrectionFactor = 2; // pdfClown gives incorrect height in Quads about 50%

					aRect.x = aRect.x - margin;
					aRect.y = aRect.y - margin;
					aRect.width = aRect.width + margin + margin;
					aRect.height = height * heightCorrectionFactor;

					// System.out.println("quad dict" + dictQuads.size());
					// this.printRectangle2D(aRect, "From dictionary");
					aSourceReference.addExtractArea(aRect);

				}
			}
		}
		return myAreas;
	}

	private String getMarkedText(Page aPDFpage, Map<Rectangle2D, List<ITextString>> allWords,
			SourceReferenceData aSourceReference) {
		String markedText, aMarkedTextFragment;
		Rectangle2D.Double anExtractArea;
		TextExtractor aTextExtractor;
		List<ITextString> theWords;
		int i, j;
		boolean endSpace;

		markedText = "";
		if (aSourceReference.getExtractAreas() != null) {
			aTextExtractor = new TextExtractor();
			for (i = 0; i < aSourceReference.getExtractAreas().size(); i++) {
				anExtractArea = aSourceReference.getExtractAreas().get(i);
				theWords = aTextExtractor.filter(allWords, anExtractArea);
				aMarkedTextFragment = "";
				endSpace = false;
				for (j = 0; j < theWords.size(); j++) {
					aMarkedTextFragment = aMarkedTextFragment + theWords.get(j).getText();
				}

				if (i == 0) {
					markedText = markedText + aMarkedTextFragment;
				} else {
					if (!endSpace) {
						markedText = markedText + " " + aMarkedTextFragment;
					} else {
						markedText = markedText + aMarkedTextFragment;

					}

				}
				if (aMarkedTextFragment.endsWith(" ")) {
					endSpace = true;
				} else {
					endSpace = false;
				}

			}
		}
		aSourceReference.setMarkedContent(markedText);
		return markedText;
	}

	private Rectangle2D.Double GetAreaForTextExtraction(Quad aQuad, SourceReferenceData aSourceReference) {

		Rectangle2D.Double theExtractArea;
		Point2D[] points;
		Point2D pointTopLeft, pointTopRight, pointBottomLeft, pointBottomRight;
		double boxWidth, boxHeight;
		double margin, heightCorrectionFactor;

		theExtractArea = null;
		if (aQuad != null) {
			// Original points
			points = aQuad.getPoints();
			// System.out.println("Points size: " + points.length);
			pointTopLeft = points[0];
			pointTopRight = points[1];
			pointBottomRight = points[2];
			pointBottomLeft = points[3];

			// for debug purpose
			// this.printPoints(pointTopLeft, pointTopRight, pointBottomRight,
			// pointBottomLeft, "original points");

			// no margin on height because is corrected by factor
			boxHeight = pointBottomLeft.getY() - pointTopLeft.getY();

			// set standard margin to let TextExtraction be succesfull
			margin = 2;
			heightCorrectionFactor = 2; // pdfClown gives incorrect height in Quads about 50%

			// correct points for succesfull TextExtraction of annotated text
			pointTopLeft.setLocation(pointTopLeft.getX() - margin, pointTopLeft.getY() - margin);
			pointTopRight.setLocation(pointTopRight.getX() + margin + margin, pointTopRight.getY() - margin);
			pointBottomRight.setLocation(pointBottomRight.getX() + margin + margin, pointBottomRight.getY() + margin);
			pointBottomLeft.setLocation(pointBottomLeft.getX() - margin, pointBottomLeft.getY() + margin);

			// for debug purpose
			// this.printPoints(pointTopLeft, pointTopRight, pointBottomRight,
			// pointBottomLeft, "corrected points");

			aSourceReference.setLeftTop(pointTopLeft);
			aSourceReference.setRightTop(pointTopRight);
			aSourceReference.setRightBottom(pointBottomRight);
			aSourceReference.setLeftBottom(pointBottomLeft);

			// Width and height
			boxWidth = pointTopRight.getX() - pointTopLeft.getX();
			boxHeight = boxHeight * heightCorrectionFactor;

			// set the extractArea
			theExtractArea = new Rectangle2D.Double();
			theExtractArea.x = pointTopLeft.getX();
			theExtractArea.y = pointTopLeft.getY();
			theExtractArea.width = boxWidth;
			theExtractArea.height = boxHeight;
			// for debug purpose
			// this.printRectangle2D(theExtractArea, "the Extract Area");
			aSourceReference.addExtractArea(theExtractArea);
		}

		// TODO Auto-generated method stub
		return theExtractArea;
	}

	private void printRectangle2D(Rectangle2D.Double anArea, String description) {
		System.out.println("\n ----------- Rectangle : " + description + " ----------------");
		System.out.println("\n---\nTop (X=" + anArea.getX() + ", Y=" + anArea.getY() + ")");
		System.out.println("Width:=" + anArea.getWidth() + ", Height=" + anArea.getHeight());
		System.out.println("-------------------------------------------------------");
	}

	private void printPoints(Point2D pointTopLeft, Point2D pointTopRight, Point2D pointBottomRight,
			Point2D pointBottomLeft, String description) {

		double w, h;

		w = pointTopRight.getX() - pointTopLeft.getX();
		h = pointBottomLeft.getY() - pointTopLeft.getY();

		System.out.println("\n ----------- points: " + description + " ----------------");
		System.out.println("pointTopLeft: (X=" + pointTopLeft.getX() + ", Y=" + pointTopLeft.getY() + ")");
		System.out.println("pointTopRight: (X=" + pointTopRight.getX() + ", Y=" + pointTopRight.getY() + ")");
		System.out.println("pointBottomRight: (X=" + pointBottomRight.getX() + ", Y=" + pointBottomRight.getY() + ")");
		System.out.println("pointBottomLeft: (X=" + pointBottomLeft.getX() + ", Y=" + pointBottomLeft.getY() + ")");
		System.out.println("width = " + w + " , height = " + h);
		System.out.println("-------------------------------------");
	}

	private List<Quad> getQuadsForAnnotation(Annotation anAnnotation, SourceReferenceData aSourceReference) {

		List<Quad> myQuads;
		TextMarkup annotationAsTextMarkup;

		myQuads = null;
		if (aSourceReference.isTextMarkup()) {
			annotationAsTextMarkup = (TextMarkup) anAnnotation;
			if (annotationAsTextMarkup != null) {
				myQuads = annotationAsTextMarkup.getMarkupBoxes();
			}
		}
		return myQuads;
	}

	private void setInformalName(PdfDictionary aDict, SourceReferenceData aSourceReference, String markedText) {
		String informalName;
		PdfDirectObject theContents;
		int posHex;

		theContents = aDict.get(PdfName.Contents);
		if (theContents != null) {
			if (!theContents.toString().isBlank() && !theContents.toString().isEmpty()
					&& !theContents.toString().equals("null")) {
				if (theContents.toString().toString().toLowerCase().contains(":")) {
					informalName = this.getNameBehindSemicolumn(theContents.toString());
					if (informalName != null) {
						if (theContents.toString().toString().toLowerCase().contains("#")) {
							informalName =  informalName.replace("#", markedText.replace("\n", " "));
							aSourceReference.setInformalName(informalName);
						} else {
							if (informalName.isBlank() || informalName.isEmpty()) {
								informalName = markedText;
							}
							aSourceReference.setInformalName(informalName);
						}
					} else {
						aSourceReference.setInformalName(markedText.replace("\n", " "));
					}

				}
				else {
					aSourceReference.setInformalName(markedText.replace("\n", " "));
				}
			}
		}

	}

	private boolean processPotentialHeading(PdfDictionary aDict, SourceReferenceData aSourceReference) {
		PdfDirectObject theContents;
		String informalName;
		boolean isHeading;

		isHeading = false;
		theContents = aDict.get(PdfName.Contents);
	//	System.out.println("\nHEADING? CONTENTS:" + theContents);
		if (theContents != null) {
			if (!theContents.toString().isBlank() && !theContents.toString().isEmpty()
					&& !theContents.toString().equals("null")) {

				if (theContents.toString().toLowerCase().equals("(t)")
						|| theContents.toString().toLowerCase().contains("titel")) {

					System.out.println("titel");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h1)")) {
					System.out.println("heading 1");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h2)")) {
					System.out.println("heading 2");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h3)")) {
					System.out.println("heading 3");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h4)")) {
					System.out.println("heading 4");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h5)")) {
					System.out.println("heading 5");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h6)")) {
					System.out.println("heading 6");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h7)")) {
					System.out.println("heading 7");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h8)")) {
					System.out.println("heading 8");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(h9)")) {
					System.out.println("heading 9");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(a)")) {
					System.out.println("artikel");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(s)")) {
					System.out.println("sectie");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(p)")) {
					System.out.println("paragraaf");
					isHeading = true;

				} else if (theContents.toString().toLowerCase().equals("(l)")) {
					System.out.println("lid");
					isHeading = true;

				}

			}
		/*	informalName = this.getNameBehindSemicolumn(theContents.toString());
			if (informalName != null) {
				aSourceReference.setInformalName(informalName);

			} else {

				aSourceReference.setInformalName(theContents.toString());

			}
			*/

		}
		return isHeading;

	}

	private void parseContentsForTags(PdfDictionary aDict, SourceReferenceData aSourceReference) {
		PdfDirectObject theContents;
		String informalName;

		theContents = aDict.get(PdfName.Contents);
		// System.out.println("\nCONTENTS:" + theContents);
		if (theContents != null) {
			if (!theContents.toString().isBlank() && !theContents.toString().isEmpty()
					&& !theContents.toString().equals("null")) {

				if (theContents.toString().toLowerCase().contains("goal")
						|| theContents.toString().toLowerCase().contains("doel")) {
					aSourceReference.setChefType(t_chefType.GOAL);

				} else if (theContents.toString().toLowerCase().contains("risk")
						|| theContents.toString().toLowerCase().contains("risico")) {
					aSourceReference.setChefType(t_chefType.RISK);

				} else if (theContents.toString().toLowerCase().contains("opportunity")
						|| theContents.toString().toLowerCase().contains("kans")) {
					aSourceReference.setChefType(t_chefType.OPPORTUNITY);

				} else if (theContents.toString().toLowerCase().contains("measurement")
						|| theContents.toString().toLowerCase().contains("maatregel")) {
					aSourceReference.setChefType(t_chefType.MEASUREMENT);

				} else if (theContents.toString().toLowerCase().contains("fact")
						|| theContents.toString().toLowerCase().contains("feit")) {
					aSourceReference.setChefType(t_chefType.FACT);

				} else if (theContents.toString().toLowerCase().contains("rule")
						|| theContents.toString().toLowerCase().contains("regel")) {
					aSourceReference.setChefType(t_chefType.RULE);

				} else if (theContents.toString().toLowerCase().contains("aanleiding")
						|| theContents.toString().toLowerCase().contains("event")
						|| theContents.toString().toLowerCase().contains("gebeurtenis")) {
					aSourceReference.setChefType(t_chefType.EVENT);

				} else if (theContents.toString().toLowerCase().contains("data")
						|| theContents.toString().toLowerCase().contains("gegeven")) {
					aSourceReference.setChefType(t_chefType.DATA);

				} else if (theContents.toString().toLowerCase().contains("bron")
						|| theContents.toString().toLowerCase().contains("gegevensbron")
						|| theContents.toString().toLowerCase().contains("databron")) {
					aSourceReference.setChefType(t_chefType.DATASOURCE);

				} else if (theContents.toString().toLowerCase().contains("norm")
						|| theContents.toString().toLowerCase().contains("streefwaarde")
						|| theContents.toString().toLowerCase().contains("bovengrens")
						|| theContents.toString().toLowerCase().contains("ondergrens")) {
					aSourceReference.setChefType(t_chefType.NORM);
					System.out.println("7");

				} else
					aSourceReference.setChefType(t_chefType.UNKNOWN);

			}
		/*	informalName = this.getNameBehindSemicolumn(theContents.toString());
			if (informalName != null) {
				aSourceReference.setInformalName(informalName);

			} else {

				aSourceReference.setInformalName(theContents.toString());

			}
			*/

		}

	}

	private String getNameBehindSemicolumn(String fullString) {
		String informalName;
		int pos;
		int l;

		informalName = null;
		if (fullString.contains(":")) {
			l = fullString.length();
			pos = fullString.indexOf(":");
			informalName = fullString.substring(pos + 1, l - 1); // +1 skip semicolon l-1 skip )
			informalName = informalName.strip();
		}

		return informalName;
	}

	private t_annotationType determineAnnotationType(PdfDictionary aDict) {

		t_annotationType theAnnotationType;
		PdfDirectObject theSubType;

		theAnnotationType = t_annotationType.UNKNOWN_TYPE;
		theSubType = aDict.get(PdfName.Subtype);
		if (theSubType != null) {
			if (theSubType.toString().equals("Highlight")) {
				theAnnotationType = t_annotationType.HIGHLIGHTED_TEXT;
				// System.out.println("I AM HIGHLIGHTED TEXT");
			} else if (theSubType.toString().equals("Text")) {
				theAnnotationType = t_annotationType.UNHIGHLIGHTED_TEXT;
				// System.out.println("I AM UNHIGHLIGHTED TEXT");
			} else if (theSubType.toString().equals("Link")) {
				theAnnotationType = t_annotationType.LINK;
				// System.out.println("I AM A LINK");
			} else if (theSubType.toString().equals("Text")) {
				theAnnotationType = t_annotationType.FREETEXT;
				// System.out.println("I AM INDEPENDEND TEXT");
			}
		}
		return theAnnotationType;
	}

	private void printDictionaryItems(PdfDictionary aDict, String description) {
		Iterator<Entry<PdfName, PdfDirectObject>> iter;
		Set<Entry<PdfName, PdfDirectObject>> theEntrySet;
		Entry<PdfName, PdfDirectObject> anEntry;
		PdfName aPdfName;
		PdfDirectObject aDirectObject;

		// System.out.println("##### Directionary :" + description + "#########");
		theEntrySet = aDict.entrySet();
		iter = theEntrySet.iterator();
		while (iter.hasNext()) {
			anEntry = (Entry<PdfName, PdfDirectObject>) iter.next();
			aPdfName = anEntry.getKey();
			aDirectObject = anEntry.getValue();

			// System.out.println("pfdName:" + aPdfName.getValue() + " [" +
			// aDirectObject.toString() + "]");
		}
//		System.out.println("##############");
	}

	private boolean checkIsAnnotation(PdfDictionary aDict) {
		boolean isAnnotation;
		PdfDirectObject theType;

		isAnnotation = false;

		theType = aDict.get(PdfName.Type);
		if (theType != null) {
			if (theType.toString().equals("Annot")) {
				isAnnotation = true;
			}
		}

		return isAnnotation;
	}

	private boolean checkTypeIsTextMarkup(Annotation anAnnotation) {
		boolean isTextMarkup;

		isTextMarkup = false;
		if (anAnnotation instanceof TextMarkup) {

			isTextMarkup = true;
		}

		return isTextMarkup;
	}

	private void walkThroughObjects() {
		PdfIndirectObject anIndirectObject;
		PdfDirectObject aDirectObject;
		int i;
		PdfDataObject dataObject;
		PdfDictionary aDict;
		PdfName aPdfName;
		PdfTextString aPdfTextString;

		Set<Entry<PdfName, PdfDirectObject>> theEntrySet;
		Entry<PdfName, PdfDirectObject> anEntry;

		for (i = 0; i < this.complianceFile.getIndirectObjects().size(); i++) {
			anIndirectObject = this.complianceFile.getIndirectObjects().get(i);
			PlanSpaceLogger.getInstance().log_Debug("\n\n[COMPLIANCE] IndirectObject : " + (i + 1) + " of "
					+ this.complianceFile.getIndirectObjects().size());
			dataObject = anIndirectObject.getDataObject();
			if (dataObject != null) {
				PlanSpaceLogger.getInstance().log_Debug("[COMPLIANCE] DataObject: " + dataObject.getClass().getName());

				if (dataObject instanceof PdfDictionary) {
					PlanSpaceLogger.getInstance().log_Debug("dictionary: ");
					aDict = (PdfDictionary) dataObject;
					theEntrySet = aDict.entrySet();

					Iterator iter = theEntrySet.iterator();
					while (iter.hasNext()) {
						anEntry = (Entry<PdfName, PdfDirectObject>) iter.next();
						aPdfName = anEntry.getKey();
						// System.out.println("pfdName:" + aPdfName.getValue());
						if (aPdfName.getValue().equals("Contents")) {
							aDirectObject = anEntry.getValue();
							// System.out.println(aDirectObject.getClass());
							if (aDirectObject instanceof PdfTextString) {
								aPdfTextString = (PdfTextString) aDirectObject;
								System.out.println("Contents value= " + aPdfTextString.getStringValue());
							}
						}

						if (aPdfName.getValue().equals("Type")) {
							aDirectObject = anEntry.getValue();
							// System.out.println("==>Type: class " + aDirectObject.toString());
							if (aDirectObject instanceof PdfTextString) {
								aPdfTextString = (PdfTextString) aDirectObject;
								System.out.println("Type Value= " + aPdfTextString.getStringValue());
							}
						}

					}
				}
			}
		}

	}

	private void extractPages(Document theComplianceDocument) {
		Page aPDFpage;

		int i;
		this.references.clear();
		if (theComplianceDocument != null) {
			for (i = 0; i < theComplianceDocument.getPages().size(); i++) {
				PlanSpaceLogger.getInstance().log_Debug(
						"[COMPLIANCE] extracting page : " + (i + 1) + " of " + theComplianceDocument.getPages().size());
				aPDFpage = theComplianceDocument.getPages().get(i);
				// this.extractTextFromPages(aPDFpage);
				// this.extractAnnotationsFromPage(aPDFpage);
				this.ParseTheAnnotations(theComplianceDocument, aPDFpage);
			}
		}

	}

	private void printTextStrings(List<ITextString> textStrings, boolean withLocation, String description) {
		int i;
		ITextString aTextString;
		String toShow;

		if (textStrings != null) {
			System.out.println("---- " + description + " ------");
			if (textStrings.size() > 0) {
				for (i = 0; i < textStrings.size(); i++) {
					aTextString = textStrings.get(i);
					toShow = aTextString.getText();
					if (withLocation) {
						PlanSpaceLogger.getInstance()
								.log_Debug("\nTEXT [" + i + " of " + textStrings.size() + "] : " + toShow);
					} else {
						PlanSpaceLogger.getInstance()
								.log_Debug("TEXT [" + i + " of " + textStrings.size() + "] : " + toShow);
					}
					PlanSpaceLogger.getInstance()
							.log_Debug("TEXT [" + i + " of " + textStrings.size() + "] : " + toShow);
					if (withLocation) {
						PlanSpaceLogger.getInstance()
								.log_Debug("LOCATED @: [X=" + aTextString.getBox().getX() + ", Y="
										+ aTextString.getBox().getY() + ", W=" + aTextString.getBox().getWidth()
										+ ", H=" + aTextString.getBox().getHeight());
					}
				}
			} else {
				PlanSpaceLogger.getInstance().log_Debug("\nNo texts found for " + description + "\n");
			}

			System.out.println("------------------------------------");
		} else {
			PlanSpaceLogger.getInstance().log_Debug("\nNo texts found for " + description + "\n");
		}

	}

}
