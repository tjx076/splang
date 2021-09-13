package com.olcap.lang.symbol;

import java.util.HashMap;
import java.util.Map;

import com.olcap.lang.InterpretException;

public abstract class BaseScope implements Scope{

	Scope enclosingScope = null;
	Map<String, Symbol> symbols = new HashMap<String, Symbol>();
	
	public BaseScope(Scope enclosingScope) {
		this.enclosingScope = enclosingScope;
	}
	
	
	@Override
	public Scope getEnclosingScope() {
		return enclosingScope;
	}

	@Override
	public void define(Symbol sym) {
		Symbol old = symbols.get(sym.getName());
		if(old!=null){
			throw new InterpretException("Symbol already exists, " + old);
		}
		
		symbols.put(sym.getName(), sym);
	}

	@Override
	public Symbol resolve(String name) {
		Symbol s = symbols.get(name);
		if(s!=null){
			return s;
		}
		
		if(enclosingScope!=null) {
			return enclosingScope.resolve(name);
		}
		
		return null;
	}


	@Override
	public String toString() {
		return "BaseScope [enclosingScope=" + enclosingScope + ", symbols=" + symbols + "]";
	}
}
