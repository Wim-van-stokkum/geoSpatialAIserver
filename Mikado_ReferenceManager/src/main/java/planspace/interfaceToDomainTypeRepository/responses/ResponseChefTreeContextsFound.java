package planspace.interfaceToDomainTypeRepository.responses;
import java.util.List;

import planspace.chefModelTypes.ChefTreeContext;

public class ResponseChefTreeContextsFound {
	List<ChefTreeContext> theTreeContexts;
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
	public List<ChefTreeContext> getTheTreeContexts() {
		return theTreeContexts;
	}
	public void setTheTreeContexts(List<ChefTreeContext> theTreeContexts) {
		this.theTreeContexts = theTreeContexts;
	}


	
	
}
