package planspace.context;

import java.util.ArrayList;
import java.util.List;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_contextAspect;

public class ContextAspect {
	private t_contextAspect contextAspectType;
	private String conceptTypeCode;
	private List<ContextAspectProperty> conceptProperties;
	
	public ContextAspect() {
		conceptProperties = new  ArrayList<ContextAspectProperty>();
	}

	public t_contextAspect getContextAspectType() {
		return contextAspectType;
	}

	public void setContextAspectType(t_contextAspect contextAspectType) {
		this.contextAspectType = contextAspectType;
	}

	public String getConceptTypeCode() {
		return conceptTypeCode;
	}

	public void setConceptTypeCode(String conceptTypeCode) {
		this.conceptTypeCode = conceptTypeCode;
	}

	public List<ContextAspectProperty> getConceptProperties() {
		return conceptProperties;
	}

	public void setConceptProperties(List<ContextAspectProperty> conceptProperties) {
		this.conceptProperties = conceptProperties;
	}
	
	

}
