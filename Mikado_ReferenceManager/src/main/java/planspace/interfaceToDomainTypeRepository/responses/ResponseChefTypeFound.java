package planspace.interfaceToDomainTypeRepository.responses;

import planspace.chefModelTypes.ChefType;

public class ResponseChefTypeFound {
	ChefType theChefType;
	String status;
	String domainCode;
	public ChefType getTheChefType() {
		return theChefType;
	}
	public void setTheChefType(ChefType theChefType) {
		this.theChefType = theChefType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	
	
	
}
