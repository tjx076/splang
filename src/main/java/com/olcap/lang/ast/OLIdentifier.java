package com.olcap.lang.ast;

import com.olcap.lang.Token;

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
