package nl.geospatialAI.BIM;

import nl.geospatialAI.Case.Case;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class BIMfile {
    public enum t_BIM_activiteitenType {
    	WONEN,  BRANDSTOF, DRIJVEN, KANTOORACTIVITEITEN
    }
	
    public enum t_BIM_gebouwenType {
    	BEDRIJFSGEBOUW, HANDELSGEBOUW, HORECAGEBOUW, KANTOORGEBOUW, WOONGEBOUW
    }
    
	private String fileName;
	private boolean active;
	
	private double objectHeight;
	private double objectWidth;
	private double objectLength;
	private double footPrint;
	private double footPrintGarden;
	private boolean hasGarden;
	
    public BIMfile() {
    	this.active = false;
    	this.setFileName("?");
    }
	
	public double getObjectLength() {
		return objectLength;
	}

	public double getFootPrintGarden() {
		return footPrintGarden;
	}

	public void setFootPrintGarden(double footPrintGarden) {
		this.footPrintGarden = footPrintGarden;
	}

	public void setObjectLength(double objectLength) {
		this.objectLength = objectLength;
	}

	public double getFootPrint() {
		return footPrint;
	}

	public void setFootPrint(double footPrint) {
		this.footPrint = footPrint;
	}

	public boolean isHasGarden() {
		return hasGarden;
	}

	public void setHasGarden(boolean hasGarden) {
		this.hasGarden = hasGarden;
	}

	private t_BIM_activiteitenType activiteitType;
	private t_BIM_gebouwenType gebouwType;
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getObjectHeight() {
		return objectHeight;
	}

	public void setObjectHeight(double objectHeight) {
		this.objectHeight = objectHeight;
	}

	public double getObjectWidth() {
		return objectWidth;
	}

	public void setObjectWidth(double objectWidth) {
		this.objectWidth = objectWidth;
	}

	public t_BIM_activiteitenType getActiviteitType() {
		return activiteitType;
	}

	public void setActiviteitType(t_BIM_activiteitenType activiteitType) {
		this.activiteitType = activiteitType;
	}

	public t_BIM_gebouwenType getGebouwType() {
		return gebouwType;
	}

	public void setGebouwType(t_BIM_gebouwenType gebouwType) {
		this.gebouwType = gebouwType;
	}

	public boolean isActive() {
	
		
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void processBIMfile(ServerGlobals theServerGlobals, Case theCase) {
		theServerGlobals.log("PROCESSING BIM FILE : " + this.getFileName());

		if (this.getFileName().equals("WONING.BIM")) {
			this.setActiviteitType(BIMfile.t_BIM_activiteitenType.WONEN);
			this.setGebouwType(BIMfile.t_BIM_gebouwenType.WOONGEBOUW);
			this.setHasGarden(true);
			this.setObjectHeight(11.5);
			this.setObjectWidth(15.23);
			this.setFootPrint(147.45);
			this.setObjectLength(13.25);
			this.setFootPrintGarden(40);

		} else if (this.getFileName().equals("KANTOOR.BIM")) {
			this.setActiviteitType(BIMfile.t_BIM_activiteitenType.KANTOORACTIVITEITEN);
			this.setGebouwType(BIMfile.t_BIM_gebouwenType.KANTOORGEBOUW);

			this.setObjectHeight(15.11);
			this.setObjectWidth(32.68);
			this.setFootPrint(317.78);
			this.setObjectLength(42.5);
			this.setFootPrintGarden(115);

		} 
		else {
			this.setActive(false);
		
		}
		
	}

	public boolean getActive() {
		// TODO Auto-generated method stub
		return this.active;
	}

}
