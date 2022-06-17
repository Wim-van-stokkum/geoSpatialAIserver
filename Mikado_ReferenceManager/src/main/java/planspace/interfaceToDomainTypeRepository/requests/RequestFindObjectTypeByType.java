package planspace.interfaceToDomainTypeRepository.requests;

public class RequestFindObjectTypeByType {
	
	String domainCode;
	String typeName;
	public String getDomainCode() {
		return domainCode;
	}
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	public String getTypeCode() {
		return typeName;
	}
	public void setTypeCode(String typeCode) {
		this.typeName = typeCode;
	}


	
}
