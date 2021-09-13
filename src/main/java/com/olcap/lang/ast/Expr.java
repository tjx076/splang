package com.olcap.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class Expr {

	List<OLIdentifier> exprArgList = new ArrayList<OLIdentifier>();
	ExprBody exprBody;
	
	public Expr() {
		super();
	}
	public List<OLIdentifier> getExprArgList() {
		return exprArgList;
	}
	public ExprBody getExprBody() {
		return exprBody;
	}
	
	
	public void setExprArgList(List<OLIdentifier> exprArgList) {
		this.exprArgList = exprArgList;
	}
	public void setExprBody(ExprBody exprBody) {
		this.exprBody = exprBody;
	}
	@Override
	public String toString() {
		return "Expr [exprArgList=" + exprArgList + ", exprBody=" + exprBody + "]";
	}
}
