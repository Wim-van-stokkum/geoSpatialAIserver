package application.frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;

public class PerspectiveGoalGridService {
	private List<ChefTaxonomyItem> theRoots;
	private List<t_chefType> currentPerspectives;
	private List<ChefTaxonomyItem> relevantRoots;

	public PerspectiveGoalGridService(List<ChefTaxonomyItem> theRootItems, List<t_chefType> perspectives) {
		theRoots = theRootItems;
		currentPerspectives = perspectives;
	}

	public Collection<ChefTaxonomyItem> getRootNodes() {
		relevantRoots = new ArrayList<ChefTaxonomyItem>();
		this.filterRootsOnPerspective();
		return relevantRoots;
	}

	public Collection<ChefTaxonomyItem> getChildNodes(ChefTaxonomyItem aParent) {

		return aParent.findAllDirectChildItemOfTypes(this.currentPerspectives);

	}

	private void filterRootsOnPerspective() {
	//	System.out.println("Hier perspectives " + currentPerspectives.toString());
		//System.out.println("Hier roots " + theRoots.size() );
		this.theRoots.forEach(aRoot -> {
		//	System.out.println("Hier ROOT " + ChefType.convertFormalTypeToString(t_language.NL, aRoot.getTheChefType().getType()) + 
			//		"  " + aRoot.getInformalName());
			if (currentPerspectives.contains(aRoot.getTheChefType().getType())) {
				relevantRoots.add(aRoot);
		//		System.out.println("Hier 1");
			} else {
			//	System.out.println("Hier 2");
				// firstRelevantTaxItemInRoot = aRoot.findFirstItemOfTypes(currentPerspectives);
				List<ChefTaxonomyItem> relevantRootsOfRoot;
				relevantRootsOfRoot = aRoot.findAllDirectChildItemOfTypes(this.currentPerspectives);
				if (relevantRootsOfRoot != null) {
					relevantRootsOfRoot.forEach(aRelRoot -> {
						relevantRoots.add(aRelRoot);
					});

				}
			}
		});

	}

}