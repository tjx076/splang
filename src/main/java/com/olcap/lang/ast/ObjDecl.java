package com.olcap.lang.ast;

import com.olcap.lang.Token;

public class ObjDecl extends AST {
	private OLIdentifier objDeclName;
	private OLString objClassFullyQualifiedName;
	public ObjDecl(OLIdentifier objDeclName, OLString objClassFullyQualifiedName) {
		super();
		this.objDeclName = objDeclName;
		this.objClassFullyQualifiedName = objClassFullyQualifiedName;
	}
	
	public ObjDecl(Token objDeclName, Token objClassFullyQualifiedName) {
		this(new OLIdentifier(objDeclName), new OLString(objClassFullyQualifiedName));
	}

	public OLIdentifier getObjDeclName() {
		return objDeclName;
	}

	public OLString getObjClassFullyQualifiedName() {
		return objClassFullyQualifiedName;
	}

	@Override
	public String toString() {
		return "ObjDecl [objDeclName=" + objDeclName + ", objClassFullyQualifiedName=" + objClassFullyQualifiedName
				+ "]";
	}
}
