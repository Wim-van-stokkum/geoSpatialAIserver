package planspace.interfaceToDomainTypeRepository.responses;

import planspace.domainTypes.DataPointType;

public class ResponseDataPointTypeFound {
	DataPointType theDataPointFound;
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
	
	public DataPointType getTheDataPointFound() {
		return theDataPointFound;
	}

	public void setTheDataPointFound(DataPointType theDataPointFound) {
		this.theDataPointFound = theDataPointFound;
	}


}
