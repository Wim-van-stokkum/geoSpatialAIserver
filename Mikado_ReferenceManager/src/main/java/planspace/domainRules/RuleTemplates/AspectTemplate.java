package planspace.domainRules.RuleTemplates;
//shared

import planspace.instanceModel.DomainInstance;
import planspace.utils.PlanSpaceLogger;

/* Deze methode maakt een indivudeel aspect aan tbv een EventContextDefinition op basis van een 
 * aangeleverde event context

   Een aspect kan worden gemaakt:
   - Door instantie uit aangeleverde event te gebruiken die aangeleverd
   - Een nieuwe DomainInstance te maken en vullen op basis van in template
   	 gespecificeerde (voorgedefinieerd) ObjectType
   	 
   	 Voor elk van methode kunnen aanvullende eigenschappen van waarden worden voorzien. 
   	 Er worden dan DomainValues toegevoegd.
   
 */

public class AspectTemplate {
	
	/*    Een aspect kan worden gemaakt:
   - Door instantie uit aangeleverde event te gebruiken die aangeleverd
   - Een nieuwe DomainInstance te maken en vullen op basis van in template
   	 gespecificeerde (voorgedefinieerd) ObjectType
   	 
	 */
	
	public enum t_contructionType {
		USE_DEFINED_OBJECT, USE_EVENTCONTEXT_ASPECT
	}
	t_contructionType contructionType;
	
	
	/* Als aspect wordt ingevuld door waarde van (ander) aspect in aangeleverd bericht, dan
	 * moet template weten welke aspect type dan te pakken
	 */
	public enum t_baseOnAspect{
		HOW, WHAT, WHERE, WHO,  WHEN, WHY
	}
	

    t_baseOnAspect mybaseAspect;
    
	/* Als aspect wordt ingevuld door voorgedefinieerd instantie van ObjectType (niet aanwezig in aangeleverd bericht,
	 * dan moet template weten om hoe de instantie is voorgedefinieerd.
	 */
    DomainInstance myDefinedObject;
    
    //* getters en setters
	public t_baseOnAspect getMybaseAspect() {
		return mybaseAspect;
	}

	public void setMybaseAspect(t_baseOnAspect mybaseAspect) {
		this.mybaseAspect = mybaseAspect;
	}

	public DomainInstance getMyDefinedObject() {
		return myDefinedObject;
	}



	EventContextDefinition.t_EventContext_AspectType useAspect;

	
	public t_contructionType getContructionType() {
		return contructionType;
	}

	public void setContructionType(t_contructionType contructionType) {
		this.contructionType = contructionType;
	}


	public EventContextDefinition.t_EventContext_AspectType getUseAspect() {
		return useAspect;
	}

	public void setUseAspect(EventContextDefinition.t_EventContext_AspectType useAspect) {
		this.useAspect = useAspect;
	}
	

	public void setTheDefinedObject(DomainInstance anAspectInstance) {
		this.myDefinedObject = anAspectInstance;
		
		
	}

	
	// METHODS
	
	
	

	public DomainInstance createDomainInstance(EventContextDefinition theConditionEventContext) {
		
		/* op basis van definitie en een aangeleverde EventContext wordt het aspect geproduceerd */
		
        DomainInstance aDomainInstance;
        aDomainInstance = null;
        
        if (this.contructionType.equals( AspectTemplate.t_contructionType.USE_DEFINED_OBJECT)) {
        	aDomainInstance = new DomainInstance();
        	
        	// De instantie in de definitie wordt gebruikt om het apect in te vullen
        	if (this.getMyDefinedObject() != null) {
        		aDomainInstance = getMyDefinedObject();
        		
        	}
        	
        	
        }
        
        if (this.contructionType.equals( AspectTemplate.t_contructionType.USE_EVENTCONTEXT_ASPECT)) {
         	// De instantie van de aangeleverde EventContext wordt gebruikt om het aspect in te vullen
        	// het baseAspect geft aan welk aspect uit de aangeleverde EventContext moet worden gepakt
        	
        	if (this.mybaseAspect.equals(AspectTemplate.t_baseOnAspect.HOW)) {
        		aDomainInstance = theConditionEventContext.getTheActivityCausingEvent();
        	}
        	if (this.mybaseAspect.equals(AspectTemplate.t_baseOnAspect.WHAT)) {
        		aDomainInstance = theConditionEventContext.getTheSubjectInvolved();
        		
        	}
         	if (this.mybaseAspect.equals(AspectTemplate.t_baseOnAspect.WHEN)) {
        		aDomainInstance = theConditionEventContext.getTheMoment();
        		
        	}
         	if (this.mybaseAspect.equals(AspectTemplate.t_baseOnAspect.WHERE)) {
        		aDomainInstance = theConditionEventContext.getTheLocation();
        		
        	}
        	if (this.mybaseAspect.equals(AspectTemplate.t_baseOnAspect.WHY)) {
        		aDomainInstance = theConditionEventContext.getTheGoal();
        		
        	}
        	if (this.mybaseAspect.equals(AspectTemplate.t_baseOnAspect.WHO)) {
        		aDomainInstance = theConditionEventContext.getTheActor();
        		
        	}
        }
        
        
        
        return aDomainInstance;
	}

	
}
