package application.frontend;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class WEB_MainSideTabs {
	private Tabs myTabs;
	private Map<Tab, WEB_AbstractedTab> mytabsRouting;
	private WEB_Workspace myWorkSpace;
	private WEB_AbstractedTab huidigeTab;

	public String myName = "Hoofdtab";

	@SuppressWarnings("unused")
	private WEB_DomainTab domainTab;
	private WEB_ReferenceTab referenceTab;
	private WEB_SimulateTab simulateTab;

	public WEB_MainSideTabs(WEB_Workspace aWEB_Workspace) {
		this.myWorkSpace = aWEB_Workspace;
		this.myTabs = new Tabs();
		myTabs.setOrientation(Tabs.Orientation.VERTICAL);
		mytabsRouting = new HashMap<>();
		initTabView();
		initTabhandler();
	}

	private void initTabView() {

		domainTab = toevoegenDomainTab("Domein");
		referenceTab = toevoegenReferenceTab("Compliance");
		simulateTab =  toevoegenSimuleerTab("Simuleer");
		myWorkSpace.registreerMainSideTabs(this.myTabs);
		huidigeTab = domainTab;
		huidigeTab.open();
	}
	
	private WEB_ReferenceTab toevoegenReferenceTab(String theName) {
		WEB_ReferenceTab newTab;
		newTab = new WEB_ReferenceTab(theName, myWorkSpace);
		newTab.joinMe(this);
		return newTab;
	}
	
	private WEB_SimulateTab toevoegenSimuleerTab(String theName) {
		WEB_SimulateTab newTab;
		newTab = new WEB_SimulateTab(theName, myWorkSpace);
		newTab.joinMe(this);
		return newTab;
	}





	private void initTabhandler() {

		this.myTabs.addSelectedChangeListener(event -> {
			huidigeTab.sluit();
			huidigeTab = mytabsRouting.get(myTabs.getSelectedTab());
			huidigeTab.prepareOpening();
			huidigeTab.open();
		});
	}



	private WEB_DomainTab toevoegenDomainTab(String tabNaam) {
		WEB_DomainTab newTab;
		newTab = new WEB_DomainTab(tabNaam, myWorkSpace);
		newTab.joinMe(this);
		return newTab;

	}



	public void registreerTab(Tab aTab, WEB_AbstractedTab theCorrespondingTab) {

		myTabs.add(aTab);

		mytabsRouting.put(aTab, theCorrespondingTab);
	}

	public void voorbereidenTabWissel() {

	}

}
