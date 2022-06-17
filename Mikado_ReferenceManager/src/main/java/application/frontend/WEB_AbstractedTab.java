package application.frontend;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;



public class WEB_AbstractedTab {
	protected Tab myTab;
	protected VerticalLayout myContent;
	protected String myName;
	protected WEB_Workspace myWorkSpace;
	

	
	public WEB_AbstractedTab(String tabNaam,  WEB_Workspace aWorkSpace) {
		myWorkSpace= aWorkSpace;
		myName = tabNaam;
		this.myTab = new Tab(myName);
		this.myContent = new VerticalLayout();

		myTab.setSelected(false);
		myContent.setVisible(false);



	}
	

	
	public VerticalLayout getMyContent() {
		return myContent;
	}



	public void setMyContent(VerticalLayout myContent) {
		this.myContent = myContent;
	}



	public void open() {
	
		myTab.setSelected(true);
		myWorkSpace.showContent(myContent);
	}

	
	public void joinMe(WEB_MainSideTabs aSideMenu) {
		aSideMenu.registreerTab(this.myTab , this);
	}



	public void sluit() {
		System.out.println("Sluiten tab: " + myName);
		myTab.setSelected(false);
		myWorkSpace.hideContent(myContent);
	}




	



	public void prepareOpening() {
		// TODO Auto-generated method stub
		
	}



	
	
}
