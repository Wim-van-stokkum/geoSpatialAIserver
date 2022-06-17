package planspace.chefModelTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import application.frontend.WEB_Session;
import planspace.export.JsonExportFile;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.jsonExport.JsonPolicyItems;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_monitoringState;
import planspace.utils.PlanSpaceLogger;

public class MonitoredConcern {
	private String chefTreeContextId;
	private String chefTypeID;
	private MonitoringRegime theRegime;
	private String valueAsString;
	private t_monitoringState lastValue;

	transient ChefType theChefype;

	public MonitoredConcern() {
		this.valueAsString = "623.233";
		lastValue = t_monitoringState.UNKNOWN;
	}

	public String getChefTypeID() {
		return chefTypeID;

	}





	public String getChefTreeContextId() {
		return chefTreeContextId;
	}

	public void setChefTreeContextId(String chefTreeContextId) {
		this.chefTreeContextId = chefTreeContextId;
	}

	public t_monitoringState getLastValue() {
		return lastValue;
	}

	public void setLastValue(t_monitoringState lastValue) {
		this.lastValue = lastValue;
	}

	public String getValueAsString() {
		return valueAsString;
	}

	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}

	public void setChefTypeID(String chefTypeID) {
		this.chefTypeID = chefTypeID;
	}

	public MonitoringRegime getTheRegime() {
		return theRegime;
	}

	public void setTheRegime(MonitoringRegime theRegime) {
		this.theRegime = theRegime;
	}

	@JsonIgnore
	public HorizontalLayout getNameLabel() {
		HorizontalLayout hz;
		Image avatar;

		String nm;

		nm = "";
		if (this.getTheChefType() != null) {

			nm = this.getTheChefType().getName();
		}

		hz = new HorizontalLayout();
		avatar = this.getChefTypeAvatar();
		Label lb = new Label(nm);

		hz.add(avatar, lb);
		return hz;

	}

	@JsonIgnore
	public HorizontalLayout getLastValueLabel() {
		HorizontalLayout hz;
		Image avatar;

		hz = new HorizontalLayout();
		avatar = this.getAvatarForMonitoringState(this.lastValue);
		Label lb = new Label(String.valueOf(this.lastValue));

		hz.add(avatar, lb);
		return hz;

	}

	@JsonIgnore
	public ChefType getTheChefType() {
		ChefType myCT;

		myCT = null;
		if (this.theChefype != null) {
			myCT = this.theChefype;
		} else {
			myCT = this.loadChefType();
			this.theChefype = myCT;

		}
		return myCT;
	}

	@JsonIgnore
	private ChefType loadChefType() {
		ChefType myCT;

		myCT = null;
		if (this.chefTypeID != null) {
			myCT = InterfaceToDomainTypeRepository.getInstance()
					.findChefTypeByID(WEB_Session.getInstance().getTheDomain().getDomainCode(), this.chefTypeID);

		}
		return myCT;
	}

	@JsonIgnore
	private Image getAvatarForMonitoringState(t_monitoringState aState) {

		// avatare picture
		Image theAvatar;

		theAvatar = new Image("/state_UNKNOWN.png", "ONBEKEND");
		if (this.getTheChefType() != null) {
			if (aState != null) {
				if (aState.equals(t_monitoringState.UNKNOWN)) {
					theAvatar = new Image("/state_UNKNOWN.png", "ONBEKEND");
				} else if (aState.equals(t_monitoringState.NEUTRAL)) {
					theAvatar = new Image("/state_NEUTRAL.png", "NEUTRAAL");
				} else if (aState.equals(t_monitoringState.LOW)) {
					if (this.getTheChefType().getType().equals(t_chefType.RISK)) {
						theAvatar = new Image("/state_LOW.png", "LAAG");
					} else {
						theAvatar = new Image("/state_kans_LOW.png", "LAAG");
					}
				} else if (aState.equals(t_monitoringState.MEDIUM)) {
					if (this.getTheChefType().getType().equals(t_chefType.RISK)) {
						theAvatar = new Image("/state_MEDIUM.png", "MEDIUM");
					} else {
						theAvatar = new Image("/state_kans_MEDIUM.png", "MEDIUM");
					}
				} else if (aState.equals(t_monitoringState.HIGH)) {

					if (this.getTheChefType().getType().equals(t_chefType.RISK)) {
						theAvatar = new Image("/state_HIGH.png", "HIGH");
					} else {
						theAvatar = new Image("/state_kans_HIGH.png", "HIGH");
					}

				} else if (aState.equals(t_monitoringState.UNACCEPTABLE)) {
					theAvatar = new Image("/state_UNACCEPTABLE.png", "ONACCEPTABEL");
				} else if (aState.equals(t_monitoringState.TRUE)) {
					theAvatar = new Image("/state_TRUE.png", "WAAR");
				} else if (aState.equals(t_monitoringState.FALSE)) {
					theAvatar = new Image("/state_FALSE.png", "ONWAAR");
				} else if (aState.equals(t_monitoringState.AVAILABLE)) {
					theAvatar = new Image("/state_AVAILABLE.png", "BESCHIKBAAR");
				} else if (aState.equals(t_monitoringState.UNAVAILABLE)) {
					theAvatar = new Image("/state_UNAVAILABLE.png", "ONBESCHIKBAAR");
				} else if (aState.equals(t_monitoringState.ASSIGNED)) {
					theAvatar = new Image("/state_ASSIGNED.png", "ASSIGNED");
				} else if (aState.equals(t_monitoringState.DUE)) {
					theAvatar = new Image("/state_DUE.png", "VERLOPEN");
				} else if (aState.equals(t_monitoringState.EXECUTED)) {
					theAvatar = new Image("/state_READY.png", "GEREED");
				}

			}
			theAvatar.setWidth("38px");
			theAvatar.setHeight("38px");
		}
		return theAvatar;
	}

	@JsonIgnore
	private Image getChefTypeAvatar() {

		// avatare picture
		Image theAvatar;

		theAvatar = new Image("/icon_tree_OTHER.png", "ONBEKEND");
		if (this.getTheChefType() != null) {
			t_chefType theType = this.getTheChefType().getType();
			if (theType != null) {
				if (theType.equals(t_chefType.GOAL)) {
					theAvatar = new Image("/icon_tree_GOAL.png", "DOEL");
				} else if (theType.equals(t_chefType.OPPORTUNITY)) {
					theAvatar = new Image("/icon_tree_KANS.png", "KANS");
				} else if (theType.equals(t_chefType.RISK)) {
					theAvatar = new Image("/icon_tree_RISK.png", "RISK");
				} else if (theType.equals(t_chefType.MEASUREMENT)) {
					theAvatar = new Image("/icon_tree_ACT.png", "ACTIE");
				} else if (theType.equals(t_chefType.EVENT)) {
					theAvatar = new Image("/icon_tree_EVENT.png", "EVENT");
				} else if (theType.equals(t_chefType.RULE)) {
					theAvatar = new Image("/icon_tree_RULE.png", "RULE");
				} else if (theType.equals(t_chefType.FACT)) {
					theAvatar = new Image("/icon_tree_FACT.png", "FACT");
				} else if (theType.equals(t_chefType.NORM)) {
					theAvatar = new Image("/icon_tree_NORM.png", "NORM");
				} else if (theType.equals(t_chefType.DATA)) {
					theAvatar = new Image("/icon_tree_DATA.png", "DATA");
				} else if (theType.equals(t_chefType.DATASOURCE)) {
					theAvatar = new Image("/icon_tree_DATASOURCE.png", "BRON");
				} else if (theType.equals(t_chefType.UNKNOWN)) {
					theAvatar = new Image("/icon_tree_OTHER.png", "UNKNOWN");
				}

			}
			theAvatar.setWidth("38px");
			theAvatar.setHeight("38px");
		}
		return theAvatar;
	}

	public void exportMonitoredConcernsToJson(JsonExportFile anExport, int level, boolean recursive,
			JsonPolicyItems thePolicyItems) {

		ChefTreeContext aContext;
		
		if (this.theChefype == null) {
			this.theChefype = this.loadChefType();
		}

		if (this.theChefype != null) {
			
			thePolicyItems.addPolicyItemByChefType(this.theChefype);

			aContext = InterfaceToDomainTypeRepository.getInstance().findChefTreeContextByID(
					WEB_Session.getInstance().getTheDomain().getDomainCode(), this.getChefTreeContextId());
			if (aContext != null) {
				aContext.exportChefItemsToJson(anExport, level, recursive, thePolicyItems);

			}

		}  else {
			PlanSpaceLogger.getInstance().log_Debug("ChefType empty");
		}

	}

}
