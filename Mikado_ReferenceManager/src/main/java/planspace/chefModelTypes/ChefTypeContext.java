package planspace.chefModelTypes;

import java.util.ArrayList;
import java.util.List;

import application.frontend.WEB_Session;
import planspace.interfaceToDomainTypeRepository.InterfaceToDomainTypeRepository;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_chefType;

public class ChefTypeContext {
	private ChefType theChefType;

	private List<ChefType> childNormObjects;
	private List<ChefType> childDataObjects;
	private List<ChefType> childDataSourceObjects;
	private List<ChefType> childFactObjects;

	private List<ChefType> childOpportunityObjects;
	private List<ChefType> childRiskObjects;
	private List<ChefType> childSubGoalObjects;
	private List<ChefType> childMeasurementObjects;

	private List<ChefType> childUnknownObjects;
	private List<ChefType> childRulesObjects;
	private List<ChefType> childEventObjects;

	private List<ChefType> theDirectChildChefObjects;
	private List<ChefType> theDirectParentChefObjects;
	private List<t_chefType> currentChildTypes;

	private List<ChefTreeContext> theRelatedTreeContexts;

	public void update() {
		reset();
		if (this.theChefType != null) {
			this.getTreeContext();
			setChildPerType();
		}
	}

	public void reset() {
		childNormObjects.clear();
		childDataObjects.clear();
		childDataSourceObjects.clear();
		childFactObjects.clear();

		childOpportunityObjects.clear();
		childRiskObjects.clear();
		childSubGoalObjects.clear();
		childMeasurementObjects.clear();

		childUnknownObjects.clear();
		childRulesObjects.clear();
		childEventObjects.clear();

		theDirectChildChefObjects.clear();
		theDirectParentChefObjects.clear();

		theRelatedTreeContexts.clear();
		currentChildTypes.clear();
	}

	public List<ChefType> getChildsForType(t_chefType aType) {
		List<ChefType> objectsOfType;

		objectsOfType = null;
		if (aType.equals(t_chefType.NORM)) {
			objectsOfType = this.childNormObjects;
		} else if (aType.equals(t_chefType.GOAL)) {
			objectsOfType = this.childSubGoalObjects;
		} else if (aType.equals(t_chefType.EVENT)) {
			objectsOfType = this.childEventObjects;
		} else if (aType.equals(t_chefType.DATA)) {
			objectsOfType = this.childDataObjects;
		} else if (aType.equals(t_chefType.DATASOURCE)) {
			objectsOfType = this.childDataSourceObjects;
		} else if (aType.equals(t_chefType.RISK)) {
			objectsOfType = this.childRiskObjects;
		} else if (aType.equals(t_chefType.OPPORTUNITY)) {
			objectsOfType = this.childOpportunityObjects;
		} else if (aType.equals(t_chefType.MEASUREMENT)) {
			objectsOfType = this.childMeasurementObjects;
		} else if (aType.equals(t_chefType.FACT)) {
			objectsOfType = this.childFactObjects;
		} else if (aType.equals(t_chefType.RULE)) {
			objectsOfType = this.childRulesObjects;
		}
		return objectsOfType;
	}

	public ChefType getTheChefType() {
		return theChefType;
	}

	public void setTheChefType(ChefType theChefType) {
		this.theChefType = theChefType;
		update();
	}

	public List<ChefType> getChildNormObjects() {
		return childNormObjects;
	}

	public List<ChefType> getChildDataObjects() {
		return childDataObjects;
	}

	public List<ChefType> getChildDataSourceObjects() {
		return childDataSourceObjects;
	}

	public List<ChefType> getChildFactObjects() {
		return childFactObjects;
	}

	public List<ChefType> getChildOpportunityObjects() {
		return childOpportunityObjects;
	}

	public List<ChefType> getChildRiskObjects() {
		return childRiskObjects;
	}

	public List<ChefType> getChildSubGoalObjects() {
		return childSubGoalObjects;
	}

	public List<ChefType> getChildMeasurementObjects() {
		return childMeasurementObjects;
	}

	public List<ChefType> getChildUnknownObjects() {
		return childUnknownObjects;
	}

	public List<ChefType> getChildRulesObjects() {
		return childRulesObjects;
	}

	public List<ChefType> getChildEventObjects() {
		return childEventObjects;
	}

	public List<ChefType> getTheDirectChildChefObjects() {
		return theDirectChildChefObjects;
	}

	public List<ChefType> getTheDirectParentChefObjects() {
		return theDirectParentChefObjects;
	}

