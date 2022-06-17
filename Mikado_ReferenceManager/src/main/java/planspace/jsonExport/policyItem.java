package planspace.jsonExport;

import java.util.ArrayList;
import java.util.List;

import planspace.chefModelTypes.ChefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_goalArcheType;

public class policyItem {
	private String _id;
	private t_chefType type;
	private String name;
	private String description;
	private String domainCode;

	private String theme;
	private t_goalArcheType goalArcheType;
	private List<policySourceReference> sourceReferences;

	public policyItem() {

	}

	public void initByChefType(ChefType aChefType) {
	
		
		this._id = aChefType.get_id();
		this.type = aChefType.getType();
		this.name = aChefType.getName();
		this.description = aChefType.getDescription();
		this.domainCode = aChefType.getDomainCode();
		this.theme = aChefType.getThemeAsString();

		sourceReferences = new ArrayList<policySourceReference>();
		if (aChefType.getSourceReferences() != null) {
			aChefType.getSourceReferences().forEach(aRef -> {
				policySourceReference aPolRef;
				
				aPolRef = new policySourceReference();
				aPolRef.initBySourceReferenceData(aRef);
				sourceReferences.add(aPolRef);
			});
		}

		if (this.type.equals(t_chefType.GOAL)) {
			this.goalArcheType = aChefType.getGoalArcheType();
		}
	}

}
