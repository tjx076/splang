package com.olcap.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class Array {
	
	List<Element> eles = new ArrayList<Element>();

	public Array(List<Element> eles) {
		super();
		this.eles = eles;
	}
	
	public List<Element> getEles() {
		return eles;
	}

	@Override
	public String toString() {
		return "Array [eles=" + eles + "]";
	}
}
