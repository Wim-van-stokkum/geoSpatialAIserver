package nl.geospatialAI.Assessment;

import nl.geospatialAI.caseContext.Location;

public class ApplicablePolicy {

	private PolicyLibrary.supportedPolicies theApplicablePolicyCode;

	double norm_max_height_house;
	double norm_max_height_office;
	double perc_water_permable;
	boolean work_home_allowed;

	public ApplicablePolicy() {
		norm_max_height_house = 9;
		norm_max_height_office = 13.5;
		perc_water_permable = 75.0;
		work_home_allowed = true;
		theApplicablePolicyCode = PolicyLibrary.supportedPolicies.POLICY_STEDENWIJK_WEERWATER_NOORD;

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
		norm_max_height_house = 5;
		norm_max_height_office = 9.5;
		perc_water_permable = 40.0;
		work_home_allowed = true;
	}

	public void setPolicyMuziekWijkZuid() {
		norm_max_height_house = 9;
		norm_max_height_office = 13.5;
		perc_water_permable = 25.0;
		work_home_allowed = true;
	}
	
	public void setPolicyMuziekWijkNoord() {
		norm_max_height_house = 6;
		norm_max_height_office = 15.5;
		perc_water_permable = 30.0;
		work_home_allowed = true;
	}
	
	public void setPolicyStedenwijkWeerwater() {
		norm_max_height_house = 7.5;
		norm_max_height_office = 20.5;
		perc_water_permable = 60.0;
		work_home_allowed = true;
	}

	public void DetermineApplicablePolicy(Location caseLocation) {

		String zipCode;

		theApplicablePolicyCode = PolicyLibrary.supportedPolicies.POLICY_STEDENWIJK_WEERWATER_NOORD;

		zipCode = caseLocation.getZipCode();
		if (zipCode.startsWith("1324")) {
			theApplicablePolicyCode = PolicyLibrary.supportedPolicies.POLICY_STEDENWIJK_WEERWATER_NOORD;
		} else if (zipCode.startsWith("1315")) {

			theApplicablePolicyCode = PolicyLibrary.supportedPolicies.POLICY_CENTRUM;
		} else if (zipCode.startsWith("1323")) {

			theApplicablePolicyCode = PolicyLibrary.supportedPolicies.POLICY_MUZIEKWIJK_ZUID;
		} else if (zipCode.startsWith("1312")) {

			theApplicablePolicyCode = PolicyLibrary.supportedPolicies.POLICY_MUZIEKWIJK_NOORD;
		}

	}

	public PolicyLibrary.supportedPolicies getApplicablePolicyCode() {
		return this.theApplicablePolicyCode;
	}

	public String getApplicablePolicyName() {
		String theName;

		theName = "Bestemmingsplan Almere Stedenwijk en Weerwater Noord";
		if (theApplicablePolicyCode.equals(PolicyLibrary.supportedPolicies.POLICY_STEDENWIJK_WEERWATER_NOORD)) {
			theName = "Bestemmingsplan Almere Stedenwijk en Weerwater Noord.";
		} else if (theApplicablePolicyCode.equals(PolicyLibrary.supportedPolicies.POLICY_CENTRUM)) {
			theName = "Bestemmingsplan Almere Centrum.";
		} else if (theApplicablePolicyCode.equals(PolicyLibrary.supportedPolicies.POLICY_MUZIEKWIJK_NOORD)) {
			theName = "Bestemmingsplan Almere Muziekwijk Noord.";
		} else if (theApplicablePolicyCode.equals(PolicyLibrary.supportedPolicies.POLICY_MUZIEKWIJK_ZUID)) {
			theName = "Bestemmingsplan Almere Muziekwijk Zuid.";
		}

		return theName;
	}
}
