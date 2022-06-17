package planspace.chefModelTypes;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import planspace.caseModel.CaseType;
import planspace.chefModelTypes.MonitoringRegime.t_monitoringFrequency;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;

public class MonitoredConcernItem {
	private ChefTaxonomyItem myChefTaxonomyItem;
	private MonitoredConcern theConcern;
	private VerticalLayout myGuiPresence;
	private RadioButtonGroup<String> regimeOptions;
	private CaseType theCaseTypeBeingEdited;

	public MonitoredConcernItem() {
		// TODO Auto-generated constructor stub
	}

	public MonitoredConcern getTheConcern() {
		return theConcern;
	}

	public void setTheConcernForChefType(MonitoredConcern theConcern, ChefType theChefType) {
		this.theConcern = theConcern;
		this.setTheChefType(theChefType);
	}

	public   VerticalLayout getMyTreeGUIpresence() {
		HorizontalLayout theChefTaxGui;
		
		
		if (this.myChefTaxonomyItem != null) {
			theChefTaxGui = myChefTaxonomyItem.getMyTreeGUIpresence();
			myGuiPresence = new VerticalLayout();
			createRegimeOptions();
			myGuiPresence.add(theChefTaxGui, regimeOptions);
			return myGuiPresence;
		}
		else
		{ return null;
			}
	}

	private void createRegimeOptions() {
	
		regimeOptions = new RadioButtonGroup<String>();
		regimeOptions.setLabel("Frequentie" );
		regimeOptions.setItems("Op verzoek", "Continue", "Geplanned");
		System.out.println(" Concern freq "  + this.theConcern.getTheRegime().getMonitoringFrequency());
		System.out.println(" Concern freq "  + MonitoringRegime.getInformalFrequencyForFormal(t_language.NL, this.theConcern.getTheRegime().getMonitoringFrequency()));
		regimeOptions.setValue(MonitoringRegime.getInformalFrequencyForFormal(t_language.NL, this.theConcern.getTheRegime().getMonitoringFrequency()));
		
		regimeOptions.addValueChangeListener(listener -> {
			if (!listener.getOldValue().equals(regimeOptions.getValue()))
			{
				this.changeRegimeFrequency(MonitoringRegime.getFormalFrequencyForInformal(t_language.NL,
					regimeOptions.getValue()));
			}
			
		});
		
		
	}

	private void changeRegimeFrequency(t_monitoringFrequency formalFrequencyForInformal) {
		
		this.theConcern.getTheRegime().setMonitoringFrequency(formalFrequencyForInformal);
		this.theCaseTypeBeingEdited.updateYourself();
		
		
	}



	public void createForTaxonomyItem(ChefTaxonomyItem anTaxItem) {
		if (anTaxItem != null) {

		}
	}

	public void openReferenceDocuments() {
		if (this.myChefTaxonomyItem != null) {
			myChefTaxonomyItem.getTheChefType().openReferenceDocuments();

		}

	}

	public void setTheChefType(ChefType theChefType) {
		if (this.myChefTaxonomyItem == null) {
			this.myChefTaxonomyItem = new ChefTaxonomyItem();

		}
		this.myChefTaxonomyItem.setTheChefType(theChefType);

	}

	public void setMonitoringCaseType(CaseType caseTypeBeingEdited) {
	    this.theCaseTypeBeingEdited =caseTypeBeingEdited;
		
	}

}
