package planspace.InterfaceToRuleRepository;

import java.util.ArrayList;
import java.util.List;

import planspace.domainRules.RuleTemplates.ChefTypeCondition;

public class ResponseChefTypeConditionFound {
	ChefTypeCondition foundChefTypeCondition;
	private String status;
	public ChefTypeCondition getFoundChefTypeCondition() {
		return foundChefTypeCondition;
	}
	public void setFoundChefTypeCondition(ChefTypeCondition foundChefTypeCondition) {
		this.foundChefTypeCondition = foundChefTypeCondition;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
