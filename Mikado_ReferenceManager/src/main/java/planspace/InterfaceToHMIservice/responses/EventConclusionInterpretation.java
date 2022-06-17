package planspace.InterfaceToHMIservice.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_aspectInSentence;
import speechInterface.WordClassification;

public class EventConclusionInterpretation {

	WordClassification wc_how;
	WordClassification wc_what;
	WordClassification wc_who;
	WordClassification wc_when;
	WordClassification wc_where;
	WordClassification wc_why;

	List<String> potentialNewWords;
	private boolean addHowToDomain;
	private boolean addWhatToDomain;
	private boolean addWhoToDomain;
	private boolean addWhereToDomain;
	private boolean addWhyToDomain;
	private boolean addWhenToDomain;
	
	public EventConclusionInterpretation() {
		addHowToDomain = false;
		addWhatToDomain = false;
		addWhoToDomain= false;
		addWhereToDomain= false;
		addWhyToDomain= false;
		addWhenToDomain= false;
	}

	
	
	public boolean isAddHowToDomain() {
		return addHowToDomain;
	}



	public void setAddHowToDomain(boolean addHowToDomain) {
		this.addHowToDomain = addHowToDomain;
	}



	public boolean isAddWhatToDomain() {
		return addWhatToDomain;
	}



	public void setAddWhatToDomain(boolean addWhatToDomain) {
		this.addWhatToDomain = addWhatToDomain;
	}



	public boolean isAddWhoToDomain() {
		return addWhoToDomain;
	}



	public void setAddWhoToDomain(boolean addWhoToDomain) {
		this.addWhoToDomain = addWhoToDomain;
	}



	public boolean isAddWhereToDomain() {
		return addWhereToDomain;
	}



	public void setAddWhereToDomain(boolean addWhereToDomain) {
		this.addWhereToDomain = addWhereToDomain;
	}



	public boolean isAddWhyToDomain() {
		return addWhyToDomain;
	}



	public void setAddWhyToDomain(boolean addWhyToDomain) {
		this.addWhyToDomain = addWhyToDomain;
	}



	public boolean isAddWhenToDomain() {
		return addWhenToDomain;
	}



	public void setAddWhenToDomain(boolean addWhenToDomain) {
		this.addWhenToDomain = addWhenToDomain;
	}



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

	public void needToAddHowToDomain(boolean addNeeded) {
		this.addHowToDomain = addNeeded;
		
	}
	public void  needToAddWhatToDomain(boolean addNeeded) {
		this.addWhatToDomain = addNeeded;
		
	}
	public void  needToAddWhoToDomain(boolean addNeeded) {
		this.addWhoToDomain = addNeeded;
		
	}
	
	public void  needToAddWhereToDomain(boolean addNeeded) {
		this.addWhereToDomain = addNeeded;
		
	}
	public void  needToAddWhenToDomain(boolean addNeeded) {
		this.addWhenToDomain = addNeeded;
		
	}

	public void  needToAddWhyToDomain(boolean addNeeded) {
		this.addWhyToDomain = addNeeded;
		
	}


}