package com.olcap.lang.ast;

import com.olcap.lang.Token;

public class OLBoolean extends OLPrimitive{
	private Boolean val;
	
	public OLBoolean(Token token) {
		super(token);
		val = Boolean.parseBoolean(token.getText());
	}

	public Boolean getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "OLBoolean [val=" + val + "]";
	}
}
