package planspace.agentinteraction.externalEventTypes;

import org.bson.Document;

import com.google.gson.Gson;

import planspace.agentinteraction.ExternalEvent;
import planspace.instanceModel.DomainInstance;

public class DomainSpecificExternalEvent extends ExternalEvent {

	/*
	 * =========================================================================
	 * Deze class beschrijft een voorkomen (sybtype) van een ExternEvent. Omdat er
	 * meerdere typen worden verwacht vooralsnog besloten een subtype aan te maken
	 * ============================================================================
	 */

	public DomainSpecificExternalEvent() {

		// constructor 
		// Enige specifieke aan deze subclass tot nu toe is het zetten van Type en Categorie
		super();
		this.setEventType("DomainSpecificExternalEvent");
		this.setEventTypeCategory("Environmental external events");

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
	
			// Gson is de externe utility class (zie POM.XML) die Java Classes kan omzetten
			// naar Json.
			gsonParser = new Gson();

			// Ik zet mijzelf (instantie van deze class) om naar Json, en deze wordt binair
			// gemaakt om
			// in MongoDB opgeslagen te kunnen worden.

			meAsJson = gsonParser.toJson(this);
		

			return meAsJson;
		}



}
