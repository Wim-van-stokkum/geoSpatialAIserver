package planspace.interfaceToDomainTypeRepository.requests;

public class RequestFindChefTypeByID{
	
	String domainCode;
	String theID;
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	public String getTheID() {
		return theID;
	}
	public void setTheID(String theID) {
		this.theID = theID;
	}
	
}
