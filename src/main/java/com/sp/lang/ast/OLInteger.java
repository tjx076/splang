package com.sp.lang.ast;

import com.sp.lang.Token;

public class OLInteger extends OLPrimitive{
	
	private Long val;
	
	public OLInteger(Token token) {
		super(token);
		val = Long.parseLong(token.getText());
	}

	public Long getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "OLInteger [val=" + val + "]";
	}
}
