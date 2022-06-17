package planspace.InterfaceToHMIservice.responses;

public class ResponseAnalyzeYesNoAnswerInSentence {
	String status;
	Boolean isPositive;
	Boolean isUnknown;
	Boolean YesOrNo;
	

	String analyzedSentence;
	
	public ResponseAnalyzeYesNoAnswerInSentence() {
		isPositive = null;
		isUnknown = null;
		YesOrNo = false;
	}
	

	public Boolean getYesOrNo() {
		return YesOrNo;
	}


	public void setYesOrNo(Boolean yesOrNo) {
		YesOrNo = yesOrNo;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getAnalyzedSentence() {
		return analyzedSentence;
	}

	public void setAnalyzedSentence(String analyzedSentence) {
		this.analyzedSentence = analyzedSentence;
	}

	public Boolean getIsPositive() {
		return isPositive;
	}

	public void setIsPositive(Boolean isPositive) {
		this.isPositive = isPositive;
	}

	public Boolean getIsUnknown() {
		return isUnknown;
	}

	public void setIsUnknown(Boolean isUnknown) {
		this.isUnknown = isUnknown;
	}







}