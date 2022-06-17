package planspace.chefModelTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ChefTaxoGridService {



	public Collection<ChefTaxonomyItem> getRootNodes() {
		List <ChefTaxonomyItem> theRoots;
		ChefTaxonomyItem theRoot;
		
	   theRoots = new ArrayList<ChefTaxonomyItem>();
	//	theRoots.add(theRoot);

		return theRoots;
	}

	public Collection<ChefTaxonomyItem> getChildNodes(ChefTaxonomyItem aParent) {

	   return aParent.getMyChildren();
	
	}
	
	
}