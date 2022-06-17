package planspace.agentConfig;

import planspace.agentConfig.AgentCharacteristics;

public class AgentConfiguration {



	/* The AgentCharateristics describe the objectives of the agent */
	 AgentCharacteristics myCharacteristics;
	 String agentConfigurationTypeCode = null;
	
	 
	 public AgentConfiguration() {
		 /* constructor */
		 myCharacteristics = null;
		 
	 }
	 
		/* Managing characteristics */

		public AgentCharacteristics getMyCharacteristics() {
			return myCharacteristics;
		}

		public void setMyCharacteristics(AgentCharacteristics myCharacteristics) {
			this.myCharacteristics = myCharacteristics;
		}

		public String getAgentConfigurationTypeCode() {
			return agentConfigurationTypeCode;
		}

		public void setAgentConfigurationTypeCode(String agentConfigurationTypeCode) {
			this.agentConfigurationTypeCode = agentConfigurationTypeCode;
		}
		
		
}
