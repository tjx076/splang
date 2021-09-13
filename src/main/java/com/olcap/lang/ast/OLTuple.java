package com.olcap.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class OLTuple {
	
	List<Element> eles = new ArrayList<Element>();

	public OLTuple(List<Element> eles) {
		super();
		this.eles = eles;
	}
	
	public List<Element> getEles() {
		return eles;
	}

	@Override
	public String toString() {
		return "Tuple [eles=" + eles + "]";
	}
}
