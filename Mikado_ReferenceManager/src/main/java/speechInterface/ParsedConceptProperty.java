package speechInterface;

import java.util.List;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_propertyDynamic;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_propertyIdentificationType;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_propertyType;


public class ParsedConceptProperty {
	WordClassification theConcept;
	WordClassification theProperty;

	t_propertyType thePropertyType;
	t_propertyDynamic thePropertyDynamics;
	t_propertyIdentificationType thePropertyIdentificationType;

	List<WordClassification> sharedIdentityWith;

	public WordClassification getTheConcept() {
		return theConcept;
	}

	public void setTheConcept(WordClassification theConcept) {
		this.theConcept = theConcept;
	}

	public WordClassification getTheProperty() {
		return theProperty;
	}

	public void setTheProperty(WordClassification theProperty) {
		this.theProperty = theProperty;
	}

	public t_propertyType getThePropertyType() {
		return thePropertyType;
	}

	public void setThePropertyType(t_propertyType thePropertyType) {
		this.thePropertyType = thePropertyType;
	}

	public t_propertyDynamic getThePropertyDynamics() {
		return thePropertyDynamics;
	}

	public void setThePropertyDynamics(t_propertyDynamic thePropertyDynamics) {
		this.thePropertyDynamics = thePropertyDynamics;
	}

	public t_propertyIdentificationType getThePropertyIdentificationType() {
		return thePropertyIdentificationType;
	}

	public void setThePropertyIdentificationType(t_propertyIdentificationType thePropertyIdentificationType) {
		this.thePropertyIdentificationType = thePropertyIdentificationType;
	}

	public List<WordClassification> getSharedIdentityWith() {
		return sharedIdentityWith;
	}

	public void setSharedIdentityWith(List<WordClassification> sharedIdentityWith) {
		this.sharedIdentityWith = sharedIdentityWith;
	}

	public String getSharedWithAsString() {
		String SharedIDasString;
		int i;
		
		SharedIDasString = "";
		if (this.sharedIdentityWith != null) {
			for (i = 0 ; i <this.sharedIdentityWith.size(); i++ ) {
				if (i == 0 ) {
				SharedIDasString = this.sharedIdentityWith.get(i).getAnalyzedWord();
				}
				else
				{
					if (i <this.sharedIdentityWith.size() - 1) {
						SharedIDasString = SharedIDasString + " ," +  this.sharedIdentityWith.get(i).getAnalyzedWord();
					}
					else
					{
						SharedIDasString = SharedIDasString + " en " +  this.sharedIdentityWith.get(i).getAnalyzedWord();
					}
				}
			}
		}
		
		return SharedIDasString;
	}

	/**********
	 * METHODS
	 */



	


}
