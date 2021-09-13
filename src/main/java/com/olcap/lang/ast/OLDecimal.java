package com.olcap.lang.ast;

import com.olcap.lang.Token;

public class OLDecimal extends  OLPrimitive{
	
	private Double val;
	
	public OLDecimal(Token token) {
		super(token);
		val= Double.parseDouble(token.getText());
	}

	public Double getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "OLDecimal [val=" + val + "]";
	}
}
