package planspace.interfaceToDomainTypeRepository.responses;

import planspace.chefModelTypes.ChefTreeContext;
import planspace.chefModelTypes.ChefType;

public class ResponseChefTreeContextFound {
ChefTreeContext theChefTreeContext;
	String status;
	String domainCode;
	
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
	public ChefTreeContext getTheChefTreeContext() {
		return theChefTreeContext;
	}
	public void setTheChefTreeContext(ChefTreeContext theChefTreeContext) {
		this.theChefTreeContext = theChefTreeContext;
	}
	
	
	
}
