package planspace.interfaceToDomainTypeRepository.requests;

public class RequestDeleteChefTypeInDomain {
	
	String domainCode;
	String theChefTypeID;

	public String getDomainCode() {
		return domainCode;
	}

	public void setaDomainCode(String aDomainCode) {
		this.domainCode = aDomainCode;
	}

	public String getTheChefTypeID() {
		return theChefTypeID;
	}

	public void setTheChefTypeID(String theChefTypeID) {
		this.theChefTypeID = theChefTypeID;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	
}
