package nl.geospatialAI.Assessment;

public class ApplicablePolicy {

	double norm_max_height_house;
	double norm_max_height_office;
	double perc_water_permable;
	boolean work_home_allowed;
	
	public ApplicablePolicy() {
		norm_max_height_house = 9;
		norm_max_height_office = 13.5;
		perc_water_permable = 75.0;
		work_home_allowed = true;
	}

	public double getNorm_max_height_house() {
		return norm_max_height_house;
	}

	public void setNorm_max_height_house(double norm_max_height_house) {
		this.norm_max_height_house = norm_max_height_house;
	}

	public double getNorm_max_height_office() {
		return norm_max_height_office;
	}

	public void setNorm_max_height_office(double norm_max_height_office) {
		this.norm_max_height_office = norm_max_height_office;
	}

	public double getPerc_water_permable() {
		return perc_water_permable;
	}

	public void setPerc_water_permable(double perc_water_permable) {
		this.perc_water_permable = perc_water_permable;
	}

	public boolean isWork_home_allowed() {
		return work_home_allowed;
	}

	public void setWork_home_allowed(boolean work_home_allowed) {
		this.work_home_allowed = work_home_allowed;
	}
	
	
	public void setPolicyCentrum() {
		norm_max_height_house = 6;
		norm_max_height_office = 9.5;
		perc_water_permable = 40.0;
		work_home_allowed = true;
	}
	
	public void setPolicyMuziekWijk() {
		norm_max_height_house = 9;
		norm_max_height_office = 13.5;
		perc_water_permable = 25.0;
		work_home_allowed = true;
	}
	
}
