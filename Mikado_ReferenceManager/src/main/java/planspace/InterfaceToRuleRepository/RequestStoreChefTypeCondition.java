package planspace.InterfaceToRuleRepository;

import com.google.gson.Gson;

import planspace.domainRules.RuleTemplates.ChefTypeCondition;

public class RequestStoreChefTypeCondition {
	
	ChefTypeCondition theChefTypeCondition;

	public ChefTypeCondition getTheChefTypeCondition() {
		return theChefTypeCondition;
	}

	public void setTheChefTypeCondition(ChefTypeCondition theChefTypeCondition) {
		this.theChefTypeCondition = theChefTypeCondition;
	}

	

	public String toJson() {
		Gson gsonParser;
		String meAsJson;

		gsonParser = new Gson();

		meAsJson = gsonParser.toJson(this);

		return meAsJson;
	}

}
