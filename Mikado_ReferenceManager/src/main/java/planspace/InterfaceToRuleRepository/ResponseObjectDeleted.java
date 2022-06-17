package planspace.InterfaceToRuleRepository;


public class ResponseObjectDeleted {

	private String status;
	private String deletedID;
	private String domainCode;

	public void setStatus(String newStatus) {
		this.status = newStatus;

	}

	


	public String getDeletedID() {
		return deletedID;
	}




	public void setDeletedID(String deletedID) {
		this.deletedID = deletedID;
	}




	public String getDomainCode() {
		return domainCode;
	}




	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}




	public String getStatus() {
		return status;
	}
	

}
