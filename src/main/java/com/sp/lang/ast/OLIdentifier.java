package com.sp.lang.ast;

import com.sp.lang.Token;

public class OLIdentifier extends OLPrimitive{

	private String val;
	
	public OLIdentifier(Token token) {
		super(token);
		val = token.getText();
	}

	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "OLIdentifier [val=" + val + "]";
	}
}
