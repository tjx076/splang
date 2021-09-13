package com.olcap.lang.ast;

import com.olcap.lang.Token;

public class OLString extends OLPrimitive{
	
	private String val;
	
	public OLString(Token token) {
		super(token);
		val = token.getText().substring(1,  token.getText().length()-1);
	}

	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "OLString [val=" + val + "]";
	}
}
