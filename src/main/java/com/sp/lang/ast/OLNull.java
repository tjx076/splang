package com.sp.lang.ast;

import com.sp.lang.Token;

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
