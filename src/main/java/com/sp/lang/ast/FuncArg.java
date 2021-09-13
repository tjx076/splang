package com.sp.lang.ast;

public class FuncArg {
	
	private Object val;

	public FuncArg(Object val) {
		super();
		this.val = val;
	}

	public Object getVal() {
		return val;
	}

	public boolean isFuncCall() {
		return val instanceof FuncCall;
	}
	
	public boolean isIdentifier() {
		return val instanceof OLIdentifier;
	}
	
	
	public boolean isInteger() {
		return val instanceof OLInteger;
	}
	
	public boolean isDecimal() {
		return val instanceof OLDecimal;
	}
	
	public boolean isString() {
		return val instanceof OLString;
	}
	
	public boolean isBoolean() {
		return val instanceof OLBoolean;
	}
	
	public boolean isNull() {
		return val instanceof OLNull;
	}
	
	public boolean isDictionary() {
		return val instanceof Dictionary;
	}
	
	public boolean isArray() {
		return val instanceof Array;
	}
	
	public boolean isTuple() {
		return val instanceof OLTuple;
	}
	
	public boolean isExpr() {
		return val instanceof Expr;
	}

	@Override
	public String toString() {
		return "FuncArg [val=" + val + "]";
	}
}
