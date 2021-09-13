package com.sp.lang.ast;

import com.sp.lang.Token;

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
