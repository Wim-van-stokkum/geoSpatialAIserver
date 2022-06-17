package application.frontend;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;



public class WEB_Workspace {
	

	private MainView theMainView;
	private WEB_MainSideTabs mySideTabs;


	public WEB_Workspace(MainView aMainView) {
		theMainView = aMainView;
	  
        mySideTabs = new WEB_MainSideTabs(this);
	//	myServices = new SERVICES_PolicyChecker();

	}

	public void registreerMainSideTabs(Tabs theTabs) {
		theMainView.addToDrawer(theTabs);
	}

	public void showContent(VerticalLayout myContent) {

		theMainView.setContent(myContent);
		myContent.setVisible(true);
	}

	public void hideContent(VerticalLayout myContent) {
///

		myContent.setVisible(false);
	}

	

	public void openVraag(Dialog myDialog) {
		// TODO Auto-generated method stub
		this.theMainView.setContent(myDialog);
		myDialog.open();
	}

}
