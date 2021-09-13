package com.olcap.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class AndTest {

	NotTest left;
	List<OperatorAndRightOperand<NotTest>> rights = new ArrayList<>();
	public AndTest(NotTest left, List<OperatorAndRightOperand<NotTest>> rights) {
		super();
		this.left = left;
		this.rights = rights;
	}
	public NotTest getLeft() {
		return left;
	}
	public List<OperatorAndRightOperand<NotTest>> getRights() {
		return rights;
	}
	@Override
	public String toString() {
		return "AndTest [left=" + left + ", rights=" + rights + "]";
	}
}
