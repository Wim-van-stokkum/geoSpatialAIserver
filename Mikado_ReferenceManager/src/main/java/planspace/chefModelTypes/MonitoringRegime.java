package planspace.chefModelTypes;

import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_language;

public class MonitoringRegime {
	public enum t_monitoringFrequency {
		ON_REQUEST, CONTINOUSLY, PLANNED
	}

	private t_monitoringFrequency monitoringFrequency;

	public MonitoringRegime() {
		this.monitoringFrequency = t_monitoringFrequency.ON_REQUEST;
	}

	public t_monitoringFrequency getMonitoringFrequency() {
		return monitoringFrequency;
	}

	public void setMonitoringFrequency(t_monitoringFrequency monitoringFrequency) {
		this.monitoringFrequency = monitoringFrequency;
	}

	public static t_monitoringFrequency getFormalFrequencyForInformal(t_language lang, String informal) {
		t_monitoringFrequency formal;

		formal = t_monitoringFrequency.ON_REQUEST;
		if (lang.equals(t_language.NL)) {
			if (informal.equals("Op verzoek")) {
				formal = t_monitoringFrequency.ON_REQUEST;
			} else if (informal.equals("Continue")) {
				formal = t_monitoringFrequency.CONTINOUSLY;
			} else if (informal.equals("Geplanned")) {
				formal = t_monitoringFrequency.PLANNED;
			}

		}
		return formal;
	}

	public static String getInformalFrequencyForFormal(t_language lang, t_monitoringFrequency formal) {
		String informal;

		informal = "Op verzoek";

		if (lang.equals(t_language.NL)) {
			if (formal.equals(t_monitoringFrequency.ON_REQUEST)) {

				informal = "Op verzoek";
			} else if (formal.equals(t_monitoringFrequency.CONTINOUSLY)) {

				informal = "Continue";
			} else if (formal.equals(t_monitoringFrequency.PLANNED)) {

				informal = "Geplanned";
			}

		}
		return informal;
	}

}
