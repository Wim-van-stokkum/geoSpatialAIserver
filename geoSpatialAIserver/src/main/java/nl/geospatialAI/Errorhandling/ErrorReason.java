package nl.geospatialAI.Errorhandling;

import nl.geospatialAI.Case.CasesDAO;
import nl.geospatialAI.serverGlobals.ServerGlobals;

public class ErrorReason {
	public enum t_ErrorReasonSeverity {
		INFO, WARNING, ERROR
	}

	public enum t_ErrorReasonType {
		NO_CASE_REGISTERED,
		NO_DATAPOINT_EXISTS,
		NO_RISK_FOUND
	}

    public t_ErrorReasonSeverity severity;
    public t_ErrorReasonType reasonCode;
    public String info;

	public t_ErrorReasonSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(t_ErrorReasonSeverity severity) {
		this.severity = severity;
	}

	public t_ErrorReasonType getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(t_ErrorReasonType reasonCode) {
		this.reasonCode = reasonCode;
	}

	public static ErrorReason createErrorReason(t_ErrorReasonType  aReasonType) {
		ErrorReason newError;

		newError = new ErrorReason();
		newError.setReasonCode(aReasonType);
		
		// Severe by default
		
		newError.setSeverity(ErrorReason.t_ErrorReasonSeverity.ERROR);
		
		// Overrule default severity
	
		return newError;

	}
	
	
	public static ErrorReason createErrorReason_info(t_ErrorReasonType  aReasonType, String info ) {
		ErrorReason newError;

		newError = ErrorReason.createErrorReason(aReasonType);
		newError.setInfo(info); 


		return newError;

	}

	private void setInfo(String theInfo) {
		this.info = theInfo;
		
		
	}

}
