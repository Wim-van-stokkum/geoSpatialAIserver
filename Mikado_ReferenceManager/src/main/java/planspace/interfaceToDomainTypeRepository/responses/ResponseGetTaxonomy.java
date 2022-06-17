package planspace.interfaceToDomainTypeRepository.responses;

import java.util.List;

import planspace.domainTypes.TaxonomyNode;

public class ResponseGetTaxonomy {
	TaxonomyNode theRootNode;
	String status;
	String domainCode;
	
	public String getDomainCode() {
		return domainCode;
	}

	public void setStatus(String aStatus) {
     this.status = aStatus;
		
	}



	public TaxonomyNode getTheRootNode() {
		return theRootNode;
	}

	public void setTheRootNode(TaxonomyNode theRootNode) {
		this.theRootNode = theRootNode;
	}

	public String getStatus() {
		return status;
	}

	public void setDomainCode(String theDomainCode) {
		this.domainCode = theDomainCode;
		
	}
	
	
}
