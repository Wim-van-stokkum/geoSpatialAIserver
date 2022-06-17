package planspace.jsonExport;

import java.util.ArrayList;
import java.util.List;

import planspace.chefModelTypes.ChefTaxonomyItem;
import planspace.chefModelTypes.ChefTreeContext;

public class policyTreeNode {

	private String policyTreeNodeId;
	private List <String> subPolicyTreeNodeIds;
	
	public policyTreeNode() {
		this.subPolicyTreeNodeIds = new ArrayList<String>();
	}
	
	public void initByChefTaxonomyItem(ChefTaxonomyItem aChefTaxItem) {
		this.policyTreeNodeId = aChefTaxItem.getTheChefType().get_id();
		 aChefTaxItem.getMyChildren().forEach(achild-> {
			 subPolicyTreeNodeIds.add(achild.getTheChefType().get_id());
		 });
	}

	public void initByChefTreeContext(ChefTreeContext aChefTypeContext) {
		this.policyTreeNodeId = aChefTypeContext.getRelatedChefObjectID();
		aChefTypeContext.getChefObjectOfDirectChildren().forEach(achild-> {
			 subPolicyTreeNodeIds.add(achild.get_id());
		 });
	}
	
	
	
}
