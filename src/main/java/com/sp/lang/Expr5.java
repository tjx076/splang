package com.sp.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sp.lang.ast.Expr;
import com.sp.lang.ast.OLIdentifier;

public class Expr5<T1,T2,T3, T4,T5, R> extends BaseExpr {

	public Expr5(LangVisitor visitor, Expr expr) {
		super(visitor, expr);
	}
	
	@SuppressWarnings("unchecked")
	public R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
		
		List<OLIdentifier> exprArgList = expr.getExprArgList();
		String key1 = exprArgList.get(0).getVal();
		String key2 = exprArgList.get(1).getVal();
		String key3 = exprArgList.get(2).getVal();
		String key4 = exprArgList.get(3).getVal();
		String key5 = exprArgList.get(4).getVal();
		
		Map<String, Object> exprArgVals = new HashMap<>();
		exprArgVals.put(key1, t1);
		exprArgVals.put(key2, t2);
		exprArgVals.put(key3, t3);
		exprArgVals.put(key4, t4);
		exprArgVals.put(key5, t5);
		
		return (R)visitor.visit(expr, exprArgVals);
	}
	
}
