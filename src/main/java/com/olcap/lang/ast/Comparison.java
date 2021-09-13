package com.olcap.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class Comparison {

	ArithExpr left;
	List<OperatorAndRightOperand<ArithExpr>> rights = new ArrayList<>();

	
	public Comparison(ArithExpr left, List<OperatorAndRightOperand<ArithExpr>> rights) {
		super();
		this.left = left;
		this.rights = rights;
	}
	public ArithExpr getLeft() {
		return left;
	}
	public List<OperatorAndRightOperand<ArithExpr>> getRights() {
		return rights;
	}
	@Override
	public String toString() {
		return "Comparison [left=" + left + ", rights=" + rights + "]";
	}
	
	
}

