package planspace.context;

import planspace.domainTypes.ElemValue;
import planspace.planspaceEnumTypes.PlanSpaceEnumTypes.t_elemExpressionOperator;

public class ContextAspectProperty {
	private String aspectPropertyCode;
	private t_elemExpressionOperator operator;
	private ElemValue elemValueToCompareWith;
	private boolean isNot ;
	
	
	public ContextAspectProperty() {
		isNot = false;
		operator = t_elemExpressionOperator.IS_EQUAL;
		elemValueToCompareWith = null;
	}


	public String getAspectPropertyCode() {
		return aspectPropertyCode;
	}


	public void setAspectPropertyCode(String aspectPropertyCode) {
		this.aspectPropertyCode = aspectPropertyCode;
	}


	public t_elemExpressionOperator getOperator() {
		return operator;
	}


	public void setOperator(t_elemExpressionOperator operator) {
		this.operator = operator;
	}


	public ElemValue getElemValueToCompareWith() {
		return elemValueToCompareWith;
	}


	public void setElemValueToCompareWith(ElemValue elemValueToCompareWith) {
		this.elemValueToCompareWith = elemValueToCompareWith;
	}


	public boolean isNot() {
		return isNot;
	}


	public void setNot(boolean isNot) {
		this.isNot = isNot;
	}
	
	

}
