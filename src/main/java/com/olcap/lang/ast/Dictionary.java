package com.olcap.lang.ast;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	
	List<DicEntry> entries = new ArrayList<DicEntry>();

	public Dictionary(List<DicEntry> entries) {
		super();
		this.entries = entries;
	}

	public List<DicEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<DicEntry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "Dictionary [entries=" + entries + "]";
	}

}