	public List<ChefTreeContext> getTheRelatedTreeContexts() {
		return theRelatedTreeContexts;
	}

	public ChefTypeContext() {
		theRelatedTreeContexts = new ArrayList<ChefTreeContext>();

		theDirectParentChefObjects = new ArrayList<ChefType>();
		theDirectChildChefObjects = new ArrayList<ChefType>();

		this.childNormObjects = new ArrayList<ChefType>();
		this.childDataObjects = new ArrayList<ChefType>();
		this.childDataSourceObjects = new ArrayList<ChefType>();
		this.childFactObjects = new ArrayList<ChefType>();

		this.childOpportunityObjects = new ArrayList<ChefType>();
		this.childRiskObjects = new ArrayList<ChefType>();
		this.childSubGoalObjects = new ArrayList<ChefType>();
		this.childMeasurementObjects = new ArrayList<ChefType>();

		this.childUnknownObjects = new ArrayList<ChefType>();
		this.childRulesObjects = new ArrayList<ChefType>();
		this.childEventObjects = new ArrayList<ChefType>();

		this.currentChildTypes = new ArrayList<t_chefType>();

	}

	public List<t_chefType> getCurrentChildTypes() {
		return currentChildTypes;
	}

	private void getTreeContext() {
		InterfaceToDomainTypeRepository theIFC;
		List<ChefTreeContext> relTaxItems;

		theIFC = InterfaceToDomainTypeRepository.getInstance();
		if (theIFC != null) {
			relTaxItems = theIFC.FindAllChefContextRelatedToChefType(
					WEB_Session.getInstance().getTheDomain().getDomainCode(), theChefType.get_id());
			this.theRelatedTreeContexts = relTaxItems;

		}

		this.loadTheDirectParentChefObjects();
		this.loadTheDirectChildChefObjects();
	}

	private void loadTheDirectParentChefObjects() {
		int i;
		ChefType aParent;

		if (theDirectParentChefObjects == null) {
			theDirectParentChefObjects = new ArrayList<ChefType>();
		}

		this.theDirectParentChefObjects.clear();
		if (this.theRelatedTreeContexts != null) {

			for (i = 0; i < this.theRelatedTreeContexts.size(); i++) {
				aParent = this.theRelatedTreeContexts.get(i).getChefObjectOfParent();
				if (aParent != null) {

					if (!this.theDirectParentChefObjects.contains(aParent)) {
						this.theDirectParentChefObjects.add(aParent);

					}
				}

			}
		}

	}

	private void setChildPerType() {
		int i;

		ChefType anItem;

		if (this.theDirectChildChefObjects != null) {

			for (i = 0; i < theDirectChildChefObjects.size(); i++) {
				anItem = theDirectChildChefObjects.get(i);
				if (anItem.getType().equals(t_chefType.NORM)) {
					childNormObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.DATA)) {
					childDataObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.FACT)) {
					childFactObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.DATASOURCE)) {
					childDataSourceObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.RISK)) {
					childRiskObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.OPPORTUNITY)) {
					childOpportunityObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.MEASUREMENT)) {
					childMeasurementObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.GOAL)) {
					childSubGoalObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.UNKNOWN)) {
					childUnknownObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.RULE)) {
					childRulesObjects.add(anItem);
				} else if (anItem.getType().equals(t_chefType.EVENT)) {
					childEventObjects.add(anItem);
				}
				registerChildType(anItem.getType());
			}
		}

	}

	private void registerChildType(t_chefType aTypeInUse) {
		if (!this.currentChildTypes.contains(aTypeInUse)) {
			currentChildTypes.add(aTypeInUse);
		}

	}

	private void loadTheDirectChildChefObjects() {
		int i, j;
		List<ChefType> theChildren;
		ChefType aChild;

		if (theDirectChildChefObjects == null) {
			theDirectChildChefObjects = new ArrayList<ChefType>();
		}
		this.theDirectChildChefObjects.clear();
		if (this.theRelatedTreeContexts != null) {
			for (i = 0; i < this.theRelatedTreeContexts.size(); i++) {
				theChildren = this.theRelatedTreeContexts.get(i).getChefObjectOfDirectChildren();
				if (theChildren != null) {

					for (j = 0; j < theChildren.size(); j++) {
						aChild = theChildren.get(j);

						if (!this.theDirectChildChefObjects.contains(aChild)) {
							this.theDirectChildChefObjects.add(aChild);

						}
					}
				}

			}
		}
	}

}
