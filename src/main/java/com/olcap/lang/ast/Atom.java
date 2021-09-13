package com.olcap.lang.ast;

public class Atom {
	
	private Object val;

	public Atom(Object val) {
		super();
		this.val = val;
	}

	public Object getVal() {
		return val;
	}

	public boolean isFuncCall() {
		return val instanceof FuncCall;
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
	
	public boolean isRefIdentifier() {
		return val instanceof OLRefIdentifier;
	}
	
	public boolean isIndexIdentifier() {
		return val instanceof OLIndexIdentifier;
	}
	
	public boolean isIdentifier() {
		return val instanceof OLIdentifier;
	}
	
	public boolean isOrTest() {
		return val instanceof OrTest;
	}
	
	@Override
	public String toString() {
		return "Atom [val=" + val + "]";
	}
}
