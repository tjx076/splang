package com.olcap.lang.ast;

import com.olcap.lang.Token;

public class OLPrimitive {
	protected Token token;

	public OLPrimitive(Token token) {
		super();
		this.token = token;
	}

	public Token getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "OLPrimitive [token=" + token + "]";
	}
}
