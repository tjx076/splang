package com.sp.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class OrTest {
	
	AndTest left;
	List<OperatorAndRightOperand<AndTest>> rights = new ArrayList<>();
	
	
	
	
	public OrTest(AndTest left, List<OperatorAndRightOperand<AndTest>> rights) {
		super();
		this.left = left;
		this.rights = rights;
	}
	public AndTest getLeft() {
		return left;
	}
	public List<OperatorAndRightOperand<AndTest>> getRights() {
		return rights;
	}
	@Override
	public String toString() {
		return "OrTest [left=" + left + ", rights=" + rights + "]";
	}
	
	
}
