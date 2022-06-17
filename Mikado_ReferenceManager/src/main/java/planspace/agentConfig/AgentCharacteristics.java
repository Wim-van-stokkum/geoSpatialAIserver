package planspace.agentConfig;

import java.util.ArrayList;
import java.util.List;

public class AgentCharacteristics {


	List<AgentObjective> myObjectives;
	String agentTypeCode;
	String agentName;
	String description;

	public AgentCharacteristics() {
		/* Contructor */
		myObjectives = new ArrayList<AgentObjective>();

	}

	//getters and Setters
	public List<AgentObjective> getMyObjectives() {
		return myObjectives;
	}

	public void setMyObjectives(List<AgentObjective> myObjectives) {
		this.myObjectives = myObjectives;
	}

	public void addObjective(AgentObjective anObjective) {
		this.myObjectives.add(anObjective);

	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentTypeCode() {
		return agentTypeCode;
	}

	public void setAgentTypeCode(String agentTypeCode) {
		this.agentTypeCode = agentTypeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
