package com.sp.lang.ast;

public class CommaExprItem {
	private Object val;

	public CommaExprItem(OLRefIdentifier val) {
		super();
		this.val = val;
	}
	
	public CommaExprItem(OLIndexIdentifier val) {
		super();
		this.val = val;
	}
	
	public CommaExprItem(OLIdentifier val) {
		super();
		this.val = val;
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

	public Object getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "CommaExprItem [val=" + val + "]";
	}
}
