package com.sp.lang.ast;

public class DicEntry {

	private OLIdentifier key;
	private Element val;
	
	public DicEntry(OLIdentifier key, Element val) {
		super();
		this.key = key;
		this.val = val;
	}
	public OLIdentifier getKey() {
		return key;
	}
	public void setKey(OLIdentifier key) {
		this.key = key;
	}
	public Element getVal() {
		return val;
	}
	public void setVal(Element val) {
		this.val = val;
	}
	@Override
	public String toString() {
		return "DicEntry [key=" + key + ", val=" + val + "]";
	}
}
