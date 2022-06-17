package application.frontend;

import java.util.Collection;
import java.util.List;

import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.domainRules.RuleTemplates.ChefTypeCondition;

public class ConditionGridService {
	List <ChefTypeCondition> theRoots;

	public ConditionGridService(List <ChefTypeCondition> theRootItems) {
		theRoots = theRootItems;
	}

	public Collection<ChefTypeCondition> getRootNodes() {

		return theRoots;
	}

	public Collection<ChefTypeCondition> getChildNodes(ChefTypeCondition aParent) {

	   return aParent.getMySubconditions();
	
	}
	
	
}