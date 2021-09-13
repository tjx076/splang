package com.sp.lang.ast;

public class AssignExpr extends AST{

	OLIdentifier assignLeftVal;
	Object assignRightOperand;
	
	public AssignExpr(OLIdentifier assignLeftVal, Object assignRightOperand) {
		super();
		this.assignLeftVal = assignLeftVal;
		this.assignRightOperand = assignRightOperand;
	}
	public OLIdentifier getAssignLeftVal() {
		return assignLeftVal;
	}
	public Object getAssignRightOperand() {
		return assignRightOperand;
	}
	@Override
	public String toString() {
		return "AssignExpr [assignLeftVal=" + assignLeftVal + ", assignRightOperand=" + assignRightOperand + "]";
	}
}
