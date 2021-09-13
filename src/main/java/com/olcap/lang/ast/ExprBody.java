package com.olcap.lang.ast;

public class ExprBody {

	private Object val;

	public ExprBody(Object val) {
		super();
		this.val = val;
	}

	public Object getVal() {
		return val;
	}
	
	public boolean isOrTest() {
		return val instanceof OrTest;
	}
	
	public boolean isCommaExpr() {
		return val instanceof CommaExpr;
	}

	@Override
	public String toString() {
		return "ExprBody [val=" + val + "]";
	}
}
