package planspace.interfaceToDomainTypeRepository.requests;

import java.util.Optional;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;

public class RequestFindChefTypeByType{
	
	String domainCode;
	String theType;
	String theTheme;
	
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	public String getTheType() {
		return theType;
	}
	public void setTheType(String theType) {
		this.theType = theType;
	}
	public String getTheTheme() {
		return theTheme;
	}
	public void setTheTheme(String theTheme) {
		this.theTheme = theTheme;
	}
	

	
}
