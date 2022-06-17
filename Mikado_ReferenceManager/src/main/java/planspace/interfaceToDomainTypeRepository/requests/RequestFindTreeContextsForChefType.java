package planspace.interfaceToDomainTypeRepository.requests;

public class RequestFindTreeContextsForChefType{
	

	String theID;
	String domainCode;
	
	public String getTheID() {
		return theID;
	}
	public void setTheID(String theID) {
		this.theID = theID;
	}
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	
}
