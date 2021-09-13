package com.olcap.lang.ast;

import com.olcap.lang.Token;

public class OLNull extends OLPrimitive{
	
	private Object val = null;
	
	public OLNull(Token token) {
		super(token);
	}

	public Object getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "OLNull [val=" + val + "]";
	}
}
