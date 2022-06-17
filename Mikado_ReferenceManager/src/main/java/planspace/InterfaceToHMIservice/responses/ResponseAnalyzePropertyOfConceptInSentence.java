package planspace.InterfaceToHMIservice.responses;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import speechInterface.ParsedConceptProperty;

public class ResponseAnalyzePropertyOfConceptInSentence {
	String status;
	String domainCode;
	String analyzedSentence;

	List<ParsedConceptProperty> theParsedConceptProperties;
	
	public ResponseAnalyzePropertyOfConceptInSentence() {
		theParsedConceptProperties = new ArrayList<ParsedConceptProperty>();
	}

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

	public List<ParsedConceptProperty> getTheParsedConceptProperties() {
		return theParsedConceptProperties;
	}

	public void setTheParsedConceptProperties(List<ParsedConceptProperty> theParsedConceptProperties) {
		this.theParsedConceptProperties = theParsedConceptProperties;
	}

	/**********
	 * methods
	 */
	
	public void addParsedConceptProperty(ParsedConceptProperty theProp) {
		if(this.theParsedConceptProperties == null) {
			theParsedConceptProperties = new ArrayList<ParsedConceptProperty>();
		}
		if (!theParsedConceptProperties.contains(theProp)) {
			theParsedConceptProperties.add(theProp);
		}
	}


	public String toJson() {
		/*
		 * Deze methode kan in instantie van deze class omzetten naar Json m.b.v de
		 * JackSon Library (zie POM.XML) Doel is deze Json op te slaan in een Mongo
		 * database. Mongo accepteert echter geen JSON als String, maar vereist de
		 * binaire versie daarvan BSON/Document
		 */

		Gson gsonParser;
		String meAsJson;

		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);

		return meAsJson;
	}


}