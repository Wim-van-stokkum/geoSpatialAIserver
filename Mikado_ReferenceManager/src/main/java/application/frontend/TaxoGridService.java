package application.frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import planspace.domainTypes.TaxonomyNode;
import planspace.utils.PlanSpaceLogger;

public class TaxoGridService {


	public Collection<TaxonomyNode> getRootNodes() {
		List <TaxonomyNode> theRoots;
		TaxonomyNode theRoot;
		
		theRoot = WEB_Session.getInstance().getDomainTaxonomy();
		theRoots = new ArrayList<TaxonomyNode>();
		theRoots.add(theRoot);

		return theRoots;
	}

	public Collection<TaxonomyNode> getChildNodes(TaxonomyNode aParent) {

	   return aParent.getMyChildren();
	
	}
	
	
}