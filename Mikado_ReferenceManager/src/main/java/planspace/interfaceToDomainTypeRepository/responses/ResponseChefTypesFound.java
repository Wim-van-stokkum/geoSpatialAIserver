package planspace.interfaceToDomainTypeRepository.responses;

import java.util.ArrayList;
import java.util.List;

import planspace.chefModelTypes.ChefType;

public class ResponseChefTypesFound {
	List<ChefType> theChefTypes;
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

	public List<ChefType> getTheChefTypes() {
		return theChefTypes;
	}

	public void setTheChefTypes(List<ChefType> theChefTypes) {
		this.theChefTypes = theChefTypes;
	}
	
	public void addChefType(ChefType  aChefType) {
		if (this.theChefTypes.contains(aChefType)) {
			this.theChefTypes.add(aChefType);
		}
	}
	
	
}
