package com.sp.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class ArithExpr {
	
	Term left;
	List<OperatorAndRightOperand<Term>> rights = new ArrayList<>();
	
	
	
	
	public ArithExpr(Term left, List<OperatorAndRightOperand<Term>> rights) {
		super();
		this.left = left;
		this.rights = rights;
	}
	public Term getLeft() {
		return left;
	}
	public List<OperatorAndRightOperand<Term>> getRights() {
		return rights;
	}
	@Override
	public String toString() {
		return "ArithExpr [left=" + left + ", rights=" + rights + "]";
	}
	
	
	
}
