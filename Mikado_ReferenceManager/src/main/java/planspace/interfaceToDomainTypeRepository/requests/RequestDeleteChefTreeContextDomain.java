package planspace.interfaceToDomainTypeRepository.requests;

public class RequestDeleteChefTreeContextDomain {
	
	String domainCode;
	String theChefTreeContextID;

	public String getDomainCode() {
		return domainCode;
	}

	public void setaDomainCode(String aDomainCode) {
		this.domainCode = aDomainCode;
	}

	public String getTheChefTreeContextID() {
		return theChefTreeContextID;
	}

	public void setTheChefTreeContextID(String theChefTreeContextID) {
		this.theChefTreeContextID = theChefTreeContextID;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	
}
