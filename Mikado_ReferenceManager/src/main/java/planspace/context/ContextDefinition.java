package planspace.context;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_contextAspect;

public class ContextDefinition {

	public List<ContextAspect> contextAspects;
	
	public ContextDefinition() {
		contextAspects = new ArrayList<ContextAspect>();
	}

	public List<ContextAspect> getContextAspects() {
		return contextAspects;
	}

	public void setContextAspects(List<ContextAspect> contextAspects) {
		this.contextAspects = contextAspects;
	}
	
	public ContextAspect addContextAspect( t_contextAspect aspectType, String conceptTypeCode) {
		ContextAspect newAspect;
		
		newAspect = new ContextAspect();
		newAspect.setContextAspectType(aspectType);
		newAspect.setConceptTypeCode(conceptTypeCode);
		this.contextAspects.add(newAspect);
		return newAspect;
	}
	
	
	public List<ContextAspect> getContextAspectsOfAspectType( t_contextAspect aspectType) {
		List<ContextAspect>  theAspects;
		
		theAspects =  new ArrayList<ContextAspect>();
		this.contextAspects.forEach(aspect -> {
			if (aspect.getContextAspectType().equals(aspectType)) {
				theAspects.add(aspect);
			}
			
		});
		return theAspects;
	}
	
	public ContextAspect getContextAspectOfObjectCode( t_contextAspect aspectType, String conceptTypeCode) {
		ContextAspect theAspect, aspect;
		int i;
		
		theAspect = null;
		
		for (i = 0 ; i < this.contextAspects.size(); i++ ) {
			aspect = this.contextAspects.get(i);
			if (aspect.getContextAspectType().equals(aspectType)) {
				if (aspect.getConceptTypeCode().equals(conceptTypeCode)) {
					theAspect = aspect;
					break;
				}
			}
		}
		return theAspect;
	}
	
	@JsonIgnore
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
