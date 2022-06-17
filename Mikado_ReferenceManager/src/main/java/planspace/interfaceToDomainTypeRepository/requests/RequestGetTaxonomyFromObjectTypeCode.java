package planspace.interfaceToDomainTypeRepository.requests;

import planspace.domainTypes.ObjectType;

public class RequestGetTaxonomyFromObjectTypeCode {

   String domainCode;
   String startFromType;
public String getDomainCode() {
	return domainCode;
}
public void setDomainCode(String domainCode) {
	this.domainCode = domainCode;
}
public String getStartFromType() {
	return startFromType;
}
public void setStartFromType(String startFromType) {
	this.startFromType = startFromType;
}
   
   

}
