package com.olcap.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.olcap.lang.ast.Expr;
import com.olcap.lang.ast.OLIdentifier;

public class Expr3<T1,T2,T3, R> extends BaseExpr {

	public Expr3(LangVisitor visitor, Expr expr) {
		super(visitor, expr);
	}
	
	@SuppressWarnings("unchecked")
	public R apply(T1 t1, T2 t2, T3 t3) {
		
		List<OLIdentifier> exprArgList = expr.getExprArgList();
		String key1 = exprArgList.get(0).getVal();
		String key2 = exprArgList.get(1).getVal();
		String key3 = exprArgList.get(2).getVal();
		
		Map<String, Object> exprArgVals = new HashMap<>();
		exprArgVals.put(key1, t1);
		exprArgVals.put(key2, t2);
		exprArgVals.put(key3, t3);
		
		return (R)visitor.visit(expr, exprArgVals);
	}
	
}
