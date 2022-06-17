package application.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;


@Route
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "PlanSpace agent", shortName = "Plan Space agent")

public class MainView extends AppLayout {



	/**
	 * 
	 */
	private static final long serialVersionUID = 3382318774441976348L;
	@SuppressWarnings("unused")
	private WEB_Workspace myMainWorkSpace;

	public MainView() {
		myMainWorkSpace = new WEB_Workspace(this);
	
		
		Image img = new Image("/planspace.png", "PlanSpace logo");
		img.setHeight("44px");

		addToNavbar(new DrawerToggle(), img);
	}
}
