package com.olcap.lang.symbol;

public class Symbol {
	
	String name;
	Object val;
	SymbolType type;
	public Symbol(String name, Object val, SymbolType type) {
		super();
		this.name = name;
		this.val = val;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
	public SymbolType getType() {
		return type;
	}
	public void setType(SymbolType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Symbol [name=" + name + ", val=" + val + ", type=" + type + "]";
	}
}
