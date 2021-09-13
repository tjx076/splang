package com.olcap.lang.ast;

public class NotTest {

	
	private Object val;

	public NotTest(OperatorAndRightOperand<NotTest> val) {
		super();
		this.val = val;
	}
	
	public NotTest(Comparison val) {
		super();
		this.val = val;
	}
	
	public boolean isComparison() {
		return val instanceof Comparison;
	}

	public Object getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "NotTest [val=" + val + "]";
	}
}
