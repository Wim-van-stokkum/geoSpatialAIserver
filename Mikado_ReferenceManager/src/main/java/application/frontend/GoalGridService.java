package application.frontend;

import java.util.Collection;
import java.util.List;

import planspace.chefModelTypes.ChefTaxonomyItem;

public class GoalGridService {
	List <ChefTaxonomyItem> theRoots;

	public GoalGridService(List <ChefTaxonomyItem> theRootItems) {
		theRoots = theRootItems;
	}

	public Collection<ChefTaxonomyItem> getRootNodes() {

		return theRoots;
	}

	public Collection<ChefTaxonomyItem> getChildNodes(ChefTaxonomyItem aParent) {

	   return aParent.getMyChildren();
	
	}
	
	
}