package com.olcap.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.olcap.lang.ast.Expr;
import com.olcap.lang.ast.OLIdentifier;

public class Expr1<T1, R> extends BaseExpr {

	public Expr1(LangVisitor visitor, Expr expr) {
		super(visitor, expr);
	}
	
	@SuppressWarnings("unchecked")
	public R apply(T1 t1) {
		
		List<OLIdentifier> exprArgList = expr.getExprArgList();
		String key1 = exprArgList.get(0).getVal();
		
		Map<String, Object> exprArgVals = new HashMap<>();
		exprArgVals.put(key1, t1);
		
		return (R)visitor.visit(expr, exprArgVals);
	}
	
}
