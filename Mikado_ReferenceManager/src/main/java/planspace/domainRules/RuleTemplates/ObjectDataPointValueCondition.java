package planspace.domainRules.RuleTemplates;

import planspace.domainTypes.ElemValue;
import planspace.instanceModel.DataPointValue;
import planspace.instanceModel.DomainInstance;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_elemExpressionOperator;
import planspace.utils.PlanSpaceLogger;

/* To be defined
 * WORK in progress
 * 
 * De AbstractEventInterpretationRule evalueert of een event context van een event voldoet aan
 * voorwaarde van een regel, nu wordt er op Object niveau gevalideerd.
 * 
 * Deze class is bedoeld om nadere voorwaarde te stellen aan de eigenschappen van de Objecten in de gebeurtenis.
 * 
 * Bijvoorbeeld Alleen gebeurtenissen in een POSTCODEGEBIED met eigenschap POSTCODE = "2634 TM"
 * 
 * 
 */
public class ObjectDataPointValueCondition {


	boolean isNot = false;
	t_elemExpressionOperator myOperator = t_elemExpressionOperator.IS_EQUAL;
	ElemValue elemValueToCompareWith;
	String dataPointTypeName_to_compare = "null";
	String dataPointTypeName_to_compareWith = "null";

	public boolean isNot() {
		return isNot;
	}

	public void setNot(boolean isNot) {
		this.isNot = isNot;
	}

	public t_elemExpressionOperator getMyOperator() {
		return myOperator;
	}

	public void setMyOperator(t_elemExpressionOperator myOperator) {
		this.myOperator = myOperator;
	}

	public ElemValue getElemValueToCompareWith() {
		return elemValueToCompareWith;
	}

	public void setElemValueToCompareWith(ElemValue elemValueToCompareWith) {
		this.elemValueToCompareWith = elemValueToCompareWith;
	}

	public String getDataPointTypeName_to_compareWith() {
		return dataPointTypeName_to_compareWith;
	}

	public void setDataPointTypeName_to_compareWith(String dataPointTypeName_to_compareWith) {
		this.dataPointTypeName_to_compareWith = dataPointTypeName_to_compareWith;
	}

	
	public String getDataPointTypeName_to_compare() {
		return dataPointTypeName_to_compare;
	}

	public void setDataPointTypeName_to_compare(String dataPointTypeName_to_compare) {
		this.dataPointTypeName_to_compare = dataPointTypeName_to_compare;
	}
	
	/* Methods */
	
	public boolean evaluateForDomainInstance(DomainInstance theDomainInstance) {
		/*
		 * this method with compare the configured datapointValue  with
		 * either: - Another Datapoint value of it's instance of type specified in
		 * condition - A contant value specified in the condition
		 */
		boolean conditionResult;
	
		DataPointValue theDPValueToCompare;
		
		
		PlanSpaceLogger.getInstance().log_RuleDebug("=====> Start Evaluation OBV value Condition: " + this.getDataPointTypeName_to_compare());
		
		conditionResult = false;
		if (theDomainInstance != null) {
			// first get the datapountvalue of configured type withing DomainInstance specified
			theDPValueToCompare = theDomainInstance.getDataPointValueByTypeName(this.dataPointTypeName_to_compare);
			if (theDPValueToCompare != null) {
				PlanSpaceLogger.getInstance().log_RuleDebug("Found datapoint to compare " +
			               theDPValueToCompare.getDataPointTypeCode());
				conditionResult = this.evaluateConditionForDataPointValue(
						theDomainInstance, theDPValueToCompare);
			} else
			{
				PlanSpaceLogger.getInstance().log_RuleDebug(
						"ERROR: property " +  this.dataPointTypeName_to_compare + " not found in ObjectDPValue condition ");
			}
		}
		else {
			PlanSpaceLogger.getInstance().log_RuleDebug(
					"ERROR: no instance received in ObjectDPValue condition");
		}
	

		
		PlanSpaceLogger.getInstance().log_RuleDebug("=====> Result Evaluation OBV value Condition: " + 
		          this.getDataPointTypeName_to_compare()  + " = "  + conditionResult);
		
		return conditionResult;
	}



	public boolean evaluateConditionForDataPointValue(DomainInstance theDomainInstance , 
			DataPointValue DP_value_toEvaluate) {

		/*
		 * this method with compare the specified datapointValue in the arguments with
		 * either: - Another Datapoint value of it's instance of type specified in
		 * condition - A contant value specified in the condition
		 */

		boolean conditionResult;
		conditionResult = false;
		/*
		if (this.dataPointTypeName_to_compareWith != "null") {
			conditionResult = compareToOtherDataPointValueInDomainInstance(theDomainInstance, DP_value_toEvaluate);

		} else 
		*/
		
		if (this.elemValueToCompareWith != null) {
			conditionResult = this.compareToDefinedValue(DP_value_toEvaluate);
		} else {
			PlanSpaceLogger.getInstance().log_Error(
					"Condition undefined evaluation strategy : " + DP_value_toEvaluate.getDataPointTypeCode());
		}
		PlanSpaceLogger.getInstance().log_RuleDebug("Result evaluation " + conditionResult );
		return conditionResult;

	}

