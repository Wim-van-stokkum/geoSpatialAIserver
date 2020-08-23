package nl.geospatialAI.beans;

public class AssessRequestContext {

	public enum tScenarioType {
		scenarioExploration, formalRequest
	}

	public enum tUsertype {
		AANVRAGER, BEOORDELAAR
	}

	public tScenarioType scenario;
	public String referenceID;
	public tUsertype usertype;

	public tUsertype getUserType() {
		return usertype;
	}

	public tScenarioType getScenario() {
		return scenario;
	}

	public void setScenario(tScenarioType scenario) {
		this.scenario = scenario;
	}

	public String getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}

}
