package com.sp.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class Term {
	
	Atom left;
	List<OperatorAndRightOperand<Atom>> rights = new ArrayList<>();
	public Term(Atom left, List<OperatorAndRightOperand<Atom>> rights) {
		super();
		this.left = left;
		this.rights = rights;
	}
	public Atom getLeft() {
		return left;
	}
	public List<OperatorAndRightOperand<Atom>> getRights() {
		return rights;
	}
	@Override
	public String toString() {
		return "Term [left=" + left + ", rights=" + rights + "]";
	}
}