	protected boolean compareToDefinedValue(DataPointValue DP_value_toEvaluate) {
		/*
		 * this method with compare the specified dapapointValue in the arguments with
		 * the contant value specified in the condition
		 */
		boolean conditionResult;

		ElemValue valueOfOther, valueToEvaluate;

		// By default result is false
		conditionResult = false;

		PlanSpaceLogger.getInstance().log_RuleDebug("Compare to defined value: " + this.elemValueToCompareWith.getValueAsString() ); 

		// get value to Evaluate from the DataPointValue received in arguments
		valueToEvaluate = DP_value_toEvaluate.getElemValueAsValue();

		if (valueToEvaluate != null) {
			// use constant value in this condition
			PlanSpaceLogger.getInstance().log_RuleDebug("COND : value to compare " + valueToEvaluate.getValueAsString());
			valueOfOther = this.getElemValueToCompareWith();
			if (valueOfOther != null) {
				// compare using operator set
				PlanSpaceLogger.getInstance().log_RuleDebug("COND : comparing to " + valueOfOther.getValueAsString());
				conditionResult = this.compareTwoElemValues(valueToEvaluate, valueOfOther);
			} else {
				PlanSpaceLogger.getInstance().log_Error(
						"No contant value to evaluate with : " + DP_value_toEvaluate.getDataPointTypeCode());
			}
		} else{
			PlanSpaceLogger.getInstance()
					.log_Error("No value to evaluate : " + DP_value_toEvaluate.getDataPointTypeCode());
		}

		return conditionResult;
	}

	protected boolean compareToOtherDataPointValueInDomainInstance(DomainInstance theDomainInstance_of_DP_valueToCompare, DataPointValue DP_value_toEvaluate) {
		/*
		 * this method with compare the specified datapointValue in the arguments with
		 * the other Datapoint value of the instance specified  of type specified in condition
		 */

		boolean conditionResult;
		ElemValue valueOfOther, valueToEvaluate;

		// By default result is false
		conditionResult = false;

		PlanSpaceLogger.getInstance().log_RuleDebug("Compare to other DP value: " + this.dataPointTypeName_to_compareWith ); 
		// get value to Evaluate form the DataPointValue received in arguments
		valueToEvaluate = DP_value_toEvaluate.getElemValueAsValue();

		if (valueToEvaluate != null) {
			PlanSpaceLogger.getInstance().log_RuleDebug("COND DP : value to compare " + valueToEvaluate.getValueAsString());
			// Get the domain instance belonging to the datapoint in the arguments
		
			if (theDomainInstance_of_DP_valueToCompare != null) {
				// In that instance we will search for the (other) datapointtype we have to
				// compare with

				if ((this.dataPointTypeName_to_compareWith != "")
						&& (this.dataPointTypeName_to_compareWith != "null")) {
					valueOfOther = theDomainInstance_of_DP_valueToCompare
							.getElemValue_of_DataPointValueByTypeName(this.dataPointTypeName_to_compareWith);
					PlanSpaceLogger.getInstance().log_RuleDebug("COND DP : value to compare with " + valueToEvaluate.getValueAsString());
					if (valueOfOther != null) {

						// Compare the values against each
						conditionResult = this.compareTwoElemValues(valueToEvaluate, valueOfOther);

					} else {
						// FUture possible an Object
					}
				} else {
					PlanSpaceLogger.getInstance().log_RuleDebug("Comparision failed, no DP typename to compare with known: "
							+ DP_value_toEvaluate.getDataPointTypeCode());

				}
			} else {
				PlanSpaceLogger.getInstance().log_Error(
						"ERROR: dataPointValue without DomainInstance : " + DP_value_toEvaluate.getDataPointTypeCode());
			}
		} else {
			PlanSpaceLogger.getInstance()
					.log_Error("ERROR: No value to evaluate for : " + DP_value_toEvaluate.getDataPointTypeCode());
		}

		return conditionResult;
	}

	protected boolean compareTwoElemValues(ElemValue valueToEvaluate, ElemValue valueOfOther) {

		boolean conditionResult;

		conditionResult = false;

		if (this.myOperator != null) {
			// Evaluate elem values against each other depending on operator set
			if (this.myOperator.equals(t_elemExpressionOperator.IS_EQUAL)) {
				conditionResult = valueToEvaluate.iAm_EqualToElemValue(valueOfOther);
			} else if (this.myOperator.equals(t_elemExpressionOperator.IS_GREATER)) {
				conditionResult = valueToEvaluate.iAm_GreaterThanElemValue(valueOfOther);
			} else if (this.myOperator.equals(t_elemExpressionOperator.IS_GREATER_EQUAL)) {
				conditionResult = valueToEvaluate.iAm_GreaterOrEqualThanElemValue(valueOfOther);
			} else if (this.myOperator.equals(t_elemExpressionOperator.IS_LESS)) {
				conditionResult = valueToEvaluate.iAm_LessThanElemValue(valueOfOther);
			} else if (this.myOperator.equals(t_elemExpressionOperator.IS_LESS_EQUAL)) {
				conditionResult = valueToEvaluate.iAm_LessOrEqualThanElemValue(valueOfOther);
			} else {
			}

			// * If condition is negative we have to inverse the value determined
			if (this.isNot) {
				if (conditionResult == true) {
					conditionResult = false;
				} else if (conditionResult == false) {
					conditionResult = true;
				}

			}

		} else {
			PlanSpaceLogger.getInstance().log_Error("No operator set for ObjectDPValue condition ");

		}

		return conditionResult;
	}

}
