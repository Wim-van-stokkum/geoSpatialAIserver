package planspace.interfaceToDomainTypeRepository.responses;

import java.util.List;

import planspace.domainTypes.DomainType;

public class ResponseGetAllDomains {
	List<DomainType> allDomains;

	public List<DomainType> getAllDomains() {
		return allDomains;
	}

	public void setAllDomains(List<DomainType> allDomains) {
		this.allDomains = allDomains;
	}

}
