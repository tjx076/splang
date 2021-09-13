package com.olcap.lang.symbol;

public class ExprScope  extends BaseScope{

	public ExprScope(Scope enclosingScope) {
		super(enclosingScope);
	}

	@Override
	public String getScopeName() {
		return "expr";
	}
}
