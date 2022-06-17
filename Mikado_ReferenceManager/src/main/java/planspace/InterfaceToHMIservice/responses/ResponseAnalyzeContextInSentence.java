package planspace.InterfaceToHMIservice.responses;

public class ResponseAnalyzeContextInSentence {
	String status;


	String analyzedSentence;

	ResponsePartWordClassificationContext analysisResult;

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

	public ResponsePartWordClassificationContext getAnalysisResult() {
		return analysisResult;
	}

	public void setAnalysisResult(ResponsePartWordClassificationContext analysisResult) {
		this.analysisResult = analysisResult;
	}







}