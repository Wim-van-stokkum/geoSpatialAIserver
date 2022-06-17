package planspace.InterfaceToRuleRepository;

import java.util.ArrayList;
import java.util.List;

import planspace.domainRules.RuleTemplates.ChefTypeCondition;

public class ResponseChefTypeConditionsFound {
	List< ChefTypeCondition> foundChefTypeConditions;
	private String status;
	public ResponseChefTypeConditionsFound() {
		foundChefTypeConditions  = new ArrayList<ChefTypeCondition>();
	}
	
	public void  addChefTypeCondition(ChefTypeCondition aCondition) {
		if (! this.foundChefTypeConditions.contains(aCondition)) {
			this.foundChefTypeConditions.add(aCondition);
		}
	}

	public List<ChefTypeCondition> getFoundChefTypeConditions() {
		return foundChefTypeConditions;
	}

	public void setFoundChefTypeConditions(List<ChefTypeCondition> foundChefTypeConditions) {
		this.foundChefTypeConditions = foundChefTypeConditions;
	}

	public void setStatus(String newStatus) {
		this.status = newStatus;
		
	}

	
	
	
	

}
