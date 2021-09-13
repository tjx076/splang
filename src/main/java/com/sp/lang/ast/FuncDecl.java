package com.sp.lang.ast;

import com.sp.lang.Token;

public class FuncDecl extends AST {
	private OLIdentifier funcDeclName;
	private OLString javaStaticMethodFullyQualifiedName;
	public FuncDecl(OLIdentifier funcDeclName, OLString javaStaticMethodFullyQualifiedName) {
		super();
		this.funcDeclName = funcDeclName;
		this.javaStaticMethodFullyQualifiedName =javaStaticMethodFullyQualifiedName;
	}
	
	public FuncDecl(Token funcDeclName, Token javaStaticMethodFullyQualifiedName) {
		this(new OLIdentifier(funcDeclName), new OLString(javaStaticMethodFullyQualifiedName));
	}

	public OLIdentifier getFuncDeclName() {
		return funcDeclName;
	}

	public OLString getJavaStaticMethodFullyQualifiedName() {
		return javaStaticMethodFullyQualifiedName;
	}

	@Override
	public String toString() {
		return "FuncDecl [funcDeclName=" + funcDeclName + ", javaStaticMethodFullyQualifiedName="
				+ javaStaticMethodFullyQualifiedName + "]";
	}
}
