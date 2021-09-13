package com.olcap.lang;

import com.olcap.lang.ast.Expr;

public class BaseExpr {
	
	LangVisitor visitor;
	Expr expr;
	public BaseExpr(LangVisitor visitor, Expr expr) {
		super();
		this.visitor = visitor;
		this.expr = expr;
	}
	
}
