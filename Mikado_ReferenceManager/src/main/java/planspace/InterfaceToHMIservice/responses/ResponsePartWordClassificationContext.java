package planspace.InterfaceToHMIservice.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_aspectInSentence;
import speechInterface.WordClassification;

public class ResponsePartWordClassificationContext {

	WordClassification wc_how;
	WordClassification wc_what;
	WordClassification wc_who;
	WordClassification wc_when;
	WordClassification wc_where;
	WordClassification wc_why;

	List<String> potentialNewWords;

	public WordClassification getWc_how() {
		return wc_how;
	}

	public void setWc_how(WordClassification wc_how) {
		this.wc_how = wc_how;
	}

	public WordClassification getWc_what() {
		return wc_what;
	}

	public void setWc_what(WordClassification wc_what) {
		this.wc_what = wc_what;
	}

	public WordClassification getWc_who() {
		return wc_who;
	}

	public void setWc_who(WordClassification wc_who) {
		this.wc_who = wc_who;
	}

	public WordClassification getWc_when() {
		return wc_when;
	}

	public void setWc_when(WordClassification wc_when) {
		this.wc_when = wc_when;
	}

	public WordClassification getWc_where() {
		return wc_where;
	}

	public void setWc_where(WordClassification wc_where) {
		this.wc_where = wc_where;
	}

	public WordClassification getWc_why() {
		return wc_why;
	}

	public void setWc_why(WordClassification wc_why) {
		this.wc_why = wc_why;
	}

	public List<String> getPotentialNewWords() {
		return potentialNewWords;
	}

	public void setPotentialNewWords(List<String> potentialNewWords) {
		this.potentialNewWords = potentialNewWords;
	}

	@JsonIgnore()
	public WordClassification getWordClassificationForAspect(t_aspectInSentence anAspect) {
		WordClassification theWordClassification;

		theWordClassification = null;

		if (anAspect.equals(t_aspectInSentence.HOW)) {
			theWordClassification = this.getWc_how();
		} else if (anAspect.equals(t_aspectInSentence.WHAT)) {
			theWordClassification = this.getWc_what();
		} else if (anAspect.equals(t_aspectInSentence.WHEN)) {
			theWordClassification = this.getWc_when();
		} else if (anAspect.equals(t_aspectInSentence.WHERE)) {
			theWordClassification = this.getWc_where();
		} else if (anAspect.equals(t_aspectInSentence.WHY)) {
			theWordClassification = this.getWc_why();
		} else if (anAspect.equals(t_aspectInSentence.WHO)) {
			theWordClassification = this.getWc_who();
		}

		return theWordClassification;
	}
	
	@JsonIgnore
	public String generateInformalDescription() {
		String theDescription;
		
		theDescription = "";
		if (this.wc_how != null) {
			theDescription = theDescription  + "Interpreteer gebeurtenis als: " + this.wc_how.analyzedWord + ". ";
		} else
		{
			theDescription = theDescription  + "Meld wat is gebeurd. ";
		}
		
		if (this.wc_what != null) {
			theDescription = theDescription  + "Met betrekking tot concept " + this.wc_what.getAnalyzedWord() + ". ";
		}
		else
		{
			theDescription = theDescription  + "Op het concept dat is geraakt door de gebeurtenis.  ";
		}
		
		if (this.wc_who != null) {
			theDescription = theDescription +  "Betrek hierbij " + this.wc_who.getAnalyzedWord() + ". ";
		}
		else
		{
			theDescription = theDescription  + "Bij de eventuele betrokkenen. ";
		}	
		
		if (this.wc_when != null) {
			theDescription = theDescription +  "Op moment " + this.wc_when.getAnalyzedWord() + ". ";
		}
		else
		{
			theDescription = theDescription  + "Op het moment van de gebeurtenis. ";
		}	
		
		if (this.wc_where != null) {
			theDescription = theDescription +  "Het locatie van geinterpreteerde gebeurtenis is " + this.wc_where.getAnalyzedWord() + ". ";
		}
		else
		{
			theDescription = theDescription  + "Op de plaats van de gebeurtenis. ";
		}	
		
		if (this.wc_why != null) {
			theDescription = theDescription +  "Met reden " + this.wc_why + ".";
		}
		else
		{
			theDescription = theDescription  + "Vanwege de reden van de gebeurtenis. ";
		}	
		
	
		
		return theDescription;
				
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