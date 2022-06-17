package planspace.interfaceToDomainTypeRepository.requests;

public class RequestFindDataPointTypeByType {
	
	String domainCode;
	String typeName;
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


}
