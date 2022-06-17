package planspace.domainRules.RuleTemplates;

/* Een  AspectPropertyCondition valideert de waarde van een eigenschap (DataPointValue) binnen
 * de DomainInstance die is aangeleverd voor een aspect in de EventContextDefinition.
 * 
 * Niet alleen het ObjectType van de EventContextDefinition moet dan overeenkomen, maar ook de 
 * waarde van deze eiegnschap moet voldoen aan de voorwaarde gesteld in de AspectPropertyConditions
 * 
 * Deze AspectPropertyCondition zijn onderdeel van EventInterpretationRules en woden geregistreerd bij de
 * het aspect WHAT, HOW, WHO, WHERE, WHEN, WHY. 
 */


public class AspectPropertyCondition extends ObjectDataPointValueCondition{
    AspectTemplate.t_baseOnAspect  aspectToCompareWith = null;
    
    
    public AspectPropertyCondition() {
      super();
    
    
    }
	
	
}
