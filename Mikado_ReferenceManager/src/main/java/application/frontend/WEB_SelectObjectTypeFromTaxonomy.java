package application.frontend;

import java.util.Iterator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;

import application.frontend.components.ContextAspectEditor;
import planspace.domainTypes.DomainType;
import planspace.domainTypes.TaxonomyNode;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.utils.PlanSpaceLogger;
import speechInterface.MatchingTaxonomyConcept;
import speechInterface.SpeechInterface;

public class WEB_SelectObjectTypeFromTaxonomy {

	public enum t_status {
		SELECTED, OPENED, CANCELED
	}

	private Dialog selectDialog;
	public TaxonomyNode selectedConcept;
	public t_status myStatus;
	private IFC_ObjectTypeRequester answerTo;

	public static WEB_SelectObjectTypeFromTaxonomy stdObjectSelector = null;

	public static WEB_SelectObjectTypeFromTaxonomy getInstance() {

	return new WEB_SelectObjectTypeFromTaxonomy() ;

	}
	
	
	public void selectForMe(IFC_ObjectTypeRequester anObjectRequesting) {
		
	
		Dialog treeDialog;

		HorizontalLayout diaHorLayout;
		Button closeButton, selectButton;
		VerticalLayout diaLayout;
		TreeGrid<TaxonomyNode> aTree;
		TaxonomyNode theTaxonomy;
		WEB_Session theSession;
		InterfaceToDomainTypeRepository theDomainModelAPI;
		DomainType theDomain;
		MatchingTaxonomyConcept theMatchingTaxonomyConcept;

		/* prepare taxonomy */

		if (anObjectRequesting != null) {
			this.answerTo = anObjectRequesting;
			myStatus = t_status.OPENED;
			selectDialog = null;
			theSession = WEB_Session.getInstance();
			if (theSession != null) {
				theDomain = theSession.getTheDomain();
				if (theDomain != null) {

					theTaxonomy = theSession.getDomainTaxonomy();
					
					if (theTaxonomy != null) {
				//		PlanSpaceLogger.getInstance().log("[INFO] Reusing taxonomy");

						treeDialog = new Dialog();
						selectDialog = treeDialog;
						diaLayout = new VerticalLayout();
						diaHorLayout = new HorizontalLayout();

						closeButton = new Button("Sluiten");
						selectButton = new Button("Selecteren");

						diaHorLayout.add(selectButton, closeButton);

						closeButton.addClickListener(e -> {
							myStatus = t_status.CANCELED;

							treeDialog.close();

						});

						selectButton.addClickListener(e -> {
							if (this.selectedConcept != null) {
								this.registerSelectedConcept();
								myStatus = t_status.SELECTED;
							}
							try {
							
								this.answerTo.acceptSelectedObjectType(selectedConcept);
							
							} catch (Exception f) {
								// ignore
							}

							treeDialog.close();

						});

		
			
						aTree = this.createInMemoryTaxonomyTree();
				
						
						
						aTree.setSizeFull();

						diaLayout.add(aTree);
						diaLayout.add(diaHorLayout);
						treeDialog.add(diaLayout);

						aTree.setMinHeight("860px");
						aTree.setMinWidth("860px");
						treeDialog.setMinWidth("880px");
						treeDialog.setMinHeight("880px");
						diaLayout.setMinWidth("900px");
						diaLayout.setMinHeight("900px");
						treeDialog.setModal(true);
						treeDialog.open();
					} else {

					}

				}
			}
			 else {
					PlanSpaceLogger.getInstance().log("ERROR: no session to select concept");
				}

		} else {
			PlanSpaceLogger.getInstance().log("Error selection request for ObjectType form unknown TAB");
		}
	}

	private void listenOut() {
		// TODO Auto-generated method stub
		SpeechInterface theSpeechInterface;
		String hearedSentence;

		theSpeechInterface = SpeechInterface.getInstance();

		hearedSentence = theSpeechInterface.listenToMic();
		
	}




	private TreeGrid<TaxonomyNode> createInMemoryTaxonomyTree() {
		TaxoGridService taxoService = new TaxoGridService();
		TreeGrid<TaxonomyNode> grid = new TreeGrid<>();

		grid.setItems(taxoService.getRootNodes(), taxoService::getChildNodes);

		grid.addHierarchyColumn(TaxonomyNode::getTreeName).setHeader("Concept");
		grid.addColumn(TaxonomyNode::getTreeDescription).setHeader("Omschrijving");

		grid.addItemClickListener(event -> {
		//	PlanSpaceLogger.getInstance().log("Now selected: " + event.getItem().getCode());
			this.selectedConcept = event.getItem();

		});

		grid.addExpandListener(event -> {

			TaxonomyNode aNode;

			Iterator<TaxonomyNode> itr = event.getItems().iterator();
			while (itr.hasNext()) {
				aNode = itr.next();
			//	PlanSpaceLogger.getInstance().log("Now selected: " + aNode.getCode());
				this.selectedConcept = aNode;
			}

		});

		return grid;
	}

	private void registerSelectedConcept() {

		int aspectNO;
		String codeToSet = selectedConcept.getCode();

		//PlanSpaceLogger.getInstance().log("REGISTER SELECTED VALUE");

	}







}
