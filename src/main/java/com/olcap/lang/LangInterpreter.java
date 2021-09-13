package com.olcap.lang;

import java.util.List;

import com.olcap.lang.ast.AssignExpr;
import com.olcap.lang.ast.FuncCall;
import com.olcap.lang.symbol.GlobalScope;
import com.olcap.lang.ast.AST;
import com.olcap.lang.ast.ObjDecl;
import com.olcap.lang.ast.FuncDecl;
//import Symbol;
//import SymbolType;

public class LangInterpreter {

	LangParser parser;
	GlobalScope globalScope;
	List<AST> astList ;
	LangVisitor visitor;
	
	
	public LangInterpreter (LangParser parser) {
		this.parser = parser;
		parser.parse();
		astList = parser.getASTList();
		globalScope = new GlobalScope();
		visitor = new LangVisitor(globalScope);
		
		initBuildinFunc();
	}
	
	private void initBuildinFunc() {
//		globalScope.define(new Symbol("sumInt", "Enumerable.sumInt", SymbolType.FuncDecl));
	}
	
	
	public Object interpret() {
		
		Object returnVal = null;
		
		for(AST ast : astList) {
			
			if(ast instanceof FuncDecl) {
				visitor.visit((FuncDecl)ast);
				continue;
			}
			
			if(ast instanceof ObjDecl) {
				visitor.visit((ObjDecl)ast);
				continue;
			}
			
			if(ast instanceof FuncCall) {
				returnVal = visitor.visit((FuncCall)ast);
				continue;
			}
			
			if(ast instanceof AssignExpr) {
				visitor.visit((AssignExpr)ast);
				continue;
			}
			
			throw new InterpretException("unknown instruction, " + ast);
		}
		
		return returnVal;
	}
	
	public LangVisitor getVisitor() {
		return visitor;
	}
	
	public GlobalScope getGlobalScope() {
		return globalScope;
	}
}
