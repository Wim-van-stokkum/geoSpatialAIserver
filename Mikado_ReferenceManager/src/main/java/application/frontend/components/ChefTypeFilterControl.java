package application.frontend.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.combobox.ComboBox;

import planspace.chefModelTypes.ChefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;

public class ChefTypeFilterControl extends ComboBox<String> {

	private enum t_filterMode {
		SPECIFIC, ALL
	};

	private static final long serialVersionUID = -1207446710309122813L;
	private List<String> chefTypeFilterOptions;
	private t_filterMode filterMode;
	private List<t_chefType> typesAllowedByFilter;
	private ChefTaxonomyGridEditor parentChefTaxonomyGridEditor;
	private PolicyReferenceWindow parentPolicyReferenceWindow;
	private ChefTaxonomyBrowser parentChefTaxonomyBrowser;


	public ChefTypeFilterControl() {
		super();
		init();

	}

	public ChefTypeFilterControl(ChefTaxonomyGridEditor aChefTaxonomyGridEditor) {
		super();
		init();
		this.parentChefTaxonomyGridEditor = aChefTaxonomyGridEditor;
		
	}
	
	public ChefTypeFilterControl(ChefTaxonomyBrowser aChefTaxonomyBrowser) {
		super();
		init();
		this.parentChefTaxonomyBrowser = aChefTaxonomyBrowser;
		
	}
	
	public ChefTypeFilterControl(PolicyReferenceWindow aPolicyReferenceWindow) {
		super();
		init();
		this.parentPolicyReferenceWindow = aPolicyReferenceWindow;
		
	}
	
	


	public t_filterMode getFilterMode() {
		return filterMode;
	}

	public void setFilterMode(t_filterMode filterMode) {
		this.filterMode = filterMode;
	}

	public List<t_chefType> getTypesAllowedByFilter() {
		return typesAllowedByFilter;
	}

	public void setTypesAllowedByFilter(List<t_chefType> typesAllowedByFilter) {
		this.typesAllowedByFilter = typesAllowedByFilter;
	}

	private void init() {
		chefTypeFilterOptions = new ArrayList<String>();
		typesAllowedByFilter = new ArrayList<t_chefType>();
		enableAllFilterOptions();

		parentChefTaxonomyGridEditor = null;
		this.addValueChangeListener(event -> {
			String seloption = (String) event.getValue();
			handleFilterChanged(seloption);
			this.notifyParent();
		});

		this.setAnnotationModeByString("Alle");
	}

	private void notifyParent() {
		if (parentChefTaxonomyGridEditor != null) {
	//		System.out.println ("Notifying : " + this.typesAllowedByFilter.toString()); 
			parentChefTaxonomyGridEditor.notifyFilterSet();
		}
		if (parentPolicyReferenceWindow != null) {
			parentPolicyReferenceWindow.notifyFilterSet();
		}
		if (this.parentChefTaxonomyBrowser != null) {
			parentChefTaxonomyBrowser.notifyFilterSet();
		}

	}

	private void handleFilterChanged(String selectedOption) {
	//	 System.out.println("option selected : " + selectedOption);
		this.setAnnotationModeByString(selectedOption);
		this.notifyParent();

	}

	public void enableAllFilterOptions() {
		this.filterMode = t_filterMode.ALL;
		this.chefTypeFilterOptions.clear();
		this.chefTypeFilterOptions.addAll(ChefType.getTypesAsStringList(true));
		this.chefTypeFilterOptions.add("Alle");
		this.setItems(chefTypeFilterOptions);
		this.setValue("Alle");

	}

	public void setSpecificFilterOptions(t_chefType aSpecificType) {
		this.chefTypeFilterOptions.clear();
		this.filterMode = t_filterMode.SPECIFIC;
		if (aSpecificType.equals(t_chefType.EVENT)) {
			this.chefTypeFilterOptions.add("Gebeurtenis/aanleiding");
		} else if (aSpecificType.equals(t_chefType.FACT)) {
			this.chefTypeFilterOptions.add("Feit");
		} else if (aSpecificType.equals(t_chefType.GOAL)) {
			this.chefTypeFilterOptions.add("Doelstelling");
		} else if (aSpecificType.equals(t_chefType.MEASUREMENT)) {
			this.chefTypeFilterOptions.add("Maatregel");
		} else if (aSpecificType.equals(t_chefType.NORM)) {
			this.chefTypeFilterOptions.add("Norm");
		} else if (aSpecificType.equals(t_chefType.OPPORTUNITY)) {
			this.chefTypeFilterOptions.add("Kans");
		} else if (aSpecificType.equals(t_chefType.RISK)) {
			this.chefTypeFilterOptions.add("Risico");
		} else if (aSpecificType.equals(t_chefType.RULE)) {
			this.chefTypeFilterOptions.add("Beleidsregel");
		} else if (aSpecificType.equals(t_chefType.DATA)) {
			this.chefTypeFilterOptions.add("Gegeven");
		} else if (aSpecificType.equals(t_chefType.DATASOURCE)) {
			this.chefTypeFilterOptions.add("Gegevensbron");
		} else if (aSpecificType.equals(t_chefType.UNKNOWN)) {
			this.chefTypeFilterOptions.add("Te bepalen");
		}

	}

	private void setAnnotationModeByString(String annoFilter) {
		typesAllowedByFilter.clear();
		if (annoFilter.equals("Gebeurtenis/aanleiding")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.EVENT);
		} else if (annoFilter.equals("Feit")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.FACT);
		} else if (annoFilter.equals("Doelstelling")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.GOAL);
		} else if (annoFilter.equals("Maatregel")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.MEASUREMENT);
		} else if (annoFilter.equals("Kans")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.OPPORTUNITY);
		} else if (annoFilter.equals("Risico")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.RISK);
		} else if (annoFilter.equals("Beleidsregel")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.RULE);
		} else if (annoFilter.equals("Norm")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.NORM);
		} else if (annoFilter.equals("Gegeven")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.DATA);
		} else if (annoFilter.equals("Gegevensbron")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.DATASOURCE);
		} else if (annoFilter.equals("Te bepalen")) {
			this.filterMode = t_filterMode.SPECIFIC;
			this.typesAllowedByFilter.add(t_chefType.UNKNOWN);
		} else if (annoFilter.equals("Alle")) {
			this.filterMode = t_filterMode.ALL;
			this.typesAllowedByFilter.add(t_chefType.EVENT);
			this.typesAllowedByFilter.add(t_chefType.FACT);
			this.typesAllowedByFilter.add(t_chefType.GOAL);
			this.typesAllowedByFilter.add(t_chefType.MEASUREMENT);
			this.typesAllowedByFilter.add(t_chefType.OPPORTUNITY);
			this.typesAllowedByFilter.add(t_chefType.RISK);
			this.typesAllowedByFilter.add(t_chefType.RULE);
			this.typesAllowedByFilter.add(t_chefType.NORM);
			this.typesAllowedByFilter.add(t_chefType.DATA);
			this.typesAllowedByFilter.add(t_chefType.DATASOURCE);
		}
	}
}
