package planspace.InterfaceToHMIservice.responses;

import com.google.gson.Gson;

public class ResponseAnalyzeConditionInSentence {
	String status;
	String domainCode;
	

	String analyzedSentence;

	ResponsePartWordClassificationContext analysisResult;

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

	public String toJson() {
		/* Deze methode kan in instantie van deze class omzetten naar Json m.b.v de JackSon Library (zie POM.XML)
		 * Doel is deze Json op te slaan in een Mongo database. 
		 * Mongo accepteert echter geen JSON als String, maar vereist de binaire versie daarvan BSON/Document
		 */
		
		Gson gsonParser;
		String meAsJson;


		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);
	

		return meAsJson;
	}







}