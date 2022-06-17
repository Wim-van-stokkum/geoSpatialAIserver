package planspace.interfaceToDomainTypeRepository.responses;

import planspace.domainTypes.ObjectType;

public class ResponseObjectTypeFound {
	ObjectType theObjectType;
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
	
	public ObjectType getTheObjectType() {
		return theObjectType;
	}

	public void setTheObjectType(ObjectType theObjectType) {
		this.theObjectType = theObjectType;
	}

	


}
