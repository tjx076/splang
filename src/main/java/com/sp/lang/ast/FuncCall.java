package com.sp.lang.ast;

import java.util.ArrayList;
import java.util.List;

import com.sp.lang.Token;

public class FuncCall extends AST {

	private OLIdentifier funcName;
	private List<FuncArg> argList = new ArrayList<FuncArg>();
	
	public void addArg(FuncArg arg) {
		argList.add(arg);
	}
	
	public void setFuncName(Token funcName) {
		setFuncName(new OLIdentifier(funcName));
	}
	
	public OLIdentifier getFuncName() {
		return funcName;
	}
	public void setFuncName(OLIdentifier funcName) {
		this.funcName = funcName;
	}
	public List<FuncArg> getArgList() {
		return argList;
	}
	public void setArgList(List<FuncArg> argList) {
		this.argList = argList;
	}

	@Override
	public String toString() {
		return "FuncCall [funcName=" + funcName + ", argList=" + argList + "]";
	}
}
