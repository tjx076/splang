package com.olcap.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class CommaExpr {
	
	List<CommaExprItem> items = new ArrayList<CommaExprItem>();
	
	public CommaExpr(List<CommaExprItem> items) {
		super();
		this.items = items;
	}
	@Override
	public String toString() {
		return "CommaExpr [items=" + items + "]";
	}
	public List<CommaExprItem> getItems() {
		return items;
	}
}
