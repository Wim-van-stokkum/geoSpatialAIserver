package planspace.interfaceToDomainTypeRepository.requests;

import planspace.chefModelTypes.ChefType;

public class RequestCreateChefType {

	ChefType newChefType;

	public ChefType getNewChefType() {
		return newChefType;
	}

	public void setNewChefType(ChefType newChefType) {
		this.newChefType = newChefType;
	}

	
}
